#!/bin/bash

set -e

echo "Starting Insurance-Banking Platform..."

# Load global env if exists
if [ -f .env ]; then
  export $(grep -v '^#' .env | xargs)
fi

# Start database
echo "🔌 Starting Database..."
(cd database && ./run.sh)

# Start backend
echo "⚙️ Starting Backend..."
(cd backend && ./run.sh)

# Start frontend
echo "🌐 Starting Frontend..."
(cd frontend && ./run.sh)