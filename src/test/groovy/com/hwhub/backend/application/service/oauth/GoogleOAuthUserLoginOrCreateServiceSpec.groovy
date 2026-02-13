package com.hwhub.backend.application.service.oauth

import com.hwhub.backend.domain.enums.AuthProvider
import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.oauth.google.GoogleUserInfo
import com.hwhub.backend.domain.repository.UserRepository
import com.hwhub.backend.presentation.rest.common.EmailAlreadyUsedForLocalAccountException
import spock.lang.Specification

import java.util.Optional

class GoogleOAuthUserLoginOrCreateServiceSpec extends Specification {

    UserRepository userRepository = Mock()
    GoogleOAuthUserLoginOrCreateService service = new GoogleOAuthUserLoginOrCreateService(userRepository)

    def "loginOrCreate は既存の Google ユーザーを返す"() {
        given:
        def info = new GoogleUserInfo(sub: "sub-123", name: "User Name", email: "user@example.com")
        def existingUser = Mock(UserModel)

        when:
        def result = service.loginOrCreate(info)

        then:
        1 * userRepository.findByAuthProviderAndAuthProviderId(AuthProvider.GOOGLE.code, "sub-123") >> Optional.of(existingUser)
        0 * userRepository.countByEmail(_)
        0 * userRepository.insert(_, _, _)

        result == existingUser
    }

    def "loginOrCreate はメールアドレスが既に使用されている場合 EmailAlreadyUsedForLocalAccountException を投げる"() {
        given:
        def info = new GoogleUserInfo(sub: "sub-new", name: "User Name", email: "used@example.com")

        when:
        service.loginOrCreate(info)

        then:
        1 * userRepository.findByAuthProviderAndAuthProviderId(AuthProvider.GOOGLE.code, "sub-new") >> Optional.empty()
        1 * userRepository.countByEmail("used@example.com") >> 1

        thrown(EmailAlreadyUsedForLocalAccountException)
    }

    def "loginOrCreate は新規ユーザーを作成して返す"() {
        given:
        def info = new GoogleUserInfo(sub: "sub-new", name: "New User", email: "new@example.com")
        def createdUser = Mock(UserModel)

        when:
        def result = service.loginOrCreate(info)

        then:
        1 * userRepository.findByAuthProviderAndAuthProviderId(AuthProvider.GOOGLE.code, "sub-new") >> Optional.empty()
        1 * userRepository.countByEmail("new@example.com") >> 0

        1 * userRepository.insert(_, 1L, ProgramType.ONL_AUTH_GOOGLE.code) >> { UserModel model, Long userId, String pgmId ->
            assert model.email == "new@example.com"
            assert model.authProvider == AuthProvider.GOOGLE.code
            assert model.authProviderId == "sub-new"
            assert model.displayName == "New User"
            createdUser
        }

        result == createdUser
    }

    def "loginOrCreate は名前がない場合デフォルト名で新規ユーザーを作成する"() {
        given:
        def info = new GoogleUserInfo(sub: "sub-new", name: null, email: "new@example.com")
        def createdUser = Mock(UserModel)

        when:
        def result = service.loginOrCreate(info)

        then:
        1 * userRepository.findByAuthProviderAndAuthProviderId(AuthProvider.GOOGLE.code, "sub-new") >> Optional.empty()
        1 * userRepository.countByEmail("new@example.com") >> 0

        1 * userRepository.insert(_, 1L, ProgramType.ONL_AUTH_GOOGLE.code) >> { UserModel model, Long userId, String pgmId ->
            assert model.displayName == "User"
            createdUser
        }

        result == createdUser
    }
}
