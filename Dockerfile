# 1. Adım: Maven kullanarak projenizi derleyin
# Maven image kullanarak projenin derlenmesi
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Adım: OpenJDK 17 image kullanarak uygulamayı çalıştırın
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]


