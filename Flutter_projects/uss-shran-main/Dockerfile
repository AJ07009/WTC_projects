FROM debian:buster-slim

RUN apt-get update
RUN apt-get install -y openjdk-11-jre curl

ADD Server/target/robotWorld-*-SNAPSHOT-webAPI-jar-with-dependencies.jar /srv/apiserver.jar
ADD RobotWorldsDB.db /srv/RobotWorldsDB.db
#ADD Docker_run.sh /srv/run.sh
#Comment for push
#COPY "Docker_run.sh" .

WORKDIR /srv
EXPOSE 5000

CMD ["java","-jar", "apiserver.jar"]
