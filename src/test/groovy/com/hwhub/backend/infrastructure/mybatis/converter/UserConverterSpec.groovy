package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.enums.AuthProvider
import com.hwhub.backend.domain.enums.ThemeMode
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MUser
import spock.lang.Specification
import spock.lang.Unroll

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
        entity.setThemeMode("DARK")
        entity.setNotificationEnabled(true)
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
            themeMode == ThemeMode.DARK
            notificationEnabled == true
            profileImageKey == "profile/key/001"
            isActive == true
            emailVerifiedAt == java.time.LocalDateTime.of(2025, 1, 1, 10, 0, 0)
        }

        and: "reconstructの仕様どおりpasswordとiconUrlはnullのままである"
        model.password == null
        model.iconUrl == null
    }

    def "toModelはthemeModeがnullの場合SYSTEMにフォールバックする"() {
        given: "themeModeがnullのMUserエンティティ"
        def entity = new MUser()
        entity.setUserId(1L)
        entity.setEmail("user@example.com")
        entity.setNotificationEnabled(false)
        entity.setIsActive(true)
        entity.setThemeMode(null)

        when: "toModelでドメインモデルに変換する"
        def model = UserConverter.toModel(entity)

        then: "themeModeはSYSTEMになる"
        model.themeMode == ThemeMode.SYSTEM
    }

    @Unroll
    def "toModelはthemeModeコード'#code'をEnum '#expected' に変換する"() {
        given: "themeModeが指定されたMUserエンティティ"
        def entity = new MUser()
        entity.setUserId(1L)
        entity.setEmail("user@example.com")
        entity.setNotificationEnabled(false)
        entity.setIsActive(true)
        entity.setThemeMode(code)

        when: "toModelでドメインモデルに変換する"
        def model = UserConverter.toModel(entity)

        then: "themeModeが正しく変換される"
        model.themeMode == expected

        where:
        code     || expected
        "DARK"   || ThemeMode.DARK
        "LIGHT"  || ThemeMode.LIGHT
        "SYSTEM" || ThemeMode.SYSTEM
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
                AuthProvider.LOCAL.code, // authProvider
                null,                    // authProviderId
                "別ユーザ",               // displayName
                "en",                    // locale
                ThemeMode.DARK,          // themeMode
                true,                    // notificationEnabled
                "profile/key/002",       // profileImageKey
                now,                     // emailVerifiedAt
                true,                    // isActive
                null,                    // createdAt
                null                     // updatedAt
        )

        when: "toEntityでMyBatisエンティティに変換する"
        def entity = UserConverter.toEntity(model)

        then: "ID・メールアドレス・パスワードハッシュなどがコピーされている"
        entity != null
        with(entity) {
            userId == 2L
            email == "another@example.com"
            passwordHash == "hashed-2"
            passwordChangedAt == null
            authProvider == AuthProvider.LOCAL.code
            authProviderId == null
            displayName == "別ユーザ"
            locale == "en"
            profileImageKey == "profile/key/002"
            isActive == true
            emailVerifiedAt != null 
        }
    }
}
