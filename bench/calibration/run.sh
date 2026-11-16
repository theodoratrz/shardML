#!/usr/bin/env bash
# The calibration harness (Plan-phase4 1a, P4.0b): the five microbenchmarks, each a ShardML model and
# workload in this directory, deployed, loaded and run with the validation driver, with the counters the
# fitting needs snapshotted around every measured run.
#
#   benchmark                  model                     workloads
#   1 point read / write       point.shardml             point-read, point-write
#   2 scan vs shard count      scan-{4,8,16,32,64}       scan (k < 0 on every shard)
#   3 distributed write txn    twopc.shardml (pinned)    twopc-1x2, twopc-2 (2PC), twopc-1x3, twopc-3 (2PC)
#   4 reference read / write   reference.shardml         ref-read, ref-write, ref-join
#   5 entry-node CPU           from 1 to 4               (the entry node's container CPU and statement counts)
#
# Per (model, workload, repetition): a warm-up driver run (discarded), a counter snapshot, the measured
# run, a snapshot; counts.py compares the counts per executed transaction with the model (`driver touches`,
# `shardml cost`): shard relations scanned and rows updated per node, single- and multi-shard statements on
# the entry node (citus_stat_counters), 2PC transactions (pg_dist_transaction); and records CPU-seconds per
# transaction of every container (cgroup usage). On the local emulated cluster only the counts mean anything
# (Plan-phase4 7.2); the fit (P4.0c) runs on the VM with the same script and the plan's sizes.
#
# For the runs it sets, on every node, citus.enable_stat_counters = on and citus.recover_2pc_interval = -1
# (2PC records stay countable), with ALTER SYSTEM; both are reset on exit.
#
#   bench/calibration/run.sh                        the plan's sizes: 1M keys, 120 s warm-up, 300 s measured
#   KEYS=30000 WARMUP=5 DURATION=20 CLIENTS=4 bench/calibration/run.sh     a local counts-only pass
#
# Environment: KEYS (kv rows; ref gets KEYS/10), WARMUP, DURATION, CLIENTS, MODE (closed|open), RATE (open),
# REPS, RTT (a label for the record; netem is P4.5), ENTRY (coordinator|workers) and TARGETS (host:port,..;
# default the coordinator), ONLY (a regex over "model/workload"), TRANSFER_MODE (default block_writes),
# JOBS (loader connections), OUT (default bench-runs/calibration).
set -uo pipefail

ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
BENCH="$ROOT/bench"
CAL="$BENCH/calibration"
SCHEMA=calib
PORT=${CITUS_PORT:-5433}
KEYS=${KEYS:-1000000}
WARMUP=${WARMUP:-120}
DURATION=${DURATION:-300}
CLIENTS=${CLIENTS:-8}
MODE=${MODE:-closed}
RATE=${RATE:-}
REPS=${REPS:-1}
RTT=${RTT:-none}
ENTRY=${ENTRY:-coordinator}
TARGETS=${TARGETS:-127.0.0.1:$PORT}
ONLY=${ONLY:-}
TRANSFER_MODE=${TRANSFER_MODE:-block_writes}
JOBS=${JOBS:-4}
OUT=${OUT:-$ROOT/bench-runs/calibration}

# model|workload[:scans per touch] ...: PostgreSQL counts the planner's own index probes in idx_scan
# (selfuncs.c get_actual_variable_range: a range bound outside the histogram, `k < 0`, and a merge-join
# selectivity estimate each read the index while planning), and Citus plans every shard query anew on its
# worker; so for those workloads every touch shows as 2 scans: scan (1 probe + 1 execution per shard) and
# ref-join (kv 1, ref 1 + 2 probes: 4 per 2-relation touch)
BENCHMARKS=(
	"point|point-read point-write"
	"scan-4|scan:2"
	"scan-8|scan:2"
	"scan-16|scan:2"
	"scan-32|scan:2"
	"scan-64|scan:2"
	"twopc|twopc-1x2 twopc-2 twopc-1x3 twopc-3"
	"reference|ref-read ref-write ref-join:2"
)

