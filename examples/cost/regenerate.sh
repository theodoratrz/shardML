#!/usr/bin/env bash
# Regenerates examples/cost/expected/ with the CLI jar: every output of the two cost examples (banking,
# TPC-C), one `shardml cost` run per file; the sweeps run inside the CLI (--sweep), never as a shell loop
# that reloads the workload per value. CI's build job runs this before its drift check, so a change to any
# output shows in `git status`. Needs the CLI jar (mvn install).
#
#   examples/cost/regenerate.sh
set -euo pipefail

HERE="$(cd "$(dirname "$0")" && pwd)"
ROOT="$(cd "$HERE/../.." && pwd)"
OUT="$HERE/expected"

shopt -s nullglob
JAR=
for jar in "$ROOT"/shardml.cli/target/*-cli.jar; do
	if [[ -z "$JAR" || "$jar" -nt "$JAR" ]]; then
		JAR="$jar"
	fi
done
if [[ -z "$JAR" ]]; then
	echo "regenerate: no CLI jar in shardml.cli/target; run 'mvn install' first" >&2
	exit 2
fi
mkdir -p "$OUT"
ERR=$(mktemp)
trap 'rm -f "$ERR"' EXIT

# one output file; the validation warnings (SHML_022: cost layouts declare no route rules) go to stderr,
# kept outside expected/ and shown only if the command fails
cost() {
	local file="$1"
	shift
	java -jar "$JAR" cost "$@" > "$OUT/$file" 2> "$ERR" || {
		cat "$ERR" >&2
		echo "regenerate: shardml cost $* failed" >&2
		exit 1
	}
}

B="$HERE/banking.workload"
C="$HERE/banking-by-customer.shardml"
A="$HERE/banking-by-account.shardml"
S_VALUES=0,0.5,0.8,1.0,1.2,1.25,1.3,1.4
OFFSETS=0,1,2,3,5,8,13,21

cost banking-by-customer.txt "$B" "$C"
cost banking-by-customer.json --json "$B" "$C"
cost banking-by-account.txt "$B" "$A"
cost banking-by-account.json --json "$B" "$A"
cost banking-comparison.txt "$B" "$C" "$A"
cost banking-comparison.csv --csv "$B" "$C" "$A"
cost banking-sweep-s.txt --sweep "s=$S_VALUES" "$B" "$C" "$A"
cost banking-sweep-s.csv --csv --sweep "s=$S_VALUES" "$B" "$C" "$A"
cost banking-sweep-offset.txt --sweep "rank-offset=$OFFSETS" "$B" "$C" "$A"
cost banking-sweep-offset.csv --csv --sweep "rank-offset=$OFFSETS" "$B" "$C" "$A"
# the crossover per rank offset: every s at every offset
cost banking-sweep-s-offset.csv --csv --sweep "s=$S_VALUES" --sweep "rank-offset=$OFFSETS" "$B" "$C" "$A"

T="$HERE/tpcc.workload"
W="$HERE/tpcc-by-warehouse.shardml"
S="$HERE/tpcc-stock-by-item.shardml"
REMOTE=0,0.01,0.1,0.3,0.6,1.0

cost tpcc-by-warehouse.txt "$T" "$W"
cost tpcc-by-warehouse.json --json "$T" "$W"
cost tpcc-stock-by-item.txt "$T" "$S"
cost tpcc-stock-by-item.json --json "$T" "$S"
cost tpcc-comparison.txt "$T" "$W" "$S"
cost tpcc-comparison.csv --csv "$T" "$W" "$S"
cost tpcc-sweep-remote.txt --sweep "remote=$REMOTE" "$T" "$W" "$S"
cost tpcc-sweep-remote.csv --csv --sweep "remote=$REMOTE" "$T" "$W" "$S"

echo "regenerate: $(ls "$OUT" | wc -l | tr -d ' ') files in $OUT"
