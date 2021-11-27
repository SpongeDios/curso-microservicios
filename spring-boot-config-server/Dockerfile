FROM openjdk:12
VOLUME /tmp
#VOLUME es opcional, no sirve de mucho
EXPOSE 8888
ADD ./target/spring-boot-config-server-0.0.1-SNAPSHOT.jar config-server.jar
ENTRYPOINT ["java", "-jar", "/config-server.jar"]
