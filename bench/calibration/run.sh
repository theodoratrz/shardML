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
# Per (model, workload, repetition): a warm-up driver run (discarded), then the measured run with no warm-up,
# around which the driver itself snapshots the cluster's counters into manifest.json (P4.3, Counters.java:
# the one implementation; with no warm-up its before snapshot precedes every transaction); counts.py compares the counts per executed transaction with the model (`driver touches`):
# read and write touches per node (pg_stat_statements executions per shard relation, the plan's amendment 2),
# single- and multi-shard statements on the entry nodes (citus_stat_counters), 2PC transactions and, per node,
# those it took part in (pg_dist_transaction), within one transaction per client plus four standard deviations;
# and records every container's CPU (cgroup usage). Under amd64 emulation CPU is not a result and is never
# reported (amendment 1); the fit (P4.0c) runs on the VM with the same script and the plan's sizes.
#
# Needs pg_stat_statements preloaded (bench/docker-compose.yml). For the runs it sets, on every node,
# citus.enable_stat_counters = on and citus.recover_2pc_interval = -1 (2PC records stay countable), and with
# LOCAL_EXEC=off citus.enable_local_execution = off, with ALTER SYSTEM; all are reset on exit, an abort included.
# LOCAL_EXEC=off is for diagnostic-only runs (plan amendment 3, 1): entering through the workers (ENTRY=workers),
# per-node touches and 2PC participation are checked only with local execution off; with it on (the default,
# and always in performance runs) they are listed, and the totals checked.
#
#   bench/calibration/run.sh                        the plan's sizes: 1M keys, 120 s warm-up, 300 s measured
#   KEYS=30000 WARMUP=5 DURATION=20 CLIENTS=4 bench/calibration/run.sh     a local counts-only pass
#
# Environment: KEYS (kv rows; ref gets KEYS/10), WARMUP, DURATION, CLIENTS, MODE (closed|open), RATE (open),
# REPS, RTT (a label for the record; netem is P4.5), ENTRY (coordinator|workers) and TARGETS (host:port,..;
# default the coordinator, or for ENTRY=workers the published workers 127.0.0.1:5434..5436: mode (b), where
# CLIENTS should be a multiple of 3 so each worker enters the same share), ONLY (a regex over "model/workload"), TRANSFER_MODE (default block_writes),
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
if [[ $ENTRY == workers ]]; then
	TARGETS=${TARGETS:-127.0.0.1:${CITUS_WORKER1_PORT:-5434},127.0.0.1:${CITUS_WORKER2_PORT:-5435},127.0.0.1:${CITUS_WORKER3_PORT:-5436}}
else
	TARGETS=${TARGETS:-127.0.0.1:$PORT}
fi
ONLY=${ONLY:-}
LOCAL_EXEC=${LOCAL_EXEC:-on}
# WARMUP_IN=run (default): the warm-up is a separate driver run; WARMUP_IN=driver: the measured run's own
# warm-up phase (its clients stop before the before snapshot; Plan-phase4 amendment 4)
WARMUP_IN=${WARMUP_IN:-run}
if [[ $WARMUP_IN != run && $WARMUP_IN != driver ]]; then
	echo "calibration: WARMUP_IN must be run or driver" >&2
	exit 2
fi
if [[ $LOCAL_EXEC != on && $LOCAL_EXEC != off ]]; then
	echo "calibration: LOCAL_EXEC must be on or off" >&2
	exit 2
fi
TRANSFER_MODE=${TRANSFER_MODE:-block_writes}
JOBS=${JOBS:-4}
OUT=${OUT:-$ROOT/bench-runs/calibration}

# model|workloads
BENCHMARKS=(
	"point|point-read point-write"
	"scan-4|scan"
	"scan-8|scan"
	"scan-16|scan"
	"scan-32|scan"
	"scan-64|scan"
	"twopc|twopc-1x2 twopc-2 twopc-1x3 twopc-3"
	"reference|ref-read ref-write ref-join"
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
	set_gucs "ALTER SYSTEM RESET citus.enable_stat_counters" "ALTER SYSTEM RESET citus.recover_2pc_interval" \
		"ALTER SYSTEM RESET citus.enable_local_execution" 2>/dev/null
	psql_db -c "DROP SCHEMA IF EXISTS $SCHEMA CASCADE;" </dev/null >/dev/null 2>&1
	rm -rf "$work"
}
trap cleanup EXIT
# an interrupt or kill exits through the EXIT trap too, so the settings are always reset
trap 'exit 130' INT
trap 'exit 143' TERM
if ! compose exec -T coordinator psql -U postgres -v ON_ERROR_STOP=1 -qAtX -c "CREATE EXTENSION IF NOT EXISTS pg_stat_statements;" >/dev/null; then
	echo "calibration: pg_stat_statements is not available (bench/docker-compose.yml preloads it; recreate the cluster)" >&2
	exit 2
