-- ------------------------------------------------------------
-- V00_000_004__insert_housework_template.sql
--   HouseworkHub: 家事テンプレート初期データ投入
-- ------------------------------------------------------------

INSERT INTO m_housework_template (
    name_ja,
    name_en,
    name_es,
    description_ja,
    description_en,
    description_es,
    recommendation_ja,
    recommendation_en,
    recommendation_es,
    category,
    recurrence_type,
    weekly_days,
    day_of_month,
    nth_week,
    weekday,
    create_user_id,
    create_program,
    created_at,
    update_user_id,
    update_program,
    updated_at
) VALUES
/* 1. トイレ掃除（毎週月・木） */
      ('トイレ掃除', 'Toilet cleaning', 'Limpieza del baño', NULL, NULL, NULL, '燃えるごみの前の日がおすすめ', 'Recommended on the day before burnable garbage day', 'Recomendado el día antes de la basura quemable', 'CLEAN', '1', 18, NULL, NULL, NULL, 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
/* 2. 加湿器の掃除（毎週土） */
      ('加湿器の掃除', 'Humidifier cleaning', 'Limpieza del humidificador', NULL, NULL, NULL, NULL, NULL, NULL, 'CLEAN', '1', 64, NULL, NULL, NULL, 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
/* 3. ゴミ捨て(段ボール)（毎月4日） */
      ('ゴミ捨て(段ボール)', 'Trash (cardboard)', 'Basura (cartón)', NULL, NULL, NULL, NULL, NULL, NULL, 'GARBAGE', '2', NULL, 4, NULL, NULL, 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
/* 4. ゴミ捨て(不燃ごみ)（毎月第4木曜） */
      ('ゴミ捨て(不燃ごみ)', 'Trash (non-burnable)', 'Basura (no quemable)', NULL, NULL, NULL, NULL, NULL, NULL, 'GARBAGE', '3', NULL, NULL, 4, 4, 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
/* 5. シーツ交換（毎週土） */
      ('シーツ交換', 'Change bed sheets', 'Cambio de sábanas', NULL, NULL, NULL, '部屋別に交換、選択しているなら2つに分けてみてね', 'You can split by room or by washing batches', 'Puedes dividir por habitación o por tandas de lavado', 'CLEAN', '1', 64, NULL, NULL, NULL, 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
/* 6. 玄関掃除（毎月最終日曜） */
      ('玄関掃除', 'Entrance cleaning', 'Limpieza de la entrada', NULL, NULL, NULL, NULL, NULL, NULL, 'CLEAN', '3', NULL, NULL, 5, 0, 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
/* 7. 猫トイレの掃除（毎週月・木） */
      ('猫トイレの掃除', 'Cat litter box cleaning', 'Limpieza del arenero del gato', NULL, NULL, NULL, '燃えるごみの前の日がおすすめ', 'Recommended on the day before burnable garbage day', 'Recomendado el día antes de la basura quemable', 'PET', '1', 18, NULL, NULL, NULL, 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
/* 8. 猫トイレの砂交換（毎月最終日曜） */
      ('猫トイレの砂交換', 'Cat litter sand replacement', 'Cambio de arena del gato', NULL, NULL, NULL, '猫砂が固まるタイプだったら月の前半後半で家事を2つに分けて登録するのがおすすめ', 'If clumping litter, consider two tasks for early/late month', 'Si la arena es aglutinante, considera dos tareas a mitad y fin de mes', 'PET', '3', NULL, NULL, 5, 0, 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6)),
/* 9. 猫のお風呂（毎月最終日曜） */
      ('猫のお風呂', 'Cat bath', 'Baño del gato', NULL, NULL, NULL, NULL, NULL, NULL, 'PET', '3', NULL, NULL, 5, 0, 1, 'INIT_DATA', NOW(6), 1, 'INIT_DATA', NOW(6));

