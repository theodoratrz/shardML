"""Phase 0 spike: run one Citus operation under write load and log every write.

For each operation: recreate the cluster (down.sh + up.sh), load setup.sql,
start four writer threads, wait BASELINE_S, run the operation, wait AFTER_S,
stop the writers. Writes go to results/raw/<op>.csv, operation timing and
parameters to results/raw/<op>.json.

For move and split, one thread ("affected") writes only keys in the shard being
moved or split and three ("unaffected") write only keys outside it. For the two
alter_distributed_table operations the whole table is rewritten, so all four
threads write across the whole table.

Usage: spike.py [op ...]   (default: all operations)
"""

import csv
import json
import random
import subprocess
import sys
import threading
import time
from pathlib import Path

import psycopg

HERE = Path(__file__).resolve().parent
BENCH = HERE.parent
RAW = HERE / "results" / "raw"

DSN = "host=127.0.0.1 port=5433 user=postgres dbname=postgres"
MOD = 100003  # k = id % MOD, as in setup.sql
ROWS = 1_000_000
THREADS = 4
BASELINE_S = 20
AFTER_S = 20
# An operation still running after this long is cancelled and recorded as
# not completing. Normal runs finish in well under a minute, even emulated.
OP_TIMEOUT_S = 300
# If the operation is still running at these points, snapshot the cluster
# state into results/diagnostics/ (two snapshots, to show what progresses).
DIAG_AT_S = (60, 240)
DIAG = HERE / "results" / "diagnostics"
SERVICES = ["coordinator", "worker1", "worker2", "worker3"]

OPS = ["move", "split", "split_remote", "shard_count", "dist_column"]

# Where the two halves of a split go. "split" keeps one half on the source
# node (worker1); "split_remote" sends both halves to other nodes.
SPLIT_TARGETS = {"split": ["worker1", "worker2"], "split_remote": ["worker2", "worker3"]}


def connect():
    # prepare_threshold=None: no server-side prepared statements, so a table
    # that is dropped and recreated underneath (alter_distributed_table)
    # cannot leave a stale plan behind.
    return psycopg.connect(DSN, autocommit=True, prepare_threshold=None)


def cluster_restart():
    subprocess.run([BENCH / "down.sh"], check=True, capture_output=True)
    out = subprocess.run([BENCH / "up.sh"], check=True, capture_output=True, text=True)
    versions = {}
    for line in out.stdout.splitlines():
        if line.startswith("Citus:"):
            versions["citus"] = line.split(":", 1)[1].strip()
        elif line.startswith("Postgres:"):
            versions["postgres"] = line.split(":", 1)[1].strip()
    return versions


def load_table(conn):
    conn.execute((HERE / "setup.sql").read_text())


def pick_shard(conn):
    """The lowest-numbered shard of t placed on worker1."""
    return conn.execute(
        """
        SELECT s.shardid, s.shardminvalue::int, s.shardmaxvalue::int
        FROM pg_dist_shard s
        JOIN pg_dist_placement p USING (shardid)
        JOIN pg_dist_node n ON n.groupid = p.groupid
        WHERE s.logicalrelid = 't'::regclass AND n.nodename = 'worker1'
        ORDER BY s.shardid
        LIMIT 1
        """
    ).fetchone()


def keys_in_shard(conn, shardid):
    rows = conn.execute(
        """
        SELECT k FROM generate_series(0, %s) AS k
        WHERE get_shard_id_for_distribution_column('t', k) = %s
        """,
        (MOD - 1, shardid),
    ).fetchall()
    return [r[0] for r in rows]


def node_id(conn, name):
    return conn.execute("SELECT nodeid FROM pg_dist_node WHERE nodename = %s", (name,)).fetchone()[0]


