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
MODE=""
if [ "$1" == "dev" ]; then
  MODE="dev"
  echo "Development mode enabled"
fi

# Load global env if exists
echo "Loading environment variables..."
if [ -f .env ]; then
  export $(grep -v '^#' .env | xargs)
fi

# Kill running services
echo "Killing existing services..."
if [ -f "kill.sh" ]; then
  bash ./kill.sh "$MODE" > /dev/null 2>&1
else
  echo "kill.sh not found."
fi

run_service() {
    local service_dir=$1
    local debug=$2

    local run_script="run.sh"

    if [ -f "$run_script" ]; then
        cd "$service_dir"

        if [ "$debug" == "debug" ]; then
          echo "Debug enabled"
          bash "$run_script" "$MODE"
        else
          echo "Debug disabled"
          bash "$run_script" "$MODE" > /dev/null 2>&1 &
        fi

        cd ..
    else
        echo "$run_script not found in $service_dir."
    fi
}

check_url() {
  local url=$1
  local sleep_time=$2
  local max_tries=$3

  try_count=0
  max_tries=10
  while true; do
    status_code=$(curl -o /dev/null -s -w "%{http_code}" "$url")

    if [[ \
      "$status_code" == "200" || \
      "$status_code" == "302" || \
      "$status_code" == "401" \
      ]]; then
      echo -e "🚀 \e[32mAccess available at\e[0m $url"
      break
    else
      try_count=$((try_count + 1))
      echo "Testing $url [$try_count/$max_tries]"

      if [ $try_count -ge $max_tries ]; then
        echo "❌ Application is not accessible after $max_tries tries. Exiting..."
        exit 1
      fi

      echo "Retrying in 10 seconds..."
      sleep 10
    fi
  done
}

# Start Database
echo -e "\e[33mStarting\e[0m Database..."
run_service "database"

# Start Backend
echo -e "\e[33mStarting\e[0m Backend..."
run_service "backend"

# Check if Backend service is accessible
echo -e "\e[33mWaiting\e[0m for backend..."
check_url "http://localhost:8080" 10 10

# Start Frontend
echo -e "\e[33mStarting\e[0m Frontend..."
run_service "frontend"

# Check if Frontend service is accessible
echo -e "\e[33mWaiting\e[0m for frontend..."
check_url "http://localhost:4200" 10 10
