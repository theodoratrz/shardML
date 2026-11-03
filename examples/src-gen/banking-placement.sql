\set ON_ERROR_STOP on
-- Shard placement for banking: moves every shard to the node the model assigns it.
-- Apply after the distribution script, with psql and outside a transaction: Citus moves
-- one shard per transaction, so every move is a statement of its own. Safe to re-run:
-- a shard already on its node is not moved.

-- Preflight: stops the script before any move if the cluster does not match the model
DO $preflight$
DECLARE
    g record;
    coloc integer;
    shards bigint;
    bad text;
BEGIN
    -- every declared node is an active primary worker, never the coordinator
    SELECT string_agg(format('%s (%s:%s)', d.name, d.host, d.port), ', ') INTO bad
    FROM (VALUES
        ('w2', 'worker2', 5432),
        ('w3', 'worker3', 5432),
        ('w1', 'worker1', 5432)
    ) AS d(name, host, port)
    WHERE NOT EXISTS (
        SELECT 1 FROM pg_dist_node n
        WHERE n.nodename = d.host AND n.nodeport = d.port
          AND n.isactive AND n.noderole = 'primary' AND n.groupid <> 0
    );
    IF bad IS NOT NULL THEN
        RAISE EXCEPTION 'placement: not an active primary worker in pg_dist_node: %', bad;
    END IF;

    FOR g IN SELECT * FROM (VALUES
        ('customers', ARRAY['customers', 'accounts', 'transactions', 'cards'], 32)
    ) AS v(root, tables, buckets) LOOP
        SELECT colocationid INTO coloc FROM pg_dist_partition
        WHERE logicalrelid = to_regclass(g.root) AND partmethod = 'h';
        IF coloc IS NULL THEN
            RAISE EXCEPTION 'placement: % is not a hash-distributed table', g.root;
        END IF;

        -- moving the root moves its whole Citus colocation group, so it must hold the model's tables and no others
        SELECT string_agg(coalesce(m.t, c.logicalrelid::text), ', ') INTO bad
        FROM (SELECT t, to_regclass(t) AS rel FROM unnest(g.tables) AS t) m
        FULL JOIN (SELECT logicalrelid FROM pg_dist_partition WHERE colocationid = coloc) c ON c.logicalrelid = m.rel
        WHERE m.rel IS NULL OR c.logicalrelid IS NULL;
        IF bad IS NOT NULL THEN
            RAISE EXCEPTION 'placement: the colocation group of % differs from the model in: %', g.root, bad;
        END IF;

        SELECT count(*) INTO shards FROM pg_dist_shard WHERE logicalrelid = to_regclass(g.root);
        IF shards <> g.buckets THEN
            RAISE EXCEPTION 'placement: % has % shards, but the model declares % buckets', g.root, shards, g.buckets;
        END IF;

        SELECT string_agg(s.shardid::text, ', ' ORDER BY s.shardid) INTO bad
        FROM pg_dist_shard s JOIN pg_dist_partition d ON d.logicalrelid = s.logicalrelid
        WHERE d.colocationid = coloc
          AND (SELECT count(*) FROM pg_dist_placement p WHERE p.shardid = s.shardid) <> 1;
        IF bad IS NOT NULL THEN
            RAISE EXCEPTION 'placement: shards of the % group without exactly one placement: %', g.root, bad;
        END IF;

        SELECT string_agg(s.shardid::text, ', ' ORDER BY s.shardid) INTO bad
        FROM pg_dist_shard s JOIN pg_dist_partition d ON d.logicalrelid = s.logicalrelid
        JOIN pg_dist_placement p ON p.shardid = s.shardid
        WHERE d.colocationid = coloc AND p.groupid = 0;
        IF bad IS NOT NULL THEN
            RAISE EXCEPTION 'placement: shards of the % group on the coordinator, which placement does not move: %', g.root, bad;
        END IF;
    END LOOP;
END $preflight$;

