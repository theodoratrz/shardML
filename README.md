# ShardML

A domain-specific language (DSL) for planning, validating, and generating database sharding configurations targeting PostgreSQL/Citus and MySQL/Vitess.

Built with **Xtext** and **Xtend**.

---

## Overview

Most large-scale applications banks, social platforms, logistics systems, rely on horizontal partitioning to handle growing data volumes. Sharding decisions are typically expressed directly in platform-specific configuration files, with little support for early validation. Errors are usually caught at deployment, when they are far more costly to fix.

ShardML addresses the gap between sharding intent and deployment correctness. Engineers declare their distribution strategy, query access patterns, and table relationships in a single model. The language validates the model against the imported SQL schema before anything is deployed, and generates deployment-ready configurations for both Citus and Vitess from one source.

---

## Features

- **Declarative syntax**: describe what to shard and how, not low-level middleware configuration
- **23 validators** covering structural correctness, colocation consistency, performance anti-patterns, and query pattern analysis
- **Policy-aware query analysis**: route rules support `allow`, `warn`, `deny` to control strictness per table
- **Multi-platform code generation**: produces Citus JSON + SQL and Vitess VSchema JSON from a single model
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

    accounts belongs_to customers
}
```

---

## Generated Artefacts

| File | Contents |
|---|---|
| `<database>-sharding.json` | Platform-specific distribution config (Citus or Vitess format) |
| `<database>-distribution.sql` | Citus SQL commands (`create_distributed_table`, `create_reference_table`) |

---

## Supported Target Platforms

| Platform | Underlying DBMS |
|---|---|
| Citus | PostgreSQL |
| Vitess | MySQL |

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
      ShardMLValidator.java            # 23 validation rules
    generator/
      ShardMLGenerator.java            # Citus/Vitess output generation
  src-gen/                             # Xtext-generated parser infrastructure
  model/generated/                     # Generated Ecore metamodels

shardml.tests/                         # JUnit 5 test suite (40 tests)
shardml.cli/                           # Command-line front end

examples/
  banking.msql / banking.shardml       # PostgreSQL/Citus scenario
  social.msql  / social.shardml        # MySQL/Vitess scenario
  src-gen/                             # Generated output for both
```

---

## Design Notes

### Cross-file scoping

ShardML uses a custom `ImportURI`-style import rather than Xtext's built-in global scope mechanism. When a `.shardml` file declares `import "schema.msql"`, the `ShardMLScopeProvider` manually resolves the URI relative to the importing resource, loads the target resource from the `ResourceSet`, and builds scopes from the resulting EMF model objects.

This was necessary because Xtext's default `ImportedNamespaceAwareLocalScopeProvider` resolves names by qualified ID, it cannot navigate a cross-metamodel reference to `sql::Table` objects defined in a separate grammar. The custom provider instead builds `IScope` instances directly from the imported `Schema`'s `Table` and `Column` lists, which makes cross-grammar references transparent to the validator and generator.

A notable detail: shard key references (`key: column_name`) are scoped to the *specific table* declared in that `ShardDecl`, not the full column namespace. This prevents false name collisions when multiple tables share a column name (e.g. `id`), a common case that Xtext's flat scoping would have gotten wrong.

### Table relationships vs. foreign keys

The `has_many`, `has_one`, and `belongs_to` relationship declarations are **application-level semantic relationships**, not a mirror of SQL foreign key constraints. The distinction is intentional: sharding decisions are driven by how the application queries data, not just by the schema's referential constraints. A foreign key tells you data is related; a relationship declaration in ShardML tells the validator which tables will be joined at the application layer, enabling colocation warnings when those tables land on different shards.

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
- Xtext / Xtend 2.42.0

---

## Limitations

- The SQL grammar supports a subset of DDL (no ALTER, no indices, no composite primary keys).
- Vitess distribution SQL is intentionally omitted, Vitess uses VSchema JSON for distribution rather than SQL DDL commands.
- Query patterns are declared manually and must be kept in sync with application code.
