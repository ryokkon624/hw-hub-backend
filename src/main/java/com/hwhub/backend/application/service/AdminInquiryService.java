package com.hwhub.backend.application.service;

import com.hwhub.backend.application.service.inquiry.InquiryService;
import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.enums.SenderType;
import com.hwhub.backend.domain.model.inquiry.AdminInquirySearchCondition;
import com.hwhub.backend.domain.model.inquiry.DailyInquiryMessage;
import com.hwhub.backend.domain.model.inquiry.DailyInquiryStatus;
import com.hwhub.backend.domain.model.inquiry.InquiryAdmin;
import com.hwhub.backend.domain.model.inquiry.InquiryId;
import com.hwhub.backend.domain.model.inquiry.InquiryModel;
import com.hwhub.backend.domain.model.inquiry.InquiryStatusSummary;
import com.hwhub.backend.domain.repository.InquiryRepository;
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminInquiryService {

  private final InquiryService inquiryService;
  private final InquiryRepository inquiryRepository;

  /**
   * スタッフ対応待ち一覧を取得する
   *
   * @return スタッフ対応待ち一覧
   */
  @Transactional(readOnly = true)
  public List<InquiryAdmin> findPendingStaff() {
    return inquiryRepository.findPendingStaff();
  }

  /**
   * 問い合わせを検索する
   *
   * @param condition 検索条件
   * @return 検索結果
   */
  @Transactional(readOnly = true)
  public List<InquiryAdmin> searchInquiries(AdminInquirySearchCondition condition) {
    return inquiryRepository.searchInquiries(condition);
  }

  /**
   * 管理者として問い合わせ詳細を取得（userId チェックなし）。
   *
   * @param inquiryId 問い合わせID
   * @return 問い合わせModel
   */
  @Transactional(readOnly = true)
  public InquiryModel getInquiryAsAdmin(Long inquiryId) {
    return inquiryRepository
        .findById(new InquiryId(inquiryId))
        .orElseThrow(() -> new ResourceNotFoundException("Inquiry not found: " + inquiryId));
  }

  /**
   * 日別・ステータス別の問い合わせ件数を集計する
   *
   * @param days 集計対象日数
   * @return 日別統計一覧
   */
  @Transactional(readOnly = true)
  public List<DailyInquiryStatus> findDailyStats(int days) {
    return inquiryRepository.findDailyStats(days);
  }

  /**
   * 日別・送信者タイプ別のメッセージ件数を集計する
   *
   * @param days 集計対象日数
   * @return 日別統計一覧
   */
  @Transactional(readOnly = true)
  public List<DailyInquiryMessage> findMessageDailyStats(int days) {
    return inquiryRepository.findMessageDailyStats(days);
  }

  /**
   * ステータス別件数サマリーを取得する
   *
   * @return ステータスサマリー
   */
  @Transactional(readOnly = true)
  public InquiryStatusSummary findStatusSummary() {
    return inquiryRepository.findStatusSummary();
  }

  /**
   * スタッフとして返信する。ステータスを STAFF_ANSWERED に更新する。
   *
   * @param inquiryId 問い合わせID
   * @param body 返信内容
   * @param operatorUserId 操作者ID
   */
  @Transactional
  public void replyAsStaff(Long inquiryId, String body, Long operatorUserId) {
    inquiryService.addMessage(
        new InquiryId(inquiryId), operatorUserId, body, SenderType.STAFF, ProgramType.ONL_ADM_INQ);
  }
}
