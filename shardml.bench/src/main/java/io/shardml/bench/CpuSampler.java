package io.shardml.bench;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.LockSupport;

/* Per-node CPU during the measured window (Plan-phase4 2.6): every node's cgroup CPU (usage_usec of
 * /sys/fs/cgroup/cpu.stat, over SQL with run_command_on_all_nodes, as the other counters) sampled every
 * INTERVAL on a connection of its own, from the window's start until stop(). Utilization of an interval on a
 * node: CPU-seconds used / (seconds elapsed x the node's cores), cores being its cgroup's CPU quota
 * (cpu.max) or, without one, its cpuset (cpuset.cpus.effective). A node is saturated when its mean
 * utilization over the window exceeds THRESHOLD (85%, section 2.6); the per-interval maximum and the number
 * of intervals above the threshold are recorded too. The plan flags any node other than the bottleneck the
 * predictor names, and the coordinator in any mode; the driver does not know the predictor, so it lists
 * every saturated node and the analysis excludes the predicted bottleneck. Under emulation the numbers are
 * recorded and `valid` is false (plan amendment 1, 5). */
final class CpuSampler implements Runnable {

	static final long INTERVAL_NANOS = 1_000_000_000L;
	static final double THRESHOLD = 0.85;

	private static final String USAGE = "SELECT split_part(split_part(pg_read_file('/sys/fs/cgroup/cpu.stat'), E'\\n', 1), ' ', 2)";
	private static final String LIMITS = "SELECT pg_read_file('/sys/fs/cgroup/cpu.max') || '|' || pg_read_file('/sys/fs/cgroup/cpuset.cpus.effective')";

	private final Connection connection;
	private final long start;
	private final Map<String, Double> cores = new TreeMap<>();
	private final List<Long> times = new ArrayList<>();
	private final List<Map<String, Long>> usages = new ArrayList<>();
	private final List<String> errors = new ArrayList<>();
	private volatile boolean stopped;

	CpuSampler(Connection connection, long start) {
		this.connection = connection;
		this.start = start;
		for (Map.Entry<String, String> e : onEveryNode(LIMITS).entrySet()) {
			String[] f = e.getValue().split("\\|");
			cores.put(e.getKey(), cores(f[0], f.length > 1 ? f[1] : ""));
		}
	}

	// cpu.max "max 100000" or "200000 100000" (quota period); cpuset "0-5,8"
	static double cores(String cpuMax, String cpuset) {
		int cpus = 0;
		for (String part : cpuset.strip().split(",")) {
			if (part.isBlank())
				continue;
			String[] r = part.strip().split("-");
			cpus += r.length == 1 ? 1 : Integer.parseInt(r[1]) - Integer.parseInt(r[0]) + 1;
		}
		String[] q = cpuMax.strip().split("\\s+");
		double quota = q.length == 2 && !q[0].equals("max") ? Double.parseDouble(q[0]) / Double.parseDouble(q[1]) : Double.POSITIVE_INFINITY;
		return Math.min(quota, cpus == 0 ? Double.POSITIVE_INFINITY : cpus);
	}

	void stop() {
		stopped = true;
	}

	@Override
	public void run() {
		long next = Math.max(start, System.nanoTime());
		while (!stopped) {
			long wait = next - System.nanoTime();
			if (wait > 0)
				LockSupport.parkNanos(wait);
			if (stopped)
				break;
			sample();
			next += INTERVAL_NANOS;
		}
		sample(); // the window's end
	}

	private void sample() {
		Map<String, Long> usage = new TreeMap<>();
		for (Map.Entry<String, String> e : onEveryNode(USAGE).entrySet()) {
			try {
				usage.put(e.getKey(), Long.parseLong(e.getValue().strip()));
			} catch (NumberFormatException x) {
				errors.add("cpu on " + e.getKey() + ": cannot read '" + e.getValue() + "'");
			}
		}
		times.add(System.nanoTime());
		usages.add(usage);
	}

	private Map<String, String> onEveryNode(String command) {
		Map<String, String> out = new TreeMap<>();
		try (PreparedStatement ps = connection.prepareStatement("SELECT n.nodename || ':' || n.nodeport, r.success, r.result"
				+ " FROM run_command_on_all_nodes(?) r JOIN pg_dist_node n ON n.nodeid = r.nodeid")) {
			ps.setString(1, command);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					if (rs.getBoolean(2))
						out.put(rs.getString(1), rs.getString(3));
					else
						errors.add("cpu on " + rs.getString(1) + ": " + rs.getString(3));
				}
			}
		} catch (SQLException e) {
			errors.add("cpu: " + e.getMessage());
		}
		return out;
	}

	Map<String, Object> summary(boolean emulated) {
		return summary(cores, times, usages, errors, emulated);
	}

	// separate from the sampling, for tests
	static Map<String, Object> summary(Map<String, Double> cores, List<Long> times, List<Map<String, Long>> usages, List<String> errors,
			boolean emulated) {
		Map<String, Object> out = new LinkedHashMap<>();
		out.put("intervalMillis", INTERVAL_NANOS / 1_000_000);
		out.put("threshold", THRESHOLD);
		out.put("cores", cores);
		List<Object> series = new ArrayList<>();
		Map<String, double[]> stats = new TreeMap<>(); // node -> { max, intervals above }
		for (int i = 1; i < times.size(); i++) {
			double seconds = (times.get(i) - times.get(i - 1)) / 1e9;
			Map<String, Object> point = new LinkedHashMap<>();
			point.put("t", Math.round((times.get(i) - times.get(0)) / 1e6) / 1000.0);
			Map<String, Object> util = new TreeMap<>();
			for (Map.Entry<String, Long> e : usages.get(i).entrySet()) {
				Long before = usages.get(i - 1).get(e.getKey());
				Double c = cores.get(e.getKey());
				if (before == null || c == null || seconds <= 0)
					continue;
				double u = (e.getValue() - before) / 1e6 / seconds / c;
				util.put(e.getKey(), Math.round(u * 10000) / 10000.0);
				double[] s = stats.computeIfAbsent(e.getKey(), k -> new double[2]);
				s[0] = Math.max(s[0], u);
				if (u > THRESHOLD)
					s[1]++;
			}
			point.put("utilization", util);
			series.add(point);
		}
		Map<String, Object> nodes = new TreeMap<>();
		List<String> saturated = new ArrayList<>();
		if (times.size() >= 2) {
			double seconds = (times.get(times.size() - 1) - times.get(0)) / 1e9;
			for (Map.Entry<String, Long> e : usages.get(usages.size() - 1).entrySet()) {
				Long first = usages.get(0).get(e.getKey());
				Double c = cores.get(e.getKey());
				if (first == null || c == null || seconds <= 0)
					continue;
				double mean = (e.getValue() - first) / 1e6 / seconds / c;
				double[] s = stats.getOrDefault(e.getKey(), new double[2]);
				Map<String, Object> n = new LinkedHashMap<>();
				n.put("mean", mean);
				n.put("max", s[0]);
				n.put("intervalsAboveThreshold", (long) s[1]);
				nodes.put(e.getKey(), n);
				if (mean > THRESHOLD)
					saturated.add(e.getKey());
			}
		}
		out.put("nodes", nodes);
		out.put("saturatedNodes", saturated);
		out.put("saturation", !saturated.isEmpty());
		out.put("valid", !emulated);
		out.put("rule", "a node is saturated when its mean cgroup CPU utilization over the measured window exceeds " + THRESHOLD
				+ " of its cores (Plan-phase4 2.6); invalid as a result when the cluster is emulated");
		out.put("series", series);
		out.put("errors", errors);
		return out;
	}
}
