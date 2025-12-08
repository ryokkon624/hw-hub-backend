package com.hwhub.backend.application.service;

import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.model.HouseholdMemberModel;
import com.hwhub.backend.domain.model.HouseholdModel;
import com.hwhub.backend.domain.repository.HouseholdMemberRepository;
import com.hwhub.backend.domain.repository.HouseholdRepository;
import com.hwhub.backend.domain.repository.HouseworkRepository;
import com.hwhub.backend.domain.repository.HouseworkTaskRepository;
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HouseholdMemberService {

  private final HouseholdMemberRepository memberRepository;
  private final HouseholdRepository householdRepository;
  private final HouseworkRepository houseworkRepository;
  private final HouseworkTaskRepository houseworkTaskRepository;
  private final HouseholdAuthorizationService authorizationService;
  private final UserIconService iconService;

  /**
   * 指定された世帯IDのメンバーを取得する。
   *
   * @param householdId 世帯ID
   * @return 指定された世帯IDのメンバー
   */
  public List<HouseholdMemberModel> getMembers(Long householdId) {
    List<HouseholdMemberModel> list = memberRepository.findActiveByHouseholdId(householdId);
    return list.stream()
        .map(
            e -> {
              e.changeIconUrl(iconService.getIconUrl(e.getProfileImageKey()));
              return e;
            })
        .toList();
  }

  /**
   * ニックネームを更新する。
   *
   * @param householdId 世帯ID
   * @param userId ユーザID
   * @param nickname ニックネーム
   */
  @Transactional
  public void updateMyNickname(Long householdId, Long userId, String nickname) {
    // 認可チェック
    authorizationService.assertUserBelongsToHousehold(householdId, userId);

    HouseholdMemberModel model = memberRepository.findById(householdId, userId);
    if (model == null) {
      throw new ResourceNotFoundException(
          "HouseholdMember not found for householdId=" + householdId + ", userId=" + userId);
    }
    model.changeNickname(nickname);

    memberRepository.update(model, userId, ProgramType.ONL_HLDMEM.getCode());
  }

  /**
   * メンバーを追加する。
   *
   * @param householdId 世帯ID
   * @param userId ユーザID
   * @param displayName ユーザの表示名
   * @param loginUserId ログインユーザのユーザID
   */
  @Transactional
  public void createMember(Long householdId, Long userId, String displayName, Long loginUserId) {

    // 過去に離脱したメンバーの場合がありえる
    HouseholdMemberModel model = memberRepository.findById(householdId, userId);
    if (model == null) {
      model = HouseholdMemberModel.create(householdId, userId, displayName);
      memberRepository.insert(model, loginUserId, ProgramType.ONL_HLDMEM.getCode());
    } else {
      // 離脱からの復帰
      model.rejoin();
      memberRepository.update(model, loginUserId, ProgramType.ONL_HLDMEM.getCode());
    }
  }

  /**
   * 自身が指定された世帯から離脱する。
   *
   * @param householdId 世帯ID
   * @param userId ユーザID
   */
  public void deleteMyself(Long householdId, Long userId) {
    // 認可チェック
    authorizationService.assertUserBelongsToHousehold(householdId, userId);

    HouseholdMemberModel model = memberRepository.findById(householdId, userId);
    model.leave();
    memberRepository.update(model, userId, ProgramType.ONL_HLDMEM.getCode());

    // 世帯ID内の担当となっている家事、タスクから担当解除
    clearAssignee(householdId, userId, userId, ProgramType.ONL_HLDMEM.getCode());
  }

  /**
   * 指定されたユーザを世帯から離脱させる。
   *
   * @param householdId 世帯ID
   * @param userId ユーザID
   * @param loginUserId　ログインユーザのユーザID
   */
  public void deleteMember(Long householdId, Long userId, Long loginUserId) {

    // 認可チェック
    if (!authorizationService.canAccessHousehold(householdId, loginUserId)) {
      throw new AccessDeniedException(
          "User does not belong to household: userId="
              + loginUserId
              + ", householdId="
              + householdId);
    }

    // オーナーであるかチェック
    HouseholdModel household = householdRepository.findById(householdId);
    if (!household.isOwner(loginUserId)) {
      throw new AccessDeniedException(
          "User cannot delete members because they are not the household owner.: userId="
              + loginUserId
              + ", householdId="
              + householdId);
    }

    HouseholdMemberModel model = memberRepository.findById(householdId, userId);
    model.leave();
    memberRepository.update(model, loginUserId, ProgramType.ONL_HLDMEM.getCode());

    // 世帯ID内の担当となっている家事、タスクから担当解除
    clearAssignee(householdId, userId, loginUserId, ProgramType.ONL_HLDMEM.getCode());
  }

  /**
   * 指定されたユーザが世帯内で担当する家事、タスクから担当を解除する。
   *
   * @param householdId 世帯ID
   * @param userId 担当から解除するユーザのユーザID
   * @param loginUserId ログインユーザのユーザID
   * @param program 更新プログラム名
   */
  private void clearAssignee(Long householdId, Long userId, Long loginUserId, String program) {
    // 家事マスタからの担当解除
    houseworkRepository.clearAssignee(householdId, userId, loginUserId, program);
    // タスクからの担当解除
    houseworkTaskRepository.clearAssignee(householdId, userId, loginUserId, program);
  }
}
