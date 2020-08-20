#!/bin/bash

mvn clean package -Dmaven.test.skip=true -f ./pom.xml
#docker build -t ay-social .
#docker tag ay-social:latest alexyakovlev90/ay-social:v11
#docker push alexyakovlev90/ay-social:v11

docker build -t ay-social-chat ./social-chat/
docker tag ay-social-chat:latest alexyakovlev90/ay-social-chat:v1
docker push alexyakovlev90/ay-social-chat:v1
