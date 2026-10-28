# Citus test cluster

> **Apple Silicon: timings are emulated.** `citusdata/citus` is published for
> amd64 only, so on an arm64 Mac every node runs under Rosetta (or QEMU)
> emulation. Measurements taken here are valid for **yes/no blocking
> behaviour** only (does a write stall during an operation or not), **not for
> absolute latency or throughput**. Cost-model validation needs the native
> arm64 image, and final evaluation needs an x86 cloud VM; both are tracked in
> `docs/PLAN.md`, Phase 0.

A local Citus cluster for the Phase 0 spike and, later, the integration tests: one
coordinator and three workers, `citusdata/citus:14.2.0-pg17` (Citus 14.2.0 on
PostgreSQL 17).

```sh
bench/up.sh      # start, wait until healthy, register workers, print versions
bench/down.sh    # stop and delete all data
```

Both scripts work from any directory. `up.sh` can be run again on a running
cluster: workers that are already registered are skipped.

Only the coordinator is reachable from the host, on `127.0.0.1:5433`:

```sh
psql -h 127.0.0.1 -p 5433 -U postgres
```

Set `CITUS_PORT` to use another port (`CITUS_PORT=6000 bench/up.sh`). There is
no password (`trust` authentication), so the port is bound to localhost only.

There are no volumes: every `up.sh` starts from an empty cluster, and `down.sh`
deletes everything.

Every node runs with `wal_level=logical`. Citus needs it for online shard moves
and splits; with the Postgres default (`replica`), `shard_transfer_mode => 'auto'`
falls back to blocking writes without any error.

## Requirements

- Docker with Compose v2 (`docker compose`, not `docker-compose`).
- **Memory:** give Docker Desktop at least **4 GB** (Settings → Resources →
  Memory). Each node uses about 120 MiB idle (measured on Citus 14.2.0 after
  loading 100k rows), so the cluster itself needs about 0.5 GB. The headroom is
  for the four Postgres instances' `shared_buffers` (128 MB each by default),
  per-connection memory under a write loop, and shard copies during moves and
  splits. Use 6–8 GB for spike runs with tables much larger than a few million
  rows.
- **Apple Silicon:** enable *Use Rosetta for x86_64/amd64 emulation on Apple
  Silicon* (Settings → General). Without it Docker falls back to QEMU, which is
  much slower.
