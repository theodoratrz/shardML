package io.shardml.conversion;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import io.shardml.schema.SqlNames;

/* Reads names and strings the way PostgreSQL does (see SqlNames): an unquoted name is folded
 * to lower case, a "quoted" one is kept as written, and a 'string' uses '' for a quote. The
 * same conversion applies to declarations and to references, so `PRIMARY KEY (ID)` links to a
 * column declared `id`.
 *
 * Verbatim text (DEFAULT, CHECK, GENERATED, WITH options, DEFERRABLE, index options and tails, and
 * the words of statements the grammar does not model) is read from the parse, not
 * from the tokens' concatenation, which would join `now() AT TIME ZONE 'utc'` into one word: the
 * tokens as written, comments dropped, and the whitespace between two tokens collapsed to one space
 * (NodeModelUtils.getTokenText). Names and strings inside it are left exactly as written, so
 * PostgreSQL reads them as it read the source. */
public class SQLValueConverters extends DefaultTerminalConverters {

	@ValueConverter(rule = "Name")
	public IValueConverter<String> Name() {
		return new IValueConverter<>() {
			@Override
			public String toValue(String string, INode node) {
				if (string == null)
					return null;
				String text = string.strip();
				if (text.equals("\"\""))
					throw new ValueConverterException("A quoted name cannot be empty.", node, null);
				return SqlNames.read(text);
			}

			@Override
			public String toString(String value) {
				return SqlNames.write(value);
			}
		};
	}

	@Override
	@ValueConverter(rule = "STRING")
	public IValueConverter<String> STRING() {
		return new IValueConverter<>() {
			@Override
			public String toValue(String string, INode node) {
				if (string == null)
					return null;
				return string.substring(1, string.length() - 1).replace("''", "'");
			}

			@Override
			public String toString(String value) {
				return "'" + value.replace("'", "''") + "'";
			}
		};
	}

	@ValueConverter(rule = "DefaultExpr")
	public IValueConverter<String> DefaultExpr() {
		return VERBATIM;
	}

	@ValueConverter(rule = "Parenthesized")
	public IValueConverter<String> Parenthesized() {
		return VERBATIM;
	}

	@ValueConverter(rule = "GeneratedTail")
	public IValueConverter<String> GeneratedTail() {
		return VERBATIM;
	}

	@ValueConverter(rule = "Deferrability")
	public IValueConverter<String> Deferrability() {
		return VERBATIM;
	}

	// the words of statements and index parts the model keeps as text (step 5)
	@ValueConverter(rule = "RawTokens")
	public IValueConverter<String> RawTokens() {
		return VERBATIM;
	}

	@ValueConverter(rule = "CreateBody")
	public IValueConverter<String> CreateBody() {
		return VERBATIM;
	}

	@ValueConverter(rule = "AlterBody")
	public IValueConverter<String> AlterBody() {
		return VERBATIM;
	}

	@ValueConverter(rule = "AlterTableBody")
	public IValueConverter<String> AlterTableBody() {
		return VERBATIM;
	}

	@ValueConverter(rule = "IndexOptions")
	public IValueConverter<String> IndexOptions() {
		return VERBATIM;
	}

	@ValueConverter(rule = "IndexTail")
	public IValueConverter<String> IndexTail() {
		return VERBATIM;
	}

	@ValueConverter(rule = "Verb")
	public IValueConverter<String> Verb() {
		return VERBATIM;
	}

	private static final IValueConverter<String> VERBATIM = new IValueConverter<>() {
		@Override
		public String toValue(String string, INode node) {
			if (node == null)
				return string;
			return NodeModelUtils.getTokenText(node);
		}

		@Override
		public String toString(String value) {
			return value;
		}
	};
}