shopt -s nullglob
CLI= DRIVER=
for jar in "$ROOT"/shardml.cli/target/*-cli.jar; do CLI="$jar"; done
for jar in "$ROOT"/shardml.bench/target/*-driver.jar; do DRIVER="$jar"; done
if [[ -z "$CLI" || -z "$DRIVER" ]]; then
	echo "calibration: no CLI or driver jar under target/; run 'mvn install' first" >&2
	exit 2
fi
if (( KEYS < 10 )); then
	echo "calibration: KEYS must be at least 10" >&2
	exit 2
fi
if ! up_log=$("$BENCH/up.sh" 2>&1); then
	echo "$up_log" >&2
	echo "calibration: bench/up.sh failed" >&2
	exit 2
fi

compose() { docker compose -f "$BENCH/docker-compose.yml" "$@"; }
psql_db() {
	compose exec -T -e PGOPTIONS="-c client_min_messages=warning -c search_path=$SCHEMA" \
		coordinator psql -U postgres -v ON_ERROR_STOP=1 -qAtX "$@"
}
SERVICES=(coordinator worker1 worker2 worker3)

# GUCs for the run, on every node; reset on exit
set_gucs() { # one ALTER SYSTEM per argument (each must run outside a transaction block)
	local svc stmt args
	for svc in "${SERVICES[@]}"; do
		args=()
		for stmt in "$@"; do args+=(-c "$stmt"); done
		compose exec -T "$svc" psql -U postgres -v ON_ERROR_STOP=1 -qAtX "${args[@]}" -c "SELECT pg_reload_conf();" >/dev/null || return 1
	done
}
work=$(mktemp -d)
cleanup() {
	set_gucs "ALTER SYSTEM RESET citus.enable_stat_counters" "ALTER SYSTEM RESET citus.recover_2pc_interval" 2>/dev/null
	psql_db -c "DROP SCHEMA IF EXISTS $SCHEMA CASCADE;" </dev/null >/dev/null 2>&1
	rm -rf "$work"
}
trap cleanup EXIT
if ! set_gucs "ALTER SYSTEM SET citus.enable_stat_counters = on" "ALTER SYSTEM SET citus.recover_2pc_interval = -1"; then
	echo "calibration: cannot set citus.enable_stat_counters / citus.recover_2pc_interval" >&2
	exit 2
fi

# the models and workloads at KEYS keys (ref: KEYS / 10)
mkdir -p "$work/in"
cp "$CAL"/*.msql "$CAL"/*.shardml "$work/in/"
for f in "$CAL"/*.workload; do
	sed -E "s/size: 1000000 \}/size: $KEYS }/; s/size: 100000 \}/size: $((KEYS / 10)) }/" "$f" >"$work/in/$(basename "$f")"
done

session="$OUT/$(date -u +%Y%m%dT%H%M%SZ)-keys$KEYS-rtt$RTT"
mkdir -p "$session"
{
	echo "keys=$KEYS warmup=$WARMUP duration=$DURATION clients=$CLIENTS mode=$MODE rate=$RATE reps=$REPS rtt=$RTT entry=$ENTRY targets=$TARGETS"
	echo "commit=$(git -C "$ROOT" rev-parse HEAD 2>/dev/null)"
	compose exec -T coordinator psql -U postgres -qAtX -c "SELECT citus_version();"
} >"$session/session.txt"

# one snapshot: per table and node (every placement, summed), scans and updates; the statement counters
# (summed over the nodes: only an entry node counts); distinct 2PC transactions on every node; every
# container's CPU
snapshot() { # tables-csv file
	local tables=$1 file=$2
	psql_db >"$file" <<SQL
SELECT 'stat|' || t || '|' || r.nodename || ':' || r.nodeport || '|' || sum(split_part(r.result, ' ', 1)::bigint) || '|'
       || sum(split_part(r.result, ' ', 2)::bigint)
FROM unnest(string_to_array('$tables', ',')) AS t,
     LATERAL run_command_on_placements(t::regclass,
       'SELECT coalesce(seq_scan, 0) + coalesce(idx_scan, 0) || '' '' || n_tup_upd FROM pg_stat_user_tables WHERE relid = ''%s''::regclass') r
GROUP BY t, r.nodename, r.nodeport ORDER BY 1;
SELECT 'counters|' || coalesce(sum(split_part(result, ' ', 1)::bigint), 0) || '|' || coalesce(sum(split_part(result, ' ', 2)::bigint), 0)
FROM run_command_on_all_nodes(\$\$SELECT query_execution_single_shard || ' ' || query_execution_multi_shard
                                  FROM citus_stat_counters WHERE name = current_database()\$\$);
SELECT 'twopc|' || coalesce(sum(result::bigint), 0)
FROM run_command_on_all_nodes(\$\$SELECT count(DISTINCT regexp_replace(gid, '_[0-9]+\$', '')) FROM pg_dist_transaction\$\$);
SQL
	for svc in "${SERVICES[@]}"; do
		echo "cpu|$svc|$(compose exec -T "$svc" sh -c "sed -n 's/^usage_usec //p' /sys/fs/cgroup/cpu.stat")"
	done >>"$file"
}

run_options=(--mode "$MODE" --clients "$CLIENTS" --targets "$TARGETS" --entry "$ENTRY" --schema "$SCHEMA")
[[ -n "$RATE" ]] && run_options+=(--rate "$RATE")
failures=0
for entry in "${BENCHMARKS[@]}"; do
	IFS='|' read -r model workloads <<<"$entry"
	selected=()
	for w in $workloads; do
		[[ -z "$ONLY" || "$model/${w%%:*}" =~ $ONLY ]] && selected+=("$w")
	done
	[[ ${#selected[@]} -gt 0 ]] || continue
	echo "== $model"
	m="$work/in/$model.shardml"
	gen="$work/gen-$model"
	if ! java -jar "$CLI" generate "$m" -o "$gen" >"$work/gen.log" 2>&1; then
		echo "  FAIL  shardml generate $model"; failures=$((failures + 1)); continue
	fi
	db=$(basename "$gen"/*-schema.sql -schema.sql)
	psql_db -c "DROP SCHEMA IF EXISTS $SCHEMA CASCADE;" -c "CREATE SCHEMA $SCHEMA;" </dev/null >/dev/null
	start=$(date +%s)
	deployed=1
	for step in schema distribution placement; do
		file="$gen/$db-$step.sql"
		[[ -f "$file" ]] || continue
		if [[ $step == placement ]]; then
			sed "s/shard_transfer_mode => 'force_logical'/shard_transfer_mode => '$TRANSFER_MODE'/" "$file" >"$work/placement-run.sql"
			file="$work/placement-run.sql"
		fi
		if ! psql_db <"$file" >"$work/$step.log" 2>&1; then
			echo "  FAIL  $db-$step.sql"; tail -5 "$work/$step.log"; failures=$((failures + 1)); deployed=0
		fi
	done
	[[ $deployed -eq 1 ]] || continue
	echo "  deploy $(( $(date +%s) - start )) s"
	# the tables this model distributes (for the snapshots)
	tables=$(psql_db -c "SELECT string_agg(quote_ident(n.nspname) || '.' || quote_ident(c.relname), ',' ORDER BY c.relname)
		FROM pg_dist_partition p JOIN pg_class c ON c.oid = p.logicalrelid JOIN pg_namespace n ON n.oid = c.relnamespace
		WHERE n.nspname = '$SCHEMA';" </dev/null)
	# load with the first workload: every workload of a model declares the same tables and rows
	start=$(date +%s)
	first=${selected[0]%%:*}
	if ! java -jar "$DRIVER" load --targets "127.0.0.1:$PORT" --schema "$SCHEMA" --jobs "$JOBS" "$work/in/$first.workload" "$m" \
		>"$session/$model-load.log" 2>"$work/load.err"; then
		echo "  FAIL  driver load"; grep -v SHML_0 "$work/load.err" | tail -3; failures=$((failures + 1)); continue
	fi
	echo "  load   $(( $(date +%s) - start )) s: $(grep '^load: [0-9]' "$session/$model-load.log")"
	for spec in "${selected[@]}"; do
		w=${spec%%:*}
		factor=1
		[[ $spec == *:* ]] && factor=${spec#*:}
		wl="$work/in/$w.workload"
		java -jar "$DRIVER" touches --json "$wl" "$m" >"$work/touches.json" 2>/dev/null
		java -jar "$CLI" cost --json "$wl" "$m" >"$work/cost.json" 2>/dev/null
		for rep in $(seq 1 "$REPS"); do
			label="$model/$w rep $rep"
			if (( WARMUP > 0 )); then
				java -jar "$DRIVER" run "${run_options[@]}" --warmup 0 --duration "$WARMUP" --rep "$rep" --out "$work/warmup" "$wl" "$m" \
					>"$work/warmup.log" 2>&1 || { echo "  FAIL  $label: warm-up run"; tail -3 "$work/warmup.log"; failures=$((failures + 1)); continue; }
			fi
			snapshot "$tables" "$work/before.txt"
			if ! java -jar "$DRIVER" run "${run_options[@]}" --warmup 0 --duration "$DURATION" --rep "$rep" --out "$session/runs" "$wl" "$m" \
				>"$work/run.log" 2>"$work/run.err"; then
				echo "  FAIL  $label: driver run"; grep -v SHML_0 "$work/run.err" | tail -3; failures=$((failures + 1)); continue
			fi
			sleep 2 # backends flush their table statistics at exit; give the last ones time
			snapshot "$tables" "$work/after.txt"
			dir=$(sed -n 's/.*-> //p' "$work/run.log" | head -1)
			cp "$work/before.txt" "$dir/counters-before.txt"
			cp "$work/after.txt" "$dir/counters-after.txt"
			cp "$work/touches.json" "$dir/model-touches.json"
			if ! python3 "$CAL/counts.py" --before "$work/before.txt" --after "$work/after.txt" --result "$dir/result.json" \
				--touches "$work/touches.json" --cost "$work/cost.json" --model "$m" --entry "$ENTRY" --label "$label" --scans-per-touch "$factor" \
				--json-out "$dir/counts.json"; then
				failures=$((failures + 1))
			fi
		done
	done
done

echo
echo "records: $session"
if [[ $failures -gt 0 ]]; then
	echo "FAILED: $failures"
	exit 1
fi
echo "OK: every microbenchmark ran and its counts per transaction agree with the model"
