# syntax=docker/dockerfile:1

################################################################################
# Dependency Stage
FROM eclipse-temurin:17-jdk-jammy as deps
WORKDIR /build

COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/

RUN --mount=type=bind,source=./pom.xml,target=/build/pom.xml \
    --mount=type=cache,target=/root/.m2,id=maven_cache \
    ./mvnw dependency:go-offline -DskipTests

################################################################################
# Build Stage
FROM deps as package
WORKDIR /build
COPY ./src src/

RUN --mount=type=bind,source=./pom.xml,target=/build/pom.xml \
    --mount=type=cache,target=/root/.m2,id=maven_cache \
    ./mvnw package -DskipTests && \
    mv target/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar

################################################################################
# Extraction Stage
FROM package as extract
WORKDIR /build
RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted

################################################################################
# Final Runtime Stage
FROM eclipse-temurin:17-jre-jammy AS final

ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser

WORKDIR /app
COPY --from=extract /build/target/extracted/dependencies/ ./
COPY --from=extract /build/target/extracted/spring-boot-loader/ ./
COPY --from=extract /build/target/extracted/snapshot-dependencies/ ./
COPY --from=extract /build/target/extracted/application/ ./

EXPOSE 8080

CMD ["java", "org.springframework.boot.loader.launch.JarLauncher"]
