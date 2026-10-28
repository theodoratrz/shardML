#!/usr/bin/env bash
# Reproduce the Phase 0 spike: for each operation, recreate the Citus cluster,
# load the table, run the operation under write load, then summarise and plot.
#
#   bench/spike/run.sh                 # all four operations
#   bench/spike/run.sh move split      # a subset
#
# The Python venv lives in bench/spike/.venv and is created on first run.
set -euo pipefail

cd "$(dirname "$0")"

if [[ ! -x .venv/bin/python ]]; then
	python3 -m venv .venv
	.venv/bin/pip install -q -r requirements.txt
fi

.venv/bin/python spike.py "$@"
../down.sh >/dev/null 2>&1
.venv/bin/python analyze.py
