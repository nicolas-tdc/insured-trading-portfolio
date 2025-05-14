#!/bin/bash

set -e

# Load global env if exists
if [ -f .env ]; then
  export $(grep -v '^#' .env | xargs)
fi

MODE=""

if [[ "$1" == "dev" ]]; then
  MODE="dev"
  echo "Starting in DEV mode (hot-reload enabled)..."
else
  echo "Starting in PROD mode..."
fi

# Start DB
echo "🔌 Starting Database..."
(cd database && ./run.sh)

# Start Backend in background
echo "⚙️ Starting Backend..."
(cd backend && ./run.sh $MODE) &
BACKEND_PID=$!

# Start Frontend in background
echo "🌐 Starting Frontend..."
(cd frontend && ./run.sh $MODE) &
FRONTEND_PID=$!

# Trap Ctrl+C to gracefully stop services
trap "kill $BACKEND_PID $FRONTEND_PID; exit" INT TERM

echo "✅ Platform running. Press Ctrl+C to stop."

# Wait until process is terminated
wait