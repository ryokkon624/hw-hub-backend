# THREAT_MODEL — hw-hub-backend

Housework Hub バックエンド（Java 21 / Spring Boot 4 / MyBatis / MySQL）の脅威モデル。
LLM ベースのセキュリティスキャン（`claude-code-security-review` / defending-code-reference-harness）および
人手のレビューが、**何を守り・何を信頼し・何を対象外とするか**を共有するための土台ドキュメント。

> 本書は Anthropic「Using LLMs to secure source code」の Find-and-Fix ループ Step 1（Threat Modeling）に対応する。
> スキャン結果のトリアージ時は、本書の「信頼する入力」「スコープ外」を参照し、重大度の過大評価を避けること。

- 対象リポジトリ: `hw-hub-backend`
- 種別: 認証ありの REST API サーバー（**認可・入力検証の最終的な強制点**）
- 最終更新: 2026-06-23
- ステータス: ドラフト（Phase 0。実装変更ではなくレビュー用の合意文書）

---

## 1. システムコンテキスト

HwHub の中核 API。フロント/モバイルからのリクエストを認証・認可し、世帯・家事・買い物・問い合わせを処理する。

| 項目         | 内容                                                                  |
| ------------ | --------------------------------------------------------------------- |
| 実行環境     | AWS ECS Fargate（信頼境界の内側）                                      |
| 主要技術     | Java 21 / Spring Boot 4 / Spring Security / MyBatis / Flyway / MySQL 8 |
| 認証         | JWT（Bearer, HS256）。`HWHUB_JWT_SECRET` で署名、有効期限 4h           |
| 認可         | `@RequiresPermission` AOP（ロール×権限）＋ 世帯メンバーシップ検証      |
| 外部連携     | S3（presigned URL）/ Google OAuth / SES（メール）                      |
| 認証情報供給 | 環境変数 ＋ Secrets Manager（本番）。実 `.env` はリポジトリ外          |

---

## 2. 守るべき資産（Assets）

| 資産                          | 説明                                                  | 影響度 |
| ----------------------------- | ----------------------------------------------------- | ------ |
| JWT 署名鍵 `HWHUB_JWT_SECRET` | 漏洩＝任意ユーザーになりすまし可能                    | 高     |
| パスワードハッシュ（BCrypt）  | `m_user`。流出時の再利用攻撃                           | 高     |
| ユーザー個人情報 / 世帯データ | email・displayName・世帯メンバーシップ・所有権        | 高     |
| 招待トークン                  | 未認証 GET で参照可能。推測されると世帯参加に悪用      | 中     |
| 管理者権限                    | テンプレ・ロール・ユーザー・問い合わせ管理            | 高     |
| OAuth / SES / DB 認証情報     | Google OAuth secret・SMTP・DB パスワード              | 高     |
| アップロード資産（S3）        | アイコン・買い物添付（presigned URL）                 | 中     |

---

## 3. エントリポイント / 攻撃面（Entry points）

23 個の REST コントローラ。とくに**未認証で到達可能な公開エンドポイント**が攻撃面の中心。

| #   | エントリポイント                                  | 信頼できない入力           |
| --- | ------------------------------------------------- | -------------------------- |
| E1  | 認証系 `/api/auth/*`（login/register/refresh）    | email・password・トークン  |
| E2  | メール認証・パスワードリセット（token ベース）    | URL 内トークン             |
| E3  | Google OAuth `/oauth/google/*`                    | idToken・state・code       |
| E4  | 招待参照 `GET /api/household-invitations/{token}` | 未認証で叩ける token        |
| E5  | 認証済み API（世帯/家事/買い物/問い合わせ）       | DTO・PathVariable・Query   |
| E6  | ファイルアップロード URL 発行（presigned）        | fileName・mimeType         |
| E7  | npm/Gradle 依存パッケージ                         | サプライチェーン           |

---

## 4. 信頼境界（Trust boundaries）

```
[フロント/モバイル:信頼できない] ──TLS+JWT──> [このサーバー:認可の砦] ──> [MySQL / S3]
                                                    │
                                                    ├─ Spring Security / JwtAuthenticationFilter（認証）
                                                    ├─ @RequiresPermission ＋ 世帯メンバーシップ検証（認可）
                                                    └─ Google OAuth ──> [Google] / SES ──> [メール]
```

**最重要原則: このサーバーが認可・入力検証の最終強制点である。**
クライアントから来る JWT・DTO・パラメータはすべて検証対象。フロントのルートガード等は信頼しない。

---

## 5. 想定する脅威（What can go wrong?）

