FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY pom.xml .
RUN ./mvnw dependency:go-offline
COPY . .
RUN ./mvnw clean package -DskipTests
ENV DATABASE_FILE=/tmp/invoices.db
CMD ["java", "-jar", "target/invoice-app-0.0.1-SNAPSHOT.jar"]