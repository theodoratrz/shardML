#!/usr/bin/env bash
# Local Citus shard split + one concurrent write. Usage: repro.sh [update|insert|delete]
set -euo pipefail; cd "$(dirname "$0")/.."
W=${1:-update}; ./down.sh >/dev/null 2>&1; ./up.sh >/dev/null 2>&1
q() { docker compose exec -T "$1" psql -U postgres -qAtX -v ON_ERROR_STOP=1 -c "$2"; }
q coordinator "CREATE TABLE t (id bigint, k bigint, v text, PRIMARY KEY (id, k));
  SELECT create_distributed_table('t', 'k', shard_count := 4);
  INSERT INTO t SELECT g, g, 'a' FROM generate_series(1, 20) g;" >/dev/null
S=$(q coordinator "SELECT shardid FROM pg_dist_shard WHERE logicalrelid = 't'::regclass ORDER BY shardid LIMIT 1")
case $W in  # k = 1 hashes into the lower half, the child that stays on worker1
  update) SQL="UPDATE t_$S SET v = 'b' WHERE id = 1 AND k = 1" ;;
  insert) SQL="INSERT INTO t_$S VALUES (101, 1, 'b')" ;;
  delete) SQL="DELETE FROM t_$S WHERE id = 1 AND k = 1" ;;
esac
# On worker1: as soon as the split has created its replication slots, write once.
q worker1 "DO \$\$ BEGIN WHILE NOT EXISTS (SELECT FROM pg_replication_slots WHERE confirmed_flush_lsn IS NOT NULL) LOOP
  PERFORM pg_sleep(0.01); END LOOP; $SQL; END \$\$" &
# Split the shard at its hash midpoint; nodes 2 and 3 are worker1 (source) and worker2.
q coordinator "SET statement_timeout = '${TIMEOUT:-30}s'; SELECT citus_split_shard_by_split_points($S,
  ARRAY['-1610612737'], ARRAY[2, 3])" >/dev/null && echo "$W: split completed" || echo "$W: split did NOT complete in ${TIMEOUT:-30} s"
wait; docker compose logs worker1 | grep -o "ERROR: .*replica identity.*" | sort -u || echo "no apply errors on worker1"
