# ShardML

ShardML is a modelling language for distributed data layout: declare how a schema is sharded across a Citus/PostgreSQL cluster, validate the design, and generate deployable configuration. The "ML" stands for "modelling language", not machine learning.

Built with **Xtext**; the scoping, validation and generation code is plain Java.

---

## Overview

Most large-scale applications banks, social platforms, logistics systems, rely on horizontal partitioning to handle growing data volumes. Sharding decisions are typically expressed directly in platform-specific configuration files, with little support for early validation. Errors are usually caught at deployment, when they are far more costly to fix.

ShardML addresses the gap between sharding intent and deployment correctness. Engineers declare their distribution strategy, query access patterns, and table relationships in a single model. The language validates the model against the imported SQL schema before anything is deployed, and generates deployment-ready Citus configuration from one source.

---

## Features

- **Declarative syntax**: describe what to shard and how, not low-level middleware configuration
- **Validators** (SHML_001 to SHML_077) covering structural correctness, colocation consistency, performance anti-patterns, query pattern analysis, the imported SQL schema and foreign keys against the layout, and workloads
- **Policy-aware query analysis**: route rules support `allow`, `warn`, `deny` to control strictness per table
- **Citus code generation**: produces the schema and distribution SQL, plus a JSON summary, from a single model
- **Cross-schema import**: reference an existing `.msql` SQL schema file; all table and column references are resolved and validated against it
- **Shard placement**: declare the cluster's worker nodes and which nodes each colocation group goes to; the generated placement script moves the shards there, safely re-runnable
- **Command-line tool**: `shardml validate`, `shardml generate` and `shardml placement`, with `file:line` diagnostics and a non-zero exit code on model errors
- **Cost model**: `shardml cost` compares layouts under a workload (distributed transactions, fan-out, data and load skew, bottleneck load) without deploying them; `shardml route` checks its routing against Citus

---

## Language at a Glance

A ShardML model imports an existing SQL schema and declares how the database should be distributed:

```
import "banking.msql"

database banking {
    type: postgres

    shard accounts {
        strategy: hash
        key: customer_id
        buckets: 32
        colocate_with: customers
    }

    route accounts {
        policy: warn

        query FindByCustomer {
            type: read
            where: customer_id = ?
        }
    }

    accounts belongs_to customers via customer_id
}
```

---

## Generated Artefacts

| File | Contents |
|---|---|
| `<database>-sharding.json` | Distribution config in JSON |
| `<database>-schema.sql` | The imported schema without its foreign keys: `CREATE SCHEMA IF NOT EXISTS`, `CREATE TABLE` (keys added by `ALTER TABLE` written inline), then `CREATE INDEX` (apply before the distribution script) |
| `<database>-distribution.sql` | Citus SQL commands (`create_distributed_table`, `create_reference_table`); every root (a table without `colocate_with`) gets `colocate_with => 'none'`, so it starts its own Citus colocation group instead of joining another table of the same shape |
| `<database>-foreign-keys.sql` | Only when the schema has foreign keys: one `ALTER TABLE .. ADD .. FOREIGN KEY` each, applied after the distribution script. Citus rejects a foreign key to a table it has not distributed yet, so foreign keys are never written inside `CREATE TABLE` |
| `<database>-placement.sql` | Only for a model that declares `node`s: moves every shard to the node the model assigns it (apply last, see below) |

### Placement script

Each `node` names a worker as Citus knows it (`pg_dist_node`). Bucket *i* of a colocation group goes to the group's *i*-th target node, round-robin: every declared node in declaration order, or the nodes of a `place … { strategy: pin nodes: … }` block. The script, applied with `psql` after the distribution script and outside a transaction:

1. runs a preflight that changes nothing and stops the script if the cluster does not match the model: every declared node must be an active primary worker (never the coordinator), and per group Citus must colocate exactly the model's tables with one shard per bucket, each on exactly one node and none on the coordinator;
2. then moves the root's shard of each bucket that is not on its target, one statement per move with `shard_transfer_mode => 'force_logical'` (Citus moves at most one shard per transaction this way, and the mode fails instead of silently blocking writes). Colocated tables move with their root. Shards are found by bucket, ordered by `shardminvalue::bigint`, never by shard ID.

