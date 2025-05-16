#!/bin/bash

echo "Deploy script: Start app..."

APP_DIR=/home/ec2-user/app
ZIP_NAME=$(ls $APP_DIR | grep ".zip" | head -n 1)
DEPLOY_DIR=$APP_DIR/deploy

# 1. 압축 해제
echo "Unzipping $ZIP_NAME..."
mkdir -p $DEPLOY_DIR
unzip -o $APP_DIR/$ZIP_NAME -d $DEPLOY_DIR

# 2. JAR 실행
JAR_NAME=$(ls $DEPLOY_DIR/deploy/*.jar | head -n 1)

if [ -f "$JAR_NAME" ]; then
  echo "Starting $JAR_NAME with profile 'prod'..."
  nohup java -jar "$JAR_NAME" --spring.profiles.active=prod > $APP_DIR/app.log 2>&1 &
else
  echo "JAR file not found in $DEPLOY_DIR/deploy/"
  exit 1
fi
