#!/bin/bash
set -e

[[ "$1" == "subscribe" ]] && {

    cd /SystemMonitor
    mvn clean compile exec:java -Dexec.mainClass="main.java.com.monitor.controller"
}

exec "$@"
