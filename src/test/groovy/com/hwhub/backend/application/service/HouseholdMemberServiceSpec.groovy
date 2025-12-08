package com.hwhub.backend.application.service

import com.hwhub.backend.domain.enums.HouseholdMemberStatus
import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.model.HouseholdMemberModel
import com.hwhub.backend.domain.model.HouseholdModel
import com.hwhub.backend.domain.repository.HouseholdMemberRepository
import com.hwhub.backend.domain.repository.HouseholdRepository
import com.hwhub.backend.domain.repository.HouseworkRepository
import com.hwhub.backend.domain.repository.HouseworkTaskRepository
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException
import org.springframework.security.access.AccessDeniedException
import spock.lang.Specification

class HouseholdMemberServiceSpec extends  Specification{

    HouseholdMemberRepository memberRepository = Mock()
    HouseholdRepository householdRepository = Mock()
    HouseworkRepository houseworkRepository = Mock()
    HouseworkTaskRepository houseworkTaskRepository = Mock()
    HouseholdAuthorizationService authorizationService = Mock()
    UserIconService iconService = Mock()

    HouseholdMemberService service = new HouseholdMemberService(
            memberRepository,
            householdRepository,
            houseworkRepository,
            houseworkTaskRepository,
            authorizationService,
            iconService
    )

    // ==================================
    // getMembers
    // ==================================

    def "getMembersはアクティブメンバーにアイコンURLを付与して返す"() {
        given:
        Long householdId = 1L
        def member = HouseholdMemberModel.reconstruct(
                householdId,
                10L,
                "表示名",
                "profile/key/001",
                null,
                "ニックネーム",
                HouseholdMemberStatus.ACTIVE.code,
                "MEMBER"
        )

        when:
        def result = service.getMembers(householdId)

        then:
        1 * memberRepository.findActiveByHouseholdId(householdId) >> [member]
        1 * iconService.getIconUrl("profile/key/001") >> "https://cdn/icon.png"

        and:
        result.size() == 1
        result[0].iconUrl == "https://cdn/icon.png"
    }

    // ==================================
    // updateMyNickname
    // ==================================

    def "updateMyNicknameは認可チェック後ニックネームを更新しリポジトリに保存する"() {
        given:
        Long householdId = 1L
        Long userId = 10L
        String newNickname = "新しいニックネーム"

        def member = HouseholdMemberModel.reconstruct(
                householdId,
                userId,
                "表示名",
                null,
                null,
                "旧ニックネーム",
                HouseholdMemberStatus.ACTIVE.code,
                "MEMBER"
        )

        when:
        service.updateMyNickname(householdId, userId, newNickname)

        then:
        1 * authorizationService.assertUserBelongsToHousehold(householdId, userId)
        1 * memberRepository.findById(householdId, userId) >> member
        1 * memberRepository.update(member, userId, ProgramType.ONL_HLDMEM.code)

        and: "ドメインモデルのニックネームも変わっている"
        member.nickname == newNickname
    }

    def "updateMyNicknameはメンバーが存在しない場合ResourceNotFoundExceptionを投げる"() {
        given:
        Long householdId = 1L
        Long userId = 10L

        when:
        service.updateMyNickname(householdId, userId, "nickname")

        then:
        1 * authorizationService.assertUserBelongsToHousehold(householdId, userId)
        1 * memberRepository.findById(householdId, userId) >> null
        0 * memberRepository.update(_, _, _)
        thrown(ResourceNotFoundException)
    }

    // ==================================
    // createMember
    // ==================================

    def "createMemberは過去にメンバーが存在しない場合新規作成してinsertする"() {
        given:
        Long householdId = 1L
        Long userId = 10L
        Long loginUserId = 99L
        String displayName = "テストユーザ"

        when:
        service.createMember(householdId, userId, displayName, loginUserId)

        then:
        1 * memberRepository.findById(householdId, userId) >> null
        1 * memberRepository.insert(_, loginUserId, ProgramType.ONL_HLDMEM.code) >> { args ->
            def m = args[0] as HouseholdMemberModel
            assert m.householdId == householdId
            assert m.userId == userId
            assert m.nickname == displayName
            assert m.status == HouseholdMemberStatus.ACTIVE.code
            return m
        }
        0 * memberRepository.update(_, _, _)
    }

