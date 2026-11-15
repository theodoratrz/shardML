package io.shardml.bench;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.HdrHistogram.Histogram;

import io.shardml.command.CostCommand;
import io.shardml.command.Inputs;
import io.shardml.cost.Layout;
import io.shardml.cost.RankMapping;
import io.shardml.cost.StatementSql;
import io.shardml.cost.Workload;

/* The Phase 4 validation driver (Plan-phase4 P4.1, 2.2-2.6):
 *
 *   driver run --mode closed|open --targets host:port[,host:port...] [options] <workload> <model.shardml>
 *   driver verify-distributions [--draws N] [--seed S] [--set n=v] [--rank-offset k] <workload> [<model.shardml>...]
 *
 * `run` replays the workload under the model's layout for a warm-up and a measured window and writes
 * result.json and manifest.json into a run directory determined by its arguments. Client i connects to
 * targets[i mod |targets|]: list the workers for mode (b), the coordinator alone for mode (a). */
public final class Driver {

	static final int OK = 0;
	static final int FAIL = 1;
	static final int USAGE = 2;

	static final String USAGE_TEXT = String.join(System.lineSeparator(),
			"Usage: driver run [options] <workload.workload> <model.shardml>",
			"  --mode closed|open        closed: N clients back to back; open: Poisson arrivals at --rate",
			"  --clients N               clients, one connection each (default 8)",
			"  --rate R                  open loop: target transactions per second (required)",
			"  --warmup S                warm-up seconds, discarded (default 120)",
			"  --duration S              measured seconds (default 300)",
			"  --drain S                 open loop: seconds after the window to start late arrivals (default 10)",
			"  --targets h:p[,h:p..]     client i connects to target i mod n (required)",
			"  --entry workers|coordinator  how the targets enter the cluster, for the manifest (default workers)",
			"  --database D --user U     (default postgres, postgres)",
			"  --schema S                search_path of every connection (default: the server's)",
			"  --seed S --rep R          run seed (default 1), repetition number (default 1)",
			"  --set name=value          override a workload param (repeatable)",
			"  --rank-offset k           rotate the top-level domains' ranks by k (default 0)",
			"  --retries K               retry serialization failures and deadlocks up to K times (default 0)",
			"  --prepare-threshold N     pgjdbc prepareThreshold (default 5)",
			"  --statement-timeout-ms T  statement_timeout of every connection (default 30000)",
			"  --git-commit C            the commit to record when git is not available (default: git rev-parse HEAD)",
			"  --out DIR                 root of the run directories (default bench-runs)",
			"Usage: driver verify-distributions [--draws N] [--seed S] [--set n=v] [--rank-offset k] <workload> [<model>...]",
			"  the sampler against the model's exact distributions (default 1000000 draws per transaction, seed 1)");

	private Driver() {
	}

	public static void main(String[] args) {
		System.exit(run(List.of(args), System.out, System.err));
	}

	static int run(List<String> args, PrintStream out, PrintStream err) {
		if (args.isEmpty() || args.get(0).equals("--help")) {
			(args.isEmpty() ? err : out).println(USAGE_TEXT);
			return args.isEmpty() ? USAGE : OK;
		}
		try {
			Options o = Options.parse(args.subList(1, args.size()), args.get(0));
			return switch (args.get(0)) {
				case "run" -> runLoad(o, out, err);
				case "verify-distributions" -> verify(o, out, err);
				default -> throw new UsageError("unknown command '" + args.get(0) + "'");
			};
		} catch (UsageError e) {
			err.println("driver: " + e.getMessage());
			err.println(USAGE_TEXT);
			return USAGE;
		} catch (IllegalArgumentException e) {
			err.println("driver: error: " + e.getMessage());
			return FAIL;
		}
	}

	static final class UsageError extends Exception {
		UsageError(String message) {
			super(message);
		}
	}

	// --- options ---

