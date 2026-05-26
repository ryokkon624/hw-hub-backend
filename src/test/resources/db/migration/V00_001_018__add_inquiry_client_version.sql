-- ------------------------------------------------------------
-- V00_001_018__add_inquiry_client_version.sql
--   問い合わせ起票時のUIクライアント・バージョン情報追加
-- ------------------------------------------------------------

/* ============================================================
   0029 : UIクライアント
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
    ('0029', 'UIクライアント', 'UiClient', 'web', 'Web',
     'Web', 'Web', 'Web',
     NULL, '10001',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6)),
    ('0029', 'UIクライアント', 'UiClient', 'mobile', 'Mobile',
     'モバイル', 'Mobile', 'Móvil',
     NULL, '10002',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6));

/* ============================================================
   t_inquiry : UIクライアント・バージョンカラム追加
   Step 1: NULL 許容で追加
   ============================================================ */
ALTER TABLE t_inquiry
  ADD COLUMN ui_client  VARCHAR(10)  NULL COMMENT 'UIクライアント:m_code:0029',
  ADD COLUMN ui_version VARCHAR(20)  NULL COMMENT 'UIバージョン（問い合わせ起票時）',
  ADD COLUMN api_version VARCHAR(20) NULL COMMENT 'APIバージョン（問い合わせ起票時）';

/* ============================================================
   Step 2: 既存レコードにデフォルト値を UPDATE
   ============================================================ */
UPDATE t_inquiry
SET ui_client   = 'web',
    ui_version  = '1.0.0',
    api_version = '1.0.0'
WHERE ui_client IS NULL;

/* ============================================================
   Step 3: NOT NULL 制約を付与
   ============================================================ */
ALTER TABLE t_inquiry
  MODIFY COLUMN ui_client  VARCHAR(10)  NOT NULL COMMENT 'UIクライアント:m_code:0029',
  MODIFY COLUMN ui_version VARCHAR(20)  NOT NULL COMMENT 'UIバージョン（問い合わせ起票時）',
  MODIFY COLUMN api_version VARCHAR(20) NOT NULL COMMENT 'APIバージョン（問い合わせ起票時）';
