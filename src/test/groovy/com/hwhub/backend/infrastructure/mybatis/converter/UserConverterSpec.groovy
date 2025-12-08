package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MUser
import spock.lang.Specification

class UserConverterSpec extends Specification{

    def "toModelは引数がnullのときnullを返す"() {
        expect:
        UserConverter.toModel(null) == null
    }

    def "toModelはエンティティからモデルへ全フィールドを変換する（passwordとiconUrlはnull）"() {
        given: "すべてのフィールドがセットされたMUserエンティティ"
        def entity = new MUser()
        entity.setUserId(1L)
        entity.setEmail("user@example.com")
        entity.setPasswordHash("hashed-password")
        entity.setDisplayName("テストユーザ")
        entity.setLocale("ja")
        entity.setProfileImageKey("profile/key/001")
        entity.setIsActive(true)

        when: "toModelでドメインモデルに変換する"
        def model = UserConverter.toModel(entity)

        then: "基本情報・パスワードハッシュ・活性フラグがコピーされている"
        model != null
        with(model) {
            userId == 1L
            email == "user@example.com"
            passwordHash == "hashed-password"
            displayName == "テストユーザ"
            locale == "ja"
            profileImageKey == "profile/key/001"
            isActive
        }

        and: "reconstructの仕様どおりpasswordとiconUrlはnullのままである"
        model.password == null
        model.iconUrl == null
    }

    def "toEntityは引数がnullのときnullを返す"() {
        expect:
        UserConverter.toEntity(null) == null
    }

    def "toEntityはモデルからエンティティへ主要フィールドを変換する"() {
        given: "reconstructで生成されたUserModel"
        def model = UserModel.reconstruct(
                2L,                      // userId
                "another@example.com",   // email
                "hashed-2",              // passwordHash
                "別ユーザ",                 // displayName
                "en",                    // locale
                "profile/key/002",       // profileImageKey
                true                     // isActive（toEntityでは使用しない）
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
        }

        and: "isActiveやiconUrlはconverterでは扱わない（DBのデフォルトや別処理に委ねる想定）"
        // entityにisActiveカラムがある場合も、このconverterではセットしない方針
    }
}