DIAG_QUERIES = {
    "all": [
        ("pg_stat_activity", """
            SELECT pid, backend_type, state, wait_event_type, wait_event,
                   now() - xact_start AS xact_age, now() - query_start AS query_age,
                   left(regexp_replace(query, '\\s+', ' ', 'g'), 150) AS query
            FROM pg_stat_activity
            WHERE pid <> pg_backend_pid()
              AND backend_type NOT IN ('checkpointer', 'background writer', 'walwriter',
                                       'autovacuum launcher', 'logical replication launcher')
            ORDER BY xact_start NULLS LAST, pid"""),
        ("blocked backends", """
            SELECT pid, pg_blocking_pids(pid) AS blocked_by, wait_event_type, wait_event,
                   left(query, 100) AS query
            FROM pg_stat_activity WHERE cardinality(pg_blocking_pids(pid)) > 0"""),
        ("pg_locks (ungranted, advisory, and on t / its shards)", """
            SELECT l.pid, a.backend_type, l.locktype, l.relation::regclass AS relation,
                   l.mode, l.granted, l.objid, pg_blocking_pids(l.pid) AS blocked_by
            FROM pg_locks l LEFT JOIN pg_stat_activity a USING (pid)
            WHERE l.pid <> pg_backend_pid()
              AND (NOT l.granted OR l.locktype = 'advisory'
                   OR (l.locktype = 'relation' AND l.relation::regclass::text ~ '^t(_[0-9]+)?$'))
            ORDER BY l.granted, l.pid"""),
    ],
    "coordinator": [
        ("citus_lock_waits", "SELECT * FROM citus_lock_waits"),
    ],
    "worker": [
        ("pg_replication_slots", """
            SELECT slot_name, plugin, active, active_pid, restart_lsn, confirmed_flush_lsn,
                   pg_size_pretty(pg_wal_lsn_diff(pg_current_wal_lsn(), confirmed_flush_lsn)) AS lag
            FROM pg_replication_slots"""),
        ("pg_stat_replication", """
            SELECT pid, application_name, state, sent_lsn, write_lsn, flush_lsn, replay_lsn,
                   write_lag, flush_lag, replay_lag
            FROM pg_stat_replication"""),
        ("pg_subscription", "SELECT subname, subenabled, subconninfo FROM pg_subscription"),
        ("pg_stat_subscription", """
            SELECT subname, pid, received_lsn, latest_end_lsn, last_msg_receipt_time
            FROM pg_stat_subscription"""),
        ("pg_stat_subscription_stats", "SELECT * FROM pg_stat_subscription_stats"),
        ("pg_subscription_rel", """
            SELECT srsubid, srrelid::regclass AS relation, srsubstate, srsublsn
            FROM pg_subscription_rel"""),
    ],
}


def compose(*args, **kwargs):
    return subprocess.run(["docker", "compose", "-f", BENCH / "docker-compose.yml", *args],
                          capture_output=True, text=True, **kwargs)


def capture_diagnostics(op, label, since):
    """Snapshot activity, locks, replication state and container logs."""
    DIAG.mkdir(parents=True, exist_ok=True)
    out = [f"# {op}: diagnostics at {label} into the operation\n"]
    for svc in SERVICES:
        sections = DIAG_QUERIES["all"] + DIAG_QUERIES["coordinator" if svc == "coordinator" else "worker"]
        for title, query in sections:
            res = compose("exec", "-T", svc, "psql", "-U", "postgres", "-X", "-P", "pager=off",
                          "-c", query, timeout=60)
            out.append(f"\n## {svc}: {title}\n{res.stdout}{res.stderr}")
    for svc in SERVICES:
        res = compose("logs", "--no-color", "--timestamps", "--since", since, svc, timeout=60)
        lines = (res.stdout + res.stderr).splitlines()
        if len(lines) > 400:
            lines = [f"... {len(lines) - 400} earlier lines omitted ..."] + lines[-400:]
        out.append(f"\n## {svc}: container log since the operation started\n" + "\n".join(lines) + "\n")
    path = DIAG / f"{op}-{label}.txt"
    path.write_text("".join(out))
    print(f"[{op}] diagnostics written to {path.relative_to(HERE)}", flush=True)
    return path.name


