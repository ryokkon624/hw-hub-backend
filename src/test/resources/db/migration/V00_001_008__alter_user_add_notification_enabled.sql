/* ============================================================
   V00_001_008__alter_user_add_notification_enabled.sql
   ============================================================ */

/* ============================================================
   m_user : 通知受信（アプリ内通知）有効フラグを追加
   - 1: 有効（デフォルト）
   - 0: 無効
   ============================================================ */

ALTER TABLE m_user
  ADD COLUMN notification_enabled TINYINT(1) NOT NULL DEFAULT 1
  COMMENT '通知受信有効フラグ(1:有効,0:無効)'
  AFTER locale;

/* ============================================================
   0020 : 通知グループ (NotificationGroup)
   - 100 : おうち関連（招待/離脱/オーナー変更など）
   - 200 : タスク割当関連
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en,
    code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
    ('0020', '通知グループ', 'NotificationGroup',
     '100', 'HOUSEHOLD',
     'おうち関連', 'Household', 'Household',
     NULL, '10001',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6)),
    ('0020', '通知グループ', 'NotificationGroup',
     '200', 'TASK_ASSIGNMENT',
     'タスク割当', 'Task assignment', 'Task assignment',
     NULL, '10002',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6));


/* ============================================================
   m_user_notification_setting : ユーザ通知設定（グループ単位）
   - 差分だけ持つ運用（未登録はデフォルトON）
   - PK: (user_id, notification_group_code)
   ============================================================ */
CREATE TABLE m_user_notification_setting( 
    user_id bigint NOT NULL COMMENT 'ユーザID'
    , notification_group VARCHAR (20) NOT NULL COMMENT '通知グループコード:m_code：0020'
    , enabled tinyint(1) NOT NULL DEFAULT 1 COMMENT '有効フラグ:(1:有効,0:無効)'
    , create_user_id bigint unsigned NOT NULL COMMENT '作成者ユーザID'
    , create_program VARCHAR (10) NOT NULL COMMENT '作成プログラム:m_code：0012'
    , created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '作成日時'
    , update_user_id bigint unsigned NOT NULL COMMENT '更新者ユーザID'
    , update_program VARCHAR (10) NOT NULL COMMENT '更新プログラム:m_code：0012'
    , updated_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) 
        ON UPDATE CURRENT_TIMESTAMP (6) COMMENT '更新日時'
    , PRIMARY KEY (user_id, notification_group)
) engine = innodb DEFAULT charset = utf8mb4 COLLATE = utf8mb4_ja_0900_as_cs COMMENT = 'ユーザ通知設定（グループ単位）'
;
