#FROM openjdk:8-jdk-alpine
FROM openjdk:11-jre-slim
ADD target/accountAPI-0.0.1-SNAPSHOT.jar accountAPI-0.0.1-SNAPSHOT.jar

#ENV WAIT_VERSION 2.10.0
#ADD https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /wait
#RUN chmod +x /wait

EXPOSE 8080

ENTRYPOINT ["java","-jar","/accountAPI-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT ["/wait", "&&", "java","-jar","/accountAPI-0.0.1-SNAPSHOT.jar"]