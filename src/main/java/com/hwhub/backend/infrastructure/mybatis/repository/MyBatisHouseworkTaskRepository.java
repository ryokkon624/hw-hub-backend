package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.model.HouseworkTask4AssignModel;
import com.hwhub.backend.domain.model.HouseworkTaskModel;
import com.hwhub.backend.domain.repository.HouseworkTaskRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.HouseworkTaskAssignmentHistConverter;
import com.hwhub.backend.infrastructure.mybatis.converter.HouseworkTaskConverter;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.HouseworkTaskAssignmentHistoryCustomMapper;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.HouseworkTaskCustomMapper;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseworkTask;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseworkTaskAssignmentHistory;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseworkTaskAssignmentHistoryExample;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.THouseworkTaskAssignmentHistoryMapper;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.THouseworkTaskMapper;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisHouseworkTaskRepository implements HouseworkTaskRepository {

  private final THouseworkTaskMapper mapper;
  private final HouseworkTaskCustomMapper customMapper;
  private final THouseworkTaskAssignmentHistoryMapper historyMapper;
  private final HouseworkTaskAssignmentHistoryCustomMapper historyCustomMapper;

  @Override
  public List<HouseworkTask4AssignModel> findForAssign(
      Long householdId, String status, LocalDate lowerDate) {
    return customMapper.selectTasksForAssign(householdId, status, lowerDate);
  }

  @Override
  public HouseworkTaskModel findById(Long houseworkTaskId) {
    THouseworkTask entity = mapper.selectByPrimaryKey(houseworkTaskId);

    // 家事タスク担当変更履歴の取得
    THouseworkTaskAssignmentHistoryExample example = new THouseworkTaskAssignmentHistoryExample();
    example.createCriteria().andHouseworkTaskIdEqualTo(houseworkTaskId);
    example.setOrderByClause("housework_task_assignment_history_id");
    List<THouseworkTaskAssignmentHistory> histories = historyMapper.selectByExample(example);

    return HouseworkTaskConverter.toModel(entity, histories);
  }

  @Override
  public HouseworkTaskModel update(HouseworkTaskModel model, Long userId, String program) {

    customMapper.updateByTaskId4Online(model, userId, program);

    THouseworkTaskAssignmentHistory insert =
        model.getHistories().stream()
            .filter(h -> h.getHouseworkTaskAssignmentHistoryId() == null)
            .findFirst()
            .map(HouseworkTaskAssignmentHistConverter::toEntity)
            .orElse(null);
    if (insert != null) {
      insert.setCreateUserId(userId);
      insert.setCreateProgram(program);
      insert.setUpdateUserId(userId);
      insert.setUpdateProgram(program);
      historyMapper.insertSelective(insert);
    }

    return this.findById(model.getHouseworkTaskId());
  }

  @Override
  public void clearAssignee(Long householdId, Long userId, Long loginUserId, String program) {
    historyCustomMapper.insertClearedAssigneeHistory(householdId, userId, loginUserId, program);
    customMapper.clearAssignee(householdId, userId, loginUserId, program);
  }

  @Override
  public long countByIdsAndHouseholdId(List<Long> taskIds, Long householdId) {
    return customMapper.countByIdsAndHouseholdId(taskIds, householdId);
  }

  @Override
  public void bulkUpdateStatus(
      List<Long> taskIds,
      String status,
      String skippedReason,
      LocalDate doneAt,
      Long userId,
      String program) {
    customMapper.bulkUpdateStatus(taskIds, status, skippedReason, doneAt, userId, program);
  }
}
