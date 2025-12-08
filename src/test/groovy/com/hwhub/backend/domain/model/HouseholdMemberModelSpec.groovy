package com.hwhub.backend.domain.model

import com.hwhub.backend.domain.enums.HouseholdMemberStatus
import spock.lang.Specification

class HouseholdMemberModelSpec extends Specification {

    // =========================
    // reconstruct
    // =========================

    def "reconstructは全プロパティをそのまま保持する"() {
        given:
        Long householdId = 1L
        Long userId = 2L
        String displayName = "display"
        String profileImageKey = "img-key"
        String iconUrl = "https://example.com/icon.png"
        String nickname = "nick"
        String status = HouseholdMemberStatus.ACTIVE.code
        String role = "OWNER"

        when:
        def model = HouseholdMemberModel.reconstruct(
                householdId,
                userId,
                displayName,
                profileImageKey,
                iconUrl,
                nickname,
                status,
                role
        )

        then:
        model.householdId == householdId
        model.userId == userId
        model.displayName == displayName
        model.profileImageKey == profileImageKey
        model.iconUrl == iconUrl
        model.nickname == nickname
        model.status == status
        model.role == role
    }

    // =========================
    // create
    // =========================

    def "createはACTIVEステータス・MEMBERロール・nickname=displayNameで生成される"() {
        given:
        Long householdId = 10L
        Long userId = 20L
        String displayName = "Taro"

        when:
        def model = HouseholdMemberModel.create(householdId, userId, displayName)

        then:
        model.householdId == householdId
        model.userId == userId
        model.displayName == displayName

        and: "未設定項目はnull"
        model.profileImageKey == null
        model.iconUrl == null

        and: "nicknameはdisplayNameで初期化される"
        model.nickname == displayName

        and: "ステータスはACTIVE"
        model.status == HouseholdMemberStatus.ACTIVE.code

        and: "ロールはMEMBER"
        model.role == "MEMBER"
    }

    // =========================
    // rejoin
    // =========================

    def "rejoinはステータスをACTIVEに戻す"() {
        given:
        def model = HouseholdMemberModel.reconstruct(
                1L,
                2L,
                "user",
                null,
                null,
                "nick",
                HouseholdMemberStatus.LEFT.code,
                "MEMBER"
        )

        when:
        model.rejoin()

        then:
        model.status == HouseholdMemberStatus.ACTIVE.code
    }

    // =========================
    // leave
    // =========================

    def "leaveはステータスをLEFTに変更する"() {
        given:
        def model = HouseholdMemberModel.reconstruct(
                1L,
                2L,
                "user",
                null,
                null,
                "nick",
                HouseholdMemberStatus.ACTIVE.code,
                "MEMBER"
        )

        when:
        model.leave()

        then:
        model.status == HouseholdMemberStatus.LEFT.code
    }

    // =========================
    // changeIconUrl
    // =========================

    def "changeIconUrlはiconUrlを上書きする"() {
        given:
        def model = HouseholdMemberModel.create(1L, 2L, "user")

        when:
        model.changeIconUrl("https://example.com/new.png")

        then:
        model.iconUrl == "https://example.com/new.png"
    }

    // =========================
    // changeNickname
    // =========================

    def "changeNicknameはnicknameを上書きする"() {
        given:
        def model = HouseholdMemberModel.create(1L, 2L, "user")

        when:
        model.changeNickname("new-nick")

        then:
        model.nickname == "new-nick"
    }
}