	record Options(String mode, int clients, double rate, int warmup, int duration, int drain, List<String> targets, String entry,
			String database, String user, String schema, long seed, int rep, Map<String, String> sets, long rankOffset, int retries,
			int prepareThreshold, int statementTimeoutMs, String gitCommit, Path out, long draws, File workload, List<File> models,
			List<String> argv) {

		static Options parse(List<String> args, String command) throws UsageError {
			String mode = null;
			int clients = 8;
			double rate = 0;
			int warmup = 120;
			int duration = 300;
			int drain = 10;
			List<String> targets = new ArrayList<>();
			String entry = "workers";
			String database = "postgres";
			String user = "postgres";
			String schema = null;
			long seed = 1;
			int rep = 1;
			Map<String, String> sets = new TreeMap<>();
			long rankOffset = 0;
			int retries = 0;
			int prepareThreshold = 5;
			int statementTimeoutMs = 30000;
			String gitCommit = null;
			Path out = Path.of("bench-runs");
			long draws = 1_000_000;
			File workload = null;
			List<File> models = new ArrayList<>();
			for (int i = 0; i < args.size(); i++) {
				String a = args.get(i);
				switch (a) {
					case "--mode" -> {
						mode = value(args, ++i, a);
						if (!mode.equals("closed") && !mode.equals("open"))
							throw new UsageError("--mode is closed or open, got '" + mode + "'");
					}
					case "--clients" -> clients = (int) whole(value(args, ++i, a), a, 1);
					case "--rate" -> {
						try {
							rate = Double.parseDouble(value(args, ++i, a));
						} catch (NumberFormatException e) {
							throw new UsageError("--rate needs a number");
						}
						if (!(rate > 0))
							throw new UsageError("--rate must be positive");
					}
					case "--warmup" -> warmup = (int) whole(value(args, ++i, a), a, 0);
					case "--duration" -> duration = (int) whole(value(args, ++i, a), a, 1);
					case "--drain" -> drain = (int) whole(value(args, ++i, a), a, 0);
					case "--targets" -> {
						for (String t : value(args, ++i, a).split(",")) {
							if (!t.matches("[^:,\\s]+:\\d+"))
								throw new UsageError("--targets needs host:port entries, got '" + t + "'");
							targets.add(t);
						}
					}
					case "--entry" -> {
						entry = value(args, ++i, a);
						if (!entry.equals("workers") && !entry.equals("coordinator"))
							throw new UsageError("--entry is workers or coordinator, got '" + entry + "'");
					}
					case "--database" -> database = value(args, ++i, a);
					case "--user" -> user = value(args, ++i, a);
					case "--schema" -> {
						schema = value(args, ++i, a);
						if (!schema.matches("[a-z_][a-z0-9_]*"))
							throw new UsageError("--schema needs a plain lower-case name, got '" + schema + "'");
					}
					case "--seed" -> seed = whole(value(args, ++i, a), a, Long.MIN_VALUE);
					case "--rep" -> rep = (int) whole(value(args, ++i, a), a, 1);
					case "--set" -> {
						String v = value(args, ++i, a);
						int eq = v.indexOf('=');
						if (eq <= 0)
							throw new UsageError("--set needs name=value, got '" + v + "'");
						if (sets.put(v.substring(0, eq), v.substring(eq + 1)) != null)
							throw new UsageError("--set " + v.substring(0, eq) + " given twice");
					}
					case "--rank-offset" -> rankOffset = whole(value(args, ++i, a), a, 0);
					case "--retries" -> retries = (int) whole(value(args, ++i, a), a, 0);
					case "--prepare-threshold" -> prepareThreshold = (int) whole(value(args, ++i, a), a, 0);
					case "--statement-timeout-ms" -> statementTimeoutMs = (int) whole(value(args, ++i, a), a, 0);
					case "--git-commit" -> gitCommit = value(args, ++i, a);
					case "--out" -> out = Path.of(value(args, ++i, a));
					case "--draws" -> draws = whole(value(args, ++i, a), a, 1);
					default -> {
						if (a.startsWith("-"))
							throw new UsageError("unknown option '" + a + "'");
						File f = new File(a);
						if (!f.isFile())
							throw new UsageError("no such file: " + a);
						if (a.endsWith(".workload") && workload == null)
							workload = f;
						else if (a.endsWith(".shardml"))
							models.add(f);
						else
							throw new UsageError("expected one .workload and .shardml models, got '" + a + "'");
					}
				}
			}
			if (workload == null)
				throw new UsageError("needs a .workload");
			if (command.equals("run")) {
				if (models.size() != 1)
					throw new UsageError("run needs exactly one .shardml model");
				if (mode == null)
					throw new UsageError("run needs --mode closed or --mode open");
				if (targets.isEmpty())
					throw new UsageError("run needs --targets");
				if (mode.equals("open") && rate == 0)
					throw new UsageError("--mode open needs --rate");
				if (mode.equals("closed") && rate != 0)
					throw new UsageError("--rate is for --mode open only");
			}
			return new Options(mode, clients, rate, warmup, duration, drain, List.copyOf(targets), entry, database, user, schema, seed, rep,
					sets, rankOffset, retries, prepareThreshold, statementTimeoutMs, gitCommit, out, draws, workload, List.copyOf(models),
					List.copyOf(args));
		}

		private static String value(List<String> args, int i, String option) throws UsageError {
			if (i >= args.size())
				throw new UsageError(option + " needs a value");
			return args.get(i);
		}

		private static long whole(String v, String option, long min) throws UsageError {
			try {
				long n = Long.parseLong(v);
				if (n >= min)
					return n;
			} catch (NumberFormatException e) {
				// below
			}
			throw new UsageError(option + " needs a whole number" + (min > Long.MIN_VALUE ? " of at least " + min : "") + ", got '" + v + "'");
		}
	}

