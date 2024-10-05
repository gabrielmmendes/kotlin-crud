FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

COPY . .

RUN sed -i 's/\r$//' gradlew
RUN chmod +x gradlew
RUN ./gradlew clean build -x test

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./build/libs/kotlin-crud-0.0.1-SNAPSHOT.jar"]