# Housework Hub（HwHub）Backend

> ここは **バックエンド（hw-hub-backend）リポジトリ** の README です。  
> **README 冒頭（次のセクション）には “全体像” を置き、下部に backend 特化の手順** をまとめます。

---

## 1. Backend の役割

- 認証（JWT）/ ユーザー・世帯・招待・家事・買い物などの API を提供
- MySQL を永続化層として利用し、MyBatis でアクセス
- S3（STG/本番は AWS S3）にファイルを保存（例：添付・画像等）
- OpenAPI/Swagger で API を可視化

---

## 2. 技術スタック

- Java 21（Amazon Corretto / Temurin など）
- Spring Boot 3.5.x
- MyBatis + MyBatis Generator（MBG）
- Flyway（DB マイグレーション）
- MySQL 8
- Test: Spock + JUnit Platform / JaCoCo

---

## 3. ディレクトリ構成

```
src/main/java/com/hwhub/backend
├── application/
│   └── service/           # サービス層（ビジネスロジックの実行）
├── config/                # 各種設定クラス
├── domain/                # ビジネスルール
│   ├── enums/             # コードマスタ由来の Enum（※編集禁止）
│   ├── model/             # ドメインモデル
│   ├── notification/      # 通知
│   ├── repository/        # リポジトリIF
│   └── storage/           # ストレージ操作IF
├── infrastructure/        # 外部接続実装（DB, S3等）
│   ├── mybatis/
│   │   ├── converter/     # Entity ⇔ Domain Modelの変換
│   │   ├── generated/     # MBG自動生成（※編集禁止）
│   │   │   ├── entity/    
│   │   │   └── mapper/    
│   │   ├── custom/        # 手書きEntity/Mapper（JOIN用など）
│   │   │   ├── entity/    
│   │   │   └── mapper/    
│   │   └── repository/    # リポジトリ実装
│   ├── notification/      # 通知
│   └── s3/                # AWS S3操作実装
├── presentation/          # 外部接点（API）
│   └── rest/              # Controller + DTO
├── security/              # 認証・認可関連
├── tool/                  # 開発支援（EnumGenerator等）
└── validation/            # 独自バリデーション
```

---

## 4. ローカル開発

### 4.1 前提

- JDK 21
- Docker / Docker Compose
- MySQL（基本は docker compose で起動）
- LocalStack（S3をローカルで擬似利用する場合に使用）
- Mailhog（メールをローカルで擬似利用する場合に使用）

### 4.2 起動

```bash
# DB 起動（hw-hub-databaseリポジトリ側で実行してください）
docker compose up -d

# LocalStack, Mailhog 起動（当リポジトリで実行してください）
docker compose up -d

# アプリ起動（IDE からでも OK）
./gradlew bootRun
```

---

## 5. 開発コマンド

### 5.1 ビルド

```bash
./gradlew clean build
```

### 5.2 テスト

```bash
./gradlew test
```

### 5.3 フォーマット/静的チェック（プロジェクト設定に合わせて）

```bash
./gradlew spotlessCheck
# あるいは
./gradlew spotlessApply
```

---

## 6. カバレッジ（JaCoCo）と成果物

### 6.1 レポート生成

```bash
./gradlew test jacocoTestReport
```

### 6.2 出力先

- JaCoCo HTML: `build/reports/jacoco/test/html/index.html`
- テストレポート: `build/reports/tests/test/index.html`

### 6.3 GitHub Pages（CI）

- main へ push / 手動実行で Pages に公開（workflow: `coverage-backend`）

---

## 7. Swagger / OpenAPI

- 起動後、Swagger UI にアクセス
    - `http://localhost:8080/swagger-ui/index.html`

---

## 8. DB マイグレーション（Flyway）

hw-hub-databaseリポジトリ側で実施してください。

- マイグレーション配置: `src/main/resources/db/migration`
- 命名規約例: `V00_000_001__create_xxx.sql`

```bash
# アプリ起動時に自動適用される想定（設定による）
./gradlew bootRun
```

---

## 9. コード生成の運用（重要）

