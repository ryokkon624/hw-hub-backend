package com.hwhub.backend.presentation.rest.admin

import com.fasterxml.jackson.databind.ObjectMapper
import com.hwhub.backend.application.service.UserRoleService
import com.hwhub.backend.domain.enums.UserRole
import com.hwhub.backend.security.CurrentUserIdArgumentResolver
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AdminRoleControllerSpec extends Specification {

    def userRoleService = Mock(UserRoleService)
    def controller = new AdminRoleController(userRoleService)
    MockMvc mockMvc
    ObjectMapper objectMapper = new ObjectMapper()

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
    // GET /api/users/me/roles
    // -------------------------------------------------
    def "GET /api/users/me/roles: ロールとパーミッションのリストを返す"() {
        given:
        setAuthentication("1")
        def serviceResult = new UserRoleService.UserRoleResult(["ADMIN"], ["11", "10"])

        when:
        def result = mockMvc.perform(get("/api/users/me/roles"))

        then:
        1 * userRoleService.getMyRolesAndPermissions(1L) >> serviceResult
        result.andExpect(status().isOk())
              .andExpect(jsonPath('$.roles[0]').value("ADMIN"))
              .andExpect(jsonPath('$.permissions[0]').value("11"))
    }

    def "GET /api/users/me/roles: ロールなしユーザーは空リストを返す"() {
        given:
        setAuthentication("99")
        def serviceResult = new UserRoleService.UserRoleResult([], [])

        when:
        def result = mockMvc.perform(get("/api/users/me/roles"))

        then:
        1 * userRoleService.getMyRolesAndPermissions(99L) >> serviceResult
        result.andExpect(status().isOk())
              .andExpect(jsonPath('$.roles').isArray())
              .andExpect(jsonPath('$.permissions').isArray())
    }

    // -------------------------------------------------
    // POST /api/admin/roles/{userId}
    // -------------------------------------------------
    def "POST /api/admin/roles/{userId}: ロールを付与して 200 を返す"() {
        given:
        setAuthentication("1")
        def body = objectMapper.writeValueAsString([role: "ADMIN"])

        when:
        def result = mockMvc.perform(
            post("/api/admin/roles/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        then:
        1 * userRoleService.assignRole(10L, UserRole.ADMIN, 1L)
        result.andExpect(status().isOk())
    }

    def "POST /api/admin/roles/{userId}: SUPPORT ロールを付与できる"() {
        given:
        setAuthentication("1")
        def body = objectMapper.writeValueAsString([role: "SPPRT"])

        when:
        def result = mockMvc.perform(
            post("/api/admin/roles/20")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        then:
        1 * userRoleService.assignRole(20L, UserRole.SUPPORT, 1L)
        result.andExpect(status().isOk())
    }

    def "POST /api/admin/roles/{userId}: role が空文字の場合 400 を返す"() {
        given:
        setAuthentication("1")
        def body = objectMapper.writeValueAsString([role: ""])

        when:
        def result = mockMvc.perform(
            post("/api/admin/roles/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        then:
        0 * userRoleService.assignRole(_, _, _)
        result.andExpect(status().isBadRequest())
    }

    // -------------------------------------------------
    // DELETE /api/admin/roles/{userId}/{role}
    // -------------------------------------------------
    def "DELETE /api/admin/roles/{userId}/{role}: ロールを削除して 200 を返す"() {
        given:
        setAuthentication("1")

        when:
        def result = mockMvc.perform(
            delete("/api/admin/roles/10/ADMIN")
        )

        then:
        1 * userRoleService.removeRole(10L, UserRole.ADMIN, 1L)
        result.andExpect(status().isOk())
    }

    def "DELETE /api/admin/roles/{userId}/{role}: SUPPORT ロールを削除できる"() {
        given:
        setAuthentication("1")

        when:
        def result = mockMvc.perform(
            delete("/api/admin/roles/20/SPPRT")
        )

        then:
        1 * userRoleService.removeRole(20L, UserRole.SUPPORT, 1L)
        result.andExpect(status().isOk())
    }
}
