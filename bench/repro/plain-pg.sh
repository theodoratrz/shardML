#!/usr/bin/env bash
# The same table shape and replica identity on plain PostgreSQL 17 (the Citus
# image with Citus not loaded): one instance, publisher in db "pub", subscriber
# in db "sub". Tables are matched by name, so they cannot share a database.
set -euo pipefail
C=shardml-plain-pg
docker rm -f $C >/dev/null 2>&1 || true
# The empty initdb.d skips the image's CREATE EXTENSION citus.
docker run -d --name $C --platform linux/amd64 -e POSTGRES_HOST_AUTH_METHOD=trust \
  --tmpfs /docker-entrypoint-initdb.d citusdata/citus:14.2.0-pg17 \
  postgres -c wal_level=logical -c shared_preload_libraries= >/dev/null
for _ in $(seq 60); do docker exec $C pg_isready -U postgres -h localhost >/dev/null 2>&1 && break; sleep 1; done
sleep 3
q() { docker exec -i $C psql -U postgres -qAtX -d "$1" -c "$2"; }
q postgres "SELECT version()"; q postgres "CREATE DATABASE pub"; q postgres "CREATE DATABASE sub"
q pub "SELECT count(*) AS citus_loaded FROM pg_extension WHERE extname = 'citus'"
for db in pub sub; do q $db "CREATE TABLE t (id bigint, k bigint, v text, PRIMARY KEY (id, k));"; done
q pub "INSERT INTO t SELECT g, g, 'a' FROM generate_series(1, 20) g; CREATE PUBLICATION p FOR TABLE t;"
# Same cluster: the slot must exist before CREATE SUBSCRIPTION, or it hangs.
q pub "SELECT 'slot ' || slot_name FROM pg_create_logical_replication_slot('s', 'pgoutput')"
q sub "CREATE SUBSCRIPTION s CONNECTION 'dbname=pub user=postgres' PUBLICATION p
       WITH (binary = true, create_slot = false, slot_name = 's');"
sleep 5
echo "1. same setup as the Citus child (PK (id, k), replica identity default): UPDATE + DELETE"
q pub "UPDATE t SET v = 'b' WHERE id = 1 AND k = 1; DELETE FROM t WHERE id = 2 AND k = 2;"
sleep 3
q sub "SELECT 'sub row 1: ' || v FROM t WHERE id = 1; SELECT 'sub row 2 exists: ' || count(*) FROM t WHERE id = 2;
       SELECT 'apply errors: ' || apply_error_count FROM pg_stat_subscription_stats;"
echo "2. the state the Citus decoder saw: a published table without a replica identity"
q pub "CREATE TABLE t2 (id bigint, k bigint, v text); INSERT INTO t2 VALUES (1, 1, 'a');
       ALTER PUBLICATION p ADD TABLE t2;"
q pub "UPDATE t2 SET v = 'b' WHERE id = 1" 2>&1 || true
docker rm -f $C >/dev/null
