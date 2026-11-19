# Etapa de compilação
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x mvnw

COPY src src
RUN ./mvnw -B clean package -DskipTests

# Etapa de execução
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/filmes-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]
