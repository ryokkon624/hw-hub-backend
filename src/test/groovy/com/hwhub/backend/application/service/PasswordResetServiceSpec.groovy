package com.hwhub.backend.application.service

import com.hwhub.backend.config.PasswordResetProperties
import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.model.UserPasswordResetModel
import com.hwhub.backend.domain.notification.PasswordResetMailSender
import com.hwhub.backend.domain.repository.UserPasswordResetRepository
import com.hwhub.backend.domain.repository.UserRepository
import com.hwhub.backend.presentation.rest.common.PasswordResetCooldownException
import com.hwhub.backend.presentation.rest.common.PasswordResetDisabledException
import com.hwhub.backend.presentation.rest.common.PasswordResetLimitExceededException
import com.hwhub.backend.presentation.rest.common.PasswordResetTokenExpiredException
import com.hwhub.backend.presentation.rest.common.PasswordResetTokenInvalidException
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import java.time.LocalDateTime

class PasswordResetServiceSpec extends Specification {

    UserRepository userRepository = Mock()
    UserPasswordResetRepository userPasswordResetRepository = Mock()
    PasswordEncoder passwordEncoder = Mock()
    PasswordResetMailSender mailSender = Mock()

    // Helper to create service with specific properties
    PasswordResetService createService(PasswordResetProperties props) {
        return new PasswordResetService(
                props,
                userRepository,
                userPasswordResetRepository,
                passwordEncoder,
                mailSender
        )
    }

    // Default properties builder
    PasswordResetProperties createProps(boolean enabled = true, boolean sendMail = true, int ttl = 30, int cooldown = 60, int limit = 5) {
        return new PasswordResetProperties(
                enabled,
                sendMail,
                ttl,
                cooldown,
                limit,
                "https://example.com",
                "/reset"
        )
    }

    // ===========================================
    // requestReset
    // ===========================================

    def "requestResetはproperties.enabledがfalseの場合、PasswordResetDisabledExceptionをthrowする"() {
        given:
        def service = createService(createProps(false))

        when:
        service.requestReset("test@example.com")

        then:
        0 * userRepository.findByEmail(_)
        thrown(PasswordResetDisabledException)
    }

    def "requestResetはユーザが見つからない場合、セキュリティ上の理由で早期リターンする"() {
        given:
        def service = createService(createProps(true))

        when:
        service.requestReset("missing@example.com")

        then:
        1 * userRepository.findByEmail("missing@example.com") >> Optional.empty()
        0 * userPasswordResetRepository.insert(_, _, _)
    }

    def "requestResetはユーザが無効な場合、早期リターンする"() {
        given:
        def service = createService(createProps(true))
        def user = Mock(UserModel) {
            isActive() >> false
        }

        when:
        service.requestReset("inactive@example.com")

        then:
        1 * userRepository.findByEmail("inactive@example.com") >> Optional.of(user)
        0 * userPasswordResetRepository.insert(_, _, _)
    }

    def "requestResetはクールダウン中の場合、PasswordResetCooldownExceptionをthrowする"() {
        given:
        def service = createService(createProps(true, true, 30, 60))
        Long userId = 10L
        def user = Mock(UserModel) {
            getUserId() >> userId
            isActive() >> true
        }

        when:
        service.requestReset("cooldown@example.com")

        then:
        1 * userRepository.findByEmail("cooldown@example.com") >> Optional.of(user)
        // latest request was 30 seconds ago
        1 * userPasswordResetRepository.findLatestRequestedAt(userId) >> Optional.of(LocalDateTime.now().minusSeconds(30))
        thrown(PasswordResetCooldownException)
    }

    def "requestResetは一日の制限を超えた場合、PasswordResetLimitExceededExceptionをthrowする"() {
        given:
        def service = createService(createProps(true, true, 30, 60, 3))
        Long userId = 11L
        def user = Mock(UserModel) {
            getUserId() >> userId
            isActive() >> true
        }

        when:
        service.requestReset("limit@example.com")

        then:
        1 * userRepository.findByEmail("limit@example.com") >> Optional.of(user)
        1 * userPasswordResetRepository.findLatestRequestedAt(userId) >> Optional.empty()
        1 * userPasswordResetRepository.countRequestedOnDate(userId, _, _) >> 3
        thrown(PasswordResetLimitExceededException)
    }

    def "requestResetは有効な場合、トークンを挿入しメールを送信する"() {
        given:
        def service = createService(createProps(true, true))
        Long userId = 12L
        def user = Mock(UserModel) {
            getUserId() >> userId
            getEmail() >> "ok@example.com"
            getDisplayName() >> "Takashi"
            getLocale() >> "ja"
            isActive() >> true
        }

        when:
        service.requestReset("ok@example.com")

        then:
        1 * userRepository.findByEmail("ok@example.com") >> Optional.of(user)
        1 * userPasswordResetRepository.findLatestRequestedAt(userId) >> Optional.empty()
        1 * userPasswordResetRepository.countRequestedOnDate(userId, _, _) >> 0

        1 * userPasswordResetRepository.insert(_, 1L, ProgramType.ONL_AUTH.code)

        1 * mailSender.sendPasswordResetMail("ok@example.com", "Takashi", { it.startsWith("https://example.com/reset?token=") }, "ja")
    }
    
