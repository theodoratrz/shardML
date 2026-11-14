package io.shardml.schema;

import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import io.shardml.sql.Column;
import io.shardml.sql.SqlPackage;
import io.shardml.sql.Table;

/* PostgreSQL's rules for names, in one place: how a name in a .msql file is read (folded or
 * unquoted, see SQLValueConverters) and how the generator writes it back.
 *  - An unquoted name is folded to lower case. A "quoted" name is kept exactly.
 *  - A name is written back quoted if it was quoted in the source (the quoted flag, read from the
 *    parse), or if it could not be read back unquoted: it is not lower-case [a-z_][a-z0-9_$]*, or
 *    it is a PostgreSQL keyword that may not be a bare name (category R or T).
 * RESERVED is PostgreSQL 17's R and T keywords; SqlKeywordTest checks it against the golden
 * taken from pg_get_keywords(). */
public final class SqlNames {

	private SqlNames() {
	}

	private static final Pattern PLAIN = Pattern.compile("[a-z_][a-z0-9_$]*");

	// pg_get_keywords() of PostgreSQL 17, categories R (reserved) and T (reserved, can be a
	// function or type name): words that are names only when quoted
	public static final Set<String> RESERVED = Set.of(
			// R
			"all", "analyse", "analyze", "and", "any", "array", "as", "asc", "asymmetric", "both", "case", "cast", "check",
			"collate", "column", "constraint", "create", "current_catalog", "current_date", "current_role", "current_time",
			"current_timestamp", "current_user", "default", "deferrable", "desc", "distinct", "do", "else", "end", "except",
			"false", "fetch", "for", "foreign", "from", "grant", "group", "having", "in", "initially", "intersect", "into",
			"lateral", "leading", "limit", "localtime", "localtimestamp", "not", "null", "offset", "on", "only", "or",
			"order", "placing", "primary", "references", "returning", "select", "session_user", "some", "symmetric",
			"system_user", "table", "then", "to", "trailing", "true", "union", "unique", "user", "using", "variadic", "when",
			"where", "window", "with",
			// T
			"authorization", "binary", "collation", "concurrently", "cross", "current_schema", "freeze", "full", "ilike",
			"inner", "is", "isnull", "join", "left", "like", "natural", "notnull", "outer", "overlaps", "right", "similar",
			"tablesample", "verbose");

	// how PostgreSQL reads an identifier token: "x" unquoted ("" -> "), anything else folded
	public static String read(String token) {
		if (token.length() >= 2 && token.startsWith("\"") && token.endsWith("\""))
			return token.substring(1, token.length() - 1).replace("\"\"", "\"");
		return token.toLowerCase(Locale.ROOT);
	}

	// whether a name must be quoted to be read back as itself
	public static boolean needsQuotes(String name) {
		return !PLAIN.matcher(name).matches() || RESERVED.contains(name);
	}

	public static String quote(String name) {
		return "\"" + name.replace("\"", "\"\"") + "\"";
	}

	// the name as PostgreSQL needs it written: quoted only where needed
	public static String write(String name) {
		return needsQuotes(name) ? quote(name) : name;
	}

	// whether the source wrote this name quoted: the quoted flag, read from the parse
	public static boolean isQuoted(EObject owner, EStructuralFeature nameFeature) {
		INode node = NodeModelUtils.getNode(owner);
		if (node == null)
			return false;
		return NodeModelUtils.findNodesForFeature(owner, nameFeature).stream()
				.anyMatch(n -> NodeModelUtils.getTokenText(n).startsWith("\""));
	}

	// a name written back as the source wrote it: quoted if it was quoted, or if it must be
	private static String write(EObject owner, EStructuralFeature feature, String name) {
		return isQuoted(owner, feature) ? quote(name) : write(name);
	}

	// the name as PostgreSQL stores it in its catalog: folded or unquoted, never quoted. For text
	// that is matched against the catalog as given, not parsed as SQL: create_distributed_table's
	// distribution column ('Acct' finds a column "Acct"; '"Acct"' and 'ACCT' do not, and 'order'
	// finds "order"; measured on Citus 14.2), and the names in the sharding JSON
	public static String stored(Table table) {
		return table.getName();
	}

	public static String stored(Column column) {
		return column.getName();
	}

	public static String table(Table table) {
		return write(table, SqlPackage.Literals.TABLE__NAME, table.getName());
	}

	public static String column(Column column) {
		return write(column, SqlPackage.Literals.COLUMN__NAME, column.getName());
	}

	public static String schema(Table table) {
		return table.getSchemaName() == null ? null : write(table, SqlPackage.Literals.TABLE__SCHEMA_NAME, table.getSchemaName());
	}

	// [schema.]table, as written in CREATE TABLE and as a regclass literal ('banking.accounts')
	public static String qualified(Table table) {
		String schema = schema(table);
		return schema == null ? table(table) : schema + "." + table(table);
	}
}
