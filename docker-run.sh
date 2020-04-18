#!/bin/bash

docker run --name trx-receiver \
    -d --rm -p 8080:8080 \
    --env-file=env-var.properties \
    alexyakovlev90/trx-receiver:latest
