/* ============================================================
   V00_001_001 : Email verification (create t_user_email_verification + alter m_user)
   ============================================================ */

-- ============================================================
-- m_user : add email_verified_at
-- ============================================================
ALTER TABLE m_user
    ADD COLUMN email_verified_at DATETIME(6) NULL COMMENT 'メール確認日時（NULL=未確認）'
    AFTER locale;

-- ============================================================
-- t_user_email_verification : ユーザメール認証トークン管理
-- ============================================================
CREATE TABLE t_user_email_verification(
      user_email_verification_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ユーザメール認証ID'
    , user_id BIGINT UNSIGNED NOT NULL COMMENT 'ユーザID:m_user.user_id'
    , token_hash BINARY(32) NOT NULL COMMENT '確認トークンハッシュ:SHA-256想定 / 生トークン非保持'
    , expires_at DATETIME(6) NOT NULL COMMENT '有効期限'
    , used_at DATETIME(6) NULL COMMENT '使用日時:（NULL=未使用）'
    , requested_at DATETIME(6) NOT NULL COMMENT '発行（送信要求）日時'
    , request_count INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '発行回数（再送回数管理）'
    , create_user_id BIGINT UNSIGNED NOT NULL COMMENT '作成者ユーザID'
    , create_program VARCHAR (10) NOT NULL COMMENT '作成プログラム:m_code：0012'
    , created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '作成日時'
    , update_user_id BIGINT UNSIGNED NOT NULL COMMENT '更新者ユーザID'
    , update_program VARCHAR (10) NOT NULL COMMENT '更新プログラム:m_code：0012'
    , updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6)
                   ON UPDATE CURRENT_TIMESTAMP (6) COMMENT '更新日時'
    , PRIMARY KEY (user_email_verification_id)
    , KEY idx_user_email_verification_user_id (user_id)
    , UNIQUE KEY uq_user_email_verification_token_hash (token_hash)
    , KEY idx_user_email_verification_expires_at (expires_at)
    , KEY idx_user_email_verification_used_at (used_at)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_ja_0900_as_cs COMMENT = 'ユーザメール認証（本人確認）';
