#!/usr/bin/env bash
# Integration test: deploy the generated Citus SQL of each example to the bench
# cluster and fail on any error.
#
#   bench/integration/test-examples.sh [model.shardml ...]
#
# Defaults to every examples/*.shardml, bench/integration/types/*.shardml
# (every column type of the .msql grammar on one distributed table),
# bench/integration/fk/*.shardml (foreign keys and verbatim constraints) and
# bench/integration/pgdump/*.shardml (pg_dump files of the SQL corpus, linked
# as .msql). Needs the CLI jar (mvn install) and Docker; starts the cluster
# with bench/up.sh if it is not running.
#
# Per model, in a fresh schema named after the database (a schema-qualified
# table of a pg_dump lands in its own schema instead, so every table the
# generated schema creates is dropped first):
#   1. <db>-schema.sql        every statement must succeed
#   2. <db>-distribution.sql  every statement must succeed
#   3. <db>-foreign-keys.sql  if generated (the schema has foreign keys): every
#                             statement must succeed. After the distribution:
#                             Citus rejects a foreign key to a table that is
#                             not distributed yet
#   4. <model>.probe.sql      optional, next to the model: queries the
#                             application relies on, typically the joins
#                             behind each relationship. Citus refuses joins
#                             between distributed tables that are not on their
#                             distribution columns, so a probe fails when
#                             colocation is declared but the keys do not match.
#                             Not run if step 2 or 3 had a failure: the tables
#                             would still be local, so every join would
#                             succeed. The probe file then counts as one
#                             failure.
#
# Every statement runs on its own, so one failure does not hide the next. Every
# model must deploy: one that generates no Citus schema is a failure, and the
# default run fails if one of its required models is missing, so CI cannot
# pass with a fixture silently left out.
set -uo pipefail

ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
BENCH="$ROOT/bench"

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
	echo "test-examples: no CLI jar in shardml.cli/target; run 'mvn install' first" >&2
	exit 2
fi
echo "test-examples: using $JAR"

models=("$@")
if [[ ${#models[@]} -eq 0 ]]; then
	models=("$ROOT"/examples/*.shardml "$ROOT"/bench/integration/types/*.shardml "$ROOT"/bench/integration/fk/*.shardml
		"$ROOT"/bench/integration/pgdump/*.shardml)
	# the default run must deploy at least these: a missing file (a renamed fixture, a glob that
	# matched nothing) fails the run instead of shrinking it
	required=(examples/banking.shardml examples/social.shardml bench/integration/types/types.shardml
		bench/integration/fk/fk.shardml bench/integration/pgdump/banking_dump.shardml bench/integration/pgdump/pgbench.shardml)
	for r in "${required[@]}"; do
		if [[ ! " ${models[*]} " == *" $ROOT/$r "* ]]; then
			echo "test-examples: required model $r not found" >&2
			exit 2
		fi
	done
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
deployed=()
file_failures=0

# Runs every statement of one file in the given schema and reports each one.
# Sets file_failures to the number of statements of this file that failed.
apply_file() {
	local schema="$1" file="$2" stmt out
	file_failures=0
	echo "  $(basename "$file")"
	while IFS= read -r -d '' stmt; do
		[[ "$stmt" =~ [^[:space:]] ]] || continue
		statements=$((statements + 1))
		if out=$(printf 'SET search_path TO %s;\n%s' "$schema" "$stmt" | psql_coord 2>&1 >/dev/null); then
			echo "    ok    $(label_of "$stmt")"
		else
			failures=$((failures + 1))
			file_failures=$((file_failures + 1))
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
		# never a silent skip: every model given to this script must deploy
		failures=$((failures + 1))
		echo "  FAIL  no -schema.sql generated: not a Citus (postgres) target"
		continue
	fi
	db=$(basename "${schema_sql[0]}" -schema.sql)

	psql_coord -c "DROP SCHEMA IF EXISTS $db CASCADE;" -c "CREATE SCHEMA $db;" >/dev/null 2>&1 \
		|| { echo "test-examples: could not reset schema $db" >&2; exit 2; }
	# tables qualified with another schema (banking.accounts, public.pgbench_accounts) from an earlier run
	while read -r table; do
		# </dev/null: docker compose exec would read the rest of the loop's input
		psql_coord -c "SET search_path TO $db;" -c "DROP TABLE IF EXISTS $table CASCADE;" </dev/null >/dev/null 2>&1 \
			|| { echo "test-examples: could not drop $table" >&2; exit 2; }
	done < <(sed -nE 's/^CREATE TABLE (IF NOT EXISTS )?([^ (]+) \($/\2/p' "${schema_sql[0]}")

	apply_file "$db" "${schema_sql[0]}"
	apply_file "$db" "$out_dir/$db-distribution.sql"
	deploy_failures=$file_failures
	if [[ -f "$out_dir/$db-foreign-keys.sql" ]]; then
		apply_file "$db" "$out_dir/$db-foreign-keys.sql"
		deploy_failures=$((deploy_failures + file_failures))
	fi
	probe="${model%.shardml}.probe.sql"
	if [[ -f "$probe" && $deploy_failures -gt 0 ]]; then
		failures=$((failures + 1))
		echo "  $(basename "$probe")"
		echo "    FAIL  not run: $deploy_failures distribution or foreign key statement(s) failed, so the probe would run on local tables"
	elif [[ -f "$probe" ]]; then
		apply_file "$db" "$probe"
	fi
	deployed+=("$(basename "$model")")
	rm -rf "$out_dir"
done

echo
echo "deployed ${#deployed[@]} of ${#models[@]} models: ${deployed[*]}"
if [[ ${#deployed[@]} -ne ${#models[@]} && $failures -eq 0 ]]; then
	failures=1 # a model that left the loop early without a FAIL line
fi
if [[ $failures -gt 0 ]]; then
	echo "FAILED: $failures of $statements statements"
	exit 1
fi
echo "OK: $statements statements"
