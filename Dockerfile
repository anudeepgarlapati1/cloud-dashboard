FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean install -Dmaven.test.skip=true

EXPOSE 8080

CMD ["java", "-jar", "target/cloud-dashboard-0.0.1-SNAPSHOT.jar"]