### 9.1 コードマスタ（m_code）→ Enum 自動生成

**DB のコードマスタ `m_code` を追加/更新したら、Enum を再生成します。**  
Gradle タスク `generateEnums` を実行すると、`com.hwhub.backend.domain.enums` 配下が更新されます。

```bash
# Enum生成
./gradlew generateEnums

# コード整形
./gradlew spotlessApply
```

- 変更が入った `domain/enums` をコミットして反映します。
- **m_code の変更は “アプリの定数（Enum）” に直結する**ため、DB 側の変更後に必ず実行してください。

### 9.2 DB 定義変更 → MyBatis Generator（MBG）再実行

**テーブル定義やカラムを変更した場合は MBG の再実行が必要です。**  
テーブル追加をした場合は、`src/main/resources/generator/generatorConfig.xml`のtableタグを更新してください。tableタグの直前にコメントアウトされているSQLを発行した結果を張り付ければOKです。
生成物は `infrastructure/mybatis/generated/*` に出力されます。

実行コマンドはプロジェクトの Gradle タスク名に依存するため、まずタスク名を確認します：

```bash
# タスク一覧から mybatis / mbg を探す（Windows の例）
./gradlew tasks | findstr /i mybatis
./gradlew tasks | findstr /i mbg
```

```bash
./gradlew mybatisGenerator
```

- `generated/*` は **手動で編集しない**（再生成で上書きされます）
- JOIN などで生成物だけでは足りない場合は `custom/*` に追加します

---

## 10. Google OAuth

### 10.1. Google OAuth: ログイン
GoogleアカウントでHwHubにログインする際に利用するAPI群。

| メソッド | パス | 説明 |
| --- | --- | --- |
| GET | /oauth/google/start | Google OAuth開始。stateを生成し、Cookieに保存後、Googleの認証画面にリダイレクトする。 |
| GET | /oauth/google/callback | Google OAuthコールバック。stateを検証後、Googleからアクセストークンを取得し、HwHubのJWTを生成して返す。 |

### 10.2. Google Link: アカウントの連携
ログイン中のHwHubアカウントにGoogleアカウントを連携する際に利用するAPI群。

| メソッド | パス | 説明 |
| --- | --- | --- |
| GET | /api/users/me/google/link/start | Google Link開始。stateを生成し、Cookieに保存後、Googleの認証画面にリダイレクトする。 |
| GET | /api/users/me/google/link/callback | Google Linkコールバック。stateを検証後、Googleからアクセストークンを取得し、HwHubのJWTを生成して返す。 |

### 10.3. 動作確認
開発環境でGoogleアカウント連携の動作確認を行う場合は、プロジェクトルート直下に.envファイルを作成すること。
.envファイルには以下の3つの環境変数を定義すること。設定する値は管理者に確認すること。

- GOOGLE_CLIENT_ID
- GOOGLE_CLIENT_SECRET
- HWHUB_JWT_SECRET

```text
GOOGLE_OAUTH_CLIENT_ID=xxxxx-xxxxxxxxx.apps.googleusercontent.com
GOOGLE_OAUTH_CLIENT_SECRET=xxxxxxxxxxxxxxxx
HWHUB_OAUTH_STATE_SECRET=xxxxxxxxxxxxxxxx
```
---

## 11. よくあるトラブルシュート

- 403 / CORS / JWT 周り：`security/`, `config/` を確認
- DB 接続：`SPRING_DATASOURCE_*` の環境変数/Secrets を確認
- ECS/ALB ヘルスチェック：`/actuator/health`（設定に依存）

---

## 環境変数

実際の値は STG/本番では Secrets Manager 等から供給します。

- `SPRING_PROFILES_ACTIVE`（例：`stg` / `prod`）
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`（Secret）
- `HWHUB_JWT_SECRET`（Secret）
- `HWHUB_OBJECT_STORAGE_BUCKET`
- `SES_SMTP_HOST`
- `SES_SMTP_USER`
- `SES_SMTP_PASSWORD`
- `GOOGLE_CLIENT_ID`
- `GOOGLE_CLIENT_SECRET`（Secret）

