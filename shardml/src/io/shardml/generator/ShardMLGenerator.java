package io.shardml.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;

import io.shardml.placement.ColocationGroup;
import io.shardml.placement.Node;
import io.shardml.placement.Placement;
import io.shardml.placement.PlacementResolver;
import io.shardml.shardML.Database;
import io.shardml.shardML.DatabaseType;
import io.shardml.shardML.Model;
import io.shardml.shardML.ShardDecl;
import io.shardml.shardML.Strategy;
import io.shardml.scoping.ImportedSchema;
import io.shardml.schema.SqlTypes;
import io.shardml.schema.SqlTypes.SqlType;
import io.shardml.sql.Column;
import io.shardml.sql.Constraint;
import io.shardml.sql.NotNull;
import io.shardml.sql.PrimaryAnnotation;
import io.shardml.sql.PrimaryKey;
import io.shardml.sql.Schema;
import io.shardml.sql.Table;
import io.shardml.sql.TableConstraint;
import io.shardml.sql.UniqueAnnotation;
import io.shardml.sql.UniqueKey;

/*Produces 3 output files per model, all for Citus:
 * 1. A json sharding config
 * 2. A SQL schema script with the imported tables
 * 3. A SQL distribution script
 * and, for a model that declares nodes, a fourth:
 * 4. A SQL placement script that moves every shard to the node the model assigns it
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
		Placement placement = PlacementResolver.resolve(database);
		if (!placement.isEmpty())
			fsa.generateFile(database.getName() + "-placement.sql", placementDefinitions(database.getName(), placement));
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
			// table constraints go after the columns, as in most hand-written DDL
			List<String> lines = new ArrayList<>();
			for (Column column : table.getColumns()) {
				StringBuilder line = new StringBuilder(column.getName()).append(" ").append(columnType(table, column));
				for (Constraint constraint : column.getConstraint())
					line.append(" ").append(constraintSQL(constraint));
				lines.add(line.toString());
			}
			for (TableConstraint constraint : table.getConstraints())
				lines.add(tableConstraintSQL(constraint));
			out.append("CREATE TABLE ").append(table.getName()).append(" (\n    ");
			out.append(String.join(",\n    ", lines));
			out.append("\n);\n");
		}
		return out;
	}

	// through SqlTypes: a type that did not parse must stop generation, not come out as the enum's
	// default INT. Validation reports the syntax error first, so this only guards direct callers
	private static String columnType(Table table, Column column) {
		SqlType type = SqlTypes.of(column);
		if (!type.isKnown())
			throw new IllegalStateException("The type of " + table.getName() + "." + column.getName()
					+ " did not parse; fix the .msql before generating.");
		return type.spelling();
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

	private static String tableConstraintSQL(TableConstraint constraint) {
		String columns = constraint.getColumns().stream().map(Column::getName).collect(Collectors.joining(", "));
		if (constraint instanceof PrimaryKey)
			return "PRIMARY KEY (" + columns + ")";
		if (constraint instanceof UniqueKey)
			return "UNIQUE (" + columns + ")";
		throw new IllegalArgumentException("Unknown table constraint: " + constraint.eClass().getName());
	}

	// generates Citus distribution commands for each sharded table.
	// hash-sharded: create_distributed_table()
	// replicated: create_reference_table()
	// shard_count goes on the first table of each colocation group only: Citus rejects it
	// together with colocate_with, and colocated tables take the group's count.
	// Each root also gets colocate_with => 'none': Citus would otherwise join roots of the same
	// shape (shard count, key type) into one colocation group, so that moving a shard of one
	// moves the other's too, and the cluster's colocation would differ from the model's
	public CharSequence distributionDefinitions(Database db) {
		StringBuilder out = new StringBuilder();
		for (ShardDecl shard : db.getShard_decl()) {
			if (isHashed(shard)) {
				out.append("SELECT create_distributed_table('").append(shard.getEntity().getName())
						.append("', '").append(shard.getId().getName()).append("' ");
				if (shard.getColocate() != null)
					out.append(" ,colocate_with => '").append(shard.getColocate().getName()).append("' ");
				else
					out.append(" ,shard_count => ").append(shard.getBuckets()).append(" ,colocate_with => 'none' ");
				out.append(");\n");
			} else {
				out.append("SELECT create_reference_table('").append(shard.getEntity().getName()).append("');\n");
			}
		}
		return out;
	}

	// generates the placement script: a preflight that stops before any move if the cluster
	// does not match the model, then one statement per bucket that moves the root's shard to
	// its target node, taking the colocated shards with it. Shards are found by bucket, the
	// i-th by shardminvalue (a text column, so cast before sorting), never by shard ID.
	// A shard already on its target matches no row, so a re-run moves nothing.
	// Each move is its own statement because Citus moves at most one shard per transaction
	// with logical replication; force_logical fails instead of falling back to blocking writes
	public CharSequence placementDefinitions(String database, Placement placement) {
		StringBuilder out = new StringBuilder();
		out.append("\\set ON_ERROR_STOP on\n");
		out.append("-- Shard placement for ").append(database).append(": moves every shard to the node the model assigns it.\n");
		out.append("-- Apply after the distribution script, with psql and outside a transaction: Citus moves\n");
		out.append("-- one shard per transaction, so every move is a statement of its own. Safe to re-run:\n");
		out.append("-- a shard already on its node is not moved.\n");
		out.append("\n");
		appendPreflight(out, placement);
		for (ColocationGroup group : placement.groups()) {
			out.append("\n");
			out.append("-- ").append(group.root()).append(" group (").append(String.join(", ", group.tables())).append("): ")
					.append(group.buckets()).append(" buckets on ")
					.append(group.targets().stream().map(Node::name).collect(Collectors.joining(", "))).append("\n");
			for (int bucket = 0; bucket < group.buckets(); bucket++) {
				Node target = group.targetOf(bucket);
				String host = sqlString(target.host());
				out.append("-- bucket ").append(bucket).append(" -> ").append(target).append("\n");
				out.append("SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, ").append(host).append(", ")
						.append(target.port()).append(", shard_transfer_mode => 'force_logical')\n");
				out.append("FROM (\n");
				out.append("    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid\n");
				out.append("    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket\n");
				out.append("          FROM pg_dist_shard WHERE logicalrelid = ").append(sqlString(group.root())).append("::regclass) b\n");
				out.append("    JOIN pg_dist_placement p ON p.shardid = b.shardid\n");
				out.append("    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'\n");
				out.append("    WHERE b.bucket = ").append(bucket).append("\n");
				out.append(") s\n");
				out.append("WHERE (s.src_host, s.src_port) <> (").append(host).append(", ").append(target.port())
						.append(") AND s.src_groupid <> 0;\n");
			}
		}
		return out;
	}

	// One DO block with no moves in it. Checks that every declared node is an active primary
	// worker, and per group that Citus colocates exactly the model's tables with the root,
	// with one shard per bucket, each on exactly one node and none on the coordinator
	private static void appendPreflight(StringBuilder out, Placement placement) {
		out.append("-- Preflight: stops the script before any move if the cluster does not match the model\n");
		out.append("DO $preflight$\n");
		out.append("DECLARE\n");
		out.append("    g record;\n");
		out.append("    coloc integer;\n");
		out.append("    shards bigint;\n");
		out.append("    bad text;\n");
		out.append("BEGIN\n");
		out.append("    -- every declared node is an active primary worker, never the coordinator\n");
		out.append("    SELECT string_agg(format('%s (%s:%s)', d.name, d.host, d.port), ', ') INTO bad\n");
		out.append("    FROM (VALUES\n");
		List<String> nodes = new ArrayList<>();
		for (Node node : placement.nodes())
			nodes.add("        (" + sqlString(node.name()) + ", " + sqlString(node.host()) + ", " + node.port() + ")");
		out.append(String.join(",\n", nodes)).append("\n");
		out.append("    ) AS d(name, host, port)\n");
		out.append("    WHERE NOT EXISTS (\n");
		out.append("        SELECT 1 FROM pg_dist_node n\n");
		out.append("        WHERE n.nodename = d.host AND n.nodeport = d.port\n");
		out.append("          AND n.isactive AND n.noderole = 'primary' AND n.groupid <> 0\n");
		out.append("    );\n");
		out.append("    IF bad IS NOT NULL THEN\n");
		out.append("        RAISE EXCEPTION 'placement: not an active primary worker in pg_dist_node: %', bad;\n");
		out.append("    END IF;\n");
		out.append("\n");
		out.append("    FOR g IN SELECT * FROM (VALUES\n");
		List<String> groups = new ArrayList<>();
		for (ColocationGroup group : placement.groups()) {
			String tables = group.tables().stream().map(ShardMLGenerator::sqlString).collect(Collectors.joining(", "));
			groups.add("        (" + sqlString(group.root()) + ", ARRAY[" + tables + "], " + group.buckets() + ")");
		}
		out.append(String.join(",\n", groups)).append("\n");
		out.append("    ) AS v(root, tables, buckets) LOOP\n");
		out.append("        SELECT colocationid INTO coloc FROM pg_dist_partition\n");
		out.append("        WHERE logicalrelid = to_regclass(g.root) AND partmethod = 'h';\n");
		out.append("        IF coloc IS NULL THEN\n");
		out.append("            RAISE EXCEPTION 'placement: % is not a hash-distributed table', g.root;\n");
		out.append("        END IF;\n");
		out.append("\n");
		out.append("        -- moving the root moves its whole Citus colocation group, so it must hold the model's tables and no others\n");
		out.append("        SELECT string_agg(coalesce(m.t, c.logicalrelid::text), ', ') INTO bad\n");
		out.append("        FROM (SELECT t, to_regclass(t) AS rel FROM unnest(g.tables) AS t) m\n");
		out.append("        FULL JOIN (SELECT logicalrelid FROM pg_dist_partition WHERE colocationid = coloc) c ON c.logicalrelid = m.rel\n");
		out.append("        WHERE m.rel IS NULL OR c.logicalrelid IS NULL;\n");
		out.append("        IF bad IS NOT NULL THEN\n");
		out.append("            RAISE EXCEPTION 'placement: the colocation group of % differs from the model in: %', g.root, bad;\n");
		out.append("        END IF;\n");
		out.append("\n");
		out.append("        SELECT count(*) INTO shards FROM pg_dist_shard WHERE logicalrelid = to_regclass(g.root);\n");
		out.append("        IF shards <> g.buckets THEN\n");
		out.append("            RAISE EXCEPTION 'placement: % has % shards, but the model declares % buckets', g.root, shards, g.buckets;\n");
		out.append("        END IF;\n");
		out.append("\n");
		out.append("        SELECT string_agg(s.shardid::text, ', ' ORDER BY s.shardid) INTO bad\n");
		out.append("        FROM pg_dist_shard s JOIN pg_dist_partition d ON d.logicalrelid = s.logicalrelid\n");
		out.append("        WHERE d.colocationid = coloc\n");
		out.append("          AND (SELECT count(*) FROM pg_dist_placement p WHERE p.shardid = s.shardid) <> 1;\n");
		out.append("        IF bad IS NOT NULL THEN\n");
		out.append("            RAISE EXCEPTION 'placement: shards of the % group without exactly one placement: %', g.root, bad;\n");
		out.append("        END IF;\n");
		out.append("\n");
		out.append("        SELECT string_agg(s.shardid::text, ', ' ORDER BY s.shardid) INTO bad\n");
		out.append("        FROM pg_dist_shard s JOIN pg_dist_partition d ON d.logicalrelid = s.logicalrelid\n");
		out.append("        JOIN pg_dist_placement p ON p.shardid = s.shardid\n");
		out.append("        WHERE d.colocationid = coloc AND p.groupid = 0;\n");
		out.append("        IF bad IS NOT NULL THEN\n");
		out.append("            RAISE EXCEPTION 'placement: shards of the % group on the coordinator, which placement does not move: %', g.root, bad;\n");
		out.append("        END IF;\n");
		out.append("    END LOOP;\n");
		out.append("END $preflight$;\n");
	}

	private static String sqlString(String value) {
		return "'" + value.replace("'", "''") + "'";
	}
}
