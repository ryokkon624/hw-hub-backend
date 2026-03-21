package com.hwhub.backend.integration.inquiry

import com.hwhub.backend.integration.IntegrationTestBase
import org.springframework.http.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class InquiryIntegrationSpec extends IntegrationTestBase {

    Long testUserId
    Long testHouseholdId

    def setup() {
        // 1. ユーザーをINSERT（locale='ja'）
        jdbcTemplate.update("""
            INSERT INTO m_user
              (email, password_hash, auth_provider, display_name, locale,
               notification_enabled, is_active,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('inquiry-test@example.com', 'dummy-hash', 'LOCAL', '問い合わせテストユーザー', 'ja',
               true, true,
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """)
        testUserId = jdbcTemplate.queryForObject(
                "SELECT user_id FROM m_user WHERE email = 'inquiry-test@example.com'",
                Long.class
        )

        // 2. 世帯をINSERT
        jdbcTemplate.update("""
            INSERT INTO m_household
              (name, owner_user_id,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('問い合わせテスト世帯', ?,
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
              (?, ?, '問い合わせテストユーザー', '1',
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, testHouseholdId, testUserId)
    }

    def cleanup() {
        // t_inquiry_message → t_inquiry の順で削除（FK制約）
        if (testUserId != null) {
            jdbcTemplate.update("""
                DELETE FROM t_inquiry_message WHERE inquiry_id IN (
                    SELECT inquiry_id FROM t_inquiry WHERE user_id = ?
                )
            """, testUserId)
            jdbcTemplate.update("DELETE FROM t_inquiry WHERE user_id = ?", testUserId)
        }
        if (testHouseholdId != null) {
            jdbcTemplate.update("DELETE FROM m_household_member WHERE household_id = ?", testHouseholdId)
            jdbcTemplate.update("DELETE FROM m_household WHERE household_id = ?", testHouseholdId)
        }
        jdbcTemplate.update("DELETE FROM m_user WHERE email = 'inquiry-test@example.com'")
        jdbcTemplate.update("DELETE FROM m_user WHERE email = 'inquiry-other@example.com'")
    }

    // ==============================================================
    // POST /api/inquiries - 問い合わせ作成
    // ==============================================================

    def "POST /api/inquiries - 正常系: 必須項目を渡してステータス200が返ること"() {
        given:
        def token = tokenFor(testUserId)
        def requestBody = [
                householdId: testHouseholdId,
                category   : "10",
                title      : "テスト件名",
                body       : "テスト本文"
        ]

        when:
        def result = mockMvc.perform(
                post("/api/inquiries")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.inquiryId').isNumber())
    }

    def "POST /api/inquiries - 正常系: DBにt_inquiryが1件・t_inquiry_messageが1件登録されていること"() {
        given:
        def token = tokenFor(testUserId)
        def requestBody = [
                householdId: testHouseholdId,
                category   : "10",
                title      : "DB確認テスト",
                body       : "本文内容"
        ]

        when:
        mockMvc.perform(
                post("/api/inquiries")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then:
        def inquiryCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM t_inquiry WHERE user_id = ? AND title = 'DB確認テスト'",
                Integer.class, testUserId
        )
        inquiryCount == 1

        and:
        def inquiryId = jdbcTemplate.queryForObject(
                "SELECT inquiry_id FROM t_inquiry WHERE user_id = ? AND title = 'DB確認テスト'",
                Long.class, testUserId
        )
        def messageCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM t_inquiry_message WHERE inquiry_id = ?",
                Integer.class, inquiryId
        )
        messageCount == 1
    }

    def "POST /api/inquiries - 異常系: 認証トークンなしでPOSTするとステータス401が返ること"() {
        given:
        def requestBody = [
                householdId: testHouseholdId,
                category   : "10",
                title      : "テスト件名",
                body       : "テスト本文"
        ]

        when:
        def result = mockMvc.perform(
                post("/api/inquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then:
        result.andExpect(status().isUnauthorized())
    }

    def "POST /api/inquiries - 異常系: titleなしでPOSTするとステータス400が返ること"() {
        given:
        def token = tokenFor(testUserId)
        def requestBody = [
                householdId: testHouseholdId,
                category   : "10",
                body       : "テスト本文"
        ]

        when:
        def result = mockMvc.perform(
                post("/api/inquiries")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then:
        result.andExpect(status().isBadRequest())
    }

    def "POST /api/inquiries - 異常系: bodyなしでPOSTするとステータス400が返ること"() {
        given:
        def token = tokenFor(testUserId)
        def requestBody = [
                householdId: testHouseholdId,
                category   : "10",
                title      : "テスト件名"
        ]

        when:
        def result = mockMvc.perform(
                post("/api/inquiries")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then:
        result.andExpect(status().isBadRequest())
    }

    // ==============================================================
    // GET /api/inquiries - 問い合わせ一覧
    // ==============================================================

    def "GET /api/inquiries - 正常系: 問い合わせが存在するときステータス200とリストが返ること"() {
        given: "問い合わせを1件作成しておく"
        def token = tokenFor(testUserId)
        mockMvc.perform(
                post("/api/inquiries")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString([
                                householdId: testHouseholdId,
                                category   : "10",
                                title      : "一覧テスト",
                                body       : "本文"
                        ]))
        )

        when:
        def result = mockMvc.perform(
                get("/api/inquiries")
                        .header("Authorization", "Bearer ${token}")
        )

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.items').isArray())
        result.andExpect(jsonPath('$.items.length()').value(org.hamcrest.Matchers.greaterThanOrEqualTo(1)))
    }

    def "GET /api/inquiries - 正常系: 問い合わせが0件のときステータス200と空リストが返ること"() {
        given:
        def token = tokenFor(testUserId)

        when:
        def result = mockMvc.perform(
                get("/api/inquiries")
                        .header("Authorization", "Bearer ${token}")
        )

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.items').isArray())
        result.andExpect(jsonPath('$.items.length()').value(0))
    }

    def "GET /api/inquiries - 異常系: 認証トークンなしでGETするとステータス401が返ること"() {
        when:
        def result = mockMvc.perform(get("/api/inquiries"))

        then:
        result.andExpect(status().isUnauthorized())
    }

    // ==============================================================
    // GET /api/inquiries/{inquiryId} - 問い合わせ詳細
    // ==============================================================

    def "GET /api/inquiries/{inquiryId} - 正常系: 存在する問い合わせIDでステータス200とメッセージが返ること"() {
        given: "問い合わせを作成する"
        def token = tokenFor(testUserId)
        def createResult = mockMvc.perform(
                post("/api/inquiries")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString([
                                householdId: testHouseholdId,
                                category   : "10",
                                title      : "詳細テスト件名",
                                body       : "詳細テスト本文"
                        ]))
        ).andReturn()
        def inquiryId = objectMapper.readValue(createResult.response.contentAsString, Map).inquiryId

        when:
        def result = mockMvc.perform(
                get("/api/inquiries/${inquiryId}")
                        .header("Authorization", "Bearer ${token}")
        )

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.inquiryId').value(inquiryId))
        result.andExpect(jsonPath('$.title').value("詳細テスト件名"))
        result.andExpect(jsonPath('$.messages').isArray())
        result.andExpect(jsonPath('$.messages.length()').value(1))
    }

    def "GET /api/inquiries/{inquiryId} - 異常系: 存在しない問い合わせIDでステータス404が返ること"() {
        given:
        def token = tokenFor(testUserId)

        when:
        def result = mockMvc.perform(
                get("/api/inquiries/99999999")
                        .header("Authorization", "Bearer ${token}")
        )

        then:
        result.andExpect(status().isNotFound())
    }

    def "GET /api/inquiries/{inquiryId} - 異常系: 他人の問い合わせIDでステータス404が返ること"() {
        given: "他人のユーザーを作成して問い合わせを登録する"
        jdbcTemplate.update("""
            INSERT INTO m_user
              (email, password_hash, auth_provider, display_name, locale,
               notification_enabled, is_active,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('inquiry-other@example.com', 'dummy-hash', 'LOCAL', '他人ユーザー', 'ja',
               true, true,
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """)
        def otherUserId = jdbcTemplate.queryForObject(
                "SELECT user_id FROM m_user WHERE email = 'inquiry-other@example.com'",
                Long.class
        )
        def otherToken = tokenFor(otherUserId)

        def createResult = mockMvc.perform(
                post("/api/inquiries")
                        .header("Authorization", "Bearer ${otherToken}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString([
                                householdId: testHouseholdId,
                                category   : "10",
                                title      : "他人の問い合わせ",
                                body       : "他人の本文"
                        ]))
        ).andReturn()
        def otherInquiryId = objectMapper.readValue(createResult.response.contentAsString, Map).inquiryId

        def myToken = tokenFor(testUserId)

        when: "自分のトークンで他人の問い合わせを取得しようとする"
        def result = mockMvc.perform(
                get("/api/inquiries/${otherInquiryId}")
                        .header("Authorization", "Bearer ${myToken}")
        )

        then:
        result.andExpect(status().isNotFound())
    }

    // ==============================================================
    // POST /api/inquiries/{inquiryId}/messages - メッセージ追加
    // ==============================================================

    def "POST /api/inquiries/{inquiryId}/messages - 正常系: メッセージを追加するとステータス200が返ること"() {
        given: "問い合わせを作成する"
        def token = tokenFor(testUserId)
        def createResult = mockMvc.perform(
                post("/api/inquiries")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString([
                                householdId: testHouseholdId,
                                category   : "10",
                                title      : "メッセージ追加テスト",
                                body       : "初回メッセージ"
                        ]))
        ).andReturn()
        def inquiryId = objectMapper.readValue(createResult.response.contentAsString, Map).inquiryId

        when:
        def result = mockMvc.perform(
                post("/api/inquiries/${inquiryId}/messages")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString([body: "追加メッセージ"]))
        )

        then:
        result.andExpect(status().isOk())
    }

    def "POST /api/inquiries/{inquiryId}/messages - 正常系: DBにt_inquiry_messageが追加されていること"() {
        given: "問い合わせを作成する"
        def token = tokenFor(testUserId)
        def createResult = mockMvc.perform(
                post("/api/inquiries")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString([
                                householdId: testHouseholdId,
                                category   : "10",
                                title      : "メッセージDB確認テスト",
                                body       : "初回メッセージ"
                        ]))
        ).andReturn()
        def inquiryId = objectMapper.readValue(createResult.response.contentAsString, Map).inquiryId

        when:
        mockMvc.perform(
                post("/api/inquiries/${inquiryId}/messages")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString([body: "追加メッセージ"]))
        )

        then: "初回メッセージ(seq=1) + 追加メッセージ(seq=2) で合計2件"
        def messageCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM t_inquiry_message WHERE inquiry_id = ?",
                Integer.class, inquiryId
        )
        messageCount == 2
    }

    // ==============================================================
    // POST /api/inquiries/{inquiryId}/close - クローズ
    // ==============================================================

    def "POST /api/inquiries/{inquiryId}/close - 正常系: クローズするとステータス200が返りstatusがCLOSEDになること"() {
        given: "問い合わせを作成する"
        def token = tokenFor(testUserId)
        def createResult = mockMvc.perform(
                post("/api/inquiries")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString([
                                householdId: testHouseholdId,
                                category   : "10",
                                title      : "クローズテスト",
                                body       : "クローズ前メッセージ"
                        ]))
        ).andReturn()
        def inquiryId = objectMapper.readValue(createResult.response.contentAsString, Map).inquiryId

        when:
        def result = mockMvc.perform(
                post("/api/inquiries/${inquiryId}/close")
                        .header("Authorization", "Bearer ${token}")
        )

        then:
        result.andExpect(status().isOk())

        and: "DBのstatusが'90'（CLOSED）になっていること"
        def status = jdbcTemplate.queryForObject(
                "SELECT status FROM t_inquiry WHERE inquiry_id = ?",
                String.class, inquiryId
        )
        status == "90"
    }

    // ==============================================================
    // POST /api/inquiries/{inquiryId}/escalate - エスカレーション
    // ==============================================================

    def "POST /api/inquiries/{inquiryId}/escalate - 正常系: エスカレーションするとステータス200が返りstatusがPENDING_STAFFになること"() {
        given: "問い合わせを作成し、AI_ANSWERED状態に更新する（escalateはAI_ANSWEREDからのみ可能）"
        def token = tokenFor(testUserId)
        def createResult = mockMvc.perform(
                post("/api/inquiries")
                        .header("Authorization", "Bearer ${token}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString([
                                householdId: testHouseholdId,
                                category   : "10",
                                title      : "エスカレーションテスト",
                                body       : "エスカレーション前メッセージ"
                        ]))
        ).andReturn()
        def inquiryId = objectMapper.readValue(createResult.response.contentAsString, Map).inquiryId
        jdbcTemplate.update(
                "UPDATE t_inquiry SET status = '10' WHERE inquiry_id = ?",
                inquiryId
        )

        when:
        def result = mockMvc.perform(
                post("/api/inquiries/${inquiryId}/escalate")
                        .header("Authorization", "Bearer ${token}")
        )

        then:
        result.andExpect(status().isOk())

        and: "DBのstatusが'20'（PENDING_STAFF）になっていること"
        def status = jdbcTemplate.queryForObject(
                "SELECT status FROM t_inquiry WHERE inquiry_id = ?",
                String.class, inquiryId
        )
        status == "20"
    }
}
