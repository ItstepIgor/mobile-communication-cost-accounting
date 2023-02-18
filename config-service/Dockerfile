FROM gradle:jdk19 as builder
COPY . /home/gradle
RUN gradle clean bootJar

#FROM adoptopenjdk:11-jre-hotspot
FROM openjdk:19
COPY --from=builder /home/gradle/build/libs/*.jar ./config-service.jar
CMD java -jar config-service.jar