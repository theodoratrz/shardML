package io.shardml.validation;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.xtext.validation.Check;

import io.shardml.schema.SqlNames;
import io.shardml.sql.Schema;
import io.shardml.sql.SqlPackage;
import io.shardml.sql.Table;

/* Checks on a .msql schema beyond its syntax (SHML_061 onward, Plan-sql section 7). The CLI
 * reports them with the schema's own file and line (ImportedSchemaValidation). */
public class SQLValidator extends AbstractSQLValidator {

	public static final String DUPLICATE_TABLE = "SHML_066";

	// ShardML and workloads refer to a table by its unqualified name, so two tables of one name
	// would link to whichever comes first: also across schemas (a.t and b.t), for now
	@Check
	public void checkDuplicateTable(Schema schema) {
		Map<String, Table> seen = new HashMap<>();
		for (Table table : schema.getTables()) {
			if (table.getName() == null)
				continue;
			Table first = seen.putIfAbsent(table.getName(), table);
			if (first == null)
				continue;
			boolean otherSchema = !java.util.Objects.equals(first.getSchemaName(), table.getSchemaName());
			error("Duplicate table " + SqlNames.qualified(table) + ": " + (otherSchema
					? SqlNames.qualified(first) + " has the same name in another schema. ShardML refers to tables by their name "
							+ "without the schema, so names must be unique in a file."
					: "it is already declared."), table, SqlPackage.Literals.TABLE__NAME, DUPLICATE_TABLE);
		}
	}
}
