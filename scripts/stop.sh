#!/bin/bash

echo "🔴 Stopping running app..."

# 현재 실행 중인 JAR 프로세스 종료
PID=$(pgrep -f 'java -jar')
if [ -n "$PID" ]; then
  kill -9 $PID
  echo "✅ Killed existing process with PID: $PID"
else
  echo "ℹ️ No running Java app found."
fi

# 이전 JAR 파일 삭제
echo "🧹 Removing old JAR files..."
rm -f /home/ec2-user/app/*.jar

echo "✅ Clean up complete."
