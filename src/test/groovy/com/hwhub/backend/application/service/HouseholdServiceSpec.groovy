package com.hwhub.backend.application.service

import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.model.HouseholdMemberModel
import com.hwhub.backend.domain.model.HouseholdModel
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.repository.HouseholdMemberRepository
import com.hwhub.backend.domain.repository.HouseholdRepository
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException
import org.springframework.security.access.AccessDeniedException
import spock.lang.Specification

class HouseholdServiceSpec extends Specification {

    HouseholdService householdService
    HouseholdRepository householdRepository = Mock(HouseholdRepository)
    HouseholdAuthorizationService householdAuthorizationService = Mock(HouseholdAuthorizationService)
    HouseholdMemberService householdMemberService = Mock(HouseholdMemberService)
    UserService userService = Mock(UserService)
    HouseholdMemberRepository householdMemberRepository = Mock(HouseholdMemberRepository)

    def setup() {
        householdService = new HouseholdService(
            householdRepository,
            householdAuthorizationService,
            householdMemberService,
            userService,
            householdMemberRepository
        )
    }

    // -------------------------------------------------
    // createHousehold
    // -------------------------------------------------
    def "createHousehold は世帯を作成し、作成者をメンバーとして登録して返す"() {
        given:
        Long userId = 100L
        String name = "My Home"
        def userProfile = UserModel.reconstruct(userId, "user@example.com", "pass", "Taro", "ja", null, true)
        def insertedHousehold = HouseholdModel.reconstruct(1L, name, userId)

        when:
        def result = householdService.createHousehold(userId, name)

        then:
        1 * householdRepository.insert(_ as HouseholdModel, userId, ProgramType.ONL_HLD.code) >> insertedHousehold
        1 * userService.getProfile(userId) >> userProfile
        1 * householdMemberService.createMember(1L, userId, "Taro", userId)
        
        and:
        result.householdId == 1L
        result.name == name
        result.ownerUserId == userId
    }

    // -------------------------------------------------
    // updateHouseholdName
    // -------------------------------------------------
    def "updateHouseholdName は認可チェック後に世帯名を更新する"() {
        given:
        Long householdId = 1L
        Long userId = 100L
        String newName = "New Name"
        def model = HouseholdModel.reconstruct(householdId, "Old Name", userId)

        when:
        householdService.updateHouseholdName(householdId, userId, newName)

        then:
        1 * householdAuthorizationService.assertUserBelongsToHousehold(householdId, userId)
        1 * householdRepository.findById(householdId) >> model
        1 * householdRepository.update(model, userId, ProgramType.ONL_HLD.code)
        
        and:
        model.name == newName
    }

    def "updateHouseholdName: 世帯が見つからない場合は ResourceNotFoundException"() {
        given:
        Long householdId = 1L
        Long userId = 100L

        when:
        householdService.updateHouseholdName(householdId, userId, "New Name")

        then:
        1 * householdAuthorizationService.assertUserBelongsToHousehold(householdId, userId)
        1 * householdRepository.findById(householdId) >> null
        thrown(ResourceNotFoundException)
        0 * householdRepository.update(_, _, _)
    }

    // -------------------------------------------------
    // deleteHousehold
    // -------------------------------------------------
    def "deleteHousehold: 正常系 - オーナーかつ自分以外のメンバーがいない場合削除成功"() {
        given:
        Long householdId = 1L
        Long userId = 100L
        def householdModel = HouseholdModel.reconstruct(1L, "Test Home", 100L)

        // mock member (自分)
        def me = Mock(HouseholdMemberModel)
        def members = [me]

        when:
        householdService.deleteHousehold(householdId, userId)

        then:
        1 * householdRepository.findById(householdId) >> householdModel
        1 * householdMemberRepository.findActiveByHouseholdId(householdId) >> members
        1 * householdMemberRepository.deleteByHouseholdId(householdId)
        1 * householdRepository.update(householdModel, userId, ProgramType.ONL_HLD.code)
    }

    def "deleteHousehold: 異常系 - 世帯が存在しない場合 ResourceNotFoundException"() {
        given:
        Long householdId = 1L
        Long userId = 100L

        when:
        householdService.deleteHousehold(householdId, userId)

        then:
        1 * householdRepository.findById(householdId) >> null
        thrown(ResourceNotFoundException)
        0 * householdMemberRepository.deleteByHouseholdId(_)
    }

    def "deleteHousehold: 異常系 - オーナーでない場合 AccessDeniedException"() {
        given:
        Long householdId = 1L
        Long userId = 200L // Not owner
        def householdModel = HouseholdModel.reconstruct(1L, "Test Home", 100L)

        when:
        householdService.deleteHousehold(householdId, userId)

        then:
        1 * householdRepository.findById(householdId) >> householdModel
        thrown(AccessDeniedException)
        0 * householdMemberRepository.deleteByHouseholdId(_)
    }

    def "deleteHousehold: 異常系 - 他のアクティブメンバーがいる場合 IllegalArgumentException"() {
        given:
        Long householdId = 1L
        Long userId = 100L
        def householdModel = HouseholdModel.reconstruct(1L, "Test Home", 100L)

        // mock members (自分 + 他人)
        def me = Mock(HouseholdMemberModel)
        def other = Mock(HouseholdMemberModel)
        def members = [me, other]

        when:
        householdService.deleteHousehold(householdId, userId)

        then:
        1 * householdRepository.findById(householdId) >> householdModel
        1 * householdMemberRepository.findActiveByHouseholdId(householdId) >> members
        thrown(IllegalArgumentException)
        0 * householdMemberRepository.deleteByHouseholdId(_)
    }
}
