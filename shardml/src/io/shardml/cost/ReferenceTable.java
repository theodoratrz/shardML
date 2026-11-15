package io.shardml.cost;

/* A replicated table: a Citus reference table, a copy on every node. */
public record ReferenceTable(String table, String qualified) implements TableLayout {
}
