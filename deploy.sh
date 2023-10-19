#!/bin/bash

APP_NAME="parcel-wizard-api"
IMAGE_NAME="apps/parcelwizard"
JAR_FILE_PATH="./build/libs/parcel-wizard-api.jar"
PS_CHECK=`docker ps -a | grep ${APP_NAME}`

echo "START........."
if [ "${PS_CHECK}" != "" ];then
  echo "기존에 실행중인 ${APP_NAME}의 컨테이너와 이미지를 삭제합니다."
  docker stop ${APP_NAME}; docker rm ${APP_NAME}; docker rmi ${IMAGE_NAME};
fi

echo "이미지 빌드를 시작합니다..."
docker build --build-arg JAR_FILE=${JAR_FILE_PATH} -t ${IMAGE_NAME} .

echo "컨테이너 구동을 시작합니다..."
docker run -d -p 8081:8081 --name ${APP_NAME} ${IMAGE_NAME}

echo "END........."
docker ps -a
