package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.HouseworkTaskRecalcRequestModel;

public interface HouseworkTaskRecalcRequestRepository {

  /**
   * タスク再計算リクエストをキューに追加する。 m_housework 更新と同じトランザクション内で呼び出す想定。
   *
   * @param model Create用のパラメータ
   * @param userId 操作ユーザ（WHO カラム用）
   * @param program プログラム名（m_code:0012）
   */
  void enqueue(HouseworkTaskRecalcRequestModel model, long userId, String program);
}
