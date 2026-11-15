package io.shardml.bench;

import java.sql.SQLException;

/* How a failed transaction is counted, by SQLSTATE (PostgreSQL Appendix A):
 *  - SERIALIZATION: 40001 serialization_failure;
 *  - DEADLOCK: 40P01 deadlock_detected;
 *  - TIMEOUT: 57014 query_canceled (statement_timeout), 55P03 lock_not_available (lock_timeout),
 *    25P03 idle_in_transaction_session_timeout;
 *  - CONNECTION: class 08 (connection exception), 57P01-57P03 (admin shutdown, crash shutdown,
 *    cannot connect now), and a driver error without SQLSTATE whose cause is an I/O error;
 *  - OTHER: anything else (a constraint violation, a Citus error, ...), counted, never dropped.
 * The first SQLSTATE found along the exception's chain (getNextException, then getCause) decides. */
enum ErrorClass {
	SERIALIZATION, DEADLOCK, TIMEOUT, CONNECTION, OTHER;

	// serialization failures and deadlocks are what a retry may fix
	boolean retryable() {
		return this == SERIALIZATION || this == DEADLOCK;
	}

	String key() {
		return name().toLowerCase();
	}

	static ErrorClass of(SQLException e) {
		String state = state(e);
		if (state == null)
			return ioCause(e) ? CONNECTION : OTHER;
		if (state.equals("40001"))
			return SERIALIZATION;
		if (state.equals("40P01"))
			return DEADLOCK;
		if (state.equals("57014") || state.equals("55P03") || state.equals("25P03"))
			return TIMEOUT;
		if (state.startsWith("08") || state.equals("57P01") || state.equals("57P02") || state.equals("57P03"))
			return CONNECTION;
		return OTHER;
	}

	private static String state(SQLException e) {
		for (Throwable t = e; t != null; t = t.getCause()) {
			if (t instanceof SQLException s) {
				for (SQLException n = s; n != null; n = n.getNextException()) {
					if (n.getSQLState() != null && !n.getSQLState().isEmpty())
						return n.getSQLState();
				}
			}
			if (t.getCause() == t)
				break;
		}
		return null;
	}

	private static boolean ioCause(Throwable e) {
		for (Throwable t = e; t != null; t = t.getCause()) {
			if (t instanceof java.io.IOException)
				return true;
			if (t.getCause() == t)
				break;
		}
		return false;
	}
}
