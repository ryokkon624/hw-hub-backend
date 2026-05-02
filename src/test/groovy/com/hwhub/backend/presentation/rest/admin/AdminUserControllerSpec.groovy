package com.hwhub.backend.presentation.rest.admin

import com.hwhub.backend.application.service.AdminUserService
import com.hwhub.backend.application.service.UserRoleService
import com.hwhub.backend.domain.enums.UserRole
import com.hwhub.backend.domain.model.AdminUserSearchCondition
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.model.UserRoleModel
import com.hwhub.backend.presentation.rest.admin.dto.AdminUserResponse
import com.hwhub.backend.security.CurrentUserIdArgumentResolver
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AdminUserControllerSpec extends Specification {

    def userRoleService = Mock(UserRoleService)
    def adminUserService = Mock(AdminUserService)
    def controller = new AdminUserController(userRoleService, adminUserService)
    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new CurrentUserIdArgumentResolver())
                .build()
    }

    def cleanup() {
        SecurityContextHolder.clearContext()
    }

    /** SecurityContext に認証情報をセットするヘルパー */
    private void setAuthentication(String userId) {
        def auth = Mock(Authentication)
        auth.getName() >> userId
        def ctx = Mock(SecurityContext)
        ctx.getAuthentication() >> auth
        SecurityContextHolder.setContext(ctx)
    }

    // -------------------------------------------------
    // GET /api/admin/users
    // -------------------------------------------------
    def "GET /api/admin/users: メールで検索してユーザーリストを返す"() {
        given:
        setAuthentication("1")
        def userModel = UserModel.reconstruct(100L, "test@example.com", null, null, "LOCAL", null, "Taro", "ja", true, null, null, true, null, null)
        def roleModel = UserRoleModel.reconstruct(10L, 100L, UserRole.ADMIN)
        def response = new UserRoleService.SearchUserResult(userModel, [roleModel])

        when:
        def result = mockMvc.perform(
            get("/api/admin/users")
                .param("email", "test@example.com")
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
        setAuthentication("1")

        when:
        def result = mockMvc.perform(
            get("/api/admin/users")
                .param("email", "nobody@example.com")
        )

        then:
        1 * userRoleService.searchUsers("nobody@example.com") >> []
        result.andExpect(status().isOk())
              .andExpect(jsonPath('$').isArray())
              .andExpect(jsonPath('$').isEmpty())
    }

    def "GET /api/admin/users: 複数ユーザーがマッチした場合全件返す"() {
        given:
        setAuthentication("1")
        def userModel1 = UserModel.reconstruct(1L, "alice@example.com", null, null, "LOCAL", null, "Alice", "en", true, null, null, true, null, null)
        def roleModel1 = UserRoleModel.reconstruct(11L, 1L, UserRole.ADMIN)
        def user1 = new UserRoleService.SearchUserResult(userModel1, [roleModel1])
        def userModel2 = UserModel.reconstruct(2L, "bob@example.com", null, null, "LOCAL", null, "Bob", "ja", true, null, null, true, null, null)
        def user2 = new UserRoleService.SearchUserResult(userModel2, [])

        when:
        def result = mockMvc.perform(
            get("/api/admin/users")
                .param("email", "example.com")
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
        setAuthentication("1")

        when:
        def result = mockMvc.perform(
            get("/api/admin/users")
        )

        then:
        0 * userRoleService.searchUsers(_)
        result.andExpect(status().isBadRequest())
    }

    // -------------------------------------------------
    // GET /api/admin/users/search
    // -------------------------------------------------
    def "GET /api/admin/users/search: 各種条件で検索して結果を返す"() {
        given:
        setAuthentication("1")
        def user = UserModel.reconstruct(1L, "test@example.com", null, null, "LOCAL", null, "Taro", "ja", true, null, null, true, null, null)

        when:
        def result = mockMvc.perform(
            get("/api/admin/users/search")
                .param("email", "test@example.com")
                .param("isActive", "true")
                .param("locale", "ja")
        )

        then:
        1 * adminUserService.searchUsers(_ as AdminUserSearchCondition) >> [user]
        result.andExpect(status().isOk())
              .andExpect(jsonPath('$[0].userId').value(1))
    }

    // -------------------------------------------------
    // POST /api/admin/users
    // -------------------------------------------------
    def "POST /api/admin/users: ユーザーを新規登録して登録結果を返す"() {
        given:
        setAuthentication("99")
        def user = UserModel.reconstruct(10L, "new@example.com", null, null, "LOCAL", null, "New", "ja", true, null, null, true, null, null)

        def json = '{"email":"new@example.com", "password":"password", "displayName":"New", "locale":"ja"}'

        when:
        def result = mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/admin/users")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(json)
        )

        then:
        1 * adminUserService.createUser("new@example.com", "password", "New", "ja", 99L) >> user
        result.andExpect(status().isOk())
              .andExpect(jsonPath('$.userId').value(10))
    }

    // -------------------------------------------------
    // PUT /api/admin/users/{userId}
    // -------------------------------------------------
    def "PUT /api/admin/users/{userId}: ユーザー情報を更新して更新結果を返す"() {
        given:
        setAuthentication("99")
        def user = UserModel.reconstruct(10L, "test@example.com", null, null, "LOCAL", null, "Updated", "en", true, null, null, true, null, null)

        def json = '{"displayName":"Updated", "locale":"en", "password":"new-pass", "isActive":true}'

        when:
        def result = mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/admin/users/10")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(json)
        )

        then:
        1 * adminUserService.updateUser(10L, "Updated", "en", "new-pass", true, 99L) >> user
        result.andExpect(status().isOk())
              .andExpect(jsonPath('$.userId').value(10))
    }
}
