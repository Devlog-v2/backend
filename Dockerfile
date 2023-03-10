# jar 파일을 docker hub에 이미지화 시켜 올리기 위한 docker file
FROM openjdk:11
ARG JAR_FILE=build/libs/hexagonal-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]