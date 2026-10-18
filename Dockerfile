FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/spokify-back.jar /app

# CMD ["mvn","spring-boot:run"]
ENTRYPOINT ["java","-jar", "spokify-back.jar"]