# ---------- Build stage ----------
FROM maven:3.9.12-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests


# ---------- Runtime stage ----------
FROM tomcat:10.1-jdk17-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/target/EmployeeManagement.war /usr/local/tomcat/webapps/EmployeeManagement.war

EXPOSE 8080

CMD ["catalina.sh", "run"]