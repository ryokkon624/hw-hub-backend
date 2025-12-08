package com.hwhub.backend.application.service;

import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.enums.TaskStatus;
import com.hwhub.backend.domain.model.HouseworkTask4AssignModel;
import com.hwhub.backend.domain.model.HouseworkTaskModel;
import com.hwhub.backend.domain.repository.HouseworkTaskRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HouseworkTaskService {

  private final HouseworkTaskRepository taskRepository;
  private final HouseholdAuthorizationService authService;

  @Transactional(readOnly = true)
  public List<HouseworkTask4AssignModel> findForAssign(
      Long householdId, String status, Long userId) {

    // 認可チェック
    if (!authService.canAccessHousehold(householdId, userId)) {
      throw new AccessDeniedException(
          "User does not belong to household: userId=" + userId + ", householdId=" + householdId);
    }

    return taskRepository.findForAssign(householdId, status);
  }

  @Transactional
  public void updateAssignee(
      Long houseworkTaskId, Long assigneeUserId, String assignResonType, Long loginUserId) {

    HouseworkTaskModel model = taskRepository.findById(houseworkTaskId);
    if (model == null) {
      throw new IllegalArgumentException("HouseworkTask not found. id=" + houseworkTaskId);
    }

    // 認可チェック
    if (!authService.canAccessHousehold(model.getHouseholdId(), loginUserId)) {
      throw new AccessDeniedException(
          "User does not belong to household: userId="
              + loginUserId
              + ", householdId="
              + model.getHouseholdId());
    }
    if (assigneeUserId != null
        && !authService.canAccessHousehold(model.getHouseholdId(), assigneeUserId)) {
      throw new AccessDeniedException(
          "User does not belong to household: userId="
              + assigneeUserId
              + ", householdId="
              + model.getHouseholdId());
    }

    model.changeAssignee(assigneeUserId, assignResonType, loginUserId);
    taskRepository.update(model, loginUserId, ProgramType.ONL_HWRTSK.getCode());
  }

  @Transactional
  public void updateStatus(
      Long houseworkTaskId, String status, String skippedReason, Long loginUserId) {

    HouseworkTaskModel model = taskRepository.findById(houseworkTaskId);
    if (model == null) {
      throw new IllegalArgumentException("HouseworkTask not found. id=" + houseworkTaskId);
    }

    // 認可チェック
    if (!authService.canAccessHousehold(model.getHouseholdId(), loginUserId)) {
      throw new AccessDeniedException(
          "User does not belong to household: userId="
              + loginUserId
              + ", householdId="
              + model.getHouseholdId());
    }

    TaskStatus taskStatus = TaskStatus.fromCode(status);
    switch (taskStatus) {
      case DONE -> model.complete();
      case SKIPPED -> model.skip(skippedReason);
      default -> throw new IllegalArgumentException("Unprocessable status: " + status);
    }
    taskRepository.update(model, loginUserId, ProgramType.ONL_HWRTSK.getCode());
  }
}
