#!/bin/bash

set -a

if [ -f .env ]; then
  source .env
fi

MODE="$1"

if [[ "$MODE" == "dev" ]]; then
  ng serve --port $NG_PORT --proxy-config proxy.conf.json &
  echo $! > pid
else
  ng build --configuration=production
  echo $! > pid
fi
