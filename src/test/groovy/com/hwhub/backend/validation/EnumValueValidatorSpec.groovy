package com.hwhub.backend.validation

import com.hwhub.backend.validation.annotation.EnumValue
import com.hwhub.backend.domain.enums.CodeEnum
import jakarta.validation.ConstraintValidatorContext
import spock.lang.Specification

class EnumValueValidatorSpec extends Specification{

    def "valueがnullのときは常にtrueを返す"() {
        given: "CodeEnum実装のEnumを指定した@EnumValue"
        def field = DummyCodeEnumClass.getDeclaredField("status")
        EnumValue annotation = field.getAnnotation(EnumValue)

        and: "validatorを初期化する"
        def validator = new EnumValueValidator()
        validator.initialize(annotation)

        expect: "nullはバリデーションOK"
        validator.isValid(null, Stub(ConstraintValidatorContext))
    }

    def "CodeEnum実装enumではcodeとvalueを比較する"() {
        given: "CodeEnum実装のEnumを指定した@EnumValue"
        def field = DummyCodeEnumClass.getDeclaredField("status")
        EnumValue annotation = field.getAnnotation(EnumValue)

        and: "validatorを初期化する"
        def validator = new EnumValueValidator()
        validator.initialize(annotation)

        expect:
        validator.isValid(value, Stub(ConstraintValidatorContext)) == expected

        where:
        value   || expected
        "1"     || true   // ACTIVE(1)
        "2"     || true   // INACTIVE(2)
        "3"     || false  // 定義されていないcode
        "ACTIVE"|| false  // nameではなくcodeでチェックされる
        ""      || false
    }

    def "通常enumではnameとvalueを比較する"() {
        given: "通常のEnumを指定した@EnumValue"
        def field = DummyNameEnumClass.getDeclaredField("status")
        EnumValue annotation = field.getAnnotation(EnumValue)

        and: "validatorを初期化する"
        def validator = new EnumValueValidator()
        validator.initialize(annotation)

        expect:
        validator.isValid(value, Stub(ConstraintValidatorContext)) == expected

        where:
        value       || expected
        "ACTIVE"    || true
        "INACTIVE"  || true
        "1"         || false  // nameではないのでNG
        "UNKNOWN"   || false
        ""          || false
    }

    def "enumClassがEnumでない場合はenumConstantsがnullになりfalseを返す"() {
        given: "enumではないクラスをenumClassとして強制セットしたvalidator"
        def validator = new EnumValueValidator()

        // Groovyの@アクセスで privateフィールドに不正なClassを注入
        validator.@enumClass = String.class   // ← enumではない

        when: "バリデーションを実行する"
        def result = validator.isValid("ANY", Stub(ConstraintValidatorContext))

        then: "enumConstants == null 分岐に入り false が返る"
        result == false
    }
    
    // ============================
    // テスト用ダミー型たち
    // ============================

    /**
     * CodeEnum実装のテスト用Enum。
     * 実際の TaskStatus / ShoppingItemStatus の簡略版イメージ。
     */
    private static enum TestCodeEnum implements CodeEnum {
        ACTIVE("1"),
        INACTIVE("2");

        private final String code

        TestCodeEnum(String code) {
            this.code = code
        }

        @Override
        String getCode() {
            return code
        }
    }

    /**
     * 通常Enum（CodeEnumを実装しない）テスト用。
     */
    private static enum TestNameEnum {
        ACTIVE,
        INACTIVE
    }

    /**
     * CodeEnum実装Enumを@EnumValue(enumClass=...)で指定したダミークラス。
     * 実運用と同じ形でアノテーションを取得するために使用。
     */
    private static class DummyCodeEnumClass {
        @EnumValue(enumClass = TestCodeEnum)
        String status
    }

    /**
     * 通常Enumを@EnumValue(enumClass=...)で指定したダミークラス。
     */
    private static class DummyNameEnumClass {
        @EnumValue(enumClass = TestNameEnum)
        String status
    }
}
