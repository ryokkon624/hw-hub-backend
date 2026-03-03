package com.hwhub.backend.application.service.notification

import com.hwhub.backend.domain.enums.NotificationGroup
import com.hwhub.backend.domain.enums.NotificationType
import com.hwhub.backend.domain.repository.UserNotificationSettingRepository
import com.hwhub.backend.domain.repository.UserRepository
import spock.lang.Specification

class NotificationPermissionServiceSpec extends Specification {

    def userRepository = Mock(UserRepository)
    def settingRepository = Mock(UserNotificationSettingRepository)
    def service = new NotificationPermissionService(userRepository, settingRepository)

    def "canReceiveはグローバル通知が無効の場合falseを返す"() {
        when:
        def result = service.canReceive(1L, NotificationType.TASK_ASSIGNED)

        then:
        1 * userRepository.isNotificationEnabled(1L) >> false
        0 * settingRepository.findEnabled(_, _)
        !result
    }

    def "canReceiveはグローバル通知が有効でグループ設定がない場合trueを返す（デフォルトON）"() {
        when:
        def result = service.canReceive(1L, NotificationType.TASK_ASSIGNED)

        then:
        1 * userRepository.isNotificationEnabled(1L) >> true
        1 * settingRepository.findEnabled(1L, NotificationGroup.TASK_ASSIGNMENT) >> Optional.empty()
        result
    }

    def "canReceiveはグローバル通知が有効でグループ設定がtrueの場合trueを返す"() {
        when:
        def result = service.canReceive(1L, NotificationType.INVITATION_ACCEPTED)

        then:
        1 * userRepository.isNotificationEnabled(1L) >> true
        1 * settingRepository.findEnabled(1L, NotificationGroup.HOUSEHOLD) >> Optional.of(true)
        result
    }

    def "canReceiveはグローバル通知が有効でグループ設定がfalseの場合falseを返す"() {
        when:
        def result = service.canReceive(1L, NotificationType.HAVE_BEEN_REMOVED)

        then:
        1 * userRepository.isNotificationEnabled(1L) >> true
        1 * settingRepository.findEnabled(1L, NotificationGroup.HOUSEHOLD) >> Optional.of(false)
        !result
    }
}
