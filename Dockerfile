# 빌드 스테이지
FROM ubuntu:latest AS build
RUN apt-get update && apt-get install -y openjdk-11-jdk
WORKDIR /app
COPY . /app
RUN chmod +x ./gradlew && ./gradlew bootjar

# 실행 스테이지
FROM openjdk:11-jre-slim
COPY --from=build /app/build/libs/chess-0.1.jar /app/
ENTRYPOINT ["java", "-jar", "/app/chess-0.1.jar"]