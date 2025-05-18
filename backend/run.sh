#!/bin/bash

set -a

if [ -f .env ]; then
  source .env
fi

MODE="$1"

if [[ "$MODE" == "dev" ]]; then
  ./mvnw spring-boot:run &
  echo $! > pid
else
  ./mvnw clean package
  java -jar target/*.jar &
  echo $! > pid
fi