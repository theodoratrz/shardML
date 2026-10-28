#!/usr/bin/env bash
# Start the Citus cluster, wait for it, register the workers and print versions.
# Safe to re-run: workers already in pg_dist_node are skipped.
set -euo pipefail

cd "$(dirname "$0")"

WORKERS=(worker1 worker2 worker3)

psql_coord() {
	docker compose exec -T coordinator psql -U postgres -v ON_ERROR_STOP=1 -qAtX "$@"
}

docker compose up -d --wait

psql_coord -c "SELECT citus_set_coordinator_host('coordinator', 5432);" >/dev/null

for w in "${WORKERS[@]}"; do
	registered=$(psql_coord -c "SELECT count(*) FROM pg_dist_node WHERE nodename = '$w';")
	if [[ "$registered" == "0" ]]; then
		psql_coord -c "SELECT citus_add_node('$w', 5432);" >/dev/null
		echo "registered $w"
	fi
done

echo
echo "Citus:    $(psql_coord -c 'SELECT citus_version();')"
echo "Postgres: $(psql_coord -c 'SHOW server_version;')"
echo "Workers:"
psql_coord -c "SELECT '  ' || node_name || ':' || node_port FROM citus_get_active_worker_nodes() ORDER BY 1;"
echo
echo "Connect:  psql -h 127.0.0.1 -p ${CITUS_PORT:-5433} -U postgres"
