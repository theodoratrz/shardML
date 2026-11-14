package io.shardml.schema;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.shardml.sql.AlterTable;
import io.shardml.sql.Statement;

/* Sorts the statements of a .msql file that the grammar does not model (Statement, and ALTER TABLE
 * with an action other than ADD constraint) by an explicit list (Plan-sql 11a, decision 3):
 *  - the pg_dump preamble: SET, SELECT pg_catalog.set_config(..), ALTER .. OWNER TO, the psql
 *    restrict / unrestrict lines, GRANT, REVOKE, COMMENT ON, DROP TABLE. Session settings,
 *    ownership, privileges, comments and cleanup: the generated schema needs none of them, so the
 *    generator leaves them out, and each file gets one INFO issue counting them (SHML_075);
 *  - SET search_path / set_config('search_path', ..) to a non-empty path: not written back either,
 *    but unlike the rest it changes the generated schema's meaning (where unqualified tables land),
 *    so it is a warning (SHML_076). An empty path (pg_dump's own, every name qualified) or DEFAULT is
 *    preamble;
 *  - sequences (CREATE SEQUENCE, ALTER SEQUENCE other than OWNER TO, a column default or identity
 *    added by ALTER TABLE): not modelled yet, an error pointing at the follow-up (SHML_061);
 *  - everything else: an error at the statement, saying what to do with it (SHML_061).
 * A statement is matched on its first words only, case-insensitively, never by a catch-all: a word
 * the lists below do not name is an error. */
public final class SqlStatements {

	private SqlStatements() {
	}

	public enum Kind {
		// the preamble, in the order the INFO issue lists them
		SET("SET"), SET_CONFIG("set_config"), OWNER_TO("ALTER .. OWNER TO"), PSQL_RESTRICT("psql restrict / unrestrict"),
		GRANT("GRANT"), REVOKE("REVOKE"), COMMENT("COMMENT ON"), DROP_TABLE("DROP TABLE"),
		// a non-empty search_path, which the generator does not write back: a warning (SHML_076)
		SEARCH_PATH(null),
		// errors
		SEQUENCE(null), UNSUPPORTED(null);

		private final String label;

		Kind(String label) {
			this.label = label;
		}

		// how the INFO issue names this kind; null for the errors
		public String label() {
			return label;
		}

		public boolean isPreamble() {
			return label != null;
		}
	}

	// kind; what: the statement's first words as written (for messages); advice: what to do, for an error
	public record Sorted(Kind kind, String what, String advice) {
	}

	private static final String SEQUENCES = "Sequences are not modelled yet; they are the planned follow-up to pg_dump "
			+ "input (Plan-sql section 3). Until then, declare the column SERIAL / BIGSERIAL or GENERATED .. AS IDENTITY "
			+ "and leave the sequence statements out.";
	private static final String MIGRATION = "Functions, procedures, triggers, views, rules and aggregates do not affect "
			+ "sharding and are not modelled: keep them in a separate migration, applied after the generated files.";
	private static final String TYPES = "User-defined types, domains and extensions are not supported: a column type must "
			+ "be a built-in type of the .msql grammar.";
	private static final String PARTITIONS = "Partitioned tables are not supported: declare the table without "
			+ "PARTITION BY, and leave its partitions out.";
	private static final String OTHER = "Only CREATE TABLE, ALTER TABLE .. ADD constraint, CREATE [UNIQUE] INDEX, CREATE "
			+ "SCHEMA and the pg_dump preamble are read from a schema file; remove the statement or apply it separately.";

	private static final Pattern OWNER_TO = Pattern.compile("(?is)\\bOWNER\\s+TO\\s+\\S+$");
	private static final Pattern SET_CONFIG = Pattern.compile("(?is)^pg_catalog\\s*\\.\\s*set_config\\s*\\(");
	// $tag$, a dollar quote with a tag (pg_dump's $_$ and $__$ are DOLLAR_STRING tokens, never seen here)
	private static final Pattern DOLLAR_TAG = Pattern.compile("\\$[A-Za-z][A-Za-z0-9_]*\\$");
	// SET [SESSION | LOCAL] search_path {= | TO} value; the value as written
	private static final Pattern SET_SEARCH_PATH = Pattern.compile("(?is)^(?:SESSION\\s+|LOCAL\\s+)?search_path\\s*(?:=|\\bTO\\b)(.*)$");
	// set_config('search_path', 'value', ..): the value, without quotes
	private static final Pattern SET_CONFIG_SEARCH_PATH = Pattern
			.compile("(?is)set_config\\s*\\(\\s*'search_path'\\s*,\\s*'((?:[^']|'')*)'");

	// '' (pg_dump's: every name qualified) or DEFAULT (the connection's own path, as if not set)
	private static boolean isEmptyPath(String value) {
		return value.strip().matches("(?i)''|DEFAULT");
	}
	// a word, up to a space, a parenthesis or a dot (for messages and kinds only: never a name)
	private static final Pattern WORD = Pattern.compile("^\\s*([^\\s(.;]+)");

