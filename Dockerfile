FROM maven:3.6.3 AS maven

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn clean install

# For Java 11,
FROM openjdk:11-jdk

ARG JAR_FILE=salesstats-aykuty-1.0.jar

WORKDIR /opt/app

# Copy the jar from the maven stage to the /opt/app directory of the current stage.
COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/
EXPOSE 8080
ENTRYPOINT ["java","-jar","salesstats-aykuty-1.0.jar"]