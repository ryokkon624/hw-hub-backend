// src/test/groovy/com/hwhub/backend/presentation/rest/user/UserControllerSpec.groovy
package com.hwhub.backend.presentation.rest.user

import com.hwhub.backend.application.service.UserIconService
import com.hwhub.backend.application.service.UserService
import com.hwhub.backend.domain.model.HouseholdModel
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.enums.AuthProvider
import com.hwhub.backend.presentation.rest.user.dto.ChangePasswordRequest
import com.hwhub.backend.presentation.rest.user.dto.CreateIconUploadUrlRequest
import com.hwhub.backend.presentation.rest.user.dto.CreateIconUploadUrlResponse
import com.hwhub.backend.presentation.rest.user.dto.UpdateIconRequest
import com.hwhub.backend.presentation.rest.user.dto.UpdateUserProfileRequest
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.server.ResponseStatusException
import spock.lang.Specification

class UserControllerSpec extends Specification {


    UserService userService = Mock()
    UserIconService userIconService = Mock()

    com.hwhub.backend.presentation.rest.auth.GoogleOAuthLinkHelper linkHelper = Mock()

    UserController controller = new UserController(userService, userIconService, linkHelper)

    // ----------------------------------------------------
    // getUserHouseholds
    // ----------------------------------------------------

    def "getUserHouseholds は authentication が null の場合 UNAUTHORIZED を投げる"() {
        when:
        controller.getUserHouseholds(null)

        then:
        def ex = thrown(ResponseStatusException)
        ex.statusCode == HttpStatus.UNAUTHORIZED
        ex.reason == "Unauthenticated"
        0 * userService._
    }

    def "getUserHouseholds は認証ユーザIDを元に UserService.getHouseholds を呼び DTO リストを返す"() {
        given:
        Long userId = 10L
        Authentication auth = Mock()
        auth.getName() >> String.valueOf(userId)

        def h1 = HouseholdModel.reconstruct(1L, "家1", userId)
        def h2 = HouseholdModel.reconstruct(2L, "家2", userId)

        when:
        def result = controller.getUserHouseholds(auth)

        then:
        1 * userService.getHouseholds(userId) >> [h1, h2]

        and:
        result.size() == 2
        // DTO の中身までは深追いせず、サイズのみ検証（マッピング経路のカバレッジ目的）
    }

    // ----------------------------------------------------
    // getProfile
    // ----------------------------------------------------

    def "getProfile は認証ユーザIDを元に UserService.getProfile を呼びレスポンスDTOを返す"() {
        given:
        Long userId = 20L
        Authentication auth = Mock()
        auth.getName() >> String.valueOf(userId)

        def user = UserModel.reconstruct(
                userId,
                "user@example.com",
                "hashed",
                null,
                AuthProvider.LOCAL.code,
                null,
                "Taro",
                "ja",
                true,
                "icon-key",
                null,
                true
        )

        when:
        def response = controller.getProfile(auth)

        then:
        1 * userService.getProfile(userId) >> user

        and:
        response != null
        // プロパティの細かい中身は DTO 実装に依存するのでここでは踏み込まない
    }

    // ----------------------------------------------------
    // updateProfile
    // ----------------------------------------------------

    def "updateProfile は認証ユーザIDとリクエストから UserService.updateProfile を呼びレスポンスDTOを返す"() {
        given:
        Long userId = 30L
        Authentication auth = Mock()
        auth.getName() >> String.valueOf(userId)

        def req = new UpdateUserProfileRequest("Hanako", "en")

        def updated = UserModel.reconstruct(
                userId,
                "hanako@example.com",
                "hashed",
                null,
                AuthProvider.LOCAL.code,
                null,
                "Hanako",
                "en",
                true,
                "icon-key",
                null,
                true
        )

        when:
        def response = controller.updateProfile(auth, req)

        then:
        1 * userService.updateProfile(userId, "Hanako", "en") >> updated

        and:
        response != null
    }

    // ----------------------------------------------------
    // createIconUploadUrl
    // ----------------------------------------------------

    def "createIconUploadUrl は認証ユーザIDとリクエストから UserIconService.createUploadUrl を呼び結果をDTOに詰めて返す"() {
        given:
        Long userId = 40L
        Authentication auth = Mock()
        auth.getName() >> String.valueOf(userId)

        def req = new CreateIconUploadUrlRequest("icon.png", "image/png")

        def serviceResult =
                new UserIconService.CreateIconUploadUrlResult(
                        "https://example.com/upload",
                        "user-icon/40/icon.png"
                )

        when:
        CreateIconUploadUrlResponse response = controller.createIconUploadUrl(req, auth)

        then:
        1 * userIconService.createUploadUrl(userId, "icon.png", "image/png") >> serviceResult

        and:
        response.uploadUrl() == "https://example.com/upload"
        response.fileKey() == "user-icon/40/icon.png"
    }

    // ----------------------------------------------------
    // updateIcon
    // ----------------------------------------------------

    def "updateIcon は認証ユーザIDと fileKey で UserIconService.updateUserIcon を呼ぶ"() {
        given:
        Long userId = 50L
        Authentication auth = Mock()
        auth.getName() >> String.valueOf(userId)

        def req = new UpdateIconRequest("user-icon/50/icon.jpg")

        when:
        controller.updateIcon(req, auth)

        then:
        1 * userIconService.updateUserIcon(userId, "user-icon/50/icon.jpg")
    }
    // ----------------------------------------------------
    // deleteAccount
    // ----------------------------------------------------

