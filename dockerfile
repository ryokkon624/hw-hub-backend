# バージョン情報（CI/CDから --build-arg APP_VERSION=v1.x.x で注入）
# ローカル開発時は "local" がデフォルト値
ARG APP_VERSION=local

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
# マルチステージビルドではARGのスコープがステージ毎にリセットされるため再宣言が必要
ARG APP_VERSION=local
ENV APP_VERSION=${APP_VERSION}

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
