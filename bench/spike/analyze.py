"""Summarise the spike runs in results/raw/ into results/results.json and one
plot per operation (results/<op>.png).

Per operation, for the writes that matter (the affected thread for move/split,
all threads for the alter_distributed_table operations):
  - duration of the operation
  - max latency of a write that overlaps the operation window
  - longest gap between consecutive successful commits that overlaps the window
  - errors during the window and in the AFTER_S seconds that follow
  - baseline p50 / p99 latency from before the operation
For move/split it also reports the unaffected threads' throughput during the
operation, as a fraction of their baseline.

Verdict: "blocks writes" if the longest gap exceeds
max(GAP_FLOOR_S, GAP_FACTOR x baseline p99), otherwise "online".
"""

import csv
import json
import statistics
import sys
from pathlib import Path

import matplotlib

matplotlib.use("Agg")
import matplotlib.pyplot as plt  # noqa: E402

HERE = Path(__file__).resolve().parent
RAW = HERE / "results" / "raw"
OUT = HERE / "results"

WARMUP_S = 2.0
GAP_FLOOR_S = 1.0
GAP_FACTOR = 20
BIN_S = 0.5


def load(op):
    meta = json.loads((RAW / f"{op}.json").read_text())
    with open(RAW / f"{op}.csv", newline="") as f:
        rows = [
            {
                "start": float(r["start_s"]),
                "end": float(r["end_s"]),
                "group": r["group"],
                "kind": r["kind"],
                "error": r["error"],
            }
            for r in csv.DictReader(f)
        ]
    return meta, rows


def percentile(values, p):
    if not values:
        return None
    values = sorted(values)
    return values[min(len(values) - 1, int(round(p / 100 * (len(values) - 1))))]


def longest_gap(rows, lo, hi):
    """Longest interval between consecutive successful commits overlapping [lo, hi]."""
    ends = sorted(r["end"] for r in rows if not r["error"])
    best = (0.0, lo, lo)
    for a, b in zip(ends, ends[1:]):
        if b > lo and a < hi and b - a > best[0]:
            best = (b - a, a, b)
    return best


def throughput(rows, lo, hi):
    n = sum(1 for r in rows if not r["error"] and lo <= r["end"] < hi)
    return n / (hi - lo) if hi > lo else None


def bin_rates(rows, lo, hi):
    """Commit rates in BIN_S bins covering [lo, hi]; a trailing bin shorter than
    half a bin is dropped, and a window shorter than one bin is one bin."""
    if hi - lo < BIN_S:
        return [throughput(rows, lo, lo + BIN_S)]
    rates = []
    t = lo
    while hi - t >= BIN_S / 2:
        rates.append(throughput(rows, t, min(t + BIN_S, hi)))
        t += BIN_S
    return rates


def summarise(op):
    meta, rows = load(op)
    lo, hi, run_end = meta["op_start_s"], meta["op_end_s"], meta["run_end_s"]
    focus_group = "affected" if op.startswith(("move", "split")) else "all"
    focus = [r for r in rows if r["group"] == focus_group]

    base = [r for r in focus if not r["error"] and WARMUP_S <= r["end"] < lo]
    base_lat = [r["end"] - r["start"] for r in base]
    p50, p99 = percentile(base_lat, 50), percentile(base_lat, 99)

    overlapping = [r for r in focus if r["start"] < hi and r["end"] > lo]
    max_lat = max((r["end"] - r["start"] for r in overlapping), default=0.0)
    gap, gap_from, gap_to = longest_gap(focus, lo, hi)

    errors_during = [r for r in rows if r["error"] and r["end"] > lo and r["start"] < hi]
    errors_after = [r for r in rows if r["error"] and r["start"] >= hi]
    messages = sorted({r["error"] for r in errors_during + errors_after})

    threshold = max(GAP_FLOOR_S, GAP_FACTOR * p99)
    result = {
        "op": op,
        "sql": meta["sql"],
        "op_error": meta["op_error"],
        "duration_s": hi - lo,
        "focus_group": focus_group,
        "baseline_p50_ms": p50 * 1000,
        "baseline_p99_ms": p99 * 1000,
        "max_latency_s": max_lat,
        "longest_gap_s": gap,
        "longest_gap_at_s": [gap_from - lo, gap_to - lo],
        "errors_during": len(errors_during),
        "errors_after": len(errors_after),
        "error_messages": messages,
        "threshold_s": threshold,
        "verdict": ("did not complete" if meta["op_error"]
                    else "blocks writes" if gap > threshold else "online"),
        "shards_after": meta["shards_after"],
        "distribution_column_after": meta["distribution_column_after"],
        "versions": meta["versions"],
    }
    for key in ("shardid", "hash_range", "affected_keys", "from", "to", "split_point", "halves_on",
                "diagnostics"):
        if key in meta:
            result[key] = meta[key]

    if focus_group == "affected":
        other = [r for r in rows if r["group"] == "unaffected"]
        base_tp = throughput(other, WARMUP_S, lo)
        other_overlapping = [r for r in other if r["start"] < hi and r["end"] > lo]
        result["unaffected"] = {
            "baseline_writes_per_s": base_tp,
            "during_writes_per_s": throughput(other, lo, hi),
            "during_pct_of_baseline": 100 * throughput(other, lo, hi) / base_tp,
            "min_bin_pct_of_baseline": 100 * min(bin_rates(other, lo, hi)) / base_tp,
            # For scale: the same statistics over the baseline, where nothing runs.
            "baseline_min_bin_pct": 100 * min(bin_rates(other, WARMUP_S, lo)) / base_tp,
            "baseline_p5_bin_pct": 100 * percentile(bin_rates(other, WARMUP_S, lo), 5) / base_tp,
            "max_latency_s": max((r["end"] - r["start"] for r in other_overlapping), default=0.0),
            "longest_gap_s": longest_gap(other, lo, hi)[0],
        }

    plot(op, rows, lo, hi, run_end, result)
    return result


