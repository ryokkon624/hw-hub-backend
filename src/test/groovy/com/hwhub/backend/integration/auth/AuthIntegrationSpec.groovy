package com.hwhub.backend.integration.auth

import com.hwhub.backend.integration.IntegrationTestBase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AuthIntegrationSpec extends IntegrationTestBase {

    @Autowired
    PasswordEncoder passwordEncoder

    def cleanup() {
        jdbcTemplate.update(
                "DELETE FROM m_user WHERE email IN ('register-test@example.com', 'login-test@example.com')"
        )
    }

    def "POST /api/auth/register - 正常系: 必須項目を渡してステータス200が返ること"() {
        given:
        def requestBody = [
                email      : "register-test@example.com",
                password   : "Password123!",
                displayName: "テストユーザー",
                locale     : "ja"
        ]

        when:
        def result = mockMvc.perform(
                post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then: "ステータス200が返ること"
        result.andExpect(status().isOk())

        and: "DBにユーザーが1件登録されていること"
        def count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM m_user WHERE email = 'register-test@example.com'",
                Integer.class
        )
        count == 1
    }

    def "POST /api/auth/login - 正常系: 正しいemail・passwordを渡してステータス200とJWTトークンが返ること"() {
        given: "ログイン可能なユーザーをDBに登録する"
        def rawPassword = "Password123!"
        def hashedPassword = passwordEncoder.encode(rawPassword)
        jdbcTemplate.update("""
            INSERT INTO m_user
              (email, password_hash, auth_provider, display_name, locale,
               notification_enabled, is_active,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('login-test@example.com', ?, 'LOCAL', 'ログインテストユーザー', 'ja',
               true, true,
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, hashedPassword)

        def requestBody = [
                email   : "login-test@example.com",
                password: rawPassword
        ]

        when:
        def result = mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then: "ステータス200が返ること"
        result.andExpect(status().isOk())

        and: "レスポンスBodyにアクセストークンとリフレッシュトークンが含まれること"
        result.andExpect(jsonPath('$.accessToken').isNotEmpty())
        result.andExpect(jsonPath('$.refreshToken').isNotEmpty())
    }

    def "POST /api/auth/refresh - 正常系: 有効なリフレッシュトークンを渡して新しいトークンペアが返ること"() {
        given: "ログイン可能なユーザーをDBに登録する"
        def rawPassword = "Password123!"
        def hashedPassword = passwordEncoder.encode(rawPassword)
        jdbcTemplate.update("""
            INSERT INTO m_user
              (email, password_hash, auth_provider, display_name, locale,
               notification_enabled, is_active,
               create_user_id, create_program, created_at,
               update_user_id, update_program, updated_at)
            VALUES
              ('login-test@example.com', ?, 'LOCAL', 'ログインテストユーザー', 'ja',
               true, true,
               1, 'IT-data', NOW(),
               1, 'IT-data', NOW())
        """, hashedPassword)

        // ログインしてリフレッシュトークンを取得
        def loginBody = [email: "login-test@example.com", password: rawPassword]
        def loginResult = mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginBody))
        ).andReturn()
        def loginJson = objectMapper.readValue(loginResult.response.contentAsString, Map)
        def refreshToken = loginJson.refreshToken

        when:
        def result = mockMvc.perform(
                post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString([refreshToken: refreshToken]))
        )

        then: "ステータス200と新しいトークンペアが返ること"
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.accessToken').isNotEmpty())
        result.andExpect(jsonPath('$.refreshToken').isNotEmpty())
    }

    def "POST /api/auth/refresh - 異常系: 無効なリフレッシュトークンでは401が返ること"() {
        given:
        def requestBody = [refreshToken: "invalid.refresh.token"]

        when:
        def result = mockMvc.perform(
                post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then:
        result.andExpect(status().isUnauthorized())
    }

    def "POST /api/auth/login - 異常系: 存在しないemailでPOSTするとステータス401が返ること"() {
        given:
        def requestBody = [
                email   : "nonexistent@example.com",
                password: "Password123!"
        ]

        when:
        def result = mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        )

        then:
        result.andExpect(status().isUnauthorized())
    }
}