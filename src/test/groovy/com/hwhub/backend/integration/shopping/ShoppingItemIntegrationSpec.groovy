package com.hwhub.backend.integration.shopping

import com.hwhub.backend.integration.IntegrationTestBase
import org.hamcrest.Matchers
import org.springframework.http.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ShoppingItemIntegrationSpec extends IntegrationTestBase {

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
              ('shopping-test@example.com', 'dummy-hash', 'LOCAL', '買い物テストユーザー', 'ja',
               true, true,
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """)
        testUserId = jdbcTemplate.queryForObject(
                "SELECT user_id FROM m_user WHERE email = 'shopping-test@example.com'",
                Long.class
        )

        // 2. 世帯をINSERT
        jdbcTemplate.update("""
            INSERT INTO m_household
              (name, owner_user_id,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('買い物テスト世帯', ?,
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
              (?, ?, '買い物テストユーザー', '1',
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, testHouseholdId, testUserId)
    }

    def cleanup() {
        if (testHouseholdId != null) {
            jdbcTemplate.update("DELETE FROM t_shopping_item WHERE household_id = ?", testHouseholdId)
            jdbcTemplate.update("DELETE FROM m_household_member WHERE household_id = ?", testHouseholdId)
            jdbcTemplate.update("DELETE FROM m_household WHERE household_id = ?", testHouseholdId)
        }
        jdbcTemplate.update("DELETE FROM m_user WHERE email = 'shopping-test@example.com'")
    }

    def "POST /api/households/{householdId}/shopping-items - 正常系: 必須項目を渡してステータス201が返ること"() {
        given:
        def token = tokenFor(testUserId)
        def requestBody = [
                name     : "テスト買い物アイテム",
                storeType: "1"
        ]

        when:
        def result = mockMvc.perform(
                post("/api/households/${testHouseholdId}/shopping-items")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then: "ステータス201が返ること"
        result.andExpect(status().isCreated())

        and: "DBにshopping_itemが1件登録されていること"
        def count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM t_shopping_item WHERE household_id = ?",
                Integer.class,
                testHouseholdId
        )
        count == 1
    }

    def "GET /api/households/{householdId}/shopping-items - 正常系: shopping_itemが存在するときステータス200と1件以上の配列が返ること"() {
        given: "買い物アイテムを1件INSERTしておく"
        jdbcTemplate.update("""
            INSERT INTO t_shopping_item
              (household_id, name, store_type, status, favorite,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              (?, 'テスト買い物アイテム', '1', '0', '0',
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, testHouseholdId)

        def token = tokenFor(testUserId)

        when:
        def result = mockMvc.perform(
                get("/api/households/${testHouseholdId}/shopping-items")
                        .header("Authorization", "Bearer ${token}")
        )

        then: "ステータス200が返ること"
        result.andExpect(status().isOk())

        and: "レスポンスBodyが配列でサイズ1以上であること"
        result.andExpect(jsonPath('$.items').isArray())
        result.andExpect(jsonPath('$.items.length()').value(Matchers.greaterThanOrEqualTo(1)))
    }

    def "PATCH /api/shopping-items/{shoppingItemId}/status - 正常系: ステータス変更でステータス204が返りDBが更新されること"() {
        given: "買い物アイテムを1件INSERTしてIDを取得する"
        jdbcTemplate.update("""
            INSERT INTO t_shopping_item
              (household_id, name, store_type, status, favorite,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              (?, 'ステータス変更テストアイテム', '1', '0', '0',
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, testHouseholdId)
        def shoppingItemId = jdbcTemplate.queryForObject(
                "SELECT shopping_item_id FROM t_shopping_item WHERE household_id = ? AND name = 'ステータス変更テストアイテム'",
                Long.class,
                testHouseholdId
        )

        def token = tokenFor(testUserId)
        def requestBody = [status: "9"]

        when:
        def result = mockMvc.perform(
                patch("/api/shopping-items/${shoppingItemId}/status")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then: "ステータス204が返ること"
        result.andExpect(status().isNoContent())

        and: "DBのstatusカラムが更新されていること"
        def updatedStatus = jdbcTemplate.queryForObject(
                "SELECT status FROM t_shopping_item WHERE shopping_item_id = ?",
                String.class,
                shoppingItemId
        )
        updatedStatus == "9"
    }

    def "PATCH /api/shopping-items/{shoppingItemId}/favorite - 正常系: お気に入りトグルでステータス204が返ること"() {
        given: "買い物アイテムを1件INSERTしてIDを取得する"
        jdbcTemplate.update("""
            INSERT INTO t_shopping_item
              (household_id, name, store_type, status, favorite,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              (?, 'お気に入りテストアイテム', '1', '0', '0',
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, testHouseholdId)
        def shoppingItemId = jdbcTemplate.queryForObject(
                "SELECT shopping_item_id FROM t_shopping_item WHERE household_id = ? AND name = 'お気に入りテストアイテム'",
                Long.class,
                testHouseholdId
        )

        def token = tokenFor(testUserId)
        def requestBody = [favorite: "1"]

        when:
        def result = mockMvc.perform(
                patch("/api/shopping-items/${shoppingItemId}/favorite")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then: "ステータス204が返ること"
        result.andExpect(status().isNoContent())
    }

    def "POST /api/households/{householdId}/shopping-items - 異常系: 認証トークンなしでPOSTするとステータス401が返ること"() {
        given:
        def requestBody = [
                name     : "テスト買い物アイテム",
                storeType: "1"
        ]

        when:
        def result = mockMvc.perform(
                post("/api/households/${testHouseholdId}/shopping-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then:
        result.andExpect(status().isUnauthorized())
    }
}
