#!/bin/bash

set -a

if [ -f .env ]; then
  source .env
fi

mode="$1"

kill_service_process "pid"