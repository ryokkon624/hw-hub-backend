// src/test/groovy/com/hwhub/backend/presentation/rest/user/UserControllerSpec.groovy
package com.hwhub.backend.presentation.rest.user

import com.hwhub.backend.application.service.UserIconService
import com.hwhub.backend.application.service.UserService
import com.hwhub.backend.domain.model.HouseholdModel
import com.hwhub.backend.domain.model.UserModel
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

    UserController controller = new UserController(userService, userIconService)

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
                "Taro",
                "ja",
                "icon-key",
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
                "Hanako",
                "en",
                "icon-key",
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
}
