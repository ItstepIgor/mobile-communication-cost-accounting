FROM gradle:jdk19 as builder
COPY . /home/gradle
RUN gradle clean bootJar

#FROM adoptopenjdk:11-jre-hotspot
FROM openjdk:19
COPY --from=builder /home/gradle/build/libs/*.jar ./calculate-service.jar
COPY ./entrypoint.sh /usr/bin/entrypoint.sh
ENTRYPOINT [ "entrypoint.sh", "calculate-service.jar" ]