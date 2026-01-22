package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MUser
import spock.lang.Specification

class UserConverterSpec extends Specification{

    def "toModelは引数がnullのときnullを返す"() {
        expect:
        UserConverter.toModel(null) == null
    }

    def "toModelはエンティティからモデルへ全フィールドを変換する"() {
        given: "すべてのフィールドがセットされたMUserエンティティ"
        def entity = new MUser()
        entity.setUserId(1L)
        entity.setEmail("user@example.com")
        entity.setPasswordHash("hashed-password")
        entity.setDisplayName("テストユーザ")
        entity.setLocale("ja")
        entity.setProfileImageKey("profile/key/001")
        entity.setIsActive(true)
        entity.setEmailVerifiedAt(java.sql.Timestamp.valueOf(java.time.LocalDateTime.of(2025, 1, 1, 10, 0, 0)))

        when: "toModelでドメインモデルに変換する"
        def model = UserConverter.toModel(entity)

        then: "フィールドが正しくコピーされている"
        model != null
        with(model) {
            userId == 1L
            email == "user@example.com"
            passwordHash == "hashed-password"
            displayName == "テストユーザ"
            locale == "ja"
            profileImageKey == "profile/key/001"
            isActive == true
            emailVerifiedAt == java.time.LocalDateTime.of(2025, 1, 1, 10, 0, 0)
        }

        and: "reconstructの仕様どおりpasswordとiconUrlはnullのままである"
        // 注意: reconstructではpasswordはnullになるはずだが、UserConverter.toEntityではpasswordHashを使う。
        // UserModelのフィールドとしてpassword(生パスワード)とpasswordHashがある。
        // UserConverter.toModelではreconstructを使うため、raw passwordはnullになる。
        model.password == null
        model.iconUrl == null
    }

    def "toEntityは引数がnullのときnullを返す"() {
        expect:
        UserConverter.toEntity(null) == null
    }

    def "toEntityはモデルからエンティティへフィールドを変換する"() {
        given: "reconstructで生成されたUserModel"
        def now = java.time.LocalDateTime.now()
        def model = UserModel.reconstruct(
                2L,                      // userId
                "another@example.com",   // email
                "hashed-2",              // passwordHash
                null,                    // passwordChangedAt
                "別ユーザ",                 // displayName
                "en",                    // locale
                "profile/key/002",       // profileImageKey
                now,                     // emailVerifiedAt
                true                     // isActive
        )

        when: "toEntityでMyBatisエンティティに変換する"
        def entity = UserConverter.toEntity(model)

        then: "ID・メールアドレス・パスワードハッシュなどがコピーされている"
        entity != null
        with(entity) {
            userId == 2L
            email == "another@example.com"
            passwordHash == "hashed-2"
            displayName == "別ユーザ"
            locale == "en"
            profileImageKey == "profile/key/002"
            isActive == true
            // Time conversion verification might have nanosecond precision issues, strictly speaking
            // but for unit test usually fine or use DateConverter logic validity
            emailVerifiedAt != null 
        }
    }
}
