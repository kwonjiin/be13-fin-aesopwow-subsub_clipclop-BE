#!/bin/bash
echo "Starting app..."
nohup java -jar /home/ec2-user/app/subsubclipclop-0.0.1-SNAPSHOT.jar > /home/ec2-user/app/app.log 2>&1 &