-- customers group (customers, accounts, transactions, cards): 32 buckets on w2, w3, w1
-- bucket 0 -> w2 (worker2:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker2', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 0
) s
WHERE (s.src_host, s.src_port) <> ('worker2', 5432) AND s.src_groupid <> 0;
-- bucket 1 -> w3 (worker3:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker3', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 1
) s
WHERE (s.src_host, s.src_port) <> ('worker3', 5432) AND s.src_groupid <> 0;
-- bucket 2 -> w1 (worker1:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker1', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 2
) s
WHERE (s.src_host, s.src_port) <> ('worker1', 5432) AND s.src_groupid <> 0;
-- bucket 3 -> w2 (worker2:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker2', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 3
) s
WHERE (s.src_host, s.src_port) <> ('worker2', 5432) AND s.src_groupid <> 0;
-- bucket 4 -> w3 (worker3:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker3', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 4
) s
WHERE (s.src_host, s.src_port) <> ('worker3', 5432) AND s.src_groupid <> 0;
-- bucket 5 -> w1 (worker1:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker1', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 5
) s
WHERE (s.src_host, s.src_port) <> ('worker1', 5432) AND s.src_groupid <> 0;
-- bucket 6 -> w2 (worker2:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker2', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 6
) s
WHERE (s.src_host, s.src_port) <> ('worker2', 5432) AND s.src_groupid <> 0;
-- bucket 7 -> w3 (worker3:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker3', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 7
) s
WHERE (s.src_host, s.src_port) <> ('worker3', 5432) AND s.src_groupid <> 0;
-- bucket 8 -> w1 (worker1:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker1', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 8
) s
WHERE (s.src_host, s.src_port) <> ('worker1', 5432) AND s.src_groupid <> 0;
-- bucket 9 -> w2 (worker2:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker2', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 9
) s
WHERE (s.src_host, s.src_port) <> ('worker2', 5432) AND s.src_groupid <> 0;
-- bucket 10 -> w3 (worker3:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker3', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 10
) s
WHERE (s.src_host, s.src_port) <> ('worker3', 5432) AND s.src_groupid <> 0;
-- bucket 11 -> w1 (worker1:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker1', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 11
) s
WHERE (s.src_host, s.src_port) <> ('worker1', 5432) AND s.src_groupid <> 0;
-- bucket 12 -> w2 (worker2:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker2', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 12
) s
WHERE (s.src_host, s.src_port) <> ('worker2', 5432) AND s.src_groupid <> 0;
-- bucket 13 -> w3 (worker3:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker3', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 13
) s
WHERE (s.src_host, s.src_port) <> ('worker3', 5432) AND s.src_groupid <> 0;
-- bucket 14 -> w1 (worker1:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker1', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 14
) s
WHERE (s.src_host, s.src_port) <> ('worker1', 5432) AND s.src_groupid <> 0;
-- bucket 15 -> w2 (worker2:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker2', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 15
) s
WHERE (s.src_host, s.src_port) <> ('worker2', 5432) AND s.src_groupid <> 0;
-- bucket 16 -> w3 (worker3:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker3', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 16
) s
WHERE (s.src_host, s.src_port) <> ('worker3', 5432) AND s.src_groupid <> 0;
-- bucket 17 -> w1 (worker1:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker1', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 17
) s
WHERE (s.src_host, s.src_port) <> ('worker1', 5432) AND s.src_groupid <> 0;
-- bucket 18 -> w2 (worker2:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker2', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 18
) s
WHERE (s.src_host, s.src_port) <> ('worker2', 5432) AND s.src_groupid <> 0;
-- bucket 19 -> w3 (worker3:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker3', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 19
) s
WHERE (s.src_host, s.src_port) <> ('worker3', 5432) AND s.src_groupid <> 0;
-- bucket 20 -> w1 (worker1:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker1', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 20
) s
WHERE (s.src_host, s.src_port) <> ('worker1', 5432) AND s.src_groupid <> 0;
-- bucket 21 -> w2 (worker2:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker2', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 21
) s
WHERE (s.src_host, s.src_port) <> ('worker2', 5432) AND s.src_groupid <> 0;
-- bucket 22 -> w3 (worker3:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker3', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 22
) s
WHERE (s.src_host, s.src_port) <> ('worker3', 5432) AND s.src_groupid <> 0;
-- bucket 23 -> w1 (worker1:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker1', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 23
) s
WHERE (s.src_host, s.src_port) <> ('worker1', 5432) AND s.src_groupid <> 0;
-- bucket 24 -> w2 (worker2:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker2', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 24
) s
WHERE (s.src_host, s.src_port) <> ('worker2', 5432) AND s.src_groupid <> 0;
-- bucket 25 -> w3 (worker3:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker3', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 25
) s
WHERE (s.src_host, s.src_port) <> ('worker3', 5432) AND s.src_groupid <> 0;
-- bucket 26 -> w1 (worker1:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker1', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 26
) s
WHERE (s.src_host, s.src_port) <> ('worker1', 5432) AND s.src_groupid <> 0;
-- bucket 27 -> w2 (worker2:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker2', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 27
) s
WHERE (s.src_host, s.src_port) <> ('worker2', 5432) AND s.src_groupid <> 0;
-- bucket 28 -> w3 (worker3:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker3', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 28
) s
WHERE (s.src_host, s.src_port) <> ('worker3', 5432) AND s.src_groupid <> 0;
-- bucket 29 -> w1 (worker1:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker1', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 29
) s
WHERE (s.src_host, s.src_port) <> ('worker1', 5432) AND s.src_groupid <> 0;
-- bucket 30 -> w2 (worker2:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker2', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 30
) s
WHERE (s.src_host, s.src_port) <> ('worker2', 5432) AND s.src_groupid <> 0;
-- bucket 31 -> w3 (worker3:5432)
SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker3', 5432, shard_transfer_mode => 'force_logical')
FROM (
    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
    JOIN pg_dist_placement p ON p.shardid = b.shardid
    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
    WHERE b.bucket = 31
) s
WHERE (s.src_host, s.src_port) <> ('worker3', 5432) AND s.src_groupid <> 0;
