-- ------------------------------------------------------------
-- V00_001_016__add_announcement.sql
--   アナウンスバナー機能追加
-- ------------------------------------------------------------

/* ============================================================
   0027 : アナウンス対象スコープ
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
    ('0027', 'アナウンス対象スコープ', 'AnnouncementScope', 'ALL', 'All',
     '全画面', 'All', 'Todo',
     '全画面に表示', '10001',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('0027', 'アナウンス対象スコープ', 'AnnouncementScope', 'HOME', 'Home',
     'ホーム', 'Home', 'Inicio',
     'ホーム画面', '10002',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('0027', 'アナウンス対象スコープ', 'AnnouncementScope', 'HW_ASSIGN', 'HouseworkAssign',
     '家事割り当て', 'Housework Assign', 'Asignación de tareas',
     '家事割り当て画面', '10003',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('0027', 'アナウンス対象スコープ', 'AnnouncementScope', 'HW_TASK', 'HouseworkTask',
     'マイタスク', 'My Tasks', 'Mis tareas',
     'マイタスク画面', '10004',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('0027', 'アナウンス対象スコープ', 'AnnouncementScope', 'HW_CONF', 'HouseworkSettings',
     '家事設定', 'Housework Settings', 'Configuración de tareas',
     '家事設定画面', '10005',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('0027', 'アナウンス対象スコープ', 'AnnouncementScope', 'SHOPPING', 'Shopping',
     '買い物リスト', 'Shopping', 'Compras',
     '買い物リスト画面', '10006',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('0027', 'アナウンス対象スコープ', 'AnnouncementScope', 'CONF_ACCT', 'SettingsAccount',
     'アカウント設定', 'Account Settings', 'Configuración de cuenta',
     'アカウント設定画面', '10007',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('0027', 'アナウンス対象スコープ', 'AnnouncementScope', 'CONF_HH', 'SettingsHousehold',
     '世帯設定', 'Household Settings', 'Configuración del hogar',
     '世帯設定画面', '10008',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('0027', 'アナウンス対象スコープ', 'AnnouncementScope', 'CONF_APP', 'SettingsApp',
     'アプリ設定', 'App Settings', 'Configuración de la app',
     'アプリ設定画面', '10009',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('0027', 'アナウンス対象スコープ', 'AnnouncementScope', 'NOTIFY', 'Notification',
     '通知', 'Notification', 'Notificación',
     '通知画面', '10010',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('0027', 'アナウンス対象スコープ', 'AnnouncementScope', 'INQUIRY', 'Inquiry',
     'お問い合わせ', 'Inquiry', 'Consulta',
     'お問い合わせ画面', '10011',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('0027', 'アナウンス対象スコープ', 'AnnouncementScope', 'ADMIN', 'Admin',
     '管理', 'Admin', 'Administración',
     '管理画面', '10012',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6));

/* ============================================================
   0028 : アナウンス重要度
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
    ('0028', 'アナウンス重要度', 'AnnouncementSeverity', 'INFO', 'Info',
     'お知らせ', 'Info', 'Información',
     '一般的なお知らせ', '10001',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('0028', 'アナウンス重要度', 'AnnouncementSeverity', 'WARN', 'Warning',
     '警告', 'Warning', 'Advertencia',
     '注意が必要な情報', '10002',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('0028', 'アナウンス重要度', 'AnnouncementSeverity', 'ERROR', 'Error',
     '重要', 'Error', 'Error',
     '重大な問題・緊急のお知らせ', '10003',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6));

/* ============================================================
   m_announcement : アナウンスマスタ
   ============================================================ */
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS m_announcement;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE m_announcement (
    announcement_id   BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT           COMMENT 'アナウンスID'
    , title_ja        VARCHAR(200)     NOT NULL                          COMMENT 'タイトル（日本語）'
    , title_en        VARCHAR(200)     NOT NULL                          COMMENT 'タイトル（英語）'
    , title_es        VARCHAR(200)     NOT NULL                          COMMENT 'タイトル（スペイン語）'
    , body_ja         TEXT             NOT NULL                          COMMENT '本文（日本語）'
    , body_en         TEXT             NOT NULL                          COMMENT '本文（英語）'
    , body_es         TEXT             NOT NULL                          COMMENT '本文（スペイン語）'
    , severity        VARCHAR(10)      NOT NULL                          COMMENT '重要度:m_code：0028'
    , target_scope    VARCHAR(10)      NOT NULL                          COMMENT '対象スコープ:m_code：0027'
    , start_at        DATETIME(6)      NOT NULL                          COMMENT '有効開始日時'
    , end_at          DATETIME(6)      NOT NULL                          COMMENT '有効終了日時'
    , create_user_id  BIGINT UNSIGNED  NOT NULL                          COMMENT '作成者ユーザID'
    , create_program  VARCHAR(10)      NOT NULL                          COMMENT '作成プログラム:m_code：0012'
    , created_at      DATETIME(6)      NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '作成日時'
    , update_user_id  BIGINT UNSIGNED  NOT NULL                          COMMENT '更新者ユーザID'
    , update_program  VARCHAR(10)      NOT NULL                          COMMENT '更新プログラム:m_code：0012'
    , updated_at      DATETIME(6)      NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
                      ON UPDATE CURRENT_TIMESTAMP(6)                    COMMENT '更新日時'
    , PRIMARY KEY (announcement_id)
    , KEY idx_announcement_period (start_at, end_at)
    , KEY idx_announcement_scope  (target_scope)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_ja_0900_as_cs COMMENT = 'アナウンスマスタ';
