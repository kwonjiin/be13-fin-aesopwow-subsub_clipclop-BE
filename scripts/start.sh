#!/bin/bash

echo "Start deployment script..."

APP_DIR=/home/ec2-user/app
BUILD_DIR=$APP_DIR/build/libs
SCRIPTS_DIR=$APP_DIR/scripts
DEPLOY_LOG=$APP_DIR/deploy.log
APP_LOG=$APP_DIR/app.log

TIME_NOW=$(date '+%Y-%m-%d %H:%M:%S')

if [ ! -f "$SCRIPTS_DIR/sha.txt" ]; then
  echo "$TIME_NOW > ERROR: $SCRIPTS_DIR/sha.txt 파일이 없습니다." >> $DEPLOY_LOG
  exit 1
fi

SHA=$(cat $SCRIPTS_DIR/sha.txt)
ZIP_NAME="${SHA}.zip"
JAR_NAME="subsubclipclop-0.0.1-SNAPSHOT.jar"
JAR_PATH="$BUILD_DIR/$JAR_NAME"

mkdir -p "$BUILD_DIR"

echo "$TIME_NOW > Downloading $ZIP_NAME from S3" >> $DEPLOY_LOG
aws s3 cp s3://aesop-be/$ZIP_NAME $BUILD_DIR/$ZIP_NAME >> $DEPLOY_LOG 2>&1
if [ $? -ne 0 ]; then
  echo "$TIME_NOW > Failed to download ZIP file from S3" >> $DEPLOY_LOG
  exit 1
fi

echo "$TIME_NOW > Unzipping $ZIP_NAME" >> $DEPLOY_LOG
unzip -o $BUILD_DIR/$ZIP_NAME -d $BUILD_DIR >> $DEPLOY_LOG 2>&1
if [ $? -ne 0 ]; then
  echo "$TIME_NOW > Failed to unzip file" >> $DEPLOY_LOG
  exit 1
fi

EXISTING_PID=$(pgrep -f "$JAR_NAME")
if [ -n "$EXISTING_PID" ]; then
  echo "$TIME_NOW > Stopping existing process (PID: $EXISTING_PID)" >> $DEPLOY_LOG
  kill $EXISTING_PID
  sleep 5
  if ps -p $EXISTING_PID > /dev/null; then
    echo "$TIME_NOW > Force killing process (PID: $EXISTING_PID)" >> $DEPLOY_LOG
    kill -9 $EXISTING_PID
  fi
fi

echo "$TIME_NOW > Starting JAR file" >> $DEPLOY_LOG
nohup java -jar "$JAR_PATH" --spring.profiles.active=prod >> $APP_LOG 2>&1 &
disown

NEW_PID=$!
echo "$TIME_NOW > New process started (PID: $NEW_PID)" >> $DEPLOY_LOG
