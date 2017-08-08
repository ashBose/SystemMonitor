FROM ubuntu:14.04

# Install.
RUN \
  sed -i 's/# \(.*multiverse$\)/\1/g' /etc/apt/sources.list && \
  apt-get update && \
  apt-get -y upgrade && \
  apt-get install -y build-essential && \
  apt-get install -y software-properties-common && \
  apt-get install -y byobu curl git htop man unzip vim wget && \
  rm -rf /var/lib/apt/lists/*

RUN apt-get install -y  openjdk-7-jdk
RUN apt-get install -y maven

COPY SystemMonitor/ .
#mvn clean compile exec:java -Dexec.mainClass="main.java.com.monitor.controller"
