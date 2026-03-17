-- ------------------------------------------------------------
-- V00_000_001__create_tables.sql
--   HouseworkHub: テーブル定義一式
-- ------------------------------------------------------------
/* ============================================================
   m_user : ユーザマスタ
   ============================================================ */
CREATE TABLE m_user(
                       user_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ユーザID'
    , email VARCHAR (255) NULL COMMENT 'メールアドレス（ログイン用）'
    , password_hash VARCHAR (255) NULL COMMENT 'パスワードハッシュ（認証用）'
    , auth_provider VARCHAR (50) NULL COMMENT '外部認証プロバイダ（LOCAL / GOOGLE / APPLE など）'
    , auth_provider_id VARCHAR (255) NULL COMMENT '外部認証ID（subなど外部一意ID）'
    , display_name VARCHAR (100) NOT NULL COMMENT '表示名'
    , profile_image_key VARCHAR (255) NULL COMMENT 'プロフィール画像ストレージキー（S3など）'
    , locale VARCHAR (5) NOT NULL DEFAULT 'ja' COMMENT '利用言語'
    , is_active TINYINT(1) NOT NULL DEFAULT 1 COMMENT '活性フラグ（1=アクティブ）'
    , create_user_id BIGINT UNSIGNED NOT NULL COMMENT '作成者ユーザID'
    , create_program VARCHAR (10) NOT NULL COMMENT '作成プログラム:m_code：0012'
    , created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '作成日時'
    , update_user_id BIGINT UNSIGNED NOT NULL COMMENT '更新者ユーザID'
    , update_program VARCHAR (10) NOT NULL COMMENT '更新プログラム:m_code：0012'
    , updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6)
                           ON UPDATE CURRENT_TIMESTAMP (6) COMMENT '更新日時'
    , PRIMARY KEY (user_id)
    , UNIQUE KEY uq_user_email(email)
    , UNIQUE KEY uq_user_auth_provider(auth_provider, auth_provider_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = 'ユーザマスタ';

/* ============================================================
   m_household : 世帯マスタ
   ============================================================ */
CREATE TABLE m_household(
                            household_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '世帯ID'
    , name VARCHAR (100) NOT NULL COMMENT '世帯名'
    , owner_user_id BIGINT UNSIGNED NOT NULL COMMENT '世帯所有者:m_user.user_id'
    , create_user_id BIGINT UNSIGNED NOT NULL COMMENT '作成者ユーザID'
    , create_program VARCHAR (10) NOT NULL COMMENT '作成プログラム:m_code：0012'
    , created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '作成日時'
    , update_user_id BIGINT UNSIGNED NOT NULL COMMENT '更新者ユーザID'
    , update_program VARCHAR (10) NOT NULL COMMENT '更新プログラム:m_code：0012'
    , updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6)
                                ON UPDATE CURRENT_TIMESTAMP (6) COMMENT '更新日時'
    , PRIMARY KEY (household_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '世帯マスタ';

/* ============================================================
   m_household_member : 世帯メンバーマスタ
   ============================================================ */
CREATE TABLE m_household_member(
                                   household_id BIGINT UNSIGNED NOT NULL COMMENT '世帯ID:m_household.household_id'
    , user_id BIGINT UNSIGNED NOT NULL COMMENT 'ユーザID:m_user.user_id'
    , nickname VARCHAR (100) NOT NULL COMMENT '世帯内でのニックネーム'
    , status VARCHAR (10) NOT NULL DEFAULT '1' COMMENT 'ステータス:m_code：0007 code_value'
    , create_user_id BIGINT UNSIGNED NOT NULL COMMENT '作成者ユーザID'
    , create_program VARCHAR (10) NOT NULL COMMENT '作成プログラム:m_code：0012'
    , created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '作成日時'
    , update_user_id BIGINT UNSIGNED NOT NULL COMMENT '更新者ユーザID'
    , update_program VARCHAR (10) NOT NULL COMMENT '更新プログラム:m_code：0012'
    , updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6)
                                       ON UPDATE CURRENT_TIMESTAMP (6) COMMENT '更新日時'
    , PRIMARY KEY (household_id, user_id)
    , KEY idx_household_member_user(user_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '世帯メンバーマスタ';

/* ============================================================
   m_code : コードマスタ
   ============================================================ */
CREATE TABLE m_code(
                       code_type VARCHAR (4) NOT NULL COMMENT 'コード種別'
    , code_type_name VARCHAR (100) NOT NULL COMMENT 'コード種別名'
    , code_type_name_en VARCHAR (100) NOT NULL COMMENT 'コード種別名（英語）'
    , code_value VARCHAR (10) NOT NULL COMMENT 'コード値'
    , name VARCHAR (100) NOT NULL COMMENT '名称'
    , display_name_ja VARCHAR (100) NULL COMMENT '表示名（日本語）'
    , display_name_en VARCHAR (100) NULL COMMENT '表示名（英語）'
    , display_name_es VARCHAR (100) NULL COMMENT '表示名（スペイン語）'
    , remarks VARCHAR (255) NULL COMMENT '備考'
    , display_order VARCHAR (5) NULL COMMENT '表示順'
    , create_user_id BIGINT UNSIGNED NOT NULL COMMENT '作成者ユーザID'
    , create_program VARCHAR (10) NOT NULL COMMENT '作成プログラム:m_code：0012'
    , created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '作成日時'
    , update_user_id BIGINT UNSIGNED NOT NULL COMMENT '更新者ユーザID'
    , update_program VARCHAR (10) NOT NULL COMMENT '更新プログラム:m_code：0012'
    , updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6)
                           ON UPDATE CURRENT_TIMESTAMP (6) COMMENT '更新日時'
    , PRIMARY KEY (code_type, code_value)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = 'コードマスタ';

/* ============================================================
   t_household_invitation : 世帯招待
   ============================================================ */
CREATE TABLE t_household_invitation(
                                       household_id BIGINT UNSIGNED NOT NULL COMMENT '世帯ID:m_household.household_id'
    , inviter_user_id BIGINT UNSIGNED NOT NULL COMMENT '招待者ユーザID:m_user.user_id'
    , invited_email VARCHAR (255) NOT NULL COMMENT '招待先メールアドレス'
    , invitation_token VARCHAR (255) NOT NULL COMMENT '招待トークン:UUID'
    , status VARCHAR (10) NOT NULL DEFAULT '0' COMMENT 'ステータス:m_code：0008 code_value'
    , expires_at DATETIME(6) NOT NULL COMMENT '招待の有効期限'
    , accepted_user_id BIGINT UNSIGNED NULL COMMENT '招待を受けたユーザID:m_user.user_id'
    , create_user_id BIGINT UNSIGNED NOT NULL COMMENT '作成者ユーザID'
    , create_program VARCHAR (10) NOT NULL COMMENT '作成プログラム:m_code：0012'
    , created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '作成日時'
    , update_user_id BIGINT UNSIGNED NOT NULL COMMENT '更新者ユーザID'
    , update_program VARCHAR (10) NOT NULL COMMENT '更新プログラム:m_code：0012'
    , updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6)
                                           ON UPDATE CURRENT_TIMESTAMP (6) COMMENT '更新日時'
    , PRIMARY KEY (invitation_token)
    , KEY idx_invitation_household(household_id)
    , KEY idx_invitation_invited_email(invited_email)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '世帯招待テーブル';

/* ============================================================
   m_housework : 家事マスタ
   ============================================================ */
CREATE TABLE m_housework(
                            housework_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '家事ID'
    , household_id BIGINT UNSIGNED NOT NULL COMMENT '世帯ID'
    , name VARCHAR (100) NOT NULL COMMENT '家事名'
    , description VARCHAR (500) NULL COMMENT '説明'
    , category VARCHAR (10) NOT NULL COMMENT 'カテゴリコード:m_code：0004'
    , recurrence_type VARCHAR (10) NOT NULL COMMENT '周期タイプコード:m_code：0001'
    , weekly_days INT UNSIGNED NULL COMMENT '週次実行曜日ビットマスク:Sun：1、Mon：2、...、Sat：64'
    , day_of_month INT NULL COMMENT '月次実行日'
    , nth_week INT NULL COMMENT '第何週:m_code：0003'
    , weekday INT NULL COMMENT '対象曜日:m_code：0002'
    , start_date DATE NOT NULL DEFAULT '2025-01-01' COMMENT '有効開始日'
    , end_date DATE NOT NULL DEFAULT '2029-12-31' COMMENT '有効終了日'
    , default_assignee_user_id BIGINT UNSIGNED NULL COMMENT 'デフォルト担当者ユーザID'
    , create_user_id BIGINT UNSIGNED NOT NULL COMMENT '作成者ユーザID'
    , create_program VARCHAR (10) NOT NULL COMMENT '作成プログラム:m_code：0012'
    , created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '作成日時'
    , update_user_id BIGINT UNSIGNED NOT NULL COMMENT '更新者ユーザID'
    , update_program VARCHAR (10) NOT NULL COMMENT '更新プログラム:m_code：0012'
    , updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6)
                                ON UPDATE CURRENT_TIMESTAMP (6) COMMENT '更新日時'
    , PRIMARY KEY (housework_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_ja_0900_as_cs COMMENT = '家事マスタ';

/* ============================================================
   m_housework_template : 家事テンプレートマスタ
   ============================================================ */
CREATE TABLE m_housework_template(
                                     housework_template_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '家事テンプレートID'
    , name_ja VARCHAR (100) NOT NULL COMMENT '家事名（日本語）'
    , name_en VARCHAR (100) NOT NULL COMMENT '家事名（英語）'
    , name_es VARCHAR (100) NOT NULL COMMENT '家事名（スペイン語）'
    , description_ja VARCHAR (500) NULL COMMENT '説明（日本語）'
    , description_en VARCHAR (500) NULL COMMENT '説明（英語）'
    , description_es VARCHAR (500) NULL COMMENT '説明（スペイン語）'
    , recommendation_ja VARCHAR (500) NULL COMMENT '設定おすすめコメント（日本語）'
    , recommendation_en VARCHAR (500) NULL COMMENT '設定おすすめコメント（英語）'
    , recommendation_es VARCHAR (500) NULL COMMENT '設定おすすめコメント（スペイン語）'
    , category VARCHAR (10) NOT NULL COMMENT 'カテゴリコード:m_code：0004'
    , recurrence_type VARCHAR (10) NOT NULL COMMENT '周期タイプコード:m_code：0001'
    , weekly_days INT UNSIGNED NULL COMMENT '週次実行曜日ビットマスク:Sun：1、Mon：2、...、Sat：64'
    , day_of_month INT NULL COMMENT '月次実行日'
    , nth_week INT NULL COMMENT '第何週:m_code：0003'
    , weekday INT NULL COMMENT '対象曜日:m_code：0002'
    , create_user_id BIGINT UNSIGNED NOT NULL COMMENT '作成者ユーザID'
    , create_program VARCHAR (10) NOT NULL COMMENT '作成プログラム:m_code：0012'
    , created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '作成日時'
    , update_user_id BIGINT UNSIGNED NOT NULL COMMENT '更新者ユーザID'
    , update_program VARCHAR (10) NOT NULL COMMENT '更新プログラム:m_code：0012'
    , updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6)
                                         ON UPDATE CURRENT_TIMESTAMP (6) COMMENT '更新日時'
    , PRIMARY KEY (housework_template_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_ja_0900_as_cs COMMENT = '家事テンプレートマスタ'
;

/* ============================================================
   t_housework_task : 家事タスク
   ============================================================ */
CREATE TABLE t_housework_task(
                                 housework_task_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '家事タスクID'
    , household_id BIGINT UNSIGNED NOT NULL COMMENT '世帯ID:m_household.household_id'
    , housework_id BIGINT UNSIGNED NOT NULL COMMENT '家事ID:m_housework.housework_id'
    , name VARCHAR (100) NOT NULL COMMENT '家事名:m_housework.name のスナップショット'
    , description VARCHAR (500) NULL COMMENT '説明:m_housework.description のスナップショット'
    , category VARCHAR (10) NOT NULL COMMENT 'カテゴリコード:m_code：0004 m_housework.description のスナップショット'
    , target_date DATE NOT NULL COMMENT '実施対象日'
    , assignee_user_id BIGINT UNSIGNED NULL COMMENT '担当者ユーザID:m_user.user_id。NULLは未割当'
    , status VARCHAR (10) NOT NULL DEFAULT '0' COMMENT 'タスクステータス:m_code：0005'
    , assign_reason_type VARCHAR (10) NULL COMMENT '直近の割当理由区分:m_code：0006'
    , done_at DATE NULL COMMENT '完了日'
    , skipped_reason VARCHAR (255) NULL COMMENT 'スキップ理由'
    , create_user_id BIGINT UNSIGNED NOT NULL COMMENT '作成者ユーザID'
    , create_program VARCHAR (10) NOT NULL COMMENT '作成プログラム:m_code：0012'
    , created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '作成日時'
    , update_user_id BIGINT UNSIGNED NOT NULL COMMENT '更新者ユーザID'
    , update_program VARCHAR (10) NOT NULL COMMENT '更新プログラム:m_code：0012'
    , updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6)
                                     ON UPDATE CURRENT_TIMESTAMP (6) COMMENT '更新日時'
    , PRIMARY KEY (housework_task_id)
    , KEY idx_task_household_date(household_id, target_date)
    , KEY idx_task_assignee(assignee_user_id)
    , KEY idx_task_housework(housework_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_ja_0900_as_cs COMMENT = '家事タスク';

/* ============================================================
   t_housework_task_assignment_history : 家事タスク担当変更履歴
   ============================================================ */
CREATE TABLE t_housework_task_assignment_history(
                                                    housework_task_assignment_history_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '家事タスク担当変更履歴ID'
    , housework_task_id BIGINT UNSIGNED NOT NULL COMMENT '家事タスクID:t_housework_task.housework_task_id'
    , household_id BIGINT UNSIGNED NOT NULL COMMENT '世帯ID:m_household.household_id'
    , from_assignee_user_id BIGINT UNSIGNED NULL COMMENT '変更前担当者ユーザID:NULLは未割当'
    , to_assignee_user_id BIGINT UNSIGNED NULL COMMENT '変更後担当者ユーザID'
    , operated_user_id BIGINT UNSIGNED NOT NULL COMMENT '操作を行ったユーザID:ドラッグした人'
    , assign_reason_type VARCHAR (10) NOT NULL COMMENT '変更理由種別:m_code：0006'
    , note VARCHAR (255) NULL COMMENT '備考・メモ'
    , changed_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '変更日時'
    , create_user_id BIGINT UNSIGNED NOT NULL COMMENT '作成者ユーザID'
    , create_program VARCHAR (10) NOT NULL COMMENT '作成プログラム:m_code：0012'
    , created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '作成日時'
    , update_user_id BIGINT UNSIGNED NOT NULL COMMENT '更新者ユーザID'
    , update_program VARCHAR (10) NOT NULL COMMENT '更新プログラム:m_code：0012'
    , updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6)
                                                        ON UPDATE CURRENT_TIMESTAMP (6) COMMENT '更新日時'
    , PRIMARY KEY (housework_task_assignment_history_id)
    , KEY idx_assignment_history_task(housework_task_id)
    , KEY idx_assignment_history_household(household_id)
    , KEY idx_assignment_history_to_user(to_assignee_user_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_ja_0900_as_cs COMMENT = '家事タスク担当変更履歴'
;

/* ============================================================
   t_shopping_item : 買い物アイテム
   ============================================================ */
CREATE TABLE t_shopping_item(
                                shopping_item_id bigint unsigned NOT NULL auto_increment COMMENT '買い物アイテムID'
    , household_id bigint unsigned NOT NULL COMMENT '世帯ID:m_household.household_id'
    , name VARCHAR (100) NOT NULL COMMENT '品名'
    , memo VARCHAR (255) NULL COMMENT 'メモ'
    , store_type VARCHAR (10) NOT NULL COMMENT '購入場所種別:m_code：0010'
    , status VARCHAR (10) NOT NULL DEFAULT '0' COMMENT 'ステータス:m_code：0009'
    , favorite VARCHAR (10) NOT NULL DEFAULT '0' COMMENT 'お気に入り:m_code：0013'
    , purchased_at DATE NULL COMMENT '購入完了日:status=9 になった日'
    , create_user_id bigint unsigned NOT NULL COMMENT '作成者ユーザID'
    , create_program VARCHAR (10) NOT NULL COMMENT '作成プログラム:m_code：0012'
    , created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '作成日時'
    , update_user_id bigint unsigned NOT NULL COMMENT '更新者ユーザID'
    , update_program VARCHAR (10) NOT NULL COMMENT '更新プログラム:m_code：0012'
    , updated_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6)
                                    ON UPDATE CURRENT_TIMESTAMP (6) COMMENT '更新日時'
    , PRIMARY KEY (shopping_item_id)
    , KEY idx_shopping_item_household(household_id)
    , KEY idx_shopping_item_household_store_status(household_id, store_type, status)
) engine = innodb DEFAULT charset = utf8mb4 COLLATE = utf8mb4_ja_0900_as_cs COMMENT = '買い物アイテム'
;

/* ============================================================
   t_shopping_item_attachment : 買い物アイテム添付ファイル
   ============================================================ */
CREATE TABLE t_shopping_item_attachment(
                                           shopping_item_attachment_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '買い物アイテム添付ファイルID'
    , shopping_item_id BIGINT UNSIGNED NOT NULL COMMENT '買い物アイテムID:t_shopping_item.shopping_item_id'
    , file_key VARCHAR (255) NOT NULL COMMENT 'ストレージキー:S3など'
    , file_name VARCHAR (255) NULL COMMENT '元ファイル名'
    , mime_type VARCHAR (100) NULL COMMENT 'MIMEタイプ'
    , sort_order INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '表示順'
    , create_user_id BIGINT UNSIGNED NOT NULL COMMENT '作成者ユーザID'
    , create_program VARCHAR (10) NOT NULL COMMENT '作成プログラム:m_code：0012'
    , created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '作成日時'
    , update_user_id BIGINT UNSIGNED NOT NULL COMMENT '更新者ユーザID'
    , update_program VARCHAR (10) NOT NULL COMMENT '更新プログラム:m_code：0012'
    , updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6)
                                               ON UPDATE CURRENT_TIMESTAMP (6) COMMENT '更新日時'
    , PRIMARY KEY (shopping_item_attachment_id)
    , KEY idx_shopping_item_attachment_item(shopping_item_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_ja_0900_as_cs COMMENT = '買い物アイテム添付ファイル'
;

/* ============================================================
   m_announcement : お知らせマスタ
   ============================================================ */
CREATE TABLE m_announcement(
                               announcement_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'お知らせID'
    , title_ja VARCHAR (200) NOT NULL COMMENT 'タイトル（日本語）'
    , title_en VARCHAR (200) NOT NULL COMMENT 'タイトル（英語）'
    , title_es VARCHAR (200) NOT NULL COMMENT 'タイトル（スペイン語）'
    , body_ja TEXT NOT NULL COMMENT '本文（日本語）'
    , body_en TEXT NOT NULL COMMENT '本文（英語）'
    , body_es TEXT NOT NULL COMMENT '本文（スペイン語）'
    , status VARCHAR (10) NOT NULL DEFAULT '1' COMMENT 'ステータス:m_code：0011'
    , publish_start_at DATETIME(6) NOT NULL COMMENT '掲載開始日時'
    , publish_end_at DATETIME(6) NULL COMMENT '掲載終了日時:NULLは無期限'
    , create_user_id BIGINT UNSIGNED NOT NULL COMMENT '作成者ユーザID'
    , create_program VARCHAR (10) NOT NULL COMMENT '作成プログラム:m_code：0012'
    , created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '作成日時'
    , update_user_id BIGINT UNSIGNED NOT NULL COMMENT '更新者ユーザID'
    , update_program VARCHAR (10) NOT NULL COMMENT '更新プログラム:m_code：0012'
    , updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6)
                                   ON UPDATE CURRENT_TIMESTAMP (6) COMMENT '更新日時'
    , PRIMARY KEY (announcement_id)
    , KEY idx_announcement_publish(status, publish_start_at, publish_end_at)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_ja_0900_as_cs COMMENT = 'お知らせマスタ'
;

/* ============================================================
   t_housework_task_recalc_request : 家事タスク再計算リクエスト
   ============================================================ */
CREATE TABLE t_housework_task_recalc_request(
                                                request_id bigint unsigned NOT NULL auto_increment COMMENT 'リクエストID'
    , housework_id bigint unsigned NOT NULL COMMENT '家事ID:m_housework.housework_id'
    , recalc_request_status VARCHAR (10) NOT NULL COMMENT 'ステータス:m_code：0014'
    , retry_count INT unsigned NOT NULL DEFAULT 0 COMMENT 'リトライ回数'
    , last_error_message VARCHAR (500) NULL COMMENT 'エラー内容:FAILED時'
    , create_user_id bigint unsigned NOT NULL COMMENT '作成者ユーザID'
    , create_program VARCHAR (10) NOT NULL COMMENT '作成プログラム:m_code：0012'
    , created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '作成日時'
    , update_user_id bigint unsigned NOT NULL COMMENT '更新者ユーザID'
    , update_program VARCHAR (10) NOT NULL COMMENT '更新プログラム:m_code：0012'
    , updated_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6)
                                                    ON UPDATE CURRENT_TIMESTAMP (6) COMMENT '更新日時'
    , PRIMARY KEY (request_id)
    , KEY idx_recalc_status(recalc_request_status)
    , KEY idx_recalc_housework(housework_id)
) engine = innodb DEFAULT charset = utf8mb4 COLLATE = utf8mb4_ja_0900_as_cs COMMENT = '家事タスク再計算リクエスト'
;
