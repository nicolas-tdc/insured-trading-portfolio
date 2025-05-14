#!/bin/bash

set -a

if [ -f .env ]; then
  source .env
fi
set +a

./mvnw spring-boot:run