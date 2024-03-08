FROM ubuntu:latest

RUN apt-get -y update
RUN apt-get -y install openjdk-17-jdk-headless
RUN apt-get -y install maven

WORKDIR /app
COPY . ./

CMD [ "mvn", "spring-boot:run"]