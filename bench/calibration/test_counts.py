#!/usr/bin/env python3
"""A self-test of counts.py, run by run.sh before anything else: a synthetic run whose counts match the model
passes, and the same run with one counter error listed in its manifest fails (a count that could not be read
is never treated as zero). No cluster needed."""

import copy
import json
import os
import subprocess
import sys
import tempfile

HERE = os.path.dirname(os.path.abspath(__file__))

MODEL = """node w1 { host: "worker1" port: 5432 }
node w2 { host: "worker2" port: 5432 }
"""

TOUCHES = {
    "nodes": ["w1", "w2"],
    "touches": {k: {"w1": 0.0, "w2": 0.0} for k in
                ("routed_read", "routed_write", "unrouted_read", "unrouted_write", "reference_read", "reference_write")},
    "statements": {"routed_read": 0.0, "routed_write": 0.0, "unrouted_read": 1.0, "unrouted_write": 0.0,
                   "reference_read": 0.0, "reference_write": 0.0},
    "two_phase": 0.0,
    "two_phase_participation": {"w1": 0.0, "w2": 0.0},
}
TOUCHES["touches"]["unrouted_read"] = {"w1": 2.0, "w2": 2.0}  # an unrouted read of 4 shards, 2 per node


def node(read, single, multi):
    return {"touches": {"read": read, "write": 0}, "statements": {"singleShard": single, "multiShard": multi},
            "cpuUsageMicros": 0, "scans": read, "tuplesUpdated": 0}


def snapshot(n):
    # n transactions after the before snapshot
    return {"nodes": {"coordinator:5432": node(0, 0, n), "worker1:5432": node(2 * n, 0, 0), "worker2:5432": node(2 * n, 0, 0)},
            "twoPhase": {"transactions": 0, "byParticipant": {}}, "errors": [], "underLoad": False}


MANIFEST = {"emulated": True, "counters": {"before": snapshot(0), "after": snapshot(1000)}, "cluster": {"errors": []},
            "clusterCpu": {"errors": []}}
RESULT = {"throughput": {"committed": 1000, "committedAfterRetry": 0, "failed": 0}, "measuredSeconds": 10}


def run(manifest, d):
    for name, value in (("manifest.json", manifest), ("result.json", RESULT), ("touches.json", TOUCHES)):
        with open(os.path.join(d, name), "w") as f:
            json.dump(value, f)
    with open(os.path.join(d, "m.shardml"), "w") as f:
        f.write(MODEL)
    return subprocess.run([sys.executable, "-I", os.path.join(HERE, "counts.py"), "--manifest", os.path.join(d, "manifest.json"),
                           "--result", os.path.join(d, "result.json"), "--touches", os.path.join(d, "touches.json"),
                           "--model", os.path.join(d, "m.shardml"), "--entry", "coordinator", "--clients", "4", "--label", "self-test"],
                          capture_output=True, text=True)


def main():
    failures = []
    with tempfile.TemporaryDirectory() as d:
        r = run(MANIFEST, d)
        if r.returncode != 0:
            failures.append("a matching run failed:\n" + r.stdout + r.stderr)
        for where in ("before", "after", "cluster", "clusterCpu"):
            m = copy.deepcopy(MANIFEST)
            part = m["counters"][where] if where in ("before", "after") else m[where]
            part["errors"] = ["touches on worker1:5432: invalid byte sequence for encoding \"UTF8\": 0x00"]
            r = run(m, d)
            if r.returncode != 1 or "could not all be read" not in r.stdout:
                failures.append("a run with a %s counter error did not fail:\n%s%s" % (where, r.stdout, r.stderr))
        m = copy.deepcopy(MANIFEST)
        m["counters"]["after"]["nodes"]["worker1:5432"]["touches"]["read"] += 100  # beyond C k = 4 * 4
        r = run(m, d)
        if r.returncode != 1 or "DIFFERS" not in r.stdout:
            failures.append("a run whose count differs did not fail:\n" + r.stdout + r.stderr)
    if failures:
        print("counts.py self-test FAILED:\n" + "\n".join(failures))
        return 1
    print("counts.py self-test: ok (a matching run passes; a counter error or a differing count fails it)")
    return 0


if __name__ == "__main__":
    sys.exit(main())
