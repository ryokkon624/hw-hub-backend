/* ============================================================
   V00_001_006__create_notification_tables.sql
   ============================================================ */

-- ============================================================
-- t_notification : 通知センターに表示する「集約済み通知」
-- ============================================================
CREATE TABLE t_notification( 
    notification_id bigint NOT NULL auto_increment COMMENT '通知ID'
    , household_id bigint NULL COMMENT '世帯ID:個人通知の場合NULL可'
    , notification_type VARCHAR (32) NOT NULL COMMENT '通知種別コード:m_code：0017'
    , actor_user_id bigint NULL COMMENT '操作ユーザーID:システム起因はNULL'
    , target_user_id bigint NOT NULL COMMENT '通知先ユーザーID'
    , is_read tinyint(1) NOT NULL DEFAULT 0 COMMENT '既読フラグ'
    , read_at datetime(6) NULL COMMENT '既読日時'
    , title_key VARCHAR (200) NULL COMMENT 'i18n title key'
    , body_key VARCHAR (200) NOT NULL COMMENT 'i18n body key'
    , params_json json NULL COMMENT 'i18n params JSON'
    , link_type VARCHAR (32) NULL COMMENT '遷移先種別コード:m_code：0019'
    , link_id bigint NULL COMMENT '遷移先ID:種別により解釈'
    , occurred_at datetime(6) NOT NULL COMMENT '通知発生日時（表示順）'
    , aggregated_key VARCHAR (255) NULL COMMENT '集約キー（バッチ集約用）'
    , aggregated_count INT NOT NULL DEFAULT 1 COMMENT '集約件数'
    , create_user_id bigint unsigned NOT NULL COMMENT '作成者ユーザID'
    , create_program VARCHAR (10) NOT NULL COMMENT '作成プログラム:m_code：0012'
    , created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '作成日時'
    , update_user_id bigint unsigned NOT NULL COMMENT '更新者ユーザID'
    , update_program VARCHAR (10) NOT NULL COMMENT '更新プログラム:m_code：0012'
    , updated_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) 
        ON UPDATE CURRENT_TIMESTAMP (6) COMMENT '更新日時'
    , PRIMARY KEY (notification_id)
    , KEY idx_notif_target_read_occurred( 
        target_user_id
        , is_read
        , occurred_at
        , notification_id
    ) 
    , KEY idx_notif_target_occurred(target_user_id, occurred_at, notification_id)
    , KEY idx_notif_household_occurred(household_id, occurred_at, notification_id)
    , KEY idx_notif_aggregated_key(aggregated_key)
    , KEY idx_notif_user_unread(target_user_id, is_read)
) engine = innodb DEFAULT charset = utf8mb4 COLLATE = utf8mb4_ja_0900_as_cs COMMENT = '通知（表示用・集約済み）'
;

-- ============================================================
-- t_notification_event : 集約前の「生イベント」（タスク割当など大量発生用）
-- ============================================================
CREATE TABLE t_notification_event( 
    notification_event_id bigint NOT NULL auto_increment COMMENT '通知イベントID'
    , household_id bigint NULL COMMENT '世帯ID（個人スコープ通知にも備えてNULL許容）'
    , notification_type VARCHAR (20) NOT NULL COMMENT '通知種別コード:m_code：0017'
    , actor_user_id bigint NULL COMMENT '操作ユーザーID'
    , target_user_id bigint NOT NULL COMMENT '通知先ユーザーID'
    , entity_id bigint NOT NULL COMMENT '対象エンティティのID'
    , aggregation_date DATE NOT NULL COMMENT '集約日付'
    , occurred_at datetime(6) NOT NULL COMMENT 'イベント発生時刻'
    , event_status VARCHAR (20) NOT NULL COMMENT 'イベント処理状態:m_code：0018'
    , processing_key VARCHAR (36) NULL COMMENT '処理キー（UUID）'
    , processing_started_at datetime(6) NULL COMMENT '処理開始時刻'
    , processed_at datetime(6) NULL COMMENT '処理完了時刻'
    , create_user_id bigint unsigned NOT NULL COMMENT '作成者ユーザID'
    , create_program VARCHAR (10) NOT NULL COMMENT '作成プログラム:m_code：0012'
    , created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '作成日時'
    , update_user_id bigint unsigned NOT NULL COMMENT '更新者ユーザID'
    , update_program VARCHAR (10) NOT NULL COMMENT '更新プログラム:m_code：0012'
    , updated_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) 
        ON UPDATE CURRENT_TIMESTAMP (6) COMMENT '更新日時'
    , PRIMARY KEY (notification_event_id)
    , KEY idx_ne_status_date_type_target_occ( 
        event_status
        , aggregation_date
        , notification_type
        , target_user_id
        , occurred_at
        , notification_event_id
    ) 
    , KEY idx_ne_task_occ(entity_id, occurred_at, notification_event_id)
    , KEY idx_ne_processing_key(processing_key, notification_event_id)
    , KEY idx_ne_target_type_date_occ( 
        target_user_id
        , notification_type
        , aggregation_date
        , occurred_at
        , notification_event_id
    )
) engine = innodb DEFAULT charset = utf8mb4 COLLATE = utf8mb4_ja_0900_as_cs COMMENT = '通知イベント'
;
