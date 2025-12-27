# ---------- build stage ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Gradle wrapper
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
RUN chmod +x ./gradlew

# 依存関係キャッシュ
RUN ./gradlew --no-daemon dependencies || true

# ソース投入 & ビルド
COPY src ./src
RUN ./gradlew --no-daemon clean bootJar

# ---------- runtime stage ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# 非rootユーザー
RUN useradd -m appuser
USER appuser

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=50.0"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
