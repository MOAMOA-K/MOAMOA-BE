FROM openjdk:17-jdk

# 나중에 버전 하드코딩을 방지하려면 *.jar로 변경하는 게 나을 듯
# -plain.jar 생성 방지하는 코드도 추가 필요함.
ARG JAR_FILE=build/libs/BE-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]