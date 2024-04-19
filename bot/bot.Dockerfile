FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/bot.jar /app/bot.jar
EXPOSE 8090
CMD ["java", "-jar", "bot.jar"]
