package com.hwhub.backend.application.service

import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.model.HouseholdModel
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.repository.HouseholdRepository
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException
import org.springframework.security.access.AccessDeniedException
import spock.lang.Specification

class HouseholdServiceSpec extends Specification{

    HouseholdRepository householdRepository = Mock()
    HouseholdAuthorizationService householdAuthorizationService = Mock()
    HouseholdMemberService householdMemberService = Mock()
    UserService userService = Mock()

    HouseholdService service = new HouseholdService(
            householdRepository,
            householdAuthorizationService,
            householdMemberService,
            userService
    )

    // ==================================
    // updateHouseholdName
    // ==================================

    def "updateHouseholdNameは認可チェック後世帯名を変更し更新する"() {
        given:
        Long householdId = 1L
        Long userId = 10L
        String newName = "新しい世帯名"

        def household = Mock(HouseholdModel)

        when:
        service.updateHouseholdName(householdId, userId, newName)

        then:
        1 * householdAuthorizationService.assertUserBelongsToHousehold(householdId, userId)
        1 * householdRepository.findById(householdId) >> household
        1 * household.changeName(newName)
        1 * householdRepository.update(household, userId, ProgramType.ONL_HLD.code)
    }

    def "updateHouseholdNameは世帯が存在しない場合ResourceNotFoundExceptionを投げる"() {
        given:
        Long householdId = 99L
        Long userId = 10L

        when:
        service.updateHouseholdName(householdId, userId, "name")

        then:
        1 * householdAuthorizationService.assertUserBelongsToHousehold(householdId, userId)
        1 * householdRepository.findById(householdId) >> null
        0 * householdRepository.update(_, _, _)
        thrown(ResourceNotFoundException)
    }

    // ==================================
    // createHousehold
    // ==================================

    def "createHouseholdは世帯を作成し本人をメンバーとして登録してからinsert結果を返す"() {
        given:
        Long userId = 10L
        String name = "マイ世帯"

        // insert後の世帯（IDが振られている想定）
        def inserted = Mock(HouseholdModel) {
            getHouseholdId() >> 123L
        }

        // ユーザプロフィール
        def userModel = UserModel.reconstruct(
                userId,
                "user@example.com",
                "hashed",
                "表示名太郎",
                "ja",
                null,
                true
        )

        when:
        def result = service.createHousehold(userId, name)

        then:
        // HouseholdModel.create(...) で生成されたインスタンスが渡ってくるので
        // insert 時点では houseId == null であることだけ軽く確認
        1 * householdRepository.insert(_, userId, ProgramType.ONL_HLD.code) >> { args ->
            def m = args[0] as HouseholdModel
            assert m.householdId == null
            assert m.isOwner(userId)   // create(name, userId) の仕様に依存（オーナー設定されている想定）
            return inserted
        }

        1 * userService.getProfile(userId) >> userModel

        // ★ここがさっきの修正版の肝：
        // inserted.getHouseholdId() が createMember に渡されていることを検証
        1 * householdMemberService.createMember(123L, userId, "表示名太郎", userId)

        and:
        result == inserted
    }
}
