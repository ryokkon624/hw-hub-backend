-- ------------------------------------------------------------
-- V00_001_014__add_sys_template_mng_permission.sql
--   システムテンプレート管理機能追加
-- ------------------------------------------------------------

/* ============================================================
   0025 : パーミッション
   SYS_TEMPLATE_MNG を追加
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
    ('0025', 'パーミッション', 'Permission', '30', 'SysTemplateMng',
     'システムテンプレート管理', 'System Template Management', 'Gestión de plantillas del sistema',
     'システムテンプレートの管理', '10030',
     1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6));

/* ============================================================
   m_role_permission : ロール・パーミッションマッピング
   ============================================================ */
INSERT INTO m_role_permission (
    role, permission,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
    -- ADMIN: 全パーミッション
    ('ADMIN', '30', 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
    ('SPPRT', '30', 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6));

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
    ('0012', 'プログラム種別', 'ProgramType', 'OnlAdmHwTp', 'ONL_ADM_HW_TP',
     'ONL_ADM_HW_TP', 'ONL_ADM_HW_TP', 'ONL_ADM_HW_TP',
     null, '20020',
     1, 'INIT_DATA', NOW(6),
     1, 'INIT_DATA', NOW(6));
