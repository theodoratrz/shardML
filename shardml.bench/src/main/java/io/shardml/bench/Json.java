package io.shardml.bench;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* A minimal JSON writer for the run's result and manifest: maps (in their iteration order), lists,
 * strings, numbers, booleans, null, and Raw (JSON text written as is, e.g. `shardml cost --json`).
 * A non-finite double is written as null. Two-space indentation, one key per line. */
final class Json {

	record Raw(String json) {
	}

	private Json() {
	}

	static String write(Object value) {
		StringBuilder out = new StringBuilder();
		write(value, out, "");
		return out.append('\n').toString();
	}

	private static void write(Object value, StringBuilder out, String indent) {
		if (value == null) {
			out.append("null");
		} else if (value instanceof Raw raw) {
			out.append(raw.json().strip().replace("\n", "\n" + indent));
		} else if (value instanceof String s) {
			string(s, out);
		} else if (value instanceof Double d) {
			out.append(Double.isFinite(d) ? number(d) : "null");
		} else if (value instanceof Float f) {
			write(f.doubleValue(), out, indent);
		} else if (value instanceof Number || value instanceof Boolean) {
			out.append(value);
		} else if (value instanceof Map<?, ?> map) {
			if (map.isEmpty()) {
				out.append("{}");
				return;
			}
			out.append("{\n");
			Iterator<? extends Map.Entry<?, ?>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<?, ?> e = it.next();
				out.append(indent).append("  ");
				string(String.valueOf(e.getKey()), out);
				out.append(": ");
				write(e.getValue(), out, indent + "  ");
				out.append(it.hasNext() ? ",\n" : "\n");
			}
			out.append(indent).append('}');
		} else if (value instanceof List<?> list) {
			if (list.isEmpty()) {
				out.append("[]");
				return;
			}
			out.append("[\n");
			for (int i = 0; i < list.size(); i++) {
				out.append(indent).append("  ");
				write(list.get(i), out, indent + "  ");
				out.append(i + 1 < list.size() ? ",\n" : "\n");
			}
			out.append(indent).append(']');
		} else {
			string(value.toString(), out);
		}
	}

	private static String number(double d) {
		if (d == Math.rint(d) && Math.abs(d) < 1e15)
			return String.valueOf((long) d);
		return String.valueOf(d);
	}

	private static void string(String s, StringBuilder out) {
		out.append('"');
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
				case '"' -> out.append("\\\"");
				case '\\' -> out.append("\\\\");
				case '\n' -> out.append("\\n");
				case '\r' -> out.append("\\r");
				case '\t' -> out.append("\\t");
				default -> {
					if (c < 0x20)
						out.append(String.format("\\u%04x", (int) c));
					else
						out.append(c);
				}
			}
		}
		out.append('"');
	}
}
