FROM openjdk:8-jdk-alpine
ADD target/accountAPI-0.0.1-SNAPSHOT.jar accountAPI-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/accountAPI-0.0.1-SNAPSHOT.jar"]