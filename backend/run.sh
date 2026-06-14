#!/bin/bash

# Resource Labor Forecast - Quick Start Script
# This script helps you build and run the Spring Boot application

set -e  # Exit on error

echo "=========================================="
echo "Resource Labor Forecast - Quick Start"
echo "=========================================="
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed!"
    echo ""
    echo "Please install Java 17 first:"
    echo "  brew install openjdk@17"
    echo ""
    echo "Or follow the instructions in SETUP.md"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Java 17 or higher is required!"
    echo "Current version: $(java -version 2>&1 | head -n 1)"
    echo ""
    echo "Please upgrade Java:"
    echo "  brew install openjdk@17"
    exit 1
fi

echo "✅ Java version: $(java -version 2>&1 | head -n 1)"

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed!"
    echo ""
    echo "Please install Maven first:"
    echo "  brew install maven"
    echo ""
    echo "Or follow the instructions in SETUP.md"
    exit 1
fi

echo "✅ Maven version: $(mvn -version | head -n 1)"
echo ""

# Build the application
echo "📦 Building the application..."
echo ""
mvn clean install -DskipTests

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Build successful!"
    echo ""
    echo "🚀 Starting the application..."
    echo ""
    echo "=========================================="
    echo "Press Ctrl+C to stop the application"
    echo "=========================================="
    echo ""
    
    # Run the application
    mvn spring-boot:run
else
    echo ""
    echo "❌ Build failed! Please check the error messages above."
    exit 1
fi

# Made with Bob
