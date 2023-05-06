FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine
LABEL "email"="elias.dominguezpires@gmail.com"
USER root
COPY target/demo-spring*.jar app.jar
ADD resources /
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "-Xms64m", "-Xmx900m","/app.jar"]