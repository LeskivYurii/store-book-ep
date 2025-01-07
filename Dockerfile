FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/book-store-service-0.0.1-SNAPSHOT.jar book-store-service-0.0.1.jar
ENTRYPOINT ["java","-jar","/book-store-service-0.0.1.jar", "--spring.profiles.active=dev"]