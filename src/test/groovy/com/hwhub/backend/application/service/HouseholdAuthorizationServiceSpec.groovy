package com.hwhub.backend.application.service

import com.hwhub.backend.domain.repository.HouseholdMemberRepository
import org.springframework.security.access.AccessDeniedException
import spock.lang.Specification

class HouseholdAuthorizationServiceSpec extends Specification{

    HouseholdMemberRepository householdMemberRepository = Mock()
    HouseholdAuthorizationService service =
            new HouseholdAuthorizationService(householdMemberRepository)

    def "assertUserBelongsToHouseholdはユーザが世帯に属している場合は何もせず終了する"() {
        given:
        Long householdId = 1L
        Long userId = 10L

        when:
        service.assertUserBelongsToHousehold(householdId, userId)

        then: "リポジトリがtrueを返せば例外は投げられない"
        1 * householdMemberRepository.existsActiveByHouseholdIdAndUserId(householdId, userId) >> true
        noExceptionThrown()
    }

    def "assertUserBelongsToHouseholdはユーザが世帯に属していない場合AccessDeniedExceptionを投げる"() {
        given:
        Long householdId = 2L
        Long userId = 20L

        when:
        service.assertUserBelongsToHousehold(householdId, userId)

        then:
        1 * householdMemberRepository.existsActiveByHouseholdIdAndUserId(householdId, userId) >> false
        def ex = thrown(AccessDeniedException)
        ex.message.contains("userId=20")
        ex.message.contains("householdId=2")
    }

    def "canAccessHouseholdはリポジトリの結果をそのまま返す（アクセス可）"() {
        given:
        Long householdId = 3L
        Long userId = 30L

        when:
        boolean result = service.canAccessHousehold(householdId, userId)

        then:
        1 * householdMemberRepository.existsActiveByHouseholdIdAndUserId(householdId, userId) >> true
        result
    }

    def "canAccessHouseholdはリポジトリの結果をそのまま返す（アクセス不可）"() {
        given:
        Long householdId = 4L
        Long userId = 40L

        when:
        boolean result = service.canAccessHousehold(householdId, userId)

        then:
        1 * householdMemberRepository.existsActiveByHouseholdIdAndUserId(householdId, userId) >> false
        !result
    }
}
