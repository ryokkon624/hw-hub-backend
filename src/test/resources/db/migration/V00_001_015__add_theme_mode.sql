-- ------------------------------------------------------------
-- V00_001_015__add_theme_mode.sql
--   ThemeMode区分追加・m_userにtheme_modeカラム追加
-- ------------------------------------------------------------

/* ============================================================
   0026 : テーマモード
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
    ('0026', 'テーマモード', 'ThemeMode', 'SYSTEM', 'System',
     'システム', 'System', 'Sistema',
     'OSの設定に従う', '10001',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('0026', 'テーマモード', 'ThemeMode', 'LIGHT', 'Light',
     'ライト', 'Light', 'Claro',
     'ライトモード固定', '10002',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('0026', 'テーマモード', 'ThemeMode', 'DARK', 'Dark',
     'ダーク', 'Dark', 'Oscuro',
     'ダークモード固定', '10003',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6));

/* ============================================================
   m_user : theme_mode カラム追加
   ============================================================ */
ALTER TABLE m_user
    ADD COLUMN theme_mode VARCHAR(10) NOT NULL DEFAULT 'SYSTEM'
        COMMENT 'テーマモード:m_code：0026'
    AFTER locale;
