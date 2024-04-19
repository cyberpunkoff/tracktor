FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/scrapper.jar /app/scrapper.jar
EXPOSE 8090
CMD ["java", "-jar", "scrapper.jar"]
