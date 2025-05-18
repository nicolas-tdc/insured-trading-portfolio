#!/bin/bash

set -a

if [ -f .env ]; then
  source .env
fi

docker compose down --remove-orphans
