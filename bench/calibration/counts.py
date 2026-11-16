#!/usr/bin/env python3
"""Plan-phase4 1a / P4.0b: one calibration run's counts against the model.

Inputs: the counter snapshots before and after the measured run (bench/calibration/run.sh writes them),
the driver's result.json, `driver touches --json` and `shardml cost --json` for the same workload and
layout, the .shardml (for node -> host:port), and the entry mode (coordinator | workers).

Per transaction executed (committed + committed after a retry + failed: a failed transaction has touched
too), against the model:
  - per layout node: shard relations scanned (seq_scan + idx_scan of every placement there) vs the model's
    read touches, and rows updated (n_tup_upd) vs its write touches. A write's own index scan is not a read
    touch, so read benchmarks compare scans and write benchmarks updates; a benchmark has one kind.
  - statements (citus_stat_counters, summed over the nodes: only an entry node counts): single-shard vs the model's routed + reference
    statements, multi-shard vs its unrouted ones;
  - 2PC: distinct transactions in pg_dist_transaction (gid without its last field) vs the model's 2PC share.
Allowed difference: 5 sqrt(expected) + 2 + 0.2% (routed touches are binomial over the nodes; the rest is
exact up to a few failed transactions).

Known factor: PostgreSQL counts the planner's own index probes (selfuncs.c get_actual_variable_range, for
a range bound outside the histogram and for a merge-join selectivity estimate) in idx_scan, and Citus plans
every shard query on its worker; --scans-per-touch gives the scans per read touch run.sh states for such a
workload (2 for scan and ref-join).

Known difference, not a failure: through the coordinator (mode a) Citus runs a standalone reference read
on the coordinator's own copy (the routing check), where the model spreads it 1/n over the workers; then
reference reads are compared as a total over every node, the coordinator included.

Recorded, not checked: CPU-seconds per transaction of every container (cgroup usage_usec deltas). On the
emulated bench cluster these are not costs (Plan-phase4 7.2); on the VM they are what P4.0c fits.

Prints one line per check and a JSON record (--json-out). Exit 1 if a check fails."""

import argparse
import json
import math
import re
import sys


def snapshot(path):
    stats, counters, twopc, cpu = {}, {}, 0, {}
    for line in open(path):
        f = line.rstrip("\n").split("|")
        if f[0] == "stat":
            # stat|table|host:port|scans|updates
            stats[(f[1], f[2])] = (int(f[3]), int(f[4]))
        elif f[0] == "counters":
            counters = {"single": int(f[1]), "multi": int(f[2])}
        elif f[0] == "twopc":
            twopc = int(f[1])
        elif f[0] == "cpu":
            cpu[f[1]] = int(f[2])
    return stats, counters, twopc, cpu


def hosts(shardml):
    text = open(shardml).read()
    return {m.group(1): "%s:%s" % (m.group(2), m.group(3))
            for m in re.finditer(r'node\s+(\w+)\s*\{\s*host:\s*"([^"]+)"\s*port:\s*(\d+)\s*\}', text)}


def allowed(expected):
    return 5 * math.sqrt(abs(expected)) + 2 + 0.002 * abs(expected)


