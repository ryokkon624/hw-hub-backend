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
    com.hwhub.backend.domain.repository.HouseholdMemberRepository householdMemberRepository = Mock()

    UserService service = new UserService(
            userRepository,
            userIconService,
            householdMemberRepository
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
    // ==================================
    // deleteAccount
    // ==================================

    def "deleteAccountは自分がOWNERで他にもメンバーがいる場合IllegalArgumentExceptionを投げる"() {
        given:
        Long userId = 40L
        Long householdId = 100L

        // 自分はOWNER
        def household = Mock(HouseholdModel) {
            getHouseholdId() >> householdId
            isOwner(userId) >> true
            getName() >> "My Home"
        }

        // メンバーは複数人いる
        def members = [
                Mock(com.hwhub.backend.domain.model.HouseholdMemberModel),
                Mock(com.hwhub.backend.domain.model.HouseholdMemberModel)
        ]

        when:
        service.deleteAccount(userId)

        then:
        1 * userRepository.findHouseholdsByUserId(userId) >> [household]
        1 * householdMemberRepository.findActiveByHouseholdId(householdId) >> members
        
        0 * userRepository.deactivate(_, _)
        0 * householdMemberRepository.deleteByUserId(_)
        
        thrown(IllegalArgumentException)
    }

    def "deleteAccountは自分がOWNERでもメンバーが自分だけなら退会可能"() {
        given:
        Long userId = 41L
        Long householdId = 101L

        // 自分はOWNER
        def household = Mock(HouseholdModel) {
            getHouseholdId() >> householdId
            isOwner(userId) >> true
        }

        // メンバーは自分だけ
        def members = [
                Mock(com.hwhub.backend.domain.model.HouseholdMemberModel)
        ]

        when:
        service.deleteAccount(userId)

        then:
        1 * userRepository.findHouseholdsByUserId(userId) >> [household]
        1 * householdMemberRepository.findActiveByHouseholdId(householdId) >> members

        // 退会処理が進む
        1 * userRepository.deactivate(userId, ProgramType.ONL_USR.code)
        1 * householdMemberRepository.deleteByUserId(userId)
    }

    def "deleteAccountは一般メンバーなら即退会可能"() {
        given:
        Long userId = 42L
        Long householdId = 102L

        // 自分はMEMBER (OWNERではない)
        def household = Mock(HouseholdModel) {
            getHouseholdId() >> householdId
            isOwner(userId) >> false
        }

        when:
        service.deleteAccount(userId)

        then:
        1 * userRepository.findHouseholdsByUserId(userId) >> [household]
        
        // メンバー数チェックはスキップされる
        0 * householdMemberRepository.findActiveByHouseholdId(_)

        // 退会処理が進む
        1 * userRepository.deactivate(userId, ProgramType.ONL_USR.code)
        1 * householdMemberRepository.deleteByUserId(userId)
    }
}
