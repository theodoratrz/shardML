package io.shardml.bench;

import java.sql.SQLException;

/* One client's connection: runs a transaction (BEGIN; its statements; COMMIT) or throws, having
 * rolled back. JdbcSession in runs; a fake in tests. */
interface Session extends AutoCloseable {

	void execute(Replay.Run run) throws SQLException;

	// drop the connection after a connection error; the next execute opens a new one
	void reset();

	@Override
	void close();
}
