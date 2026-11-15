package io.shardml.cost;

/* One point of a param sweep: the swept param, its value as given on the command line, and the
 * report of one layout at that value. */
public record SweepPoint(String param, String value, CostReport report) {
}