	// --- verify-distributions ---

	private static int verify(Options o, PrintStream out, PrintStream err) {
		Inputs.Loaded loaded = Inputs.load(o.workload(), o.models(), o.sets(), err);
		RankMapping mapping = RankMapping.of(loaded.workload(), o.rankOffset());
		VerifyDistributions.Report report = VerifyDistributions.run(loaded.workload(), loaded.layouts(), mapping, o.draws(), o.seed());
		out.println("verify-distributions: workload " + loaded.workload().name() + ", " + o.draws() + " draws per transaction, seed "
				+ o.seed() + ", rank offset " + o.rankOffset() + (o.sets().isEmpty() ? "" : ", set " + o.sets()) + ", layouts "
				+ loaded.layouts().stream().map(Layout::name).toList());
		out.print(VerifyDistributions.format(report));
		return report.passed() ? OK : FAIL;
	}

	// --- run ---

	// <out>/<workload>/<layout>/<entry>/<mode>-c<N>[-r<rate>]/<params>-k<offset>/seed-<S>-rep-<R>
	static Path runDirectory(Options o, String workload, String layout) {
		String load = o.mode() + "-c" + o.clients() + (o.mode().equals("open") ? "-r" + plain(o.rate()) : "");
		String params = o.sets().isEmpty() ? "defaults"
				: String.join(",", o.sets().entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).toList());
		return o.out().resolve(workload).resolve(layout).resolve(o.entry()).resolve(load).resolve(params + "-k" + o.rankOffset())
				.resolve("seed-" + o.seed() + "-rep-" + o.rep());
	}

	private static String plain(double d) {
		return d == Math.rint(d) ? String.valueOf((long) d) : String.valueOf(d);
	}

	private static int runLoad(Options o, PrintStream out, PrintStream err) {
		Inputs.Loaded loaded = Inputs.load(o.workload(), o.models(), o.sets(), err);
		Workload workload = loaded.workload();
		Layout layout = loaded.layouts().get(0);
		RankMapping mapping = RankMapping.of(workload, o.rankOffset());
		Replay replay = new Replay(workload, layout, mapping);
		Path dir = runDirectory(o, workload.name(), layout.name());
		if (Files.exists(dir))
			throw new IllegalArgumentException("run directory " + dir + " exists; a run never overwrites one (change --rep or --out)");
		Map<String, String> settings = JdbcSession.properties(o.user(), o.schema(), o.prepareThreshold(), o.statementTimeoutMs(), 10,
				Math.max(60, o.statementTimeoutMs() / 1000 + 30));
		List<JdbcSession> sessions = new ArrayList<>();
		for (int i = 0; i < o.clients(); i++) {
			String url = "jdbc:postgresql://" + o.targets().get(i % o.targets().size()) + "/" + o.database();
			sessions.add(new JdbcSession(url, settings, replay, layout));
		}
		// every client connects before the run starts: an unreachable target is an error, not a failed transaction
		for (JdbcSession s : sessions) {
			try {
				s.execute(new Replay.Run(0, List.of()));
			} catch (java.sql.SQLException e) {
				sessions.forEach(JdbcSession::close);
				throw new IllegalArgumentException("cannot connect: " + e.getMessage());
			}
		}
		Map<String, Object> manifest = manifest(o, loaded, replay, settings, err);
		Instant started = Instant.now();
		manifest.put("startedAt", started.toString());
		try {
			Files.createDirectories(dir);
			Files.writeString(dir.resolve("manifest.json"), Json.write(manifest));
		} catch (IOException e) {
			throw new IllegalArgumentException("cannot write " + dir + ": " + e.getMessage());
		}
		out.println("driver: " + o.mode() + " loop, " + o.clients() + " clients" + (o.mode().equals("open") ? ", " + plain(o.rate()) + " tx/s" : "")
				+ ", warm-up " + o.warmup() + " s, measured " + o.duration() + " s -> " + dir);

		Streams streams = new Streams(o.seed(), o.clients());
		long t0 = System.nanoTime() + 200_000_000L;
		long warmEnd = t0 + o.warmup() * 1_000_000_000L;
		long end = warmEnd + o.duration() * 1_000_000_000L;
		long drainEnd = end + o.drain() * 1_000_000_000L;
		List<Client> clients = new ArrayList<>();
		List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < o.clients(); i++) {
			Schedule schedule = o.mode().equals("open") ? new Schedule(streams.arrivals(i), o.rate() / o.clients()) : null;
			Client c = new Client(replay, sessions.get(i), streams.draws(i), schedule, o.retries(), t0, warmEnd, end, drainEnd, Client.REAL);
			clients.add(c);
			Thread t = new Thread(c, "client-" + i);
			threads.add(t);
		}
		CpuMeter cpu = new CpuMeter(warmEnd, end);
		Thread cpuThread = new Thread(cpu, "cpu-meter");
		cpuThread.setDaemon(true);
		cpuThread.start();
		threads.forEach(Thread::start);
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new IllegalStateException("interrupted");
			}
		}
		try {
			cpuThread.join(5000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		Stats total = new Stats(replay.transactions().size());
		List<String> crashes = new ArrayList<>();
		for (int i = 0; i < clients.size(); i++) {
			total.add(clients.get(i).stats);
			if (clients.get(i).crash() != null)
				crashes.add("client " + i + ": " + clients.get(i).crash());
		}
		Map<String, Object> result = result(o, replay, total, cpu.summary(Runtime.getRuntime().availableProcessors()), crashes);
		manifest.put("finishedAt", Instant.now().toString());
		try {
			Files.writeString(dir.resolve("result.json"), Json.write(result));
			Files.writeString(dir.resolve("manifest.json"), Json.write(manifest));
		} catch (IOException e) {
			throw new IllegalArgumentException("cannot write " + dir + ": " + e.getMessage());
		}
		out.print(summary(result));
		if (!crashes.isEmpty()) {
			err.println("driver: client crashes: " + crashes);
			return FAIL;
		}
		return OK;
	}

	@SuppressWarnings("unchecked")
	static String summary(Map<String, Object> result) {
		Map<String, Object> tp = (Map<String, Object>) result.get("throughput");
		Map<String, Object> lat = (Map<String, Object>) ((Map<String, Object>) result.get("latencyMicros")).get("overall");
		Map<String, Object> cpu = (Map<String, Object>) result.get("driverCpu");
		return String.format("committed %s (%.1f tx/s), failed %s %s, p50 %s us, p99 %s us, max %s us, driver CPU mean %.1f%% max %.1f%%%s%n",
				tp.get("committed"), (Double) tp.get("committedPerSecond"), tp.get("failed"), result.get("errors"), lat.get("p50"),
				lat.get("p99"), lat.get("max"), 100 * (Double) cpu.get("mean"), 100 * (Double) cpu.get("max"),
				Boolean.TRUE.equals(cpu.get("overLimit")) ? " OVER THE 50% LIMIT" : "");
	}

	static Map<String, Object> result(Options o, Replay replay, Stats s, CpuMeter.Summary cpu, List<String> crashes) {
		Map<String, Object> r = new LinkedHashMap<>();
		r.put("format", "shardml-driver-result/1");
		r.put("mode", o.mode());
		r.put("entry", o.entry());
		r.put("clients", o.clients());
		r.put("targetRate", o.mode().equals("open") ? o.rate() : null);
		r.put("warmupSeconds", o.warmup());
		r.put("measuredSeconds", o.duration());
		double seconds = o.duration();
		Map<String, Object> tp = new LinkedHashMap<>();
		tp.put("committed", s.committed);
		tp.put("committedPerSecond", s.committed / seconds);
		tp.put("committedAfterRetry", s.committedAfterRetry);
		tp.put("committedAfterRetryPerSecond", s.committedAfterRetry / seconds);
		tp.put("failed", s.failedTotal());
		if (o.mode().equals("open")) {
			tp.put("offered", s.offered);
			tp.put("offeredPerSecond", s.offered / seconds);
			tp.put("achievedPerSecond", (s.committed + s.committedAfterRetry) / seconds);
			tp.put("unstarted", s.unstarted);
		}
		r.put("throughput", tp);
		Map<String, Object> errors = new LinkedHashMap<>();
		for (ErrorClass c : ErrorClass.values())
			errors.put(c.key(), s.failed[c.ordinal()]);
		r.put("errors", errors);
		Map<String, Object> retries = new LinkedHashMap<>();
		retries.put("maxRetries", o.retries());
		retries.put("retryAttempts", s.retries);
		retries.put("committedAfterRetry", s.committedAfterRetry);
		retries.put("latencyMicros", histogram(s.afterRetry));
		r.put("retries", retries);
		Map<String, Object> latency = new LinkedHashMap<>();
		latency.put("from", o.mode().equals("open") ? "intended start (the arrival schedule)" : "start");
		latency.put("overall", histogram(s.overall));
		Map<String, Object> byTemplate = new LinkedHashMap<>();
		for (int t = 0; t < replay.transactions().size(); t++) {
			Map<String, Object> h = histogram(s.byTemplate[t]);
			Map<String, Object> f = new LinkedHashMap<>();
			for (ErrorClass c : ErrorClass.values())
				f.put(c.key(), s.failedByTemplate[t][c.ordinal()]);
			h.put("failed", f);
			byTemplate.put(replay.transactions().get(t).name(), h);
		}
		latency.put("byTemplate", byTemplate);
		r.put("latencyMicros", latency);
		if (o.mode().equals("open"))
			r.put("scheduleLagMicros", histogram(s.lag));
		Map<String, Object> c = new LinkedHashMap<>();
		c.put("cores", cpu.cores());
		c.put("samples", cpu.samples());
		c.put("mean", cpu.mean());
		c.put("max", cpu.max());
		c.put("limit", cpu.limit());
		c.put("overLimit", cpu.overLimit());
		r.put("driverCpu", c);
		Map<String, Object> accounting = new LinkedHashMap<>();
		long ended = s.committed + s.committedAfterRetry + s.failedTotal();
		accounting.put("started", s.started);
		accounting.put("ended", ended);
		accounting.put("balanced", ended == s.started && (!o.mode().equals("open") || s.offered == s.started + s.unstarted));
		r.put("accounting", accounting);
		r.put("clientCrashes", crashes);
		return r;
	}

	static Map<String, Object> histogram(Histogram h) {
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("count", h.getTotalCount());
		boolean empty = h.getTotalCount() == 0;
		m.put("p50", empty ? null : h.getValueAtPercentile(50));
		m.put("p95", empty ? null : h.getValueAtPercentile(95));
		m.put("p99", empty ? null : h.getValueAtPercentile(99));
		m.put("max", empty ? null : h.getMaxValue());
		m.put("mean", empty ? null : h.getMean());
		ByteBuffer buffer = ByteBuffer.allocate(h.getNeededByteBufferCapacity());
		int length = h.encodeIntoCompressedByteBuffer(buffer);
		m.put("hdrHistogramBase64", Base64.getEncoder().encodeToString(java.util.Arrays.copyOf(buffer.array(), length)));
		return m;
	}

	// --- manifest ---

	private static Map<String, Object> manifest(Options o, Inputs.Loaded loaded, Replay replay, Map<String, String> settings, PrintStream err) {
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("format", "shardml-driver-manifest/1");
		Map<String, Object> host = new LinkedHashMap<>();
		try {
			host.put("hostname", InetAddress.getLocalHost().getHostName());
		} catch (IOException e) {
			host.put("hostname", "unknown");
		}
		host.put("os", System.getProperty("os.name") + " " + System.getProperty("os.version") + " " + System.getProperty("os.arch"));
		host.put("availableProcessors", Runtime.getRuntime().availableProcessors());
		m.put("host", host);
		Map<String, Object> jvm = new LinkedHashMap<>();
		jvm.put("version", System.getProperty("java.version"));
		jvm.put("vendor", System.getProperty("java.vendor"));
		jvm.put("vm", System.getProperty("java.vm.name") + " " + System.getProperty("java.vm.version"));
		jvm.put("inputArguments", ManagementFactory.getRuntimeMXBean().getInputArguments());
		jvm.put("maxHeapBytes", Runtime.getRuntime().maxMemory());
		m.put("jvm", jvm);
		Map<String, Object> driver = new LinkedHashMap<>();
		Properties versions = versions();
		driver.put("driverVersion", versions.getProperty("driver", "unknown"));
		driver.put("pgjdbc", versions.getProperty("pgjdbc", "unknown"));
		driver.put("hdrHistogram", versions.getProperty("hdrhistogram", "unknown"));
		driver.put("mode", o.mode());
		driver.put("clients", o.clients());
		driver.put("targetRate", o.mode().equals("open") ? o.rate() : null);
		driver.put("thinkTime", 0);
		driver.put("warmupSeconds", o.warmup());
		driver.put("measuredSeconds", o.duration());
		driver.put("drainSeconds", o.mode().equals("open") ? o.drain() : null);
		driver.put("entry", o.entry());
		driver.put("targets", o.targets());
		driver.put("clientTarget", "client i connects to targets[i mod " + o.targets().size() + "]");
		driver.put("database", o.database());
		driver.put("connectionProperties", settings);
		driver.put("autocommit", false);
		driver.put("preparedStatements", "one PreparedStatement per statement per connection");
		driver.put("transaction", "one per template run: statements, then COMMIT; ROLLBACK on error");
		driver.put("retries", o.retries());
		driver.put("connectionErrorPauseMillis", Client.CONNECTION_PAUSE_NANOS / 1_000_000);
		m.put("driver", driver);
		Map<String, Object> seeds = new LinkedHashMap<>();
		seeds.put("seed", o.seed());
		seeds.put("rep", o.rep());
		seeds.put("streams", Streams.DESCRIPTION);
		m.put("seeds", seeds);
		Map<String, Object> workload = new LinkedHashMap<>();
		workload.put("name", loaded.workload().name());
		workload.put("layout", loaded.layouts().get(0).name());
		workload.put("set", o.sets());
		workload.put("rankOffset", o.rankOffset());
		Map<String, Object> semantics = new LinkedHashMap<>();
		semantics.put("reads", "SELECT, every row read");
		semantics.put("writes", "UPDATE of the payload column to its own value (StatementSql.payloadColumn), no row count change");
		semantics.put("deletes", "refused (a delete changes the snapshot)");
		semantics.put("inserts", "none (the workload language has none; Plan-phase4 2.4)");
		workload.put("replay", semantics);
		Map<String, Object> templates = new LinkedHashMap<>();
		for (int t = 0; t < replay.transactions().size(); t++) {
			List<String> sql = new ArrayList<>();
			for (StatementSql.Template template : replay.templates(t))
				sql.add(template.sql());
			Map<String, Object> entry = new LinkedHashMap<>();
			entry.put("weight", replay.transactions().get(t).weight());
			entry.put("statements", sql);
			templates.put(replay.transactions().get(t).name(), entry);
		}
		workload.put("templates", templates);
		m.put("workload", workload);
		m.put("files", hashes(o));
		m.put("git", git(o));
		m.put("modelReport", modelReport(o, err));
		m.put("arguments", o.argv());
		return m;
	}

	private static Properties versions() {
		Properties p = new Properties();
		try (var in = Driver.class.getResourceAsStream("versions.properties")) {
			if (in != null)
				p.load(in);
		} catch (IOException e) {
			// unknown versions are recorded as such
		}
		return p;
	}

	// the cost report of this layout and workload, as `shardml cost --json` prints it
	private static Object modelReport(Options o, PrintStream err) {
		List<String> args = new ArrayList<>();
		args.add("--json");
		o.sets().forEach((k, v) -> {
			args.add("--set");
			args.add(k + "=" + v);
		});
		args.add("--rank-offset");
		args.add(String.valueOf(o.rankOffset()));
		args.add(o.workload().getPath());
		args.add(o.models().get(0).getPath());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream errors = new ByteArrayOutputStream();
		int exit = CostCommand.run(args, new PrintStream(out, true, StandardCharsets.UTF_8), new PrintStream(errors, true, StandardCharsets.UTF_8));
		if (exit != 0) {
			err.println("driver: warning: shardml cost failed (" + exit + "): " + errors.toString(StandardCharsets.UTF_8).strip());
			return Map.of("error", errors.toString(StandardCharsets.UTF_8).strip());
		}
		return new Json.Raw(out.toString(StandardCharsets.UTF_8));
	}

	private static final Pattern IMPORT = Pattern.compile("import\\s+\"([^\"]+)\"");

	// SHA-256 of the workload, the model and the .msql files they import
	static Map<String, Object> hashes(Options o) {
		Map<String, Object> out = new LinkedHashMap<>();
		List<File> files = new ArrayList<>();
		files.add(o.workload());
		files.addAll(o.models());
		for (int i = 0; i < files.size(); i++) {
			File f = files.get(i);
			try {
				String text = Files.readString(f.toPath());
				out.put(display(f), sha256(Files.readAllBytes(f.toPath())));
				Matcher m = IMPORT.matcher(text);
				while (m.find()) {
					File imported = new File(f.getAbsoluteFile().getParentFile(), m.group(1));
					if (files.stream().noneMatch(x -> x.getAbsoluteFile().toPath().normalize().equals(imported.toPath().normalize())))
						files.add(imported);
				}
			} catch (IOException e) {
				out.put(display(f), "unreadable: " + e.getMessage());
			}
		}
		return out;
	}

	// the file's path, normalized, relative to the working directory when it is under it
	private static String display(File f) {
		Path p = f.toPath().toAbsolutePath().normalize();
		Path cwd = Path.of("").toAbsolutePath();
		return (p.startsWith(cwd) ? cwd.relativize(p) : p).toString();
	}

	static String sha256(byte[] bytes) {
		try {
			StringBuilder hex = new StringBuilder();
			for (byte b : MessageDigest.getInstance("SHA-256").digest(bytes))
				hex.append(String.format("%02x", b));
			return hex.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}

	private static Map<String, Object> git(Options o) {
		Map<String, Object> g = new LinkedHashMap<>();
		String commit = command("git", "rev-parse", "HEAD");
		if (commit == null) {
			g.put("commit", o.gitCommit() == null ? "unknown" : o.gitCommit());
			g.put("source", o.gitCommit() == null ? "git not available" : "--git-commit");
		} else {
			g.put("commit", commit);
			String status = command("git", "status", "--porcelain", "--untracked-files=no");
			g.put("dirty", status == null ? null : !status.isEmpty());
			g.put("source", "git rev-parse HEAD");
		}
		return g;
	}

	private static String command(String... args) {
		try {
			Process p = new ProcessBuilder(args).redirectErrorStream(true).start();
			String text = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8).strip();
			return p.waitFor() == 0 ? text : null;
		} catch (IOException e) {
			return null;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return null;
		}
	}
}
