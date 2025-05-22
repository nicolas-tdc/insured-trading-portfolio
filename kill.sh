#!/bin/bash

set -e

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

# Set the mode based on the first argument
MODE=""
if [ "$1" == "dev" ]; then
  MODE="dev"
  echo "Development mode enabled"
fi

kill_service() {
    local service_dir=$1

    local kill_script="kill.sh"

    if [ -f "$kill_script" ]; then
        cd "$service_dir"
        bash "$kill_script" "$MODE" > /dev/null 2>&1 &
        cd ..
    else
        echo "$kill_script not found in $service_dir."
    fi
}

kill_service_process() {
    local pid_file=$1

    if [ -f "$pid_file" ] ; then
        service_pid=$(cat "$pid_file")
        if ps -p $service_pid > /dev/null; then
            kill -9 $service_pid
        fi

        rm -f "$pid_file"
    fi
}
export -f kill_service_process

# Kill database
echo -e "\e[33mKilling\e[0m Database..."
kill_service "database"

# Kill Backend
echo -e "\e[33mKilling\e[0m Backend..."
kill_service "backend"

# Kill Frontend
echo -e "\e[33mKilling\e[0m Frontend..."
kill_service "frontend"

echo -e "🔻\e[32mStopped application\e[0m"