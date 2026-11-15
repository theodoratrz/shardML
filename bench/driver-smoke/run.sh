#!/usr/bin/env bash
# Smoke test of the validation driver (Plan-phase4 P4.1) on the local bench cluster: does it connect
# through the workers, replay, count and write its result? Its numbers are NOT performance results (the
# cluster runs under amd64 emulation on a laptop, and the workload is scaled down): never quote them as
# results.
#
# For each banking layout (C: by customer, A: by account), in a scratch schema:
#   1. deploy the generated schema, distribution and placement;
#   2. fill the tables for banking-smoke.workload, whose mapping is closed-form (10 accounts per customer,
#      2 transactions rows per account), with plain SQL (this is not the Phase 4 loader);
#   3. run the driver in a JDK container on the cluster's network (the workers publish no port), mode (b):
#      clients round-robin over worker1..3; closed loop, then open loop at 70% of the closed loop's rate;
#   4. print throughput, p99, error counts and driver CPU from result.json.
#
#   bench/driver-smoke/run.sh            (WARMUP, DURATION, CLIENTS, JDK_IMAGE override the defaults)
set -uo pipefail

ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
BENCH="$ROOT/bench"
HERE="$BENCH/driver-smoke"
SCHEMA=driver_smoke
WARMUP=${WARMUP:-5}
DURATION=${DURATION:-20}
CLIENTS=${CLIENTS:-8}
JDK_IMAGE=${JDK_IMAGE:-eclipse-temurin:21-jre}
NETWORK=shardml-bench_default

