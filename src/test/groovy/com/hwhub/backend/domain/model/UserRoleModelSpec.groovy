package com.hwhub.backend.domain.model

import com.hwhub.backend.domain.enums.UserRole
import spock.lang.Specification

class UserRoleModelSpec extends Specification {

    // -------------------------------------------------
    // reconstruct
    // -------------------------------------------------
    def "reconstruct: 全フィールドが正しく設定されたモデルを返す"() {
        when:
        def model = UserRoleModel.reconstruct(1L, 100L, UserRole.ADMIN)

        then:
        model.userRoleId == 1L
        model.userId == 100L
        model.role == UserRole.ADMIN
    }

    def "reconstruct: 異なるロールでも正しく生成される"() {
        when:
        def model = UserRoleModel.reconstruct(2L, 200L, UserRole.SUPPORT)

        then:
        model.userRoleId == 2L
        model.userId == 200L
        model.role == UserRole.SUPPORT
    }

    def "reconstruct: where ブロックで全 UserRole を網羅する"() {
        when:
        def model = UserRoleModel.reconstruct(id, userId, role)

        then:
        model.userRoleId == id
        model.userId == userId
        model.role == role

        where:
        id | userId | role
        1L | 10L    | UserRole.ADMIN
        2L | 20L    | UserRole.SUPPORT
    }
}
