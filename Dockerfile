FROM openjdk:11-jre-slim
COPY build/libs/trading-demo-0.0.1-SNAPSHOT.jar /trading-demo.jar
ENTRYPOINT ["java", "-jar", "/trading-demo.jar"]