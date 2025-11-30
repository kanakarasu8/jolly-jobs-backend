FROM amazoncorretto:17

WORKDIR /app

# Copy ONLY the userservice jar
COPY userservice/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
