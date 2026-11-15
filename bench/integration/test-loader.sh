#!/usr/bin/env bash
# Integration test of the Phase 4 loader (Plan-phase4 2.5, P4.2): for each case, in a fresh schema, deploy the
# generated schema, distribution and placement, run `driver load` through the coordinator, and require that
# the rows on every node (count(*) of every shard placement, summed per node) equal the model's data-skew rows
# exactly. Scaled-down workloads (bench/integration/loader/) so it runs in about a minute; the full examples
# load the same way:
#
#   bench/integration/test-loader.sh
#   CASES_FILE=... bench/integration/test-loader.sh      (one "label|model|workload|driver options" per line)
set -uo pipefail

ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
BENCH="$ROOT/bench"
SCHEMA=loader_check
PORT=${CITUS_PORT:-5433}

shopt -s nullglob
CLI= DRIVER=
for jar in "$ROOT"/shardml.cli/target/*-cli.jar; do CLI="$jar"; done
for jar in "$ROOT"/shardml.bench/target/*-driver.jar; do DRIVER="$jar"; done
if [[ -z "$CLI" || -z "$DRIVER" ]]; then
	echo "test-loader: no CLI or driver jar under target/; run 'mvn install' first" >&2
	exit 2
fi
if ! up_log=$("$BENCH/up.sh" 2>&1); then
	echo "$up_log" >&2
	echo "test-loader: bench/up.sh failed" >&2
	exit 2
fi

psql_db() {
	docker compose -f "$BENCH/docker-compose.yml" exec -T -e PGOPTIONS="-c client_min_messages=warning -c search_path=$SCHEMA" \
		coordinator psql -U postgres -v ON_ERROR_STOP=1 -qAtX "$@"
}

work=$(mktemp -d)
trap 'rm -rf "$work"; psql_db -c "DROP SCHEMA IF EXISTS $SCHEMA CASCADE;" </dev/null >/dev/null 2>&1' EXIT
failures=0

CASES=(
	"banking-smoke C|examples/cost/banking-by-customer.shardml|bench/driver-smoke/banking-smoke.workload|"
	"banking-smoke A|examples/cost/banking-by-account.shardml|bench/driver-smoke/banking-smoke.workload|"
	"banking-small C s=1.2 k=7|examples/cost/banking-by-customer.shardml|bench/integration/loader/banking-small.workload|--set s=1.2 --rank-offset 7"
	"banking-small A s=1.2 k=7|examples/cost/banking-by-account.shardml|bench/integration/loader/banking-small.workload|--set s=1.2 --rank-offset 7"
	"tpcc-small W|examples/cost/tpcc-by-warehouse.shardml|bench/integration/loader/tpcc-small.workload|"
	"tpcc-small S|examples/cost/tpcc-stock-by-item.shardml|bench/integration/loader/tpcc-small.workload|"
)
if [[ -n "${CASES_FILE:-}" ]]; then
	mapfile -t CASES <"$CASES_FILE"
fi

for case in "${CASES[@]}"; do
	IFS='|' read -r label model workload options <<<"$case"
	echo "== $label"
	out="$work/case"
	rm -rf "$out" && mkdir -p "$out"
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
	start=$(date +%s)
	# shellcheck disable=SC2086
	if java -jar "$DRIVER" load --targets "127.0.0.1:$PORT" --schema "$SCHEMA" --jobs "${JOBS:-4}" $options "$ROOT/$workload" "$ROOT/$model" \
		>"$out/load.log" 2>"$out/load.err"; then
		echo "  ok    $(grep -c ' ok$' "$out/load.log") node checks equal the model ($(( $(date +%s) - start )) s): $(grep '^load: [0-9]' "$out/load.log")"
	else
		echo "  FAIL  driver load"
		grep -v "SHML_022" "$out/load.err" | tail -5
		grep "DIFFERS\|FAILED" "$out/load.log" | head -10
		failures=$((failures + 1))
	fi
	# foreign keys, if the layout has any, after the rows
	if [[ -f "$out/gen/$db-foreign-keys.sql" ]] && ! psql_db <"$out/gen/$db-foreign-keys.sql" >"$out/fk.log" 2>&1; then
		echo "  FAIL  $db-foreign-keys.sql after loading"; tail -3 "$out/fk.log"; failures=$((failures + 1))
	fi
done

echo
if [[ $failures -gt 0 ]]; then
	echo "FAILED: $failures"
	exit 1
fi
echo "OK: rows per node equal the model's data-skew rows for every case"
