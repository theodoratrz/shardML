#!/usr/bin/env python3
"""Plan-phase4 1a / P4.0b: one calibration run's counts against the model.

Inputs: the counter snapshots before and after the measured run (bench/calibration/run.sh writes them),
the driver's result.json, `driver touches --json` for the same workload and layout, the .shardml (node ->
host:port), the entry mode (coordinator | workers) and the number of clients.

N = transactions the driver executed in the measured run (committed + committed after a retry + failed: a
failed transaction has touched too). With no warm-up inside the run (run.sh warms up in a separate run) the
driver counts every transaction it executes. Checked against the model, per transaction:
  - per layout node, read and write touches: the per-node load diagnostic of the plan's amendment 2,
    from pg_stat_statements: the executions of every statement on the node that names a shard of the
    model's tables, times the number of distinct such shards it names, reads (SELECT) and writes (UPDATE,
    DELETE, INSERT, MERGE) apart. It counts executions, so the planner's own index probes do not count, and
    an UPDATE counts once (as a write). Model: the touch kinds summed per node, reads and writes apart;
  - statements (citus_stat_counters, summed over the nodes: only an entry node counts): single-shard vs the
    model's routed + reference statements, multi-shard vs its unrouted ones;
  - 2PC: distinct transactions in pg_dist_transaction (gid without its last field) vs the model's two_phase,
    and per node, the distinct transactions with that node's group among their participants vs the model's
    two_phase_participation.

Allowed deviation, per check: |measured - N m| <= C k + 4 sqrt(N v), where
  - m: the model's count per transaction;
  - C k: one transaction per client at the window's edges (C clients; k: the most one transaction can add to
    this count: the transaction's touches of that kind, its statements, or 1 for 2PC), for a transaction
    whose counters straddle a snapshot;
  - 4 sqrt(N v): four standard deviations of the sum of N independent transactions. v: the per-transaction
    variance, from the model's own probabilities: for the routed and reference touches on a node, r m - m^2
    (r: relations per such statement; exact for one statement per transaction, and 0 when the node is
    certain, as for pinned tables), for 2PC p (1 - p), and 0 for unrouted touches and statement counts.

Not checked, listed with both numbers: entering through the workers (mode b) with Citus local execution on
(its default), a statement whose shard is on the entry worker runs there without a connection, and that
execution appears neither in pg_stat_statements nor, as a 2PC participant, in pg_dist_transaction; so the
per-node touches and the per-node 2PC participation undercount it (about one routed statement in n). The
totals (statements, 2PC transactions) are exact, and are checked. With citus.enable_local_execution = off the
per-node counts match too (amendment 2), but that changes what is measured, so it is never set for a run.

Known difference, not a failure: through the coordinator (mode a) Citus runs a standalone reference read on
the coordinator's own copy (the routing check), where the model spreads it 1/n over the workers; then
reference reads are compared as a total over every node, the coordinator included.

Listed, not checked: the plan's original counters (seq_scan + idx_scan, n_tup_upd per node; amendment 1
says why they do not measure touches), and CPU-seconds per transaction of every container (cgroup
usage_usec): under amd64 emulation CPU is not a result and is never reported (amendment 1, 5).

Prints one line per check and a JSON record (--json-out). Exit 1 if a check fails."""

import argparse
import json
import math
import re
import sys


def snapshot(path):
    snap = {"stats": {}, "touches": {}, "counters": {}, "twopc": 0, "twopc_node": {}, "cpu": {}}
    for line in open(path):
        f = line.rstrip("\n").split("|")
        if f[0] == "stat":  # stat|table|host:port|scans|updates
            snap["stats"][(f[1], f[2])] = (int(f[3]), int(f[4]))
        elif f[0] == "touch":  # touch|host:port|read or write|count
            snap["touches"][(f[1], f[2])] = int(f[3])
        elif f[0] == "counters":
            snap["counters"] = {"single": int(f[1]), "multi": int(f[2])}
        elif f[0] == "twopc":
            snap["twopc"] = int(f[1])
        elif f[0] == "twopcnode":  # twopcnode|host:port|count
            snap["twopc_node"][f[1]] = int(f[2])
        elif f[0] == "cpu":
            snap["cpu"][f[1]] = int(f[2])
    return snap


def delta(after, before):
    return {k: v - before.get(k, 0) for k, v in after.items()}


def hosts(shardml):
    text = open(shardml).read()
    return {m.group(1): "%s:%s" % (m.group(2), m.group(3))
            for m in re.finditer(r'node\s+(\w+)\s*\{\s*host:\s*"([^"]+)"\s*port:\s*(\d+)\s*\}', text)}


