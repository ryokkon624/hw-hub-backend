package com.hwhub.backend.application.service

import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.repository.UserRepository
import com.hwhub.backend.domain.storage.ObjectStorageClient
import com.hwhub.backend.infrastructure.s3.ObjectStorageConfig.UserIconStorageSettings
import spock.lang.Specification

import java.net.URL
import java.time.Duration
import java.util.Optional

class UserIconServiceSpec extends Specification {

    UserRepository userRepository = Mock()
    ObjectStorageClient objectStorageClient = Mock()
    UserIconStorageSettings storageSettings =
            new UserIconStorageSettings(
                    "bucket-name",
                    "user-icon",
                    Duration.ofSeconds(600)
            )

    UserIconService service = new UserIconService(
            userRepository,
            objectStorageClient,
            storageSettings
    )

    // ==================================
    // createUploadUrl
    // ==================================

    def "createUploadUrlはユーザが存在しない場合IllegalStateExceptionを投げる"() {
        given:
        Long userId = 10L

        when:
        service.createUploadUrl(userId, "icon.jpg", "image/jpeg")

        then:
        1 * userRepository.findById(userId) >> Optional.empty()
        thrown(IllegalStateException)
    }

    def "createUploadUrlは拡張子付きファイル名のときpresigned PUT URLとキーを返す"() {
        given:
        Long userId = 10L
        String fileName = "icon.jpg"
        String mimeType = "image/jpeg"

        def user = Mock(UserModel) {
            getUserId() >> userId
        }

        when:
        def result = service.createUploadUrl(userId, fileName, mimeType)

        then:
        1 * userRepository.findById(userId) >> Optional.of(user)

        1 * objectStorageClient.createPresignedPutUrl(
                "bucket-name",
                { String key ->
                    assert key == "user-icon/${userId}/icon.jpg"
                    true
                },
                mimeType,
                _ as Duration
        ) >> new URL("https://example.com/upload-url")

        and:
        result.uploadUrl == "https://example.com/upload-url"
        result.fileKey == "user-icon/${userId}/icon.jpg"
    }

    def "createUploadUrlは拡張子なしファイル名のとき拡張子なしのキーを生成する"() {
        given:
        Long userId = 11L
        String fileName = "icon"          // ドットなし
        String mimeType = "image/png"

        def user = Mock(UserModel) {
            getUserId() >> userId
        }

        when:
        def result = service.createUploadUrl(userId, fileName, mimeType)

        then:
        1 * userRepository.findById(userId) >> Optional.of(user)

        1 * objectStorageClient.createPresignedPutUrl(
                "bucket-name",
                { String key ->
                    assert key == "user-icon/${userId}/icon"   // 拡張子なし
                    true
                },
                mimeType,
                _ as Duration
        ) >> new URL("https://example.com/upload-noext")

        and:
        result.uploadUrl == "https://example.com/upload-noext"
        result.fileKey == "user-icon/${userId}/icon"
    }

    def "createUploadUrlはfileNameがnullのときも拡張子なしのキーを生成する"() {
        given:
        Long userId = 12L
        String mimeType = "image/png"

        def user = Mock(UserModel) {
            getUserId() >> userId
        }

        when:
        def result = service.createUploadUrl(userId, null, mimeType)

        then:
        1 * userRepository.findById(userId) >> Optional.of(user)

        1 * objectStorageClient.createPresignedPutUrl(
                "bucket-name",
                { String key ->
                    assert key == "user-icon/${userId}/icon"   // ext=""
                    true
                },
                mimeType,
                _ as Duration
        ) >> new URL("https://example.com/upload-nullname")

        and:
        result.uploadUrl == "https://example.com/upload-nullname"
        result.fileKey == "user-icon/${userId}/icon"
    }

    // ==================================
    // updateUserIcon
    // ==================================

    def "updateUserIconはユーザが存在しない場合IllegalStateExceptionを投げる"() {
        given:
        Long userId = 20L

        when:
        service.updateUserIcon(userId, "new-key")

        then:
        1 * userRepository.findById(userId) >> Optional.empty()
        thrown(IllegalStateException)
    }

    def "updateUserIconは旧アイコンキーがnullの場合deleteObjectを呼ばずに更新する"() {
        given:
        Long userId = 21L
        String newKey = "new-key"

        def user = Mock(UserModel) {
            getProfileImageKey() >> null
        }

        when:
        service.updateUserIcon(userId, newKey)

        then:
        1 * userRepository.findById(userId) >> Optional.of(user)

        0 * objectStorageClient.deleteObject(_, _)

        1 * user.changeProfileImageKey(newKey)
        1 * userRepository.updateProfileImgKey(user, ProgramType.ONL_USRICON.code)
    }

