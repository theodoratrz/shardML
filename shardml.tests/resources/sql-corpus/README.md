# SQL grammar corpus

Real-world PostgreSQL DDL that the `.msql` grammar (`shardml/src/io/shardml/SQL.xtext`) is tested
against: parsing, validation, round-trip through the generator, and deployment on the bench Citus
cluster. The files are fixtures, not examples: some of them are meant to fail (see Pagila).

Every file starts with a `/* */` header giving the same source, commit, license and edits as the table
below. `/* */` is valid in PostgreSQL and in the grammar, so the header changes no result. The upstream
license texts are in [`licenses/`](licenses/).

| File | Source | Commit | License | Edits |
|---|---|---|---|---|
| `tpcc.sql` | [cmu-db/benchbase](https://github.com/cmu-db/benchbase) `src/main/resources/benchmarks/tpcc/ddl-postgres.sql`: TPC-C's 9 tables, hand-written, lower case | `33c00473807e` | Apache-2.0, Copyright 2021 by OLTPBenchmark Project ([notice](licenses/benchbase-LICENSE.txt), [text](licenses/Apache-2.0.txt)) | header only |
| `pgbench.sql` | `pgbench -i -I dtpf` (tables, primary keys, foreign keys; no data) by pgbench 17.10, then `pg_dump --schema-only` 17.10, on the bench coordinator (`citusdata/citus:14.2.0-pg17`) | PostgreSQL 17.10 | PostgreSQL License ([COPYRIGHT](licenses/postgresql-COPYRIGHT.txt)) | header only; generated, so it contains pg_dump's random `\restrict` token |
| `banking-pg_dump.sql` | `examples/banking.shardml` as deployed and distributed by `bench/integration/test-examples.sh` (schema `banking`), then `pg_dump --schema-only -n banking` 17.10 on the coordinator | this repository at `fa7520c` | this repository's | header only; generated, as above |
| `chinook.sql` | [lerocha/chinook-database](https://github.com/lerocha/chinook-database) `ChinookDatabase/DataSources/Chinook_PostgreSql.sql`: hand-written, upper case, foreign keys in `ALTER TABLE`, `CREATE INDEX` | `7f67772503d7` | MIT, Copyright (c) 2008-2024 Luis Rocha ([text](licenses/chinook-LICENSE.txt)) | header; the `INSERT` statements (data) removed; the `DROP DATABASE`, `CREATE DATABASE` and psql `\c` lines removed |
| `northwind.sql` | [pthom/northwind_psql](https://github.com/pthom/northwind_psql) `northwind.sql`: pg_dump-like, keys in `ALTER TABLE ONLY .. ADD CONSTRAINT` | `cd0ef28d6636` | Microsoft Public License (Ms-PL); the database originates from Microsoft ([text](licenses/northwind_psql-LICENSE.txt)) | header; the `INSERT` statements (data) removed |
| `pagila-schema.sql` | [devrimgunduz/pagila](https://github.com/devrimgunduz/pagila) `pagila-schema.sql`: a full pg_dump schema (sequences, domains, enum types, functions, triggers, views, partitions) | `6ea53f812254` | PostgreSQL License ([text](licenses/pagila-LICENSE.txt)) | header only |

The commits are the head of each repository's default branch on 2026-11-10, when the files were taken.

## Pagila is a negative fixture

Pagila uses PostgreSQL 18 syntax (`GENERATED ALWAYS AS (..) VIRTUAL`) and the pgvector type
`public.vector(20)`. The bench cluster runs PostgreSQL 17.10 without pgvector, so the file can't be
deployed there as it is, and its deploy test is skipped (the reason is in the test's name). It still goes
through parsing, validation and round-trip, where it checks that every statement the grammar does not
support is reported as an error at its location, never skipped.
