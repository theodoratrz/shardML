#!/usr/bin/env bash
# Integration test: does the cost model route like Citus? (Plan-phase3 4.5b; the static half of the model,
# before Phase 4 adds load.) For each example layout (banking C and A, TPC-C W and S), in a fresh schema:
#
#   1. deploy the generated schema, distribution, placement and foreign keys (if any);
#   2. keys: `shardml route --keys 10000` gives keys of each key type with the model's bucket and node; one
#      query compares them with get_shard_id_for_distribution_column (bucket = the shard's index by
#      shardminvalue, node = its placement);
#   3. statements: `shardml route` gives every workload statement as SQL for sampled bindings, with the
#      model's prediction (shape, task count, nodes); each runs as EXPLAIN (FORMAT JSON) with
#      citus.explain_all_tasks and citus.enable_repartition_joins on, and is compared: Job."Task Count",
#      the nodes of the tasks, and whether Job."Dependent Jobs" exists (a repartition) exactly when the model
#      says repartition.
#
# Differences that are stated model assumptions are listed in bench/integration/cost-routing/allowlist.tsv
# with their reason; any other difference fails, and so does a listed one that no longer occurs. Nothing
# executes but EXPLAIN; the schema is dropped at the end.
#
#   bench/integration/test-cost-routing.sh
set -uo pipefail

ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
BENCH="$ROOT/bench"
HERE="$ROOT/bench/integration/cost-routing"
ALLOWLIST="$HERE/allowlist.tsv"
SCHEMA=cost_routing
SAMPLES=${SAMPLES:-10}
KEYS=${KEYS:-10000}

