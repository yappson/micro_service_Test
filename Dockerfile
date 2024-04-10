FROM maven:3.9.6-amazoncorretto-17-debian AS builder
WORKDIR /app
COPY pom.xml .
COPY src/ /app/src/
RUN mvn clean install -DskipTests

FROM  eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/File-Upload-Example-0.0.1-SNAPSHOT.jar /app/File-Upload-Example-0.0.1-SNAPSHOT.jar
COPY src/main/resources/application.properties /app/application.properties
EXPOSE 9191
#CMD ["java", "-jar", "LotusDB-0.0.1-SNAPSHOT.jar"]
CMD ["java", "-Xms1024m", "-Xmx1024m", "-jar", "File-Upload-Example-0.0.1-SNAPSHOT.jar"]
#CMD ["sh", "-c", "sleep 60 && java -Xms1024m -Xmx1024m -jar File-Upload-Example-0.0.1-SNAPSHOT.jar"]
