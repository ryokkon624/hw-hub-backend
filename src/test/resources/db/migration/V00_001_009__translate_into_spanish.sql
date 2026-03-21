/* ============================================================
   V00_001_009__translate_into_spanish.sql
   ============================================================ */

/* ============================================================
   0013 : お気に入りフラグ
   ============================================================ */
UPDATE m_code SET name = '通常', display_name_ja = '通常', display_name_en = 'Normal', display_name_es = 'Normal', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0013' AND code_value = '0';
UPDATE m_code SET name = 'お気に入り', display_name_ja = 'お気に入り', display_name_en = 'Favorite', display_name_es = 'Favorito', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0013' AND code_value = '1';

/* ============================================================
   0014 : 家事タスク再計算ステータス
   ============================================================ */
UPDATE m_code SET name = '保留中', display_name_ja = '保留中', display_name_en = 'Pending', display_name_es = 'Pendiente', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0014' AND code_value = '0';
UPDATE m_code SET name = '処理中', display_name_ja = '処理中', display_name_en = 'Processing', display_name_es = 'Procesando', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0014' AND code_value = '1';
UPDATE m_code SET name = '完了', display_name_ja = '完了', display_name_en = 'Done', display_name_es = 'Completado', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0014' AND code_value = '2';
UPDATE m_code SET name = '失敗', display_name_ja = '失敗', display_name_en = 'Failed', display_name_es = 'Fallido', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0014' AND code_value = '9';

/* ============================================================
   0015 : 認証プロバイダー
   ============================================================ */
UPDATE m_code SET name = 'Google', display_name_ja = 'Google', display_name_en = 'Google', display_name_es = 'Google', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0015' AND code_value = 'GOOGLE';
UPDATE m_code SET name = 'Local', display_name_ja = 'Local', display_name_en = 'Local', display_name_es = 'Local', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0015' AND code_value = 'LOCAL';

/* ============================================================
   0016 : OAuth Flow
   ============================================================ */
UPDATE m_code SET name = 'Link', display_name_ja = 'Link', display_name_en = 'Link', display_name_es = 'Link', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0016' AND code_value = 'LINK';
UPDATE m_code SET name = 'Login', display_name_ja = 'Login', display_name_en = 'Login', display_name_es = 'Login', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0016' AND code_value = 'LOGIN';

/* ============================================================
   0017 : 通知種別
   ============================================================ */
UPDATE m_code SET display_name_en = 'Invitation accepted', display_name_es = 'Invitación aceptada', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0017' AND code_value = '0101';
UPDATE m_code SET display_name_en = 'Invitation declined', display_name_es = 'Invitación rechazada', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0017' AND code_value = '0102';
UPDATE m_code SET display_name_en = 'Have been removed', display_name_es = 'Has sido eliminado', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0017' AND code_value = '0201';
UPDATE m_code SET display_name_en = 'Left the household', display_name_es = 'Abandonó el hogar', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0017' AND code_value = '0202';
UPDATE m_code SET display_name_en = 'Assigned to the owner', display_name_es = 'Asignado al propietario', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0017' AND code_value = '0203';
UPDATE m_code SET display_name_en = 'Task assigned', display_name_es = 'Tarea asignada', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0017' AND code_value = '0301';
UPDATE m_code SET display_name_en = 'Be dumped task', display_name_es = 'Tarea descartada', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0017' AND code_value = '0302';
UPDATE m_code SET display_name_en = 'Your task was taken', display_name_es = 'Tu tarea fue tomada', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0017' AND code_value = '0303';

/* ============================================================
   0018 : イベントステータス
   ============================================================ */
UPDATE m_code SET name = '保留中', display_name_ja = '保留中', display_name_en = 'Pending', display_name_es = 'Pendiente', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0018' AND code_value = '0';
UPDATE m_code SET name = '処理中', display_name_ja = '処理中', display_name_en = 'Processing', display_name_es = 'Procesando', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0018' AND code_value = '1';
UPDATE m_code SET name = '完了', display_name_ja = '完了', display_name_en = 'Done', display_name_es = 'Completado', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0018' AND code_value = '2';

/* ============================================================
   0019 : 通知リンク種別
   ============================================================ */
UPDATE m_code SET name = 'おうち設定', display_name_ja = 'おうち設定', display_name_en = 'Household', display_name_es = 'Hogar', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0019' AND code_value = 'Household';
UPDATE m_code SET name = '招待', display_name_ja = '招待', display_name_en = 'Invitation', display_name_es = 'Invitación', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0019' AND code_value = 'Invite';
UPDATE m_code SET name = 'My Tasks', display_name_ja = 'My Tasks', display_name_en = 'My Tasks', display_name_es = 'Mis tareas', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0019' AND code_value = 'MyTasks';
UPDATE m_code SET name = 'なし', display_name_ja = 'なし', display_name_en = 'None', display_name_es = 'Ninguno', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0019' AND code_value = 'None';
UPDATE m_code SET name = '設定', display_name_ja = '設定', display_name_en = 'Settings', display_name_es = 'Configuración', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0019' AND code_value = 'Settings';

/* ============================================================
   0020 : 通知グループ
   ============================================================ */
UPDATE m_code SET name = 'おうち関連', display_name_ja = 'おうち関連', display_name_en = 'Household', display_name_es = 'Hogar', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0020' AND code_value = '100';
UPDATE m_code SET name = 'タスク割当', display_name_ja = 'タスク割当', display_name_en = 'Task assignment', display_name_es = 'Asignación de tareas', update_program = 'SYSTEM', updated_at = now(6) WHERE code_type = '0020' AND code_value = '200';
