package com.hwhub.backend.application.service

import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.model.HouseholdModel
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.repository.UserRepository
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException
import spock.lang.Specification

import java.util.Optional

class UserServiceSpec extends Specification {

    UserRepository userRepository = Mock()
    UserIconService userIconService = Mock()

    UserService service = new UserService(
            userRepository,
            userIconService
    )

    // ==================================
    // getHouseholds
    // ==================================

    def "getHouseholdsはユーザIDに紐づく世帯一覧を返す"() {
        given:
        Long userId = 10L
        def households = [Mock(HouseholdModel), Mock(HouseholdModel)]

        when:
        def result = service.getHouseholds(userId)

        then:
        1 * userRepository.findHouseholdsByUserId(userId) >> households
        result == households
    }

    // ==================================
    // getProfile
    // ==================================

    def "getProfileはユーザが存在しない場合ResourceNotFoundExceptionを投げる"() {
        given:
        Long userId = 20L

        when:
        service.getProfile(userId)

        then:
        1 * userRepository.findById(userId) >> Optional.empty()
        thrown(ResourceNotFoundException)
    }

    def "getProfileはユーザ取得後にiconUrlを設定して返す"() {
        given:
        Long userId = 21L
        String profileImageKey = "user-icon/21/icon.jpg"

        def user = Mock(UserModel) {
            getProfileImageKey() >> profileImageKey
        }

        when:
        def result = service.getProfile(userId)

        then:
        1 * userRepository.findById(userId) >> Optional.of(user)

        1 * userIconService.getIconUrl(profileImageKey) >> "https://example.com/icon-21"
        1 * user.setIconUrl("https://example.com/icon-21")

        and:
        result == user
    }

    // ==================================
    // updateProfile
    // ==================================

    def "updateProfileはユーザが存在しない場合ResourceNotFoundExceptionを投げる"() {
        given:
        Long userId = 30L

        when:
        service.updateProfile(userId, "New Name", "ja")

        then:
        1 * userRepository.findById(userId) >> Optional.empty()
        thrown(ResourceNotFoundException)
    }

    def "updateProfileはプロフィールを更新しiconUrlを設定して返す"() {
        given:
        Long userId = 31L
        String newName = "New Display Name"
        String newLocale = "en"
        String profileImageKey = "user-icon/31/icon.png"

        def user = Mock(UserModel) {
            getProfileImageKey() >> profileImageKey
        }

        when:
        def result = service.updateProfile(userId, newName, newLocale)

        then:
        1 * userRepository.findById(userId) >> Optional.of(user)

        // プロフィール更新
        1 * user.changeProfile(newName, newLocale)

        // 永続化
        1 * userRepository.updateForEnduser(user, userId, ProgramType.ONL_USR.code)

        // アイコンURL付与
        1 * userIconService.getIconUrl(profileImageKey) >> "https://example.com/icon-31"
        1 * user.setIconUrl("https://example.com/icon-31")

        and:
        result == user
    }
}
