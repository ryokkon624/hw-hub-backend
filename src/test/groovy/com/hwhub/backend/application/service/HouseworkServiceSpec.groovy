package com.hwhub.backend.application.service

import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.enums.RecurrenceType
import com.hwhub.backend.domain.model.HouseworkModel
import com.hwhub.backend.domain.model.HouseworkTaskRecalcRequestModel
import com.hwhub.backend.domain.repository.HouseworkRepository
import com.hwhub.backend.domain.repository.HouseworkTaskRecalcRequestRepository
import org.springframework.security.access.AccessDeniedException
import spock.lang.Specification

import java.time.LocalDate

class HouseworkServiceSpec extends Specification{

    HouseworkRepository houseworkRepository = Mock()
    HouseholdAuthorizationService householdAuthorizationService = Mock()
    HouseworkTaskRecalcRequestRepository taskRecalcRepository = Mock()

    HouseworkService service = new HouseworkService(
            houseworkRepository,
            householdAuthorizationService,
            taskRecalcRepository
    )

    // ==================================
    // listByHousehold / findById
    // ==================================

    def "listByHouseholdは指定世帯の家事一覧を返す"() {
        given:
        Long householdId = 1L
        def list = [Mock(HouseworkModel)]

        when:
        def result = service.listByHousehold(householdId)

        then:
        1 * houseworkRepository.findByHouseholdId(householdId) >> list
        result == list
    }

    def "findByIdは指定IDの家事を返す"() {
        given:
        Long houseworkId = 10L
        def model = Mock(HouseworkModel)

        when:
        def result = service.findById(houseworkId)

        then:
        1 * houseworkRepository.findByHouseworkId(houseworkId) >> model
        result == model
    }

    // ==================================
    // createHousework
    // ==================================

    def "createHouseworkはdefaultAssigneeUserIdがnullなら認可チェックせずinsertする"() {
        given:
        Long userId = 99L
        Long householdId = 1L

        def model = HouseworkModel.create(
                householdId,
                "家事名",
                "説明",
                "CAT",
                RecurrenceType.WEEKLY.code,
                21,
                null,
                null,
                null,
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                null // defaultAssigneeUserId
        )

        when:
        def result = service.createHousework(model, userId)

        then:
        0 * householdAuthorizationService.canAccessHousehold(_, _)
        1 * houseworkRepository.insert(model, userId, ProgramType.ONL_HWR.code)
        result.is(model)
    }

    def "createHouseworkはdefaultAssigneeUserIdが世帯メンバーでない場合AccessDeniedException"() {
        given:
        Long userId = 99L
        Long householdId = 1L
        Long defaultAssignee = 10L

        def model = HouseworkModel.create(
                householdId,
                "家事名",
                "説明",
                "CAT",
                RecurrenceType.WEEKLY.code,
                21,
                null,
                null,
                null,
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                defaultAssignee
        )

        when:
        service.createHousework(model, userId)

        then:
        1 * householdAuthorizationService.canAccessHousehold(householdId, defaultAssignee) >> false
        0 * houseworkRepository.insert(_, _, _)
        thrown(AccessDeniedException)
    }

    def "createHouseworkはdefaultAssigneeUserIdが世帯メンバーならinsertする"() {
        given:
        Long userId = 99L
        Long householdId = 1L
        Long defaultAssignee = 10L

        def model = HouseworkModel.create(
                householdId,
                "家事名",
                "説明",
                "CAT",
                RecurrenceType.WEEKLY.code,
                21,
                null,
                null,
                null,
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                defaultAssignee
        )

        when:
        def result = service.createHousework(model, userId)

        then:
        1 * householdAuthorizationService.canAccessHousehold(householdId, defaultAssignee) >> true
        1 * houseworkRepository.insert(model, userId, ProgramType.ONL_HWR.code)
        result.is(model)
    }

    // ==================================
    // updateHousework
    // ==================================

    def "updateHouseworkはdefaultAssigneeUserIdが世帯メンバーでない場合IllegalArgumentException"() {
        given:
        Long houseworkId = 100L
        Long householdId = 1L
        Long userId = 99L
        Long defaultAssignee = 10L

        def input = Mock(HouseworkModel) {
            getHouseholdId() >> householdId
            getDefaultAssigneeUserId() >> defaultAssignee
        }

        when:
        service.updateHousework(houseworkId, input, userId)

        then:
        1 * householdAuthorizationService.canAccessHousehold(householdId, defaultAssignee) >> false
        0 * houseworkRepository.findByHouseworkId(_)
        thrown(IllegalArgumentException)
    }

    def "updateHouseworkはWEEKLYのときsetRecurrenceWeeklyを呼び出し更新と再計算キュー投入を行う"() {
        given:
        Long houseworkId = 100L
        Long householdId = 1L
        Long userId = 99L
        Integer weeklyDays = 21
        LocalDate start = LocalDate.of(2025, 1, 1)
        LocalDate end = LocalDate.of(2025, 1, 31)

        def input = Mock(HouseworkModel) {
            getHouseholdId() >> householdId
            getDefaultAssigneeUserId() >> null
            getName() >> "家事名"
            getDescription() >> "説明"
            getCategory() >> "CAT"
            getRecurrenceType() >> RecurrenceType.WEEKLY.code
            getWeeklyDays() >> weeklyDays
            getStartDate() >> start
            getEndDate() >> end
        }

        def existing = Mock(HouseworkModel) {
            getHouseworkId() >> houseworkId
        }

        when:
        def result = service.updateHousework(houseworkId, input, userId)

        then:
        0 * householdAuthorizationService.canAccessHousehold(_, _)

        1 * houseworkRepository.findByHouseworkId(houseworkId) >> existing

        1 * existing.setBasicInfo("家事名", "説明", "CAT")
        1 * existing.setRecurrenceWeekly(weeklyDays)
        1 * existing.setEffectivePriod(start, end)
        1 * existing.setDefaultAssigneeUserId(null)

        1 * houseworkRepository.update(existing, userId, ProgramType.ONL_HWR.code)

        1 * taskRecalcRepository.enqueue(_, userId, ProgramType.ONL_HWR.code) >> { args ->
            HouseworkTaskRecalcRequestModel req = args[0] as HouseworkTaskRecalcRequestModel
            assert req.houseworkId == houseworkId
        }

        result.is(existing)
    }

