package io.shardml.cost;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.shardml.placement.ColocationGroups;
import io.shardml.placement.Placement;
import io.shardml.placement.PlacementResolver;
import io.shardml.schema.SchemaFacts;
import io.shardml.schema.SchemaFacts.ColumnFacts;
import io.shardml.schema.SchemaFacts.TableFacts;
import io.shardml.schema.SqlNames;
import io.shardml.schema.SqlTypes.SqlType;
import io.shardml.scoping.ImportedSchema;
import io.shardml.shardML.Database;
import io.shardml.shardML.ShardDecl;
import io.shardml.shardML.Strategy;
import io.shardml.sql.Schema;

/* Builds a Layout from a parsed .shardml model. Expects a model without validation errors, as
 * PlacementResolver does. Key columns, their types and the schema are read through SchemaFacts and
 * SqlTypes only (ColumnTypeAccessGuardTest). A valid model has only hash and replicated tables:
 * SHML_029 rejects consistent_hash on postgres. */
public final class Layouts {

	private Layouts() {
	}

	public static Layout of(Database db) {
		if (db.getNodes().isEmpty())
			throw new IllegalArgumentException("Layout '" + db.getName()
					+ "' declares no nodes; the cost model needs a placement. Add node blocks to the model.");
		Schema schema = ImportedSchema.of(db);
		if (schema == null)
			throw new IllegalStateException("Layout '" + db.getName() + "' has no readable .msql import");
		Map<String, TableFacts> facts = SchemaFacts.of(schema);
		String file = schema.eResource() == null ? "its schema" : schema.eResource().getURI().lastSegment();
		List<TableLayout> tables = new ArrayList<>();
		for (ShardDecl shard : db.getShard_decl()) {
			String table = SqlNames.stored(shard.getEntity());
			String qualified = SqlNames.qualified(shard.getEntity());
			if (shard.getStrategy() == Strategy.REPLICATED) {
				tables.add(new ReferenceTable(table, qualified));
				continue;
			}
			if (shard.getStrategy() != Strategy.HASH)
				throw new IllegalStateException("'" + table + "' uses strategy " + shard.getStrategy().getLiteral()
						+ ", which SHML_029 rejects; validate the model first.");
			String key = SqlNames.stored(shard.getId());
			ColumnFacts column = facts.get(table).column(key);
			SqlType type = column.type();
			if (!type.isKnown())
				throw new IllegalArgumentException("Shard key " + table + "." + key + " of '" + db.getName()
						+ "' has a type that did not parse in " + file + "; fix the schema first.");
			KeyType keyType = KeyType.of(type.base())
					.orElseThrow(() -> new IllegalArgumentException("Shard key " + table + "." + key + " of '" + db.getName()
							+ "' is " + type.spelling() + "; the cost model hashes only SMALLINT, INTEGER/SERIAL and "
							+ "BIGINT/BIGSERIAL keys (the types whose Citus hash it reproduces)."));
			ShardDecl root = ColocationGroups.rootOf(db, shard);
			tables.add(new HashTable(table, qualified, key, keyType, SqlNames.stored(root.getEntity()), root.getBuckets()));
		}
		Placement placement = PlacementResolver.resolve(db);
		return new Layout(db.getName(), placement.nodes(), tables, placement, SchemaView.of(facts));
	}
}
