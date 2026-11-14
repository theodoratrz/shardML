-- Run by bench/integration/test-examples.sh after the distribution script. Each statement fails
-- with a message that names what is wrong (a text that is not an integer cannot be cast to one; a
-- DO block raises). The script splits statements at lines ending in ';', so inside a DO block no
-- line ends in one.

-- the primary keys pg_dump declared in ALTER TABLE ONLY .. ADD CONSTRAINT reached the catalog,
-- with their names and columns
SELECT coalesce(('primary keys differ: ' || string_agg(coalesce(e.rel || ' ' || e.name || ' ' || e.def, 'unexpected')
           || ' / ' || coalesce(a.rel || ' ' || a.name || ' ' || a.def, 'missing'), '; '))::int, 0)
FROM (VALUES
    ('banking.accounts', 'accounts_pkey', 'PRIMARY KEY (customer_id, id)'),
    ('banking.cards', 'cards_pkey', 'PRIMARY KEY (customer_id, id)'),
    ('banking.customers', 'customers_pkey', 'PRIMARY KEY (id)'),
    ('banking.transactions', 'transactions_pkey', 'PRIMARY KEY (customer_id, id)')
) AS e(rel, name, def)
FULL JOIN (SELECT conrelid::regclass::text AS rel, conname::text AS name, pg_get_constraintdef(oid) AS def
           FROM pg_constraint WHERE contype = 'p' AND connamespace = 'banking'::regnamespace) a
       ON a.rel = e.rel AND a.name = e.name AND a.def = e.def
WHERE e.rel IS NULL OR a.rel IS NULL;

-- the four tables are distributed by the layout's keys, in one colocation group
SELECT coalesce(('distribution differs: ' || string_agg(e.rel || ' ' || coalesce(a.col, 'not distributed'), '; '))::int, 0)
FROM (VALUES ('banking.customers', 'id'), ('banking.accounts', 'customer_id'), ('banking.transactions', 'customer_id'),
             ('banking.cards', 'customer_id')) AS e(rel, col)
LEFT JOIN (SELECT logicalrelid::regclass::text AS rel, column_to_column_name(logicalrelid, partkey) AS col
           FROM pg_dist_partition WHERE partmethod = 'h') a ON a.rel = e.rel
WHERE a.col IS DISTINCT FROM e.col
   OR (SELECT count(DISTINCT colocationid) FROM pg_dist_partition
       WHERE logicalrelid::regclass::text LIKE 'banking.%') <> 1;

-- the keys are enforced, across shards
INSERT INTO banking.customers (id, name) VALUES (1, 'one'), (2, 'two');
INSERT INTO banking.accounts (id, customer_id) VALUES (10, 1), (10, 2);
DO $p$ BEGIN
    INSERT INTO banking.accounts (id, customer_id) VALUES (10, 1); RAISE EXCEPTION 'PRIMARY KEY accounts_pkey not enforced'; EXCEPTION
    WHEN unique_violation THEN NULL; END $p$;
DO $p$ BEGIN
    INSERT INTO banking.customers (id, name) VALUES (1, 'again'); RAISE EXCEPTION 'PRIMARY KEY customers_pkey not enforced'; EXCEPTION
    WHEN unique_violation THEN NULL; END $p$;
