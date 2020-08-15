#!/bin/bash

#docker build -t kittenhouse .

docker-compose down
rm -rf ./mysql-master/data/*
rm -rf ./kittenhouse/data/*
rm -rf ./clickhouse-server/data/*
rm -rf ./rabbitmq/data/*
rm -rf ./redis/data/*
docker-compose build
docker-compose up -d

