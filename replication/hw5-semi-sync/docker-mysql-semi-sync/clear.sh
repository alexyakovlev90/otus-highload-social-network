#!/bin/bash

docker-compose down
rm -rf ./master/data/*
rm -rf ./slave1/data/*
rm -rf ./slave2/data/*