	public static Sorted of(Statement statement) {
		if (statement.getMeta() != null) {
			String meta = statement.getMeta().strip();
			String command = meta.split("\\s+")[0];
			if (is(command, "\\\\restrict") || is(command, "\\\\unrestrict"))
				return new Sorted(Kind.PSQL_RESTRICT, command, null);
			return new Sorted(Kind.UNSUPPORTED, "psql meta-command " + command,
					"psql meta-commands are not SQL: remove it from the schema file.");
		}
		String verb = statement.getVerb();
		String body = statement.getBody() == null ? "" : statement.getBody();
		String first = firstWord(body);
		String what = first.isEmpty() ? verb : verb + " " + first;
		// a dollar quote the lexer does not know: the statement was cut at the body's first ';'
		Matcher tag = DOLLAR_TAG.matcher(verb + body);
		if (tag.find())
			return new Sorted(Kind.UNSUPPORTED, verb.equals("$") ? tag.group() : what, "It uses the dollar quote "
					+ tag.group() + ", which is not supported (only $$, $_$ and $__$, the tags pg_dump writes): the statement "
					+ "was cut at the first ';' of the quoted body, and the rest of the body reads as statements of their "
					+ "own. Keep functions in a separate migration, applied after the generated files.");
		if (is(verb, "SET")) {
			Matcher path = SET_SEARCH_PATH.matcher(body);
			if (path.find() && !isEmptyPath(path.group(1)))
				return new Sorted(Kind.SEARCH_PATH, verb + " search_path", path.group(1).strip());
			return new Sorted(Kind.SET, verb, null);
		}
		if (is(verb, "SELECT") && SET_CONFIG.matcher(body).find()) {
			Matcher path = SET_CONFIG_SEARCH_PATH.matcher(body);
			if (path.find() && !path.group(1).isEmpty())
				return new Sorted(Kind.SEARCH_PATH, "set_config('search_path', ..)", "'" + path.group(1) + "'");
			return new Sorted(Kind.SET_CONFIG, verb, null);
		}
		if (is(verb, "GRANT"))
			return new Sorted(Kind.GRANT, verb, null);
		if (is(verb, "REVOKE"))
			return new Sorted(Kind.REVOKE, verb, null);
		if (is(verb, "COMMENT") && is(first, "ON"))
			return new Sorted(Kind.COMMENT, what, null);
		if (is(verb, "DROP"))
			return is(first, "TABLE") ? new Sorted(Kind.DROP_TABLE, what, null)
					: new Sorted(Kind.UNSUPPORTED, what, "Only DROP TABLE is recognised (and left out of the generated schema).");
		if (is(verb, "ALTER")) {
			if (OWNER_TO.matcher(body).find())
				return new Sorted(Kind.OWNER_TO, what, null);
			if (is(first, "SEQUENCE"))
				return new Sorted(Kind.SEQUENCE, what, SEQUENCES);
			return new Sorted(Kind.UNSUPPORTED, what, OTHER);
		}
		if (is(verb, "CREATE"))
			return create(what, body);
		if (is(verb, "PARTITION"))
			return new Sorted(Kind.UNSUPPORTED, what, PARTITIONS);
		// a clause the statement before does not support, read on its own once that statement ended
		if (is(verb, "NULLS") || is(verb, "TABLESPACE"))
			return new Sorted(Kind.UNSUPPORTED, what, "It is a clause of the statement before it, which the grammar does "
					+ "not support there (CREATE INDEX .. NULLS [NOT] DISTINCT, TABLESPACE): leave it out.");
		return new Sorted(Kind.UNSUPPORTED, verb, OTHER);
	}

	// CREATE <first words>: which of the unsupported kinds
	private static Sorted create(String what, String body) {
		List<String> words = words(body, 3);
		int i = 0;
		if (words.size() > 1 && is(words.get(0), "OR") && is(words.get(1), "REPLACE"))
			i = 2;
		String object = i < words.size() ? words.get(i) : "";
		if (i > 0)
			what = what + " " + String.join(" ", words.subList(1, Math.min(i + 1, words.size()))); // CREATE OR REPLACE FUNCTION
		if (is(object, "SEQUENCE"))
			return new Sorted(Kind.SEQUENCE, what, SEQUENCES);
		for (String w : List.of("FUNCTION", "PROCEDURE", "TRIGGER", "VIEW", "MATERIALIZED", "AGGREGATE", "RULE"))
			if (is(object, w))
				return new Sorted(Kind.UNSUPPORTED, what, MIGRATION);
		for (String w : List.of("TYPE", "DOMAIN", "EXTENSION"))
			if (is(object, w))
				return new Sorted(Kind.UNSUPPORTED, what, TYPES);
		return new Sorted(Kind.UNSUPPORTED, what, OTHER);
	}

	// ALTER TABLE t <body>, the actions other than ADD constraint
	public static Sorted of(AlterTable alter) {
		String body = alter.getBody();
		String what = "ALTER TABLE .. " + firstWord(body);
		if (OWNER_TO.matcher(body).find())
			return new Sorted(Kind.OWNER_TO, what, null);
		if (Pattern.compile("(?is)\\bnextval\\s*\\(").matcher(body).find()
				|| Pattern.compile("(?is)^ALTER\\s+(COLUMN\\s+)?\\S+\\s+ADD\\s+GENERATED\\b").matcher(body).find())
			return new Sorted(Kind.SEQUENCE, what, SEQUENCES);
		if (Pattern.compile("(?is)^(ATTACH|DETACH)\\s+PARTITION\\b").matcher(body).find())
			return new Sorted(Kind.UNSUPPORTED, what + " PARTITION", PARTITIONS);
		return new Sorted(Kind.UNSUPPORTED, what, OTHER);
	}

	private static boolean is(String word, String expected) {
		return word != null && word.matches("(?i)" + expected);
	}

	private static String firstWord(String text) {
		List<String> words = words(text, 1);
		return words.isEmpty() ? "" : words.get(0);
	}

	// the first n words of a text, up to a parenthesis, a dot or a ';'
	private static List<String> words(String text, int n) {
		List<String> words = new java.util.ArrayList<>();
		String rest = text;
		while (words.size() < n) {
			Matcher m = WORD.matcher(rest);
			if (!m.find())
				break;
			words.add(m.group(1));
			rest = rest.substring(m.end());
		}
		return words;
	}
}
