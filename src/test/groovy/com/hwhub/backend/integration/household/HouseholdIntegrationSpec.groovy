package com.hwhub.backend.integration.household

import com.hwhub.backend.integration.IntegrationTestBase
import org.springframework.http.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class HouseholdIntegrationSpec extends IntegrationTestBase {

    def cleanup() {
        jdbcTemplate.update("""
            DELETE FROM m_household_member WHERE user_id IN (
                SELECT user_id FROM m_user WHERE email = 'household-test@example.com'
            )
        """)
        jdbcTemplate.update("""
            DELETE FROM m_household WHERE owner_user_id IN (
                SELECT user_id FROM m_user WHERE email = 'household-test@example.com'
            )
        """)
        jdbcTemplate.update("DELETE FROM m_user WHERE email = 'household-test@example.com'")
    }

    def "POST /api/households - 正常系: 認証済みユーザーが世帯名を渡してステータス200が返ること"() {
        given: "ユーザーをDBに登録してJWTトークンを生成する"
        jdbcTemplate.update("""
            INSERT INTO m_user
              (email, password_hash, auth_provider, display_name, locale,
               notification_enabled, is_active,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('household-test@example.com', 'dummy-hash', 'LOCAL', '世帯テストユーザー', 'ja',
               true, true,
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """)
        def userId = jdbcTemplate.queryForObject(
                "SELECT user_id FROM m_user WHERE email = 'household-test@example.com'",
                Long.class
        )
        def token = tokenFor(userId)

        def requestBody = [name: "テスト世帯"]

        when:
        def result = mockMvc.perform(
                post("/api/households")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then: "ステータス200が返ること"
        result.andExpect(status().isOk())

        and: "DBにhouseholdが1件登録されていること"
        def count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM m_household WHERE owner_user_id = ?",
                Integer.class,
                userId
        )
        count == 1
    }

    def "POST /api/households - 異常系: 認証トークンなしでPOSTするとステータス401が返ること"() {
        given:
        def requestBody = [name: "テスト世帯"]

        when:
        def result = mockMvc.perform(
                post("/api/households")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then:
        result.andExpect(status().isUnauthorized())
    }
}
