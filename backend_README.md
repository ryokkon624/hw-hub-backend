# Housework Hub（HwHub）Backend

> ここは **バックエンド（hw-hub-backend）リポジトリ** の README です。  
> **README 冒頭（次のセクション）には "全体像" を置き、下部に backend 特化の手順** をまとめます。

---

## 1. Backend の役割

- 認証（JWT）/ ユーザー・世帯・招待・家事・買い物などの API を提供
- MySQL を永続化層として利用し、MyBatis でアクセス
- S3（STG/本番は AWS S3）にファイルを保存（例：添付・画像等）
- OpenAPI/Swagger で API を可視化
- 問い合わせへのスタッフ返信・管理機能（ロール・ユーザー・問い合わせ管理）を提供

---

## 2. 技術スタック

- Java 21（Amazon Corretto / Temurin など）
- Spring Boot 4.0.x
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
│   ├── oauth/             # OAuth
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
│   ├── oauth/             # OAuth
│   └── s3/                # AWS S3操作実装
├── presentation/          # 外部接点（API）
│   └── rest/              # Controller + DTO
├── security/              # 認証・認可関連（JWT / AOP パーミッションチェック）
├── tool/                  # 開発支援（EnumGenerator等）
└── validation/            # 独自バリデーション
```

---

## 4. アーキテクチャ方針

### 4.1 レイヤー構成と依存方向

DDDライクな4層構造を採用(DDDの考え方を参考にしたレイヤードアーキテクチャ。Domain Events、CQRSは不採用)し、**上位レイヤーが下位レイヤーに依存する** 単方向の依存を守ります。

```
Presentation層  →  Application層  →  Domain層
                                          ↑
                    Infrastructure層  ─────┘
```

### 4.2 オブジェクト種別とレイヤーごとの扱い

各オブジェクトが「どのレイヤーで扱えるか（依存してよいか）」を以下の表に示します。

| レイヤー | オブジェクト種別 | Presentation | Application | Domain | Infrastructure | メモ |
|---|---|:---:|:---:|:---:|:---:|---|
| Presentation | request/response DTO | ○ | × | × | × | class / record どちらでも可 |
| Application | Presentation層への戻り値DTO | ○ | ○ | × | × | Service の Inner Class として record で実装 |
| Domain | Model | ○ | ○ | ○ | ○ | 業務的な単位・業務処理あり |
| Domain | 参照系Model | ○ | ○ | ○ | ○ | record で実装 |
| Domain | 検索条件VO | ○ | ○ | ○ | ○ | record で実装。ユースケース固有のものと考えるとApplication層にあるべきとも考えられる。しかし、Repository Interface の引数として渡すため、Domain層に置く方針とする。 |
| Infrastructure | generated entity | × | × | × | ○ | MBG生成テーブルと1対1 |
| Infrastructure | custom entity | × | × | × | ○ | JOINの結果を受け取るための手書きEntity |

**ポイント**

- `generated entity` と `custom entity` は Infrastructure 層に閉じる。Service / Controller には渡さない。
- Domain Model は全層から参照できるが、`Infrastructure → Domain` の依存（Converter での変換）は許容する。
- Presentation 層の DTO は Controller 内で完結させ、Service には渡さない。

### 4.3 パーミッションチェック（AOP）

管理系エンドポイントは `@RequiresPermission` アノテーションで宣言的に権限を制御します。

```java
// Service メソッドに付与する例
@RequiresPermission(Permission.INQUIRY_REPLY)
public List<AdminInquiryRow> findPendingStaff() { ... }
```

AOP（`RequiresPermissionAspect`）が SecurityContext の userId からロールを取得し、
`m_role_permission` テーブルのマッピングと照合して 403 を返します。

---

## 5. ローカル開発

### 5.1 前提

- JDK 21
- Docker / Docker Compose
- MySQL（基本は docker compose で起動）
- LocalStack（S3をローカルで擬似利用する場合に使用）
- Mailhog（メールをローカルで擬似利用する場合に使用）

### 5.2 起動

```bash
# DB 起動（hw-hub-databaseリポジトリ側で実行してください）
docker compose up -d

# LocalStack, Mailhog 起動（当リポジトリで実行してください）
docker compose up -d

