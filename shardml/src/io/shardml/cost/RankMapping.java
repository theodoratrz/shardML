package io.shardml.cost;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.shardml.cost.Workload.Dist;
import io.shardml.cost.Workload.Domain;
import io.shardml.cost.Workload.KeyPath;
import io.shardml.cost.Workload.Zipf;

/* Ranks, values and parents of a workload's domains, for one global rank offset k; shared with
 * the Phase 4 loader and driver, so they agree with the model for any k.
 *  - A top-level domain: rank r is value ((r - 1 + k) mod size) + 1 (k reduced mod size).
 *  - A child (`within`) domain is never offset: its values are numbered 1, 2, ... contiguously in
 *    the parent's rank order, childCount(parent rank r) = size * pmf(r) by largest remainder (ties to
 *    the lower rank). So rank = value, and the parent of a child value is found by binary search
 *    on the prefix sums of the child counts, then mapped to the parent's value.
 * Everything per domain is built once, in arrays (child counts as prefix sums); KeyBuckets, the
 * value -> bucket array of a key path, is built once per (path, hasher, bucket count) and cached. */
public final class RankMapping {

	private final long offset;
	private final Map<String, Domain> domains = new LinkedHashMap<>();
	// a child domain's prefix sums: cumulative[r] = children of parent ranks 1..r; cumulative[0] = 0
	private final Map<String, long[]> cumulative = new HashMap<>();
	private final Map<List<Object>, KeyBuckets> buckets = new HashMap<>();

	private RankMapping(Workload workload, long offset) {
		this.offset = offset;
		for (Domain domain : workload.domains())
			domains.put(domain.name(), domain);
		for (Domain domain : workload.domains()) {
			if (domain.within() == null)
				continue;
			Domain parent = domain(domain.within().parent());
			long[] counts = LargestRemainder.apportion(domain.size(), weights((int) parent.size(), domain.within().children()));
			long[] sums = new long[counts.length + 1];
			for (int r = 1; r <= counts.length; r++)
				sums[r] = sums[r - 1] + counts[r - 1];
			cumulative.put(domain.name(), sums);
		}
	}

	// offset >= 0; it is reduced mod each top-level domain's size
	public static RankMapping of(Workload workload, long offset) {
		if (offset < 0)
			throw new IllegalArgumentException("The rank offset is " + offset + "; it must be at least 0.");
		return new RankMapping(workload, offset);
	}

	// the weights of ranks 1..n under a distribution (uniform is zipf(0))
	static double[] weights(int n, Dist dist) {
		return LargestRemainder.zipfWeights(n, dist instanceof Zipf zipf ? zipf.s() : 0);
	}

	public long offset() {
		return offset;
	}

	public long size(String domain) {
		return domain(domain).size();
	}

	// the parent domain's name, or null for a top-level domain
	public String parentOf(String domain) {
		Domain d = domain(domain);
		return d.within() == null ? null : d.within().parent();
	}

	public long value(String domain, long rank) {
		Domain d = check(domain, rank);
		if (d.within() != null)
			return rank;
		return Math.floorMod(rank - 1 + offset, d.size()) + 1;
	}

	public long rank(String domain, long value) {
		Domain d = check(domain, value);
		if (d.within() != null)
			return value;
		return Math.floorMod(value - 1 - offset, d.size()) + 1;
	}

	// the parent's value of a child value
	public long parent(String child, long value) {
		check(child, value);
		long[] sums = childSums(child);
		// the smallest parent rank r with sums[r] >= value
		int lo = 1;
		int hi = sums.length - 1;
		while (lo < hi) {
			int mid = (lo + hi) >>> 1;
			if (sums[mid] >= value)
				hi = mid;
			else
				lo = mid + 1;
		}
		return value(domain(child).within().parent(), lo);
	}

	// the first child value of a parent value; 0 children: the value its children would start at
	public long firstChild(String child, long parentValue) {
		long[] sums = childSums(child);
		long r = rank(domain(child).within().parent(), parentValue);
		return sums[(int) r - 1] + 1;
	}

	public long childCount(String child, long parentValue) {
		long[] sums = childSums(child);
		long r = rank(domain(child).within().parent(), parentValue);
		return sums[(int) r] - sums[(int) r - 1];
	}

	// a value of path.domain() mapped to its ancestor path.target(), through every parent on the way
	public long project(KeyPath path, long value) {
		String current = path.domain();
		long v = value;
		for (String target : path.parents()) {
			while (!current.equals(target)) {
				String parent = parentOf(current);
				if (parent == null)
					throw new IllegalArgumentException("Domain '" + target + "' is not an ancestor of '" + path.domain() + "'.");
				v = parent(current, v);
				current = parent;
			}
		}
		return v;
	}

	// project(path, v) for many values: the path resolved once into its prefix sums and the offsets of
	// its top-level parents, then an array walk per value (no lookup by name). The same values as project
	public java.util.function.LongUnaryOperator projector(KeyPath path) {
		List<long[]> sums = new java.util.ArrayList<>();
		List<long[]> parents = new java.util.ArrayList<>(); // { 1 if the parent is top-level, its size }
		String current = path.domain();
		for (String target : path.parents()) {
			while (!current.equals(target)) {
				String parent = parentOf(current);
				if (parent == null)
					throw new IllegalArgumentException("Domain '" + target + "' is not an ancestor of '" + path.domain() + "'.");
				sums.add(childSums(current));
				Domain p = domain(parent);
				parents.add(new long[] { p.within() == null ? 1 : 0, p.size() });
				current = parent;
			}
		}
		long[][] steps = sums.toArray(long[][]::new);
		long[][] parentInfo = parents.toArray(long[][]::new);
		long k = offset;
		return value -> {
			long v = value;
			for (int s = 0; s < steps.length; s++) {
				long[] cumulative = steps[s];
				int lo = 1;
				int hi = cumulative.length - 1;
				while (lo < hi) {
					int mid = (lo + hi) >>> 1;
					if (cumulative[mid] >= v)
						hi = mid;
					else
						lo = mid + 1;
				}
				v = parentInfo[s][0] == 1 ? Math.floorMod(lo - 1 + k, parentInfo[s][1]) + 1 : lo;
			}
			return v;
		};
	}

	// the bucket of every value of path.domain(), through the path, hashed into the given bucket
	// count: built once and cached
	public synchronized KeyBuckets buckets(KeyPath path, KeyHasher hasher, int bucketCount) {
		List<Object> key = List.of(path, hasher, bucketCount);
		KeyBuckets cached = buckets.get(key);
		if (cached == null) {
			int size = (int) size(path.domain());
			int[] array = new int[size];
			java.util.function.LongUnaryOperator projector = projector(path);
			for (int v = 1; v <= size; v++)
				array[v - 1] = hasher.bucket(projector.applyAsLong(v), bucketCount);
			cached = new KeyBuckets(array, bucketCount);
			buckets.put(key, cached);
		}
		return cached;
	}

	private long[] childSums(String child) {
		long[] sums = cumulative.get(child);
		if (sums == null)
			throw new IllegalArgumentException("Domain '" + child + "' is not a child domain.");
		return sums;
	}

	private Domain domain(String name) {
		Domain d = domains.get(name);
		if (d == null)
			throw new IllegalArgumentException("No domain '" + name + "'.");
		return d;
	}

	private Domain check(String name, long valueOrRank) {
		Domain d = domain(name);
		if (valueOrRank < 1 || valueOrRank > d.size())
			throw new IllegalArgumentException(valueOrRank + " is outside domain '" + name + "' (1.." + d.size() + ").");
		return d;
	}
}
