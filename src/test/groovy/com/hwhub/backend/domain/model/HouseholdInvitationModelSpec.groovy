package com.hwhub.backend.domain.model

import com.hwhub.backend.domain.enums.InvitationStatus
import spock.lang.Specification

import java.time.LocalDateTime

class HouseholdInvitationModelSpec extends Specification {

    def "reconstructは引数で渡した値をそのままプロパティに設定する"() {
        given:
        def token = "token-123"
        def householdId = 1L
        def householdName = "My Household"
        def inviterUserId = 2L
        def inviterName = "inviter"
        def invitedEmail = "test@example.com"
        def status = InvitationStatus.ACCEPTED.code
        def expiresAt = LocalDateTime.now().plusDays(3)
        def acceptedUserId = 3L
        def acceptedUserName = "accepted"
        def createdAt = LocalDateTime.now().minusDays(1)

        when:
        def model = HouseholdInvitationModel.reconstruct(
                token,
                householdId,
                householdName,
                inviterUserId,
                inviterName,
                invitedEmail,
                status,
                expiresAt,
                acceptedUserId,
                acceptedUserName,
                createdAt
        )

        then:
        model.invitationToken == token
        model.householdId == householdId
        model.householdName == householdName
        model.inviterUserId == inviterUserId
        model.inviterName == inviterName
        model.invitedEmail == invitedEmail
        model.status == status
        model.expiresAt == expiresAt
        model.acceptedUserId == acceptedUserId
        model.acceptedUserName == acceptedUserName
        model.createdAt == createdAt
    }

    def "createはPENDINGステータスと7日後の期限を持つ招待を生成する"() {
        given:
        def before = LocalDateTime.now()
        Long householdId = 10L
        Long inviterUserId = 20L
        String invitedEmail = "invited@example.com"

        when:
        def model = HouseholdInvitationModel.create(householdId, inviterUserId, invitedEmail)
        def after = LocalDateTime.now()

        then: "トークンはnullでも空文字でもない"
        model.invitationToken != null
        !model.invitationToken.isBlank()

        and: "世帯ID・招待者ID・メールはそのまま反映される"
        model.householdId == householdId
        model.inviterUserId == inviterUserId
        model.invitedEmail == invitedEmail

        and: "ステータスはPENDINGになる"
        model.status == InvitationStatus.PENDING.code

        and: "期限は現在時刻より後かつ、おおよそ7日後の範囲内になっている"
        model.expiresAt.isAfter(before)
        model.expiresAt.isBefore(after.plusDays(8))  // だいたい7日以内

        and: "受け入れ関連の情報はnullで初期化される"
        model.acceptedUserId == null
        model.acceptedUserName == null
        model.createdAt == null
    }

    def "isExpiredはexpiresAtが現在より前ならtrueを返す"() {
        given:
        def past = LocalDateTime.now().minusMinutes(1)

        def model = HouseholdInvitationModel.reconstruct(
                "token",
                1L,
                "household",
                2L,
                "inviter",
                "test@example.com",
                InvitationStatus.PENDING.code,
                past,
                null,
                null,
                null
        )

        expect:
        model.isExpired()
    }

    def "isExpiredはexpiresAtがnullまたは未来ならfalseを返す"() {
        given:
        def future = LocalDateTime.now().plusMinutes(1)

        def modelFuture = HouseholdInvitationModel.reconstruct(
                "token",
                1L,
                "household",
                2L,
                "inviter",
                "test@example.com",
                InvitationStatus.PENDING.code,
                future,
                null,
                null,
                null
        )

        def modelNull = HouseholdInvitationModel.reconstruct(
                "token2",
                1L,
                "household",
                2L,
                "inviter",
                "test2@example.com",
                InvitationStatus.PENDING.code,
                null,
                null,
                null,
                null
        )

        expect:
        !modelFuture.isExpired()
        !modelNull.isExpired()
    }

    def "isTerminalはACCEPTED/DECLINED/REVOKEDのときtrueを返す"() {
        expect:
        HouseholdInvitationModel.reconstruct(
                "t1",
                1L,
                null,
                2L,
                null,
                "a@example.com",
                InvitationStatus.ACCEPTED.code,
                null,
                null,
                null,
                null
        ).isTerminal()

        HouseholdInvitationModel.reconstruct(
                "t2",
                1L,
                null,
                2L,
                null,
                "b@example.com",
                InvitationStatus.DECLINED.code,
                null,
                null,
                null,
                null
        ).isTerminal()

        HouseholdInvitationModel.reconstruct(
                "t3",
                1L,
                null,
                2L,
                null,
                "c@example.com",
                InvitationStatus.REVOKED.code,
                null,
                null,
                null,
                null
        ).isTerminal()
    }

    def "isTerminalはPENDINGやEXPIREDの場合falseを返す"() {
        expect:
        !HouseholdInvitationModel.reconstruct(
                "t4",
                1L,
                null,
                2L,
                null,
                "p@example.com",
                InvitationStatus.PENDING.code,
                null,
                null,
                null,
                null
        ).isTerminal()

        !HouseholdInvitationModel.reconstruct(
                "t5",
                1L,
                null,
                2L,
                null,
                "e@example.com",
                InvitationStatus.EXPIRED.code,
                null,
                null,
                null,
                null
        ).isTerminal()
    }
}
