#!/usr/bin/env bash
# Integration test: apply generated placement scripts to the bench cluster and
# check the live shard map against `shardml placement`.
#
#   bench/integration/test-placement.sh
#
# Needs the CLI jar (mvn install) and Docker; starts the cluster with
# bench/up.sh if it is not running. Deploys examples/banking.shardml to a fresh
# schema, then, in order, each step starting from where the last one left off:
#   1. refused nodes  fixtures declaring the coordinator or an unregistered host
#                     as a node fail in the preflight, with no shard moved
#   2. apply          the example's placement script; the live map of every
#                     table equals `shardml placement`
#   3. re-apply       the same script again moves nothing: the (shardid,
#                     groupid, placementid) snapshot is unchanged
#   4. convergence    placement/banking-pinned.shardml pins the group to two of
#                     the three nodes; the live map converges to it, moving
#                     exactly the buckets whose node changes
#   5. interrupted    a copy of the example's script in which one needed move
#                     fails stops there, the earlier moves stay done, and the
#                     real script then finishes the job
#
# Every psql run is bounded by PLACEMENT_TIMEOUT seconds (default 900): a move
# that stalls would otherwise hang the test with no output.
set -uo pipefail

ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
BENCH="$ROOT/bench"
FIXTURES="$BENCH/integration/placement"
SCHEMA=banking
TIMEOUT="${PLACEMENT_TIMEOUT:-900}"

