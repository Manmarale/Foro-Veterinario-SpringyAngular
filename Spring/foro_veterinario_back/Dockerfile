
# ============================
#  Etapa 1: Build del proyecto
# ============================

FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app
# Copiamos solo el POM primero (para cachear dependencias)
COPY pom.xml .
# Copiamos el código fuente y compilamos
COPY  src ./src
RUN mvn clean package -DskipTests


# ============================
#  Etapa 2: Imagen final (runtime)
# ============================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos solo el JAR compilado desde la etapa anterior
COPY --from=build /app/target/foro_veterinario-0.0.1-SNAPSHOT.jar app.jar

# Puerto por defecto
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]



