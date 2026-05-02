package com.hwhub.backend.presentation.rest.housework;

import com.hwhub.backend.application.service.HouseholdAuthorizationService;
import com.hwhub.backend.application.service.HouseworkService;
import com.hwhub.backend.domain.model.HouseworkModel;
import com.hwhub.backend.presentation.rest.housework.dto.HouseworkDto;
import com.hwhub.backend.presentation.rest.housework.dto.HouseworkSaveRequest;
import com.hwhub.backend.security.CurrentUserId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/houseworks")
@RequiredArgsConstructor
@Validated
public class HouseworkController {

  private final HouseworkService houseworkService;
  private final HouseholdAuthorizationService householdAuthorizationService;

  /** 家事マスタの一覧取得。 GET api/houseworks?householdId=1 */
  @GetMapping
  public List<HouseworkDto> list(
      @RequestParam("householdId") @NotNull @Positive Long householdId,
      @CurrentUserId Long loginUserId) {
    // 認可チェック
    householdAuthorizationService.assertUserBelongsToHousehold(householdId, loginUserId);

    return houseworkService.listByHousehold(householdId).stream()
        .map(HouseworkDto::fromModel)
        .toList();
  }

  /** 家事マスタの単一取得。 GET /api/houseworks */
  // GET /api/houseworks/{houseworkId}
  @GetMapping("/{houseworkId}")
  public HouseworkDto getOne(
      @PathVariable("houseworkId") Long houseworkId, @CurrentUserId Long loginUserId) {
    HouseworkModel model = houseworkService.findById(houseworkId);
    householdAuthorizationService.assertUserBelongsToHousehold(model.getHouseholdId(), loginUserId);

    return HouseworkDto.fromModel(model);
  }

  /** 家事マスタ登録 POST /api/houseworks */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public HouseworkDto createHousework(
      @Valid @RequestBody HouseworkSaveRequest request, @CurrentUserId Long loginUserId) {
    HouseworkModel model = request.toModelForCreate();
    HouseworkModel created = houseworkService.createHousework(model, loginUserId);
    return HouseworkDto.fromModel(created);
  }

  /** 家事マスタ更新 PUT /api/houseworks/{houseworkId} */
  @PutMapping("/{houseworkId}")
  public HouseworkDto updateHousework(
      @PathVariable("houseworkId") Long houseworkId,
      @Valid @RequestBody HouseworkSaveRequest request,
      @CurrentUserId Long loginUserId) {
    // 認可チェック
    householdAuthorizationService.assertUserBelongsToHousehold(
        request.getHouseholdId(), loginUserId);

    HouseworkModel model = request.toModelForCreate();
    HouseworkModel updated = houseworkService.updateHousework(houseworkId, model, loginUserId);
    return HouseworkDto.fromModel(updated);
  }

  /** 家事マスタ削除 DELETE /api/houseworks/{id} */
  @DeleteMapping("/{houseworkId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteHousework(@PathVariable("houseworkId") Long houseworkId) {
    houseworkService.deleteHousework(houseworkId);
  }
}
