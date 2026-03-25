package com.hwhub.backend.security.oauth

import com.hwhub.backend.domain.enums.OAuthFlow
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class OAuthStateSignerSpec extends Specification {

    Clock clock = Clock.fixed(Instant.parse("2024-01-01T00:00:00Z"), ZoneId.of("UTC"))
    OAuthStateSigner signer = new OAuthStateSigner(clock)
    String secret = "test-secret-key-1234567890123456"

    def "generate は正しいフォーマットの署名付きトークンを生成する"() {
        given:
        String kind = "LINK"
        String subject = "user123"
        long ttl = 3600

        when:
        String state = signer.generate(kind, subject, secret, ttl)

        then:
        state != null
        state.contains(".")
        
        def parts = state.split("\\.")
        parts.length == 2
        
        def payload = new String(Base64.urlDecoder.decode(parts[0]))
        def segments = payload.split("\\|")
        segments.length == 4
        segments[0] == kind
        segments[1] == subject
        // segments[2] is exp
        // segments[3] is nonce
    }

    def "verify は正当なトークンを検証できる"() {
        given:
        String state = signer.generate("LOGIN", "none", secret, 3600)

        expect:
        signer.verify(state, secret)
    }

    def "verify は署名が不正な場合 false を返す"() {
        given:
        String validState = signer.generate("LOGIN", "none", secret, 3600)
        String invalidState = validState + "invalid"

        expect:
        !signer.verify(invalidState, secret)
    }

    def "verify はシークレットが異なる場合 false を返す"() {
        given:
        String state = signer.generate("LOGIN", "none", secret, 3600)
        String wrongSecret = "wrong-secret-key-0987654321098765"

        expect:
        !signer.verify(state, wrongSecret)
    }

    def "verify は期限切れの場合 false を返す"() {
        given:
        long ttl = -1 // expired
        String state = signer.generate("LOGIN", "none", secret, ttl)

        expect:
        !signer.verify(state, secret)
    }

    def "verify はフォーマット不正の場合 false を返す"() {
        expect:
        !signer.verify(null, secret)
        !signer.verify("invalid-format", secret)
        !signer.verify("no.dot", secret) // decoding fails or signature mismatch
    }

    def "verify はペイロードが破損している場合 false を返す"() {
        given:
        String payload = "short|payload|123" 

        def badPayload = "A|B" // only 2 parts
        
        expect:
        !signer.verify("bad.signature", secret)
    }

    def "verify は旧形式の3partsも検証できる"() {
        given:
        long exp = Instant.now(clock).epochSecond + 3600
        String rawOld = "user123|${exp}|nonce"
        
        // Manually sign
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256")
        mac.init(new javax.crypto.spec.SecretKeySpec(secret.getBytes(), "HmacSHA256"))
        String sig = Base64.urlEncoder.withoutPadding().encodeToString(mac.doFinal(rawOld.getBytes()))
        
        String b64Payload = Base64.urlEncoder.withoutPadding().encodeToString(rawOld.getBytes())
        String state = b64Payload + "." + sig

        expect:
        signer.verify(state, secret)
    }

    def "extractPurpose は kind を返す"() {
        given:
        String state = signer.generate("LINK", "user1", secret, 3600)

        expect:
        signer.extractPurpose(state) == "LINK"
    }
    
    def "extractPurpose はフォーマット不正時に例外を投げる"() {
        when:
        signer.extractPurpose("invalid")
        
        then:
        thrown(IllegalArgumentException)
    }

    def "extractSubject は LINK フローの場合 subject を返す"() {
        given:
        String state = signer.generate("LINK", "user99", secret, 3600)

        expect:
        signer.extractSubject(state) == "user99"
    }

    def "extractSubject は LINK フロー以外の場合例外を投げる"() {
        given:
        String state = signer.generate("LOGIN", "user99", secret, 3600)

        when:
        signer.extractSubject(state)

        then:
        thrown(IllegalArgumentException)
    }

    def "generate は kind や subject が null の場合でも動作する"() {
        when:
        String state = signer.generate(null, null, secret, 3600)

        then:
        state != null
        def parts = state.split("\\.")
        def payload = new String(Base64.urlDecoder.decode(parts[0]))
        def segments = payload.split("\\|", -1)
        segments[0] == ""
        segments[1] == ""
    }

    def "verify はペイロードが極端に短い場合 false を返す"() {
        given: "2つのパーツしかないペイロード（kind, subject のみなど）"
        String raw = "A|B"
        String b64 = Base64.urlEncoder.withoutPadding().encodeToString(raw.getBytes())
        String sig = Base64.urlEncoder.withoutPadding().encodeToString("sig".getBytes()) // dummy

        expect:
        !signer.verify(b64 + "." + sig, secret)
    }

    def "extractPurpose はペイロード構造が不正な場合例外を投げる"() {
        given: "空のペイロード"
        String raw = ""
        String b64 = Base64.urlEncoder.withoutPadding().encodeToString(raw.getBytes())
        String sig = "dummy"

        when:
        signer.extractPurpose(b64 + "." + sig)

        then:
        thrown(IllegalArgumentException)
    }

    def "extractSubject はペイロードが2パーツ未満の場合例外を投げる"() {
        given:
        String raw = "ONLYKIND"
        String b64 = Base64.urlEncoder.withoutPadding().encodeToString(raw.getBytes())
        String sig = "dummy"

        when:
        signer.extractSubject(b64 + "." + sig)

        then:
        thrown(IllegalArgumentException)
    }

    def "parse の境界条件テスト"() {
        expect:
        signer.parse(null) == null
        signer.parse(".onlysig") == null
        signer.parse("onlypayload.") == null
        signer.parse("a.b.c") != null // lastIndexOf を使うので a.b が payload になる
    }

    def "constantTimeEquals は一方が null の場合 false を返す"() {
        expect:
        !signer.constantTimeEquals(null, "sig")
        !signer.constantTimeEquals("sig", null)
    }

    def "verify はペイロードが3または4パーツではない場合 false を返す"() {
        given:
        // 有効なシークレットで署名を作成するが、ペイロードの中身を2パーツにする
        String payload = "PART1|PART2"
        String b64 = Base64.urlEncoder.withoutPadding().encodeToString(payload.getBytes())
        String signature = signer.hmacSha256Base64Url(b64, secret)

        expect:
        !signer.verify(b64 + "." + signature, secret)
    }

    def "extractPurpose はパース結果が null の場合例外を投げる"() {
        when:
        signer.extractPurpose("invalid-format")

        then:
        thrown(IllegalArgumentException)
    }

    def "extractSubject はパース結果が null の場合例外を投げる"() {
        when:
        signer.extractSubject("invalid-format")

        then:
        thrown(IllegalArgumentException)
    }
}
