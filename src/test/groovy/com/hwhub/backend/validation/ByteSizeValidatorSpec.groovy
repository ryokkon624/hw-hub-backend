package com.hwhub.backend.validation

import com.hwhub.backend.validation.annotation.ByteSize
import spock.lang.Specification

class ByteSizeValidatorSpec extends Specification{

    ByteSizeValidator validator

    def setup() {
        validator = new ByteSizeValidator()
    }

    def "valueがnullのときはtrueを返す"() {
        given: "maxバイト数を適当に設定しておく"
        validator.@max = 10

        expect: "nullはバリデーションOK"
        validator.isValid(null, null)
    }

    def "UTF-8のバイト長がmax以下ならtrue、超えていればfalseを返す"() {
        given: "maxバイト数を設定する"
        validator.@max = max

        expect: "UTF-8のバイト数にもとづいて判定される"
        validator.isValid(value, null) == expected

        where:
        value       | max || expected
        ""          | 0   || true      // 空文字は0バイト → OK
        "a"         | 1   || true      // 1バイト → OK
        "a"         | 0   || false     // 0バイト制限 → NG
        "abc"       | 3   || true      // 3バイト → OK
        "abc"       | 2   || false     // 3バイト → NG

        // 日本語（UTF-8で3バイト/文字）を含むケース
        "あ"        | 3   || true      // "あ" は 3バイト → OK
        "あ"        | 2   || false     // 3バイト > 2 → NG
        "あい"      | 6   || true      // 6バイト → OK
        "あい"      | 5   || false     // 6バイト > 5 → NG

        // 英数字 + 日本語の混在
        "aあ"       | 4   || true      // "a"(1) + "あ"(3) = 4バイト → OK
        "aあ"       | 3   || false     // 4バイト > 3 → NG
    }

    def "maxちょうどのバイト長のときtrueを返す"() {
        given: "maxバイト数を3に設定する"
        validator.@max = 3

        expect: "3バイトちょうどの文字列はOK"
        validator.isValid("あ", null)  // "あ" は3バイト
    }

    def "initializeでannotationのmaxが設定される"() {
        given: "@ByteSize(max=5) が付与されたフィールドのアノテーションを取得する"
        def field = DummyClass.getDeclaredField("name")
        ByteSize annotation = field.getAnnotation(ByteSize)

        and: "validatorをannotationで初期化する"
        def validator = new ByteSizeValidator()
        validator.initialize(annotation)

        expect: "max=5 が反映されている（5バイトまではOK、6バイトはNG）"
        validator.isValid("12345", null)   // 5バイト → OK
        !validator.isValid("123456", null) // 6バイト → NG
    }
    /**
     * テスト用のダミークラス。
     * 実運用と同じく @ByteSize(max=...) から annotation を取得するために使用。
     */
    private static class DummyClass {
        @ByteSize(max = 5)
        String name
    }
}
