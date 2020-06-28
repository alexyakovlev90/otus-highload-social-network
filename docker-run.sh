#!/bin/bash

docker run --name ay-social \
    -d --rm -p 8888:8888 \
    alexyakovlev90/ay-social:v9
#    --env-file=env-var.properties \