| ID  | 脅威                                                                                  | 関連                                       | 重大度 |
| --- | ------------------------------------------------------------------------------------- | ------------------------------------------ | ------ |
| T1  | **認可漏れ（IDOR/世帯越境）**: 他世帯/他ユーザーのリソースを ID 指定で操作            | 各 Controller / HouseholdAuthorizationService | 高     |
| T2  | **公開エンドポイントの悪用**: login のブルートフォース、register のユーザー列挙       | AuthController                              | 中     |
| T3  | **トークンの推測可能性**: 招待・メール認証・パスワードリセットの token の entropy 不足 | Invitation / PasswordReset                 | 中     |
| T4  | **JWT 失効のタイムスキュー**: `passwordChangedAt` と `iat` の秒比較・TZ 依存          | JwtAuthenticationFilter                     | 中     |
| T5  | **SQL インジェクション**: MyBatis で `${}` をユーザー入力に使う混入                   | mapper（現状は `#{}` 徹底で低リスク）       | 中     |
| T6  | **presigned URL の悪用**: TTL/対象/所有者検証、content-type 偽装                       | ShoppingItemAttachmentService / UserIconService | 中     |
| T7  | **管理者権限の付与・初期管理者の扱い**                                                 | Admin*Controller / @RequiresPermission      | 中     |
| T8  | **例外・ログからの情報漏洩**: スタックトレース/機微値のレスポンス・ログ出力           | GlobalExceptionHandler                      | 低〜中 |
| T9  | **依存パッケージの既知脆弱性**: 推移的依存を含むサプライチェーン                       | build.gradle                               | 中     |

---

## 6. 現状の対策（既存コントロール）

- **認証**: `JwtAuthenticationFilter`（Bearer 検証・失効判定）、stateless セッション、パスワード変更時のトークン失効。
- **認可**: `@RequiresPermission` AOP（User→Role→Permission の 3 段）、`HouseholdAuthorizationService.assertUserBelongsToHousehold` をほぼ全リソースで実施。
- **入力検証**: Bean Validation（`@Valid`/`@Email`/`@NotBlank`）＋ カスタム（`@ByteSize`/`@EnumValue`）、`GlobalExceptionHandler` で 400 集約。
- **パスワード**: BCrypt、リセットトークンの TTL・再送クールダウン・回数上限。
- **通信境界**: CORS は環境ごとに許可元を限定、CSRF 無効（stateless+JWT で代替）、OAuth state は HMAC 署名＋TTL。
- **SQL**: MyBatis はパラメータ化（`#{}`）徹底、カスタムマッパーに `${}` 無し。
- **シークレット**: 実 `.env` はリポジトリ外（gitignore）。コミットされているのは `.env.example`（プレースホルダ）のみ＝**漏洩なしを git 履歴で確認済み**。本番は Secrets Manager。

---

## 7. 信頼する入力（Trusted inputs）— スキャン時の過大評価を防ぐ

- **検証済み JWT**（署名・有効期限・失効チェック後）。
- **Spring Security / `@RequiresPermission` の認可結果**。
- **MyBatis のパラメータ化クエリ**（`#{}`）。
- **BCrypt ハッシュ**・**S3 presigned URL（AWS 署名）**。
- **Secrets Manager / 環境変数で供給される秘密**（コード内ハードコードではない）。

---

## 8. スコープ外（Out of scope）

- フロントエンドの XSS / CSP → `hw-hub-frontend`
- バッチ・AI（Claude API）処理・ナレッジ同期 → `hw-hub-batch`
- インフラ・IAM・Secrets Manager・VPC・ALB・WAF → `hw-hub-infra`
- DB スキーマ・データ保護 → `hw-hub-database`
- 物理セキュリティ・ソーシャルエンジニアリング

> 補償的統制（WAF・ALB・ネットワーク制御）はソースに現れないため、サーバー単体スキャンでは検証できない。それを根拠に「未対策」と断ずる指摘は誤検知として扱う。

---

## 9. レビュー観点チェック（Did we do a good job?）

1. すべてのリソース操作に世帯/ロール認可が網羅されているか（IDOR 不在）
2. 招待・メール認証・リセットの token は十分なランダム性・短い TTL を持つか
3. 公開エンドポイントにレート制限・ユーザー列挙対策があるか
4. MyBatis に `${}` でユーザー入力を組む箇所が混入していないか
5. 例外レスポンス・ログに機微情報（秘密・PII・スタックトレース）が出ていないか
6. 依存パッケージに既知の高/重大脆弱性がないか（Phase 2 の深掘りスキャン対象）

---

## 10. 更新運用

- 認証・認可・外部連携・新規エンドポイント追加など**信頼境界に関わる変更時**に本書を更新する。
- スキャン結果のトリアージで前提が誤っていた場合、本書を正とせず修正する。
- Retro でセキュリティ指摘を棚卸しする際、本書の見直し要否を確認する。
