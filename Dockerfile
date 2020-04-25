FROM openjdk:12-oraclelinux7
MAINTAINER Vanderson Assis
RUN mkdir /opt/app
COPY /target/market-place-purchase-0.0.1-SNAPSHOT.jar /opt/app
WORKDIR /opt/app
CMD java -jar market-place-purchase-0.0.1-SNAPSHOT.jar
EXPOSE 8080