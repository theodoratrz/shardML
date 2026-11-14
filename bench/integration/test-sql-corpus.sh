#!/usr/bin/env bash
# Integration test: does the generated schema lose anything? For each clean file of the SQL corpus
# (shardml.tests/resources/sql-corpus; Pagila is a negative fixture and is left out):
#
#   1. the ORIGINAL file is applied to a fresh database;
#   2. the CLI generates <db>-schema.sql and <db>-foreign-keys.sql from the same file (through a
#      model that imports it; the layout does not change the schema files), and they are applied to
#      a second fresh database;
#   3. the two catalogs are dumped, normalized, one fact per line, and compared: per table its
#      columns (position, format_type, NOT NULL, default expression, identity and generated
#      flags), constraints (name, type, pg_get_constraintdef), indexes (pg_get_indexdef),
#      reloptions, comments and privileges, and the schemas.
#
# Every difference must be listed in bench/integration/sql-corpus/allowlist.tsv with its reason;
# an unlisted difference fails, and so does a listed one that no longer occurs. Separate databases,
# not schemas, because the dumps hard-code their schema names (banking., public.). The databases are
# created from template0 on the bench coordinator: plain PostgreSQL 17, no Citus extension in them.
#
#   bench/integration/test-sql-corpus.sh [corpus-dir]
#
# Needs the CLI jar (mvn install) and Docker; starts the cluster with bench/up.sh if it is not
# running.
set -uo pipefail

ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
BENCH="$ROOT/bench"
CORPUS="${1:-$ROOT/shardml.tests/resources/sql-corpus}"
ALLOWLIST="$ROOT/bench/integration/sql-corpus/allowlist.tsv"
FILES=(tpcc pgbench banking-pg_dump chinook northwind)