def plot(op, rows, lo, hi, run_end, result):
    colours = {"affected": "#d1495b", "unaffected": "#4a7bb7", "all": "#4a7bb7"}
    fig, (ax_lat, ax_tp) = plt.subplots(2, 1, figsize=(11, 6.5), sharex=True,
                                        gridspec_kw={"height_ratios": [3, 2]})
    for ax in (ax_lat, ax_tp):
        ax.axvspan(lo, hi, color="#f2c14e", alpha=0.3, lw=0, label="operation")

    for group in ("unaffected", "all", "affected"):
        ok = [r for r in rows if r["group"] == group and not r["error"]]
        if ok:
            ax_lat.scatter([r["end"] for r in ok], [1000 * (r["end"] - r["start"]) for r in ok],
                           s=2, color=colours[group], alpha=0.5, lw=0, label=f"{group} writes")
    bad = [r for r in rows if r["error"]]
    if bad:
        ax_lat.scatter([r["end"] for r in bad], [1000 * max(r["end"] - r["start"], 1e-4) for r in bad],
                       s=18, color="black", marker="x", lw=1, label=f"errors ({len(bad)})")
    ax_lat.set_yscale("log")
    ax_lat.set_ylabel("write latency (ms, log)")
    ax_lat.legend(loc="upper left", fontsize=8, markerscale=3)
    ax_lat.set_title(f"{op}: {result['verdict']} — longest gap {result['longest_gap_s']:.2f}s, "
                     f"operation {result['duration_s']:.1f}s", fontsize=11)

    nbins = int(run_end / BIN_S) + 1
    edges = [i * BIN_S for i in range(nbins + 1)]
    for group in ("unaffected", "all", "affected"):
        ok = [r["end"] for r in rows if r["group"] == group and not r["error"]]
        if not ok:
            continue
        counts = [0] * nbins
        for e in ok:
            counts[min(int(e / BIN_S), nbins - 1)] += 1
        ax_tp.step(edges[:-1], [c / BIN_S for c in counts], where="post", color=colours[group],
                   lw=1, label=f"{group}")
    ax_tp.set_ylabel(f"commits/s ({BIN_S}s bins)")
    ax_tp.set_xlabel("time since writers started (s)")
    ax_tp.legend(loc="upper left", fontsize=8)
    ax_tp.set_ylim(bottom=0)

    fig.tight_layout()
    fig.savefig(OUT / f"{op}.png", dpi=110)
    plt.close(fig)


def main():
    ops = sys.argv[1:] or [p.stem for p in sorted(RAW.glob("*.json"))]
    order = ["move", "split", "split_remote", "shard_count", "dist_column"]
    ops.sort(key=lambda o: order.index(o) if o in order else len(order))
    results = [summarise(op) for op in ops]
    (OUT / "results.json").write_text(json.dumps(results, indent=2))

    print("| op | verdict | duration | longest gap | max latency | baseline p99 | errors (during/after) | unaffected throughput (mean / min bin) |")
    print("|---|---|---|---|---|---|---|---|")
    for r in results:
        u = r.get("unaffected")
        tp = (f"{u['during_pct_of_baseline']:.0f}% / {u['min_bin_pct_of_baseline']:.0f}% "
              f"(baseline min bin {u['baseline_min_bin_pct']:.0f}%)") if u else "n/a"
        print(f"| {r['op']} | {r['verdict']} | {r['duration_s']:.1f}s | {r['longest_gap_s']:.2f}s | "
              f"{r['max_latency_s']:.2f}s | {r['baseline_p99_ms']:.1f}ms | "
              f"{r['errors_during']}/{r['errors_after']} | {tp} |")


if __name__ == "__main__":
    main()
