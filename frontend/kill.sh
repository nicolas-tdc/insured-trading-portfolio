#!/bin/bash

set -a

if [ -f .env ]; then
  source .env
fi

MODE="$1"

kill_service_process "pid"