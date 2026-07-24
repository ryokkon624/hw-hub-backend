package com.hwhub.backend.integration.housework

import com.hwhub.backend.integration.IntegrationTestBase
import org.springframework.http.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
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

    def "POST /api/houseworks - 異常系: 呼び出し元が所属しない世帯のhouseholdIdを指定するとステータス403が返り家事が作成されないこと（越境作成拒否）"() {
        given: "別ユーザーが所有する別世帯を作成する（testUserは所属していない）"
        jdbcTemplate.update("""
            INSERT INTO m_user
              (email, password_hash, auth_provider, display_name, locale,
               notification_enabled, is_active,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('housework-other@example.com', 'dummy-hash', 'LOCAL', '別ユーザー', 'ja',
               true, true,
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """)
        def otherUserId = jdbcTemplate.queryForObject(
                "SELECT user_id FROM m_user WHERE email = 'housework-other@example.com'",
                Long.class
        )

        jdbcTemplate.update("""
            INSERT INTO m_household
              (name, owner_user_id,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('別世帯', ?,
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, otherUserId)
        def otherHouseholdId = jdbcTemplate.queryForObject(
                "SELECT household_id FROM m_household WHERE owner_user_id = ?",
                Long.class,
                otherUserId
        )

        and: "testUser（別世帯に属していない）のトークンで、別世帯のhouseholdIdを指定して送る"
        def token = tokenFor(testUserId)
        def requestBody = [
                householdId   : otherHouseholdId,
                name          : "越境作成テスト",
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

        then: "ステータス403が返ること"
        result.andExpect(status().isForbidden())

        and: "DBに家事が作成されていないこと"
        def count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM m_housework WHERE household_id = ?",
                Integer.class,
                otherHouseholdId
        )
        count == 0

        cleanup:
        jdbcTemplate.update("DELETE FROM m_housework WHERE household_id = ?", otherHouseholdId)
        jdbcTemplate.update("DELETE FROM m_household WHERE household_id = ?", otherHouseholdId)
        jdbcTemplate.update("DELETE FROM m_user WHERE user_id = ?", otherUserId)
    }

    def "PUT /api/houseworks/{houseworkId} - 正常系: 自世帯の家事を更新するとステータス200が返り内容が更新されること"() {
        given: "家事を1件INSERTしておく"
        jdbcTemplate.update("""
            INSERT INTO m_housework
              (household_id, name, category, recurrence_type, weekly_days,
               start_date, end_date,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              (?, '更新前家事', 'CLEAN', '1', 1,
               '2025-01-01', '2025-12-31',
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, testHouseholdId)
        def houseworkId = jdbcTemplate.queryForObject(
                "SELECT housework_id FROM m_housework WHERE household_id = ? AND name = '更新前家事'",
                Long.class,
                testHouseholdId
        )

        def token = tokenFor(testUserId)
        def requestBody = [
                householdId   : testHouseholdId,
                name          : "更新後家事",
                category      : "CLEAN",
                recurrenceType: "1",
                weeklyDays    : 2,
                startDate     : "2025-02-01",
                endDate       : "2025-11-30"
        ]

        when:
        def result = mockMvc.perform(
                put("/api/houseworks/${houseworkId}")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then: "ステータス200が返ること"
        result.andExpect(status().isOk())

        and: "DBのname/weekly_daysカラムが更新されていること"
        def updated = jdbcTemplate.queryForMap(
                "SELECT name, weekly_days FROM m_housework WHERE housework_id = ?",
                houseworkId
        )
        updated.name == "更新後家事"
        updated.weekly_days == 2
    }

    def "PUT /api/houseworks/{houseworkId} - 異常系: 別世帯の家事をbodyのhouseholdIdを自世帯に偽装して更新しようとするとステータス403が返り対象レコードが変更されないこと（越境更新拒否）"() {
        given: "別ユーザーが所有する別世帯と、その世帯に紐づく家事を作成する"
        jdbcTemplate.update("""
            INSERT INTO m_user
              (email, password_hash, auth_provider, display_name, locale,
               notification_enabled, is_active,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('housework-victim@example.com', 'dummy-hash', 'LOCAL', '被害者ユーザー', 'ja',
               true, true,
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """)
        def otherUserId = jdbcTemplate.queryForObject(
                "SELECT user_id FROM m_user WHERE email = 'housework-victim@example.com'",
                Long.class
        )

        jdbcTemplate.update("""
            INSERT INTO m_household
              (name, owner_user_id,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('別世帯', ?,
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, otherUserId)
        def otherHouseholdId = jdbcTemplate.queryForObject(
                "SELECT household_id FROM m_household WHERE owner_user_id = ?",
                Long.class,
                otherUserId
        )

        jdbcTemplate.update("""
            INSERT INTO m_housework
              (household_id, name, category, recurrence_type, weekly_days,
               start_date, end_date,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              (?, '別世帯の家事', 'CLEAN', '1', 1,
               '2025-01-01', '2025-12-31',
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, otherHouseholdId)
        def otherHouseworkId = jdbcTemplate.queryForObject(
                "SELECT housework_id FROM m_housework WHERE household_id = ?",
                Long.class,
                otherHouseholdId
        )

        and: "testUser（別世帯に属していない）のトークンで、bodyのhouseholdIdを自世帯に偽装して送る"
        def token = tokenFor(testUserId)
        def requestBody = [
                householdId   : testHouseholdId,
                name          : "越境更新テスト",
                category      : "CLEAN",
                recurrenceType: "1",
                weeklyDays    : 1,
                startDate     : "2025-01-01",
                endDate       : "2025-12-31"
        ]

        when:
        def result = mockMvc.perform(
                put("/api/houseworks/${otherHouseworkId}")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then: "ステータス403が返ること"
        result.andExpect(status().isForbidden())

        and: "DBの対象家事は変更されていないこと"
        def name = jdbcTemplate.queryForObject(
                "SELECT name FROM m_housework WHERE housework_id = ?",
                String.class,
                otherHouseworkId
        )
        name == "別世帯の家事"

        cleanup:
        jdbcTemplate.update("DELETE FROM m_housework WHERE household_id = ?", otherHouseholdId)
        jdbcTemplate.update("DELETE FROM m_household WHERE household_id = ?", otherHouseholdId)
        jdbcTemplate.update("DELETE FROM m_user WHERE user_id = ?", otherUserId)
    }
}
