#!/bin/bash

. ./setup.sh

mkdir -p ${CUR_DIR}/../log/locator
mkdir -p ${CUR_DIR}/../log/server1
mkdir -p ${CUR_DIR}/../log/server2

gfsh <<!
start locator --name=locator1 --properties-file=${CUR_DIR}/../config/locator/gemfire.properties --bind-address=localhost --port=10334 --dir=${CUR_DIR}/../log/locator --J=-Xms512m --J=-Xmx512m
start server --name=server1 --classpath=${POC_CLASSPATH} --server-port=40411 --cache-xml-file=${CUR_DIR}/../config/server/cache.xml --properties-file=${CUR_DIR}/../config/server/gemfire.properties --locators=localhost[10334] --dir=${CUR_DIR}/../log/server1 --initial-heap=4g --max-heap=4g --J=-Dgemfire.http-service-port=7070 --J=-Dgemfire.http-service-bind-address=localhost --J=-Dgemfire.start-dev-rest-api=true --J=-Xss256k
start server --name=server2 --classpath=${POC_CLASSPATH} --server-port=40412 --cache-xml-file=${CUR_DIR}/../config/server/cache.xml --properties-file=${CUR_DIR}/../config/server/gemfire.properties --locators=localhost[10334] --dir=${CUR_DIR}/../log/server2 --initial-heap=4g --max-heap=4g --J=-Xss256k
connect --locator=localhost[10334];
list members;
list regions;
exit;
!

