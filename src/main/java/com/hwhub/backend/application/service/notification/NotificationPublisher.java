package com.hwhub.backend.application.service.notification;

import com.hwhub.backend.domain.enums.NotificationLinkType;
import com.hwhub.backend.domain.enums.NotificationType;
import com.hwhub.backend.domain.model.HouseholdMemberModel;
import com.hwhub.backend.domain.model.HouseholdModel;
import com.hwhub.backend.domain.model.HouseworkTaskModel;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.model.notification.NotificationEventModel;
import com.hwhub.backend.domain.model.notification.NotificationLink;
import com.hwhub.backend.domain.model.notification.NotificationMessage;
import com.hwhub.backend.domain.model.notification.NotificationModel;
import com.hwhub.backend.domain.repository.HouseholdMemberRepository;
import com.hwhub.backend.domain.repository.HouseholdRepository;
import com.hwhub.backend.domain.repository.NotificationEventRepository;
import com.hwhub.backend.domain.repository.NotificationRepository;
import com.hwhub.backend.domain.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationPublisher {

  private final NotificationRepository notificationRepository;
  private final NotificationEventRepository notificationEventRepository;
  private final HouseholdRepository householdRepository;
  private final HouseholdMemberRepository householdMemberRepository;
  private final UserRepository userRepository;

  /**
   * 世帯から削除された場合の通知。
   *
   * <ul>
   *   <li>同期：通知センターに即表示
   *   <li>通知先：削除された対象者
   *   <li>遷移先：なし
   * </ul>
   *
   * @param householdId 世帯ID
   * @param operatorUserId 操作者のユーザーID(世帯のオーナー)
   * @param targetUserId 通知対象のユーザーID(削除されたメンバー)
   * @param program プログラム名
   */
  @Transactional
  public void publishRemoveMemberByOwner(
      Long householdId, Long operatorUserId, Long targetUserId, String program) {

    // i18n key + params
    HouseholdModel household = householdRepository.findById(householdId);
    Map<String, Object> params = new HashMap<>();
    params.put("householdName", household.getName());
    NotificationMessage message =
        new NotificationMessage(
            "notifications.messages.removedFromHousehold.title",
            "notifications.messages.removedFromHousehold.body",
            params);

    // 通知発生日時
    LocalDateTime now = LocalDateTime.now();

    NotificationModel model =
        NotificationModel.newUnread(
            householdId,
            NotificationType.HAVE_BEEN_REMOVED,
            operatorUserId,
            targetUserId,
            message,
            NotificationLink.none(),
            now);

    notificationRepository.insert(model, program);
  }

  /**
   * 世帯から自発的に離脱した場合の通知。
   *
   * <ul>
   *   <li>同期：通知センターに即表示
   *   <li>通知先：世帯のメンバー全員
   *   <li>遷移先：おうち設定
   * </ul>
   *
   * @param householdId 世帯ID
   * @param removedUserId 離脱したメンバーのユーザーID
   * @param removedUserName 離脱したメンバーの表示名
   * @param program プログラム名
   */
  @Transactional
  public void publishMemberRemoved(
      Long householdId, Long removedUserId, String removedUserName, String program) {

    // 世帯メンバーの取得
    List<HouseholdMemberModel> members =
        householdMemberRepository.findActiveByHouseholdId(householdId);

    // i18n key + params
    HouseholdModel household = householdRepository.findById(householdId);
    if (removedUserName == null) {
      Optional<UserModel> userOpt = userRepository.findById(removedUserId);
      if (!userOpt.isEmpty()) {
        removedUserName = userOpt.get().getDisplayName();
      }
    }
    Map<String, Object> params = new HashMap<>();
    params.put("householdName", household.getName());
    params.put("memberName", removedUserName);
    NotificationMessage message =
        new NotificationMessage(
            "notifications.messages.leftHousehold.title",
            "notifications.messages.leftHousehold.body",
            params);

    NotificationLink link = new NotificationLink(NotificationLinkType.HOUSEHOLD, householdId);

    // 通知発生日時
    LocalDateTime now = LocalDateTime.now();

    List<NotificationModel> notifications =
        members.stream()
            .map(
                member ->
                    NotificationModel.newUnread(
                        householdId,
                        NotificationType.LEFT_THE_HOUSEHOLD,
                        removedUserId,
                        member.getUserId(),
                        message,
                        link,
                        now))
            .toList();

    notificationRepository.bulkInsert(notifications, program);
  }

  /**
   * 招待を承認した場合の通知。
   *
   * <ul>
   *   <li>同期：通知センターに即表示
   *   <li>通知先：招待の作成者
   *   <li>遷移先：おうち設定
   * </ul>
   *
   * @param householdId 世帯ID
   * @param acceptedUserId 承認したメンバーのユーザーID
   * @param inviterUserId 招待の作成者
   * @param program プログラム名
   */
  @Transactional
  public void publishAcceptInvitation(
      Long householdId, Long acceptedUserId, Long inviterUserId, String program) {

    // i18n key + params
    HouseholdModel household = householdRepository.findById(householdId);
    Map<String, Object> params = new HashMap<>();
    params.put("householdName", household.getName());
    Optional<UserModel> userOpt = userRepository.findById(acceptedUserId);
    if (!userOpt.isEmpty()) {
      params.put("memberName", userOpt.get().getDisplayName());
    }

    NotificationMessage message =
        new NotificationMessage(
            "notifications.messages.acceptInvitation.title",
            "notifications.messages.acceptInvitation.body",
            params);

    NotificationLink link = new NotificationLink(NotificationLinkType.INVITATION, householdId);

    LocalDateTime now = LocalDateTime.now();

    NotificationModel model =
        NotificationModel.newUnread(
            householdId,
            NotificationType.INVITATION_ACCEPTED,
            acceptedUserId,
            inviterUserId,
            message,
            link,
            now);

    notificationRepository.insert(model, program);
  }

  /**
   * 招待を辞退した場合の通知。
   *
   * <ul>
   *   <li>同期：通知センターに即表示
   *   <li>通知先：招待の作成者
   *   <li>遷移先：おうち設定
   * </ul>
   *
   * @param householdId 世帯ID
   * @param declinedUserId 辞退したメンバーのユーザーID
   * @param inviterUserId 招待の作成者
   * @param program プログラム名
   */
  @Transactional
  public void publishDeclineInvitation(
      Long householdId, Long declinedUserId, Long inviterUserId, String program) {

    // i18n key + params
    HouseholdModel household = householdRepository.findById(householdId);
    Map<String, Object> params = new HashMap<>();
    params.put("householdName", household.getName());
    Optional<UserModel> userOpt = userRepository.findById(declinedUserId);
    if (!userOpt.isEmpty()) {
      params.put("memberName", userOpt.get().getDisplayName());
    }

    NotificationMessage message =
        new NotificationMessage(
            "notifications.messages.declineInvitation.title",
            "notifications.messages.declineInvitation.body",
            params);

    NotificationLink link = new NotificationLink(NotificationLinkType.INVITATION, householdId);

    LocalDateTime now = LocalDateTime.now();

    NotificationModel model =
        NotificationModel.newUnread(
            householdId,
            NotificationType.INVITATION_DECLINED,
            declinedUserId,
            inviterUserId,
            message,
            link,
            now);

    notificationRepository.insert(model, program);
  }

  /**
   * おうちのオーナーに設定された場合の通知。
   *
   * <ul>
   *   <li>同期：通知センターに即表示
   *   <li>通知先：オーナーになったユーザー
   *   <li>遷移先：おうち設定
   * </ul>
   *
   * @param householdId 世帯ID
   * @param formerOwnerUserId 旧オーナーのユーザーID
   * @param newOwnerUserId 新オーナーのユーザーID
   * @param program プログラム名
   */
  @Transactional
  public void publishAssigned2Owner(
      Long householdId, Long formerOwnerUserId, Long newOwnerUserId, String program) {

    // i18n key + params
    HouseholdModel household = householdRepository.findById(householdId);
    Map<String, Object> params = new HashMap<>();
    params.put("householdName", household.getName());

    NotificationMessage message =
        new NotificationMessage(
            "notifications.messages.assigned2Owner.title",
            "notifications.messages.assigned2Owner.body",
            params);

    NotificationLink link = new NotificationLink(NotificationLinkType.HOUSEHOLD, householdId);

    LocalDateTime now = LocalDateTime.now();

    NotificationModel model =
        NotificationModel.newUnread(
            householdId,
            NotificationType.ASSIGNED_TO_THE_OWNER,
            formerOwnerUserId,
            newOwnerUserId,
            message,
            link,
            now);

    notificationRepository.insert(model, program);
  }

  /**
   * タスク割り当てイベント
   *
   * <ul>
   *   <li>非同期：eventを一定時間で集約して表示する。集約処理はバッチで行う
   *   <li>遷移先：MyTasks
   * </ul>
   *
   * 通知先：
   *
   * <table>
   * <tr>
   * <td>Before Assignee</td>
   * <td>Operator</td>
   * <td>After Assignee</td>
   * <td>Notification Recipient</td>
   * <td>Notification Type</td>
   * </tr>
   * <tr>
   * <td>null</td>
   * <td>user1</td>
   * <td>user1</td>
   * <td>-</td>
   * <td>-</td>
   * </tr>
   * <tr>
   * <td>null</td>
   * <td>user1</td>
   * <td>user2</td>
   * <td>user2</td>
   * <td>TASK_ASSIGNED</td>
   * </tr>
   * <tr>
   * <td>user1</td>
   * <td>user1</td>
   * <td>null</td>
   * <td>-</td>
   * <td>-</td>
   * </tr>
   * <tr>
   * <td>user1</td>
   * <td>user1</td>
   * <td>user1</td>
   * <td>-</td>
   * <td>-</td>
   * </tr>
   * <tr>
   * <td>user1</td>
   * <td>user1</td>
   * <td>user2</td>
   * <td>user2</td>
   * <td>BE_DUMPED_TASK</td>
   * </tr>
   * <tr>
   * <td>user1</td>
   * <td>user2</td>
   * <td>null</td>
   * <td>-</td>
   * <td>-</td>
   * </tr>
   * <tr>
   * <td>user1</td>
   * <td>user2</td>
   * <td>user1</td>
   * <td>-</td>
   * <td>-</td>
   * </tr>
   * <tr>
   * <td>user1</td>
   * <td>user2</td>
   * <td>user2</td>
   * <td>user1</td>
   * <td>YOUR_TASK_WAS_TAKEN</td>
   * </tr>
   * <tr>
   * <td>user1</td>
   * <td>user2</td>
   * <td>user3</td>
   * <td>user3</td>
   * <td>TASK_ASSIGNED</td>
   * </tr>
   * </table>
   *
   * @param householdId 世帯ID
   * @param actorUserId 操作者のユーザーID
   * @param targetUserId 通知対象のユーザーID
   * @param taskId タスクID
   */
  @Transactional
  public void publishTaskAssignedEvent(
      HouseworkTaskModel task, Long beforeAssigneeUserId, Long operatorUserId, String program) {

    Long afterAssigneeUserId = task.getAssigneeUserId();

    // 未割り当て
    if (afterAssigneeUserId == null) return;
    // 変更なし
    if (afterAssigneeUserId.equals(beforeAssigneeUserId)) return;

    // 操作者がタスクの担当者
    if (operatorUserId != null && operatorUserId.equals(afterAssigneeUserId)) {
      // 未割当を自分が取った
      if (beforeAssigneeUserId == null) return;

      // 他の人からタスクを奪った：beforeAssigneeに通知
      NotificationType type = NotificationType.YOUR_TASK_WAS_TAKEN;
      Long targetUserId = beforeAssigneeUserId;
      insertTaskNotificationEvent(task, type, targetUserId, operatorUserId, program);
    } else {

      // 操作者が自分以外に割り当てた：afterassigneeに通知
      NotificationType type;
      Long targetUserId = afterAssigneeUserId;

      if (beforeAssigneeUserId == null) {
        // 未割当を割り当てた
        type = NotificationType.TASK_ASSIGNED;
      } else if (operatorUserId != null && operatorUserId.equals(beforeAssigneeUserId)) {
        // 他の人にタスクを押し付けた
        type = NotificationType.BE_DUMPED_TASK;
      } else {
        // 第3者がタスクを付け替えた
        type = NotificationType.TASK_ASSIGNED;
      }

      insertTaskNotificationEvent(task, type, targetUserId, operatorUserId, program);
    }
  }

  private void insertTaskNotificationEvent(
      HouseworkTaskModel task,
      NotificationType type,
      Long targetUserId,
      Long operatorUserId,
      String program) {
    NotificationEventModel event =
        NotificationEventModel.newUnprocessed(
            task.getHouseholdId(),
            type,
            operatorUserId,
            targetUserId,
            task.getHouseworkTaskId(),
            task.getTargetDate(),
            LocalDateTime.now());
    notificationEventRepository.insert(event, operatorUserId, program);
  }
}
