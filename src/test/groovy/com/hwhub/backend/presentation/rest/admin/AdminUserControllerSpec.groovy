package com.hwhub.backend.presentation.rest.admin

import com.hwhub.backend.application.service.UserRoleService
import com.hwhub.backend.presentation.rest.admin.dto.AdminUserResponse
import org.springframework.security.core.Authentication
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AdminUserControllerSpec extends Specification {

    def userRoleService = Mock(UserRoleService)
    def controller = new AdminUserController(userRoleService)
    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    // -------------------------------------------------
    // GET /api/admin/users
    // -------------------------------------------------
    def "GET /api/admin/users: メールで検索してユーザーリストを返す"() {
        given:
        def auth = Mock(Authentication)
        auth.getName() >> "1"
        def response = new AdminUserResponse(100L, "test@example.com", "Taro", "ja", true, ["ADMIN"])

        when:
        def result = mockMvc.perform(
            get("/api/admin/users")
                .param("email", "test@example.com")
                .principal(auth)
        )

        then:
        1 * userRoleService.searchUsers("test@example.com") >> [response]
        result.andExpect(status().isOk())
              .andExpect(jsonPath('$[0].userId').value(100))
              .andExpect(jsonPath('$[0].email').value("test@example.com"))
              .andExpect(jsonPath('$[0].displayName').value("Taro"))
              .andExpect(jsonPath('$[0].locale').value("ja"))
              .andExpect(jsonPath('$[0].isActive').value(true))
              .andExpect(jsonPath('$[0].roles[0]').value("ADMIN"))
    }

    def "GET /api/admin/users: 該当ユーザーがいない場合は空リストを返す"() {
        given:
        def auth = Mock(Authentication)
        auth.getName() >> "1"

        when:
        def result = mockMvc.perform(
            get("/api/admin/users")
                .param("email", "nobody@example.com")
                .principal(auth)
        )

        then:
        1 * userRoleService.searchUsers("nobody@example.com") >> []
        result.andExpect(status().isOk())
              .andExpect(jsonPath('$').isArray())
              .andExpect(jsonPath('$').isEmpty())
    }

    def "GET /api/admin/users: 複数ユーザーがマッチした場合全件返す"() {
        given:
        def auth = Mock(Authentication)
        auth.getName() >> "1"
        def user1 = new AdminUserResponse(1L, "alice@example.com", "Alice", "en", true, ["ADMIN"])
        def user2 = new AdminUserResponse(2L, "bob@example.com", "Bob", "ja", true, [])

        when:
        def result = mockMvc.perform(
            get("/api/admin/users")
                .param("email", "example.com")
                .principal(auth)
        )

        then:
        1 * userRoleService.searchUsers("example.com") >> [user1, user2]
        result.andExpect(status().isOk())
              .andExpect(jsonPath('$').isArray())
              .andExpect(jsonPath('$.length()').value(2))
              .andExpect(jsonPath('$[0].email').value("alice@example.com"))
              .andExpect(jsonPath('$[1].email').value("bob@example.com"))
    }

    def "GET /api/admin/users: email パラメータがない場合 400 を返す"() {
        given:
        def auth = Mock(Authentication)
        auth.getName() >> "1"

        when:
        def result = mockMvc.perform(
            get("/api/admin/users").principal(auth)
        )

        then:
        0 * userRoleService.searchUsers(_)
        result.andExpect(status().isBadRequest())
    }
}
