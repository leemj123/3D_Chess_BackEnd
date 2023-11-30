FROM ubuntu:22.04
RUN apt-get update && apt-get install -y openjdk-11-jre-headless
COPY --from=build /app/build/libs/chess-0.1.jar /app/
ENTRYPOINT ["java", "${JAVA_OPTS}", "-jar", "/app/chess-0.1.jar"]