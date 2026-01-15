package com.hwhub.backend.presentation.rest.housework;

import com.hwhub.backend.application.service.HouseworkTaskService;
import com.hwhub.backend.domain.enums.TaskStatus;
import com.hwhub.backend.domain.model.HouseworkTask4AssignModel;
import com.hwhub.backend.presentation.rest.housework.dto.HouseworkTaskResponse;
import com.hwhub.backend.presentation.rest.housework.dto.UpdateAssigneeRequest;
import com.hwhub.backend.presentation.rest.housework.dto.UpdateStatusRequest;
import com.hwhub.backend.validation.annotation.EnumValue;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/housework-tasks")
@RequiredArgsConstructor
@Validated
public class HouseworkTaskController {
  private final HouseworkTaskService houseworkTaskService;

  /**
   * 指定された世帯の家事タスク一覧を取得します。（割り当て画面などでの利用を想定）。
   *
   * @param householdId 家事タスクを検索する世帯ID
   * @param status 検索対象とするタスクの状態（例: 0=未完了）。デフォルトは未完了。
   * @param authentication 認証情報。ログインユーザーのIDを取得するために使用。
   * @return 該当する家事タスクのレスポンスオブジェクトのリスト
   */
  @GetMapping
  public List<HouseworkTaskResponse> getTasks(
      @RequestParam("householdId") Long householdId,
      @RequestParam(name = "status", defaultValue = "0") @EnumValue(enumClass = TaskStatus.class)
          String status,
      Authentication authentication) {

    Long userId = Long.valueOf(authentication.getName());

    List<HouseworkTask4AssignModel> models =
        houseworkTaskService.findForAssign(householdId, status, userId);
    return models.stream().map(HouseworkTaskResponse::fromModel).toList();
  }

  /**
   * 指定された家事タスクIDのタスクの担当者を変更します。
   *
   * @param taskId 担当者を変更する対象の家事タスクID
   * @param request 担当者として割り当てるユーザーIDと、割り当て理由を含むリクエストボディ
   * @param authentication 認証情報。操作を実行したログインユーザーのIDを取得するために使用。
   */
  @PatchMapping("/{taskId}/assign")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateAssignee(
      @PathVariable("taskId") Long taskId,
      @Valid @RequestBody UpdateAssigneeRequest request,
      Authentication authentication) {
    Long loginUserId = Long.valueOf(authentication.getName());
    houseworkTaskService.updateAssignee(
        taskId, request.assigneeUserId(), request.assignReasonType(), loginUserId);
  }

  /**
   * 指定された家事タスクIDのタスクのステータスを更新します。
   *
   * @param taskId 状態を更新する対象の家事タスクID
   * @param request 変更するステータスと、スキップの場合の理由を含むリクエストボディ
   * @param authentication 認証情報。操作を実行したログインユーザーのIDを取得するために使用。
   */
  @PatchMapping("/{taskId}/status")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateStatus(
      @PathVariable("taskId") Long taskId,
      @Valid @RequestBody UpdateStatusRequest request,
      Authentication authentication) {
    Long loginUserId = Long.valueOf(authentication.getName());
    houseworkTaskService.updateStatus(
        taskId, request.status(), request.skippedReason(), loginUserId);
  }
}