class Watchdog(threading.Thread):
    """Captures diagnostics at each DIAG_AT_S mark the operation is still running at."""

    def __init__(self, op, since):
        super().__init__(daemon=True)
        self.op, self.since = op, since
        self.done = threading.Event()
        self.files = []

    def run(self):
        start = time.monotonic()
        for mark in DIAG_AT_S:
            if self.done.wait(max(0, mark - (time.monotonic() - start))):
                return
            self.files.append(capture_diagnostics(self.op, f"{mark}s", self.since))


class Writer(threading.Thread):
    """Tight loop of 50% INSERT of a fresh row, 50% UPDATE of an existing row.

    keys=None means any k. Inserted ids are k + MOD * m with m unique per
    thread and well above the loaded range, so threads never collide.
    """

    def __init__(self, idx, group, keys, t0, stop):
        super().__init__(daemon=True)
        self.idx, self.group, self.keys = idx, group, keys
        self.t0, self.stop = t0, stop
        self.log = []
        self.rng = random.Random(idx)
        self.next_m = 1_000_000 + idx * 100_000_000
        self.conn = None

    def pick_existing(self):
        if self.keys is None:
            row_id = self.rng.randint(1, ROWS)
            return row_id, row_id % MOD
        k = self.rng.choice(self.keys)
        # Loaded ids with this k are k, k + MOD, ..., up to ROWS.
        m_max = (ROWS - k) // MOD
        m = self.rng.randint(1 if k == 0 else 0, m_max)
        return k + MOD * m, k

    def pick_new(self):
        k = self.rng.randrange(MOD) if self.keys is None else self.rng.choice(self.keys)
        self.next_m += 1
        return k + MOD * self.next_m, k

    def run(self):
        while not self.stop.is_set():
            if self.conn is None or self.conn.closed:
                try:
                    self.conn = connect()
                except psycopg.Error as e:
                    now = time.monotonic() - self.t0
                    self.log.append((now, now, self.idx, self.group, "connect", str(e).strip()))
                    time.sleep(0.05)
                    continue
            if self.rng.random() < 0.5:
                kind = "insert"
                sql = "INSERT INTO t (id, k, v) VALUES (%s, %s, 'new')"
                params = self.pick_new()
            else:
                kind = "update"
                # The value comes from the client: Citus rejects volatile
                # functions such as random() in a distributed UPDATE.
                sql = "UPDATE t SET v = %s WHERE id = %s AND k = %s"
                params = (f"{self.rng.getrandbits(64):016x}", *self.pick_existing())
            start = time.monotonic() - self.t0
            err = ""
            try:
                self.conn.execute(sql, params)
            except psycopg.Error as e:
                err = str(e).strip().splitlines()[0] if str(e).strip() else type(e).__name__
                if self.conn.broken:
                    self.conn.close()
            end = time.monotonic() - self.t0
            self.log.append((start, end, self.idx, self.group, kind, err))
        if self.conn is not None:
            self.conn.close()


