package com.hwhub.backend.application.service

import org.springframework.security.access.AccessDeniedException
import spock.lang.Specification
import spock.lang.Unroll

class UploadImagePolicySpec extends Specification {

    // ==================================
    // sanitizeExtension
    // ==================================

    @Unroll
    def "sanitizeExtensionは許可された拡張子(#fileName)を小文字のドット付きで返す"() {
        expect:
        UploadImagePolicy.sanitizeExtension(fileName) == expected

        where:
        fileName      || expected
        "photo.jpg"   || ".jpg"
        "photo.JPG"   || ".jpg"
        "photo.jpeg"  || ".jpeg"
        "photo.png"   || ".png"
        "photo.GIF"   || ".gif"
        "photo.webp"  || ".webp"
    }

    def "sanitizeExtensionは拡張子なしファイル名の場合空文字を返す"() {
        expect:
        UploadImagePolicy.sanitizeExtension("noextension") == ""
    }

    def "sanitizeExtensionはfileNameがnullの場合空文字を返す"() {
        expect:
        UploadImagePolicy.sanitizeExtension(null) == ""
    }

    def "sanitizeExtensionは許可されていない拡張子の場合IllegalArgumentExceptionを投げる"() {
        when:
        UploadImagePolicy.sanitizeExtension("evil.exe")

        then:
        thrown(IllegalArgumentException)
    }

    def "sanitizeExtensionはパストラバーサルを含むファイル名の場合パス区切り以降のみを見て安全に判定する"() {
        expect:
        // "a.jpg/../../evil" のようなファイル名でも、最後のパス区切り以降("evil")だけを見るため
        // 拡張子なし判定になり、path traversal がキーに混入しない
        UploadImagePolicy.sanitizeExtension("a.jpg/../../evil") == ""
    }

    def "sanitizeExtensionはパス区切りを含んでいても最後のセグメントの拡張子が許可されていれば通す"() {
        expect:
        UploadImagePolicy.sanitizeExtension("../../evil.jpg") == ".jpg"
    }

    // ==================================
    // assertAllowedMimeType
    // ==================================

    @Unroll
    def "assertAllowedMimeTypeは許可されたmimeType(#mimeType)なら例外を投げない"() {
        when:
        UploadImagePolicy.assertAllowedMimeType(mimeType)

        then:
        noExceptionThrown()

        where:
        mimeType << ["image/jpeg", "image/png", "image/gif", "image/webp"]
    }

    def "assertAllowedMimeTypeは許可されていないmimeTypeの場合IllegalArgumentExceptionを投げる"() {
        when:
        UploadImagePolicy.assertAllowedMimeType("text/html")

        then:
        thrown(IllegalArgumentException)
    }

    def "assertAllowedMimeTypeはnullの場合IllegalArgumentExceptionを投げる"() {
        when:
        UploadImagePolicy.assertAllowedMimeType(null)

        then:
        thrown(IllegalArgumentException)
    }

    // ==================================
    // assertKeyWithinPrefix
    // ==================================

    def "assertKeyWithinPrefixはプレフィックス配下の単一セグメントキーなら例外を投げない"() {
        when:
        UploadImagePolicy.assertKeyWithinPrefix("shopping-item/1/100/uuid.jpg", "shopping-item/1/100/")

        then:
        noExceptionThrown()
    }

    def "assertKeyWithinPrefixはプレフィックスに一致しない場合AccessDeniedExceptionを投げる"() {
        when:
        UploadImagePolicy.assertKeyWithinPrefix("shopping-item/2/100/uuid.jpg", "shopping-item/1/100/")

        then:
        thrown(AccessDeniedException)
    }

    def "assertKeyWithinPrefixはfileKeyがnullの場合AccessDeniedExceptionを投げる"() {
        when:
        UploadImagePolicy.assertKeyWithinPrefix(null, "shopping-item/1/100/")

        then:
        thrown(AccessDeniedException)
    }

    def "assertKeyWithinPrefixはプレフィックス以降に「/」が含まれる場合AccessDeniedExceptionを投げる"() {
        when:
        UploadImagePolicy.assertKeyWithinPrefix("shopping-item/1/100/sub/uuid.jpg", "shopping-item/1/100/")

        then:
        thrown(AccessDeniedException)
    }

    def "assertKeyWithinPrefixはプレフィックス以降に「..」が含まれる場合AccessDeniedExceptionを投げる"() {
        when:
        UploadImagePolicy.assertKeyWithinPrefix("user-icon/5/../6/icon.jpg", "user-icon/5/")

        then:
        thrown(AccessDeniedException)
    }
}