shopt -s nullglob
JAR=
for jar in "$ROOT"/shardml.cli/target/*-cli.jar; do
	if [[ -z "$JAR" || "$jar" -nt "$JAR" ]]; then
		JAR="$jar"
	fi
done
if [[ -z "$JAR" ]]; then
	echo "test-sql-corpus: no CLI jar in shardml.cli/target; run 'mvn install' first" >&2
	exit 2
fi

if ! up_log=$("$BENCH/up.sh" 2>&1); then
	echo "$up_log" >&2
	echo "test-sql-corpus: bench/up.sh failed" >&2
	exit 2
fi

# psql on the coordinator, reading stdin (a file, or nothing)
psql_db() {
	local db="$1"
	shift
	docker compose -f "$BENCH/docker-compose.yml" exec -T -e PGOPTIONS='-c client_min_messages=warning' coordinator \
		psql -U postgres -d "$db" -v ON_ERROR_STOP=1 -qAtX "$@"
}

# one fact per line, sorted: what the comparison sees of a database
CATALOG=$(cat <<'SQL'
WITH t AS (
    SELECT c.oid, n.nspname || '.' || c.relname AS name, c.reloptions, c.relacl
    FROM pg_class c JOIN pg_namespace n ON n.oid = c.relnamespace
    WHERE c.relkind IN ('r', 'p') AND n.nspname NOT IN ('pg_catalog', 'information_schema') AND n.nspname NOT LIKE 'pg\_%'
)
SELECT line FROM (
    SELECT 'schema ' || nspname AS line FROM pg_namespace
    WHERE nspname NOT IN ('pg_catalog', 'information_schema', 'public') AND nspname NOT LIKE 'pg\_%'
    UNION ALL
    SELECT format('column %s.%s #%s %s%s%s%s%s', t.name, a.attname, a.attnum, format_type(a.atttypid, a.atttypmod),
                  CASE WHEN a.attnotnull THEN ' NOT NULL' ELSE '' END,
                  coalesce(' DEFAULT ' || pg_get_expr(d.adbin, d.adrelid), ''),
                  CASE WHEN a.attidentity <> '' THEN ' IDENTITY ' || a.attidentity::text ELSE '' END,
                  CASE WHEN a.attgenerated <> '' THEN ' GENERATED ' || a.attgenerated::text ELSE '' END)
    FROM t JOIN pg_attribute a ON a.attrelid = t.oid AND a.attnum > 0 AND NOT a.attisdropped
    LEFT JOIN pg_attrdef d ON d.adrelid = a.attrelid AND d.adnum = a.attnum
    UNION ALL
    SELECT format('constraint %s %s %s %s', t.name, k.conname, k.contype, pg_get_constraintdef(k.oid))
    FROM t JOIN pg_constraint k ON k.conrelid = t.oid
    UNION ALL
    SELECT format('index %s %s', t.name, pg_get_indexdef(i.indexrelid))
    FROM t JOIN pg_index i ON i.indrelid = t.oid
    UNION ALL
    SELECT format('reloptions %s %s', t.name, t.reloptions) FROM t WHERE t.reloptions IS NOT NULL
    UNION ALL
    SELECT format('privileges %s %s', t.name, t.relacl) FROM t WHERE t.relacl IS NOT NULL
    UNION ALL
    SELECT format('comment %s %s', t.name, obj_description(t.oid, 'pg_class')) FROM t
    WHERE obj_description(t.oid, 'pg_class') IS NOT NULL
    UNION ALL
    SELECT format('comment %s.%s %s', t.name, a.attname, col_description(t.oid, a.attnum))
    FROM t JOIN pg_attribute a ON a.attrelid = t.oid AND a.attnum > 0 AND NOT a.attisdropped
    WHERE col_description(t.oid, a.attnum) IS NOT NULL
) facts ORDER BY line;
SQL
)

work=$(mktemp -d)
trap 'rm -rf "$work"' EXIT
failures=0
: >"$work/seen"

for name in "${FILES[@]}"; do
	file="$CORPUS/$name.sql"
	db=$(tr -c 'a-z0-9\n' '_' <<<"$name")
	echo "== $name"
	if [[ ! -s "$file" ]]; then
		echo "  FAIL  $file missing"
		failures=$((failures + 1))
		continue
	fi

	# the generated files: a model that imports the file and replicates its first table (a model needs
	# one shard declaration; the layout does not change the schema files)
	dir="$work/$name"
	mkdir -p "$dir"
	cp "$file" "$dir/$name.msql"
	first=$(sed -nE 's/^[Cc][Rr][Ee][Aa][Tt][Ee] [Tt][Aa][Bb][Ll][Ee] (IF NOT EXISTS )?([^ (]+\.)?([^ (.]+).*/\3/p' "$file" | head -1)
	printf 'import "%s.msql"\n\ndatabase %s {\n    type: postgres\n\n    shard %s {\n        strategy: replicated\n    }\n}\n' \
		"$name" "$db" "$first" >"$dir/$name.shardml"
	if ! java -jar "$JAR" generate "$dir/$name.shardml" -o "$dir/out" >"$dir/cli.log" 2>&1; then
		echo "  FAIL  shardml generate"
		sed 's/^/        /' "$dir/cli.log"
		failures=$((failures + 1))
		continue
	fi

	ok=1
	for side in original generated; do
		psql_db postgres -c "DROP DATABASE IF EXISTS cmp_${side}_$db;" -c "CREATE DATABASE cmp_${side}_$db TEMPLATE template0;" \
			</dev/null >/dev/null || { echo "test-sql-corpus: could not create cmp_${side}_$db" >&2; exit 2; }
	done
	if ! out=$(psql_db "cmp_original_$db" <"$file" 2>&1); then
		echo "  FAIL  the original does not load"
		sed 's/^/        /' <<<"$out"
		ok=0
	fi
	generated=("$dir/out/$db-schema.sql")
	[[ -f "$dir/out/$db-foreign-keys.sql" ]] && generated+=("$dir/out/$db-foreign-keys.sql")
	if ! out=$(cat "${generated[@]}" | psql_db "cmp_generated_$db" 2>&1); then
		echo "  FAIL  the generated schema does not load"
		sed 's/^/        /' <<<"$out"
		ok=0
	fi
	if [[ $ok -eq 0 ]]; then
		failures=$((failures + 1))
		continue
	fi

	# a catalog dump that fails, or finds nothing, fails the file: an empty comparison proves nothing
	for side in original generated; do
		if ! psql_db "cmp_${side}_$db" -c "$CATALOG" </dev/null >"$dir/$side.txt" 2>"$dir/$side.err" \
			|| ! grep -q '^column ' "$dir/$side.txt"; then
			echo "  FAIL  no catalog for the $side database"
			sed 's/^/        /' "$dir/$side.err"
			ok=0
		fi
	done
	if [[ $ok -eq 0 ]]; then
		failures=$((failures + 1))
		continue
	fi
	echo "  $(wc -l <"$dir/original.txt" | tr -d ' ') facts in the original, $(wc -l <"$dir/generated.txt" | tr -d ' ') generated"

	# each difference: listed (with its reason) or a failure
	while IFS=$'\t' read -r side line; do
		reason=$(awk -F'\t' -v f="$name" -v s="$side" -v l="$line" '$1 == f && $2 == s && $3 == l { print $4; exit }' "$ALLOWLIST")
		if [[ -n "$reason" ]]; then
			echo "  allowed  $side: $line  ($reason)"
			printf '%s\t%s\t%s\n' "$name" "$side" "$line" >>"$work/seen"
		else
			echo "  FAIL     $side: $line"
			failures=$((failures + 1))
		fi
	done < <(comm -23 "$dir/original.txt" "$dir/generated.txt" | sed 's/^/original-only\t/'
		comm -13 "$dir/original.txt" "$dir/generated.txt" | sed 's/^/generated-only\t/')

	for side in original generated; do
		psql_db postgres -c "DROP DATABASE cmp_${side}_$db;" </dev/null >/dev/null
	done
done

# a listed difference that no longer occurs is stale: the list must say what is true
while IFS=$'\t' read -r file side line reason; do
	[[ -z "$file" || "$file" == \#* ]] && continue
	if ! grep -qxF "$(printf '%s\t%s\t%s' "$file" "$side" "$line")" "$work/seen"; then
		echo "FAIL  stale allowlist entry: $file $side: $line"
		failures=$((failures + 1))
	fi
done <"$ALLOWLIST"

echo
if [[ $failures -gt 0 ]]; then
	echo "FAILED: $failures"
	exit 1
fi
echo "OK: ${#FILES[@]} corpus files, original and generated catalogs equal but for the allowlist"
