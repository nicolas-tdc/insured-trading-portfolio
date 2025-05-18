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
  ng build --prod
  ng serve --prod --port $NG_PORT &
  echo $! > pid
fi