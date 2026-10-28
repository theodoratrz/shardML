#!/usr/bin/env bash
# Integration test: deploy the generated Citus SQL of each example to the bench
# cluster and fail on any error.
#
#   bench/integration/test-examples.sh [model.shardml ...]
#
# Defaults to every examples/*.shardml. Needs the CLI jar (mvn install) and
# Docker; starts the cluster with bench/up.sh if it is not running.
#
# Per model, in a fresh schema named after the database:
#   1. <db>-schema.sql        every statement must succeed
#   2. <db>-distribution.sql  every statement must succeed
#   3. <model>.probe.sql      optional, next to the model: queries the
#                             application relies on, typically the joins
#                             behind each relationship. Citus refuses joins
#                             between distributed tables that are not on their
#                             distribution columns, so a probe fails when
#                             colocation is declared but the keys do not match.
#
# Every statement runs on its own, so one failure does not hide the next.
set -uo pipefail

ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
BENCH="$ROOT/bench"

shopt -s nullglob
jars=("$ROOT"/shardml.cli/target/*-cli.jar)
if [[ ${#jars[@]} -eq 0 ]]; then
	echo "test-examples: no CLI jar in shardml.cli/target; run 'mvn install' first" >&2
	exit 2
fi
JAR="${jars[0]}"

models=("$@")
if [[ ${#models[@]} -eq 0 ]]; then
	models=("$ROOT"/examples/*.shardml)
fi

if ! up_log=$("$BENCH/up.sh" 2>&1); then
	echo "$up_log" >&2
	echo "test-examples: bench/up.sh failed" >&2
	exit 2
fi

psql_coord() {
	docker compose -f "$BENCH/docker-compose.yml" exec -T coordinator \
		psql -U postgres -v ON_ERROR_STOP=1 -qAtX "$@"
}

# Splits a SQL file into statements (a statement ends at a line ending in ';'),
# NUL-separated. Comment lines stay with the statement that follows them.
split_statements() {
	awk '
		{ buf = buf $0 "\n" }
		/;[[:space:]]*$/ { printf "%s%c", buf, 0; buf = "" }
		END { if (buf ~ /[^[:space:]]/) printf "%s%c", buf, 0 }
	' "$1"
}

# Label for the report: the comment line just above the SQL if there is one,
# else the first line of SQL.
label_of() {
	local comment sql
	comment=$(grep -E '^[[:space:]]*--' <<<"$1" | tail -1 | sed -E 's/^[[:space:]]*--[[:space:]]*//')
	sql=$(grep -m1 -vE '^[[:space:]]*(--|$)' <<<"$1")
	if [[ -n "$comment" ]]; then echo "$comment"; else echo "${sql:0:100}"; fi
}

failures=0
statements=0

# Runs every statement of one file in the given schema and reports each one.
apply_file() {
	local schema="$1" file="$2" stmt out
	echo "  $(basename "$file")"
	while IFS= read -r -d '' stmt; do
		[[ "$stmt" =~ [^[:space:]] ]] || continue
		statements=$((statements + 1))
		if out=$(printf 'SET search_path TO %s;\n%s' "$schema" "$stmt" | psql_coord 2>&1 >/dev/null); then
			echo "    ok    $(label_of "$stmt")"
		else
			failures=$((failures + 1))
			echo "    FAIL  $(label_of "$stmt")"
			sed -E 's/^psql:<stdin>:[0-9]+: //; s/^/            /' <<<"$out"
		fi
	done < <(split_statements "$file")
}

for model in "${models[@]}"; do
	out_dir=$(mktemp -d)
	echo "== $(basename "$model")"
	if ! java -jar "$JAR" generate "$model" -o "$out_dir" >/dev/null 2>"$out_dir/cli.err"; then
		failures=$((failures + 1))
		echo "  FAIL  shardml generate"
		sed 's/^/        /' "$out_dir/cli.err"
		continue
	fi

	schema_sql=("$out_dir"/*-schema.sql)
	if [[ ${#schema_sql[@]} -eq 0 ]]; then
		echo "  skipped: not a Citus (postgres) target"
		continue
	fi
	db=$(basename "${schema_sql[0]}" -schema.sql)

	psql_coord -c "DROP SCHEMA IF EXISTS $db CASCADE;" -c "CREATE SCHEMA $db;" >/dev/null 2>&1 \
		|| { echo "test-examples: could not reset schema $db" >&2; exit 2; }

	apply_file "$db" "${schema_sql[0]}"
	apply_file "$db" "$out_dir/$db-distribution.sql"
	probe="${model%.shardml}.probe.sql"
	if [[ -f "$probe" ]]; then
		apply_file "$db" "$probe"
	fi
	rm -rf "$out_dir"
done

echo
if [[ $failures -gt 0 ]]; then
	echo "FAILED: $failures of $statements statements"
	exit 1
fi
echo "OK: $statements statements"
