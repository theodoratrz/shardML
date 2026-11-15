package io.shardml.bench;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.function.LongSupplier;

/* The driver's own CPU over the measured window: the process CPU time
 * (com.sun.management.OperatingSystemMXBean.getProcessCpuTime, all threads; on Linux read from
 * /proc/self) sampled once a second, as a fraction of the cores the JVM may use
 * (Runtime.availableProcessors, which honours the container's cpuset and quota). The run is flagged
 * when the mean over the window exceeds the limit (Plan-phase4 2.6: 50%); the largest one-second
 * fraction is reported too. */
final class CpuMeter implements Runnable {

	static final double LIMIT = 0.5;

	record Summary(int cores, int samples, double mean, double max, double limit, boolean overLimit) {
	}

	private final LongSupplier cpu;
	private final LongSupplier clock;
	private final long from;
	private final long to;
	private final List<long[]> samples = new ArrayList<>();

	CpuMeter(long from, long to) {
		this(processCpu(), System::nanoTime, from, to);
	}

	CpuMeter(LongSupplier cpu, LongSupplier clock, long from, long to) {
		this.cpu = cpu;
		this.clock = clock;
		this.from = from;
		this.to = to;
	}

	static LongSupplier processCpu() {
		java.lang.management.OperatingSystemMXBean bean = ManagementFactory.getOperatingSystemMXBean();
		if (bean instanceof com.sun.management.OperatingSystemMXBean sun)
			return sun::getProcessCpuTime;
		return () -> -1;
	}

	@Override
	public void run() {
		long next = from;
		while (true) {
			long now = clock.getAsLong();
			while (now < next) {
				java.util.concurrent.locks.LockSupport.parkNanos(next - now);
				if (Thread.currentThread().isInterrupted())
					return;
				now = clock.getAsLong();
			}
			synchronized (samples) {
				samples.add(new long[] { now, cpu.getAsLong() });
			}
			if (next >= to)
				return;
			next = Math.min(to, next + 1_000_000_000L);
		}
	}

	Summary summary(int cores) {
		synchronized (samples) {
			return summarize(samples, cores, LIMIT);
		}
	}

	// samples: {wall nanos, process CPU nanos}, in time order
	static Summary summarize(List<long[]> samples, int cores, double limit) {
		if (samples.size() < 2 || samples.get(0)[1] < 0)
			return new Summary(cores, samples.size(), Double.NaN, Double.NaN, limit, false);
		long[] first = samples.get(0);
		long[] last = samples.get(samples.size() - 1);
		double mean = (double) (last[1] - first[1]) / ((double) (last[0] - first[0]) * cores);
		double max = 0;
		for (int i = 1; i < samples.size(); i++) {
			long wall = samples.get(i)[0] - samples.get(i - 1)[0];
			if (wall > 0)
				max = Math.max(max, (double) (samples.get(i)[1] - samples.get(i - 1)[1]) / ((double) wall * cores));
		}
		return new Summary(cores, samples.size(), mean, max, limit, mean > limit);
	}
}
