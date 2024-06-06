FROM dockerhub.timeweb.cloud/library/openjdk:17
WORKDIR /app
COPY ./build/libs/*.jar /URLShortening.jar
#<path-to-jar-file>
EXPOSE 8080
ENTRYPOINT ["java","-jar","/URLShortening.jar"]