# アプリ起動（IDE からでも OK）
./gradlew bootRun
```

---

## 6. 開発コマンド

### 6.1 ビルド

```bash
./gradlew clean build
```

### 6.2 テスト

```bash
# UT（単体テスト）
./gradlew test
```

統合テスト（PR時にCIで実行、ローカルでも実行可能）：

```bash
./gradlew integrationTest
```

全テスト実行（UT＋統合テスト）：

```bash
./gradlew clean test integrationTest
```

**統合テストについて：**
- Testcontainers を使用してMySQLコンテナを自動起動
- Flywayマイグレーションが自動適用される
- PRのCIパイプラインで自動実行される

### 6.3 フォーマット/静的チェック

```bash
./gradlew spotlessCheck
# あるいは
./gradlew spotlessApply
```

---

## 7. カバレッジ（JaCoCo）と成果物

### 7.1 レポート生成

```bash
./gradlew test jacocoTestReport
```

### 7.2 出力先

- JaCoCo HTML: `build/reports/jacoco/test/html/index.html`
- テストレポート: `build/reports/tests/test/index.html`

### 7.3 GitHub Pages（CI）

- main へ push / 手動実行で Pages に公開（workflow: `coverage-backend`）

---

## 8. Swagger / OpenAPI

- 起動後、Swagger UI にアクセス
    - `http://localhost:8080/swagger-ui/index.html`

---

## 9. DB マイグレーション（Flyway）

hw-hub-databaseリポジトリ側で実施してください。

- マイグレーション配置: `src/main/resources/db/migration`
- 命名規約例: `V00_000_001__create_xxx.sql`

```bash
# アプリ起動時に自動適用される想定（設定による）
./gradlew bootRun
```

---

## 10. コード生成の運用（重要）

### 10.1 コードマスタ（m_code）→ Enum 自動生成

**DB のコードマスタ `m_code` を追加/更新したら、Enum を再生成します。**  
Gradle タスク `generateEnums` を実行すると、`com.hwhub.backend.domain.enums` 配下が更新されます。

```bash
# Enum生成
./gradlew generateEnums

# コード整形
./gradlew spotlessApply
```

- 変更が入った `domain/enums` をコミットして反映します。
- **m_code の変更は "アプリの定数（Enum）" に直結する**ため、DB 側の変更後に必ず実行してください。

### 10.2 DB 定義変更 → MyBatis Generator（MBG）再実行

**テーブル定義やカラムを変更した場合は MBG の再実行が必要です。**  
テーブル追加をした場合は、`src/main/resources/generator/generatorConfig.xml` の tableタグを更新してください。
tableタグ直前にコメントアウトされたSQLを実行し結果を貼り付けることで更新可能です。

```bash
./gradlew mybatisGenerator
```

- `generated/*` は **手動で編集しない**（再生成で上書きされます）
- JOIN などで生成物だけでは足りない場合は `custom/*` に追加します

---

## 11. Google OAuth

### 11.1. Google OAuth: ログイン

| メソッド | パス | 説明 |
| --- | --- | --- |
| GET | /oauth/google/start | Google OAuth開始。stateを生成し、Cookieに保存後、Googleの認証画面にリダイレクトする。 |
| GET | /oauth/google/callback | Google OAuthコールバック。stateを検証後、Googleからアクセストークンを取得し、HwHubのJWTを生成して返す。 |

### 11.2. Google Link: アカウントの連携

| メソッド | パス | 説明 |
| --- | --- | --- |
| GET | /api/users/me/google/link/start | Google Link開始。stateを生成し、Cookieに保存後、Googleの認証画面にリダイレクトする。 |
| GET | /api/users/me/google/link/callback | Google Linkコールバック。stateを検証後、Googleからアクセストークンを取得し、HwHubのJWTを生成して返す。 |

### 11.3. 動作確認

開発環境でGoogleアカウント連携の動作確認を行う場合は、プロジェクトルート直下に `.env` ファイルを作成すること。
`.env.sample`をコピーして利用すること。

```text
GOOGLE_OAUTH_CLIENT_ID=xxxxx-xxxxxxxxx.apps.googleusercontent.com
GOOGLE_OAUTH_CLIENT_SECRET=xxxxxxxxxxxxxxxx
HWHUB_OAUTH_STATE_SECRET=xxxxxxxxxxxxxxxx
```

---

## 12. よくあるトラブルシュート

- 403 / CORS / JWT 周り：`security/`, `config/` を確認
- DB 接続：`SPRING_DATASOURCE_*` の環境変数/Secrets を確認
- ECS/ALB ヘルスチェック：`/actuator/health`（設定に依存）
- `@RequiresPermission` が効かない：`build.gradle` に `aspectjweaver` があるか確認

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
- `SES_SMTP_USER`（Secret）
- `SES_SMTP_PASSWORD`（Secret）
- `GOOGLE_CLIENT_ID`（Secret）
- `GOOGLE_CLIENT_SECRET`（Secret）
