FROM openjdk:11
WORKDIR /
ADD target/*.jar /
RUN ls -ltr
EXPOSE 8080
CMD java -jar demofss-0.0.3-SNAPSHOT.jar
