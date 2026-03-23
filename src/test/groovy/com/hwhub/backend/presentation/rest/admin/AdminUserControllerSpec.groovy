package com.hwhub.backend.presentation.rest.admin

import com.hwhub.backend.application.service.UserRoleService
import com.hwhub.backend.presentation.rest.admin.dto.AdminUserResponse
import com.hwhub.backend.domain.enums.UserRole
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.model.UserRoleModel
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
        def userModel = UserModel.reconstruct(100L, "test@example.com", null, null, "LOCAL", null, "Taro", "ja", true, null, null, true)
        def roleModel = UserRoleModel.reconstruct(10L, 100L, UserRole.ADMIN)
        def response = new UserRoleService.SearchUserResult(userModel, [roleModel])

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
        def userModel1 = UserModel.reconstruct(1L, "alice@example.com", null, null, "LOCAL", null, "Alice", "en", true, null, null, true)
        def roleModel1 = UserRoleModel.reconstruct(11L, 1L, UserRole.ADMIN)
        def user1 = new UserRoleService.SearchUserResult(userModel1, [roleModel1])
        def userModel2 = UserModel.reconstruct(2L, "bob@example.com", null, null, "LOCAL", null, "Bob", "ja", true, null, null, true)
        def user2 = new UserRoleService.SearchUserResult(userModel2, [])

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
