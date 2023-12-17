FROM openjdk:18-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} hibernate-final-project-1.0.jar
ENTRYPOINT ["java", "-jar", "hibernate-final-project-1.0.jar"]