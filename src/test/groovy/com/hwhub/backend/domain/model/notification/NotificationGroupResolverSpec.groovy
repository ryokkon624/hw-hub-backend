package com.hwhub.backend.domain.model.notification

import com.hwhub.backend.domain.enums.NotificationGroup
import com.hwhub.backend.domain.enums.NotificationType
import spock.lang.Specification

class NotificationGroupResolverSpec extends Specification {

    def "resolveは各NotificationTypeに対して正しいNotificationGroupを返す"() {
        expect:
        NotificationGroupResolver.resolve(type) == expectedGroup

        where:
        type                                            | expectedGroup
        NotificationType.INVITATION_ACCEPTED            | NotificationGroup.HOUSEHOLD
        NotificationType.INVITATION_DECLINED            | NotificationGroup.HOUSEHOLD
        NotificationType.HAVE_BEEN_REMOVED              | NotificationGroup.HOUSEHOLD
        NotificationType.LEFT_THE_HOUSEHOLD             | NotificationGroup.HOUSEHOLD
        NotificationType.ASSIGNED_TO_THE_OWNER          | NotificationGroup.HOUSEHOLD
        NotificationType.TASK_ASSIGNED                  | NotificationGroup.TASK_ASSIGNMENT
        NotificationType.YOUR_TASK_WAS_TAKEN            | NotificationGroup.TASK_ASSIGNMENT
        NotificationType.BE_DUMPED_TASK                 | NotificationGroup.TASK_ASSIGNMENT
        NotificationType.YOUR_INQUIRY_HAS_BEEN_REPLIED  | NotificationGroup.INQUIRY
    }
}
