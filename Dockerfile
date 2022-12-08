FROM openjdk:8-jdk-alpine
COPY target/ELearningByAshok.jar ELearningByAshok.jar
ENTRYPOINT ["java","-jar","/ELearningByAshok.jar"]