package com.hwhub.backend.integration.housework

import com.hwhub.backend.integration.IntegrationTestBase
import org.springframework.http.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class HouseworkIntegrationSpec extends IntegrationTestBase {

    Long testUserId
    Long testHouseholdId

    def setup() {
        // 1. ユーザーをINSERT
        jdbcTemplate.update("""
            INSERT INTO m_user
              (email, password_hash, auth_provider, display_name, locale,
               notification_enabled, is_active,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('housework-test@example.com', 'dummy-hash', 'LOCAL', '家事テストユーザー', 'ja',
               true, true,
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """)
        testUserId = jdbcTemplate.queryForObject(
                "SELECT user_id FROM m_user WHERE email = 'housework-test@example.com'",
                Long.class
        )

        // 2. 世帯をINSERT
        jdbcTemplate.update("""
            INSERT INTO m_household
              (name, owner_user_id,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('家事テスト世帯', ?,
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
              (?, ?, '家事テストユーザー', '1',
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, testHouseholdId, testUserId)
    }

    def cleanup() {
        if (testHouseholdId != null) {
            jdbcTemplate.update("DELETE FROM m_housework WHERE household_id = ?", testHouseholdId)
            jdbcTemplate.update("DELETE FROM m_household_member WHERE household_id = ?", testHouseholdId)
            jdbcTemplate.update("DELETE FROM m_household WHERE household_id = ?", testHouseholdId)
        }
        jdbcTemplate.update("DELETE FROM m_user WHERE email = 'housework-test@example.com'")
    }

    def "POST /api/houseworks - 正常系: 必須項目を渡してステータス201が返ること"() {
        given:
        def token = tokenFor(testUserId)
        def requestBody = [
                householdId   : testHouseholdId,
                name          : "テスト家事",
                category      : "CLEAN",
                recurrenceType: "1",
                weeklyDays    : 1,
                startDate     : "2025-01-01",
                endDate       : "2025-12-31"
        ]

        when:
        def result = mockMvc.perform(
                post("/api/houseworks")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then: "ステータス201が返ること"
        result.andExpect(status().isCreated())

        and: "DBにhouseworkが1件登録されていること"
        def count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM m_housework WHERE household_id = ?",
                Integer.class,
                testHouseholdId
        )
        count == 1
    }

    def "GET /api/houseworks - 正常系: houseworkが存在するときステータス200と1件以上の配列が返ること"() {
        given: "家事を1件INSERTしておく"
        jdbcTemplate.update("""
            INSERT INTO m_housework
              (household_id, name, category, recurrence_type, weekly_days,
               start_date, end_date,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              (?, 'テスト家事', 'CLEAN', '1', 1,
               '2025-01-01', '2025-12-31',
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, testHouseholdId)

        def token = tokenFor(testUserId)

        when:
        def result = mockMvc.perform(
                get("/api/houseworks")
                        .param("householdId", testHouseholdId.toString())
                        .header("Authorization", "Bearer ${token}")
        )

        then: "ステータス200が返ること"
        result.andExpect(status().isOk())

        and: "レスポンスBodyが配列でサイズ1以上であること"
        result.andExpect(jsonPath('$').isArray())
        result.andExpect(jsonPath('$.length()').value(org.hamcrest.Matchers.greaterThanOrEqualTo(1)))
    }

    def "POST /api/houseworks - 異常系: 認証トークンなしでPOSTするとステータス401が返ること"() {
        given:
        def requestBody = [
                householdId   : testHouseholdId,
                name          : "テスト家事",
                category      : "CLEAN",
                recurrenceType: "1",
                weeklyDays    : 1,
                startDate     : "2025-01-01",
                endDate       : "2025-12-31"
        ]

        when:
        def result = mockMvc.perform(
                post("/api/houseworks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then:
        result.andExpect(status().isUnauthorized())
    }
}
