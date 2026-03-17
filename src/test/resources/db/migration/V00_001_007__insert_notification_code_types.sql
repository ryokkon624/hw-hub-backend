/* ============================================================
   V00_001_007__insert_notification_code_types.sql
   ============================================================ */

-- ============================================================
-- 0017 : 通知種別
-- ============================================================
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0017', '通知種別', 'NotificationType', '0101', 'INV_ACCEPTED',
       '招待が承認されました', 'Invitation accepted', 'Invitation accepted',
       '招待の承認通知', '10101',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),

      ('0017', '通知種別', 'NotificationType', '0102', 'INV_DECLINED',
       '招待が辞退されました', 'Invitation declined', 'Invitation declined',
       '招待の辞退通知', '10102',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),


      ('0017', '通知種別', 'NotificationType', '0201', 'MEM_BE_REMOVED',
       'おうちから離脱させられました', 'Have been removed', 'Have been removed',
       'あなたはおうちのメンバーから削除されました', '10201',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),

      ('0017', '通知種別', 'NotificationType', '0202', 'MEM_LEFT',
       'おうちから離脱しました', 'Left the household', 'Left the household',
       'メンバーが自発的におうちから離脱しました', '10202',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),

      ('0017', '通知種別', 'NotificationType', '0203', 'ASSIGNED_OWNER',
       'おうちのオーナーにアサインされました', 'Assigned to the owner', 'Assigned to the owner',
       'おうちのOWNERになりました', '10203',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),

      ('0017', '通知種別', 'NotificationType', '0301', 'TASK_ASSIGNED',
       'タスクが割り当てられました', 'TASK_ASSIGNED', 'TASK_ASSIGNED',
       null, '10301',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),

      ('0017', '通知種別', 'NotificationType', '0302', 'BE_DUMPED_TASK',
       'タスクが押し付けられた', 'BE_DUMPED_TASK', 'BE_DUMPED_TASK',
       null, '10302',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),

      ('0017', '通知種別', 'NotificationType', '0303', 'YOUR_TASK_WAS_TAKEN',
       'タスクが奪われた', 'YOUR_TASK_WAS_TAKEN', 'YOUR_TASK_WAS_TAKEN',
       null, '10303',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));

-- ============================================================
-- 0018 : イベントステータス
-- ============================================================
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0018', 'イベントステータス', 'EventStatus', '0', 'PENDING',
       'PENDING', 'PENDING', 'PENDING',
       null, '10001',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0018', 'イベントステータス', 'EventStatus', '1', 'PROCESSING',
       'PROCESSING', 'PROCESSING', 'PROCESSING',
       null, '10002',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0018', 'イベントステータス', 'EventStatus', '2', 'DONE',
       'DONE', 'DONE', 'DONE',
       null, '10003',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));

-- ============================================================
-- 0019 : 通知リンク種別（通知から遷移させる先）
-- ============================================================
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0019', '通知リンク種別', 'NotificationLinkType', 'None', 'NONE',
       'なし', 'None', 'None',
       '遷移なし', '10001',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),

      ('0019', '通知リンク種別', 'NotificationLinkType', 'MyTasks', 'MY_TASKS',
       'My Tasks', 'My Tasks', 'My Tasks',
       'My Tasksへ遷移', '10002',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),

      ('0019', '通知リンク種別', 'NotificationLinkType', 'Household', 'HOUSEHOLD',
       'おうち', 'Household', 'Household',
       'おうち設定画面へ遷移', '10003',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),

      ('0019', '通知リンク種別', 'NotificationLinkType', 'Invite', 'INVITATION',
       '招待', 'Invitation', 'Invitation',
       '招待画面/結果へ遷移', '10004',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),

      ('0019', '通知リンク種別', 'NotificationLinkType', 'Settings', 'SETTINGS',
       '設定', 'Settings', 'Settings',
       '設定画面へ遷移', '10005',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));

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
      ('0012', 'プログラム種別', 'ProgramType', 'BtcNtfAggr', 'BTC_NTF_AGGR',
       'BTC_NTF_AGGR', 'BTC_NTF_AGGR', 'BTC_NTF_AGGR',
       NULL, '20103',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0012', 'プログラム種別', 'ProgramType', 'OnlNtfQry', 'ONL_NTF_QRY',
       'ONL_NTF_QRY', 'ONL_NTF_QRY', 'ONL_NTF_QRY',
       NULL, '20015',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));
