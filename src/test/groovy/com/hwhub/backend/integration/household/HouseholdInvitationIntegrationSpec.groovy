package com.hwhub.backend.integration.household

import com.hwhub.backend.integration.IntegrationTestBase
import org.springframework.http.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class HouseholdInvitationIntegrationSpec extends IntegrationTestBase {

    Long inviterUserId
    Long inviteeUserId
    Long testHouseholdId

    def setup() {
        // 1. 招待者ユーザー（世帯オーナー）をINSERT
        jdbcTemplate.update("""
            INSERT INTO m_user
              (email, password_hash, auth_provider, display_name, locale,
               notification_enabled, is_active,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('invitation-inviter@example.com', 'dummy-hash', 'LOCAL', '招待者ユーザー', 'ja',
               true, true,
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """)
        inviterUserId = jdbcTemplate.queryForObject(
                "SELECT user_id FROM m_user WHERE email = 'invitation-inviter@example.com'",
                Long.class
        )

        // 2. 世帯をINSERT
        jdbcTemplate.update("""
            INSERT INTO m_household
              (name, owner_user_id,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('招待テスト世帯', ?,
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, inviterUserId)
        testHouseholdId = jdbcTemplate.queryForObject(
                "SELECT household_id FROM m_household WHERE owner_user_id = ?",
                Long.class,
                inviterUserId
        )

        // 3. 世帯メンバーをINSERT（招待者を世帯に紐付け）
        jdbcTemplate.update("""
            INSERT INTO m_household_member
              (household_id, user_id, nickname, status,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              (?, ?, '招待者ユーザー', '1',
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, testHouseholdId, inviterUserId)

        // 4. 被招待者ユーザーをINSERT
        jdbcTemplate.update("""
            INSERT INTO m_user
              (email, password_hash, auth_provider, display_name, locale,
               notification_enabled, is_active,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('invitation-invitee@example.com', 'dummy-hash', 'LOCAL', '被招待者ユーザー', 'ja',
               true, true,
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """)
        inviteeUserId = jdbcTemplate.queryForObject(
                "SELECT user_id FROM m_user WHERE email = 'invitation-invitee@example.com'",
                Long.class
        )
    }

    def cleanup() {
        if (testHouseholdId != null) {
            jdbcTemplate.update("DELETE FROM t_household_invitation WHERE household_id = ?", testHouseholdId)
            jdbcTemplate.update("DELETE FROM m_household_member WHERE household_id = ?", testHouseholdId)
            jdbcTemplate.update("DELETE FROM m_household WHERE household_id = ?", testHouseholdId)
        }
        jdbcTemplate.update("DELETE FROM m_user WHERE email IN ('invitation-inviter@example.com', 'invitation-invitee@example.com')")
    }

    def "POST /api/households/{householdId}/invitations - 正常系: 招待者のトークンで認証して招待を作成するとステータス200が返ること"() {
        given:
        def token = tokenFor(inviterUserId)
        def requestBody = [invitedEmail: "invitation-invitee@example.com"]

        when:
        def result = mockMvc.perform(
                post("/api/households/${testHouseholdId}/invitations")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then: "ステータス200が返ること"
        result.andExpect(status().isOk())

        and: "DBにinvitationが1件登録されていること"
        def count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM t_household_invitation WHERE household_id = ?",
                Integer.class,
                testHouseholdId
        )
        count == 1
    }

    def "GET /api/household-invitations/{token} - 正常系: Authorizationヘッダーなしでステータス200が返ること"() {
        given: "invitationを1件INSERTしておく"
        def invitationToken = UUID.randomUUID().toString()
        jdbcTemplate.update("""
            INSERT INTO t_household_invitation
              (invitation_token, household_id, inviter_user_id, invited_email, status, expires_at,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              (?, ?, ?, 'invitation-invitee@example.com', '0', DATE_ADD(NOW(), INTERVAL 7 DAY),
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, invitationToken, testHouseholdId, inviterUserId)

        when:
        def result = mockMvc.perform(
                get("/api/household-invitations/${invitationToken}")
        )

        then: "ステータス200が返ること"
        result.andExpect(status().isOk())
    }

    def "POST /api/household-invitations/{token}/accept - 正常系: 被招待者が承諾するとステータス204が返り世帯メンバーが2件になること"() {
        given: "invitationを1件INSERTしておく"
        def invitationToken = UUID.randomUUID().toString()
        jdbcTemplate.update("""
            INSERT INTO t_household_invitation
              (invitation_token, household_id, inviter_user_id, invited_email, status, expires_at,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              (?, ?, ?, 'invitation-invitee@example.com', '0', DATE_ADD(NOW(), INTERVAL 7 DAY),
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, invitationToken, testHouseholdId, inviterUserId)

        def token = tokenFor(inviteeUserId)

        when:
        def result = mockMvc.perform(
                post("/api/household-invitations/${invitationToken}/accept")
                        .header("Authorization", "Bearer ${token}")
        )

        then: "ステータス204が返ること"
        result.andExpect(status().isNoContent())

        and: "DBの世帯メンバーが2件になっていること"
        def memberCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM m_household_member WHERE household_id = ?",
                Integer.class,
                testHouseholdId
        )
        memberCount == 2
    }

    def "POST /api/household-invitations/{token}/decline - 正常系: 被招待者が辞退するとステータス204が返りinvitationのstatusが辞退済みになること"() {
        given: "invitationを1件INSERTしておく"
        def invitationToken = UUID.randomUUID().toString()
        jdbcTemplate.update("""
            INSERT INTO t_household_invitation
              (invitation_token, household_id, inviter_user_id, invited_email, status, expires_at,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              (?, ?, ?, 'invitation-invitee@example.com', '0', DATE_ADD(NOW(), INTERVAL 7 DAY),
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, invitationToken, testHouseholdId, inviterUserId)

        def token = tokenFor(inviteeUserId)

        when:
        def result = mockMvc.perform(
                post("/api/household-invitations/${invitationToken}/decline")
                        .header("Authorization", "Bearer ${token}")
        )

        then: "ステータス204が返ること"
        result.andExpect(status().isNoContent())

        and: "DBのinvitationのstatusが辞退済み（'7'）になっていること"
        def invStatus = jdbcTemplate.queryForObject(
                "SELECT status FROM t_household_invitation WHERE invitation_token = ?",
                String.class,
                invitationToken
        )
        invStatus == "7"
    }
}
