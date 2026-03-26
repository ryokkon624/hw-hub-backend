package com.hwhub.backend.security

import com.hwhub.backend.domain.enums.Permission
import com.hwhub.backend.domain.enums.UserRole
import com.hwhub.backend.domain.model.UserRoleModel
import com.hwhub.backend.domain.repository.RolePermissionRepository
import com.hwhub.backend.domain.repository.UserRoleRepository
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextImpl
import spock.lang.Specification

class RequiresPermissionAspectSpec extends Specification {

    UserRoleRepository userRoleRepository = Mock(UserRoleRepository)
    RolePermissionRepository rolePermissionRepository = Mock(RolePermissionRepository)
    RequiresPermissionAspect aspect

    def setup() {
        aspect = new RequiresPermissionAspect(userRoleRepository, rolePermissionRepository)
        SecurityContextHolder.clearContext()
    }

    def cleanup() {
        SecurityContextHolder.clearContext()
    }

    // -------------------------------------------------
    // ヘルパー
    // -------------------------------------------------
    private RequiresPermission mockAnnotation(Permission... permissions) {
        def annotation = Mock(RequiresPermission)
        annotation.value() >> permissions
        return annotation
    }

    private static void setAuthentication(String userId) {
        def auth = new UsernamePasswordAuthenticationToken(userId, null, [])
        SecurityContextHolder.setContext(new SecurityContextImpl(auth))
    }

    // -------------------------------------------------
    // 認証なし
    // -------------------------------------------------
    def "checkPermission: Authentication が null の場合 AccessDeniedException を投げる"() {
        given:
        SecurityContextHolder.clearContext()
        def annotation = mockAnnotation(Permission.ROLE_MANAGEMENT)

        when:
        aspect.checkPermission(annotation)

        then:
        def e = thrown(AccessDeniedException)
        e.message == "Unauthorized"
    }

    def "checkPermission: isAuthenticated() が false の場合 AccessDeniedException を投げる"() {
        given:
        def auth = Mock(Authentication)
        auth.isAuthenticated() >> false
        SecurityContextHolder.setContext(new SecurityContextImpl(auth))
        def annotation = mockAnnotation(Permission.ROLE_MANAGEMENT)

        when:
        aspect.checkPermission(annotation)

        then:
        def e = thrown(AccessDeniedException)
        e.message == "Unauthorized"
    }

    // -------------------------------------------------
    // ロールなし
    // -------------------------------------------------
    def "checkPermission: ロールが割り当てられていない場合 AccessDeniedException を投げる"() {
        given:
        setAuthentication("1")
        def annotation = mockAnnotation(Permission.ROLE_MANAGEMENT)

        when:
        aspect.checkPermission(annotation)

        then:
        1 * userRoleRepository.findByUserId(1L) >> []
        def e = thrown(AccessDeniedException)
        e.message == "No role assigned"
    }

    // -------------------------------------------------
    // パーミッションなし
    // -------------------------------------------------
    def "checkPermission: パーミッションが不足している場合 AccessDeniedException を投げる"() {
        given:
        setAuthentication("1")
        def roleModel = UserRoleModel.reconstruct(10L, 1L, UserRole.SUPPORT)
        def annotation = mockAnnotation(Permission.ROLE_MANAGEMENT)

        when:
        aspect.checkPermission(annotation)

        then:
        1 * userRoleRepository.findByUserId(1L) >> [roleModel]
        1 * rolePermissionRepository.findPermissionsByRoles([UserRole.SUPPORT]) >> ["20"] // INQUIRY_REPLY のみ

        def e = thrown(AccessDeniedException)
        e.message == "Permission denied"
    }

    // -------------------------------------------------
    // パーミッションあり（正常系）
    // -------------------------------------------------
    def "checkPermission: 必要なパーミッションを持っている場合は例外を投げない"() {
        given:
        setAuthentication("1")
        def roleModel = UserRoleModel.reconstruct(10L, 1L, UserRole.ADMIN)
        def annotation = mockAnnotation(Permission.ROLE_MANAGEMENT)

        when:
        aspect.checkPermission(annotation)

        then:
        1 * userRoleRepository.findByUserId(1L) >> [roleModel]
        1 * rolePermissionRepository.findPermissionsByRoles([UserRole.ADMIN]) >> ["11", "10"]
        noExceptionThrown()
    }

    def "checkPermission: 複数パーミッション指定でどれか1つ持っていれば通過する"() {
        given:
        setAuthentication("2")
        def roleModel = UserRoleModel.reconstruct(11L, 2L, UserRole.SUPPORT)
        def annotation = mockAnnotation(Permission.ROLE_MANAGEMENT, Permission.INQUIRY_REPLY)

        when:
        aspect.checkPermission(annotation)

        then:
        1 * userRoleRepository.findByUserId(2L) >> [roleModel]
        1 * rolePermissionRepository.findPermissionsByRoles([UserRole.SUPPORT]) >> ["20"] // INQUIRY_REPLY のみ
        noExceptionThrown()
    }

    def "checkPermission: 複数ロールの場合、集約されたパーミッションで判定する"() {
        given:
        setAuthentication("3")
        def adminRole = UserRoleModel.reconstruct(10L, 3L, UserRole.ADMIN)
        def supportRole = UserRoleModel.reconstruct(11L, 3L, UserRole.SUPPORT)
        def annotation = mockAnnotation(Permission.ROLE_MANAGEMENT)

        when:
        aspect.checkPermission(annotation)

        then:
        1 * userRoleRepository.findByUserId(3L) >> [adminRole, supportRole]
        1 * rolePermissionRepository.findPermissionsByRoles([UserRole.ADMIN, UserRole.SUPPORT]) >> ["11", "10", "20"]
        noExceptionThrown()
    }
}
