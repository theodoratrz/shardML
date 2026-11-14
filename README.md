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
- **39 validators** (SHML_001 to SHML_039) covering structural correctness, colocation consistency, performance anti-patterns, and query pattern analysis
- **Policy-aware query analysis**: route rules support `allow`, `warn`, `deny` to control strictness per table
- **Citus code generation**: produces the schema and distribution SQL, plus a JSON summary, from a single model
- **Cross-schema import**: reference an existing `.msql` SQL schema file; all table and column references are resolved and validated against it
- **Shard placement**: declare the cluster's worker nodes and which nodes each colocation group goes to; the generated placement script moves the shards there, safely re-runnable
- **Command-line tool**: `shardml validate`, `shardml generate` and `shardml placement`, with `file:line` diagnostics and a non-zero exit code on model errors

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
| `<database>-schema.sql` | `CREATE TABLE` statements for the imported schema (apply before the distribution script) |
| `<database>-distribution.sql` | Citus SQL commands (`create_distributed_table`, `create_reference_table`); every root (a table without `colocate_with`) gets `colocate_with => 'none'`, so it starts its own Citus colocation group instead of joining another table of the same shape |
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

shardml.tests/                         # JUnit 5 test suite (100 tests)
shardml.cli/                           # Command-line front end

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
- A keyword can be a table or column name exactly when PostgreSQL allows it bare: `key`, `text`, `date`, `int`, ... are names, while reserved words (`table`, `unique`, `primary`, `not`, `null`, `create`, `with`) must be quoted, e.g. `"unique" BIGINT`. The list follows PostgreSQL 17's `pg_get_keywords()`.
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

### Table relationships vs. foreign keys

The `has_many`, `has_one`, and `belongs_to` relationship declarations are **application-level semantic relationships**, not a mirror of SQL foreign key constraints. The distinction is intentional: sharding decisions are driven by how the application queries data, not just by the schema's referential constraints. A foreign key tells you data is related; a relationship declaration in ShardML tells the validator which tables will be joined at the application layer, enabling colocation warnings when those tables land on different shards.

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
mvn test                                                   # all 100 tests
mvn -pl shardml.tests test -Dtest=ShardMLParsingTest       # one class
```

The tests cover all major validators and the generator, including multi-resource scenarios that load the `.msql` schema and the `.shardml` model into one `ResourceSet`.

---

## Development Environment

- Java 21
- Maven
- Xtext 2.42.0

---

## Limitations

- The SQL grammar supports a subset of DDL (no ALTER, no indices, no FOREIGN KEY constraints).
- `via` is trusted. `X belongs_to Y via k`, with `k` the shard key of `X`, is taken to mean that `k` references the shard key of `Y`. Nothing checks which column `k` really references, so a model that names the wrong column still validates; only the type check (SHML_027) and the examples' probe joins against a real cluster catch it.
- Citus is the only target; Vitess is not supported.
- Query patterns are declared manually and must be kept in sync with application code.
