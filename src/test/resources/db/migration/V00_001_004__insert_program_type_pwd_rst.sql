/* ============================================================
   V00_001_004__insert_program_type_pwd_rst.sql
   ============================================================ */

/* ============================================================
   0012 : プログラム種別
   機能追加：パスワードリセット、世帯削除
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0012', 'プログラム種別', 'ProgramType', 'BtcHldClen', 'BTC_HLD_CLEN',
       'BTC_HLD_CLEN', 'BTC_HLD_CLEN', 'BTC_HLD_CLEN',
       NULL, '20103',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0012', 'プログラム種別', 'ProgramType', 'OnlPwdRst', 'ONL_PWDRST',
       'ONL_PWDRST', 'ONL_PWDRST', 'ONL_PWDRST',
       NULL, '20013',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));

/* ============================================================
   0012 : プログラム種別
   display_orderの補正
   ============================================================ */
UPDATE m_code SET display_order = '20100', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0012' AND code_value = 'BtcInvExpr';
UPDATE m_code SET display_order = '20101', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0012' AND code_value = 'BtcTskGen';
UPDATE m_code SET display_order = '20102', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0012' AND code_value = 'BtcTskRecl';
