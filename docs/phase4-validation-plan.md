# ShardML Phase 4: validating the cost model against Citus (pre-registered plan)

This document pre-registers how Phase 4 tests the static cost model of ShardML v3.0.0 (`shardml cost`,
package `io.shardml.cost`) against measured behaviour on a Citus cluster: the experiments, the predictors, the
hypotheses and their success and failure criteria. It is committed before any calibration or validation run and
is not edited afterwards, except as section 4.3 allows. Background: the README's "Cost model" section and
`examples/cost/README.md` (the examples and the model's predictions for them). The driver and loader reuse the
cost model's own classes (`CitusHash`, `RankMapping`, `KeyBuckets`, `LargestRemainder`, `RowCounts`, `Marginals`,
`Workloads`, `Layout`, `Placement`, and the SQL of `shardml route`).

**The claim under test.** The model ranks layouts the way end-to-end throughput and tail latency do, and it
locates crossovers (the parameter value where the better layout changes). Per-metric agreement (2PC rate vs
`pg_dist_transaction`, load vs `pg_stat_user_tables`, ...) is reported as a diagnostic only: validating the model
on its own counters would be circular (it would show that Citus routes as predicted, which the routing check already does,
not that the predictions order layouts correctly).

---

## 0. What Phase 3 already shows, and what it means for the claims

From `examples/cost/README.md` (the model's own predictions for the examples):
- **Banking: the crossover depends on where the hot keys land.** The `s` at which layout C's bottleneck passes
  layout A's ranges **0.946 to 1.380 over rank offsets {0, 1, 2, 3, 5, 8, 13, 21}** (median 1.120), and at offset 13
  there is none up to `s` = 1.4. The rank offset decides which customers are hottest and so which nodes they
  hash to. So every claim about a crossover is **per hot-key placement** (per rank offset), never "the" crossover
  of the workload; Phase 4 measures it per offset and compares per offset.
- **TPC-C: the raw metrics disagree with each other.** 2PC favours W (by warehouse) at every remote ratio (W at or
  below S up to remote = 1.0: 0.493893 vs 0.493969), while the bottleneck load favours S (stock by item) at every
  ratio (7.438 vs 8.185), as do data and load skew. **The uncalibrated metrics therefore do not define a ranking**:
  which layout "wins" depends on how 2PC cost trades against per-node load on this cluster at this RTT. That is
  what the calibration step (section 1a) is for, and why two predictors are pre-registered (section 4).

---

## 1. Experiment matrix

### 1.1 Points

| Example | Layouts | Swept | Values | Fixed |
|---|---|---|---|---|
| Banking (primary) | C (by customer), A (by account) | `s` | 0.9, 1.0, 1.1, 1.2, 1.25, 1.3, 1.35, 1.4, 1.5 | default weights 2 / 48 / 50 |
| Banking offsets | C, A | rank offset | 0, 1, 8, 13, 21 | `s` at the points above around each offset's s* |
| TPC-C (curve) | W (by warehouse), S (stock by item) | `remote` | 0, 0.01, 0.1, 0.3, 0.6, 1.0 | `pay_remote` 0.15 |
| Network | all of the above | netem RTT | 0.1, 0.5, 2 ms | |

- Why these `s`: the model's s* at offset 0 is 1.277 (`examples/cost/README.md`, interpolated), and over offsets {0, 1, 2, 3, 5, 8,
  13, 21} it spreads 0.946 .. 1.380 (none at 13 up to 1.4). The grid brackets that spread with 0.05 steps where it
  matters and stops at 1.5, where the model's data skew is large and 1M accounts still leave
  only 16.5% of customers childless (stated, not hidden).
- Offsets: 0 (s* 1.277), 1 (0.980), 8 (0.946, the lowest), 13 (no crossover predicted), 21 (1.380, the highest):
  they test whether the measured crossover MOVES with the offset as predicted, including a predicted absence.
- TPC-C: the model predicts no 2PC crossover (W at or below S up to remote 1.0) and S better on skew and
  bottleneck everywhere; S needs `citus.enable_repartition_joins` for stock_level (on for S, recorded). A test of a
  predicted non-crossover and of a fixed ordering.
- netem: the 2PC cost is dominated by round trips, so the ranking of a 2PC-heavy layout can change with RTT. All
  three for the minimum subset's s values; 0.5 ms only for the rest unless time allows.

### 1.2 Load generation: closed loop to find saturation, open loop for latency

- **Saturated throughput** (H1): closed loop, N clients each issuing transactions back to back, N stepped
  (8, 16, 32, 64, 128) in 60 s probes; the plateau N is the smallest N whose throughput is within 3% of the
  maximum; the measurement run uses that N. Closed loop is the standard way to find capacity and does not
  depend on guessing an arrival rate.
