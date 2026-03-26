package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.enums.UserRole
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MUserRole
import spock.lang.Specification
import spock.lang.Unroll

class UserRoleConverterSpec extends Specification {

    // -------------------------------------------------
    // toModel
    // -------------------------------------------------
    @Unroll
    def "toModel: ロール '#roleCode' の MUserRole エンティティを正しく UserRoleModel に変換する"() {
        given:
        def entity = new MUserRole()
        entity.setUserRoleId(1L)
        entity.setUserId(100L)
        entity.setRole(roleCode)

        when:
        def model = UserRoleConverter.toModel(entity)

        then:
        model != null
        model.userRoleId == 1L
        model.userId == 100L
        model.role == expectedRole

        where:
        roleCode | expectedRole
        "ADMIN"  | UserRole.ADMIN
        "SPPRT"  | UserRole.SUPPORT
    }

    def "toModel: 各フィールドが 1 対 1 でコピーされる"() {
        given:
        def entity = new MUserRole()
        entity.setUserRoleId(99L)
        entity.setUserId(999L)
        entity.setRole("ADMIN")

        when:
        def model = UserRoleConverter.toModel(entity)

        then:
        with(model) {
            userRoleId == 99L
            userId == 999L
            role == UserRole.ADMIN
        }
    }

    def "toModel: 不正なロールコードの場合 IllegalArgumentException を投げる"() {
        given:
        def entity = new MUserRole()
        entity.setUserRoleId(1L)
        entity.setUserId(1L)
        entity.setRole("INVALID_ROLE")

        when:
        UserRoleConverter.toModel(entity)

        then:
        thrown(IllegalArgumentException)
    }

    def "private constructor coverage"() {
        when:
        def constructor = UserRoleConverter.class.getDeclaredConstructor()
        constructor.setAccessible(true)
        def instance = constructor.newInstance()

        then:
        instance != null
    }
}
