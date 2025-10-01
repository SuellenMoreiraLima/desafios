# ===================================================================
# DESAFIO SANTANDER - DOCKERFILE MULTI-STAGE
# ===================================================================

# Stage 1: Build da aplicação
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /build

# Copiar arquivos Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Baixar dependências (layer cache)
RUN ./mvnw dependency:go-offline -B

# Copiar código fonte
COPY src src

# Build da aplicação
RUN ./mvnw clean package -DskipTests -B

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

# Criar usuário não-root para segurança
RUN addgroup -S spring && adduser -S spring -G spring

# Instalar dependências para healthcheck
RUN apk add --no-cache curl

# Configurar diretório de trabalho
WORKDIR /app

# Copiar jar da stage anterior
COPY --from=builder /build/target/desafio-*.jar app.jar

# Mudar ownership para usuário spring
RUN chown spring:spring app.jar

# Mudar para usuário não-root
USER spring:spring

# Expor porta
EXPOSE 8080

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# JVM otimizada para containers
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-XX:+UseG1GC", \
    "-XX:+UseStringDeduplication", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "app.jar"]

# Metadados
LABEL maintainer="Desafio Santander" \
      description="API para consulta de CEP com log de consultas" \
      version="1.0.0" \
      java.version="17"