- **Tail latency at a fixed offered load** (H2): open loop (Poisson arrivals at a fixed rate, the driver
  timing from the scheduled start, so coordinated omission is avoided), at 70% of the LOWER of the two layouts'
  saturated throughput at that point, so both layouts face the same offered load and neither is saturated.
  p50 / p95 / p99 from HdrHistogram.
- Closed-loop latency is recorded too but not used for ranking (it is a function of throughput at saturation).

### 1.3 Repetitions and windows

- Per point and layout: 3 repetitions in the minimum subset, 5 in the full matrix; layouts interleaved (C, A,
  A, C, C, ...) on the SAME loaded data so drift and noisy-neighbour effects hit both.
- Each run: 120 s warm-up (discarded; caches warm, autovacuum quiet), 300 s measurement. Saturation probes 60 s.
- Between repetitions: `VACUUM ANALYZE` not run (no inserts, see 2.4); counters are snapshotted, never reset.

### 1.4 Data volume and load time

- Banking: customers 10,000; accounts 1,000,000; transactions 2,000,000; cards 10,000: about 3.0M rows,
  roughly 0.5 GB with indexes (narrow rows). TPC-C (12 warehouses): order_line 3.6M, stock 1.2M, customer 360k,
  history / orders 360k each, new_order 108k: about 6M rows, roughly 1.2 GB. Both fit in memory on the
  candidate VMs (shared_buffers sized per node so the working set is cached; recorded).
- Load: the loader (2.5) writes with `COPY` in parallel per table; expected 3-6 min per (example, point) on the
  candidates. Each layout lives in its own database on the same cluster, both loaded once per point, so a
  point is loaded once and measured for both layouts.
- Reload needed when the data changes: banking `s` and the rank offset change who owns which accounts (child
  counts per parent rank), so every (s, offset) is a load; TPC-C `remote` changes only the transactions, so TPC-C
  loads once.

### 1.5 Size of the full matrix

Banking offset 0: 9 s x 2 layouts x 5 reps x (2 + 5) min = 10.5 h + 9 loads (~1 h) + saturation probes (~1.5 h).
Offsets: 4 more offsets x 5 s (around each s*) ~ 25 h. TPC-C: 6 x 2 x 5 x 7 min = 7 h. Times 3 RTTs only for the
minimum subset. **About 45 h of run time for the full matrix at one RTT**; the minimum subset (section 6) is
about 4 h.

---

## 1a. Phase 4.0: calibration, before any validation run

**Rule: the calibrated predictor's parameters are fitted only on these microbenchmarks, never on the validation
workloads (banking, TPC-C) or their measurements. They may be refitted once, from the microbenchmarks only,
before any validation run (section 4.3); after a validation run, P1's parameters never change.**

Microbenchmarks, on the same VM, cluster, pinning and GUCs as the validation runs, at each netem RTT (0.1, 0.5,
2 ms), each on its own small schema (a single hash table of 32 shards over the 3 workers, int8 key, one payload
column, 1M rows, cached), closed loop to saturation and open loop at a fixed rate, 3 repetitions of 120 + 300 s:
1. **single-shard point read** (`SELECT .. WHERE key = $k`, routed) and **point write** (`UPDATE .. SET payload =
   payload + 1 WHERE key = $k`): CPU-seconds per operation on the shard's worker and on the entry node, latency;
2. **multi-shard scan cost vs shard count**: an unrouted indexed predicate on tables of 4, 8, 16, 32 and 64 shards
   (per-shard cost and fixed per-statement cost by linear fit over the shard count);
3. **distributed write transaction**: BEGIN; one routed write on each of 2 (then 3) distinct workers; COMMIT, vs
   the same on one worker: the 2PC cost (extra CPU per participant, extra latency) at each RTT;
4. **reference table read and write** (100k rows): a standalone point read and a point write, and a reference read
   joined into a routed statement;
5. **entry-node CPU per statement** (planning and routing): CPU on the node the driver connects to, per routed,
   unrouted and reference statement, from items 1 to 4 (cgroup CPU over the window / statements executed).

**From these, per-operation costs**: c_read, c_write (per routed touch, on the shard's node), c_shard (per shard of
an unrouted statement) and c_stmt_scan (fixed), c_ref_read, c_ref_write (per node), c_plan (per statement, on the
entry node), and c_2pc(RTT, participants) (CPU per participant, and commit latency).

**The calibrated throughput predictor P1** (formula and fitting procedure committed before calibration, step
P4.0a): the model reports, per node, the touches per transaction by kind (routed read, routed write, unrouted per
shard, reference read, reference write), the statements per transaction, the 2PC share and the fan-out. The
service demand per transaction on node n is
  D_n = sum_kinds touches_n,kind * c_kind + statements_entry(n) * c_plan + P2PC * participants_n * c_2pc(RTT),
