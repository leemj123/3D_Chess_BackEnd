# 빌드 스테이지
FROM ubuntu:22.04 AS build
# ... 여기서 필요한 빌드 명령어들을 실행 ...
RUN apt-get update && apt-get install -y openjdk-11-jdk python3.10 python3.10-dev
WORKDIR /app
COPY . /app
RUN chmod +x ./gradlew && ./gradlew bootjar

# 실행 스테이지
FROM ubuntu:22.04
RUN apt-get update && apt-get install -y openjdk-11-jre-headless python3.10 python3.10-dev
WORKDIR /app
COPY --from=build /app/build/libs/chess-0.1.jar /app/
ENTRYPOINT ["/bin/sh", "-c"]
CMD ["exec java $JAVA_OPTS -jar /app/chess-0.1.jar"]