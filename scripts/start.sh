#!/bin/bash

set -e
chmod u+w /home/ec2-user/app

# 환경 변수 적용
if [ -f "/home/ec2-user/app/.env" ]; then
  export $(grep -v '^#' /home/ec2-user/app/.env | xargs)
fi

#  기본 경로 설정
JAR_NAME="subsubclipclop-0.0.1-SNAPSHOT.jar"
JAR_PATH="/home/ec2-user/app/build/libs/subsubclipclop-0.0.1-SNAPSHOT.jar"
APP_LOG="/home/ec2-user/app/app.log"

# 1. 기존 gunicorn 프로세스 종료
PID=$(pgrep -f "$JAR_NAME")
if [ -n "${PID:-}" ]; then
  echo "기존 gunicorn 프로세스 종료: $PID"
  kill -9 $PID
  sleep 2
fi

# 새 애플리케이션 실행 (백그라운드)
nohup java -jar "$JAR_PATH" --spring.profiles.active=prod >> "$APP_LOG" 2>&1 &
disown  # 부모 프로세스 종료되어도 계속 실행되도록