FROM openjdk:11-jdk AS build
WORKDIR /app
COPY . /app
RUN chmod +x ./gradlew && ./gradlew bootjar
ENTRYPOINT ["sh","-c", "java ${JAVA_OPTS} -jar /app/build/libs/chess-0.1.jar"]