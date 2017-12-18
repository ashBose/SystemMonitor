#!/bin/bash
set -e

[[ "$1" == "produce" ]] && {

    #cd /SystemMonitor
    #mvn clean compile exec:java -Dexec.mainClass="main.java.com.monitor.controller"
    sleep 10000
}

exec "$@"
