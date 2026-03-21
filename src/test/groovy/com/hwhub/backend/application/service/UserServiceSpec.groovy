package com.hwhub.backend.application.service

import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.model.HouseholdModel
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.repository.UserRepository
import com.hwhub.backend.domain.oauth.google.GoogleTokenResponse
import com.hwhub.backend.domain.oauth.google.GoogleUserInfo
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException
import spock.lang.Specification

import java.util.Optional

class UserServiceSpec extends Specification {

    UserRepository userRepository = Mock()
    com.hwhub.backend.domain.repository.UserNotificationSettingRepository settingRepository = Mock()
    UserIconService userIconService = Mock()
    com.hwhub.backend.domain.repository.HouseholdMemberRepository householdMemberRepository = Mock()
    org.springframework.security.crypto.password.PasswordEncoder passwordEncoder = Mock()
    AuthUserResolver authUserResolver = Mock()
    com.hwhub.backend.application.service.oauth.GoogleOAuthService googleOAuthService = Mock()

    UserService service = new UserService(
            userRepository,
            settingRepository,
            userIconService,
            householdMemberRepository,
            passwordEncoder,
            authUserResolver,
            googleOAuthService
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

    // ==================================
    // changePassword
    // ==================================

    def "changePasswordはユーザが見つからない場合、IllegalArgumentExceptionをthrowする"() {
        given:
        Long userId = 50L
        String currentPass = "old"
        String newPass = "new"

        when:
        service.changePassword(currentPass, newPass)

        then:
        1 * authUserResolver.requireUserId() >> userId
        1 * userRepository.findById(userId) >> Optional.empty()
        thrown(IllegalArgumentException)
    }

    def "changePasswordは現在のパスワードが一致しない場合、CurrentPasswordInvalidExceptionをthrowする"() {
        given:
        Long userId = 51L
        def user = Mock(UserModel) {
            getPasswordHash() >> "hashedOld"
        }

        when:
        service.changePassword("wrong", "new")

        then:
        1 * authUserResolver.requireUserId() >> userId
        1 * userRepository.findById(userId) >> Optional.of(user)
        1 * passwordEncoder.matches("wrong", "hashedOld") >> false
        thrown(com.hwhub.backend.presentation.rest.common.CurrentPasswordInvalidException)
    }

    def "changePasswordは新しいパスワードが古いパスワードと同じ場合、PasswordSameAsOldExceptionをthrowする"() {
        given:
        Long userId = 52L
        def user = Mock(UserModel) {
            getPasswordHash() >> "hashedOld"
        }

        when:
        service.changePassword("old", "old")

        then:
        1 * authUserResolver.requireUserId() >> userId
        1 * userRepository.findById(userId) >> Optional.of(user)
        1 * passwordEncoder.matches("old", "hashedOld") >> true
        // 2nd check
        1 * passwordEncoder.matches("old", "hashedOld") >> true
        thrown(com.hwhub.backend.presentation.rest.common.PasswordSameAsOldException)
    }

    def "changePasswordは有効な場合、パスワードハッシュを更新する"() {
        given:
        Long userId = 53L
        def user = Mock(UserModel) {
            getPasswordHash() >> "hashedOld"
        }

        when:
        service.changePassword("old", "new")

        then:
        1 * authUserResolver.requireUserId() >> userId
        1 * userRepository.findById(userId) >> Optional.of(user)
        
        // 1. match current
        1 * passwordEncoder.matches("old", "hashedOld") >> true
        // 2. match new (should be false)
        1 * passwordEncoder.matches("new", "hashedOld") >> false
        
        // 3. encode new
        1 * passwordEncoder.encode("new") >> "hashedNew"
        
        1 * user.changePasswordHash("hashedNew", _)
        1 * userRepository.updatePassword(user, userId, ProgramType.ONL_USR.code)
    }

    // ==================================
    // linkGoogleAccount
    // ==================================

    def "linkGoogleAccountは正常にGoogleアカウントを連携する"() {
        given:
        Long loginUserId = 60L
        String code = "auth-code"
        String token = "access-token"
        String sub = "google-sub"
        String email = "test@example.com"
        String name = "Google Name"

        def tokenResponse = new GoogleTokenResponse(accessToken: token)
        def userInfo = new GoogleUserInfo(sub: sub, email: email, name: name)
        
        def user = Mock(UserModel) {
            getUserId() >> loginUserId
            getAuthProvider() >> "LOCAL"
            getDisplayName() >> "Local Name"
        }

        when:
        service.linkGoogleAccount(loginUserId, code)

        then:
        1 * googleOAuthService.exchangeCodeForToken(code) >> tokenResponse
        1 * googleOAuthService.fetchUserInfo(token) >> userInfo

        1 * userRepository.findById(loginUserId) >> Optional.of(user)
        
        // 重複チェック
        1 * userRepository.findByAuthProviderAndAuthProviderId("GOOGLE", sub) >> Optional.empty()

        // リンク処理
        1 * userRepository.linkGoogleAccount(loginUserId, sub, email, name, ProgramType.ONL_USR.code)
    }

    def "linkGoogleAccountは既に自身が連携済みの場合GoogleAccountAlreadyLinkedExceptionを投げる"() {
        given:
        Long loginUserId = 61L
        String code = "code"
        
        def tokenResponse = new GoogleTokenResponse(accessToken: "token")
        def userInfo = new GoogleUserInfo(sub: "sub", email: "email")

        def user = Mock(UserModel) {
            getAuthProvider() >> "GOOGLE"
            getAuthProviderId() >> "sub"
        }

        when:
        service.linkGoogleAccount(loginUserId, code)

        then:
        1 * googleOAuthService.exchangeCodeForToken(code) >> tokenResponse
        1 * googleOAuthService.fetchUserInfo("token") >> userInfo
        1 * userRepository.findById(loginUserId) >> Optional.of(user)

        thrown(com.hwhub.backend.presentation.rest.common.GoogleAccountAlreadyLinkedException)
    }

    def "linkGoogleAccountは別のユーザが既にそのGoogleアカウントを使用している場合GoogleSubAlreadyUsedExceptionを投げる"() {
        given:
        Long loginUserId = 62L
        Long otherUserId = 99L
        String code = "code"
        String sub = "sub"

        def tokenResponse = new GoogleTokenResponse(accessToken: "token")
        def userInfo = new GoogleUserInfo(sub: sub, email: "email")

        def user = Mock(UserModel) {
            getUserId() >> loginUserId
            getAuthProvider() >> "LOCAL"
        }
        
        def otherUser = Mock(UserModel) {
            getUserId() >> otherUserId
        }

        when:
        service.linkGoogleAccount(loginUserId, code)

        then:
        1 * googleOAuthService.exchangeCodeForToken(code) >> tokenResponse
        1 * googleOAuthService.fetchUserInfo("token") >> userInfo
        1 * userRepository.findById(loginUserId) >> Optional.of(user)

        // 重複チェックで別ユーザが見つかる
        1 * userRepository.findByAuthProviderAndAuthProviderId("GOOGLE", sub) >> Optional.of(otherUser)

        thrown(com.hwhub.backend.presentation.rest.common.GoogleSubAlreadyUsedException)
    }

    // ==================================
    // getSettings
    // ==================================

    def "getSettingsは通知設定を含むNotificationSettingsResultを返す"() {
        given:
        Long userId = 70L

        when:
        def result = service.getSettings(userId)

        then:
        1 * userRepository.isNotificationEnabled(userId) >> true
        1 * settingRepository.findEnabled(userId, com.hwhub.backend.domain.enums.NotificationGroup.HOUSEHOLD) >> Optional.empty()
        1 * settingRepository.findEnabled(userId, com.hwhub.backend.domain.enums.NotificationGroup.TASK_ASSIGNMENT) >> Optional.of(false)
        1 * settingRepository.findEnabled(userId, com.hwhub.backend.domain.enums.NotificationGroup.INQUIRY) >> Optional.empty()

        and:
        result.notificationEnabled() == true
        result.groupSettings().get(com.hwhub.backend.domain.enums.NotificationGroup.HOUSEHOLD) == true
        result.groupSettings().get(com.hwhub.backend.domain.enums.NotificationGroup.TASK_ASSIGNMENT) == false
        result.groupSettings().get(com.hwhub.backend.domain.enums.NotificationGroup.INQUIRY) == true
    }

    def "getSettingsは通知無効の場合、全グループがfalseのNotificationSettingsResultを返す"() {
        given:
        Long userId = 71L

        when:
        def result = service.getSettings(userId)

        then:
        1 * userRepository.isNotificationEnabled(userId) >> false
        // buildNotificationGroupMapでnotificationEnabled=falseの場合、全グループfalse
        0 * settingRepository.findEnabled(_, _)

        and:
        result.notificationEnabled() == false
        result.groupSettings().get(com.hwhub.backend.domain.enums.NotificationGroup.HOUSEHOLD) == false
        result.groupSettings().get(com.hwhub.backend.domain.enums.NotificationGroup.TASK_ASSIGNMENT) == false
    }

    // ==================================
    // updateNotificationEnabled
    // ==================================

    def "updateNotificationEnabledは通知有効でグループ設定を更新する"() {
        given:
        Long userId = 80L
        def groupSettings = [
            (com.hwhub.backend.domain.enums.NotificationGroup.HOUSEHOLD): true,
            (com.hwhub.backend.domain.enums.NotificationGroup.TASK_ASSIGNMENT): false
        ]
        // buildNotificationGroupMap用のスタブ
        userRepository.isNotificationEnabled(userId) >> true
        settingRepository.findEnabled(userId, com.hwhub.backend.domain.enums.NotificationGroup.HOUSEHOLD) >> Optional.empty()
        settingRepository.findEnabled(userId, com.hwhub.backend.domain.enums.NotificationGroup.TASK_ASSIGNMENT) >> Optional.of(false)
        settingRepository.findEnabled(userId, com.hwhub.backend.domain.enums.NotificationGroup.INQUIRY) >> Optional.empty()

        when:
        def result = service.updateNotificationEnabled(userId, true, groupSettings)

        then:
        // グローバル設定の更新
        1 * userRepository.updateNotificationEnabled(userId, true, userId, com.hwhub.backend.domain.enums.ProgramType.ONL_USR.getCode())

        // enabled=trueのグループは差分削除
        1 * settingRepository.delete(userId, com.hwhub.backend.domain.enums.NotificationGroup.HOUSEHOLD)

        // enabled=falseのグループは差分登録
        1 * settingRepository.upsert(userId, com.hwhub.backend.domain.enums.NotificationGroup.TASK_ASSIGNMENT, false, userId, com.hwhub.backend.domain.enums.ProgramType.ONL_USR.getCode())

        and:
        result.notificationEnabled() == true
    }

    def "updateNotificationEnabledは通知無効の場合、グループ設定を更新しない"() {
        given:
        Long userId = 81L
        def groupSettings = [
            (com.hwhub.backend.domain.enums.NotificationGroup.HOUSEHOLD): true
        ]

        when:
        def result = service.updateNotificationEnabled(userId, false, groupSettings)

        then:
        // グローバル設定の更新
        1 * userRepository.updateNotificationEnabled(userId, false, userId, com.hwhub.backend.domain.enums.ProgramType.ONL_USR.getCode())

        // 通知無効の場合、グループ設定は変更されない
        0 * settingRepository.delete(_, _)
        0 * settingRepository.upsert(_, _, _, _, _)

        and:
        result.notificationEnabled() == false
    }

    def "updateNotificationEnabledはenabled=nullのグループ設定をスキップする"() {
        given:
        Long userId = 82L
        def groupSettings = new LinkedHashMap()
        groupSettings.put(com.hwhub.backend.domain.enums.NotificationGroup.HOUSEHOLD, null)
        groupSettings.put(com.hwhub.backend.domain.enums.NotificationGroup.TASK_ASSIGNMENT, true)

        when:
        def result = service.updateNotificationEnabled(userId, true, groupSettings)

        then:
        1 * userRepository.updateNotificationEnabled(userId, true, userId, com.hwhub.backend.domain.enums.ProgramType.ONL_USR.getCode())

        // null値のグループはスキップ
        0 * settingRepository.delete(userId, com.hwhub.backend.domain.enums.NotificationGroup.HOUSEHOLD)
        0 * settingRepository.upsert(userId, com.hwhub.backend.domain.enums.NotificationGroup.HOUSEHOLD, _, _, _)

        // true値のグループは差分削除
        1 * settingRepository.delete(userId, com.hwhub.backend.domain.enums.NotificationGroup.TASK_ASSIGNMENT)

        // buildNotificationGroupMapの呼び出し
        _ * userRepository.isNotificationEnabled(userId) >> true
        _ * settingRepository.findEnabled(userId, _) >> Optional.empty()
    }
}
