FROM openjdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
VOLUME ./main-app
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]