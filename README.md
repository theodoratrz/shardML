# ShardML

A domain-specific language (DSL) for planning, validating, and generating database sharding configurations targeting PostgreSQL/Citus and MySQL/Vitess.

Built with **Xtext** and **Xtend**.

---

## Overview

Most large-scale applications banks, social platforms, logistics systems, rely on horizontal partitioning to handle growing data volumes. Sharding decisions are typically expressed directly in platform-specific configuration files, with little support for early validation. Errors are usually caught at deployment, when they are far more costly to fix.

ShardML addresses the gap between sharding intent and deployment correctness. Engineers declare their distribution strategy, query access patterns, and table relationships in a single model. The language validates the model against the imported SQL schema at edit time, directly in the IDE, and generates deployment-ready configurations for both Citus and Vitess from one source.

---

## Features

- **Declarative syntax**: describe what to shard and how, not low-level middleware configuration
- **25 validators** covering structural correctness, colocation consistency, performance anti-patterns, and query pattern analysis
- **Policy-aware query analysis**: route rules support `allow`, `warn`, `deny` to control strictness per table
- **Multi-platform code generation**: produces Citus JSON + SQL and Vitess VSchema JSON from a single model
- **Cross-schema import**: reference an existing `.msql` SQL schema file; all table and column references are resolved and validated against it
- **Eclipse IDE integration**: errors and warnings appear inline as you type

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
uk.ac.kcl.inf.mdd1.ShardML/           # Core language plugin
  src/
    uk/ac/kcl/inf/mdd1/
      SQL.xtext                        # Lightweight DDL parser (.msql schema files)
      ShardML.xtext                    # ShardML language grammar (.shardml)
      scoping/
        ShardMLScopeProvider.xtend     # Cross-resource reference resolution
      validation/
        ShardMLValidator.xtend         # 25 validation rules
      generator/
        ShardMLGenerator.xtend         # Citus/Vitess output generation
  src-gen/                             # Xtext-generated parser infrastructure

uk.ac.kcl.inf.mdd1.ShardML.ide/       # IDE content-assist support
uk.ac.kcl.inf.mdd1.ShardML.ui/        # Eclipse editor integration
uk.ac.kcl.inf.mdd1.ShardML.tests/     # JUnit test suite (32 tests)
uk.ac.kcl.inf.mdd1.ShardML.ui.tests/  # UI-level tests

TestShard/                             # Example project
  banking.msql / banking.shardml       # PostgreSQL/Citus scenario
  social.msql  / social.shardml        # MySQL/Vitess scenario
```

---

## Design Notes

### Cross-file scoping

ShardML uses a custom `ImportURI`-style import rather than Xtext's built-in global scope mechanism. When a `.shardml` file declares `import "schema.msql"`, the `ShardMLScopeProvider` manually resolves the URI relative to the importing resource, loads the target resource from the `ResourceSet`, and builds scopes from the resulting EMF model objects.

This was necessary because Xtext's default `ImportedNamespaceAwareLocalScopeProvider` resolves names by qualified ID, it cannot navigate a cross-metamodel reference to `sql::Table` objects defined in a separate grammar. The custom provider instead builds `IScope` instances directly from the imported `Schema`'s `Table` and `Column` lists, which makes cross-grammar references transparent to the validator and IDE.

A notable detail: shard key references (`key: column_name`) are scoped to the *specific table* declared in that `ShardDecl`, not the full column namespace. This prevents false name collisions when multiple tables share a column name (e.g. `id`), a common case that Xtext's flat scoping would have gotten wrong.

### Table relationships vs. foreign keys

The `has_many`, `has_one`, and `belongs_to` relationship declarations are **application-level semantic relationships**, not a mirror of SQL foreign key constraints. The distinction is intentional: sharding decisions are driven by how the application queries data, not just by the schema's referential constraints. A foreign key tells you data is related; a relationship declaration in ShardML tells the validator which tables will be joined at the application layer, enabling colocation warnings when those tables land on different shards.

### Abstract query patterns

Rather than embedding raw SQL, ShardML uses an abstracted `QueryPattern` structure (type, where clause, sort, group-by, distinct). This keeps models readable and platform-neutral, and gives the validator enough information to reason about scatter-gather risk without a full query planner.

The trade-off is maintainability: query patterns must be kept in sync with application code manually. If a new query is introduced in the application that doesn't match any declared pattern, ShardML has no way to detect it. This is a known limitation — the model reflects *intended* access patterns at design time, not a runtime contract. A future direction would be a static analysis pass over application code to infer and compare against declared patterns.

---

## Running the Examples

1. Clone the repository and import all projects into Eclipse (File → Import → Existing Projects into Workspace)
2. Right-click `uk.ac.kcl.inf.mdd1.ShardML` → **Run As** → **Eclipse Application**
3. In the runtime Eclipse, import `TestShard` as an existing project
4. Open `banking.shardml` or `social.shardml` — validators run automatically as you type
5. Save (Ctrl+S) to trigger code generation; output files appear in `TestShard/src-gen/`

---

## Running the Tests

Right-click `uk.ac.kcl.inf.mdd1.ShardML.tests` → **Run As** → **JUnit Test**

32 tests covering all major validators, including multi-resource scenarios requiring a full `ResourceSet`.

---

## Development Environment

- Eclipse DSL Edition 4.39.0 (2026-03)
- Java 21 (Eclipse Adoptium)
- Xtext / Xtend 2.42.0
- macOS aarch64

---

## Limitations

- The SQL grammar supports a subset of DDL (no ALTER, no indices, no composite primary keys).
- Vitess distribution SQL is intentionally omitted, Vitess uses VSchema JSON for distribution rather than SQL DDL commands.
- Query patterns are declared manually and must be kept in sync with application code.
