FROM openjdk:17-alpine

WORKDIR app

COPY . .
RUN apk add --no-cache maven
RUN mvn install

CMD mvn spring-boot:run

EXPOSE 8080

CMD ["java", "-jar", "target/Vmmanagement-0.0.1-SNAPSHOT.jar"]