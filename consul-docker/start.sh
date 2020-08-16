#!/bin/bash

docker-compose down
rm -rf ./mysql/data/*
rm -rf ./rabbitmq/data/*
rm -rf ./redis/data/*
docker-compose build
docker-compose up -d mysql redis rabbitmq consul chat_app

sleep 5

curl -s -XPUT -d"{
  \"Name\": \"mysql\",
  \"ID\": \"mysql\",
  \"Tags\": [ \"mysql\" ],
  \"Address\": \"localhost\",
  \"Port\": 4406,
  \"Check\": {
    \"Name\": \"MySQL TCP on port 3306\",
    \"ID\": \"mysql\",
    \"Interval\": \"10s\",
    \"TCP\": \"mysql:3306\",
    \"Timeout\": \"1s\",
    \"Status\": \"passing\"
  }
}" localhost:8500/v1/agent/service/register

curl -s -XPUT -d"{
  \"Name\": \"chat_app_grpc\",
  \"ID\": \"chat_app_grpc\",
  \"Tags\": [ \"chat_app\" ],
  \"Address\": \"localhost\",
  \"Port\": 7070,
  \"Check\": {
    \"Name\": \"chat_app_grpc TCP on port 7070\",
    \"ID\": \"chat_app\",
    \"Interval\": \"10s\",
    \"TCP\": \"chat_app:7070\",
    \"Timeout\": \"1s\",
    \"Status\": \"passing\"
  }
}" localhost:8500/v1/agent/service/register

sleep 5
docker-compose up -d social_app
