package com.hwhub.backend.integration.notification

import com.hwhub.backend.integration.IntegrationTestBase

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class NotificationIntegrationSpec extends IntegrationTestBase {

    Long testUserId
    Long testHouseholdId

    def setup() {
        // 1. ユーザーをINSERT（notification_enabled=trueにしないとサービスが空リストを返す）
        jdbcTemplate.update("""
            INSERT INTO m_user
              (email, password_hash, auth_provider, display_name, locale,
               notification_enabled, is_active,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('notification-test@example.com', 'dummy-hash', 'LOCAL', '通知テストユーザー', 'ja',
               true, true,
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """)
        testUserId = jdbcTemplate.queryForObject(
                "SELECT user_id FROM m_user WHERE email = 'notification-test@example.com'",
                Long.class
        )

        // 2. 世帯をINSERT
        jdbcTemplate.update("""
            INSERT INTO m_household
              (name, owner_user_id,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('通知テスト世帯', ?,
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, testUserId)
        testHouseholdId = jdbcTemplate.queryForObject(
                "SELECT household_id FROM m_household WHERE owner_user_id = ?",
                Long.class,
                testUserId
        )

        // 3. 世帯メンバーをINSERT
        jdbcTemplate.update("""
            INSERT INTO m_household_member
              (household_id, user_id, nickname, status,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              (?, ?, '通知テストユーザー', '1',
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, testHouseholdId, testUserId)

        // 4. notification2件をINSERT
        //    既読1件（is_read=1, read_at=非NULL）
        //    未読1件（is_read=0, read_at=NULL）
        //    notification_type: '0101' (INVITATION_ACCEPTED)
        //    link_type: 'None' (NONE) — fromCode()が例外を投げないように有効値を使う
        jdbcTemplate.update("""
            INSERT INTO t_notification
              (household_id, notification_type, actor_user_id, target_user_id,
               is_read, read_at,
               body_key, link_type, occurred_at, aggregated_count,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              (?, '0101', ?, ?, 1, NOW(), 'notification.body.invitation.accepted', 'None', NOW(), 1, 1, 'IT-data', NOW(), 1, 'IT-data', NOW()),
              (?, '0101', ?, ?, 0, NULL, 'notification.body.invitation.accepted', 'None', NOW(), 1, 1, 'IT-data', NOW(), 1, 'IT-data', NOW())
        """,
                testHouseholdId, testUserId, testUserId,
                testHouseholdId, testUserId, testUserId
        )
    }

    def cleanup() {
        if (testUserId != null) {
            jdbcTemplate.update("DELETE FROM t_notification WHERE target_user_id = ?", testUserId)
        }
        if (testHouseholdId != null) {
            jdbcTemplate.update("DELETE FROM m_household_member WHERE household_id = ?", testHouseholdId)
            jdbcTemplate.update("DELETE FROM m_household WHERE household_id = ?", testHouseholdId)
        }
        jdbcTemplate.update("DELETE FROM m_user WHERE email = 'notification-test@example.com'")
    }

    def "GET /api/notifications - 正常系: ステータス200が返りレスポンスBodyが2件の配列であること"() {
        given:
        def token = tokenFor(testUserId)

        when:
        def result = mockMvc.perform(
                get("/api/notifications")
                        .param("markRead", "false")
                        .header("Authorization", "Bearer ${token}")
        )

        then: "ステータス200が返ること"
        result.andExpect(status().isOk())

        and: "レスポンスBodyが配列でサイズ2であること"
        result.andExpect(jsonPath('$.items').isArray())
        result.andExpect(jsonPath('$.items.length()').value(2))
    }

    def "GET /api/notifications/unread-count - 正常系: ステータス200が返りunreadCountが1であること"() {
        given:
        def token = tokenFor(testUserId)

        when:
        def result = mockMvc.perform(
                get("/api/notifications/unread-count")
                        .header("Authorization", "Bearer ${token}")
        )

        then: "ステータス200が返ること"
        result.andExpect(status().isOk())

        and: "unreadCountが1であること"
        result.andExpect(jsonPath('$.unreadCount').value(1))
    }

    def "GET /api/notifications - 異常系: 認証トークンなしでGETするとステータス401が返ること"() {
        when:
        def result = mockMvc.perform(
                get("/api/notifications")
        )

        then:
        result.andExpect(status().isUnauthorized())
    }
}
