
FROM postgres:latest
EXPOSE 5432

FROM maven:3.9.9 AS build
COPY pom.xml .
COPY /src ./src/
RUN mvn clean package -DskipTests

FROM openjdk:17 AS prod
COPY --from=build target/vertical-sharding-0.0.1-SNAPSHOT.jar vertical-sharding.jar
EXPOSE 9000

ENTRYPOINT ["java", "-jar", "vertical-sharding.jar"]