package com.hwhub.backend.domain.model

import spock.lang.Specification
import java.time.LocalDateTime
import com.hwhub.backend.domain.enums.AuthProvider

class UserModelSpec extends Specification {

    def "reconstructはpasswordがnullでpasswordHashがセットされたUserModelを生成する"() {
        given:
        Long userId = 1L
        String email = "test@example.com"
        String passwordHash = "hashed-password"
        String displayName = "テストユーザ"
        String locale = "ja"
        String profileImageKey = "icon/key.png"
        boolean isActive = true

        when:
        def model = UserModel.reconstruct(
                userId,
                email,
                passwordHash,
                null,
                AuthProvider.LOCAL.code,
                null,
                displayName,
                locale,
                true,
                profileImageKey,
                null,
                isActive
        )

        then:
        model.userId == userId
        model.email == email
        model.password == null                     // ★ reconstructなのでnull
        model.passwordHash == passwordHash
        model.passwordChangedAt == null // added
        model.displayName == displayName
        model.locale == locale
        model.profileImageKey == profileImageKey
        model.iconUrl == null
        model.emailVerifiedAt == null // added
        model.isActive == true
    }

    def "createはuserIdとpasswordHashがnullでisActive=trueのUserModelを生成する"() {
        given:
        String email = "new@example.com"
        String password = "plain-password"
        String displayName = "新規ユーザ"
        String locale = "en"

        when:
        def model = UserModel.create(email, password, displayName, locale)

        then:
        model.userId == null
        model.email == email
        model.password == password
        model.passwordHash == null
        model.displayName == displayName
        model.locale == locale
        model.profileImageKey == null
        model.iconUrl == null
        model.isActive == true
    }

    def "setPasswordHashでpasswordHashが更新される"() {
        given:
        def model = UserModel.create(
                "test@example.com",
                "plain-password",
                "テストユーザ",
                "ja"
        )

        when:
        model.setPasswordHash("new-hashed-password")

        then:
        model.passwordHash == "new-hashed-password"
    }

    def "changePasswordHash updates passwordHash and passwordChangedAt"() {
        given:
        def model = UserModel.reconstruct(
                1L, "test@example.com", "old", null, AuthProvider.LOCAL.code, null, "User", "en", true, null, null, true
        )
        def newHash = "new-hash"
        def changedAt = LocalDateTime.now()

        when:
        model.changePasswordHash(newHash, changedAt)

        then:
        model.passwordHash == newHash
        model.passwordChangedAt == changedAt
    }

    def "setIconUrlでiconUrlが設定される"() {
        given:
        def model = UserModel.reconstruct(
                1L,
                "test@example.com",
                "hashed",
                null,
                AuthProvider.LOCAL.code,
                null,
                "テストユーザ",
                "ja",
                true,
                "icon/key.png",
                null,
                true
        )

        when:
        model.setIconUrl("https://example.com/icon.png")

        then:
        model.iconUrl == "https://example.com/icon.png"
    }

    def "changeProfileでdisplayNameとlocaleが更新される"() {
        given:
        def model = UserModel.reconstruct(
                1L,
                "test@example.com",
                "hashed",
                null,
                AuthProvider.LOCAL.code,
                null,
                "旧表示名",
                "ja",
                true,
                null,
                null,
                true
        )

        when:
        model.changeProfile("新表示名", "en")

        then:
        model.displayName == "新表示名"
        model.locale == "en"
    }

    def "changeProfileImageKeyでprofileImageKeyが更新される"() {
        given:
        def model = UserModel.reconstruct(
                1L,
                "test@example.com",
                "hashed",
                null,
                AuthProvider.LOCAL.code,
                null,
                "テストユーザ",
                "ja",
                true,
                "old/key.png",
                null,
                true
        )

        when:
        model.changeProfileImageKey("new/key.png")

        then:
        model.profileImageKey == "new/key.png"
    }

    def "activateでisActiveがtrueになる"() {
        given:
        def model = UserModel.reconstruct(
                1L,
                "inactive@example.com",
                "hashed",
                null,
                AuthProvider.LOCAL.code,
                null,
                "退会ユーザ",
                "ja",
                true,
                null,
                null,
                false
        )

        when:
        model.activate()

        then:
        model.isActive == true
    }

    def "createGoogleUser initializes google user correctly"() {
        given:
        String email = "c@c.com"
        String sub = "sub123"
        String name = "Carol"
        LocalDateTime now = LocalDateTime.now()

        when:
        def user = UserModel.createGoogleUser(email, sub, name, now)

        then:
        user.userId == null
        user.email == email
        user.authProvider == AuthProvider.GOOGLE.code
        user.authProviderId == sub
        user.displayName == name
        user.locale == "ja" // default as per impl
        user.emailVerifiedAt == now
        user.isActive == true
    }
}
