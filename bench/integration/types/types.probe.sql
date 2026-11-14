-- Run by bench/integration/test-examples.sh after the distribution script. Each statement fails
-- with a message that names what is wrong (a text that is not an integer cannot be cast to one).

-- every column has the type the .msql declares, as the catalog spells it (format_type)
SELECT coalesce(('catalog types differ: ' || string_agg(
           coalesce(e.col, a.attname) || ' is ' || coalesce(format_type(a.atttypid, a.atttypmod), 'missing')
           || ', expected ' || coalesce(e.typ, 'no such column'), '; ' ORDER BY coalesce(e.col, a.attname)))::int, 0)
FROM (VALUES
    ('id', 'bigint'),
    ('integer', 'integer'),
    ('smallint', 'smallint'),
    ('serial', 'integer'),
    ('numeric', 'numeric'),
    ('amount', 'numeric(12,0)'),
    ('price', 'numeric(12,2)'),
    ('decimal', 'numeric(5,1)'),
    ('real', 'real'),
    ('float', 'double precision'),
    ('double', 'double precision'),
    ('char', 'character(2)'),
    ('character', 'character(3)'),
    ('varying', 'character varying(20)'),
    ('timestamptz', 'timestamp with time zone'),
    ('zone', 'timestamp with time zone'),
    ('time', 'timestamp without time zone'),
    ('jsonb', 'jsonb'),
    ('bytea', 'bytea')
) AS e(col, typ)
FULL JOIN (SELECT attname, atttypid, atttypmod FROM pg_attribute
           WHERE attrelid = 'all_types'::regclass AND attnum > 0 AND NOT attisdropped) a ON a.attname = e.col
WHERE e.col IS NULL OR a.attname IS NULL OR format_type(a.atttypid, a.atttypmod) <> e.typ;

-- all_types is hash-distributed by id, in 4 shards
SELECT coalesce(('not distributed as declared: ' || string_agg(partmethod::text || ' by '
           || column_to_column_name(logicalrelid, partkey) || ', ' || (SELECT count(*) FROM pg_dist_shard s
           WHERE s.logicalrelid = p.logicalrelid) || ' shards', ''))::int, 0)
FROM pg_dist_partition p
WHERE logicalrelid = 'all_types'::regclass
  AND (partmethod <> 'h' OR column_to_column_name(logicalrelid, partkey) <> 'id'
       OR (SELECT count(*) FROM pg_dist_shard s WHERE s.logicalrelid = p.logicalrelid) <> 4);

-- a row with a value of every type goes in, through the serial defaults
INSERT INTO all_types (integer, smallint, numeric, amount, price, decimal, real, float, double, char, character,
                       varying, timestamptz, zone, time, jsonb, bytea)
VALUES (2147483647, 32767, 1.5, 123456789012, 1234567890.12, 1234.5, 1.25, 2.5, 3.5, 'ab', 'abc',
        'twenty characters!!!', '2026-11-14 12:00:00+00', '2026-11-14 12:00:00+00', '2026-11-14 12:00:00',
        '{"k": [1, 2]}', '\xdeadbeef');

-- and reads back as written; the serials count from 1
SELECT coalesce(('values differ: ' || string_agg(row_to_json(t)::text, ''))::int, 0)
FROM all_types t
WHERE NOT (id = 1 AND integer = 2147483647 AND smallint = 32767 AND serial = 1 AND numeric = 1.5
           AND amount = 123456789012 AND price = 1234567890.12 AND decimal = 1234.5 AND real = 1.25
           AND float = 2.5 AND double = 3.5 AND char = 'ab' AND character = 'abc' AND octet_length(character) = 3
           AND varying = 'twenty characters!!!' AND timestamptz = '2026-11-14 12:00:00+00'
           AND zone = timestamptz AND time = '2026-11-14 12:00:00' AND jsonb -> 'k' ->> 1 = '2'
           AND bytea = '\xdeadbeef'::bytea);
