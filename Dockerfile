FROM maven:3-amazoncorretto-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn -P prod-docker -DskipTests=true package

FROM amazoncorretto:21
EXPOSE 9090
COPY --from=builder /app/target/rent-house.jar /opt/app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar", "/opt/app.jar"]