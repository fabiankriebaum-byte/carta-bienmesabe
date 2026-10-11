# Paso 1: Usar una imagen oficial de Eclipse Temurin y Maven para compilar
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Paso 2: Usar la imagen oficial ligera de Eclipse Temurin para ejecutar la app
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/data ./data
COPY --from=build /app/img ./img
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
