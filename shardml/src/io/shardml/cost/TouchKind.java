package io.shardml.cost;

/* The kinds of touch and of statement P1 costs separately (Plan-phase4 1a): a statement's route (Routing:
 * routed to one bucket, unrouted over every bucket, reference-only) and whether it reads or writes. */
public enum TouchKind {
	ROUTED_READ("routed read", "routed_read"),
	ROUTED_WRITE("routed write", "routed_write"),
	UNROUTED_READ("unrouted read", "unrouted_read"),
	UNROUTED_WRITE("unrouted write", "unrouted_write"),
	REFERENCE_READ("reference read", "reference_read"),
	REFERENCE_WRITE("reference write", "reference_write");

	private final String label;
	private final String key;

	TouchKind(String label, String key) {
		this.label = label;
		this.key = key;
	}

	public String label() {
		return label;
	}

	// snake case, for JSON
	public String key() {
		return key;
	}

	public boolean write() {
		return this == ROUTED_WRITE || this == UNROUTED_WRITE || this == REFERENCE_WRITE;
	}

	static TouchKind of(Routing.Route route, boolean write) {
		if (route instanceof Routing.Routed)
			return write ? ROUTED_WRITE : ROUTED_READ;
		if (route instanceof Routing.AllShards)
			return write ? UNROUTED_WRITE : UNROUTED_READ;
		if (route instanceof Routing.ReferenceOnly)
			return write ? REFERENCE_WRITE : REFERENCE_READ;
		throw new IllegalArgumentException("a repartition join has no touch kind");
	}
}