A shard already on its node is skipped, so a second run moves nothing, and a run stopped halfway is finished by running the script again. `shardml placement <model>` prints the same map, one `table<TAB>bucket<TAB>host:port` line per shard.

`generate` never deletes files. If a model's `node` declarations are later removed, delete the stale `<database>-placement.sql` from `src-gen/` by hand.

---

## Supported Target Platforms

| Platform | Underlying DBMS |
|---|---|
| Citus | PostgreSQL |

Earlier versions also generated Vitess output for `type: mysql`. It was not a valid VSchema (no `sharded` flag, no `vindexes` map, per-table bucket counts Vitess has no notion of), so it was dropped rather than kept as fake support. `type: mysql` still parses and is rejected with SHML_025.

---

## Project Structure

```
shardml/                               # Language runtime
  src/io/shardml/
    SQL.xtext                          # Lightweight DDL parser (.msql schema files)
    ShardML.xtext                      # ShardML language grammar (.shardml)
    GenerateSQLMaven.mwe2              # Xtext generation, step 1 (SQL)
    GenerateShardMLMaven.mwe2          # Xtext generation, step 2 (ShardML)
    scoping/
      ShardMLScopeProvider.java        # Cross-resource reference resolution
    validation/
      ShardMLValidator.java            # 24 validation rules
    generator/
      ShardMLGenerator.java            # Citus output generation
  src-gen/                             # Xtext-generated parser infrastructure
  model/generated/                     # Generated Ecore metamodels

shardml.tests/                         # JUnit 5 test suite
shardml.cli/                           # Command-line front end
shardml.bench/                         # Phase 4 validation driver (replays a workload on Citus)

examples/
  README.md                            # What each example shows
  banking.msql / banking.shardml       # Banking scenario
  social.msql  / social.shardml        # Social network scenario
  src-gen/                             # Generated output for both
```

---

## Design Notes

### Cross-file scoping

ShardML uses a custom `ImportURI`-style import rather than Xtext's built-in global scope mechanism. When a `.shardml` file declares `import "schema.msql"`, the `ShardMLScopeProvider` manually resolves the URI relative to the importing resource, loads the target resource from the `ResourceSet`, and builds scopes from the resulting EMF model objects.

This was necessary because Xtext's default `ImportedNamespaceAwareLocalScopeProvider` resolves names by qualified ID, it cannot navigate a cross-metamodel reference to `sql::Table` objects defined in a separate grammar. The custom provider instead builds `IScope` instances directly from the imported `Schema`'s `Table` and `Column` lists, which makes cross-grammar references transparent to the validator and generator.

A notable detail: column references are scoped to the *specific table* they belong to, not the full column namespace: a shard key (`key:`) to the table of its `ShardDecl`, a `via` column to the subject of its `belongs_to`, and `where:` / `sort:` / `group_by:` columns to the table of their `route`. This prevents false name collisions when multiple tables share a column name (e.g. `id`), a common case that Xtext's flat scoping would have gotten wrong.

### Names and keywords in `.msql` files

`.msql` schemas are read the way PostgreSQL reads SQL:

- Keywords match in any case (`create table`, `CREATE TABLE`). Statements may end in `;`. Comments are `--` and `/* */`; `//` is not a comment (it was before).
- An unquoted name is folded to lower case, so `CREATE TABLE Customers` declares `customers`, and a model refers to it as `customers`. A `"quoted"` name is kept exactly as written, and the generator writes it back quoted.
- A keyword can be a table or column name exactly when PostgreSQL allows it bare: `key`, `text`, `date`, `int`, `set`, `match`, ... are names, while reserved words (`table`, `unique`, `primary`, `not`, `null`, `create`, `with`, `check`, `default`, `constraint`, `references`, `foreign`, `on`, `as`, `full`, ...) must be quoted, e.g. `"unique" BIGINT`. The list follows PostgreSQL 17's `pg_get_keywords()`.
- Tables may be schema-qualified (`banking.accounts`) and use `IF NOT EXISTS`; both are written back as given. A model refers to a table by its name without the schema, so two tables of one name in one file are an error (SHML_066), also in different schemas.
- Known limit: a quoted name that is not a plain identifier (with a space, or mixed case meant to differ from the folded name) cannot be referred to from a `.shardml` or `.workload` file yet.

