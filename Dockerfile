FROM amazoncorretto:17-alpine-jdk
ADD target/users.jar users.jar
ENTRYPOINT ["java", "-jar", "users.jar"]