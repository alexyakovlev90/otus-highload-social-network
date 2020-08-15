#!/bin/bash

#docker build -t kittenhouse .

docker-compose down
rm -rf ./mysql/data/*
rm -rf ./rabbitmq/data/*
rm -rf ./redis/data/*
docker-compose build
docker-compose up -d mysql redis rabbitmq

sleep 5
docker-compose up -d app1 app2

sleep 10
docker-compose up -d nginx

