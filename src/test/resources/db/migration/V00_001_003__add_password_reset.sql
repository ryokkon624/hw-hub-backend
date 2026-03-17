/* ============================================================
   V00_001_003__add_password_reset.sql
   - m_user: password_changed_at 追加
   - t_user_password_reset: パスワードリセット要求/トークン管理
   ============================================================ */

-- ----------------------------------------------------------------
-- m_user: パスワード最終変更日時（JWT失効判定に使用）
-- ----------------------------------------------------------------
ALTER TABLE m_user
  ADD COLUMN password_changed_at DATETIME(6) NULL COMMENT 'パスワード最終変更日時:NULL=まだ一度もパスワード変更していない。NOT NULL:最後にパスワード変更した日時' 
  AFTER password_hash;

-- ----------------------------------------------------------------
-- t_user_password_reset: パスワードリセットトークン管理
-- ----------------------------------------------------------------
CREATE TABLE t_user_password_reset (
    user_password_reset_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'パスワードリセットID'
  , user_id BIGINT UNSIGNED NOT NULL COMMENT 'ユーザID'
  , token_hash BINARY(32) NOT NULL COMMENT 'リセットトークンハッシュ（SHA-256 / 生トークン非保持）'
  , expires_at DATETIME(6) NOT NULL COMMENT '有効期限'
  , used_at DATETIME(6) NULL COMMENT '使用済み日時'
  , requested_at DATETIME(6) NOT NULL COMMENT '要求日時'
  , request_count INT NOT NULL COMMENT '要求回数'
  , create_user_id BIGINT UNSIGNED NOT NULL COMMENT '作成者ユーザID'
  , create_program VARCHAR(10) NOT NULL COMMENT '作成プログラム:m_code：0012'
  , created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '作成日時'
  , update_user_id BIGINT UNSIGNED NOT NULL COMMENT '更新者ユーザID'
  , update_program VARCHAR(10) NOT NULL COMMENT '更新プログラム:m_code：0012'
  , updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
      ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新日時'

  , PRIMARY KEY (user_password_reset_id)

  -- 検索用Index（verify / cooldown / 運用）
  , KEY idx_user_password_reset_token_hash (token_hash)
  , KEY idx_user_password_reset_user_id_requested_at (user_id, requested_at)
  , KEY idx_user_password_reset_user_id (user_id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='パスワードリセットトークン管理';

