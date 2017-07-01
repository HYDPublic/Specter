#!/bin/bash

KAFKA_HOME=${HOME}/Downloads/kafka_2.11-0.10.2.0

PATH=$KAFKA_HOME/bin:$PATH

export KAFKA_HOME
export PATH

xterm -e "$KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties"

echo "*** Let the force be with you ***"