    def "updateHouseworkはMONTHLYのときsetRecurrenceMonthlyを呼び出す"() {
        given:
        Long houseworkId = 101L
        Long householdId = 1L
        Long userId = 99L
        Integer dayOfMonth = 15
        LocalDate start = LocalDate.of(2025, 2, 1)
        LocalDate end = LocalDate.of(2025, 2, 28)

        def input = Mock(HouseworkModel) {
            getHouseholdId() >> householdId
            getDefaultAssigneeUserId() >> null
            getName() >> "月次家事"
            getDescription() >> "説明"
            getCategory() >> "CAT"
            getRecurrenceType() >> RecurrenceType.MONTHLY.code
            getDayOfMonth() >> dayOfMonth
            getStartDate() >> start
            getEndDate() >> end
        }

        def existing = Mock(HouseworkModel) {
            getHouseworkId() >> houseworkId
        }

        when:
        service.updateHousework(houseworkId, input, userId)

        then:
        1 * houseworkRepository.findByHouseworkId(houseworkId) >> existing
        1 * existing.setBasicInfo("月次家事", "説明", "CAT")
        1 * existing.setRecurrenceMonthly(dayOfMonth)
        1 * existing.setEffectivePriod(start, end)
        1 * existing.setDefaultAssigneeUserId(null)
        1 * houseworkRepository.update(existing, userId, ProgramType.ONL_HWR.code)
        1 * taskRecalcRepository.enqueue(_, userId, ProgramType.ONL_HWR.code)
    }

    def "updateHouseworkはNTH_WEEKDAYのときsetRecurrenceNthweekdayを呼び出す"() {
        given:
        Long houseworkId = 102L
        Long householdId = 1L
        Long userId = 99L
        Integer nthWeek = 2
        Integer weekday = 3
        LocalDate start = LocalDate.of(2025, 3, 1)
        LocalDate end = LocalDate.of(2025, 3, 31)

        def input = Mock(HouseworkModel) {
            getHouseholdId() >> householdId
            getDefaultAssigneeUserId() >> null
            getName() >> "第n曜家事"
            getDescription() >> "説明"
            getCategory() >> "CAT"
            getRecurrenceType() >> RecurrenceType.NTH_WEEKDAY.code
            getNthWeek() >> nthWeek
            getWeekday() >> weekday
            getStartDate() >> start
            getEndDate() >> end
        }

        def existing = Mock(HouseworkModel) {
            getHouseworkId() >> houseworkId
        }

        when:
        service.updateHousework(houseworkId, input, userId)

        then:
        1 * houseworkRepository.findByHouseworkId(houseworkId) >> existing
        1 * existing.setBasicInfo("第n曜家事", "説明", "CAT")
        1 * existing.setRecurrenceNthweekday(nthWeek, weekday)
        1 * existing.setEffectivePriod(start, end)
        1 * existing.setDefaultAssigneeUserId(null)
        1 * houseworkRepository.update(existing, userId, ProgramType.ONL_HWR.code)
        1 * taskRecalcRepository.enqueue(_, userId, ProgramType.ONL_HWR.code)
    }

    def "updateHouseworkはdefaultAssigneeUserIdが世帯メンバーのとき認可チェックを通過して更新される"() {
        given:
        Long houseworkId = 200L
        Long householdId = 1L
        Long userId = 99L
        Long defaultAssignee = 10L
        Integer weeklyDays = 21
        LocalDate start = LocalDate.of(2025, 4, 1)
        LocalDate end = LocalDate.of(2025, 4, 30)

        def input = Mock(HouseworkModel) {
            getHouseholdId() >> householdId
            getDefaultAssigneeUserId() >> defaultAssignee
            getName() >> "家事名"
            getDescription() >> "説明"
            getCategory() >> "CAT"
            getRecurrenceType() >> RecurrenceType.WEEKLY.code
            getWeeklyDays() >> weeklyDays
            getStartDate() >> start
            getEndDate() >> end
        }

        def existing = Mock(HouseworkModel) {
            getHouseworkId() >> houseworkId
        }

        when:
        def result = service.updateHousework(houseworkId, input, userId)

        then: "defaultAssigneeUserId != null なので canAccessHousehold が呼ばれ、trueなら続行される"
        1 * householdAuthorizationService.canAccessHousehold(householdId, defaultAssignee) >> true
        1 * houseworkRepository.findByHouseworkId(houseworkId) >> existing

        1 * existing.setBasicInfo("家事名", "説明", "CAT")
        1 * existing.setRecurrenceWeekly(weeklyDays)
        1 * existing.setEffectivePriod(start, end)
        1 * existing.setDefaultAssigneeUserId(defaultAssignee)

        1 * houseworkRepository.update(existing, userId, ProgramType.ONL_HWR.code)
        1 * taskRecalcRepository.enqueue(_, userId, ProgramType.ONL_HWR.code)

        result.is(existing)
    }

    // ==================================
    // deleteHousework
    // ==================================

    def "deleteHouseworkは指定IDの家事を削除する"() {
        when:
        service.deleteHousework(123L)

        then:
        1 * houseworkRepository.delete(123L)
    }
}
