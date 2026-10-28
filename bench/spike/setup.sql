-- Test table for the Phase 0 spike. k = id % 100003, so every id maps to one k
-- and the writer can address a row by (id, k) without a lookup.
DROP TABLE IF EXISTS t;

CREATE TABLE t (
    id bigint,
    k  bigint,
    v  text,
    PRIMARY KEY (id, k)
);

SELECT create_distributed_table('t', 'k', shard_count := 32);

INSERT INTO t (id, k, v)
SELECT g, g % 100003, md5(g::text)
FROM generate_series(1, 1000000) AS g;

ANALYZE t;
