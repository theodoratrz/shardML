# Cost model examples

Two workloads, each with two layouts, for `shardml cost` (the main README has the command and its
assumptions). Every output under `expected/` is written by `regenerate.sh`, which CI's build job runs before
its drift check; `CostExamplesTest` regenerates them through the command and compares byte for byte.

| Pair | Workload | Layouts |
|---|---|---|
| Banking, the tension case | `banking.workload` over `banking.msql` | C `banking-by-customer.shardml` (everything by customer), A `banking-by-account.shardml` (accounts by id, transactions by account_id colocated with them; customers by id, cards by customer_id colocated with them) |
| TPC-C, a validation curve | `tpcc.workload` over `tpcc.msql` | W `tpcc-by-warehouse.shardml` (everything by warehouse, item a reference table), S `tpcc-stock-by-item.shardml` (as W, stock by s_i_id, its own root) |

All four layouts use the same three nodes (w2, w3, w1) and 32 buckets, and declare `belongs_to .. via` for every
colocated table. Validation gives no errors; the only warnings are SHML_022 ("no route rule"), one per sharded
table: cost layouts do not need route rules (the workload holds the queries).

## Running them

```sh
mvn -B install
J=shardml.cli/target/shardml.cli-1.0.0-SNAPSHOT-cli.jar
java -jar $J cost examples/cost/banking.workload examples/cost/banking-by-customer.shardml examples/cost/banking-by-account.shardml
java -jar $J cost --csv --sweep s=0,0.5,0.8,1.0,1.2,1.25,1.3,1.4 examples/cost/banking.workload examples/cost/banking-by-*.shardml
java -jar $J cost --sweep remote=0,0.01,0.1,0.3,0.6,1.0 examples/cost/tpcc.workload examples/cost/tpcc-*.shardml
examples/cost/regenerate.sh      # every output under expected/, about a minute
```

Wall-clock on an M-series laptop (JVM start included): one banking report 1.3 s; the `s` sweep (8 values x 2
layouts) 3.2 s; the rank-offset sweep (8 x 2) 3.4 s; `s` x offset (8 x 8 x 2) 20.6 s; the TPC-C `remote` sweep
(6 x 2) 1.2 s.

## The workloads

Banking (Plan-phase3 5.A): 10,000 customers; 1,000,000 accounts, the number per customer ~ zipf(`s`); 2,000,000
transactions over accounts. `overview` (weight 2) reads one customer's customer, accounts and cards rows;
`history` (48) reads one account's transactions; `transfer` (50) writes two distinct accounts and their
transactions. Account-level operations are uniform over accounts, so large customers are the hot ones.

TPC-C (Plan-phase3 5.B), simplified: 12 warehouses, 100,000 items; NURand is replaced by uniform draws, an order
has exactly 10 lines (TPC-C: 5 to 15), delivery covers one district (TPC-C: 10), and each table carries its keys
and one payload column. `remote` is the probability that an order line's stock is in another warehouse,
`pay_remote` that a payment's customer is. `stock_level` joins order_line with stock on
`ol_w_id = s_w_id AND ol_i_id = s_i_id`, as TPC-C does: under W that join is colocated and routed (it pairs the
two distribution columns and the where pins the warehouse); under S it is a repartition join (stock is its own
colocation group), which default Citus refuses.

## What the model predicts

These are the model's predictions, not measurements: no throughput or latency has been measured yet (Phase 4).

**Banking.** At the default weights the layouts trade off as follows (rank offset 0; `expected/banking-sweep-s.txt`):

| s | 2PC C / A | multi-node C / A | fan-out C / A | data skew C / A | load skew C / A | bottleneck C / A |
|---|---|---|---|---|---|---|
| 0 | 0.3328 / 0.3330 | 0.3328 / 0.3530 | 1.0000 / 1.2441 | 1.0503 / 1.0327 | 1.0503 / 1.0324 | 0.8893 / 1.0875 |
| 1.0 | 0.3301 / 0.3330 | 0.3301 / 0.3530 | 1.0000 / 1.2441 | 1.1240 / 1.0327 | 1.1223 / 1.0324 | 0.9502 / 1.0875 |
| 1.25 | 0.3219 / 0.3330 | 0.3219 / 0.3530 | 1.0000 / 1.2441 | 1.2680 / 1.0327 | 1.2639 / 1.0324 | 1.0701 / 1.0875 |
| 1.3 | 0.3193 / 0.3330 | 0.3193 / 0.3530 | 1.0000 / 1.2441 | 1.3067 / 1.0327 | 1.3019 / 1.0324 | 1.1023 / 1.0875 |
| 1.4 | 0.3129 / 0.3330 | 0.3129 / 0.3530 | 1.0000 / 1.2441 | 1.3904 / 1.0327 | 1.3842 / 1.0324 | 1.1720 / 1.0875 |

At every `s`, C has the lower 2PC, multi-node share and fan-out, and A the lower data and load skew. The bottleneck
(the busiest node's load) is lower under C up to `s` = 1.25 and under A from `s` = 1.3. A's bottleneck does not
depend on `s` or on the rank offset (1.087453 throughout).

The `s` where C's bottleneck passes A's, by linear interpolation between the sweep points (an interpolation, not
a computed root), for each rank offset (`expected/banking-sweep-s-offset.csv`):

| rank offset | 0 | 1 | 2 | 3 | 5 | 8 | 13 | 21 |
|---|---|---|---|---|---|---|---|---|
| s* | 1.277 | 0.980 | 1.087 | 1.129 | 1.120 | 0.946 | none in [0, 1.4] | 1.380 |

Over the seven offsets with a crossover: min 0.946, median 1.120, max 1.380. At offset 13 the hottest customers
land so that C's bottleneck stays below A's up to `s` = 1.4 (1.0759 vs 1.0875): no crossover in the swept range.
The crossover depends on which nodes the hottest customers hash to, so it is a spread, not one number.

**TPC-C** (`expected/tpcc-sweep-remote.txt`):

| remote | 0 | 0.01 | 0.1 | 0.3 | 0.6 | 1.0 |
|---|---|---|---|---|---|---|
| 2PC W | 0.043977 | 0.073699 | 0.269747 | 0.442836 | 0.489443 | 0.493893 |
| 2PC S | 0.493969 | 0.493969 | 0.493969 | 0.493969 | 0.493969 | 0.493969 |

W's 2PC rises with the remote ratio towards S's; S's does not depend on it (its stock writes are routed by item,
not by warehouse). At `remote` = 1.0 they are 0.493893 vs 0.493969: W stays at or below S over the whole range,
so **no 2PC crossover appears**. Data skew is 1.500000 (W) vs 1.405595 (S), load skew 1.374091 vs 1.254362,
bottleneck 8.185000 vs 7.438365, at every remote ratio (neither layout's load depends on it). So S has the lower
skew and bottleneck, W the lower 2PC; neither layout dominates, and S needs repartition joins enabled for
stock_level. The plan expected S might overtake W past some remote ratio; on these metrics it does not.
