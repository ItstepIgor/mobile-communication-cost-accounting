FROM gradle:jdk19 AS builder
COPY . /home/gradle
RUN gradle clean bootJar

#FROM adoptopenjdk:11-jre-hotspot
FROM openjdk:19
COPY --from=builder /home/gradle/build/libs/*.jar ./import-service.jar
COPY ./entrypoint.sh /usr/bin/entrypoint.sh
RUN chmod +x  /usr/bin/entrypoint.sh
ENTRYPOINT [ "entrypoint.sh", "import-service.jar" ]