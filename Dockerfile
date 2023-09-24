FROM openjdk:11-jdk AS build
WORKDIR /app
COPY . /app
RUN chmod +x ./gradlew && ./gradlew bootjar
COPY /build/libs/chess-0.1.jar /app/ChessService.jar
ENTRYPOINT ["sh","-c", "java ${JAVA_OPTS} -jar /app/ChessService.jar"]