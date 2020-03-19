FROM openjdk:11

VOLUME /tmp
ADD maven/iris-service-0.0.1-SNAPSHOT.jar iris-service-0.0.1-SNAPSHOT.jar
RUN sh -c 'touch /iris-service-0.0.1-SNAPSHOT'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/iris-service-0.0.1-SNAPSHOT.jar"]