package com.hwhub.backend.domain.model

import spock.lang.Specification

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
                displayName,
                locale,
                profileImageKey,
                isActive
        )

        then:
        model.userId == userId
        model.email == email
        model.password == null                     // ★ reconstructなのでnull
        model.passwordHash == passwordHash
        model.displayName == displayName
        model.locale == locale
        model.profileImageKey == profileImageKey
        model.iconUrl == null
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

    def "setIconUrlでiconUrlが設定される"() {
        given:
        def model = UserModel.reconstruct(
                1L,
                "test@example.com",
                "hashed",
                "テストユーザ",
                "ja",
                "icon/key.png",
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
                "旧表示名",
                "ja",
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
                "テストユーザ",
                "ja",
                "old/key.png",
                true
        )

        when:
        model.changeProfileImageKey("new/key.png")

        then:
        model.profileImageKey == "new/key.png"
    }
}