and the predicted saturated throughput is X = min_n cores_n / D_n (the bottleneck node in CPU-seconds, cores_n the
node's pinned physical cores). P1 ranks layouts by X (higher wins). Fitting: each c from its own microbenchmark,
as the slope of CPU-seconds against operations (non-negative least squares where several are fitted together),
with the bootstrap interval over repetitions; c_2pc per RTT from item 3. A second, latency predictor (mean commit
latency from the per-statement latencies and c_2pc latency) is reported as a diagnostic for H2, not scored.

**Model change this needs (small, in `io.shardml.cost`)**: the load vectors split by touch kind (today read and
write totals only), and per-node statement counts at the entry node; both are already computed internally
(Routing gives the kind of every statement). Done in step P4.0b with tests; no change to existing outputs.

---

## 2. The driver and the loader

### 2.1 Why not pgbench

pgbench 17.10 accepts `random_zipfian(lb, ub, s)` only for **s in [1.001, 1000]** (re-probed on the bench
coordinator: 0.5 and 1.0 and 1000.5 are errors, 1.001 and 1000 run), which excludes `s` = 0.9 and 1.0 of the
sweep. Per the PostgreSQL documentation its sampler is the rejection-inversion method of Hörmann and Derflinger
(not checked against the source). It has no rank offset (rank 1 is `lb`), no `within` domains (an account's
customer would need an extra query, which changes the load), and no `distinct from` / `else with`. So: a
custom Java driver that replays the workload exactly as the model defines it.

### 2.2 Replay: every feature, none dropped

The driver links the `shardml` core jar and builds its samplers from the same records the model evaluates
(`Workloads.of(model, overrides)`, `RankMapping.of(workload, offset)`):

| Feature | Model | Replay |
|---|---|---|
| domain values 1..size | `Workload.Domain` | the same |
| `uniform`, `zipf(s)` | pmf over ranks (StrictMath.pow) | inverse CDF over a cumulative array of the same pmf (`Marginals.draw`), then `RankMapping.value(domain, rank)` |
| rank offset k | `RankMapping` | the same object |
| `within` children | `LargestRemainder` counts, contiguous ids in parent-rank order | the same `RankMapping` (`firstChild`, `parent`) |
| projection `$a.customer` | `RankMapping.project` | the same (`projector(path)`) |
| `distinct from $y` | pmf renormalised over D minus y's value | redraw until the value differs (same distribution) |
| `$y, else with p:` | y with 1 - p, else the excluding draw | Bernoulli(p) from the seeded stream, then the excluding draw |
| `repeat k` | k independent iterations | a loop of k |
| statements | `Routing`, the SQL of `RouteCommand.sql` | prepared statements with the same shape: SELECT for reads, `UPDATE .. SET payload = payload + 1` for writes (a real write, no row count change), DELETE replaced by an UPDATE (see 2.4) |
| template mix | weight / total | a seeded categorical draw per transaction |
| params, `--set` | `Workloads.of` | the same overrides |

Each client has its own seeded `SplittableRandom` (seed = run seed + client id), so a run is reproducible.

### 2.3 Distribution check before any run

For every variable of every template: 10^6 draws, compared with the exact pmf (`Marginals`): a chi-square test
over the top-100 ranks plus one tail bin, and over buckets (the `KeyBuckets` mass m_use per routed binding),
p < 0.001 fails the driver build. Also checked: P[x != y] of each `else with` equals p (binomial test), and no
`distinct from` draw ever equals its base. This is a unit test of the driver (no cluster).

### 2.4 Writes and inserts

The model is a snapshot: inserts would grow tables during a run. So writes are updates of a payload column
(`balance`, `w_ytd`, `s_quantity`, ...), never inserts; TPC-C's inserts (orders, order_line, history,
new_order) are replayed as updates of the rows of the same key (stated simplification; the model treats inserts
and updates alike). Deletes (none in the examples) would be updates too. Recorded in every run manifest.

### 2.5 The loader shares the ids

