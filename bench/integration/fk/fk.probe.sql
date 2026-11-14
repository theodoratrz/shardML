-- Run by bench/integration/test-examples.sh after the distribution and foreign key scripts. Each
-- statement fails with a message that names what is wrong (a text that is not an integer cannot be
-- cast to one; a DO block raises). The script splits statements at lines ending in ';', so inside a
-- DO block no line ends in one.

-- the three foreign keys are in the catalog as declared: names, columns, the referenced key
-- (PostgreSQL fills in an omitted column list) and the actions
SELECT coalesce(('foreign keys differ: ' || string_agg(coalesce(e.rel || ' ' || e.name || ' ' || e.def, 'unexpected')
           || ' / ' || coalesce(a.rel || ' ' || a.name || ' ' || a.def, 'missing'), '; '))::int, 0)
FROM (VALUES
    ('campaigns', 'campaigns_company_id_fkey', 'FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE'),
    ('campaigns', 'campaigns_country_fkey', 'FOREIGN KEY (country) REFERENCES countries(code)'),
    ('ads', 'ads_campaign_fkey', 'FOREIGN KEY (campaign_id, company_id) REFERENCES campaigns(id, company_id) ON DELETE CASCADE')
) AS e(rel, name, def)
FULL JOIN (SELECT conrelid::regclass::text AS rel, conname::text AS name, pg_get_constraintdef(oid) AS def
           FROM pg_constraint WHERE contype = 'f' AND connamespace = 'fk'::regnamespace) a
       ON a.rel = e.rel AND a.name = e.name AND a.def = e.def
WHERE e.rel IS NULL OR a.rel IS NULL;

-- the verbatim texts reached the catalog: defaults, named and unnamed checks, the identity, the options
SELECT coalesce(('verbatim constraints differ: ' || string_agg(e.what || ' is ' || coalesce(a.got, 'missing')
           || ', expected ' || e.expected, '; '))::int, 0)
FROM (VALUES
    ('default companies.created_at', 'now()'),
    ('default campaigns.budget', '0'),
    ('default campaigns.status', '''draft''::text'),
    ('check companies.name_not_blank', 'CHECK ((length(TRIM(BOTH FROM name)) > 0))'),
    ('check campaigns.campaigns_budget_check', 'CHECK ((budget >= (0)::numeric))'),
    ('identity ads.id', 'd'),
    ('options campaigns', '{fillfactor=90}')
) AS e(what, expected)
LEFT JOIN (
    SELECT 'default ' || c.relname || '.' || a.attname AS what, pg_get_expr(d.adbin, d.adrelid) AS got
    FROM pg_attrdef d JOIN pg_attribute a ON a.attrelid = d.adrelid AND a.attnum = d.adnum
    JOIN pg_class c ON c.oid = d.adrelid WHERE c.relnamespace = 'fk'::regnamespace
    UNION ALL
    SELECT 'check ' || conrelid::regclass::text || '.' || conname, pg_get_constraintdef(oid)
    FROM pg_constraint WHERE contype = 'c' AND connamespace = 'fk'::regnamespace
    UNION ALL
    SELECT 'identity ' || c.relname || '.' || a.attname, a.attidentity::text
    FROM pg_attribute a JOIN pg_class c ON c.oid = a.attrelid
    WHERE c.relnamespace = 'fk'::regnamespace AND a.attidentity <> ''
    UNION ALL
    SELECT 'options ' || relname, reloptions::text FROM pg_class
    WHERE relnamespace = 'fk'::regnamespace AND reloptions IS NOT NULL AND relkind = 'r'
) a ON a.what = e.what
WHERE a.got IS DISTINCT FROM e.expected;

-- rows in two companies, so in two shards; the defaults and the identity fill what is left out
INSERT INTO countries VALUES ('GR', 'Greece');
INSERT INTO companies (id, name) VALUES (1, 'one'), (2, 'two');
INSERT INTO campaigns (company_id, id, country) VALUES (1, 10, 'GR'), (2, 20, NULL);
INSERT INTO ads (company_id, campaign_id) VALUES (1, 10), (1, 10), (2, 20);
DO $p$ BEGIN IF (SELECT count(*) FROM campaigns WHERE status = 'draft' AND budget = 0) <> 2
    OR (SELECT count(DISTINCT id) FROM ads) <> 3 THEN RAISE EXCEPTION 'defaults or identity not applied'; END IF; END $p$;

-- the checks are enforced
DO $p$ BEGIN
    INSERT INTO companies (id, name) VALUES (3, '  '); RAISE EXCEPTION 'CHECK name_not_blank not enforced'; EXCEPTION
    WHEN check_violation THEN NULL; END $p$;
DO $p$ BEGIN
    UPDATE campaigns SET budget = -1 WHERE company_id = 2; RAISE EXCEPTION 'CHECK budget >= 0 not enforced'; EXCEPTION
    WHEN check_violation THEN NULL; END $p$;

-- the foreign keys are enforced: to the reference table, and between colocated shards
DO $p$ BEGIN
    INSERT INTO campaigns (company_id, id, country) VALUES (1, 11, 'XX'); RAISE EXCEPTION 'campaigns -> countries not enforced'; EXCEPTION
    WHEN foreign_key_violation THEN NULL; END $p$;
DO $p$ BEGIN
    INSERT INTO campaigns (company_id, id) VALUES (3, 30); RAISE EXCEPTION 'campaigns -> companies not enforced'; EXCEPTION
    WHEN foreign_key_violation THEN NULL; END $p$;
DO $p$ BEGIN
    INSERT INTO ads (company_id, campaign_id) VALUES (2, 10); RAISE EXCEPTION 'ads -> campaigns not enforced'; EXCEPTION
    WHEN foreign_key_violation THEN NULL; END $p$;

-- ON DELETE CASCADE runs down both levels in the deleted company's shard, and leaves the other alone
DELETE FROM companies WHERE id = 1;
SELECT coalesce(('cascade left: ' || string_agg(t || ' ' || n, ', '))::int, 0)
FROM (SELECT 'campaigns' AS t, count(*) FILTER (WHERE company_id = 1) || ' of company 1, '
             || count(*) FILTER (WHERE company_id = 2) || ' of company 2' AS n,
             count(*) FILTER (WHERE company_id = 1) = 0 AND count(*) FILTER (WHERE company_id = 2) = 1 AS ok FROM campaigns
      UNION ALL
      SELECT 'ads', count(*) FILTER (WHERE company_id = 1) || ' of company 1, '
             || count(*) FILTER (WHERE company_id = 2) || ' of company 2',
             count(*) FILTER (WHERE company_id = 1) = 0 AND count(*) FILTER (WHERE company_id = 2) = 1 FROM ads) x
WHERE NOT ok;
