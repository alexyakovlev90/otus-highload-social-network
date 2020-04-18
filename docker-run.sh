#!/bin/bash

docker run --name ay-social \
    -d --rm -p 8080:8080 \
    alexyakovlev90/ay-social:v1
#    --env-file=env-var.properties \