# the newest CLI jar: target/ can hold the jars of several versions, and the
# first match in name order need not be the one just built
shopt -s nullglob
JAR=
for jar in "$ROOT"/shardml.cli/target/*-cli.jar; do
	if [[ -z "$JAR" || "$jar" -nt "$JAR" ]]; then
		JAR="$jar"
	fi
done
if [[ -z "$JAR" ]]; then
	echo "test-placement: no CLI jar in shardml.cli/target; run 'mvn install' first" >&2
	exit 2
fi
echo "test-placement: using $JAR"

if ! up_log=$("$BENCH/up.sh" 2>&1); then
	echo "$up_log" >&2
	echo "test-placement: bench/up.sh failed" >&2
	exit 2
fi

WORK=$(mktemp -d)
trap 'rm -rf "$WORK"' EXIT

psql_coord() {
	docker compose -f "$BENCH/docker-compose.yml" exec -T coordinator \
		psql -U postgres -v ON_ERROR_STOP=1 -qAtX "$@"
}

# timeout(1) is not on macOS; perl's alarm does the same there
with_timeout() {
	if command -v timeout >/dev/null; then
		timeout "$@"
	else
		perl -e 'alarm shift; exec @ARGV or die "exec: $!\n"' "$@"
	fi
}

fail() {
	echo "  FAIL  $1"
	[[ $# -gt 1 ]] && sed 's/^/        /' <<<"$2"
	echo
	echo "FAILED"
	exit 1
}

ok() {
	echo "  ok    $1"
}

# generate <model> <dir>: runs shardml generate into dir
generate() {
	mkdir -p "$2"
	java -jar "$JAR" generate "$1" -o "$2" >/dev/null 2>"$2/cli.err" \
		|| fail "shardml generate $(basename "$1")" "$(cat "$2/cli.err")"
}

# expected <model>: the model's map as `shardml placement` prints it
expected() {
	java -jar "$JAR" placement "$1" 2>/dev/null || fail "shardml placement $(basename "$1")"
}

# apply <file>: runs a placement script with psql in the test schema, bounded by
# the timeout; prints psql's output and returns its exit status
apply() {
	{ echo "SET search_path TO $SCHEMA;"; cat "$1"; } \
		| with_timeout "$TIMEOUT" docker compose -f "$BENCH/docker-compose.yml" exec -T coordinator \
			psql -U postgres -v ON_ERROR_STOP=1 -qAtX 2>&1 >/dev/null
	local status=$?
	if [[ $status -eq 124 || $status -eq 142 ]]; then
		fail "$(basename "$1") did not finish within ${TIMEOUT}s"
	fi
	return $status
}

# live_map <table...>: table, bucket and host:port of every shard, buckets
# ordered by shardminvalue as a number (it is a text column)
live_map() {
	local table
	for table in "$@"; do
		psql_coord -F $'\t' -c "
			SELECT '$table', row_number() OVER (ORDER BY s.shardminvalue::bigint) - 1, n.nodename || ':' || n.nodeport
			FROM pg_dist_shard s
			JOIN pg_dist_placement p ON p.shardid = s.shardid
			JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
			WHERE s.logicalrelid = '$SCHEMA.$table'::regclass
			ORDER BY s.shardminvalue::bigint;"
	done
}

# every placement of the test schema; a move gives a shard a new placementid
snapshot() {
	psql_coord -c "
		SELECT p.shardid, p.groupid, p.placementid
		FROM pg_dist_placement p
		JOIN pg_dist_shard s ON s.shardid = p.shardid
		JOIN pg_class c ON c.oid = s.logicalrelid
		JOIN pg_namespace ns ON ns.oid = c.relnamespace
		WHERE ns.nspname = '$SCHEMA'
		ORDER BY 1, 2, 3;"
}

# tables of a map, in its order
tables_of() {
	cut -f1 "$1" | awk '!seen[$0]++'
}

# check_map <label> <expected map file>: the live map must equal it
check_map() {
	local live
	live=$(live_map $(tables_of "$2"))
	if [[ "$live" != "$(cat "$2")" ]]; then
		fail "$1" "$(diff <(echo "$live") "$2" | head -20)"
	fi
	ok "$1 ($(wc -l <"$2" | tr -d ' ') shards)"
}

# moved <before> <after>: how many placements a run replaced
moved() {
	comm -13 <(sort "$1") <(sort "$2") | wc -l | tr -d ' '
}

# differing <map> <map>: how many buckets of the root (the first table) are on
# different nodes in the two maps
differing() {
	local root
	root=$(head -1 "$1" | cut -f1)
	paste "$1" "$2" | awk -F'\t' -v root="$root" '$1 == root && $3 != $6' | wc -l | tr -d ' '
}

echo "== deploy examples/banking.shardml"
generate "$ROOT/examples/banking.shardml" "$WORK/banking"
[[ -f "$WORK/banking/banking-placement.sql" ]] || fail "no banking-placement.sql: the example must declare nodes"
psql_coord -c "DROP SCHEMA IF EXISTS $SCHEMA CASCADE;" -c "CREATE SCHEMA $SCHEMA;" >/dev/null 2>&1 \
	|| fail "reset schema $SCHEMA"
{ echo "SET search_path TO $SCHEMA;"; cat "$WORK/banking/banking-schema.sql" "$WORK/banking/banking-distribution.sql"; } \
	| psql_coord >/dev/null || fail "deploy banking schema and distribution"
ok "schema and distribution"
expected "$ROOT/examples/banking.shardml" >"$WORK/banking.map"
tables=$(tables_of "$WORK/banking.map" | tr '\n' ' ')

echo "== 1. refused nodes"
for fixture in banking-coordinator-node banking-unknown-node; do
	generate "$FIXTURES/$fixture.shardml" "$WORK/$fixture"
	snapshot >"$WORK/before"
	if out=$(apply "$WORK/$fixture/banking-placement.sql"); then
		fail "$fixture: placement succeeded, expected the preflight to refuse it"
	fi
	grep -q "placement: not an active primary worker in pg_dist_node" <<<"$out" \
		|| fail "$fixture: failed, but not in the node preflight" "$out"
	snapshot >"$WORK/after"
	[[ $(moved "$WORK/before" "$WORK/after") -eq 0 ]] || fail "$fixture: shards moved before the preflight failed"
	ok "$fixture: $(grep -o 'not an active primary worker.*' <<<"$out"), 0 moves"
done

echo "== 2. apply"
snapshot >"$WORK/before"
started=$SECONDS
out=$(apply "$WORK/banking/banking-placement.sql") || fail "banking-placement.sql" "$out"
snapshot >"$WORK/after"
ok "banking-placement.sql: $(moved "$WORK/before" "$WORK/after") placements moved in $((SECONDS - started))s"
check_map "live map of $tables== shardml placement" "$WORK/banking.map"

echo "== 3. re-apply"
cp "$WORK/after" "$WORK/before"
out=$(apply "$WORK/banking/banking-placement.sql") || fail "banking-placement.sql, second run" "$out"
snapshot >"$WORK/after"
cmp -s "$WORK/before" "$WORK/after" \
	|| fail "second run changed placements" "$(diff "$WORK/before" "$WORK/after" | head -20)"
ok "second run: snapshot of $(wc -l <"$WORK/after" | tr -d ' ') placements unchanged, 0 moves"

echo "== 4. convergence"
generate "$FIXTURES/banking-pinned.shardml" "$WORK/pinned"
expected "$FIXTURES/banking-pinned.shardml" >"$WORK/pinned.map"
groups=$(differing "$WORK/banking.map" "$WORK/pinned.map")
[[ $groups -gt 0 ]] || fail "the pinned fixture places every bucket where the example does; nothing to converge"
cp "$WORK/after" "$WORK/before"
started=$SECONDS
out=$(apply "$WORK/pinned/banking-placement.sql") || fail "pinned banking-placement.sql" "$out"
snapshot >"$WORK/after"
check_map "live map == shardml placement of banking-pinned" "$WORK/pinned.map"
table_count=$(tables_of "$WORK/pinned.map" | wc -l | tr -d ' ')
n=$(moved "$WORK/before" "$WORK/after")
[[ $n -eq $((groups * table_count)) ]] \
	|| fail "moved $n placements; expected $groups group moves x $table_count tables"
ok "$groups group moves ($n placements) in $((SECONDS - started))s, only the buckets whose node changed"

echo "== 5. interrupted"
# break the move of the first bucket from the middle on that has to move: a bucket
# already on its node is skipped, so breaking its move would break nothing
BROKEN_BUCKET=$(paste "$WORK/pinned.map" "$WORK/banking.map" | awk -F'\t' -v root="$(head -1 "$WORK/banking.map" | cut -f1)" \
	'$1 == root && $2 >= 16 && $3 != $6 { print $2; exit }')
[[ -n "$BROKEN_BUCKET" ]] || fail "no bucket from 16 on has to move"
# its move targets a host Citus does not know, so it fails
awk -v bucket="$BROKEN_BUCKET" '
	index($0, "-- bucket " bucket " ->") == 1 { broken = 1 }
	broken && /^SELECT citus_move_shard_placement/ { sub(/, '\''[^'\'']*'\'', [0-9]+, shard_transfer_mode/, ", '\''unregistered-host'\'', 5432, shard_transfer_mode"); broken = 0 }
	{ print }
' "$WORK/banking/banking-placement.sql" >"$WORK/broken.sql"
[[ $(diff "$WORK/banking/banking-placement.sql" "$WORK/broken.sql" | grep -c '^>') -eq 1 ]] \
	|| fail "could not break the move of bucket $BROKEN_BUCKET"
# where the shards must be: the example's node below the broken bucket, the pinned
# fixture's (where they are now) from it on
paste "$WORK/banking.map" "$WORK/pinned.map" \
	| awk -F'\t' -v OFS='\t' -v bucket="$BROKEN_BUCKET" '{ print $1, $2, ($2 < bucket ? $3 : $6) }' >"$WORK/interrupted.map"
earlier=$(differing "$WORK/pinned.map" "$WORK/interrupted.map")
[[ $earlier -gt 0 ]] || fail "no bucket below $BROKEN_BUCKET needs a move, so the interruption proves nothing"
if out=$(apply "$WORK/broken.sql"); then
	fail "the broken script succeeded"
fi
grep -q "non-existing node" <<<"$out" || fail "the broken script failed for another reason" "$out"
ok "broken script stopped at bucket $BROKEN_BUCKET: $(head -1 <<<"$out")"
check_map "the $earlier moves before bucket $BROKEN_BUCKET stayed done, none after it ran" "$WORK/interrupted.map"
snapshot >"$WORK/before"
out=$(apply "$WORK/banking/banking-placement.sql") || fail "banking-placement.sql after the interruption" "$out"
snapshot >"$WORK/after"
check_map "re-run converges: live map == shardml placement" "$WORK/banking.map"
rest=$(differing "$WORK/interrupted.map" "$WORK/banking.map")
n=$(moved "$WORK/before" "$WORK/after")
[[ $n -eq $((rest * table_count)) ]] || fail "re-run moved $n placements; expected $rest group moves x $table_count tables"
ok "re-run moved only the $rest buckets left ($n placements)"

echo
echo "OK"