fi
gucs=("ALTER SYSTEM SET citus.enable_stat_counters = on" "ALTER SYSTEM SET citus.recover_2pc_interval = -1")
[[ $LOCAL_EXEC == off ]] && gucs+=("ALTER SYSTEM SET citus.enable_local_execution = off")
if ! set_gucs "${gucs[@]}"; then
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

# mode (b): Citus local execution hides the entry worker's own executions from the per-node counters
local_execution=$(compose exec -T worker1 psql -U postgres -qAtX -c "SHOW citus.enable_local_execution;" | tr -d '[:space:]')
run_options=(--mode "$MODE" --clients "$CLIENTS" --targets "$TARGETS" --entry "$ENTRY" --schema "$SCHEMA")
[[ -n "$RATE" ]] && run_options+=(--rate "$RATE")
failures=0
for entry in "${BENCHMARKS[@]}"; do
	IFS='|' read -r model workloads <<<"$entry"
	selected=()
	for w in $workloads; do
		[[ -z "$ONLY" || "$model/$w" =~ $ONLY ]] && selected+=("$w")
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
	# load with the first workload: every workload of a model declares the same tables and rows
	start=$(date +%s)
	if ! java -jar "$DRIVER" load --targets "127.0.0.1:$PORT" --schema "$SCHEMA" --jobs "$JOBS" "$work/in/${selected[0]}.workload" "$m" \
		>"$session/$model-load.log" 2>"$work/load.err"; then
		echo "  FAIL  driver load"; grep -v SHML_0 "$work/load.err" | tail -3; failures=$((failures + 1)); continue
	fi
	echo "  load   $(( $(date +%s) - start )) s: $(grep '^load: [0-9]' "$session/$model-load.log")"
	# pg_stat_statements from a clean slate: the statements of a shard move can leave an entry whose text
	# holds a NUL byte, and then reading the view fails ("invalid byte sequence for encoding UTF8: 0x00");
	# nothing moves during the runs, and the touches are deltas, so a reset here loses nothing
	for svc in "${SERVICES[@]}"; do
		compose exec -T "$svc" psql -U postgres -v ON_ERROR_STOP=1 -qAtX -c "SELECT pg_stat_statements_reset();" >/dev/null ||
			{ echo "  FAIL  pg_stat_statements_reset on $svc"; failures=$((failures + 1)); }
	done
	for w in "${selected[@]}"; do
		wl="$work/in/$w.workload"
		java -jar "$DRIVER" touches --json "$wl" "$m" >"$work/touches.json" 2>/dev/null
		for rep in $(seq 1 "$REPS"); do
			label="$model/$w rep $rep"
			measured_warmup=0
			[[ $WARMUP_IN == driver ]] && measured_warmup=$WARMUP
			if [[ $WARMUP_IN == run ]] && (( WARMUP > 0 )); then
				java -jar "$DRIVER" run "${run_options[@]}" --warmup 0 --duration "$WARMUP" --rep "$rep" --out "$work/warmup" "$wl" "$m" \
					>"$work/warmup.log" 2>&1 || { echo "  FAIL  $label: warm-up run"; tail -3 "$work/warmup.log"; failures=$((failures + 1)); continue; }
			fi
			if ! java -jar "$DRIVER" run "${run_options[@]}" --warmup "$measured_warmup" --duration "$DURATION" --rep "$rep" --out "$session/runs" "$wl" "$m" \
				>"$work/run.log" 2>"$work/run.err"; then
				echo "  FAIL  $label: driver run"; grep -v SHML_0 "$work/run.err" | tail -3; failures=$((failures + 1)); continue
			fi
			# the driver snapshots the counters around the measured window into manifest.json (Counters.java)
			dir=$(sed -n 's/.*-> //p' "$work/run.log" | head -1)
			cp "$work/touches.json" "$dir/model-touches.json"
			if ! python3 "$CAL/counts.py" --manifest "$dir/manifest.json" --result "$dir/result.json" \
				--touches "$work/touches.json" --model "$m" --entry "$ENTRY" --clients "$CLIENTS" --label "$label" \
				--local-execution "$local_execution" \
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
