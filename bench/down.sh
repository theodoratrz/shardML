#!/usr/bin/env bash
# Stop the Citus cluster and discard its data.
set -euo pipefail

cd "$(dirname "$0")"

docker compose down -v --remove-orphans
