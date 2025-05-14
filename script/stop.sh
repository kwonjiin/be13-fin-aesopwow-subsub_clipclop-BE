#!/bin/bash
echo "Stopping running app..."
pkill -f 'java -jar' || echo "No app running"
