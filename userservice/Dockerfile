FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy entire project
COPY userservice /app

RUN mvn -e -X -U dependency:purge-local-repository -DmanualInclude="org.apache.httpcomponents:httpcore5"

# Build jar
RUN mvn clean package -DskipTests

# ------------ Runtime image --------------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy jar from previous stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