### Column types in `.msql` files

| Types | Written back as |
|---|---|
| `INT`, `INTEGER`, `SMALLINT`, `BIGINT`, `SERIAL`, `BIGSERIAL` | as spelled (`SERIAL` / `BIGSERIAL` keep their sequence default) |
| `NUMERIC`, `DECIMAL`, `NUMERIC(p)`, `NUMERIC(p,s)`, `DECIMAL(p,s)`, `REAL`, `FLOAT`, `DOUBLE PRECISION` | as spelled |
| `VARCHAR(n)`, `CHARACTER VARYING(n)`, `CHAR(n)`, `CHARACTER(n)`, `TEXT` | as spelled |
| `TIMESTAMP`, `TIMESTAMPTZ`, `DATE`, `BOOLEAN`, `UUID`, `JSONB`, `BYTEA` | as spelled |
| `TIMESTAMP WITHOUT TIME ZONE`, `TIMESTAMP WITH TIME ZONE` | `TIMESTAMP`, `TIMESTAMPTZ` (the same types) |

Type words are column names where PostgreSQL allows it (`double DOUBLE PRECISION`, `zone TIMESTAMPTZ`). Validators compare PostgreSQL's types, not spellings: `INT`, `INTEGER` and `SERIAL` are all `int4`, so a `SERIAL` key colocates with an `INT` foreign key. Any other type (`INTERVAL`, arrays, user-defined types) is a syntax error, and so is a sized type without its size (`VARCHAR`, `CHAR`). A column whose type did not parse is never read as `INT`. `bench/integration/types/` deploys one distributed table with every type above to the bench cluster.

### Constraints and foreign keys in `.msql` files

Besides `PRIMARY KEY`, `UNIQUE` and `NOT NULL`, a `.msql` schema may use:

- **Named constraints** (`CONSTRAINT name ...`) on any column or table constraint, and the explicit `NULL`.
- **Foreign keys**, column-level (`customer_id BIGINT REFERENCES customers`) or table-level and composite (`FOREIGN KEY (a, b) REFERENCES t (x, y)`), with `MATCH`, `ON DELETE` / `ON UPDATE` (`NO ACTION`, `RESTRICT`, `CASCADE`, `SET NULL`, `SET DEFAULT`) and `[NOT] DEFERRABLE` / `INITIALLY ..`. The referenced table must be in the same file; a schema qualifier (`REFERENCES public.t`) must match its `CREATE TABLE`. Without a column list, a key references the primary key.
- **`DEFAULT`, `CHECK`, `GENERATED .. AS IDENTITY` and `GENERATED ALWAYS AS (..) STORED`**, and **`WITH (..)` table options**. Their text is kept as written and written back verbatim: comments are dropped and whitespace is collapsed, nothing else changes.

The generator writes foreign keys to `<database>-foreign-keys.sql`; everything else stays in `CREATE TABLE`. Errors:

