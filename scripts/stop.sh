#!/bin/bash

echo "Stopping running app..."

# 현재 실행 중인 JAR 프로세스 종료
PID=$(pgrep -f 'java -jar')
if [ -n "$PID" ]; then
  echo "Found Java PID: $PID"
  kill -9 $PID
  echo "Killed existing process with PID: $PID"
else
  echo "No running Java app found."
fi

# 이전 JAR 파일 삭제
echo "Removing old JAR files..."
ls -l /home/ec2-user/app/
rm -v /home/ec2-user/app/*.jar
ls -l /home/ec2-user/app/

echo "Clean up complete."