    def "updateUserIconは旧アイコンキーが異なる場合deleteObjectを呼び出してから更新する"() {
        given:
        Long userId = 22L
        String oldKey = "old-key"
        String newKey = "new-key"

        def user = Mock(UserModel) {
            getProfileImageKey() >> oldKey
        }

        when:
        service.updateUserIcon(userId, newKey)

        then:
        1 * userRepository.findById(userId) >> Optional.of(user)

        1 * objectStorageClient.deleteObject("bucket-name", oldKey)
        1 * user.changeProfileImageKey(newKey)
        1 * userRepository.updateProfileImgKey(user, ProgramType.ONL_USRICON.code)
    }

    def "updateUserIconは旧アイコン削除に失敗しても処理を継続する"() {
        given:
        Long userId = 23L
        String oldKey = "old-key"
        String newKey = "new-key"

        def user = Mock(UserModel) {
            getProfileImageKey() >> oldKey
        }

        when:
        service.updateUserIcon(userId, newKey)

        then:
        1 * userRepository.findById(userId) >> Optional.of(user)

        1 * objectStorageClient.deleteObject("bucket-name", oldKey) >> { throw new RuntimeException("S3 error") }

        // 例外が握りつぶされ、以降の処理が実行されること
        1 * user.changeProfileImageKey(newKey)
        1 * userRepository.updateProfileImgKey(user, ProgramType.ONL_USRICON.code)
    }

    // ==================================
    // getIconUrl(Long)
    // ==================================

    def "getIconUrl(Long)はユーザが存在しない場合IllegalStateExceptionを投げる"() {
        given:
        Long userId = 30L

        when:
        service.getIconUrl(userId)

        then:
        1 * userRepository.findById(userId) >> Optional.empty()
        thrown(IllegalStateException)
    }

    def "getIconUrl(Long)はプロフィール画像キーがnullの場合nullを返しpresigned GETを呼ばない"() {
        given:
        Long userId = 31L

        def user = Mock(UserModel) {
            getProfileImageKey() >> null
        }

        when:
        def result = service.getIconUrl(userId)

        then:
        1 * userRepository.findById(userId) >> Optional.of(user)
        0 * objectStorageClient.createPresignedGetUrl(_, _, _)

        and:
        result == null
    }

    def "getIconUrl(Long)はプロフィール画像キーが空文字の場合nullを返しpresigned GETを呼ばない"() {
        given:
        Long userId = 32L

        def user = Mock(UserModel) {
            getProfileImageKey() >> "  "   // blank
        }

        when:
        def result = service.getIconUrl(userId)

        then:
        1 * userRepository.findById(userId) >> Optional.of(user)
        0 * objectStorageClient.createPresignedGetUrl(_, _, _)

        and:
        result == null
    }

    def "getIconUrl(Long)はプロフィール画像キーが存在する場合presigned GET URLを返す"() {
        given:
        Long userId = 33L
        String fileKey = "user-icon/33/icon.jpg"

        def user = Mock(UserModel) {
            getProfileImageKey() >> fileKey
        }

        when:
        def result = service.getIconUrl(userId)

        then:
        1 * userRepository.findById(userId) >> Optional.of(user)

        1 * objectStorageClient.createPresignedGetUrl(
                "bucket-name",
                fileKey,
                _ as Duration
        ) >> new URL("https://example.com/icon-33")

        and:
        result == "https://example.com/icon-33"
    }

    // ==================================
    // getIconUrl(String) 直接呼び出し
    // ==================================

    def "getIconUrl(String)はfileKeyがnullの場合nullを返しpresigned GETを呼ばない"() {
        when:
        def result = service.getIconUrl(null)

        then:
        0 * objectStorageClient.createPresignedGetUrl(_, _, _)
        result == null
    }

    def "getIconUrl(String)はfileKeyが空文字の場合nullを返しpresigned GETを呼ばない"() {
        when:
        def result = service.getIconUrl("  ")

        then:
        0 * objectStorageClient.createPresignedGetUrl(_, _, _)
        result == null
    }

    def "getIconUrl(String)はfileKeyが指定されている場合presigned GET URLを返す"() {
        given:
        String fileKey = "user-icon/99/icon.jpg"

        when:
        def result = service.getIconUrl(fileKey)

        then:
        1 * objectStorageClient.createPresignedGetUrl(
                "bucket-name",
                fileKey,
                _ as Duration
        ) >> new URL("https://example.com/icon-99")

        and:
        result == "https://example.com/icon-99"
    }
}
