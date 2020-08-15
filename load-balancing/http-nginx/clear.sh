#!/bin/bash

docker-compose down
rm -rf ./mysql/data/*
rm -rf ./rabbitmq/data/*
rm -rf ./redis/data/*
