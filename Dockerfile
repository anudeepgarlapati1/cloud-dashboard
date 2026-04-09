FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean install

EXPOSE 8080

CMD ["java", "-jar", "target/cloud-dashboard-1.0.0.jar"]
