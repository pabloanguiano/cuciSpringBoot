
##FROM eclipse-temurin:26-jdk AS builder
#WORKDIR /app
#COPY . .
#ENV JAVA_HOME=/opt/java/openjdk
#ENV PATH="${JAVA_HOME}/bin:${PATH}"
#COPY pom.xml .
#RUN ./mvnw clean package -DskipTests
#
#
##FROM eclipse-temurin:26-jre
#WORKDIR /app
#COPY --from=builder /app/target/*.jar app.jar
#EXPOSE 8083
#ENTRYPOINT ["java", "-jar", "app.jar"]

# ETAPA 1: Compilación nativa rápida (ARM64 en tu Mac) usando Maven oficial
FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# Copiar el pom.xml y descargar dependencias primero (aprovecha la caché de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests -B

# ETAPA 2: Imagen final para AWS App Runner (x86_64 / amd64)
FROM --platform=linux/amd64 eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el JAR compilado desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]