-- ------------------------------------------------------------
-- V00_001_010__add_inquiry.sql
--   問い合わせ機能追加
-- ------------------------------------------------------------

/* ============================================================
   0021 : 問い合わせカテゴリ
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
    ('0021', '問い合わせカテゴリ', 'InquiryCategory', '10', 'General',
     '一般', 'General', 'General',
     NULL, '10001',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6)),
    ('0021', '問い合わせカテゴリ', 'InquiryCategory', '20', 'Housework',
     '家事機能', 'Housework', 'Tareas del hogar',
     NULL, '10002',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6)),
    ('0021', '問い合わせカテゴリ', 'InquiryCategory', '21', 'Shopping',
     '買い物機能', 'Shopping', 'Compras',
     NULL, '10003',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6)),
    ('0021', '問い合わせカテゴリ', 'InquiryCategory', '30', 'Account',
     'アカウント設定', 'Account Settings', 'Cuenta Configuración',
     NULL, '10004',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6)),
    ('0021', '問い合わせカテゴリ', 'InquiryCategory', '40', 'Bug',
     '不具合報告', 'Bug Report', 'Reporte de error',
     NULL, '10005',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6)),
    ('0021', '問い合わせカテゴリ', 'InquiryCategory', '90', 'Other',
     'その他', 'Other', 'Otro',
     NULL, '10006',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6));

/* ============================================================
   0022 : 問い合わせステータス
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
    ('0022', '問い合わせステータス', 'InquiryStatus', '00', 'Open',
     '受付中', 'Open', 'Abierto',
     '送信直後・AI返信処理待ち状態', '10001',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6)),
    ('0022', '問い合わせステータス', 'InquiryStatus', '10', 'AiAnswered',
     'AI返信済', 'AI Answered', 'Respondido por IA',
     'AI返信済・ユーザー確認待ち状態', '10002',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6)),
    ('0022', '問い合わせステータス', 'InquiryStatus', '20', 'PendingStaff',
     'スタッフ対応待ち', 'Pending Staff', 'Pendiente de personal',
     'ユーザーが解決しなかったを選択・スタッフ返信待ち状態', '10003',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6)),
    ('0022', '問い合わせステータス', 'InquiryStatus', '25', 'StaffAnswered',
     'スタッフ返信済', 'Staff Answered', 'Respondido por personal',
     'スタッフ返信済・ユーザー確認待ち状態（STAFF対応機能実装時に使用）', '10004',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6)),
    ('0022', '問い合わせステータス', 'InquiryStatus', '90', 'Closed',
     'クローズ', 'Closed', 'Cerrado',
     'ユーザーが解決したを選択・終端状態', '10005',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6));

/* ============================================================
   0023 : 送信者タイプ
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
    ('0023', '送信者タイプ', 'SenderType', 'USER', 'User',
     'あなた', 'You', 'Tú',
     NULL, '10001',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6)),
    ('0023', '送信者タイプ', 'SenderType', 'AI', 'AI',
     'AIサポート', 'AI Support', 'Soporte IA',
     NULL, '10002',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6)),
    ('0023', '送信者タイプ', 'SenderType', 'STAFF', 'Staff',
     'スタッフ', 'Staff', 'Personal',
     NULL, '10003',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6));

/* ============================================================
   0012 : プログラム種別
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
    ('0012', 'プログラム種別', 'ProgramType', 'OnlInqry', 'ONL_INQRY',
     'ONL_INQRY', 'ONL_INQRY', 'ONL_INQRY',
     NULL, '20016',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6));

/* ============================================================
   t_inquiry : 問い合わせ
   ============================================================ */
CREATE TABLE t_inquiry (
    inquiry_id      BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT           COMMENT '問い合わせID'
    , user_id       BIGINT UNSIGNED  NOT NULL                          COMMENT 'ユーザーID:m_user.user_id'
    , household_id  BIGINT UNSIGNED  NOT NULL                          COMMENT '世帯ID:m_household.household_id'
    , category      VARCHAR(10)      NOT NULL                          COMMENT '問い合わせカテゴリ:m_code：0021'
    , status        VARCHAR(10)      NOT NULL DEFAULT '00'             COMMENT '問い合わせステータス:m_code：0022'
    , title         VARCHAR(200)     NOT NULL                          COMMENT '件名'
    , create_user_id  BIGINT UNSIGNED NOT NULL                         COMMENT '作成者ユーザID'
    , create_program  VARCHAR(10)    NOT NULL                          COMMENT '作成プログラム:m_code：0012'
    , created_at      DATETIME(6)    NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '作成日時'
    , update_user_id  BIGINT UNSIGNED NOT NULL                         COMMENT '更新者ユーザID'
    , update_program  VARCHAR(10)    NOT NULL                          COMMENT '更新プログラム:m_code：0012'
    , updated_at      DATETIME(6)    NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
                      ON UPDATE CURRENT_TIMESTAMP(6)                  COMMENT '更新日時'
    , PRIMARY KEY (inquiry_id)
    , KEY idx_inquiry_user(user_id)
    , KEY idx_inquiry_household_status(household_id, status)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_ja_0900_as_cs COMMENT = '問い合わせ';

/* ============================================================
   t_inquiry_message : 問い合わせメッセージ
   ============================================================ */
CREATE TABLE t_inquiry_message (
    message_id      BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT           COMMENT 'メッセージID'
    , inquiry_id    BIGINT UNSIGNED  NOT NULL                          COMMENT '問い合わせID:t_inquiry.inquiry_id'
    , seq           INT UNSIGNED     NOT NULL                          COMMENT 'メッセージ連番（1始まり）'
    , sender_type   VARCHAR(10)      NOT NULL                          COMMENT '送信者タイプ:m_code：0023'
    , body          TEXT             NOT NULL                          COMMENT '本文'
    , create_user_id  BIGINT UNSIGNED NOT NULL                         COMMENT '作成者ユーザID'
    , create_program  VARCHAR(10)    NOT NULL                          COMMENT '作成プログラム:m_code：0012'
    , created_at      DATETIME(6)    NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '作成日時'
    , update_user_id  BIGINT UNSIGNED NOT NULL                         COMMENT '更新者ユーザID'
    , update_program  VARCHAR(10)    NOT NULL                          COMMENT '更新プログラム:m_code：0012'
    , updated_at      DATETIME(6)    NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
                      ON UPDATE CURRENT_TIMESTAMP(6)                  COMMENT '更新日時'
    , PRIMARY KEY (message_id)
    , UNIQUE KEY uq_inquiry_message_seq (inquiry_id, seq)
    , KEY idx_inquiry_message_inquiry(inquiry_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_ja_0900_as_cs COMMENT = '問い合わせメッセージ';