def main():
    p = argparse.ArgumentParser()
    p.add_argument("--before", required=True)
    p.add_argument("--after", required=True)
    p.add_argument("--result", required=True)
    p.add_argument("--touches", required=True)
    p.add_argument("--cost", required=True)
    p.add_argument("--model", required=True)
    p.add_argument("--entry", required=True, choices=["coordinator", "workers"])
    p.add_argument("--label", required=True)
    p.add_argument("--scans-per-touch", type=float, default=1.0,
                   help="index scans PostgreSQL counts per read touch (2 where the planner probes the index)")
    p.add_argument("--json-out")
    a = p.parse_args()

    s0, c0, t0, cpu0 = snapshot(a.before)
    s1, c1, t1, cpu1 = snapshot(a.after)
    result = json.load(open(a.result))
    touches = json.load(open(a.touches))
    cost = json.load(open(a.cost))
    node_host = hosts(a.model)

    tp = result["throughput"]
    failed = tp["failed"]
    executed = tp["committed"] + tp["committedAfterRetry"] + failed
    if executed == 0:
        print("  FAIL  %s: no transaction executed" % a.label)
        return 1

    # measured deltas per host:port, over every table of the snapshot
    scans, updates = {}, {}
    for key, (sc, up) in s1.items():
        b = s0.get(key, (0, 0))
        scans[key[1]] = scans.get(key[1], 0) + sc - b[0]
        updates[key[1]] = updates.get(key[1], 0) + up - b[1]

    kinds = touches["touches"]
    reads = {n: sum(v[n] for k, v in kinds.items() if k.endswith("_read")) for n in touches["nodes"]}
    writes = {n: sum(v[n] for k, v in kinds.items() if k.endswith("_write")) for n in touches["nodes"]}
    ref_reads = kinds["reference_read"]
    mode_a_ref = a.entry == "coordinator" and sum(ref_reads.values()) > 0

    checks = []

    def check(what, expected, measured, note=""):
        ok = abs(measured - expected) <= allowed(expected)
        checks.append({"check": what, "expected": round(expected, 3), "measured": measured, "ok": ok, "note": note})

    # a write's own index scan counts as a scan: a benchmark that writes compares updates only
    has_writes = sum(writes.values()) > 0
    for n in touches["nodes"]:
        host = node_host[n]
        if has_writes:
            checks.append({"check": "scans on %s" % n, "expected": None, "measured": scans.get(host, 0), "ok": True,
                           "note": "listed"})
        elif not mode_a_ref:
            check("scans on %s" % n, reads[n] * a.scans_per_touch * executed, scans.get(host, 0),
                  "" if a.scans_per_touch == 1 else "x%g: the planner's index probes count as scans" % a.scans_per_touch)
        check("updates on %s" % n, writes[n] * executed, updates.get(host, 0))
    if mode_a_ref and not has_writes:
        layout_hosts = set(node_host.values())
        total = sum(scans.values())
        check("scans, all nodes (coordinator included)", sum(reads.values()) * a.scans_per_touch * executed, total,
              "mode (a): standalone reference reads run on the coordinator's copy")
        coordinator = sum(v for h, v in scans.items() if h not in layout_hosts)
        checks.append({"check": "scans on the coordinator", "expected": None, "measured": coordinator, "ok": True,
                       "note": "listed"})
    # reference writes also update the coordinator's copy: listed, not compared
    for h in sorted(set(updates) - set(node_host.values())):
        checks.append({"check": "updates on %s (not a layout node)" % h, "expected": None, "measured": updates[h],
                       "ok": True, "note": "listed"})

    statements = touches["statements"]
    single = statements["routed_read"] + statements["routed_write"] + statements["reference_read"] + statements["reference_write"]
    multi = statements["unrouted_read"] + statements["unrouted_write"]
    if c0 and c1:
        check("single-shard statements (entry nodes)", single * executed, c1["single"] - c0["single"])
        check("multi-shard statements (entry nodes)", multi * executed, c1["multi"] - c0["multi"])
    check("2PC transactions", cost["distributed"]["twoPhase"] * executed, t1 - t0)

    cpu = {c: (cpu1[c] - cpu0.get(c, 0)) / 1e6 / executed for c in cpu1}

    ok = all(c["ok"] for c in checks)
    print("  %s  %s: %d transactions (%d failed)" % ("ok  " if ok else "FAIL", a.label, executed, failed))
    for c in checks:
        exp = "-" if c["expected"] is None else "%.1f" % c["expected"]
        flag = "(listed)" if c["note"] == "listed" else ("ok" if c["ok"] else "DIFFERS")
        print("        %-48s model %12s  measured %10d  %s%s" % (c["check"], exp, c["measured"], flag,
                                                                  "" if c["note"] in ("", "listed") else "  [" + c["note"] + "]"))
    print("        CPU-s per transaction (recorded, not a cost on the emulated cluster): "
          + ", ".join("%s %.6f" % (k, v) for k, v in sorted(cpu.items())))
    if a.json_out:
        with open(a.json_out, "w") as out:
            json.dump({"label": a.label, "entry": a.entry, "executed": executed, "failed": failed,
                       "committed": tp["committed"], "measuredSeconds": result["measuredSeconds"],
                       "latencyMicros": result["latencyMicros"]["overall"], "checks": checks,
                       "cpuSecondsPerTransaction": cpu, "model": {"touches": touches, "twoPhase": cost["distributed"]["twoPhase"]},
                       "ok": ok}, out, indent=1)
    return 0 if ok else 1


if __name__ == "__main__":
    sys.exit(main())
