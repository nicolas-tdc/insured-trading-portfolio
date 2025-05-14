#!/bin/bash

set -a
if [ -f .env ]; then
  source .env
fi
set +a

MODE="$1"

if [[ "$MODE" == "dev" ]]; then
  echo "Backend: DEV mode (hot-reload enabled)"
  ./mvnw spring-boot:run
else
  echo "Backend: PROD mode"
  ./mvnw clean package
  java -jar target/*.jar
fi