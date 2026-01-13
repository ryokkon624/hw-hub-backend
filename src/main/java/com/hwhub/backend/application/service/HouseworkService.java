package com.hwhub.backend.application.service;

import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.enums.RecurrenceType;
import com.hwhub.backend.domain.model.HouseworkModel;
import com.hwhub.backend.domain.model.HouseworkTaskRecalcRequestModel;
import com.hwhub.backend.domain.repository.HouseworkRepository;
import com.hwhub.backend.domain.repository.HouseworkTaskRecalcRequestRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/** 家事に関するユースケース実行クラス。 Controller から呼ばれるアプリケーションサービス。 */
@Service
@RequiredArgsConstructor
public class HouseworkService {

  private final HouseworkRepository houseworkRepository;
  private final HouseholdAuthorizationService householdAuthorizationService;
  private final HouseworkTaskRecalcRequestRepository taskRecalcRepository;

  /** 家事マスタ一覧取得 */
  public List<HouseworkModel> listByHousehold(Long householdId) {
    return houseworkRepository.findByHouseholdId(householdId);
  }

  /** 家事マスタ単一取得 */
  public HouseworkModel findById(Long houseworkId) {
    return houseworkRepository.findByHouseworkId(houseworkId);
  }

  /** 家事マスタ登録 */
  public HouseworkModel createHousework(HouseworkModel model, Long userId) {

    Long householdId = model.getHouseholdId();
    Long defaultAssigneeUserId = model.getDefaultAssigneeUserId();

    // 認可チェック：デフォルト担当者が世帯メンバーではない場合
    if (defaultAssigneeUserId != null) {
      if (!householdAuthorizationService.canAccessHousehold(householdId, defaultAssigneeUserId)) {
        throw new AccessDeniedException(
            "User does not belong to household: userId=" + userId + ", householdId=" + householdId);
      }
    }

    return houseworkRepository.insert(model, userId, ProgramType.ONL_HWR.getCode());
  }

  /** 家事マスタ更新 */
  public HouseworkModel updateHousework(Long houseworkId, HouseworkModel input, Long userId) {

    Long householdId = input.getHouseholdId();
    Long defaultAssigneeUserId = input.getDefaultAssigneeUserId();

    // 認可チェック：デフォルト担当者が世帯メンバーではない場合
    if (defaultAssigneeUserId != null) {
      if (!householdAuthorizationService.canAccessHousehold(householdId, defaultAssigneeUserId)) {
        throw new IllegalArgumentException(
            "defaultAssigneeUserId is not a member of the household.");
      }
    }

    HouseworkModel model = houseworkRepository.findByHouseworkId(houseworkId);
    model.setBasicInfo(input.getName(), input.getDescription(), input.getCategory());
    RecurrenceType type = RecurrenceType.fromCode(input.getRecurrenceType());
    switch (type) {
      case WEEKLY -> model.setRecurrenceWeekly(input.getWeeklyDays());
      case MONTHLY -> model.setRecurrenceMonthly(input.getDayOfMonth());
      case NTH_WEEKDAY -> model.setRecurrenceNthweekday(input.getNthWeek(), input.getWeekday());
      default -> throw new IllegalArgumentException("Unsupported recurrence type: " + type);
    }
    model.setEffectivePriod(input.getStartDate(), input.getEndDate());
    model.setDefaultAssigneeUserId(defaultAssigneeUserId);

    HouseworkModel updated =
        houseworkRepository.update(model, userId, ProgramType.ONL_HWR.getCode());

    // 家事タスクの反映リクエスト
    taskRecalcRepository.enqueue(
        HouseworkTaskRecalcRequestModel.create(updated.getHouseworkId()),
        userId,
        ProgramType.ONL_HWR.getCode());

    return updated;
  }

  public void deleteHousework(Long id) {
    houseworkRepository.delete(id);
  }
}