| Code | |
|---|---|
| SHML_061 | a statement that is neither modelled nor pg_dump preamble (see below) |
| SHML_062 | unknown table in `REFERENCES`, `ALTER TABLE .. ADD` or `CREATE INDEX` |
| SHML_063 | unknown column in a key, a foreign key or an index |
| SHML_064 | a foreign key's column count differs from the referenced one's (or from the referenced primary key's) |
| SHML_065 | a foreign key references columns that are neither the primary key nor a `UNIQUE`; or omits them, and the table has no primary key |
| SHML_067 | more than one `PRIMARY KEY` on a table |
| SHML_068 | `DEFAULT nextval(..)`: sequences are not modelled; use `SERIAL` / `BIGSERIAL` or `GENERATED .. AS IDENTITY` |

The schema's foreign keys are also checked against the layout, by the rules Citus applies (measured on Citus 14.2):

| Code | |
|---|---|
| SHML_069 | a foreign key between two hash-sharded tables does not pair the two shard keys |
| SHML_070 | it does, but the tables are not colocated |
| SHML_071 | a foreign key from a replicated table to a hash-sharded one |
| SHML_072 | a foreign key whose columns include the shard key has `ON DELETE SET NULL` / `SET DEFAULT`, or `ON UPDATE SET NULL` / `SET DEFAULT` / `CASCADE` (every other action is accepted, and every action on a key that leaves the shard key out) |
| SHML_073 | (warning) a foreign key pairs the shard keys of two colocated tables, but the model has no `belongs_to .. via` for it; the message names the declaration to add |
| SHML_074 | the shard key is a stored generated column (`GENERATED ALWAYS AS (..) STORED`); an identity column is fine |
| SHML_077 | a unique index with an expression or a `WHERE` does not have the shard key among its plain columns (Citus rejects it at distribution; an expression over the shard key does not count). A plain unique index is a unique key, checked by SHML_024 |

A hash-sharded table may reference a replicated one. A foreign key from or to a table the model does not shard is not checked: Citus accepts one from a local table to a distributed one, but enforces it only on the local side.

Verbatim text is not checked beyond `nextval`, and can fail on deploy although the schema validated: a function, type or collation the schema does not create cannot be told from a built-in without a database. PostgreSQL 18's `VIRTUAL` generated columns are a syntax error. `bench/integration/fk/` deploys foreign keys of every allowed kind and verbatim constraints to the bench cluster and checks that each is enforced.

### pg_dump files as `.msql` schemas

A `pg_dump --schema-only` file can be imported as it is (rename or link it to `.msql`). Besides `CREATE TABLE`, a schema file may hold:

- **`ALTER TABLE [IF EXISTS] [ONLY] t ADD [CONSTRAINT n] PRIMARY KEY | UNIQUE | FOREIGN KEY | CHECK ..`**, where pg_dump declares every key. Such a key belongs to its table exactly as if it were declared inline: the validators see it, and the generator writes it inline in `CREATE TABLE` (a foreign key in `<database>-foreign-keys.sql`).
- **`CREATE [UNIQUE] INDEX [IF NOT EXISTS] [name] ON [ONLY] t [USING method] (..)`**, with columns or `(expressions)`, operator classes and ordering, and `INCLUDE (..)`, `WITH (..)` and `WHERE (..)` (the predicate parenthesized, as pg_dump writes it); written back after the tables. A unique index on plain columns without a `WHERE` is a unique key, for SHML_024 and for foreign keys.
- **`CREATE SCHEMA [IF NOT EXISTS] s`**, written back as `CREATE SCHEMA IF NOT EXISTS s`.
- **The pg_dump preamble**, recognised one kind at a time and left out of the generated schema: `SET`, `SELECT pg_catalog.set_config(..)`, `ALTER .. OWNER TO`, the psql `\restrict` / `\unrestrict` lines, `GRANT`, `REVOKE`, `COMMENT ON`, and `DROP TABLE [IF EXISTS]`. Each file gets one INFO issue counting them (SHML_075). The exception is a non-empty `search_path` (`SET search_path = s`, or `set_config('search_path', 's', ..)`): it is not written back either, but it decides where unqualified tables land, so it is a warning (SHML_076); qualify the tables, or apply the generated files with that `search_path`. pg_dump's own empty `search_path` (it qualifies every name) is preamble.

Any other statement is an error at its first word (SHML_061), which says what to do with it: functions, triggers, views, materialized views and aggregates belong in a separate migration applied after the generated files; user-defined types, domains, extensions and partitioned tables (`PARTITION BY`, `ATTACH PARTITION`) are not supported; sequences (`CREATE SEQUENCE`, `ALTER SEQUENCE`, a `nextval` default or identity added by `ALTER TABLE`) are the planned follow-up; any other `DROP`, and psql meta-commands other than `\restrict` / `\unrestrict`, are errors too. Nothing is skipped silently. Function bodies may be dollar-quoted with the tags pg_dump writes (`$$`, `$_$`, `$__$`); any other tag (`$body$`) is an error that says so, and the statements after it are still read.

`ALTER TABLE .. ADD` and `CREATE INDEX` name their table like `REFERENCES`: unknown tables are SHML_062 and unknown columns SHML_063. `bench/integration/pgdump/` deploys two pg_dump files of the corpus (`banking-pg_dump`, `pgbench`) through schema, distribution and foreign keys, and checks the keys in the catalog and that each foreign key is enforced.

`bench/integration/test-sql-corpus.sh` (CI) checks that the generated schema loses nothing. For each clean corpus file (all but Pagila) it applies the original file to one fresh database and the generated schema and foreign keys to another, on plain PostgreSQL 17, and compares the catalogs fact by fact: columns (position, type, NOT NULL, default, identity, generated), constraints, indexes, table options, comments and privileges. Any difference not listed with its reason in `bench/integration/sql-corpus/allowlist.tsv` fails; today the list is empty, all five catalogs are equal.

### Table relationships vs. foreign keys

The `has_many`, `has_one`, and `belongs_to` relationship declarations are **application-level semantic relationships**, not a mirror of SQL foreign key constraints (the schema's own foreign keys are checked separately, see above). The distinction is intentional: sharding decisions are driven by how the application queries data, not just by the schema's referential constraints. A foreign key tells you data is related; a relationship declaration in ShardML tells the validator which tables will be joined at the application layer, enabling colocation warnings when those tables land on different shards.

`belongs_to` names the foreign key column on the subject (`accounts belongs_to customers via customer_id`). When that column is the subject's shard key, it ties the subject's placement to the parent's: a table's *root key* is its shard key, followed through such links. Tables joined by `colocate_with` must share a root key, with the same column type at every link (SHML_026, SHML_027); otherwise their related rows hash to different shards and Citus refuses the join. A colocated table with no `belongs_to` to a sharded table at all gets a warning instead (SHML_030): its key may well reference the other table's, the model just does not say so. Types are compared the way Citus does: `VARCHAR(50)` colocates with `VARCHAR(100)`, but `VARCHAR` with `TEXT` or `INT` with `BIGINT` does not.

### Abstract query patterns

Rather than embedding raw SQL, ShardML uses an abstracted `QueryPattern` structure (type, where clause, sort, group-by, distinct). This keeps models readable and platform-neutral, and gives the validator enough information to reason about scatter-gather risk without a full query planner.

The trade-off is maintainability: query patterns must be kept in sync with application code manually. If a new query is introduced in the application that doesn't match any declared pattern, ShardML has no way to detect it. This is a known limitation — the model reflects *intended* access patterns at design time, not a runtime contract. A future direction would be a static analysis pass over application code to infer and compare against declared patterns.

---

## Building

Requires Java 21 and Maven. The build regenerates the parsers from the grammars, compiles everything and runs the tests:

```sh
mvn clean install
```

On macOS, if `java` on your `PATH` is not 21, set `JAVA_HOME=$(/usr/libexec/java_home -v 21)` first.

---

## Running the Examples

`mvn install` produces a self-contained jar at `shardml.cli/target/shardml.cli-<version>-cli.jar`:

```sh
java -jar shardml.cli/target/*-cli.jar validate examples/banking.shardml
java -jar shardml.cli/target/*-cli.jar generate examples/banking.shardml [-o <dir>]
java -jar shardml.cli/target/*-cli.jar placement examples/banking.shardml
```

`validate` prints diagnostics as `file:line: severity: message [SHML_0xx]` and exits 1 if the model has errors. `generate` validates first, then writes the output files to `src-gen/` next to the model (or to `-o`). `placement` validates first, then prints the node of every shard.

Every command also validates the `.msql` schema the model (or workload) imports, and reports its issues under the `.msql` file's name, e.g. `banking.msql:12: error: ...`. A schema error counts like a model error: the command exits 1 and `generate` writes nothing. `generate` also refuses a column whose type did not parse.

> **Fixed after v2.1.0:** v2.0.0 and v2.1.0 validated only the `.shardml` file. A schema with a column type the grammar does not know (e.g. `code CHAR(2) NOT NULL`) passed `validate`, and `generate` wrote that column as `INT`, dropping its `NOT NULL` and the table's key constraints. If you generated with those versions from a schema that has a syntax error, regenerate.

---

## Running the Tests

```sh
mvn test                                                   # all tests
mvn -pl shardml.tests test -Dtest=ShardMLParsingTest       # one class
```

The tests cover all major validators and the generator, including multi-resource scenarios that load the `.msql` schema and the `.shardml` model into one `ResourceSet`.

---

## Development Environment

- Java 21
- Maven
- Xtext 2.42.0

---

## Cost model

`shardml cost` predicts, before anything is deployed, how a workload will behave under a sharding layout, so that
layouts can be compared side by side. It predicts **counts**: how many nodes a transaction involves, how many
shards a statement touches, how rows and work spread over the nodes. It does **not** predict latency or
throughput, and it gives **no composite score**: the metrics are reported next to each other and the trade-off
is the reader's. **The predictions are not yet validated against measured throughput** (that is Phase 4); what
is validated is the routing they rest on (below).

### What it reports

With n declared worker nodes, template probabilities pi_j = weight_j / total weight, and a statement's node set
being the nodes its shard tasks run on:

- **M1, distributed transactions**, two numbers, weighted over templates:
  **2PC** = P[the write statements of a transaction together touch more than one node] (what Citus commits with
  two-phase commit); **multi-node** = P[all its statements together touch more than one node].
- **M2, fan-out**: tasks per executed statement (1 when routed to one shard, the group's bucket count when not),
  its mean, the share of statements with more than one task, and the maximum.
- **M3, data skew**: rows(n) = the rows of the hash-distributed tables whose shards live on n;
  skew = max_n rows(n) / mean_n rows(n), over every declared node (reference tables are listed, not counted).
- **M4, load**: L(n) = sum_j pi_j * E[shard relations on n that template j's statements touch], in unweighted
  shard-relation touches per transaction; the **bottleneck** max_n L(n), and the **load skew** max / mean.
- **Repartition joins** (joins not on the distribution columns of one colocation group): flagged with their
  template and weight, and left out of M1, M2 and M4; default Citus refuses them.

Everything is computed exactly from per-value distributions (never by sampling), within a step budget.

### The workload language

A `.workload` imports the same `.msql` as the layouts, so one workload is evaluated against many layouts:

```
import "banking.msql"
workload banking {
    param s = 1.0                                         // overridable: --set s=1.2, --sweep s=1.0,1.2
    domain customer { size: 10000 }                       // values 1..size
    domain account  { size: 1000000 within customer zipf($s) }   // children per parent ~ zipf
    table accounts { rows: per account  id: account  customer_id: account.customer }
    transaction transfer weight 50 {                      // weight / total = the template's probability
        let src = account uniform                         // uniform or zipf(s) draws
        let dst = account uniform distinct from $src      // also: let x = $y, else with p: D dist
        write accounts where customer_id = $src.customer AND id = $src
        repeat 10 { ... }                                 // a body drawn again each time
    }
}
```

Only `column = $var` routes a statement to a shard; `?`, ranges and `OR` mean every shard.

### Assumptions and known limits

- The shard of a key is Citus's own hash (`worker_hash`), reproduced exactly for `SMALLINT`, `INTEGER` /
  `SERIAL` and `BIGINT` / `BIGSERIAL` keys; other key types are rejected.
- Rank r of a distribution is value r, rotated by `--rank-offset k` for top-level domains; a child domain's
  values follow its parent's rank order. Which keys are hot, and so which nodes, depends on k.
- Load counts unweighted shard-relation touches.
- A reference-table read adds 1/n to every worker (Citus actually plans a standalone one on the coordinator); a
  reference-table write touches every worker (it commits on all of them).
- Only hash-distributed and reference tables are modelled: a statement on a local (coordinator-only) table is
  rejected.

### Example: banking, by customer vs by account

`examples/cost/` (10,000 customers, 1,000,000 accounts, accounts per customer ~ zipf(s)):

```
$ java -jar shardml.cli/target/shardml.cli-1.0.0-SNAPSHOT-cli.jar cost examples/cost/banking.workload \
      examples/cost/banking-by-customer.shardml examples/cost/banking-by-account.shardml
shardml cost: 2 layouts x banking
params: s=1, overview_w=2, history_w=48, transfer_w=50; rank offset 0

metric             banking_by_customer  banking_by_account
distributed_2pc    0.330080             0.333010
multi_node         0.330080             0.353010
fan_out_mean       1.000000             1.244094
multi_shard_share  0.000000             0.007874
fan_out_max        1                    32
data_skew          1.123992             1.032734
data_skew_node     w3                   w3
load_skew          1.122335             1.032392
load_skew_node     w3                   w3
bottleneck         0.950244             1.087453
bottleneck_node    w3                   w3
total_touches      2.540000             3.160000
repartition_joins  0                    0
```

Sharding by customer does less work (2.54 vs 3.16 touches per transaction) and keeps every statement on one
shard, but concentrates rows and load on the nodes of the customers who own the most accounts. As s grows, its
bottleneck rises past the by-account layout's: at rank offset 0 between s = 1.25 and 1.3 (1.277 by linear
interpolation between sweep points). Over the rank offsets 0, 1, 2, 3, 5, 8, 13 and 21, that crossover lies
between s = 0.946 and 1.380 (median 1.120), and at offset 13 there is none up to s = 1.4. In the TPC-C example
(by warehouse vs stock by item, over the remote-warehouse ratio) the predicted 2PC curves do **not** cross: by
warehouse stays at or below stock-by-item up to a fully remote workload, while stock-by-item has the lower
skew and bottleneck throughout. Details and all sweep outputs: `examples/cost/README.md`.

### Reproducing

```sh
mvn -B clean install                               # about 3 minutes, all tests
examples/cost/regenerate.sh                        # every example output, about a minute
java -jar shardml.cli/target/*-cli.jar cost --sweep s=0,0.5,0.8,1.0,1.2,1.25,1.3,1.4 \
    examples/cost/banking.workload examples/cost/banking-by-*.shardml       # about 3 seconds
```

Options: `--json`, `--csv` (comparisons and sweeps), `--set name=value`, `--rank-offset k`,
`--sweep name=v1,..` and `--sweep rank-offset=k1,..`, `--compare`. Every file is validated first (exit 1 on an
error); an error between the files (a layout without nodes, a schema mismatch, an unknown param) exits 1 with
`shardml cost: error: ...`; a usage error exits 2.

### Checking the model's routing against Citus

`shardml route <model.shardml> <workload>` prints every workload statement as SQL for sampled bindings (the
hottest values, the smallest and largest, seeded draws) with the cost model's prediction: task count, nodes, or a
repartition join. `bench/integration/test-cost-routing.sh` (CI) deploys each example layout on the bench
cluster and compares the predictions with `EXPLAIN (FORMAT JSON)`, and 10,000 keys per key type with
`get_shard_id_for_distribution_column`. Every bucket, node and task count matches; the differences are the two
reference-table assumptions above, listed in `bench/integration/cost-routing/allowlist.tsv`: Citus plans a
standalone reference read on the coordinator, and `EXPLAIN` shows a reference write as one coordinator task,
not the replicated writes on every worker.

### Validation driver (Phase 4, in progress)

`shardml.bench` replays a workload against a Citus cluster exactly as the cost model defines it, to measure
what the model predicts (`docs/phase4-validation-plan.md`). It draws every binding with the model's own code
(`io.shardml.cost.Sampler`: the same pmf, rank offset, projections, `distinct from`, `else with` and `repeat`) and
sends the SQL `shardml route` prints, with placeholders (`io.shardml.cost.StatementSql`): reads as `SELECT`,
writes as an `UPDATE` of a payload column to its own value, so the data the model describes never changes; a
workload with a `delete` is refused.

```sh
J=shardml.bench/target/shardml.bench-1.0.0-SNAPSHOT-driver.jar
java -jar $J verify-distributions examples/cost/banking.workload examples/cost/banking-by-*.shardml
java -jar $J run --mode closed --clients 32 --targets worker1:5432,worker2:5432,worker3:5432 \
    examples/cost/banking.workload examples/cost/banking-by-customer.shardml
java -jar $J run --mode open --rate 500 --targets ... examples/cost/banking.workload examples/cost/banking-by-account.shardml
```

`verify-distributions` compares 10^6 draws per transaction with the model's exact marginals (chi-square,
Bonferroni over all tests at a family alpha of 0.001, no cluster). `run` uses one connection per client
(autocommit off, prepared statements; client i connects to target i mod n), a warm-up and a measured window
(default 120 s and 300 s), HdrHistogram latencies per transaction and overall, and counts serialization
failures, deadlocks, timeouts and connection errors (no retries unless `--retries K`). The open loop times
each transaction from its intended start in a fixed Poisson schedule and reports the schedule lag. Each run
writes `result.json` and `manifest.json` (host, JVM, driver settings, seeds, file hashes, the model's report,
git commit) to `bench-runs/<workload>/<layout>/<entry>/<mode>/<params>/seed-<s>-rep-<r>/`.
`driver load --targets <coordinator> <workload> <model>` fills a deployed layout with the rows the model
counts (`RowCounts.forEachRowGroup`, the rank offset and `--set` applied), with `COPY` through the coordinator on
`--jobs` connections, then checks that every node holds exactly the model's data-skew rows per table (`count(*)`
of every shard placement, summed per node); any difference fails. It also looks up up to 1,000 keys per hash
table, sampled evenly over the generated rows, with `get_shard_id_for_distribution_column` (Citus's bucket and
node must be the model's), and reports the `COPY` rate in rows/s and the bytes per table and node
(`pg_total_relation_size` of every placement). A mapped column takes its key path's value; a
`unique` column, or a key column the workload does not map, the row's number; any other column a constant of its
type. `bench/integration/test-loader.sh` runs this check on scaled-down workloads.
Client i's templates and values depend only on the workload, the seed and i, so two layouts replay the same
transactions. `bench/driver-smoke/run.sh` checks that it runs on the local bench cluster; its numbers are not
measurements.

## Limitations

- The SQL grammar supports a subset of DDL: `CREATE TABLE`, `ALTER TABLE .. ADD` constraint, `CREATE [UNIQUE] INDEX`, `CREATE SCHEMA` and the pg_dump preamble. Sequences, user-defined types, partitions, functions, triggers and views are errors (SHML_061), and other `ALTER TABLE` actions (`ALTER COLUMN`, `ADD COLUMN`, ..) are not supported.
- `via` is trusted. `X belongs_to Y via k`, with `k` the shard key of `X`, is taken to mean that `k` references the shard key of `Y`. Nothing checks which column `k` really references, so a model that names the wrong column still validates; only the type check (SHML_027) and the examples' probe joins against a real cluster catch it.
- Citus is the only target; Vitess is not supported.
- Query patterns are declared manually and must be kept in sync with application code.
- The cost model (in progress, `io.shardml.cost`) models hash-distributed and reference tables only. A workload statement on a table the layout does not distribute (a local, coordinator-only table) is rejected, not modelled. Shard keys must be `SMALLINT`, `INTEGER`/`SERIAL` or `BIGINT`/`BIGSERIAL`, the types whose Citus hash it reproduces.
