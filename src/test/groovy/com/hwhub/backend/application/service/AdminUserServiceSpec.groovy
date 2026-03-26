package com.hwhub.backend.application.service

import com.hwhub.backend.domain.enums.AuthProvider
import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.model.AdminUserSearchCondition
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.repository.UserRepository
import com.hwhub.backend.presentation.rest.common.EmailAlreadyUsedException
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import java.time.LocalDateTime

class AdminUserServiceSpec extends Specification {

    UserRepository userRepository = Mock()
    PasswordEncoder passwordEncoder = Mock()
    UserIconService userIconService = Mock()

    AdminUserService service = new AdminUserService(userRepository, passwordEncoder, userIconService)

    def "searchUsers: 検索条件に基づいてユーザーリストを返し、アイコンURLを設定する"() {
        given:
        def condition = new AdminUserSearchCondition("test@example.com", null, null)
        def user = UserModel.reconstruct(
                1L, "test@example.com", null, null, AuthProvider.LOCAL.code, null, "Taro", "ja", true, "icon-key", null, true, null, null
        )
        def users = [user]

        when:
        def result = service.searchUsers(condition)

        then:
        1 * userRepository.searchByCondition(condition) >> users
        1 * userIconService.getIconUrl("icon-key") >> "http://example.com/icon.png"
        
        and:
        result.size() == 1
        result[0].iconUrl == "http://example.com/icon.png"
    }

    def "createUser: 未使用のメールアドレスでユーザーを作成し、認証済みとしてマークする"() {
        given:
        String email = "new@example.com"
        String password = "plain-password"
        String displayName = "New User"
        String locale = "ja"
        Long operatorUserId = 99L

        def createdUser = UserModel.reconstruct(
                10L, email, "hashed-password", null, AuthProvider.LOCAL.code, null, displayName, locale, true, null, null, true, null, null
        )

        when:
        def result = service.createUser(email, password, displayName, locale, operatorUserId)

        then:
        1 * userRepository.findByEmail(email) >> Optional.empty()
        1 * passwordEncoder.encode(password) >> "hashed-password"
        1 * userRepository.insertByAdmin(_ as UserModel, operatorUserId, ProgramType.ONL_ADM_USR.getCode()) >> createdUser
        1 * userRepository.markEmailVerified(10L, _ as LocalDateTime, operatorUserId, ProgramType.ONL_ADM_USR.getCode())

        and:
        result == createdUser
    }

    def "createUser: 既に使用されているメールアドレスの場合は EmailAlreadyUsedException を投げる"() {
        given:
        String email = "existing@example.com"
        def existingUser = Mock(UserModel)

        when:
        service.createUser(email, "pass", "Name", "ja", 1L)

        then:
        1 * userRepository.findByEmail(email) >> Optional.of(existingUser)
        thrown(EmailAlreadyUsedException)
        0 * userRepository.insertByAdmin(_, _, _)
    }

    def "updateUser: 指定されたユーザー情報をすべて更新する"() {
        given:
        Long targetUserId = 1L
        Long operatorUserId = 99L
        def user = UserModel.reconstruct(
                targetUserId, "test@example.com", "old-hash", null, AuthProvider.LOCAL.code, null, "Old Name", "ja", true, null, null, true, null, null
        )

        when:
        def result = service.updateUser(targetUserId, "New Name", "en", "new-password", false, operatorUserId)

        then:
        1 * userRepository.findById(targetUserId) >> Optional.of(user)
        1 * passwordEncoder.encode("new-password") >> "new-hash"
        1 * userRepository.updateByAdmin(user, operatorUserId, ProgramType.ONL_ADM_USR.getCode())

        and:
        user.displayName == "New Name"
        user.locale == "en"
        user.passwordHash == "new-hash"
        !user.isActive()
        result == user
    }

    def "updateUser: isActive が null、password が空文字の場合はそれらの更新をスキップする"() {
        given:
        Long targetUserId = 1L
        def user = UserModel.reconstruct(
                targetUserId, "test@example.com", "old-hash", null, AuthProvider.LOCAL.code, null, "Old Name", "ja", true, null, null, true, null, null
        )

        when:
        service.updateUser(targetUserId, "New Name", "en", "", null, 99L)

        then:
        1 * userRepository.findById(targetUserId) >> Optional.of(user)
        0 * passwordEncoder.encode(_)
        1 * userRepository.updateByAdmin(user, 99L, ProgramType.ONL_ADM_USR.getCode())

        and:
        user.displayName == "New Name"
        user.isActive() // 変更なし
        user.passwordHash == "old-hash" // 変更なし
    }

    def "updateUser: isActive が true の場合はアクティブ化する"() {
        given:
        Long targetUserId = 1L
        def user = UserModel.reconstruct(
                targetUserId, "test@example.com", "hash", null, AuthProvider.LOCAL.code, null, "Name", "ja", true, null, null, false, null, null
        )

        when:
        service.updateUser(targetUserId, "Name", "ja", null, true, 99L)

        then:
        1 * userRepository.findById(targetUserId) >> Optional.of(user)
        user.isActive()
    }

    def "updateUser: ユーザーが見つからない場合は ResourceNotFoundException を投げる"() {
        given:
        Long targetUserId = 999L

        when:
        service.updateUser(targetUserId, "Name", "ja", null, true, 1L)

        then:
        1 * userRepository.findById(targetUserId) >> Optional.empty()
        thrown(ResourceNotFoundException)
    }
}
