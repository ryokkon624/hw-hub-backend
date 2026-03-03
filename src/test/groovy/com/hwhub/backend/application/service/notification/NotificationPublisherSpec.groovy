package com.hwhub.backend.application.service.notification

import com.hwhub.backend.domain.enums.NotificationType
import com.hwhub.backend.domain.model.HouseholdMemberModel
import com.hwhub.backend.domain.model.HouseholdModel
import com.hwhub.backend.domain.model.HouseworkTaskModel
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.repository.HouseholdMemberRepository
import com.hwhub.backend.domain.repository.HouseholdRepository
import com.hwhub.backend.domain.repository.NotificationEventRepository
import com.hwhub.backend.domain.repository.NotificationRepository
import com.hwhub.backend.domain.repository.UserRepository
import spock.lang.Specification
import spock.lang.Unroll
import java.time.LocalDate
import java.time.LocalDateTime

class NotificationPublisherSpec extends Specification {

    def permissionService = Mock(NotificationPermissionService)
    def notificationRepository = Mock(NotificationRepository)
    def notificationEventRepository = Mock(NotificationEventRepository)
    def householdRepository = Mock(HouseholdRepository)
    def householdMemberRepository = Mock(HouseholdMemberRepository)
    def userRepository = Mock(UserRepository)

    def publisher = new NotificationPublisher(
            permissionService,
            notificationRepository,
            notificationEventRepository,
            householdRepository,
            householdMemberRepository,
            userRepository
    )

    def setup() {
        // デフォルトで通知許可
        permissionService.canReceive(_, _) >> true
    }

    def "publishRemoveMemberByOwnerが正しくNotificationRepositoryを呼び出すこと"() {
        setup:
        def household = Mock(HouseholdModel)
        household.getName() >> "テスト世帯"
        householdRepository.findById(1L) >> household

        when:
        publisher.publishRemoveMemberByOwner(1L, 2L, 3L, "prg")

        then:
        1 * notificationRepository.insert({ it.targetUserId == 3L && it.notificationType == NotificationType.HAVE_BEEN_REMOVED && it.message.params().householdName == "テスト世帯" }, "prg")
    }

    def "publishMemberRemovedが正しくNotificationRepositoryを呼び出すこと"() {
        setup:
        def member1 = Mock(HouseholdMemberModel)
        member1.getUserId() >> 4L
        def member2 = Mock(HouseholdMemberModel)
        member2.getUserId() >> 5L
        householdMemberRepository.findActiveByHouseholdId(1L) >> [member1, member2]

        def household = Mock(HouseholdModel)
        household.getName() >> "テスト世帯"
        householdRepository.findById(1L) >> household

        def user = Mock(UserModel)
        user.getDisplayName() >> "テストユーザー"
        userRepository.findById(2L) >> Optional.of(user)

        when:
        // removedUserNameがnullの場合
        publisher.publishMemberRemoved(1L, 2L, null, "prg")

        then:
        1 * notificationRepository.bulkInsert({ it.size() == 2 && it[0].targetUserId == 4L && it[1].targetUserId == 5L && it[0].message.params().memberName == "テストユーザー" }, "prg")
    }

    def "publishMemberRemovedでremovedUserNameが指定されている場合はそちらが利用されること"() {
        setup:
        def member1 = Mock(HouseholdMemberModel)
        member1.getUserId() >> 4L
        householdMemberRepository.findActiveByHouseholdId(1L) >> [member1]

        def household = Mock(HouseholdModel)
        household.getName() >> "テスト世帯"
        householdRepository.findById(1L) >> household

        when:
        publisher.publishMemberRemoved(1L, 2L, "指定名", "prg")

        then:
        1 * notificationRepository.bulkInsert({ it[0].message.params().memberName == "指定名" }, "prg")
        0 * userRepository.findById(_)
    }

    def "publishMemberRemovedでremovedUserNameがnullかつUserRepositoryから取得できない場合はnullのまま処理されること"() {
        setup:
        def member1 = Mock(HouseholdMemberModel)
        member1.getUserId() >> 4L
        householdMemberRepository.findActiveByHouseholdId(1L) >> [member1]

        def household = Mock(HouseholdModel)
        household.getName() >> "テスト世帯"
        householdRepository.findById(1L) >> household

        userRepository.findById(2L) >> Optional.empty()

        when:
        publisher.publishMemberRemoved(1L, 2L, null, "prg")

        then:
        1 * notificationRepository.bulkInsert({ it[0].message.params().memberName == null }, "prg")
    }

