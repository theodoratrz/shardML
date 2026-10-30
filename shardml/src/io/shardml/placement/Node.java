package io.shardml.placement;

/* A worker node as the model declares it: the logical name, and where Citus knows it. */
public record Node(String name, String host, int port) {

	@Override
	public String toString() {
		return name + " (" + host + ":" + port + ")";
	}
}
