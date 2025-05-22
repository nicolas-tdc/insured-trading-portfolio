#!/bin/bash

set -a

if [ -f .env ]; then
  source .env
fi

mode="$1"

if [[ "$mode" == "dev" ]]; then
  ./mvnw spring-boot:run &
  echo $! > pid
else
  ./mvnw clean package
  java -jar target/*.jar &
  echo $! > pid
fi