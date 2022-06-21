FROM openjdk:8-oracle
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} codeMarkTestTask.jar
ENTRYPOINT ["java", "-jar", "codeMarkTestTask.jar"]