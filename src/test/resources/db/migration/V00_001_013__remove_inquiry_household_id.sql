-- ------------------------------------------------------------
-- V00_001_013__remove_inquiry_household_id.sql
--   問い合わせテーブルから household_id を削除
-- ------------------------------------------------------------

ALTER TABLE t_inquiry DROP COLUMN household_id;