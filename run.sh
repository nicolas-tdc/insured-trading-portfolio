#!/bin/bash

set -a

# Check Docker is available
if ! command -v docker &> /dev/null; then
  echo "❌ Docker not found. Please install Docker. Exiting..."
  exit 1
fi

# Check if Docker Compose is available
if ! command -v docker compose &> /dev/null; then
  echo "❌ Docker Compose not found. Please install Docker Compose. Exiting..."
  exit 1
fi

# Check if curl is available
if ! command -v curl &> /dev/null; then
  echo "❌ curl not found. Please install curl. Exiting..."
  exit 1
fi

# Set the mode based on the first argument
mode=""
if [ "$1" == "dev" ]; then
  mode="dev"
  echo "Starting in DEV mode (hot-reload enabled)"
else
  echo "Starting in PROD mode"
fi

# Load global env if exists
echo "Loading environment variables..."
if [ -f .env ]; then
  export $(grep -v '^#' .env | xargs)
fi

# Kill running services
echo "Killing existing services..."
if [ -f "kill.sh" ]; then
  bash ./kill.sh $mode > /dev/null 2>&1
else
  echo "kill.sh not found."
fi

run_service() {
    local service_dir=$1

    local run_script="run.sh"

    if [ -f "$run_script" ]; then
        cd "$service_dir"
        bash "$run_script" $MODE > /dev/null 2>&1 &
        cd ..
    else
        echo "$run_script not found in $service_dir."
    fi
}

# Start Database
echo "Starting Database..."
run_service "database"

# Start Backend
echo "Starting Backend..."
run_service "backend"

# Start Frontend
echo "Starting Frontend..."
run_service "frontend"

# Check if the application is accessible
url="http://localhost:8080"

echo "Waiting for $url to become accessible..."
try_count=0
max_tries=10
while true; do
  status_code=$(curl -o /dev/null -s -w "%{http_code}" "$url")

  if [[ "$status_code" == "200" || "$status_code" == "302" || "$status_code" == "401" ]]; then
    echo "🚀 Access the application at: $url"
    exit 0
  else
    try_count=$((try_count + 1))
    echo "Testing $url [$try_count/$max_tries]"

    if [ $try_count -ge $max_tries ]; then
      echo "❌ Application is not accessible after $max_tries tries. Exiting..."
      exit 1
    fi

    echo "Retrying in 5 seconds..."
    sleep 10
  fi
done