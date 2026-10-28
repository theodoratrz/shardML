package io.shardml.generator;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;

import io.shardml.shardML.Database;
import io.shardml.shardML.DatabaseType;
import io.shardml.shardML.Model;
import io.shardml.shardML.ShardDecl;
import io.shardml.shardML.Strategy;

/*Produces 2 output files per model:
 * 1. A platform specific json sharding config
 * 2. A SQL distribution script (only for Citus)
 * The generator dispatches on database type to avoid vendor lock in.
 */
public class ShardMLGenerator extends AbstractGenerator {

	// generate both output files
	@Override
	public void doGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
		Model model = (Model) resource.getContents().get(0);
		Database database = model.getDb();
		fsa.generateFile(database.getName() + "-sharding.json", generateJson(database));
		fsa.generateFile(database.getName() + "-distribution.sql", generateSQL(database));
	}

	public CharSequence generateJson(Database db) {
		if (db.getType() == DatabaseType.POSTGRES)
			return PostgresJSON(db);
		else
			return MySQLJSON(db);
	}

	private static boolean isHashed(ShardDecl shard) {
		return shard.getStrategy() == Strategy.HASH || shard.getStrategy() == Strategy.CONS_HASH;
	}

	// Generates a Citus-comoatible json configuration
	// hash-sharded tables include distrbution_column, shard_count and colocate_with field.
	// replicated tables use distribution_type: reference
	public CharSequence PostgresJSON(Database db) {
		StringBuilder out = new StringBuilder();
		out.append("{\n");
		out.append("    \"database\": \"").append(db.getName()).append("\",\n");
		out.append("    \"target\": \"citus\",\n");
		out.append("    \"tables\": [\n");
		boolean first = true;
		for (ShardDecl shard : db.getShard_decl()) {
			if (!first)
				out.append("        },\n");
			first = false;
			out.append("        {\n");
			out.append("            \"name\": \"").append(shard.getEntity().getName()).append("\",\n");
			if (isHashed(shard)) {
				out.append("            \"distribution_column\": \"").append(shard.getId().getName()).append("\",\n");
				out.append("            \"shard_count\": ").append(shard.getBuckets()).append(",\n");
				out.append("            \"distribution_type\": \"hash\"\n");
				if (shard.getColocate() != null)
					out.append("            ,\"colocate_with\": \"").append(shard.getColocate().getName()).append("\"\n");
			} else {
				out.append("            \"distribution_type\": \"reference\"\n");
			}
		}
		if (!first)
			out.append("        }\n");
		out.append("    ]\n");
		out.append("}\n");
		return out;
	}

	// Generates a Vitess-compatible json configuration
	// uses vindex and vindex-type fields following Vitess VSchema conventions
	// colocate_with field is optional and is included when a colocation directive is presented
	public CharSequence MySQLJSON(Database db) {
		StringBuilder out = new StringBuilder();
		out.append("{\n");
		out.append("    \"database\": \"").append(db.getName()).append("\",\n");
		out.append("    \"target\": \"vitess\",\n");
		out.append("    \"keyspace\": \"").append(db.getName()).append("\",\n");
		out.append("    \"tables\": [\n");
		boolean first = true;
		for (ShardDecl shard : db.getShard_decl()) {
			if (!first)
				out.append("        },\n");
			first = false;
			out.append("        {\n");
			out.append("            \"name\": \"").append(shard.getEntity().getName()).append("\",\n");
			if (isHashed(shard)) {
				out.append("            \"vindex\": \"").append(shard.getId().getName()).append("\",\n");
				out.append("            \"vindex_type\": \"hash\",\n");
				out.append("            \"buckets\": ").append(shard.getBuckets()).append("\n");
				if (shard.getColocate() != null)
					out.append("            ,\"colocate_with\": \"").append(shard.getColocate().getName()).append("\"\n");
			} else {
				out.append("            \"vindex_type\": \"reference\"\n");
			}
		}
		if (!first)
			out.append("        }\n");
		out.append("    ]\n");
		out.append("}\n");
		return out;
	}

	// generates SQL distribution script.
	public CharSequence generateSQL(Database db) {
		if (db.getType() == DatabaseType.POSTGRES)
			return distributionDefinitions(db);
		return "";
	}

	// generates Citus distribution commands for each sharded table.
	// hash-sharded: create_distributed_table()
	// replicated: create_reference_table()
	public CharSequence distributionDefinitions(Database db) {
		StringBuilder out = new StringBuilder();
		for (ShardDecl shard : db.getShard_decl()) {
			if (isHashed(shard)) {
				out.append("SELECT create_distributed_table('").append(shard.getEntity().getName())
						.append("', '").append(shard.getId().getName()).append("' ");
				if (shard.getColocate() != null)
					out.append(" ,colocate_with => '").append(shard.getColocate().getName()).append("' ");
				out.append(");\n");
			} else {
				out.append("SELECT create_reference_table('").append(shard.getEntity().getName()).append("');\n");
			}
		}
		return out;
	}
}
