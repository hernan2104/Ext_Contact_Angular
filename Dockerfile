FROM amazoncorretto:17-alpine-jdk
MAINTAINER ITX
COPY target/ExtContact-0.0.1-SNAPSHOT.jar extcontact.jar
ENTRYPOINT ["java", "-jar", "/extcontact.jar"]