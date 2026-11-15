package io.shardml.bench;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import io.shardml.cost.KeyType;
import io.shardml.cost.Layout;
import io.shardml.cost.StatementSql;
import io.shardml.cost.Workload.Condition;
import io.shardml.cost.Workload.Kind;
import io.shardml.schema.SqlTypes.SqlType;

/* One pgjdbc connection per client (Plan-phase4 2.6): autocommit off, every statement a
 * PreparedStatement prepared once per connection, one database transaction per template run
 * (statements, then COMMIT; ROLLBACK on any error). Reads are executed and their rows read to the
 * end; writes are executeUpdate. A parameter is bound with its column's integer type (smallint,
 * int, bigint), so the comparison is on the column's own type and Citus prunes on it. */
final class JdbcSession implements Session {

	// the connection settings, recorded in the run manifest
	static Map<String, String> properties(String user, String schema, int prepareThreshold, int statementTimeoutMs,
			int connectTimeoutSeconds, int socketTimeoutSeconds) {
		Map<String, String> p = new LinkedHashMap<>();
		p.put("user", user);
		p.put("prepareThreshold", String.valueOf(prepareThreshold));
		p.put("preparedStatementCacheQueries", "256");
		p.put("connectTimeout", String.valueOf(connectTimeoutSeconds));
		p.put("socketTimeout", String.valueOf(socketTimeoutSeconds));
		p.put("tcpKeepAlive", "true");
		p.put("ApplicationName", "shardml-driver");
		p.put("options", "-c statement_timeout=" + statementTimeoutMs + (schema == null ? "" : " -c search_path=" + schema));
		return p;
	}

	private final String url;
	private final Properties properties = new Properties();
	private final Replay replay;
	// per transaction, per statement: its SQL, whether it is a read, and per placeholder its key type
	private final String[][] sql;
	private final boolean[][] reads;
	private final KeyType[][][] types;
	private Connection connection;
	private PreparedStatement[][] statements;

	JdbcSession(String url, Map<String, String> settings, Replay replay, Layout layout) {
		this.url = url;
		this.properties.putAll(settings);
		this.replay = replay;
		int n = replay.transactions().size();
		sql = new String[n][];
		reads = new boolean[n][];
		types = new KeyType[n][][];
		for (int t = 0; t < n; t++) {
			List<StatementSql.Template> templates = replay.templates(t);
			sql[t] = new String[templates.size()];
			reads[t] = new boolean[templates.size()];
			types[t] = new KeyType[templates.size()][];
			for (int s = 0; s < templates.size(); s++) {
				StatementSql.Template template = templates.get(s);
				sql[t][s] = template.sql();
				reads[t][s] = template.statement().kind() == Kind.READ;
				List<KeyType> list = new ArrayList<>();
				for (Condition c : template.statement().conditions()) {
					if (c.value() == null)
						continue;
					SqlType type = layout.schema().type(c.column().table(), c.column().column());
					list.add(type == null ? KeyType.INT8 : KeyType.of(type.base()).orElse(KeyType.INT8));
				}
				types[t][s] = list.toArray(KeyType[]::new);
			}
		}
	}

	@Override
	public void execute(Replay.Run run) throws SQLException {
		if (connection == null)
			open();
		int t = run.template();
		try {
			for (Replay.Execution e : run.executions()) {
				PreparedStatement ps = statements[t][e.statement()];
				if (ps == null)
					ps = statements[t][e.statement()] = connection.prepareStatement(sql[t][e.statement()]);
				long[] p = e.parameters();
				KeyType[] k = types[t][e.statement()];
				for (int i = 0; i < p.length; i++) {
					switch (k[i]) {
						case INT2 -> ps.setShort(i + 1, (short) p[i]);
						case INT4 -> ps.setInt(i + 1, (int) p[i]);
						case INT8 -> ps.setLong(i + 1, p[i]);
					}
				}
				if (reads[t][e.statement()]) {
					try (ResultSet rs = ps.executeQuery()) {
						while (rs.next()) {
							// read every row, as an application would
						}
					}
				} else {
					ps.executeUpdate();
				}
			}
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ignored) {
				// the transaction is counted as failed by e; a broken connection is reset by the caller
			}
			throw e;
		}
	}

	private void open() throws SQLException {
		connection = DriverManager.getConnection(url, properties);
		connection.setAutoCommit(false);
		statements = new PreparedStatement[sql.length][];
		for (int t = 0; t < sql.length; t++)
			statements[t] = new PreparedStatement[sql[t].length];
	}

	@Override
	public void reset() {
		close();
	}

	@Override
	public void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException ignored) {
				// closing a broken connection
			}
		}
		connection = null;
		statements = null;
	}

	Replay replay() {
		return replay;
	}
}
