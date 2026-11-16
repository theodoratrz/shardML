package io.shardml.bench;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import io.shardml.cost.HashTable;
import io.shardml.cost.Layout;
import io.shardml.cost.TableLayout;

/* The cluster's counters around a run's measured window (Plan-phase4 section 3, step P4.3; amendments 1 to 3),
 * read with SQL through one connection (the run's first target: the coordinator in mode (a), a worker in mode
 * (b); Citus metadata is on every node), per node keyed host:port as pg_dist_node names it:
 *  - touches: the per-node load diagnostic of amendment 2: over pg_stat_statements on the node, the calls of
 *    every statement naming a shard of the layout's tables (<table>_<shardid>), times the distinct such shards
 *    it names; reads (SELECT) and writes (UPDATE, DELETE, INSERT, MERGE) apart; statements reading the
 *    statistics themselves excluded. Executions: the planner's index probes do not count, an UPDATE counts once;
 *  - statements: citus_stat_counters' single- and multi-shard executions of the database (counted on the node
 *    that plans the statement: the entry node);
 *  - twoPhase: distinct 2PC transactions (gid without its last field) over every node's pg_dist_transaction,
 *    and per participant node (the records' group);
 *  - cpuUsageMicros: the node's cgroup CPU (usage_usec of /sys/fs/cgroup/cpu.stat, read with pg_read_file);
 *  - scans / tuplesUpdated: the plan's original seq_scan + idx_scan and n_tup_upd over the layout's placements,
 *    kept for the record only (amendment 1: they count planner probes and count writes twice).
 * A counter that cannot be read is left out and its error listed under "errors"; the snapshot never fails a run.
 * Settings that decide whether a counter is complete are recorded with it (pg_stat_statements present,
 * citus.enable_stat_counters, citus.recover_2pc_interval = -1, citus.enable_local_execution). */
final class Counters {

	private Counters() {
	}

	// \m(t1|t2)_[0-9]+\M: a shard relation of one of the tables (the PostgreSQL ARE word boundaries)
	static String shardPattern(Layout layout) {
		List<String> names = new ArrayList<>();
		for (TableLayout t : layout.tables())
			names.add(t.table().replaceAll("([^A-Za-z0-9_])", "\\\\$1"));
		return "\\m(" + String.join("|", names) + ")_[0-9]+\\M";
	}

	// a statement's leading comments: Citus prefixes every shard query with the tenant it tracks
	// (citus.stat_tenants_track, e.g. /*{"cId":347,"tId":"10954"}*/), which holds no '*'
	static final String LEADING_COMMENTS = "^(\\s*/\\*[^*]*\\*/)*\\s*";