    def "deleteAccount は認証ユーザIDで UserService.deleteAccount を呼ぶ"() {
        given:
        Long userId = 60L
        Authentication auth = Mock()
        auth.getName() >> String.valueOf(userId)

        when:
        controller.deleteAccount(auth)

        then:
        1 * userService.deleteAccount(userId)
    }

    // ----------------------------------------------------
    // changePassword
    // ----------------------------------------------------

    def "changePassword はリクエストパラメータで UserService.changePassword を呼ぶ"() {
        given:
        def req = new ChangePasswordRequest("old", "new")
        
        when:
        def response = controller.changePassword(req)

        then:
        1 * userService.changePassword("old", "new")
        response.statusCode == HttpStatus.NO_CONTENT
    }

    // ----------------------------------------------------
    // startGoogleLink
    // ----------------------------------------------------

    def "startGoogleLink は認証ユーザIDでステートを生成し、Cookieを設定し、URLを返す"() {
        given:
        Long userId = 70L
        Authentication auth = Mock()
        auth.getName() >> String.valueOf(userId)
        def responseMock = Mock(jakarta.servlet.http.HttpServletResponse)

        def state = "generated-state"
        def url = "https://accounts.google.com/..."

        when:
        def result = controller.startGoogleLink(auth, responseMock)

        then:
        1 * linkHelper.generateStateForLink(userId) >> state
        1 * linkHelper.setStateCookie(responseMock, state)
        1 * linkHelper.buildAuthorizationUrl(state) >> url

        result.statusCode == HttpStatus.OK
        result.body.authorizationUrl() == url
    }

    def "startGoogleLink は認証がない場合 UNAUTHORIZED を投げる"() {
        given:
        def responseMock = Mock(jakarta.servlet.http.HttpServletResponse)

        when:
        controller.startGoogleLink(null, responseMock)

        then:
        def ex = thrown(ResponseStatusException)
        ex.statusCode == HttpStatus.UNAUTHORIZED
    }

    // ==================================
    // getNotificationSettings
    // ==================================

    def "getNotificationSettingsは通知設定を返す"() {
        given:
        def auth = Mock(Authentication)
        auth.getName() >> "1"
        def groupMap = new LinkedHashMap()
        groupMap.put(com.hwhub.backend.domain.enums.NotificationGroup.HOUSEHOLD, true)
        groupMap.put(com.hwhub.backend.domain.enums.NotificationGroup.TASK_ASSIGNMENT, false)
        def settingsResult = new com.hwhub.backend.application.service.UserService.NotificationSettingsResult(true, groupMap)

        when:
        def result = controller.getNotificationSettings(auth)

        then:
        1 * userService.getSettings(1L) >> settingsResult

        and:
        result.notificationEnabled() == true
        result.groupSettings().get("100") == true
        result.groupSettings().get("200") == false
    }

    // ==================================
    // updateNotificationSettings
    // ==================================

    def "updateNotificationSettingsは通知設定を更新して返す"() {
        given:
        def auth = Mock(Authentication)
        auth.getName() >> "1"

        def reqGroupSettings = new LinkedHashMap()
        reqGroupSettings.put("100", true)
        reqGroupSettings.put("200", false)
        def req = new com.hwhub.backend.presentation.rest.user.dto.UpdateNotificationSettingsRequest(true, reqGroupSettings)

        def resultGroupMap = new LinkedHashMap()
        resultGroupMap.put(com.hwhub.backend.domain.enums.NotificationGroup.HOUSEHOLD, true)
        resultGroupMap.put(com.hwhub.backend.domain.enums.NotificationGroup.TASK_ASSIGNMENT, false)
        def settingsResult = new com.hwhub.backend.application.service.UserService.NotificationSettingsResult(true, resultGroupMap)

        when:
        def result = controller.updateNotificationSettings(req, auth)

        then:
        1 * userService.updateNotificationEnabled(1L, true, _) >> settingsResult

        and:
        result.notificationEnabled() == true
        result.groupSettings().get("100") == true
        result.groupSettings().get("200") == false
    }

    def "updateNotificationSettingsはgroupSettingsがnullの場合も正常に動作する"() {
        given:
        def auth = Mock(Authentication)
        auth.getName() >> "1"
        def req = new com.hwhub.backend.presentation.rest.user.dto.UpdateNotificationSettingsRequest(false, null)

        def resultGroupMap = new LinkedHashMap()
        resultGroupMap.put(com.hwhub.backend.domain.enums.NotificationGroup.HOUSEHOLD, false)
        resultGroupMap.put(com.hwhub.backend.domain.enums.NotificationGroup.TASK_ASSIGNMENT, false)
        def settingsResult = new com.hwhub.backend.application.service.UserService.NotificationSettingsResult(false, resultGroupMap)

        when:
        def result = controller.updateNotificationSettings(req, auth)

        then:
        1 * userService.updateNotificationEnabled(1L, false, _) >> { args ->
            // 引数のgroupSettingsが空マップであること
            assert args[2] instanceof Map
            assert args[2].isEmpty()
            return settingsResult
        }

        and:
        result.notificationEnabled() == false
    }
}
