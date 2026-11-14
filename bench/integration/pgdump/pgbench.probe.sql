-- Run by bench/integration/test-examples.sh after the distribution and foreign key scripts. Each
-- statement fails with a message that names what is wrong (a text that is not an integer cannot be
-- cast to one; a DO block raises). The script splits statements at lines ending in ';', so inside a
-- DO block no line ends in one.

-- every key pg_dump declared in ALTER TABLE ONLY .. ADD CONSTRAINT reached the catalog: 3 primary
-- keys and 5 foreign keys, with their names, columns and referenced tables
SELECT coalesce(('keys differ: ' || string_agg(coalesce(e.rel || ' ' || e.name || ' ' || e.def, 'unexpected')
           || ' / ' || coalesce(a.rel || ' ' || a.name || ' ' || a.def, 'missing'), '; '))::int, 0)
FROM (VALUES
    ('pgbench_accounts', 'pgbench_accounts_pkey', 'PRIMARY KEY (aid)'),
    ('pgbench_branches', 'pgbench_branches_pkey', 'PRIMARY KEY (bid)'),
    ('pgbench_tellers', 'pgbench_tellers_pkey', 'PRIMARY KEY (tid)'),
    ('pgbench_accounts', 'pgbench_accounts_bid_fkey', 'FOREIGN KEY (bid) REFERENCES pgbench_branches(bid)'),
    ('pgbench_history', 'pgbench_history_aid_fkey', 'FOREIGN KEY (aid) REFERENCES pgbench_accounts(aid)'),
    ('pgbench_history', 'pgbench_history_bid_fkey', 'FOREIGN KEY (bid) REFERENCES pgbench_branches(bid)'),
    ('pgbench_history', 'pgbench_history_tid_fkey', 'FOREIGN KEY (tid) REFERENCES pgbench_tellers(tid)'),
    ('pgbench_tellers', 'pgbench_tellers_bid_fkey', 'FOREIGN KEY (bid) REFERENCES pgbench_branches(bid)')
) AS e(rel, name, def)
FULL JOIN (SELECT c.relname::text AS rel, k.conname::text AS name,
                  replace(pg_get_constraintdef(k.oid), 'public.', '') AS def
           FROM pg_constraint k JOIN pg_class c ON c.oid = k.conrelid
           WHERE k.contype IN ('p', 'f') AND k.connamespace = 'public'::regnamespace AND c.relname LIKE 'pgbench\_%') a
       ON a.rel = e.rel AND a.name = e.name AND a.def = e.def
WHERE e.rel IS NULL OR a.rel IS NULL;

-- the layout: branches and tellers are reference tables, accounts and history distributed by aid and colocated
SELECT coalesce(('distribution differs: ' || string_agg(e.rel || ' ' || coalesce(a.how, 'not distributed'), '; '))::int, 0)
FROM (VALUES ('public.pgbench_branches', 'reference'), ('public.pgbench_tellers', 'reference'),
             ('public.pgbench_accounts', 'hash aid'), ('public.pgbench_history', 'hash aid')) AS e(rel, how)
LEFT JOIN (SELECT logicalrelid::regclass::text AS rel,
                  CASE partmethod WHEN 'n' THEN 'reference' ELSE 'hash ' || column_to_column_name(logicalrelid, partkey) END AS how
           FROM pg_dist_partition) a ON a.rel = e.rel
WHERE a.how IS DISTINCT FROM e.how
   OR (SELECT count(DISTINCT colocationid) FROM pg_dist_partition
       WHERE logicalrelid IN ('public.pgbench_accounts'::regclass, 'public.pgbench_history'::regclass)) <> 1;

-- rows that satisfy every key, in two shards of accounts
INSERT INTO public.pgbench_branches (bid) VALUES (1);
INSERT INTO public.pgbench_tellers (tid, bid) VALUES (1, 1);
INSERT INTO public.pgbench_accounts (aid, bid) VALUES (1, 1), (2, 1);
INSERT INTO public.pgbench_history (tid, bid, aid, delta) VALUES (1, 1, 1, 5), (1, 1, 2, 7);

-- each foreign key is enforced
DO $p$ BEGIN
    INSERT INTO public.pgbench_history (tid, bid, aid) VALUES (1, 1, 99); RAISE EXCEPTION 'pgbench_history_aid_fkey not enforced'; EXCEPTION
    WHEN foreign_key_violation THEN NULL; END $p$;
DO $p$ BEGIN
    INSERT INTO public.pgbench_history (tid, bid, aid) VALUES (1, 99, 1); RAISE EXCEPTION 'pgbench_history_bid_fkey not enforced'; EXCEPTION
    WHEN foreign_key_violation THEN NULL; END $p$;
DO $p$ BEGIN
    INSERT INTO public.pgbench_history (tid, bid, aid) VALUES (99, 1, 1); RAISE EXCEPTION 'pgbench_history_tid_fkey not enforced'; EXCEPTION
    WHEN foreign_key_violation THEN NULL; END $p$;
DO $p$ BEGIN
    INSERT INTO public.pgbench_accounts (aid, bid) VALUES (3, 99); RAISE EXCEPTION 'pgbench_accounts_bid_fkey not enforced'; EXCEPTION
    WHEN foreign_key_violation THEN NULL; END $p$;
DO $p$ BEGIN
    INSERT INTO public.pgbench_tellers (tid, bid) VALUES (2, 99); RAISE EXCEPTION 'pgbench_tellers_bid_fkey not enforced'; EXCEPTION
    WHEN foreign_key_violation THEN NULL; END $p$;
-- and a referenced row cannot go while referenced (NO ACTION, from the reference table to the shards)
DO $p$ BEGIN
    DELETE FROM public.pgbench_branches WHERE bid = 1; RAISE EXCEPTION 'pgbench_branches row deleted while referenced'; EXCEPTION
    WHEN foreign_key_violation THEN NULL; END $p$;
