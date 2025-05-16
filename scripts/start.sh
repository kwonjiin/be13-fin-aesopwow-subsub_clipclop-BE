#!/bin/bash

echo "Start deployment script..."

APP_DIR=/home/ec2-user/app
BUILD_DIR=$APP_DIR/build/libs
SCRIPTS_DIR=$APP_DIR/scripts
DEPLOY_LOG=$APP_DIR/deploy.log
APP_LOG=$APP_DIR/app.log

TIME_NOW=$(date '+%Y-%m-%d %H:%M:%S')

SHA=$(cat $SCRIPTS_DIR/sha.txt)
ZIP_NAME="${SHA}.zip"
JAR_NAME="subsubclipclop-0.0.1-SNAPSHOT.jar"
JAR_PATH="$BUILD_DIR/$JAR_NAME"

# S3에서 ZIP 다운로드
echo "$TIME_NOW > Downloading $ZIP_NAME from S3" >> $DEPLOY_LOG
aws s3 cp s3://aesop-be/$ZIP_NAME $BUILD_DIR/$ZIP_NAME >> $DEPLOY_LOG 2>&1
if [ $? -ne 0 ]; then
  echo "$TIME_NOW > Failed to download ZIP file from S3" >> $DEPLOY_LOG
  exit 1
fi

# 기존 빌드 디렉토리 정리 (선택 사항)
# rm -rf $BUILD_DIR/*

# ZIP 압축 해제
echo "$TIME_NOW > Unzipping $ZIP_NAME" >> $DEPLOY_LOG
unzip -o $BUILD_DIR/$ZIP_NAME -d $BUILD_DIR >> $DEPLOY_LOG 2>&1
if [ $? -ne 0 ]; then
  echo "$TIME_NOW > Failed to unzip file" >> $DEPLOY_LOG
  exit 1
fi

# 기존 JAR 프로세스 종료
EXISTING_PID=$(pgrep -f "$JAR_NAME")
if [ -n "$EXISTING_PID" ]; then
  echo "$TIME_NOW > Stopping existing process (PID: $EXISTING_PID)" >> $DEPLOY_LOG
  kill -9 $EXISTING_PID
fi

# JAR 실행
echo "$TIME_NOW > Starting JAR file" >> $DEPLOY_LOG
nohup java -jar "$JAR_PATH" --spring.profiles.active=prod >> $APP_LOG 2>&1 &

NEW_PID=$!
echo "$TIME_NOW > New process started (PID: $NEW_PID)" >> $DEPLOY_LOG
