# ShardML

A domain-specific language (DSL) for planning, validating, and generating database sharding configurations for PostgreSQL/Citus.

Built with **Xtext**; the scoping, validation and generation code is plain Java.

---

## Overview

Most large-scale applications banks, social platforms, logistics systems, rely on horizontal partitioning to handle growing data volumes. Sharding decisions are typically expressed directly in platform-specific configuration files, with little support for early validation. Errors are usually caught at deployment, when they are far more costly to fix.

ShardML addresses the gap between sharding intent and deployment correctness. Engineers declare their distribution strategy, query access patterns, and table relationships in a single model. The language validates the model against the imported SQL schema before anything is deployed, and generates deployment-ready Citus configuration from one source.

---

## Features

- **Declarative syntax**: describe what to shard and how, not low-level middleware configuration
- **29 validators** covering structural correctness, colocation consistency, performance anti-patterns, and query pattern analysis
- **Policy-aware query analysis**: route rules support `allow`, `warn`, `deny` to control strictness per table
- **Citus code generation**: produces the schema and distribution SQL, plus a JSON summary, from a single model
- **Cross-schema import**: reference an existing `.msql` SQL schema file; all table and column references are resolved and validated against it
- **Command-line tool**: `shardml validate` and `shardml generate`, with `file:line` diagnostics and a non-zero exit code on model errors

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
| `<database>-distribution.sql` | Citus SQL commands (`create_distributed_table`, `create_reference_table`) |

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

shardml.tests/                         # JUnit 5 test suite (40 tests)
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
```

`validate` prints diagnostics as `file:line: severity: message [SHML_0xx]` and exits 1 if the model has errors. `generate` validates first, then writes the output files to `src-gen/` next to the model (or to `-o`).

---

## Running the Tests

```sh
mvn test                                                   # all 40 tests
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

- The SQL grammar supports a subset of DDL (no ALTER, no indices, no composite primary keys).
- Citus is the only target; Vitess is not supported.
- Query patterns are declared manually and must be kept in sync with application code.
