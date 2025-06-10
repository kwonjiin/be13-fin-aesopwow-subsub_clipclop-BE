#!/bin/bash

chmod u+w /home/ec2-user/app

# 환경 변수 적용
if [ -f "/home/ec2-user/app/.env" ]; then
  export $(grep -v '^#' /home/ec2-user/app/.env | xargs)
fi

JAR_NAME="subsubclipclop-0.0.1-SNAPSHOT.jar"
JAR_PATH="/home/ec2-user/app/build/libs/$JAR_NAME"
APP_LOG="/home/ec2-user/app/app.log"
PORT=8010

# 1. 포트 기반으로 기존 프로세스 확인 및 종료
PID=$(lsof -t -i:$PORT)
if [ -n "$PID" ]; then
  echo "포트 $PORT 사용 중인 프로세스 종료: $PID"
  kill -9 "$PID"
  sleep 2
fi

# 2. 새 애플리케이션 실행
echo "애플리케이션 시작..."
nohup java -jar "$JAR_PATH" --spring.profiles.active=prod >> "$APP_LOG" 2>&1 &
echo "백그라운드 실행 완료"
