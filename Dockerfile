FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM tomcat:9.0-jdk21-temurin
COPY --from=build /app/target/JobTracker.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080