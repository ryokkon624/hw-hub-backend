-- ------------------------------------------------------------
-- V00_001_012__add_user_role.sql
--   ロール・パーミッション機能追加
-- ------------------------------------------------------------

/* ============================================================
   0024 : ユーザーロール
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
    ('0024', 'ユーザーロール', 'UserRole', 'ADMIN', 'Admin',
     '管理者', 'Admin', 'Administrador',
     '全管理機能にアクセス可能', '10001',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('0024', 'ユーザーロール', 'UserRole', 'SPPRT', 'Support',
     'サポート', 'Support', 'Soporte',
     '問い合わせ対応のみ可能', '10002',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6));

/* ============================================================
   0025 : パーミッション
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
    ('0025', 'パーミッション', 'Permission', '20', 'InquiryReply',
     '問い合わせ返信', 'Inquiry Reply', 'Responder consultas',
     '問い合わせへの返信・ステータス更新', '10020',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('0025', 'パーミッション', 'Permission', '10', 'UserListView',
     'ユーザー一覧閲覧', 'User List View', 'Ver lista de usuarios',
     'ユーザー一覧・詳細の閲覧', '10010',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('0025', 'パーミッション', 'Permission', '11', 'RoleManage',
     'ロール管理', 'Role Management', 'Gestión de roles',
     'ユーザーへのロール付与・削除', '10011',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6));

/* ============================================================
   m_user_role : ユーザーロール
   ============================================================ */
CREATE TABLE m_user_role (
    user_role_id    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT      COMMENT 'ユーザーロールID'
    , user_id       BIGINT UNSIGNED NOT NULL                     COMMENT 'ユーザーID:m_user.user_id'
    , role          VARCHAR(10)     NOT NULL                     COMMENT 'ロール:m_code：0024'
    , create_user_id  BIGINT UNSIGNED NOT NULL                   COMMENT '作成者ユーザID'
    , create_program  VARCHAR(10)   NOT NULL                     COMMENT '作成プログラム:m_code：0012'
    , created_at      DATETIME(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '作成日時'
    , update_user_id  BIGINT UNSIGNED NOT NULL                   COMMENT '更新者ユーザID'
    , update_program  VARCHAR(10)   NOT NULL                     COMMENT '更新プログラム:m_code：0012'
    , updated_at      DATETIME(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
                      ON UPDATE CURRENT_TIMESTAMP(6)             COMMENT '更新日時'
    , PRIMARY KEY (user_role_id)
    , UNIQUE KEY uq_user_role (user_id, role)
    , KEY idx_user_role_user(user_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_ja_0900_as_cs COMMENT = 'ユーザーロール';

/* ============================================================
   m_role_permission : ロール・パーミッションマッピング
   ============================================================ */
CREATE TABLE m_role_permission (
    role_permission_id  BIGINT UNSIGNED NOT NULL AUTO_INCREMENT  COMMENT 'ロールパーミッションID'
    , role              VARCHAR(10)     NOT NULL                 COMMENT 'ロール:m_code：0024'
    , permission        VARCHAR(10)     NOT NULL                 COMMENT 'パーミッション:m_code：0025'
    , create_user_id  BIGINT UNSIGNED NOT NULL                   COMMENT '作成者ユーザID'
    , create_program  VARCHAR(10)   NOT NULL                     COMMENT '作成プログラム:m_code：0012'
    , created_at      DATETIME(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '作成日時'
    , update_user_id  BIGINT UNSIGNED NOT NULL                   COMMENT '更新者ユーザID'
    , update_program  VARCHAR(10)   NOT NULL                     COMMENT '更新プログラム:m_code：0012'
    , updated_at      DATETIME(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
                      ON UPDATE CURRENT_TIMESTAMP(6)             COMMENT '更新日時'
    , PRIMARY KEY (role_permission_id)
    , UNIQUE KEY uq_role_permission (role, permission)
    , KEY idx_role_permission_role(role)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_ja_0900_as_cs COMMENT = 'ロールパーミッションマッピング';

/* ============================================================
   m_role_permission 初期データ
   ADMIN    → 全パーミッション
   SUPPORT  → INQUIRY_REPLY のみ
   ============================================================ */
INSERT INTO m_role_permission (
    role, permission,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
    -- ADMIN: 全パーミッション
    ('ADMIN', '10', 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('ADMIN', '11', 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('ADMIN', '20', 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    -- SUPPORT: INQUIRY_REPLY のみ
    ('SPPRT', '20', 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6));

/* ============================================================
   0012 : プログラム種別 - ユーザロール
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
    ('0012', 'プログラム種別', 'ProgramType', 'OnlUsrRole', 'ONL_USR_ROLE',
     'ONL_USR_ROLE', 'ONL_USR_ROLE', 'ONL_USR_ROLE',
     null, '20017',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6)),
    ('0012', 'プログラム種別', 'ProgramType', 'OnlAdmInq', 'ONL_ADM_INQ',
     'ONL_ADM_INQ', 'ONL_ADM_INQ', 'ONL_ADM_INQ',
     null, '20018',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6)),
    ('0012', 'プログラム種別', 'ProgramType', 'OnlAdmUsr', 'ONL_ADM_USR',
     'ONL_ADM_USR', 'ONL_ADM_USR', 'ONL_ADM_USR',
     null, '20019',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6));