	static Map<String, Object> snapshot(Connection c, Layout layout) {
		Map<String, Object> out = new LinkedHashMap<>();
		Map<String, Map<String, Object>> nodes = new TreeMap<>();
		List<String> errors = new ArrayList<>();
		long started = System.nanoTime();
		out.put("takenAt", Instant.now().toString());
		String pattern = shardPattern(layout);
		// touches (first: the other queries below name shards too, and are excluded by name anyway)
		String touches = "SELECT coalesce(string_agg(side || '=' || x, ','), '') FROM ("
				+ " SELECT CASE WHEN query ~* '" + LEADING_COMMENTS + "select' THEN 'read'"
				+ " WHEN query ~* '" + LEADING_COMMENTS + "(update|delete|insert|merge)' THEN 'write' END AS side,"
				+ " sum(calls * (SELECT count(DISTINCT m[1]) FROM regexp_matches(query, '" + pattern + "', 'g') AS m)) AS x"
				+ " FROM pg_stat_statements WHERE dbid = (SELECT oid FROM pg_database WHERE datname = current_database())"
				+ " AND query !~ 'pg_stat_user_tables|pg_total_relation_size|pg_dist_|pg_stat_statements'"
				+ " GROUP BY 1) t WHERE side IS NOT NULL";
		onEveryNode(c, touches, nodes, errors, "touches", (node, result) -> {
			Map<String, Object> t = new LinkedHashMap<>();
			t.put("read", 0L);
			t.put("write", 0L);
			for (String e : result.split(",")) {
				if (!e.isEmpty())
					t.put(e.substring(0, e.indexOf('=')), Long.parseLong(e.substring(e.indexOf('=') + 1)));
			}
			node.put("touches", t);
		});
		// entries pg_stat_statements evicted (pg_stat_statements.max): a change over the window loses counts
		onEveryNode(c, "SELECT dealloc FROM pg_stat_statements_info", nodes, errors, "pg_stat_statements_info",
				(node, result) -> node.put("pgStatStatementsDeallocations", Long.parseLong(result.strip())));
		onEveryNode(c, "SELECT coalesce((SELECT query_execution_single_shard || ',' || query_execution_multi_shard FROM citus_stat_counters"
				+ " WHERE name = current_database()), '0,0')", nodes, errors, "statements", (node, result) -> {
					String[] f = result.split(",");
					Map<String, Object> s = new LinkedHashMap<>();
					s.put("singleShard", Long.parseLong(f[0]));
					s.put("multiShard", Long.parseLong(f[1]));
					node.put("statements", s);
				});
		onEveryNode(c, "SELECT split_part(split_part(pg_read_file('/sys/fs/cgroup/cpu.stat'), E'\\n', 1), ' ', 2)", nodes, errors, "cpu",
				(node, result) -> node.put("cpuUsageMicros", Long.parseLong(result.strip())));
		// 2PC: the records live on the initiating node, one per participant (its group)
		Map<String, Object> twoPhase = new LinkedHashMap<>();
		try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery(
				"SELECT coalesce(sum(result::bigint), 0) FROM run_command_on_all_nodes($$SELECT count(DISTINCT regexp_replace(gid, '_[0-9]+$', ''))"
						+ " FROM pg_dist_transaction$$) WHERE success")) {
			rs.next();
			twoPhase.put("transactions", rs.getLong(1));
		} catch (SQLException e) {
			errors.add("twoPhase: " + e.getMessage());
		}
		Map<String, Object> byParticipant = new TreeMap<>();
		try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery(
				"SELECT split_part(e, '=', 1), sum(split_part(e, '=', 2)::bigint) FROM run_command_on_all_nodes($$"
						+ " SELECT coalesce(string_agg(n.nodename || ':' || n.nodeport || '=' || c, ','), '') FROM (SELECT groupid,"
						+ " count(DISTINCT regexp_replace(gid, '_[0-9]+$', '')) AS c FROM pg_dist_transaction GROUP BY groupid) x"
						+ " JOIN pg_dist_node n ON n.groupid = x.groupid AND n.noderole = 'primary'$$) r, string_to_table(r.result, ',') AS e"
						+ " WHERE r.success AND e <> '' GROUP BY 1")) {
			while (rs.next())
				byParticipant.put(rs.getString(1), rs.getLong(2));
			twoPhase.put("byParticipant", byParticipant);
		} catch (SQLException e) {
			errors.add("twoPhase byParticipant: " + e.getMessage());
		}
		// the plan's original counters, for the record
		try (PreparedStatement ps = c.prepareStatement("SELECT r.nodename || ':' || r.nodeport, sum(split_part(r.result, ' ', 1)::bigint),"
				+ " sum(split_part(r.result, ' ', 2)::bigint) FROM run_command_on_placements(?::regclass, 'SELECT coalesce(seq_scan, 0)"
				+ " + coalesce(idx_scan, 0) || '' '' || n_tup_upd FROM pg_stat_user_tables WHERE relid = ''%s''::regclass') r"
				+ " WHERE r.success GROUP BY 1")) {
			for (TableLayout t : layout.tables()) {
				ps.setString(1, t.qualified());
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						Map<String, Object> node = nodes.computeIfAbsent(rs.getString(1), k -> new LinkedHashMap<>());
						node.merge("scans", rs.getLong(2), (x, y) -> (Long) x + (Long) y);
						node.merge("tuplesUpdated", rs.getLong(3), (x, y) -> (Long) x + (Long) y);
					}
				}
			}
		} catch (SQLException e) {
			errors.add("scans: " + e.getMessage());
		}
		out.put("nodes", nodes);
		out.put("twoPhase", twoPhase);
		out.put("errors", errors);
		out.put("durationMillis", (System.nanoTime() - started) / 1_000_000);
		return out;
	}

	private interface NodeResult {
		void accept(Map<String, Object> node, String result);
	}

	// the command on every node (run_command_on_all_nodes), each successful result to the node's map
	private static void onEveryNode(Connection c, String command, Map<String, Map<String, Object>> nodes, List<String> errors, String what,
			NodeResult consumer) {
		try (PreparedStatement ps = c.prepareStatement("SELECT n.nodename || ':' || n.nodeport, r.success, r.result"
				+ " FROM run_command_on_all_nodes(?) r JOIN pg_dist_node n ON n.nodeid = r.nodeid ORDER BY 1")) {
			ps.setString(1, command);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					if (!rs.getBoolean(2)) {
						errors.add(what + " on " + rs.getString(1) + ": " + rs.getString(3));
						continue;
					}
					try {
						consumer.accept(nodes.computeIfAbsent(rs.getString(1), k -> new LinkedHashMap<>()), rs.getString(3));
					} catch (RuntimeException e) {
						errors.add(what + " on " + rs.getString(1) + ": cannot read '" + rs.getString(3) + "'");
					}
				}
			}
		} catch (SQLException e) {
			errors.add(what + ": " + e.getMessage());
		}
	}

	// --- the cluster, once per run ---

	// x86_64 / amd64 -> amd64, aarch64 / arm64 -> arm64; anything else as given
	static String arch(String name) {
		String a = name.strip().toLowerCase(Locale.ROOT);
		return switch (a) {
			case "x86_64", "amd64", "x64" -> "amd64";
			case "aarch64", "arm64" -> "arm64";
			default -> a;
		};
	}

	/* The cluster facts a run records: per node its PostgreSQL / Citus versions and architecture; whether it is
	 * emulated (some node's architecture differs from the host the driver runs on, as on the local bench
	 * cluster: amd64 images on an arm64 Mac; valid when the driver runs on the cluster's host, as planned), so
	 * that CPU from an emulated run cannot be mistaken for a result (amendment 1, 5); the settings the counters
	 * depend on; every non-default setting of the entry node; and the shard map of the layout's tables. */
	static Map<String, Object> cluster(Connection c, Layout layout, String hostArch) {
		Map<String, Object> out = new LinkedHashMap<>();
		List<String> errors = new ArrayList<>();
		Map<String, Map<String, Object>> nodes = new TreeMap<>();
		onEveryNode(c, "SELECT version()", nodes, errors, "version", (node, result) -> {
			node.put("version", result);
			int on = result.indexOf(" on ");
			node.put("arch", on < 0 ? "unknown" : arch(result.substring(on + 4).split("-")[0]));
		});
		onEveryNode(c, "SELECT citus_version()", nodes, errors, "citus_version", (node, result) -> node.put("citus", result));
		onEveryNode(c, "SELECT coalesce((SELECT extversion FROM pg_extension WHERE extname = 'pg_stat_statements'), 'absent')"
				+ " || ',' || current_setting('citus.enable_stat_counters') || ',' || current_setting('citus.recover_2pc_interval')"
				+ " || ',' || current_setting('citus.enable_local_execution')", nodes, errors, "settings", (node, result) -> {
					String[] f = result.split(",");
					Map<String, Object> s = new LinkedHashMap<>();
					s.put("pg_stat_statements", f[0]);
					s.put("citus.enable_stat_counters", f[1]);
					s.put("citus.recover_2pc_interval", f[2]);
					s.put("citus.enable_local_execution", f[3]);
					node.put("counterSettings", s);
				});
		out.put("nodes", nodes);
		String host = arch(hostArch);
		boolean emulated = nodes.values().stream().anyMatch(n -> n.get("arch") != null && !host.equals(n.get("arch")));
		Map<String, Object> emulation = new LinkedHashMap<>();
		emulation.put("hostArch", host);
		emulation.put("emulated", emulated);
		emulation.put("rule", "emulated when some node's architecture (from version()) differs from the driver host's (os.arch); "
				+ "CPU from an emulated run is never a result (plan amendment 1, 5)");
		out.put("emulation", emulation);
		Map<String, Object> settings = new TreeMap<>();
		try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery(
				"SELECT name, setting FROM pg_settings WHERE source NOT IN ('default', 'override') OR name LIKE 'citus.%' ORDER BY 1")) {
			while (rs.next())
				settings.put(rs.getString(1), rs.getString(2));
		} catch (SQLException e) {
			errors.add("settings: " + e.getMessage());
		}
		out.put("entryNodeSettings", settings);
		Map<String, Object> placement = new LinkedHashMap<>();
		try (PreparedStatement ps = c.prepareStatement("SELECT n.nodename || ':' || n.nodeport FROM pg_dist_shard s"
				+ " JOIN pg_dist_placement p ON p.shardid = s.shardid JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'"
				+ " WHERE s.logicalrelid = ?::regclass ORDER BY s.shardminvalue::bigint, n.nodename, n.nodeport")) {
			for (TableLayout t : layout.tables()) {
				if (!(t instanceof HashTable))
					continue;
				ps.setString(1, t.qualified());
				List<String> buckets = new ArrayList<>();
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next())
						buckets.add(rs.getString(1));
				}
				placement.put(t.table(), buckets);
			}
		} catch (SQLException e) {
			errors.add("placement: " + e.getMessage());
		}
		out.put("placementByBucket", placement);
		out.put("errors", errors);
		return out;
	}

	// --- entry shares (amendment 3, 2) ---

	static final double SHARE_TOLERANCE = 0.02;

	/* Per target: its clients and the transactions and statements they executed in the window; each target's
	 * share of the statements; uniform when the clients are a multiple of the targets and every share is within
	 * SHARE_TOLERANCE of 1 / targets. */
	static Map<String, Object> entry(List<String> targets, int clients, long[] transactionsByClient, long[] statementsByClient) {
		int n = targets.size();
		long[] tx = new long[n];
		long[] st = new long[n];
		int[] cl = new int[n];
		for (int i = 0; i < clients; i++) {
			cl[i % n]++;
			tx[i % n] += transactionsByClient[i];
			st[i % n] += statementsByClient[i];
		}
		long total = 0;
		for (long s : st)
			total += s;
		List<Object> rows = new ArrayList<>();
		double worst = 0;
		for (int t = 0; t < n; t++) {
			double share = total == 0 ? 0 : (double) st[t] / total;
			worst = Math.max(worst, Math.abs(share - 1.0 / n));
			Map<String, Object> row = new LinkedHashMap<>();
			row.put("target", targets.get(t));
			row.put("clients", cl[t]);
			row.put("transactions", tx[t]);
			row.put("statements", st[t]);
			row.put("share", share);
			rows.add(row);
		}
		boolean uniform = clients % n == 0 && worst <= SHARE_TOLERANCE;
		Map<String, Object> out = new LinkedHashMap<>();
		out.put("targets", rows);
		out.put("expectedShare", 1.0 / n);
		out.put("maxShareDeviation", worst);
		out.put("uniform", uniform);
		out.put("rule", "uniform when clients are a multiple of the targets and every statement share is within " + SHARE_TOLERANCE
				+ " of 1/targets (plan amendment 3, 2)");
		return out;
	}

	// --- hot keys (Plan-phase4 section 3: citus_stat_tenants, citus.stat_tenants_track = 'all') ---

	static final int TENANTS = 100;

	// zero the tenant counters on every node (citus_stat_tenants_reset), with no client running: the after
	// read then holds the measured window's counts (this + last period, the period set longer than a run)
	static void resetTenants(Connection c, List<String> errors) {
		try (Statement s = c.createStatement()) {
			s.execute("SELECT citus_stat_tenants_reset()");
		} catch (SQLException e) {
			errors.add("tenants reset: " + e.getMessage());
		}
	}

	/* The top TENANTS tenants (distribution-column values of router statements) by statements, over every
	 * node: read and statement counts and CPU of this period plus the last, with the tracking settings; with
	 * tracking off, only the settings (tracked: false), which is not an error. */
	static Map<String, Object> tenants(Connection c, List<String> errors) {
		Map<String, Object> out = new LinkedHashMap<>();
		try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery("SELECT current_setting('citus.stat_tenants_track'),"
				+ " current_setting('citus.stat_tenants_period'), current_setting('citus.stat_tenants_limit')")) {
			rs.next();
			out.put("track", rs.getString(1));
			out.put("periodSeconds", Long.parseLong(rs.getString(2)));
			out.put("limit", Long.parseLong(rs.getString(3)));
		} catch (SQLException e) {
			errors.add("tenants settings: " + e.getMessage());
			return out;
		}
		boolean tracked = !"none".equals(out.get("track"));
		out.put("tracked", tracked);
		if (!tracked)
			return out;
		List<Object> top = new ArrayList<>();
		try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery("SELECT nodeid, colocation_id, tenant_attribute,"
				+ " read_count_in_this_period + read_count_in_last_period, query_count_in_this_period + query_count_in_last_period,"
				+ " cpu_usage_in_this_period + cpu_usage_in_last_period FROM citus_stat_tenants"
				+ " ORDER BY 5 DESC, 4 DESC, 2, 3 LIMIT " + TENANTS)) {
			while (rs.next()) {
				Map<String, Object> t = new LinkedHashMap<>();
				t.put("nodeid", rs.getInt(1));
				t.put("colocationId", rs.getInt(2));
				t.put("tenant", rs.getString(3));
				t.put("reads", rs.getLong(4));
				t.put("statements", rs.getLong(5));
				t.put("cpuSeconds", rs.getDouble(6));
				top.add(t);
			}
		} catch (SQLException e) {
			errors.add("tenants: " + e.getMessage());
		}
		out.put("top", top);
		return out;
	}
}
