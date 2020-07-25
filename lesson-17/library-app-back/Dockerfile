FROM openjdk:11-jre-slim
COPY /target/library-app-back-1.0.jar /app/library-app-back.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/library-app-back.jar"]