Rows are generated from `RowCounts.forEachKeyValue` (rows per key value, the model's own numbers) and the
unique-id convention (1..rows); child keys from `RankMapping`. After loading, rows per node are read with
`run_command_on_placements(t, 'select count(*) from %s')` summed per node and must EQUAL the model's data-skew
rows per node exactly (the cost model's numbers); a mismatch stops the session. This checks the loader, not the model.

### 2.6 Connections, coordinator, CPU limit

- **Where the driver connects.** Citus 14 syncs its metadata to every worker (on the bench cluster
  every worker (Citus 14.2: `pg_dist_node.hasmetadata` and `metadatasynced` true for all workers,
  `citus.enable_metadata_sync = on`), so both options need no cluster change:
  - (a) **Through the coordinator only, the coordinator modelled as a node.** The model gains a coordinator node:
    c_plan for every statement, and standalone reference reads there (what Citus does, per the routing check). Simple, but one
    coordinator plans every statement and is likely to saturate first, so measured throughput would compare
    coordinator capacity, the same for both layouts, and hide the placement effects the model is about.
  - (b) **Through the workers (round-robin per client), no coordinator in the data path.** Each worker plans the
    statements it receives (c_plan spread 1/n), coordinates their 2PC, and executes standalone reference reads on
    its local placement, which is exactly the model's 1/n reference-read assumption. Costs: a routed statement
    whose shard is on another worker takes an extra hop (latency, and forwarding CPU on the entry worker, measured
    as c_plan in calibration); more connections per worker.
  - **Mode (b) is primary**, for every validation run. It removes a bottleneck the layouts do not cause, matches the
    model's reference-read assumption, and P1 accounts for the entry-node cost through c_plan. (a) is run once on
    the minimum subset as a sensitivity check (about 2 VM hours), with the coordinator modelled as a node.
- One JDBC connection per client (pgjdbc, prepared statements, autocommit off, one transaction per template
  execution: BEGIN; statements; COMMIT); in (b), client i connects to worker i mod 3.
- **CPU saturation check, every node**: per-container CPU sampled every second; a run in which any Citus node
  other than the bottleneck the predictor names, or the coordinator in any mode (it should be near idle in (b)),
  is above 85% at saturation is flagged, and the coordinator's CPU is reported for every run (risk 7.2).
- Driver CPU: measured from `/proc/<pid>/stat` over the measurement window against the driver's cpuset; a run
  where the driver exceeds 50% of its cores at saturation is rejected and repeated with more driver cores.

---

## 3. Measurements and their sources

| What | Source | How |
|---|---|---|
| Throughput | driver | committed transactions / measurement window, per template and total |
| Latency p50 / p95 / p99 | driver | HdrHistogram per template and total (open loop: from the scheduled start) |
| Per-node load (M4 diagnostic) | workers' `pg_stat_user_tables` per shard relation | after-minus-before deltas of `seq_scan + idx_scan + n_tup_upd` (no inserts), summed per node; never reset |
| 2PC rate (M1 diagnostic) | `pg_dist_transaction` | distinct gid prefixes (`citus_<initiator>_<pid>_<txn>`, last field dropped) / committed transactions, `citus.recover_2pc_interval = -1` during the run |
| Multi-shard share (M2 diagnostic) | `citus_stat_counters` | deltas of single vs multi-shard counts |
| Hot keys | `citus_stat_tenants` (`citus.stat_tenants_track = 'all'`) | top-100 keys' read counts vs the pmf |
| Rows per node (M3) | placements | `run_command_on_placements` counts per node (2.5) |
| Coordinator / driver / node CPU | host | per container cgroup CPU usage, 1 s samples |

**Run manifest** (JSON, committed with the results): VM size and region, `lscpu` (model, sockets, threads per
core), kernel, Docker version, container cpusets, memory limits, Citus and PostgreSQL versions (`citus_version()`,
`version()`), every non-default GUC (`pg_settings` where source <> 'default', and `citus.*`), netem config (`tc
qdisc show`, measured RTT), the shardml commit hash, the sha256 of every model / workload / schema file, params
and rank offset, seeds, client count, offered rate, window lengths, the placement map, and the driver's CPU.

---

## 4. Statistics and pre-registration

### 4.1 What a ranking test can show with two layouts

With two layouts per example, a ranking at one point is a sign (which is better). Rank correlation over two
items is meaningless; what can be scored is sign agreement across the sweep and the crossover's location.

- **Per point**: the measured difference (A - C throughput, and p99) with a 95% confidence interval from the
  repetitions (bootstrap over runs, 10,000 resamples; runs are the unit, not seconds). A point is **decided** when
  the interval excludes 0.
- **Across the sweep**: of the decided points, how many each predictor gets right, **always reported as a count
  next to the percentage** ("7 of 9 decided points, 78%"), with the undecided points listed.
- **Small-sample caveat** (stated in every report): a one-sided sign test against chance (p = 0.5) needs, for
  p <= 0.05, at least 5 of 5, 6 of 6, 7 of 7, 7 of 8, 8 of 9, 9 of 10 or 10 of 12 agreements; with 4 or fewer
  decided points no result is significant. Percentages over a handful of points are reported, not over-read.
- **The banking minimum subset has 5 points** (s = 1.0, 1.2, 1.25, 1.3, 1.4 at offset 0), so with all 5 decided
  the sign test passes only at **5 of 5** (p = 0.031). A **4 of 5** result (p = 0.19) is reported as a **near-miss**,
  with each point's bootstrap interval, and counts as neither success nor failure. Fewer than 5 decided points is
  inconclusive. **The full matrix decides the claim**; the minimum subset is a first look.
- **Crossover**: the measured s*_m is where the throughput difference changes sign, by linear interpolation of the
  per-point means (the model's rule), with a bootstrap interval; compared with each predictor's s* at that offset.

**Third layouts** (`C64`: banking C with 64 buckets; `W-item`: TPC-C W with `item` hash-distributed by `i_id`)
would make each point a 3-way ranking. **Deferred until after the minimum subset**: decided then, on its
results. The minimum subset keeps 120 s warm-up and 300 s measurement runs.

### 4.2 Two pre-registered predictors, hypotheses and criteria

- **P0, uncalibrated (the baseline)**: the layout with the lower bottleneck load (the model's M4 max_n L(n)) wins
  on throughput; the lower 2PC share wins on p99.
- **P1, calibrated (the one scored)**: the layout with the higher predicted saturated throughput X (section 1a)
  wins on throughput.
- **Order of commitments**, each a commit before the next step, never edited afterwards:
  1. P4.0a: the P1 formula and its fitting procedure (section 1a), before any calibration run;
  2. P4.0c: after calibration, the fitted parameters and the **predicted ranking table** for every planned point
     (example, offset, `s` / remote, RTT, layout pair: P0's and P1's winner, P1's X per layout, P1's predicted s*),
     before any validation run;
  3. then validation.
- **H1 (throughput ranking)**: at each decided point, P1's winner has the higher saturated throughput.
  Success: P1 right on >= 80% of decided points AND the count passes the sign test above. Failure: P1 right on
  < 60% of decided points. P0's score is reported next to it, by the same rule, as the baseline.
- **H2 (tail latency)**: at a fixed offered load, the layout with the lower predicted 2PC share has the lower p99
  (P0's rule; P1's latency estimate is a diagnostic). Reported with the same counts and caveat, not used to judge
  P1.
- **H3 (crossover, per hot-key placement)**: for banking, per offset, P1's predicted s* against the measured s*_m.
  At offset 0: success if s*_m exists in [0.9, 1.5] and |s*_m - s*_P1| <= 0.10; failure if no crossover in [0.9, 1.5]
  where P1 predicts one, or |s*_m - s*_P1| > 0.25. Across offsets {0, 1, 8, 21} (full matrix): Spearman of predicted
  vs measured s* >= 0.6; at offset 13, no measured crossover below 1.4 if P1 also predicts none there.
- **H4 (TPC-C)**: P1 names a winner per remote ratio (P0 cannot: its metrics disagree, section 0); scored like H1.
- Between success and failure: inconclusive. **Either outcome is a finding**: P1 failing while P0 succeeds, or both
  failing, is reported with the diagnostics (per-node load, 2PC rate, entry-node CPU) that show where the model or
  the calibration diverged.

### 4.3 If P1 fails

- **Refitting**: P1's parameters may be refitted **once**, **from the microbenchmarks only** (e.g. when a
  microbenchmark turns out to be flawed), and **only before validation**. A refit is committed with its reason and
  a new predicted ranking table (P4.0c repeated) before the first validation run. After the first validation run,
  P1 is frozen.
- **The validation points are not changed after P4.0c**: no point is added, dropped or moved (examples, layouts,
  offsets, `s` / remote values, RTTs, repetitions, windows) once the predicted ranking table is committed. A point
  that cannot be run (an infrastructure failure) is reported as missing, with the reason, not replaced.
- **A model change after seeing validation data** (a new touch kind, a coordinator node, a refitted cost, a
  different formula) is a **new version, P1b**, reported **alongside P1, never replacing it**, and scored only on
  points it was not fitted to (for example: changed after banking at offset 0, scored on offsets 1, 8, 21 and on
  TPC-C).
- **A failed hypothesis is written up as a finding**, with the data: the per-point measurements and intervals,
  P0's and P1's predictions, the calibration parameters, and the diagnostics (per-node load, 2PC rate, entry-node
  CPU) that show where the prediction diverged.

---

## 5. Environment

### 5.1 Shape: one VM, pinned containers

- One x86 VM. Each Citus node (coordinator + 3 workers) in a container pinned with `--cpuset-cpus` to its own
  PHYSICAL cores: Azure vCPUs are hyperthreads, so a node gets whole sibling pairs (`lscpu -e` gives the core of
  each CPU; pin both threads of a core to the same container), and the driver gets other cores, never a sibling
  of a node's core. Memory limits per container, swap off.
- Requirements: 4 Citus containers x 2 physical cores (4 vCPU) = 16 vCPU, plus the driver 4-8 vCPU, plus 2 vCPU
  for the host and collectors: **24-28 vCPU, 64-96 GB RAM, premium SSD 256 GB**.
- Two candidate sizes (the choice is recorded in every run manifest):
  - **32 vCPU / 16 physical cores, 128 GB** (e.g. Azure Standard_D32s_v5): 2 physical cores per Citus node.
  - **16 vCPU / 8 physical cores, 64 GB** (e.g. Azure Standard_D16s_v5): 1 physical core per Citus node, 4 vCPU
    for the driver; tighter, so the driver-CPU limit is likelier to reject runs.

### 5.2 Inter-node latency (netem)

- Applied inside each Citus container (`--cap-add NET_ADMIN`) on its egress, only towards the other Citus
  containers (a `prio` qdisc with a `netem delay RTT/2` band and `u32` filters on their IPs), so the
  driver <-> coordinator path stays at loopback speed (the model is about inter-node round trips).
- Swept RTT: 0.1, 0.5, 2 ms. Verified per session: `ping -c 100` between every container pair (median within 10%
  of the target RTT) and a `SELECT 1` round trip coordinator -> worker via `run_command_on_workers` timing.
  Recorded in the manifest.

### 5.3 Provisioning

A plain shell script (`bench/phase4/provision.sh`) with the Azure CLI and a cloud-init file: resource group,
VM, NSG (ssh from the operator's IP only), cloud-init installs Docker, pins the image `citusdata/citus:14.2.0-pg17`
by digest, clones the repo at a commit and builds the driver. Chosen over Terraform: one dependency (`az`), easy to
read, enough for one VM; everything in it is idempotent and logged.
- Between sessions: `az vm deallocate`; at the end: `az group delete` (removes everything).

---

## 6. Steps (a commit each, each with a "done when")

| # | Step | Done when | Effort |
|---|---|---|---|
| P4.0a | P1 formula and fitting procedure (1a, 4.2) committed | committed before any calibration run | 0.5 d |
| P4.0b | Model extension: load by touch kind, entry-node statement counts (no existing output changes); calibration harness (the five microbenchmarks) | unit tests; goldens unchanged; harness runs on the bench cluster (counts only) | 2.5 d |
| P4.1 | Driver core: samplers on the Phase 3 records, template mix, prepared statements from `RouteCommand.sql` (writes as updates); connection mode (b) and (a) | the distribution check (2.3) passes for both examples; unit tests for every let form | 2 d |
| P4.2 | Loader: rows from `RowCounts`, ids from `RankMapping`, parallel COPY | rows per node on the bench cluster equal the model's data skew rows exactly | 1.5 d |
| P4.3 | Measurement: counter snapshots and deltas, 2PC gid counting, HdrHistogram, manifest, CPU per node | a dry run produces a complete manifest and diagnostics | 1.5 d |
| P4.4 | Local dry run (emulated: counts only) | count diagnostics agree with the model within sampling error | 1 d |
| P4.5 | Provisioning, pinning, netem, verification | one command reaches a verified cluster (pins, RTT, metadata sync); one tears it down | 1.5 d |
| P4.0c | Calibration on the VM; fitted parameters and the predicted ranking table committed | committed before any validation run | 0.5 d + ~6 VM h |
| P4.7 | Minimum subset (P0 and P1 scored), plus the mode (a) sensitivity check | one figure with intervals; counts and caveat | 1 d wall, ~8 VM h |
| P4.8 | Full matrix (third layouts if decided after P4.7) | all points measured; manifests complete | 2-3 d wall |
| P4.9 | Analysis and report | H1-H4 scored as pre-registered; README section | 1.5 d |

**Effort**: about 12.5 working days of development and analysis, plus run time. **VM time**: calibration about 6
hours (5 microbenchmarks x 3 RTTs x 3 repetitions x ~7 min, plus saturation probes), the minimum subset about 6,
the mode (a) check about 2: about 14 VM hours to the first scored figure; the full matrix adds about 50.

**Minimum viable subset**: calibration (P4.0c), then banking C vs A at rank offset 0, `s` in {1.0, 1.2, 1.25, 1.3,
1.4}, 3 repetitions, RTT 0.5 ms, connection mode (b), 120 s + 300 s runs, saturated throughput and p99 at 70%
load. One figure: measured throughput ratio A/C vs `s` with intervals, P1's predicted ratio and s*, and P0's s* =
1.277 marked; with the counts and the sign-test caveat (5 points: only 5 of 5 passes; 4 of 5 is a near-miss, 4.1).
Repetitions: 3 for the minimum subset, 5 for the full matrix. Connection mode (b) is primary; mode (a) runs once,
on the minimum subset, as the sensitivity check. P1 as specified in 1a.

---

## 7. Risks and open questions

### 7.1 Open design choice

- **Third layouts** (`C64`, `W-item`, section 4.1): decided after the minimum subset, before the full matrix, and
  added to the predicted ranking table before any of their runs.

Fixed by this document: 3 repetitions for the minimum subset and 5 for the full matrix; 120 s warm-up and 300 s
measurement; connection mode (b) as primary and mode (a) as one sensitivity check; P1 as specified in section 1a;
the thresholds of section 4.2.

### 7.2 What could invalidate the comparison

- **An entry node saturates first**: in mode (a) the coordinator plans every statement; in mode (b) the planning is
  spread, but an entry worker can still saturate. Detected by the per-node CPU check (2.6); P1 includes c_plan.
- **Reference reads**: through the coordinator (mode a) Citus runs them there (the routing check, `bench/integration/test-cost-routing.sh`), against the model's 1/n
  assumption; mode (b) runs them on the receiving worker, which matches it. TPC-C's item reads (new_order, 45% x 10
  per order) make this matter for TPC-C.
- **Calibration does not transfer**: microbenchmark costs may not add up for mixed transactions (cache sharing,
  lock waits). Reported as a P1 failure mode; P0 is the check that the model's structure alone is not the issue.
- **Memory vs disk**: if the working set spills, I/O dominates and touches are not comparable; mitigated by sizing
  (1.4) and checking the buffer hit ratio per run.
- **Noisy neighbours / CPU frequency** on a cloud VM: mitigated by interleaving layouts and repetitions; a session
  whose repeated baseline drifts by > 5% is discarded.
- **Driver fidelity**: mitigated by 2.3 and the loader check 2.5.
- **Writes as updates** (2.4) change TPC-C's real write mix; stated.
- **Emulation** is out: no throughput or latency is taken from the local emulated cluster.

### 7.3 What Phase 4 may need from the Phase 3 model

- **Load by touch kind and entry-node statement counts** (P4.0b, needed by P1, which weights touches by kind
  with calibrated costs; P0 counts all touches alike).
- A **coordinator node** only if the mode (a) sensitivity check shows the coordinator matters; any such change
  after validation data is a new version (P1b, section 4.3).
- A **driver entry point** in the core: a public sampler built from `Marginals` (today `Marginals.draw` is
  package-private) so the driver does not copy the pmf code.
- Exposing per-template SQL with placeholders from `RouteCommand` (today it inlines values).

---

## Amendments

Appended, never edited in place; the text above stays as committed. Each entry is dated and states its evidence.

### Amendment 1 (2026-11-16): touch kinds, 2PC participation, the load diagnostic, emulated CPU

**Timing.** This amendment predates every measurement it affects: no calibration run (P4.0c) and no validation
run has been made. The only runs so far are the counts-only harness pass on the local emulated cluster
(`bench/calibration/run.sh`, commit `4679a66`), which fits nothing and is not a result.

**Evidence** (step P4.0b): the model extension (`CostModel.touchProfile`, commit `e270592`) and the calibration
harness (commit `4679a66`). In its local pass (30,000 keys, 4 clients, 20 s runs through the coordinator) every
microbenchmark's counts per executed transaction agree with the model, given points 1 and 5 below.

1. **The per-node load diagnostic of section 3 is wrong as defined.** `seq_scan + idx_scan + n_tup_upd`:
   - counts every routed write twice: an `UPDATE .. WHERE key = $k` increments both `idx_scan` (the lookup) and
     `n_tup_upd` (the harness's point-write benchmark);
   - counts the planner's own index probes: PostgreSQL's selectivity estimation reads the index
     (`get_actual_variable_range`) for a range bound outside the column's histogram and for a merge-join estimate,
     and those reads increment `idx_scan`; Citus plans every shard query anew on its worker, so every execution
     pays them. Measured: `EXPLAIN SELECT * FROM t WHERE k < 0` alone adds 1 to `idx_scan`; a join
     `t JOIN ref ON t.r = ref.id WHERE t.k = 5` adds 3 to ref's `idx_scan` (1 execution + 2 planner probes). In
     the harness the unrouted probe (`k < 0`) and the reference join showed exactly 2 scans per model touch.
   The diagnostic is redefined in a later amendment, before any measurement uses it.
2. **Six touch kinds**, not five: routed read, routed write, unrouted read, unrouted write, reference read,
   reference write (`TouchKind`). Section 1a's "unrouted per shard" cost c_shard applies to both unrouted kinds:
   **P1 sums the two unrouted kinds** and costs them with c_shard. Entry-node statement counts are the statements per
   transaction by kind divided by the number of nodes (mode (b), round-robin); in mode (a) the coordinator receives
   all of them.
3. **A reference table joined into a routed statement is modelled as routed** (each relation of the statement is a
   routed touch on the routed node, as in the Phase 3 load), so **P1 has no term of its own for it**: such a touch
   is costed with c_read. Microbenchmark 4 measures the joined case separately; its cost is reported as a
   diagnostic, not fitted into P1. **Threat to validity**: if a joined reference read costs differently from a
   routed read, P1 misprices the workloads that join reference tables (TPC-C's stock-level and new-order joins with
   `item` in layout W). Reported with the results, never corrected after validation data (section 4.3).
4. **P1's 2PC term uses per-node participation.** In D_n of section 1a, `P2PC * participants_n` is the model's
   probability, per transaction, that the transaction commits with 2PC **and node n is one of its participants**
   (a node a write of the transaction touches); the model computes it exactly (step P4.0b follow-up), and the
   calibration's 2PC benchmarks check it per node.
5. **CPU measured under amd64 emulation is not a valid result and is never reported**, neither as a cost, nor as a
   calibration parameter, nor as a diagnostic: the local bench cluster runs amd64 images emulated on an arm64 host,
   so its CPU-seconds measure the emulator. Counts-only checks (rows per node, touches, statements, 2PC
   transactions) do not depend on the CPU and are unaffected. Section 7.2's "Emulation is out" is extended from
   throughput and latency to CPU.

### Amendment 2 (2026-11-16): the per-node load diagnostic, redefined

**Timing.** Like amendment 1, this predates every measurement it affects: no calibration (P4.0c) or validation
run has been made; the only runs are the counts-only harness passes on the local emulated cluster.

**Definition** (replaces section 3's "after-minus-before deltas of `seq_scan + idx_scan + n_tup_upd`, summed per
node", which amendment 1, point 1 showed to double-count writes and to count planner index probes). On every
node, with `pg_stat_statements` preloaded (`pg_stat_statements.track = all`,
`pg_stat_statements.track_utility = off`) and reset after the last shard move before the run:
- **read touches on node n** = sum, over the `pg_stat_statements` entries of the run's database on n whose
  query begins with `SELECT`, of `calls` times the number of distinct shard relations of the layout's tables
  the query names (`<table>_<shardid>`);
- **write touches on node n** = the same over the queries beginning with `UPDATE`, `DELETE`, `INSERT` or `MERGE`;
- statements that read the statistics themselves (naming `pg_stat_user_tables`, `pg_total_relation_size`,
  `pg_dist_` or `pg_stat_statements`) are excluded;
- each as the after-minus-before delta over the measured window, compared per executed transaction with the
  model's touches summed over the read kinds and over the write kinds (`TouchProfile`).
It counts executions, so the planner's index probes do not count, an `UPDATE` counts once (as a write), and a
statement joining k shard relations counts k touches, as the model does.
**Per-node 2PC participation** (amendment 1, point 4) is measured as the distinct 2PC transactions (`gid` without
its last field) whose `pg_dist_transaction` records name the node's group, on every node, with
`citus.recover_2pc_interval = -1` during the run.

**Evidence.** The calibration harness (`bench/calibration/run.sh`), local pass through the coordinator (mode (a);
30,000 keys, 4 clients, 20 s per run, 14 runs): every read and write touch count per node, every statement count
and every 2PC count (total and per node) agrees with the model with no per-workload factor, within one transaction
per client plus four standard deviations of the model's own probabilities, and exactly wherever the count is
certain (for example 93,588 read touches on one node for the 64-shard probe, model 93,588.0; 7,247 2PC
transactions on each of three pinned workers, model 7,247.0). The unrouted probe and the reference join, which
showed 2 index scans per touch under the old definition, match without a factor.

**Limit: connection mode (b).** Entering through a worker with Citus local execution on (the default), a
statement whose shard is on the entry worker runs there without a connection, and that execution is recorded
neither in `pg_stat_statements` nor, as a 2PC participant, in `pg_dist_transaction`. Measured through the
published workers (6 clients, round-robin): point reads 9,552 / 9,477 / 8,485 read touches against the model's
14,238 / 14,225 / 12,746 (about two thirds: the local third is missing), and per-node 2PC participation 11,928 /
11,984 against 18,352 on each written worker; the totals (single-shard statements 41,209 and 36,704, 2PC
transactions 18,352) are exact. With `citus.enable_local_execution = off` on the workers the same runs match the
model on every node, but that setting changes the system being measured, so it is not used in validation runs.
**Therefore, in mode (b) this diagnostic is reported as measured and is not compared per node with the model;**
its per-node comparison is made in mode (a) runs (the sensitivity check, section 2.6), and in mode (b) only the
totals are compared. This changes no hypothesis or predictor: the per-node load is a diagnostic (section 3), and
P1 does not use it.