    def "requestResetはproperties.sendMailがfalseの場合、トークンを挿入するがメールは送信しない"() {
        given:
        def service = createService(createProps(true, false))
        Long userId = 13L
        def user = Mock(UserModel) {
            getUserId() >> userId
            isActive() >> true
        }

        when:
        service.requestReset("nomail@example.com")

        then:
        1 * userRepository.findByEmail(_) >> Optional.of(user)
        1 * userPasswordResetRepository.findLatestRequestedAt(_) >> Optional.empty()
        1 * userPasswordResetRepository.countRequestedOnDate(_, _, _) >> 0

        1 * userPasswordResetRepository.insert(_, _, _)

        0 * mailSender.sendPasswordResetMail(_, _, _, _)
    }

    // ===========================================
    // confirmReset
    // ===========================================

    def "confirmResetはproperties.enabledがfalseの場合、PasswordResetTokenInvalidExceptionをthrowする"() {
        given:
        def service = createService(createProps(false))

        when:
        service.confirmReset("token", "newPass")

        then:
        thrown(PasswordResetTokenInvalidException)
    }

    def "confirmResetはトークンが見つからないか使用できない場合、PasswordResetTokenInvalidExceptionをthrowする"() {
        given:
        def service = createService(createProps(true))

        when:
        service.confirmReset("missing-token", "newPass")

        then:
        1 * userPasswordResetRepository.findUsableByTokenHash(_, _) >> Optional.empty()
        thrown(PasswordResetTokenInvalidException)
    }

    def "confirmResetはトークンが期限切れの場合、PasswordResetTokenExpiredExceptionをthrowする"() {
        given:
        def service = createService(createProps(true))
        def reset = Mock(UserPasswordResetModel) {
            getExpiresAt() >> LocalDateTime.now().minusMinutes(1)
        }

        when:
        service.confirmReset("expired-token", "newPass")

        then:
        1 * userPasswordResetRepository.findUsableByTokenHash(_, _) >> Optional.of(reset)
        thrown(PasswordResetTokenExpiredException)
    }

    def "confirmResetはユーザが見つからない場合、PasswordResetTokenInvalidExceptionをthrowする"() {
        given:
        def service = createService(createProps(true))
        def reset = Mock(UserPasswordResetModel) {
            getExpiresAt() >> LocalDateTime.now().plusMinutes(10)
            getUserId() >> 99L
        }

        when:
        service.confirmReset("valid-token", "newPass")

        then:
        1 * userPasswordResetRepository.findUsableByTokenHash(_, _) >> Optional.of(reset)
        1 * userRepository.findById(99L) >> Optional.empty()
        thrown(PasswordResetTokenInvalidException)
    }

    def "confirmResetは成功時、パスワードを更新しトークンを使用済みにする"() {
        given:
        def service = createService(createProps(true))
        Long userId = 100L
        def reset = Mock(UserPasswordResetModel) {
            getUserPasswordResetId() >> 555L
            getExpiresAt() >> LocalDateTime.now().plusMinutes(10)
            getUserId() >> userId
        }
        def user = Mock(UserModel)

        when:
        service.confirmReset("valid-token", "newPass")

        then:
        1 * userPasswordResetRepository.findUsableByTokenHash(_, _) >> Optional.of(reset)
        1 * userRepository.findById(userId) >> Optional.of(user)

        1 * passwordEncoder.encode("newPass") >> "hashedPass"
        1 * user.changePasswordHash("hashedPass", _ as LocalDateTime)
        1 * userRepository.updatePassword(user, 1L, ProgramType.ONL_PWDRST.code)

        // Return 1 meaning updated locally
        1 * userPasswordResetRepository.markUsedIfUnused(555L, _, 1L, ProgramType.ONL_AUTH.code) >> 1
    }

    def "confirmResetはトークン使用状況の更新に失敗した場合（競合状態）、PasswordResetTokenInvalidExceptionをthrowする"() {
        given:
        def service = createService(createProps(true))
        Long userId = 101L
        def reset = Mock(UserPasswordResetModel) {
            getUserPasswordResetId() >> 666L
            getExpiresAt() >> LocalDateTime.now().plusMinutes(10)
            getUserId() >> userId
        }
        def user = Mock(UserModel)

        when:
        service.confirmReset("race-token", "newPass")

        then:
        1 * userPasswordResetRepository.findUsableByTokenHash(_, _) >> Optional.of(reset)
        1 * userRepository.findById(userId) >> Optional.of(user)
        1 * userRepository.updatePassword(_, _, _)

        // Return 0 implying someone else used it or it's invalid now
        1 * userPasswordResetRepository.markUsedIfUnused(666L, _, _, _) >> 0
        thrown(PasswordResetTokenInvalidException)
    }
}
