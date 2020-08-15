FROM openjdk:11-jre-slim
COPY /target/report-analyzer-1.0.jar /app/report-analyzer.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/report-analyzer.jar"]