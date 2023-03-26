FROM openjdk:8-jdk-alpine
ADD ./target/accountAPI-0.0.1-SNAPSHOT.jar /usr/src/myapp/accountAPI-0.0.1-SNAPSHOT.jar
WORKDIR /usr/src/myapp
ENV DB_URL=jdbc:mysql://localhost:3306/java_to_dev_app_db
ENV DB_USERNAME=java_to_dev
ENV DB_PASSWORD=nE5kMc7JCGNqwDQM
EXPOSE 8080

ENTRYPOINT ["java","-jar","/accountAPI-0.0.1-SNAPSHOT.jar"]