    def "createMemberは過去にメンバーが存在する場合rejoinしてupdateする"() {
        given:
        Long householdId = 1L
        Long userId = 10L
        Long loginUserId = 99L

        def member = HouseholdMemberModel.reconstruct(
                householdId,
                userId,
                "表示名",
                null,
                null,
                "ニックネーム",
                HouseholdMemberStatus.LEFT.code,
                "MEMBER"
        )

        when:
        service.createMember(householdId, userId, "表示名", loginUserId)

        then:
        1 * memberRepository.findById(householdId, userId) >> member
        1 * memberRepository.update(member, loginUserId, ProgramType.ONL_HLDMEM.code)
        0 * memberRepository.insert(_, _, _)

        and: "rejoinによりステータスがACTIVEになっている"
        member.status == HouseholdMemberStatus.ACTIVE.code
    }

    // ==================================
    // deleteMyself
    // ==================================

    def "deleteMyselfは本人の離脱と担当解除を行う"() {
        given:
        Long householdId = 1L
        Long userId = 10L

        def member = HouseholdMemberModel.reconstruct(
                householdId,
                userId,
                "表示名",
                null,
                null,
                "ニックネーム",
                HouseholdMemberStatus.ACTIVE.code,
                "MEMBER"
        )

        when:
        service.deleteMyself(householdId, userId)

        then:
        1 * authorizationService.assertUserBelongsToHousehold(householdId, userId)
        1 * memberRepository.findById(householdId, userId) >> member
        1 * memberRepository.update(member, userId, ProgramType.ONL_HLDMEM.code)
        1 * houseworkRepository.clearAssignee(householdId, userId, userId, ProgramType.ONL_HLDMEM.code)
        1 * houseworkTaskRepository.clearAssignee(householdId, userId, userId, ProgramType.ONL_HLDMEM.code)

        and: "ステータスはLEFTになっている"
        member.status == HouseholdMemberStatus.LEFT.code
    }

    // ==================================
    // deleteMember
    // ==================================

    def "deleteMemberはアクセス権がない場合AccessDeniedExceptionを投げる"() {
        given:
        Long householdId = 1L
        Long targetUserId = 10L
        Long loginUserId = 99L

        when:
        service.deleteMember(householdId, targetUserId, loginUserId)

        then:
        1 * authorizationService.canAccessHousehold(householdId, loginUserId) >> false
        0 * householdRepository.findById(_)
        thrown(AccessDeniedException)
    }

    def "deleteMemberはオーナーでないユーザが実行した場合AccessDeniedExceptionを投げる"() {
        given:
        Long householdId = 1L
        Long targetUserId = 10L
        Long loginUserId = 99L

        def household = Mock(HouseholdModel)

        when:
        service.deleteMember(householdId, targetUserId, loginUserId)

        then:
        1 * authorizationService.canAccessHousehold(householdId, loginUserId) >> true
        1 * householdRepository.findById(householdId) >> household
        1 * household.isOwner(loginUserId) >> false
        0 * memberRepository.findById(_, _)
        thrown(AccessDeniedException)
    }

    def "deleteMemberはオーナーが実行した場合ターゲットメンバーを離脱させ担当解除を行う"() {
        given:
        Long householdId = 1L
        Long targetUserId = 10L
        Long loginUserId = 99L

        def household = Mock(HouseholdModel)
        def member = HouseholdMemberModel.reconstruct(
                householdId,
                targetUserId,
                "表示名",
                null,
                null,
                "ニックネーム",
                HouseholdMemberStatus.ACTIVE.code,
                "MEMBER"
        )

        when:
        service.deleteMember(householdId, targetUserId, loginUserId)

        then:
        1 * authorizationService.canAccessHousehold(householdId, loginUserId) >> true
        1 * householdRepository.findById(householdId) >> household
        1 * household.isOwner(loginUserId) >> true
        1 * memberRepository.findById(householdId, targetUserId) >> member
        1 * memberRepository.update(member, loginUserId, ProgramType.ONL_HLDMEM.code)
        1 * houseworkRepository.clearAssignee(householdId, targetUserId, loginUserId, ProgramType.ONL_HLDMEM.code)
        1 * houseworkTaskRepository.clearAssignee(householdId, targetUserId, loginUserId, ProgramType.ONL_HLDMEM.code)

        and:
        member.status == HouseholdMemberStatus.LEFT.code
    }
}
