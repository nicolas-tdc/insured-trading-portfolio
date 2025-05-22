#!/bin/bash

set -e

check_requirements() {
  local requirements=("docker")
  for req in "${requirements[@]}"; do
    if ! command -v "$req" &> /dev/null; then
      echo "❌ $req not found. Please install or update $req. Exiting..."
      exit 1
    fi
  done

  if ! docker compose version &> /dev/null; then
    echo "❌ Docker Compose (v2 plugin) not found. Please install it."
    exit 1
  fi
}

kill_service() {
    local service_dir=$1

    local kill_script="kill.sh"

    if [ -f "$kill_script" ]; then
        cd "$service_dir"
        bash "$kill_script" "$mode" > /dev/null 2>&1 &
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

# Set the mode based on the first argument
mode=""
if [ "$1" == "dev" ]; then
  mode="dev"
  echo "Development mode enabled"
fi

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