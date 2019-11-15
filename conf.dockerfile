FROM openjdk:12-oraclelinux7
MAINTAINER Vanderson Assis
ENV SPRING_PROFILES_ACTIVE deploy
RUN mkdir /opt/app
COPY /target/market-place-purchase-0.0.1.jar /opt/app
WORKDIR /opt/app
CMD java -jar market-place-purchase-0.0.1.jar
EXPOSE 8081