shopt -s nullglob
CLI= DRIVER=
for jar in "$ROOT"/shardml.cli/target/*-cli.jar; do CLI="$jar"; done
for jar in "$ROOT"/shardml.bench/target/*-driver.jar; do DRIVER="$jar"; done
if [[ -z "$CLI" || -z "$DRIVER" ]]; then
	echo "driver-smoke: no CLI or driver jar under target/; run 'mvn install' first" >&2
	exit 2
fi
if ! up_log=$("$BENCH/up.sh" 2>&1); then
	echo "$up_log" >&2
	echo "driver-smoke: bench/up.sh failed" >&2
	exit 2
fi

psql_db() {
	docker compose -f "$BENCH/docker-compose.yml" exec -T -e PGOPTIONS="-c client_min_messages=warning -c search_path=$SCHEMA" \
		coordinator psql -U postgres -v ON_ERROR_STOP=1 -qAtX "$@"
}

work=$(mktemp -d)
trap 'rm -rf "$work"; psql_db -c "DROP SCHEMA IF EXISTS $SCHEMA CASCADE;" </dev/null >/dev/null 2>&1' EXIT
commit=$(git -C "$ROOT" rev-parse HEAD 2>/dev/null || echo unknown)
failures=0

# the driver in a container on the cluster's network; the repository read-only, runs written to $work/runs
driver() {
	docker run --rm --network "$NETWORK" -v "$ROOT":/repo:ro -v "$work":/work -w /repo "$JDK_IMAGE" \
		java -jar "/repo/${DRIVER#"$ROOT"/}" run --targets worker1:5432,worker2:5432,worker3:5432 --entry workers \
		--schema "$SCHEMA" --clients "$CLIENTS" --warmup "$WARMUP" --duration "$DURATION" --git-commit "$commit" \
		--out /work/runs "$@" bench/driver-smoke/banking-smoke.workload "$model" </dev/null
}

field() {
	python3 - "$1" "$2" <<'PY'
import json, sys
r = json.load(open(sys.argv[1]))
v = r
for k in sys.argv[2].split("."):
    v = v[k]
print(v)
PY
}

report() {
	python3 - "$1" "$2" <<'PY'
import json, sys
label, path = sys.argv[1], sys.argv[2]
r = json.load(open(path))
t, l, c = r["throughput"], r["latencyMicros"]["overall"], r["driverCpu"]
errors = ", ".join("%s %d" % (k, v) for k, v in r["errors"].items())
line = "  %-26s committed %6d  %7.1f tx/s  p99 %8.1f ms  errors: %s  driver CPU %.0f%% (max %.0f%%, %d cores)%s" % (
    label, t["committed"], t["committedPerSecond"], (l["p99"] or 0) / 1000, errors, 100 * c["mean"], 100 * c["max"], c["cores"],
    " OVER LIMIT" if c["overLimit"] else "")
if r["mode"] == "open":
    line += "\n  %-26s offered %.1f tx/s, achieved %.1f tx/s, unstarted %d, schedule lag p99 %.1f ms" % (
        "", t["offeredPerSecond"], t["achievedPerSecond"], t["unstarted"], (r["scheduleLagMicros"]["p99"] or 0) / 1000)
if not r["accounting"]["balanced"]:
    line += "\n  ACCOUNTING DOES NOT BALANCE"
print(line)
PY
}

for model in examples/cost/banking-by-customer.shardml examples/cost/banking-by-account.shardml; do
	label=$(basename "$model" .shardml)
	echo "== $label"
	out="$work/$label"
	mkdir -p "$out"
	if ! java -jar "$CLI" generate "$ROOT/$model" -o "$out/gen" >"$out/gen.log" 2>&1; then
		echo "  FAIL  shardml generate"; failures=$((failures + 1)); continue
	fi
	db=$(basename "$out"/gen/*-schema.sql -schema.sql)
	psql_db -c "DROP SCHEMA IF EXISTS $SCHEMA CASCADE;" -c "CREATE SCHEMA $SCHEMA;" </dev/null >/dev/null
	deployed=1
	for step in schema distribution placement; do
		file="$out/gen/$db-$step.sql"
		[[ -f "$file" ]] || continue
		if ! psql_db <"$file" >"$out/$step.log" 2>&1; then
			echo "  FAIL  $db-$step.sql"; tail -5 "$out/$step.log"; failures=$((failures + 1)); deployed=0
		fi
	done
	[[ $deployed -eq 1 ]] || continue
	# banking-smoke.workload's rows: account a belongs to customer (a - 1) / 10 + 1, transactions row t to account
	# (t - 1) / 2 + 1; card c to customer c
	if ! psql_db >"$out/fill.log" 2>&1 <<'SQL'
INSERT INTO customers (id, name) SELECT g, 'customer ' || g FROM generate_series(1, 300) g;
INSERT INTO cards (id, customer_id) SELECT g, g FROM generate_series(1, 300) g;
INSERT INTO accounts (id, customer_id, balance) SELECT g, (g - 1) / 10 + 1, 100 FROM generate_series(1, 3000) g;
INSERT INTO transactions (id, account_id, customer_id, amount)
    SELECT g, (g - 1) / 2 + 1, ((g - 1) / 2) / 10 + 1, 1 FROM generate_series(1, 6000) g;
ANALYZE;
SQL
	then
		echo "  FAIL  fill"; tail -5 "$out/fill.log"; failures=$((failures + 1)); continue
	fi
	if ! driver --mode closed >"$out/closed.log" 2>&1; then
		echo "  FAIL  closed loop"; tail -5 "$out/closed.log"; failures=$((failures + 1)); continue
	fi
	closed=$(ls "$work"/runs/banking_smoke/"$db"/workers/closed-*/*/*/result.json)
	report "$label closed (c$CLIENTS)" "$closed"
	rate=$(python3 -c "import sys; print(max(1, round(0.7 * float(sys.argv[1]))))" "$(field "$closed" throughput.committedPerSecond)")
	if ! driver --mode open --rate "$rate" >"$out/open.log" 2>&1; then
		echo "  FAIL  open loop"; tail -5 "$out/open.log"; failures=$((failures + 1)); continue
	fi
	report "$label open ($rate tx/s)" "$(ls "$work"/runs/banking_smoke/"$db"/workers/open-*/*/*/result.json)"
done

if [[ -n "${KEEP:-}" ]]; then
	mkdir -p "$KEEP" && cp -R "$work/runs" "$KEEP/" && echo "runs copied to $KEEP/runs"
fi
echo
if [[ $failures -gt 0 ]]; then
	echo "FAILED: $failures"
	exit 1
fi
echo "OK: the driver ran closed and open loops through the workers on both layouts (smoke numbers, not results)"
