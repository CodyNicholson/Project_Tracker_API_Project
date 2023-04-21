FROM eclipse-temurin:17-jdk-alpine

VOLUME /tmp

ARG JAR_FILE

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]

#ENTRYPOINT ["java","-jar","/app.jar"]

# docker build --build-arg JAR_FILE=build/libs/Project_Tracker_API_Project-0.0.1.jar -t project-tracker .
# docker run -p 9000:9000 --network=host --env-file ./docker.env project-tracker --server.port=9000

# docker run -d -p 5433:5432 --name docker-postgres -e POSTGRES_PASSWORD=postgres postgres
# docker exec -it docker-postgres bash
# psql -U postgres
# psql -h localhost -p 5432 -U postgres -W

# docker run -d -p 9000:9000 --network=host --env-file ./docker.env project-tracker --server.port=9000

# docker ps
# docker container inspect 826800376d78
# docker network ls
# docker exec -it kind_nightingale /bin/sh
# ip addr show docker0
