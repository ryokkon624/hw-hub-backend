-- ------------------------------------------------------------
-- V00_000_003__insert_admin_user.sql
--   HouseworkHub: 管理者ユーザ投入
-- ------------------------------------------------------------

INSERT INTO m_user (
    user_id,
    email,
    password_hash,
    auth_provider,
    auth_provider_id,
    display_name,
    profile_image_key,
    locale,
    is_active,
    create_user_id,
    create_program,
    created_at,
    update_user_id,
    update_program,
    updated_at
) VALUES
      (1, 'admin@houseworkhub.local', 'NotLoginEbjxjK/wXfRfHjx11bADubv5Wp0EG/DAQev1OAFDXFDIVMp7.IhK', 'LOCAL', NULL, 'システム管理者', NULL, 'ja', 1, 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
      (2, 'batch@houseworkhub.local', 'NotLoginEbjxjK/wXfRfHjx11bADubv5Wp0EG/DAQev1OAFDXFDIVMp7.IhK', 'LOCAL', NULL, 'バッチ', NULL, 'ja', 1, 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
      (3, 'maintenance@houseworkhub.local', 'NotLoginEbjxjK/wXfRfHjx11bADubv5Wp0EG/DAQev1OAFDXFDIVMp7.IhK', 'LOCAL', NULL, '運用保守', NULL, 'ja', 1, 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6));
