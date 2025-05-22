#!/bin/bash

set -a

check_requirements() {
  local requirements=("docker" "curl" "ng" "npm" "node" "java" "mvn")
  for req in "${requirements[@]}"; do
    if ! command -v "$req" &> /dev/null; then
      echo "❌ $req not found. Please install or update $req. Exiting..."
      exit 1
    fi
    echo "✅ $req is installed."
  done

  if ! docker compose version &> /dev/null; then
    echo "❌ Docker Compose (v2 plugin) not found. Please install it."
    exit 1
  fi
}

run_service() {
    local service_dir=$1
    local $mode=$2
    local debug=$2

    local run_script="run.sh"

    if [ -f "$run_script" ]; then
        cd "$service_dir"

        if [ "$debug" == "debug" ]; then
          echo "Debug enabled"
          bash "$run_script" "$mode"
        else
          echo "Debug disabled"
          bash "$run_script" "$mode" > /dev/null 2>&1 &
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
        echo "❌ Not accessible after $max_tries tries. Exiting..."
        exit 1
      fi

      echo "Retrying in 10 seconds..."
      sleep 10
    fi
  done
}

check_requirements

# Set the mode based on the first argument
mode=""
if [ "$1" == "dev" ]; then
  mode="dev"
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
  bash ./kill.sh "$mode" > /dev/null 2>&1
else
  echo "kill.sh not found."
fi

# Start Database
echo -e "\e[33mStarting\e[0m Database..."
run_service "database" "$mode"

# Start Backend
echo -e "\e[33mStarting\e[0m Backend..."
run_service "backend" "$mode"

# Check if Backend service is accessible
echo -e "\e[33mWaiting\e[0m for backend..."
check_url "http://localhost:8080" 10 10

# Start Frontend
echo -e "\e[33mStarting\e[0m Frontend..."
run_service "frontend" "$mode"

# Check if Frontend service is accessible
echo -e "\e[33mWaiting\e[0m for frontend..."
check_url "http://localhost:4200" 10 10
