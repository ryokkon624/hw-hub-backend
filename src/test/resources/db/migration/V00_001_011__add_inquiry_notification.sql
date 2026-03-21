-- ------------------------------------------------------------
-- V00_001_011__add_inquiry_notification.sql
--   問い合わせ通知 m_code 追加
-- ------------------------------------------------------------

/* ============================================================
   0017 : 通知種別 - 問い合わせ返信
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
    ('0017', '通知種別', 'NotificationType', '0401', 'INQ_REPLIED',
     '問い合わせに返信がありました', 'Your inquiry has been replied', 'Su consulta ha sido respondida',
     '問い合わせへの返信通知（AI/STAFF共通）', '10401',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6));

/* ============================================================
   0019 : 通知リンク種別 - 問い合わせ詳細
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
    ('0019', '通知リンク種別', 'NotificationLinkType', 'Inquiry', 'INQUIRY',
     '問い合わせ詳細', 'Inquiry Detail', 'Detalle de consulta',
     '問い合わせ詳細画面へ遷移。link_id = inquiry_id', '10006',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6));

/* ============================================================
   0020 : 通知グループ (NotificationGroup)
   - 900 : 問い合わせ進捗
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
     '900', '問い合わせ進捗',
     '問い合わせ', 'Inquiry', 'Consulta',
     NULL, '10900',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6));

/* ============================================================
   0012 : プログラム種別 - 問い合わせ通知発行
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
    ('0012', 'プログラム種別', 'ProgramType', 'BtcInqAI', 'BTC_INQ_AI',
     'BTC_INQ_AI', 'BTC_INQ_AI', 'BTC_INQ_AI',
     null, '20105',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6));
