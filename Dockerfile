FROM openjdk:11.0.6-jdk-stretch

EXPOSE 8080

WORKDIR /usr/local/bin/

COPY target/user-service-0.0.1-SNAPSHOT.jar user-service.jar

CMD ["java", "-jar", "user-service.jar"]