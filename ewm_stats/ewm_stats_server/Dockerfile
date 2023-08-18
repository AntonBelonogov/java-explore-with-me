FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar ewm_stats_server.jar
ENTRYPOINT ["java","-jar","/ewm_stats_server.jar"]