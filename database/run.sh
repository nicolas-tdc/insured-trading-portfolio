#!/bin/bash

set -a

if [ -f .env ]; then
  source .env
fi

$mode="$1"

docker compose up -d --build --force-recreate --remove-orphans
