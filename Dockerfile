FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080
ARG JAR_FILE
COPY ${JAR_FILE} parcel-wizard-api.jar
ENTRYPOINT ["java","-jar","/parcel-wizard-api.jar"]
