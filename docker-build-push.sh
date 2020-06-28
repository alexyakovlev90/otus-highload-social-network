#!/bin/bash

mvn clean package -Dmaven.test.skip=true -f ./pom.xml
docker build -t ay-social .
docker tag ay-social:latest alexyakovlev90/ay-social:v9
docker push alexyakovlev90/ay-social:v9
