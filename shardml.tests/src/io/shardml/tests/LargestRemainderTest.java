package io.shardml.tests;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.cost.LargestRemainder;

// Largest-remainder rounding, and the childless-parent shares Plan-phase3 section 5.A quotes
// for banking (10000 customers)
public class LargestRemainderTest {

	private static long childless(long children, int parents, double s) {
		long[] counts = LargestRemainder.apportion(children, LargestRemainder.zipfWeights(parents, s));
		Assertions.assertEquals(children, Arrays.stream(counts).sum());
		return Arrays.stream(counts).filter(c -> c == 0).count();
	}

	@Test
	public void testLeftoversGoToTheLowerRankOnTies() {
		Assertions.assertArrayEquals(new long[] { 1, 1, 0 }, LargestRemainder.apportion(2, LargestRemainder.zipfWeights(3, 0)));
		Assertions.assertArrayEquals(new long[] { 4, 3 }, LargestRemainder.apportion(7, LargestRemainder.zipfWeights(2, 0)));
	}

	@Test
	public void testLargestRemainderWins() {
		// 10 * (4/7, 2/7, 1/7) = 5.71, 2.86, 1.43: floors 5, 2, 1, and the two left over go to
		// the largest remainders .86 and .71
		Assertions.assertArrayEquals(new long[] { 6, 3, 1 }, LargestRemainder.apportion(10, new double[] { 4, 2, 1 }));
	}

	@Test
	public void testZipfWeights() {
		Assertions.assertArrayEquals(new double[] { 1, 0.25, 1.0 / 9 }, LargestRemainder.zipfWeights(3, 2), 1e-12);
		Assertions.assertArrayEquals(new double[] { 1, 1, 1 }, LargestRemainder.zipfWeights(3, 0), 0);
	}

	@Test
	public void testPlanChildlessShares() {
		// 23.9%, 63.4%, 0% and 16.5% in the plan
		Assertions.assertEquals(2385, childless(200_000, 10_000, 1.3));
		Assertions.assertEquals(6339, childless(200_000, 10_000, 1.5));
		Assertions.assertEquals(0, childless(1_000_000, 10_000, 1.4));
		Assertions.assertEquals(1650, childless(1_000_000, 10_000, 1.5));
	}
}
