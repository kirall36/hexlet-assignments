FROM gradle:7.2.0-jdk16
ARG JAR_FILE=app/build/libs/*.jar
COPY ${JAR_FILE} simple-application.jar
EXPOSE 3000
ENTRYPOINT ["java", "-jar", "simple-application.jar"]