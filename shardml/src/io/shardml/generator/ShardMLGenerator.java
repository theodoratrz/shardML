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
import io.shardml.scoping.ImportedSchema;
import io.shardml.sql.Column;
import io.shardml.sql.Constraint;
import io.shardml.sql.NotNull;
import io.shardml.sql.PrimaryAnnotation;
import io.shardml.sql.Schema;
import io.shardml.sql.Table;
import io.shardml.sql.UniqueAnnotation;

/*Produces 3 output files per model, all for Citus:
 * 1. A json sharding config
 * 2. A SQL schema script with the imported tables
 * 3. A SQL distribution script
 * Citus (postgres) is the only supported target; the validator rejects
 * mysql models (SHML_025), so they never reach the generator.
 */
public class ShardMLGenerator extends AbstractGenerator {

	// generate all three output files
	@Override
	public void doGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
		Model model = (Model) resource.getContents().get(0);
		Database database = model.getDb();
		if (database.getType() != DatabaseType.POSTGRES)
			return;
		fsa.generateFile(database.getName() + "-sharding.json", PostgresJSON(database));
		fsa.generateFile(database.getName() + "-schema.sql", schemaDefinitions(ImportedSchema.of(model)));
		fsa.generateFile(database.getName() + "-distribution.sql", distributionDefinitions(database));
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

	// generates CREATE TABLE statements for every table of the imported schema,
	// so the schema and distribution scripts together deploy to an empty database.
	public CharSequence schemaDefinitions(Schema schema) {
		StringBuilder out = new StringBuilder();
		if (schema == null)
			return out;
		for (Table table : schema.getTables()) {
			if (out.length() > 0)
				out.append("\n");
			out.append("CREATE TABLE ").append(table.getName()).append(" (\n");
			for (int i = 0; i < table.getColumns().size(); i++) {
				Column column = table.getColumns().get(i);
				out.append("    ").append(column.getName()).append(" ").append(columnType(column));
				for (Constraint constraint : column.getConstraint())
					out.append(" ").append(constraintSQL(constraint));
				out.append(i < table.getColumns().size() - 1 ? ",\n" : "\n");
			}
			out.append(");\n");
		}
		return out;
	}

	private static String columnType(Column column) {
		// simpleType defaults to INT when unset, so the varchar check must come first
		if (column.getVarchar() != null)
			return "VARCHAR(" + column.getVarchar().getSize() + ")";
		return column.getSimpleType().getLiteral();
	}

	private static String constraintSQL(Constraint constraint) {
		if (constraint instanceof PrimaryAnnotation)
			return "PRIMARY KEY";
		if (constraint instanceof UniqueAnnotation)
			return "UNIQUE";
		if (constraint instanceof NotNull)
			return "NOT NULL";
		throw new IllegalArgumentException("Unknown constraint: " + constraint.eClass().getName());
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