def main():
    p = argparse.ArgumentParser()
    p.add_argument("--before", required=True)
    p.add_argument("--after", required=True)
    p.add_argument("--result", required=True)
    p.add_argument("--touches", required=True)
    p.add_argument("--model", required=True)
    p.add_argument("--entry", required=True, choices=["coordinator", "workers"])
    p.add_argument("--clients", required=True, type=int)
    p.add_argument("--local-execution", default="on", choices=["on", "off"],
                   help="citus.enable_local_execution on the workers (mode b: per-node counts are checked only when off)")
    p.add_argument("--label", required=True)
    p.add_argument("--json-out")
    a = p.parse_args()

    s0, s1 = snapshot(a.before), snapshot(a.after)
    result = json.load(open(a.result))
    model = json.load(open(a.touches))
    node_host = hosts(a.model)
    layout_hosts = set(node_host.values())
    C = a.clients

    tp = result["throughput"]
    failed = tp["failed"]
    N = tp["committed"] + tp["committedAfterRetry"] + failed
    if N == 0:
        print("  FAIL  %s: no transaction executed" % a.label)
        return 1

    touched = delta(s1["touches"], s0["touches"])
    kinds = model["touches"]
    statements = model["statements"]
    checks = []

    def check(what, m, k, v, measured, note=""):
        expected = N * m
        allowed = C * k + 4 * math.sqrt(N * max(v, 0.0))
        ok = abs(measured - expected) <= allowed
        checks.append({"check": what, "expected": round(expected, 3), "allowed": round(allowed, 3), "measured": measured,
                       "ok": ok, "note": note})

    def listed(what, measured, note="listed"):
        checks.append({"check": what, "expected": None, "allowed": None, "measured": measured, "ok": True, "note": note})

    # mode (b) with local execution: per-node counts listed with the model's value, not checked
    per_node_blind = a.entry == "workers" and a.local_execution == "on"
    blind_note = "not checked: local execution on the entry worker is invisible here (mode b)"

    def per_node(what, m, k, v, measured):
        if per_node_blind:
            checks.append({"check": what, "expected": round(N * m, 3), "allowed": None, "measured": measured, "ok": True,
                           "note": blind_note})
        else:
            check(what, m, k, v, measured)

    mode_a_ref = a.entry == "coordinator" and sum(kinds["reference_read"].values()) > 0
    for side in ("read", "write"):
        group = [k for k in kinds if k.endswith("_" + side)]
        per_txn = sum(sum(kinds[k].values()) for k in group)  # k: a transaction's touches of this side
        # relations per routed or reference statement of this side, for the variance
        random_kinds = [k for k in group if not k.startswith("unrouted")]
        random_statements = sum(statements[k] for k in random_kinds)
        random_touches = sum(sum(kinds[k].values()) for k in random_kinds)
        r = random_touches / random_statements if random_statements else 0
        for n, host in node_host.items():
            if side == "read" and mode_a_ref:
                continue
            m = sum(kinds[k][n] for k in group)
            mr = sum(kinds[k][n] for k in random_kinds)
            per_node("%s touches on %s" % (side, n), m, per_txn, r * mr - mr * mr, touched.get((host, side), 0))
        if side == "read" and mode_a_ref:
            total = sum(v for (h, sd), v in touched.items() if sd == "read")
            check("read touches, all nodes (coordinator included)", per_txn, per_txn, 0, total,
                  "mode (a): standalone reference reads run on the coordinator's copy")
        for (h, sd), v in sorted(touched.items()):
            if sd == side and h not in layout_hosts:
                listed("%s touches on %s (not a layout node)" % (side, h), v)

    single = sum(statements[k] for k in ("routed_read", "routed_write", "reference_read", "reference_write"))
    multi = statements["unrouted_read"] + statements["unrouted_write"]
    c = delta(s1["counters"], s0["counters"])
    if c:
        check("single-shard statements (entry nodes)", single, single + multi, 0, c["single"])
        check("multi-shard statements (entry nodes)", multi, single + multi, 0, c["multi"])
    p2 = model["two_phase"]
    check("2PC transactions", p2, 1, p2 * (1 - p2), s1["twopc"] - s0["twopc"])
    tn = delta(s1["twopc_node"], s0["twopc_node"])
    for n, host in node_host.items():
        q = model["two_phase_participation"][n]
        per_node("2PC transactions with %s a participant" % n, q, 1, q * (1 - q), tn.get(host, 0))
    for h in sorted(set(tn) - layout_hosts):
        listed("2PC transactions with %s a participant (not a layout node)" % h, tn[h])

    # the plan's original counters, for the record (amendment 1: not touches)
    stats = {}
    for key, (sc, up) in s1["stats"].items():
        b = s0["stats"].get(key, (0, 0))
        st = stats.setdefault(key[1], [0, 0])
        st[0] += sc - b[0]
        st[1] += up - b[1]
    for h in sorted(stats):
        listed("seq_scan + idx_scan / n_tup_upd on %s" % h, stats[h][0], "listed; n_tup_upd %d" % stats[h][1])

    cpu = {k: (s1["cpu"][k] - s0["cpu"].get(k, 0)) / 1e6 / N for k in s1["cpu"]}

    ok = all(ch["ok"] for ch in checks)
    print("  %s  %s: %d transactions (%d failed), %d clients, entry %s" % ("ok  " if ok else "FAIL", a.label, N, failed, C, a.entry))
    for ch in checks:
        if ch["expected"] is None:
            print("        %-52s %24s  measured %10d  (%s)" % (ch["check"], "", ch["measured"], ch["note"]))
        elif ch["allowed"] is None:
            print("        %-52s model %11.1f %9s measured %10d  (%s)" % (ch["check"], ch["expected"], "", ch["measured"], ch["note"]))
        else:
            print("        %-52s model %11.1f +- %-7.1f measured %10d  %s%s" % (
                ch["check"], ch["expected"], ch["allowed"], ch["measured"], "ok" if ch["ok"] else "DIFFERS",
                "" if not ch["note"] else "  [" + ch["note"] + "]"))
    if a.json_out:
        with open(a.json_out, "w") as out:
            json.dump({"label": a.label, "entry": a.entry, "clients": C, "executed": N, "failed": failed,
                       "committed": tp["committed"], "measuredSeconds": result["measuredSeconds"], "checks": checks,
                       "cpuSecondsPerTransactionEmulatedNotAResult": cpu, "model": model, "ok": ok}, out, indent=1)
    return 0 if ok else 1


if __name__ == "__main__":
    sys.exit(main())
