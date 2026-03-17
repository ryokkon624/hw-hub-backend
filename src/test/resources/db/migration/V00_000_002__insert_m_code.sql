-- ------------------------------------------------------------
-- m_code 初期データ投入（V00_000_002）
-- ------------------------------------------------------------

/* ============================================================
   0001 : 周期タイプ
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0001', '周期タイプ', 'RecurrenceType', '1', 'Weekly',
       '毎週', 'Weekly', 'Semanal',
       NULL, '10001',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0001', '周期タイプ', 'RecurrenceType', '2', 'Monthly',
       '毎月', 'Monthly', 'Mensual',
       NULL, '10002',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0001', '周期タイプ', 'RecurrenceType', '3', 'NthWeekday',
       '第n曜日', 'Nth Weekday', 'Enésimo día',
       NULL, '10003',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));

/* ============================================================
   0002 : 曜日
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0002', '曜日', 'Weekday', '0', 'Sun',
       '日曜日', 'Sunday', 'Domingo',
       NULL, '10001',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0002', '曜日', 'Weekday', '1', 'Mon',
       '月曜日', 'Monday', 'Lunes',
       NULL, '10002',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0002', '曜日', 'Weekday', '2', 'Tue',
       '火曜日', 'Tuesday', 'Martes',
       NULL, '10003',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0002', '曜日', 'Weekday', '3', 'Wed',
       '水曜日', 'Wednesday', 'Miércoles',
       NULL, '10004',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0002', '曜日', 'Weekday', '4', 'Thu',
       '木曜日', 'Thursday', 'Jueves',
       NULL, '10005',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0002', '曜日', 'Weekday', '5', 'Fri',
       '金曜日', 'Friday', 'Viernes',
       NULL, '10006',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0002', '曜日', 'Weekday', '6', 'Sat',
       '土曜日', 'Saturday', 'Sábado',
       NULL, '10007',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));

/* ============================================================
   0003 : 第n週
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0003', '第n週', 'NthWeek', '1', 'First',
       '第1週', 'First Week', 'Primera semana',
       NULL, '10001',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0003', '第n週', 'NthWeek', '2', 'Second',
       '第2週', 'Second Week', 'Segunda semana',
       NULL, '10002',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0003', '第n週', 'NthWeek', '3', 'Third',
       '第3週', 'Third Week', 'Tercera semana',
       NULL, '10003',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0003', '第n週', 'NthWeek', '4', 'Fourth',
       '第4週', 'Fourth Week', 'Cuarta semana',
       NULL, '10004',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0003', '第n週', 'NthWeek', '5', 'Last',
       '最終週', 'Last Week', 'Última semana',
       NULL, '10005',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));

/* ============================================================
   0004 : カテゴリ
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0004', 'カテゴリ', 'Category', 'PET', 'ペット',
       'ペット関連', 'Pet', 'Mascotas',
       NULL, '10001',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0004', 'カテゴリ', 'Category', 'CLEAN', '掃除',
       '掃除', 'Cleaning', 'Limpieza',
       NULL, '10002',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0004', 'カテゴリ', 'Category', 'GARBAGE', 'ゴミ出し',
       'ゴミ出し', 'Garbage', 'Basura',
       NULL, '10003',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0004', 'カテゴリ', 'Category', 'GARDEN', '庭の手入れ',
       '庭の手入れ', 'Garden', 'Jardín',
       NULL, '10004',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0004', 'カテゴリ', 'Category', 'KITCHEN', 'キッチン関連',
       'キッチン関連', 'Kitchen', 'Cocina',
       NULL, '10005',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0004', 'カテゴリ', 'Category', 'OTHER', 'その他',
       'その他', 'Other', 'Otro',
       NULL, '10006',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));

/* ============================================================
   0005 : 家事タスクステータス
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0005', '家事タスクステータス', 'TaskStatus', '0', '未対応',
       '未対応', 'Not Done', 'No realizado',
       NULL, '10001',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0005', '家事タスクステータス', 'TaskStatus', '1', '完了',
       '完了', 'Done', 'Hecho',
       NULL, '10002',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0005', '家事タスクステータス', 'TaskStatus', '9', 'スキップ',
       'スキップ', 'Skipped', 'Saltado',
       NULL, '10003',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));

/* ============================================================
   0006 : 家事タスク割当理由
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0006', '家事タスク割当理由', 'TaskAssignReason', '0', '自発的',
       '自発的に担当', 'Self-assigned', 'Asignado por sí mismo',
       NULL, '10001',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0006', '家事タスク割当理由', 'TaskAssignReason', '1', 'お願い♡',
       'お願いされて担当', 'By Request', 'Por solicitud',
       NULL, '10002',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0006', '家事タスク割当理由', 'TaskAssignReason', '2', '押しつけ',
       '押しつけられた', 'Forced', 'Forzado',
       NULL, '10003',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0006', '家事タスク割当理由', 'TaskAssignReason', '9', 'システム',
       'システム割当', 'System Assigned', 'Asignado por sistema',
       NULL, '10004',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));

/* ============================================================
   0007 : 世帯メンバーステータス
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0007', '世帯メンバーステータス', 'HouseholdMemberStatus', '0', '招待中',
       '招待中', 'Invited', 'Invitado',
       NULL, '10001',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0007', '世帯メンバーステータス', 'HouseholdMemberStatus', '1', '有効',
       '有効', 'Active', 'Activo',
       NULL, '10002',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0007', '世帯メンバーステータス', 'HouseholdMemberStatus', '9', '離脱',
       '離脱', 'Left', 'Salió',
       NULL, '10003',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));

/* ============================================================
   0008 : 世帯招待ステータス
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0008', '世帯招待ステータス', 'InvitationStatus', '0', '招待中',
       '招待中', 'Pending', 'Pendiente',
       NULL, '10001',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0008', '世帯招待ステータス', 'InvitationStatus', '1', '承認済み',
       '承認済み', 'Accepted', 'Aceptado',
       NULL, '10002',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0008', '世帯招待ステータス', 'InvitationStatus', '7', '拒否',
       '拒否', 'Declined', 'Declined',
       NULL, '10003',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0008', '世帯招待ステータス', 'InvitationStatus', '8', '取り消し',
       '取り消し', 'Revoked', 'Revocado',
       NULL, '10004',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0008', '世帯招待ステータス', 'InvitationStatus', '9', '期限切れ',
       '期限切れ', 'Expired', 'Expirado',
       NULL, '10005',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));

/* ============================================================
   0009 : 買い物アイテムステータス
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0009', '買い物アイテムステータス', 'ShoppingItemStatus', '0', '未購入',
       '未購入', 'Not Purchased', 'No comprado',
       NULL, '10001',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0009', '買い物アイテムステータス', 'ShoppingItemStatus', '1', 'かご',
       'かご', 'In Basket', 'En el carrito',
       NULL, '10002',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0009', '買い物アイテムステータス', 'ShoppingItemStatus', '9', '購入済み',
       '購入済み', 'Purchased', 'Comprado',
       NULL, '10003',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));

/* ============================================================
   0010 : 購入場所種別
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0010', '購入場所種別', 'PurchaseLocationType', '1', 'スーパー',
       'スーパー', 'Supermarket', 'Supermercado',
       NULL, '10001',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0010', '購入場所種別', 'PurchaseLocationType', '2', 'オンライン',
       'オンライン', 'Online', 'En línea',
       NULL, '10002',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0010', '購入場所種別', 'PurchaseLocationType', '3', 'ドラッグストア',
       'ドラッグストア', 'Drugstore', 'Farmacia',
       NULL, '10003',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));

/* ============================================================
   0011 : お知らせステータス
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0011', 'お知らせステータス', 'NotificationStatus', '1', '有効',
       '有効', 'Active', 'Activo',
       NULL, '10001',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0011', 'お知らせステータス', 'NotificationStatus', '0', '無効',
       '無効', 'Inactive', 'Inactivo',
       NULL, '10002',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));

/* ============================================================
   0012 : プログラム種別
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0012', 'プログラム種別', 'ProgramType', 'SYSTEM', 'システム',
       'システム', 'SYSTEM', 'Sistema',
       NULL, '10001',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0012', 'プログラム種別', 'ProgramType', 'ADMIN', '管理者',
       '管理者プログラム', 'Admin', 'Administrador',
       NULL, '10002',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0012', 'プログラム種別', 'ProgramType', 'OnlAuth', 'ONL_AUTH',
       'ONL_AUTH', 'ONL_AUTH', 'ONL_AUTH',
       NULL, '20001',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0012', 'プログラム種別', 'ProgramType', 'OnlCode', 'ONL_CODE',
       'ONL_CODE', 'ONL_CODE', 'ONL_CODE',
       NULL, '20002',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0012', 'プログラム種別', 'ProgramType', 'OnlHldAuth', 'ONL_HLDAUTH',
       'ONL_HLDAUTH', 'ONL_HLDAUTH', 'ONL_HLDAUTH',
       NULL, '20003',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0012', 'プログラム種別', 'ProgramType', 'OnlHldInvi', 'ONL_HLDINVI',
       'ONL_HLDINVI', 'ONL_HLDINVI', 'ONL_HLDINVI',
       NULL, '20004',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0012', 'プログラム種別', 'ProgramType', 'OnlHldMem', 'ONL_HLDMEM',
       'ONL_HLDMEM', 'ONL_HLDMEM', 'ONL_HLDMEM',
       NULL, '20005',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0012', 'プログラム種別', 'ProgramType', 'OnlHld', 'ONL_HLD',
       'ONL_HLD', 'ONL_HLD', 'ONL_HLD',
       NULL, '20006',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0012', 'プログラム種別', 'ProgramType', 'OnlHwr', 'ONL_HWR',
       'ONL_HWR', 'ONL_HWR', 'ONL_HWR',
       NULL, '20007',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0012', 'プログラム種別', 'ProgramType', 'OnlHwrTsk', 'ONL_HWRTSK',
       'ONL_HWRTSK', 'ONL_HWRTSK', 'ONL_HWRTSK',
       NULL, '20008',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0012', 'プログラム種別', 'ProgramType', 'OnlShpAtch', 'ONL_SHPATCH',
       'ONL_SHPATCH', 'ONL_SHPATCH', 'ONL_SHPATCH',
       NULL, '20009',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0012', 'プログラム種別', 'ProgramType', 'OnlShp', 'ONL_SHP',
       'ONL_SHP', 'ONL_SHP', 'ONL_SHP',
       NULL, '20010',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0012', 'プログラム種別', 'ProgramType', 'OnlUsrIcon', 'ONL_USRICON',
       'ONL_USRICON', 'ONL_USRICON', 'ONL_USRICON',
       NULL, '20011',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0012', 'プログラム種別', 'ProgramType', 'OnlUsr', 'ONL_USR',
       'ONL_USR', 'ONL_USR', 'ONL_USR',
       NULL, '20012',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0012', 'プログラム種別', 'ProgramType', 'BtcInvExpr', 'BTC_INV_EXPR',
       'BTC_INV_EXPR', 'BTC_INV_EXPR', 'BTC_INV_EXPR',
       NULL, '20012',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0012', 'プログラム種別', 'ProgramType', 'BtcTskGen', 'BTC_TSK_GEN',
       'BTC_TSK_GEN', 'BTC_TSK_GEN', 'BTC_TSK_GEN',
       NULL, '20012',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6)),
      ('0012', 'プログラム種別', 'ProgramType', 'BtcTskRecl', 'BTC_TSK_RECL',
       'BTC_TSK_RECL', 'BTC_TSK_RECL', 'BTC_TSK_RECL',
       NULL, '20012',
       1, 'INIT_DATA', NOW(6),
       1, 'INIT_DATA', NOW(6));

/* ============================================================
   0013 : お気に入りフラグ
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0013', 'お気に入りフラグ', 'FavoriteFlag', '0',
       'Normal', '通常', 'Normal', 'Normal',
       NULL, '10001', 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),

      ('0013', 'お気に入りフラグ', 'FavoriteFlag', '1',
       'Favorite', 'お気に入り', 'Favorite', 'Favorito',
       NULL, '10002', 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6));

/* ============================================================
   0014 : 家事タスク再計算ステータス
   ============================================================ */
INSERT INTO m_code (
    code_type, code_type_name, code_type_name_en, code_value, name,
    display_name_ja, display_name_en, display_name_es,
    remarks, display_order,
    create_user_id, create_program, created_at,
    update_user_id, update_program, updated_at
) VALUES
      ('0014', '家事タスク再計算ステータス', 'TaskRecalcStatus', '0',
       'PENDING', 'PENDING', 'PENDING', 'PENDING',
       NULL, '10001', 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
      ('0014', '家事タスク再計算ステータス', 'TaskRecalcStatus', '1',
       'PROCESSING', 'PROCESSING', 'PROCESSING', 'PROCESSING',
       NULL, '10001', 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
      ('0014', '家事タスク再計算ステータス', 'TaskRecalcStatus', '2',
       'DONE', 'DONE', 'DONE', 'DONE',
       NULL, '10001', 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
      ('0014', '家事タスク再計算ステータス', 'TaskRecalcStatus', '9',
       'FAILED', 'FAILED', 'FAILED', 'FAILED',
       NULL, '10001', 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6));

