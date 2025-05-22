#!/bin/bash

set -a

if [ -f .env ]; then
  source .env
fi

mode="$1"

if [[ "$mode" == "dev" ]]; then
  ng serve --port $NG_PORT --proxy-config proxy.conf.json &
  echo $! > pid
else
  ng build --configuration=production
  echo $! > pid
fi