def run_op(op):
    print(f"[{op}] restarting cluster", flush=True)
    versions = cluster_restart()
    conn = connect()
    print(f"[{op}] loading {ROWS} rows", flush=True)
    load_table(conn)

    params = {}
    if op in ("move", *SPLIT_TARGETS):
        shardid, lo, hi = pick_shard(conn)
        affected = keys_in_shard(conn, shardid)
        affected_set = set(affected)
        unaffected = [k for k in range(MOD) if k not in affected_set]
        params = {"shardid": shardid, "hash_range": [lo, hi], "affected_keys": len(affected)}
        groups = [("affected", affected)] + [("unaffected", unaffected)] * (THREADS - 1)
        if op == "move":
            sql = (
                "SELECT citus_move_shard_placement(%s, 'worker1', 5432, 'worker2', 5432, "
                "shard_transfer_mode => 'auto')"
            )
            args = (shardid,)
            params["from"], params["to"] = "worker1", "worker2"
        else:
            point = (lo + hi) // 2
            nodes = [node_id(conn, n) for n in SPLIT_TARGETS[op]]
            sql = (
                "SELECT citus_split_shard_by_split_points(%s, ARRAY[%s], ARRAY[%s::int, %s::int], "
                "shard_transfer_mode => 'auto')"
            )
            args = (shardid, str(point), *nodes)
            params["split_point"] = point
            params["halves_on"] = SPLIT_TARGETS[op]
    elif op == "shard_count":
        groups = [("all", None)] * THREADS
        sql, args = "SELECT alter_distributed_table('t', shard_count := 64)", ()
    elif op == "dist_column":
        groups = [("all", None)] * THREADS
        sql, args = "SELECT alter_distributed_table('t', distribution_column := 'id')", ()
    else:
        raise SystemExit(f"unknown operation {op!r}; expected one of {OPS}")

    t0 = time.monotonic()
    stop = threading.Event()
    writers = [Writer(i, g, keys, t0, stop) for i, (g, keys) in enumerate(groups)]
    for w in writers:
        w.start()

    print(f"[{op}] baseline {BASELINE_S}s", flush=True)
    time.sleep(BASELINE_S)
    print(f"[{op}] running: {sql % tuple(repr(a) for a in args)}", flush=True)
    conn.execute(f"SET statement_timeout = '{OP_TIMEOUT_S}s'")
    watchdog = Watchdog(op, time.strftime("%Y-%m-%dT%H:%M:%SZ", time.gmtime()))
    watchdog.start()
    op_start = time.monotonic() - t0
    op_error = ""
    try:
        conn.execute(sql, args)
    except psycopg.errors.QueryCanceled as e:
        op_error = f"timed out after {OP_TIMEOUT_S}s and was cancelled: {str(e).strip()}"
    except psycopg.Error as e:
        op_error = str(e).strip()
    op_end = time.monotonic() - t0
    watchdog.done.set()
    watchdog.join()
    conn.execute("RESET statement_timeout")
    print(f"[{op}] finished in {op_end - op_start:.1f}s{' with error: ' + op_error if op_error else ''}",
          flush=True)
    time.sleep(AFTER_S)
    stop.set()
    for w in writers:
        w.join(timeout=30)
        if w.is_alive() and w.conn is not None:
            w.conn.cancel()
            w.join(timeout=10)
    run_end = time.monotonic() - t0

    shards_after = conn.execute(
        "SELECT count(*) FROM pg_dist_shard WHERE logicalrelid = 't'::regclass"
    ).fetchone()[0]
    dist_col = conn.execute("SELECT column_to_column_name('t'::regclass, partkey) FROM pg_dist_partition "
                            "WHERE logicalrelid = 't'::regclass").fetchone()[0]
    conn.close()

    RAW.mkdir(parents=True, exist_ok=True)
    with open(RAW / f"{op}.csv", "w", newline="") as f:
        out = csv.writer(f)
        out.writerow(["start_s", "end_s", "thread", "group", "kind", "error"])
        for w in writers:
            for start, end, idx, group, kind, err in w.log:
                out.writerow([f"{start:.6f}", f"{end:.6f}", idx, group, kind, err])
    meta = {
        "op": op,
        "sql": sql % tuple(repr(a) for a in args),
        "versions": versions,
        "op_start_s": op_start,
        "op_end_s": op_end,
        "run_end_s": run_end,
        "op_error": op_error,
        "shards_after": shards_after,
        "distribution_column_after": dist_col,
        "diagnostics": watchdog.files,
        **params,
    }
    (RAW / f"{op}.json").write_text(json.dumps(meta, indent=2))
    print(f"[{op}] {sum(len(w.log) for w in writers)} writes logged", flush=True)


def main():
    ops = sys.argv[1:] or OPS
    for op in ops:
        if op not in OPS:
            raise SystemExit(f"unknown operation {op!r}; expected one of {OPS}")
    for op in ops:
        run_op(op)


if __name__ == "__main__":
    main()
