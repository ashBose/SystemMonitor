FROM ubuntu:16.04

# Install.
RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y  software-properties-common && \
    add-apt-repository ppa:webupd8team/java -y && \
    apt-get update && \
    echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
    apt-get install -y oracle-java8-installer && \
    apt-get clean && apt-get install -y git
RUN apt-get install -y maven

RUN git clone https://github.com/ashBose/SystemMonitor.git
#ENV JAVA_HOME=/lib/jvm/java-1.7.0-openjdk

#COPY SystemMonitor/ .
COPY libsigar-amd64-linux.so /
COPY entrypoint.sh /

ENTRYPOINT ["/entrypoint.sh"]
CMD ["produce"]
