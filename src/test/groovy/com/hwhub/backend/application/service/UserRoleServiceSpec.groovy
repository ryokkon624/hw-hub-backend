package com.hwhub.backend.application.service

import com.hwhub.backend.domain.enums.Permission
import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.enums.UserRole
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.model.UserRoleModel
import com.hwhub.backend.domain.repository.RolePermissionRepository
import com.hwhub.backend.domain.repository.UserRepository
import com.hwhub.backend.domain.repository.UserRoleRepository
import spock.lang.Specification

class UserRoleServiceSpec extends Specification {

    UserRoleRepository userRoleRepository = Mock(UserRoleRepository)
    RolePermissionRepository rolePermissionRepository = Mock(RolePermissionRepository)
    UserRepository userRepository = Mock(UserRepository)

    UserRoleService service

    def setup() {
        service = new UserRoleService(userRoleRepository, rolePermissionRepository, userRepository)
    }

    // -------------------------------------------------
    // getMyRolesAndPermissions
    // -------------------------------------------------
    def "getMyRolesAndPermissions: ロールがある場合、ロールとパーミッションを返す"() {
        given:
        Long userId = 1L
        def roleModel = UserRoleModel.reconstruct(10L, userId, UserRole.ADMIN)

        when:
        def result = service.getMyRolesAndPermissions(userId)

        then:
        1 * userRoleRepository.findByUserId(userId) >> [roleModel]
        1 * rolePermissionRepository.findPermissionsByRoles([UserRole.ADMIN]) >> ["11", "10"]

        and:
        result.roles() == ["ADMIN"]
        result.permissions() == ["11", "10"]
    }

    def "getMyRolesAndPermissions: ロールが複数ある場合、全ロールとパーミッションを返す"() {
        given:
        Long userId = 1L
        def adminRole = UserRoleModel.reconstruct(10L, userId, UserRole.ADMIN)
        def supportRole = UserRoleModel.reconstruct(11L, userId, UserRole.SUPPORT)

        when:
        def result = service.getMyRolesAndPermissions(userId)

        then:
        1 * userRoleRepository.findByUserId(userId) >> [adminRole, supportRole]
        1 * rolePermissionRepository.findPermissionsByRoles([UserRole.ADMIN, UserRole.SUPPORT]) >> ["11", "10", "20"]

        and:
        result.roles() == ["ADMIN", "SPPRT"]
        result.permissions() == ["11", "10", "20"]
    }

    def "getMyRolesAndPermissions: ロールがない場合、空リストを返す（DB 問い合わせなし）"() {
        given:
        Long userId = 1L

        when:
        def result = service.getMyRolesAndPermissions(userId)

        then:
        1 * userRoleRepository.findByUserId(userId) >> []
        0 * rolePermissionRepository.findPermissionsByRoles(_)

        and:
        result.roles().isEmpty()
        result.permissions().isEmpty()
    }

    // -------------------------------------------------
    // searchUsers
    // -------------------------------------------------
    def "searchUsers: メールで検索してロール情報を付与したレスポンスを返す"() {
        given:
        String email = "test@example.com"
        def user = UserModel.reconstruct(100L, email, null, null, "LOCAL", null, "Taro", "ja", true, null, null, true)
        def roleModel = UserRoleModel.reconstruct(10L, 100L, UserRole.ADMIN)

        when:
        def result = service.searchUsers(email)

        then:
        1 * userRepository.searchByEmail(email) >> [user]
        1 * userRoleRepository.findByUserId(100L) >> [roleModel]

        and:
        result.size() == 1
        result[0].userId() == 100L
        result[0].email() == email
        result[0].displayName() == "Taro"
        result[0].locale() == "ja"
        result[0].isActive()
        result[0].roles() == ["ADMIN"]
    }

    def "searchUsers: 該当ユーザーがいない場合、空リストを返す"() {
        given:
        String email = "nobody@example.com"

        when:
        def result = service.searchUsers(email)

        then:
        1 * userRepository.searchByEmail(email) >> []
        0 * userRoleRepository.findByUserId(_)

        and:
        result.isEmpty()
    }

    def "searchUsers: ロールなしユーザーは roles が空リストで返る"() {
        given:
        String email = "norole@example.com"
        def user = UserModel.reconstruct(200L, email, null, null, "LOCAL", null, "Norole", "en", true, null, null, true)

        when:
        def result = service.searchUsers(email)

        then:
        1 * userRepository.searchByEmail(email) >> [user]
        1 * userRoleRepository.findByUserId(200L) >> []

        and:
        result.size() == 1
        result[0].roles().isEmpty()
    }

    // -------------------------------------------------
    // assignRole
    // -------------------------------------------------
    def "assignRole: ロールを持っていない場合は insert を呼ぶ"() {
        given:
        Long targetUserId = 10L
        Long operatorUserId = 1L

        when:
        service.assignRole(targetUserId, UserRole.ADMIN, operatorUserId)

        then:
        1 * userRoleRepository.exists(targetUserId, UserRole.ADMIN) >> false
        1 * userRoleRepository.insert(targetUserId, UserRole.ADMIN, operatorUserId, ProgramType.ONL_USR_ROLE.code)
    }

    def "assignRole: すでにロールを持っている場合は insert を呼ばない（冪等）"() {
        given:
        Long targetUserId = 10L
        Long operatorUserId = 1L

        when:
        service.assignRole(targetUserId, UserRole.ADMIN, operatorUserId)

        then:
        1 * userRoleRepository.exists(targetUserId, UserRole.ADMIN) >> true
        0 * userRoleRepository.insert(_, _, _, _)
    }

    def "assignRole: SUPPORT ロールでも同様に動作する"() {
        given:
        Long targetUserId = 20L
        Long operatorUserId = 1L

        when:
        service.assignRole(targetUserId, UserRole.SUPPORT, operatorUserId)

        then:
        1 * userRoleRepository.exists(targetUserId, UserRole.SUPPORT) >> false
        1 * userRoleRepository.insert(targetUserId, UserRole.SUPPORT, operatorUserId, ProgramType.ONL_USR_ROLE.code)
    }

    // -------------------------------------------------
    // removeRole
    // -------------------------------------------------
    def "removeRole: delete を正しく呼ぶ"() {
        given:
        Long targetUserId = 10L
        Long operatorUserId = 1L

        when:
        service.removeRole(targetUserId, UserRole.ADMIN, operatorUserId)

        then:
        1 * userRoleRepository.delete(targetUserId, UserRole.ADMIN, operatorUserId, ProgramType.ONL_USR_ROLE.code)
    }

    def "removeRole: SUPPORT ロールでも delete が呼ばれる"() {
        given:
        Long targetUserId = 20L
        Long operatorUserId = 1L

        when:
        service.removeRole(targetUserId, UserRole.SUPPORT, operatorUserId)

        then:
        1 * userRoleRepository.delete(targetUserId, UserRole.SUPPORT, operatorUserId, ProgramType.ONL_USR_ROLE.code)
    }
}
