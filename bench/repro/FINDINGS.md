# Local shard split hang: findings

> **Under emulation.** Everything here ran on Apple Silicon with the amd64-only
> `citusdata/citus:14.2.0-pg17` image under Rosetta. The failure is a logical
> replication error with a deterministic trigger, not a timing measurement, so
> emulation is unlikely to be the cause. It still needs confirming on native
> hardware (a native arm64 image or an x86 machine; see `bench/README.md`).

## Summary

The hang in `bench/spike` is a **Citus bug**, already reported upstream as
[citusdata/citus#6790](https://github.com/citusdata/citus/issues/6790), "Shard
splits might hang up with concurrent transactions". It has been open since
March 2023, is labelled `bug`, and has no root cause or fix. It still reproduces
on Citus 14.2.0 / PostgreSQL 17.10.

- **Trigger:** one `UPDATE` or `DELETE` on the source shard, written after the split
  creates its replication slots and before it creates the replica identity of a
  child that stays on the source node. `INSERT` does not trigger it.
- **Cause:** for a child shard placed on the **source** node, Citus creates the
  primary key only *after* creating the replication slots. Logical decoding reads
  the catalog as of each change's WAL position. So an UPDATE/DELETE in that window
  is decoded against a version of the child with no replica identity, and goes out
  with no key columns. The subscriber, whose copy of the child does have the
  primary key by then, rejects it:
  `publisher did not send replica identity column expected by the logical replication target relation`.
  The apply worker restarts, re-decodes the same change, and fails again,
  indefinitely. The split waits for that subscription to catch up and never
  returns.
- **Why children on other nodes are fine:** the decoder sees those through "dummy"
  stand-in shards on the source node. Citus gives the stand-ins a replica identity
  *before* creating the slots, and the code comment explains that it does so to
  avoid exactly this kind of stall. The source-node child gets no stand-in (the
  real child already has that name), so the early replica identity never happens
  for it.
- **Plain PostgreSQL** behaves correctly with the same table and replica identity,
  and refuses to produce the problematic state in the first place (see step 4).

## 1. Narrowing the trigger

`repro.sh [update|insert|delete]` (~20 lines): fresh cluster, a 20-row table with
the spike's shape, `PRIMARY KEY (id, k)` distributed on `k`, 4 shards. It splits
the first shard (on worker1) into worker1 + worker2, and writes **one** row change
on that shard from worker1 as soon as the split's replication slots exist. The
row (`k = 1`) hashes into the half that stays on worker1.

| write | runs | result |
|---|---|---|
| `UPDATE` | 3 | **hung 3/3**, `publisher did not send replica identity column … "public.t_102012"` |
| `DELETE` | 2 | **hung 2/2**, same error |
| `INSERT` | 2 | completed 2/2, no apply errors |

UPDATE and DELETE are the changes the subscriber has to locate by replica
identity. INSERT is not.

I used this single-statement repro in place of re-running the spike's load
generator in INSERT-only and UPDATE-only modes. It answers the same question
deterministically and with no harness. The direct write to the shard on worker1 is
what a distributed `UPDATE … WHERE k = 1` through the coordinator executes there.

**Timing matters.** With the same single UPDATE fired later, once the split's
subscription on worker1 is enabled (after the copy), the split **completes**.
On the 20-row table, a split with UPDATEs sent from outside every 0.2 s also
completed, because the split finished before any UPDATE landed in the window. The
window runs from slot creation to the creation of the child's replica identity,
which is why the spike (four threads, continuous writes) hit it on every run.

## 2. Minimal repro

```sh
bench/repro/repro.sh update          # hangs; prints the error after the 30 s timeout
TIMEOUT=900 bench/repro/repro.sh update &   # hold it hung for inspection
```

Needs Docker and the `bench/` cluster (`bench/up.sh` is called for you).

## 3. Where the mismatch is

While the split was hung, on worker1:

**The catalogs as they are now show no mismatch.** The source shard `t_102008`
and the new local child `t_102012` both have `relreplident = d` (default: use the
primary key) and the same `PRIMARY KEY (id, k)` index. The publications have no
column list (`attnames = {id,k,v}`) and no row filter. Both subscriptions'
relations are in state `r` (ready). So "the new local shard's replica identity
doesn't match the source's" is not true at the time of the hang. The mismatch is
in *when* the identity appeared.

**The order of creation is the mismatch.** From `pg_class.xmin` and the commit
records in the WAL (`pg_waldump`):

| xid | commit LSN | what |
|---|---|---|
| 744 | | source shard `t_102008` + its PK (from table creation) |
| 746 | `0/018E3EA0` | **local child `t_102012` created, no PK** |
| 747 | `0/018E7790` | stand-in on worker1 for the child that goes to worker2 (`t_102013`) |
| 748 | `0/018E8BC0` | stand-in's PK `t_pkey_102013`: **before the slots** |
| **751** | **`0/018EDF68`** | **the UPDATE** (the failing change: "in transaction 751, finished at 0/18EDF68") |
| **756** | **`0/018FA490`** | **local child's PK `t_pkey_102012`: after the UPDATE** |

The stuck slot (`citus_shard_split_slot_2_10_1`, plugin `citus`) is confirmed up to
`0/18EDF10`, just before the UPDATE. It never moves past it. The other slot, for
worker2, streams normally.

**The code agrees.** `NonBlockingShardSplit` in
[`src/backend/distributed/operations/shard_split.c` at v14.2.0](https://github.com/citusdata/citus/blob/v14.2.0/src/backend/distributed/operations/shard_split.c#L1437-L1544)
runs:

1. `CreateSplitShardsForShardGroup`: real children, no indexes.
2. `CreateDummyShardsForShardGroup`: stand-ins, but per its comment ("Note 2"),
   none for children on the source node, since the real child exists there.
3. `CreateReplicaIdentitiesForDummyShards`, commented: *"This needs to be done
   before the subscriptions are created. Otherwise the subscription creation will
   get stuck waiting for the publication to send a replica identity."*
4. `CreatePublications`, then `worker_split_shard_replication_setup`.
5. **`CreateReplicationSlots`**, the decoding start point.
6. `CreateSubscriptions`.
7. **`CreateReplicaIdentities(logicalRepTargetList)`**: replica identities on the
   real targets, the source-node child included, **after** the slots.
8. `DoSplitCopy`, then catch-up and cutover.

Any UPDATE/DELETE committed between steps 5 and 7 on the part of the source shard
that maps to a source-node child is decoded against that child as of the change's
LSN, when it had no replica identity.

## 4. Citus or Postgres?

`plain-pg.sh`: the same image with Citus not loaded (`shared_preload_libraries=''`),
so plain PostgreSQL 17.10. One instance, publisher in database `pub`, subscriber in
database `sub`. (A subscription matches tables by schema and name, so it cannot
target a different table in the *same* database; two databases in one instance is
the closest equivalent. Same-cluster subscriptions also need the slot created
beforehand.)

1. **Same setup as the Citus child** (`PRIMARY KEY (id, k)`, replica identity
   default): UPDATE and DELETE replicate correctly, `apply_error_count = 0`.
2. **The state the Citus decoder produced:** a published table with no replica
   identity. Postgres refuses the UPDATE at the source:
   `cannot update table "t2" because it does not have a replica identity and publishes updates`.

So Postgres never lets an UPDATE/DELETE be published for a relation without a
replica identity. In Citus the UPDATE is legal, because it runs on the source shard,
which has a PK. Citus's split decoder (plugin `citus`) then attributes it to the
child, which at that LSN had none. That re-attribution is outside Postgres's check,
so **it is Citus**.

## 5. Upstream

Searched `citusdata/citus` issues and PRs for the error text, "replica identity"
+ split, split hang/stuck, and `citus_split_shard_by_split_points`.

- **[#6790](https://github.com/citusdata/citus/issues/6790)** (open, `bug`, 2023-03-22):
  the same error, from a split under concurrent pgbench UPDATEs. A maintainer
  noted "without pgbench running, shard splits continue normally", it was
  marked for 11.3, and a later comment reports the same bug with
  `create_distributed_table_concurrently` + rebalancing. No root cause or fix is
  posted.
- Related but different: #7220 / #7229 (large splits timing out the WAL
  receiver, fixed with keepalives), #6303 (split hang with a dropped column +
  jsonb, fixed), #8828 (a new transfer mode for tables without a replica identity).

Since #6790 exists, the right action is a comment on it with the root cause and
minimal repro, not a new issue. Draft below; **not posted**.

### Draft comment for citusdata/citus#6790

> Still reproduces on Citus 14.2.0 / PostgreSQL 17.10. I believe I've found the
> cause and have a deterministic repro with a single UPDATE.
>
> **It needs a split child placed on the source node.** The split hangs only when
> one of the target nodes is the source node. Sending both halves to other nodes
> completes under the same load.
>
> **Cause.** In `NonBlockingShardSplit`, dummy shards get their replica identities
> before the replication slots are created (`CreateReplicaIdentitiesForDummyShards`,
> whose comment describes exactly this stall). No dummy is created for a child on
> the source node, because the real child exists there ("Note 2" in
> `CreateDummyShardsForShardGroup`). The real child's replica identity is created
> by `CreateReplicaIdentities(logicalRepTargetList)`, *after*
> `CreateReplicationSlots`. An UPDATE/DELETE on the source shard committed in that
> window is routed by the split decoder to the source-node child and decoded with
> the catalog as of its LSN, when the child had no replica identity. So no key
> columns are sent, and the apply worker fails with `publisher did not send replica
> identity column expected by the logical replication target relation`. It
> restarts and re-decodes the same change, so the split waits forever. INSERTs are
> unaffected. Plain PostgreSQL prevents this state by rejecting UPDATEs on
> published tables without a replica identity; the split decoder's
> re-attribution gets around that check.
>
> Evidence (one run): the local child `t_102012` was created by xid 746 (committed
> at 0/018E3EA0). The UPDATE was xid 751, finished at 0/018EDF68, which is what
> the error's CONTEXT reports. The child's PK `t_pkey_102012` was xid 756,
> committed at 0/018FA490. The stuck slot is confirmed at 0/18EDF10. The dummy for
> the remote child got its PK as xid 748 (0/018E8BC0), before the slots.
>
> **Repro** (4 shards, split the first one, which lives on the node with nodeid 2,
> into nodes 2 and 3; `k = 1` hashes into the half staying on node 2):
>
> ```sql
> -- coordinator
> CREATE TABLE t (id bigint, k bigint, v text, PRIMARY KEY (id, k));
> SELECT create_distributed_table('t', 'k', shard_count := 4);
> INSERT INTO t SELECT g, g, 'a' FROM generate_series(1, 20) g;
>
> -- on the worker holding shard 102008, started first:
> DO $$ BEGIN
>   WHILE NOT EXISTS (SELECT FROM pg_replication_slots WHERE confirmed_flush_lsn IS NOT NULL)
>   LOOP PERFORM pg_sleep(0.01); END LOOP;
>   UPDATE t_102008 SET v = 'b' WHERE id = 1 AND k = 1;
> END $$;
>
> -- coordinator, concurrently: never returns
> SELECT citus_split_shard_by_split_points(102008, ARRAY['-1610612737'], ARRAY[2, 3]);
> ```
>
> 3/3 hangs with UPDATE, 2/2 with DELETE, 0/2 with INSERT. If the UPDATE is
> issued after the split's subscriptions are enabled, the split completes.
>
> **Possible fix:** create the replica identities of split children that are
> placed on the source node together with the dummy shards' (before
> `CreateReplicationSlots`), i.e. treat them like dummies for this purpose.
>
> Caveat: so far reproduced only on amd64 under emulation (Apple Silicon /
> Rosetta); native confirmation pending.

## What this means for ShardML

- A plan can still use splits online, but must not place a split child on the
  source node until this is fixed. Both halves go to other nodes, possibly
  followed by a move back.
- This probably also affects any Citus operation that splits with a child staying
  on the source node, e.g. `isolate_tenant_to_new_shard` in logical mode. #6790's
  last comment points the same way for `create_distributed_table_concurrently`.
  **Not tested here.**
- The executor needs its own timeout and must watch
  `pg_stat_subscription_stats.apply_error_count` and slot lag. A hung split holds
  WAL on the source node and never reports an error to the caller.

## Files

- `repro.sh`: minimal Citus repro (`update` | `insert` | `delete`, `TIMEOUT=`).
- `plain-pg.sh`: the same setup on plain PostgreSQL.
