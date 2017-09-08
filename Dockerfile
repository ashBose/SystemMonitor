FROM ubuntu:14.04

# Install.
RUN \
  sed -i 's/# \(.*multiverse$\)/\1/g' /etc/apt/sources.list && \
  apt-get update && \
  apt-get -y upgrade && \
  apt-get install -y build-essential && \
  apt-get install -y software-properties-common && \
  rm -rf /var/lib/apt/lists/*

RUN apt-get install -y  openjdk-7-jdk
RUN apt-get install -y maven

ENV JAVA_HOME=/lib/jvm/java-1.7.0-openjdk

COPY SystemMonitor/ .
COPY entrypoint.sh /

ENTRYPOINT ["/entrypoint.sh"]
CMD ["produce"]
