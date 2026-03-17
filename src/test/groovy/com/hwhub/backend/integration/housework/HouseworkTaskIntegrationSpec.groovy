package com.hwhub.backend.integration.housework

import com.hwhub.backend.integration.IntegrationTestBase
import org.hamcrest.Matchers
import org.springframework.http.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class HouseworkTaskIntegrationSpec extends IntegrationTestBase {

    Long testUserId
    Long testHouseholdId
    Long testHouseworkId
    Long testTaskId

    def setup() {
        // 1. ユーザーをINSERT
        jdbcTemplate.update("""
            INSERT INTO m_user
              (email, password_hash, auth_provider, display_name, locale,
               notification_enabled, is_active,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('task-test@example.com', 'dummy-hash', 'LOCAL', 'タスクテストユーザー', 'ja',
               true, true,
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """)
        testUserId = jdbcTemplate.queryForObject(
                "SELECT user_id FROM m_user WHERE email = 'task-test@example.com'",
                Long.class
        )

        // 2. 世帯をINSERT
        jdbcTemplate.update("""
            INSERT INTO m_household
              (name, owner_user_id,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('タスクテスト世帯', ?,
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
              (?, ?, 'タスクテストユーザー', '1',
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, testHouseholdId, testUserId)

        // 4. houseworkをINSERT
        jdbcTemplate.update("""
            INSERT INTO m_housework
              (household_id, name, category, recurrence_type, weekly_days,
               start_date, end_date,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              (?, 'タスクテスト家事', 'CLEAN', '1', 1,
               '2025-01-01', '2025-12-31',
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, testHouseholdId)
        testHouseworkId = jdbcTemplate.queryForObject(
                "SELECT housework_id FROM m_housework WHERE household_id = ?",
                Long.class,
                testHouseholdId
        )

        // 5. housework_taskをINSERT
        jdbcTemplate.update("""
            INSERT INTO t_housework_task
              (household_id, housework_id, name, category, target_date, status,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              (?, ?, 'タスクテスト家事', 'CLEAN', CURDATE(), '0',
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, testHouseholdId, testHouseworkId)
        testTaskId = jdbcTemplate.queryForObject(
                "SELECT housework_task_id FROM t_housework_task WHERE housework_id = ?",
                Long.class,
                testHouseworkId
        )
    }

    def cleanup() {
        if (testHouseholdId != null) {
            jdbcTemplate.update("DELETE FROM t_housework_task WHERE household_id = ?", testHouseholdId)
            jdbcTemplate.update("DELETE FROM m_housework WHERE household_id = ?", testHouseholdId)
            jdbcTemplate.update("DELETE FROM m_household_member WHERE household_id = ?", testHouseholdId)
            jdbcTemplate.update("DELETE FROM m_household WHERE household_id = ?", testHouseholdId)
        }
        jdbcTemplate.update("DELETE FROM m_user WHERE email = 'task-test@example.com'")
    }

    def "GET /api/housework-tasks - 正常系: housework_taskが存在するときステータス200と1件以上の配列が返ること"() {
        given:
        def token = tokenFor(testUserId)

        when:
        def result = mockMvc.perform(
                get("/api/housework-tasks")
                        .param("householdId", testHouseholdId.toString())
                        .header("Authorization", "Bearer ${token}")
        )

        then: "ステータス200が返ること"
        result.andExpect(status().isOk())

        and: "レスポンスBodyが配列でサイズ1以上であること"
        result.andExpect(jsonPath('$').isArray())
        result.andExpect(jsonPath('$.length()').value(Matchers.greaterThanOrEqualTo(1)))
    }

    def "PATCH /api/housework-tasks/{taskId}/status - 正常系: ステータス変更でステータス204が返りDBが更新されること"() {
        given:
        def token = tokenFor(testUserId)
        def requestBody = [status: "1"]  // DONE

        when:
        def result = mockMvc.perform(
                patch("/api/housework-tasks/${testTaskId}/status")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then: "ステータス204が返ること"
        result.andExpect(status().isNoContent())

        and: "DBのstatusカラムが更新されていること"
        def updatedStatus = jdbcTemplate.queryForObject(
                "SELECT status FROM t_housework_task WHERE housework_task_id = ?",
                String.class,
                testTaskId
        )
        updatedStatus == "1"
    }

    def "PATCH /api/housework-tasks/{taskId}/assign - 正常系: 担当者アサインでステータス204が返りDBが更新されること"() {
        given:
        def token = tokenFor(testUserId)
        def requestBody = [
                assigneeUserId  : testUserId,
                assignReasonType: "0"  // SELF_ASSIGNED
        ]

        when:
        def result = mockMvc.perform(
                patch("/api/housework-tasks/${testTaskId}/assign")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then: "ステータス204が返ること"
        result.andExpect(status().isNoContent())

        and: "DBのassignee_user_idが更新されていること"
        def assignedUserId = jdbcTemplate.queryForObject(
                "SELECT assignee_user_id FROM t_housework_task WHERE housework_task_id = ?",
                Long.class,
                testTaskId
        )
        assignedUserId == testUserId
    }

    def "PATCH /api/housework-tasks/bulk-status - 正常系: 一括ステータス変更でステータス204が返りDBの対象タスクが全て更新されること"() {
        given:
        def token = tokenFor(testUserId)
        def requestBody = [
                taskIds: [testTaskId],
                status : "1"  // DONE
        ]

        when:
        def result = mockMvc.perform(
                patch("/api/housework-tasks/bulk-status")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then: "ステータス204が返ること"
        result.andExpect(status().isNoContent())

        and: "DBの対象タスクが全て更新されていること"
        def updatedStatus = jdbcTemplate.queryForObject(
                "SELECT status FROM t_housework_task WHERE housework_task_id = ?",
                String.class,
                testTaskId
        )
        updatedStatus == "1"
    }

    def "GET /api/housework-tasks - 異常系: 認証トークンなしでGETするとステータス401が返ること"() {
        when:
        def result = mockMvc.perform(
                get("/api/housework-tasks")
                        .param("householdId", testHouseholdId.toString())
        )

        then:
        result.andExpect(status().isUnauthorized())
    }
}
