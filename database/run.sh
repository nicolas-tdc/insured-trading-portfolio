#!/bin/bash

set -a

if [ -f .env ]; then
  source .env
fi

docker compose up -d --build --force-recreate --remove-orphans