shopt -s nullglob
JAR=
for jar in "$ROOT"/shardml.cli/target/*-cli.jar; do
	if [[ -z "$JAR" || "$jar" -nt "$JAR" ]]; then
		JAR="$jar"
	fi
done
if [[ -z "$JAR" ]]; then
	echo "test-cost-routing: no CLI jar in shardml.cli/target; run 'mvn install' first" >&2
	exit 2
fi

if ! up_log=$("$BENCH/up.sh" 2>&1); then
	echo "$up_log" >&2
	echo "test-cost-routing: bench/up.sh failed" >&2
	exit 2
fi

# psql on the coordinator, in the scratch schema, reading stdin
psql_db() {
	docker compose -f "$BENCH/docker-compose.yml" exec -T -e PGOPTIONS="-c client_min_messages=warning -c search_path=$SCHEMA" \
		coordinator psql -U postgres -v ON_ERROR_STOP=1 -qAtX "$@"
}

reset_schema() {
	psql_db -c "DROP SCHEMA IF EXISTS $SCHEMA CASCADE;" -c "CREATE SCHEMA $SCHEMA;" </dev/null >/dev/null \
		|| { echo "test-cost-routing: could not reset schema $SCHEMA" >&2; exit 2; }
}

work=$(mktemp -d)
trap 'rm -rf "$work"; psql_db -c "DROP SCHEMA IF EXISTS $SCHEMA CASCADE;" </dev/null >/dev/null 2>&1' EXIT
failures=0
: >"$work/differences"

# label, model, workloads
CASES=(
	"banking-by-customer|examples/cost/banking-by-customer.shardml|examples/cost/banking.workload"
	"banking-by-account|examples/cost/banking-by-account.shardml|examples/cost/banking.workload"
	"tpcc-by-warehouse|examples/cost/tpcc-by-warehouse.shardml|examples/cost/tpcc.workload bench/integration/cost-routing/shapes.workload"
	"tpcc-stock-by-item|examples/cost/tpcc-stock-by-item.shardml|examples/cost/tpcc.workload bench/integration/cost-routing/shapes.workload"
)

for case in "${CASES[@]}"; do
	IFS='|' read -r label model workloads <<<"$case"
	echo "== $label"
	out="$work/$label"
	mkdir -p "$out"
	if ! java -jar "$JAR" generate "$ROOT/$model" -o "$out/gen" >"$out/gen.log" 2>&1; then
		echo "  FAIL  shardml generate"
		sed 's/^/        /' "$out/gen.log"
		failures=$((failures + 1))
		continue
	fi
	db=$(basename "$out"/gen/*-schema.sql -schema.sql)
	reset_schema
	deployed=1
	for step in schema distribution placement foreign-keys; do
		file="$out/gen/$db-$step.sql"
		[[ -f "$file" ]] || continue
		if ! psql_db <"$file" >"$out/$step.log" 2>&1; then
			echo "  FAIL  $db-$step.sql"
			sed 's/^/        /' "$out/$step.log" | tail -5
			failures=$((failures + 1))
			deployed=0
		fi
	done
	[[ $deployed -eq 1 ]] || continue

	# (a) keys
	first=${workloads%% *}
	java -jar "$JAR" route --keys "$KEYS" "$ROOT/$model" "$ROOT/$first" >"$out/keys.tsv" 2>/dev/null
	python3 - "$out/keys.tsv" >"$out/keys.sql" <<'PY'
import sys
rows = [line.rstrip("\n").split("\t") for line in open(sys.argv[1]) if line.strip()]
values = ",\n".join("('%s', '%s', %s, '%s')" % (t, k, b, n) for t, k, b, n in rows)
print("""WITH k(tbl, key, bucket, node) AS (VALUES
%s),
actual AS (
    SELECT k.*, s.bucket AS actual_bucket, n.nodename || ':' || n.nodeport AS actual_node
    FROM k
    JOIN LATERAL (SELECT get_shard_id_for_distribution_column(k.tbl::regclass, k.key) AS shardid) g ON true
    JOIN (SELECT shardid, logicalrelid, row_number() OVER (PARTITION BY logicalrelid ORDER BY shardminvalue::int) - 1 AS bucket
          FROM pg_dist_shard) s ON s.shardid = g.shardid
    JOIN pg_dist_placement p ON p.shardid = g.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
)
SELECT count(*) || ' keys, ' || count(*) FILTER (WHERE bucket <> actual_bucket) || ' bucket mismatches, '
       || count(*) FILTER (WHERE node <> actual_node) || ' node mismatches; first: '
       || coalesce(min(tbl || ' ' || key || ' model ' || bucket || '/' || node || ' citus ' || actual_bucket || '/' || actual_node)
                   FILTER (WHERE bucket <> actual_bucket OR node <> actual_node), 'none')
FROM actual;""" % values)
PY
	if ! keys=$(psql_db <"$out/keys.sql" 2>&1); then
		echo "  FAIL  keys: $keys"
		failures=$((failures + 1))
	else
		echo "  keys  $keys"
		if [[ "$keys" != *" 0 bucket mismatches, 0 node mismatches"* ]]; then
			failures=$((failures + 1))
			echo "  FAIL  keys: the model's bucket or node differs from Citus"
		fi
	fi

	# (b) statements
	: >"$out/route.tsv"
	for w in $workloads; do
		java -jar "$JAR" route --samples "$SAMPLES" "$ROOT/$model" "$ROOT/$w" 2>/dev/null \
			| sed "s|^|$(basename "$w" .workload)\t|" >>"$out/route.tsv"
	done
	{
		echo "SET citus.explain_all_tasks = on;"
		echo "SET citus.enable_repartition_joins = on;"
		n=0
		while IFS=$'\t' read -r wl template statement sample shape tasks nodes sql; do
			n=$((n + 1))
			echo "\\echo @@$n"
			echo "EXPLAIN (FORMAT JSON) $sql;"
		done <"$out/route.tsv"
	} >"$out/explain.sql"
	if ! psql_db <"$out/explain.sql" >"$out/explain.out" 2>&1; then
		echo "  FAIL  EXPLAIN"
		tail -5 "$out/explain.out" | sed 's/^/        /'
		failures=$((failures + 1))
		continue
	fi
	python3 - "$label" "$out/route.tsv" "$out/explain.out" >>"$work/differences" <<'PY'
import json, re, sys
label, route_file, explain_file = sys.argv[1:4]
routes = [line.rstrip("\n").split("\t") for line in open(route_file) if line.strip()]
blocks = re.split(r"^@@(\d+)$", open(explain_file).read(), flags=re.M)
plans = {int(blocks[i]): json.loads(blocks[i + 1]) for i in range(1, len(blocks), 2)}
assert len(plans) == len(routes), (len(plans), len(routes))
checked = 0
for n, (wl, template, statement, sample, shape, tasks, nodes, sql) in enumerate(routes, 1):
    job = plans[n][0]["Plan"].get("Distributed Query", {}).get("Job", {})
    repartition = "Dependent Jobs" in job
    actual_tasks = str(job.get("Task Count", "-"))
    actual_nodes = set()
    for task in job.get("Tasks", []):
        m = re.search(r"host=(\S+) port=(\d+)", task.get("Node", ""))
        if m:
            actual_nodes.add(m.group(1) + ":" + m.group(2))
    actual_nodes = ",".join(sorted(actual_nodes)) or "-"
    key = "\t".join([label, wl, template, statement])
    checked += 1
    if (shape == "repartition") != repartition:
        print(key + "\trepartition\tmodel " + shape + ", citus " + ("repartition" if repartition else "no repartition") + "\t" + sql)
    if shape != "repartition":
        if tasks != actual_tasks:
            print(key + "\ttasks\tmodel " + tasks + ", citus " + actual_tasks + "\t" + sql)
        if nodes != actual_nodes:
            print(key + "\tnodes\tmodel " + nodes + ", citus " + actual_nodes + "\t" + sql)
print("#checked\t" + label + "\t" + str(checked))
PY
	echo "  $(grep -c "" "$out/route.tsv") statements checked"
done

# every difference: listed (layout, workload, template, statement, field) with its reason, or a failure
python3 - "$work/differences" "$ALLOWLIST" <<'PY'
import sys
diffs, allow_file = sys.argv[1], sys.argv[2]
allowed = {}
for line in open(allow_file):
    if line.startswith("#") or not line.strip():
        continue
    f = line.rstrip("\n").split("\t")
    allowed[tuple(f[:5])] = f[5] if len(f) > 5 else ""
used, failures, summary = set(), 0, {}
for line in open(diffs):
    f = line.rstrip("\n").split("\t")
    if f[0] == "#checked":
        summary[f[1]] = int(f[2])
        continue
    key = tuple(f[:5])
    if key in allowed:
        used.add(key)
    else:
        failures += 1
        print("  FAIL  " + " ".join(f[:5]) + ": " + f[5] + "\n        " + f[6])
for key, reason in allowed.items():
    if key in used:
        print("  allowed  " + " ".join(key) + " (" + reason + ")")
    else:
        failures += 1
        print("  FAIL  stale allowlist entry: " + " ".join(key))
for label, n in summary.items():
    print("  " + label + ": " + str(n) + " statement samples checked")
sys.exit(1 if failures else 0)
PY
[[ $? -eq 0 ]] || failures=$((failures + 1))

echo
if [[ $failures -gt 0 ]]; then
	echo "FAILED: $failures"
	exit 1
fi
echo "OK: routing matches Citus for every layout, but for the allowlist"
