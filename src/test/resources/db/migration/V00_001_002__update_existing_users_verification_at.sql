/* ============================================================
   V00_001_002 : Update existing users verification at
   ============================================================ */
UPDATE m_user 
SET
    email_verified_at = now(6)
    , update_user_id = 1
    , update_program = 'DATA_PATCH'
    , updated_at = now(6) 
WHERE
    email_verified_at IS NULL;