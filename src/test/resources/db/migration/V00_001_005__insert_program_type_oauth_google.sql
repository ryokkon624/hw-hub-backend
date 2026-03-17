/* ============================================================
   V00_001_005__insert_program_type_oauth_google.sql
   ============================================================ */

/* ============================================================
   0012 : プログラム種別
   機能追加：Google OAuth
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0012', 'プログラム種別', 'ProgramType', 'OnlAuthGgl', 'ONL_AUTH_GOOGLE',
       'ONL_AUTH_GOOGLE', 'ONL_AUTH_GOOGLE', 'ONL_AUTH_GOOGLE',
       NULL, '20014',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));
/* ============================================================
   0015 : 認証プロバイダー
   機能追加：Google Account連携
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0015', '認証プロバイダー', 'AuthProvider', 'LOCAL', 'LOCAL',
       'LOCAL', 'LOCAL', 'LOCAL',
       NULL, '10001',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0015', '認証プロバイダー', 'AuthProvider', 'GOOGLE', 'GOOGLE',
       'GOOGLE', 'GOOGLE', 'GOOGLE',
       NULL, '10002',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));
/* ============================================================
   0016 : OAuthフロー
   機能追加：Google Account連携
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0016', 'OAuth Flow', 'OAuthFlow', 'LINK', 'LINK',
       'LINK', 'LINK', 'LINK',
       NULL, '10001',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0016', 'OAuth Flow', 'OAuthFlow', 'LOGIN', 'LOGIN',
       'LOGIN', 'LOGIN', 'LOGIN',
       NULL, '10002',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));
