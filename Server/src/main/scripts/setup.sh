#!/bin/bash
PATH=$PATH:$JAVA_HOME/bin:$GEMFIRE_HOME/bin

export CUR_DIR=`pwd`

export POC_CLASSPATH=${CUR_DIR}/../config:${CUR_DIR}/../lib/*:.

echo ""
echo "CLASSPATH as follows : ${POC_CLASSPATH} "
echo ""