    def "publishAcceptInvitationが正しくNotificationRepositoryを呼び出すこと"() {
        setup:
        def household = Mock(HouseholdModel)
        household.getName() >> "テスト世帯"
        householdRepository.findById(1L) >> household

        def user = Mock(UserModel)
        user.getDisplayName() >> "承認ユーザー"
        userRepository.findById(2L) >> Optional.of(user)

        when:
        publisher.publishAcceptInvitation(1L, 2L, 3L, "prg")

        then:
        1 * notificationRepository.insert({ it.targetUserId == 3L && it.actorUserId == 2L && it.message.params().memberName == "承認ユーザー" && it.notificationType == NotificationType.INVITATION_ACCEPTED }, "prg")
    }

    def "publishAcceptInvitationでユーザー情報が取得できない場合はmemberNameは設定されないこと"() {
        setup:
        def household = Mock(HouseholdModel)
        household.getName() >> "テスト世帯"
        householdRepository.findById(1L) >> household

        userRepository.findById(2L) >> Optional.empty()

        when:
        publisher.publishAcceptInvitation(1L, 2L, 3L, "prg")

        then:
        1 * notificationRepository.insert({ !it.message.params().containsKey("memberName") }, "prg")
    }

    def "publishDeclineInvitationが正しくNotificationRepositoryを呼び出すこと"() {
        setup:
        def household = Mock(HouseholdModel)
        household.getName() >> "テスト世帯"
        householdRepository.findById(1L) >> household

        def user = Mock(UserModel)
        user.getDisplayName() >> "辞退ユーザー"
        userRepository.findById(2L) >> Optional.of(user)

        when:
        publisher.publishDeclineInvitation(1L, 2L, 3L, "prg")

        then:
        1 * notificationRepository.insert({ it.targetUserId == 3L && it.actorUserId == 2L && it.message.params().memberName == "辞退ユーザー" && it.notificationType == NotificationType.INVITATION_DECLINED }, "prg")
    }

    def "publishDeclineInvitationでユーザー情報が取得できない場合はmemberNameは設定されないこと"() {
        setup:
        def household = Mock(HouseholdModel)
        household.getName() >> "テスト世帯"
        householdRepository.findById(1L) >> household

        userRepository.findById(2L) >> Optional.empty()

        when:
        publisher.publishDeclineInvitation(1L, 2L, 3L, "prg")

        then:
        1 * notificationRepository.insert({ !it.message.params().containsKey("memberName") }, "prg")
    }

    def "publishAssigned2Ownerが正しくNotificationRepositoryを呼び出すこと"() {
        setup:
        def household = Mock(HouseholdModel)
        household.getName() >> "テスト世帯"
        householdRepository.findById(1L) >> household

        when:
        publisher.publishAssigned2Owner(1L, 2L, 3L, "prg")

        then:
        1 * notificationRepository.insert({ it.targetUserId == 3L && it.actorUserId == 2L && it.notificationType == NotificationType.ASSIGNED_TO_THE_OWNER }, "prg")
    }

    @Unroll
    def "publishTaskAssignedEvent - パターン: #desc -> 実行の有無(#expectCall), 通知先(#expTarget), タイプ(#expType)"() {
        setup:
        def task = Mock(HouseworkTaskModel)
        task.getAssigneeUserId() >> afterAssignee
        task.getHouseholdId() >> 1L
        task.getHouseworkTaskId() >> 100L
        task.getTargetDate() >> LocalDate.now()

        when:
        publisher.publishTaskAssignedEvent(task, beforeAssignee, operatorUserId, "prg")

        then:
        if (expectCall) {
            1 * notificationEventRepository.insert({ it.targetUserId == expTarget && it.notificationType == expType }, operatorUserId, "prg")
        } else {
            0 * notificationEventRepository.insert(*_)
        }

        where:
        desc                          | beforeAssignee | operatorUserId | afterAssignee || expectCall | expTarget | expType
        "未割り当てを自分に"          | null           | 1L             | 1L            || false      | null      | null
        "未割り当てを他人に"          | null           | 1L             | 2L            || true       | 2L        | NotificationType.TASK_ASSIGNED
        "自分以外を未割り当てに"      | 1L             | 2L             | null          || false      | null      | null
        "変更なし"                    | 1L             | 1L             | 1L            || false      | null      | null
        "自分から他人に（押し付け）"  | 1L             | 1L             | 2L            || true       | 2L        | NotificationType.BE_DUMPED_TASK
        "他人から自分に（奪う）"      | 1L             | 2L             | 2L            || true       | 1L        | NotificationType.YOUR_TASK_WAS_TAKEN
        "第三者が他人のタスクを付替"  | 1L             | 2L             | 3L            || true       | 3L        | NotificationType.TASK_ASSIGNED
        "操作者がnullかつ未割り当て"  | null           | null           | 2L            || true       | 2L        | NotificationType.TASK_ASSIGNED
        "操作者がnullかつ第三者付替"  | 1L             | null           | 2L            || true       | 2L        | NotificationType.TASK_ASSIGNED
    }
}
