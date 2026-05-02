package com.hwhub.backend.presentation.rest.invitation;

import com.hwhub.backend.application.service.HouseholdInvitationService;
import com.hwhub.backend.domain.model.HouseholdInvitationModel;
import com.hwhub.backend.presentation.rest.invitation.dto.InvitationResponce;
import com.hwhub.backend.security.CurrentUserId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/household-invitations")
@RequiredArgsConstructor
public class HouseholdInvitationController {

  private final HouseholdInvitationService service;

  @Operation(security = {})
  @GetMapping("/{token}")
  public InvitationResponce getInvitation(@PathVariable("token") String token) {
    HouseholdInvitationModel model = service.getInvitation(token);
    return InvitationResponce.fromModel(model);
  }

  @PostMapping("/{token}/accept")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void accept(@PathVariable("token") String token, @CurrentUserId Long loginUserId) {
    service.acceptInvitation(token, loginUserId);
  }

  @PostMapping("/{token}/decline")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void decline(@PathVariable("token") String token, @CurrentUserId Long loginUserId) {
    service.declineInvitation(token, loginUserId);
  }

  @PostMapping("/{token}/revoke")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void revoke(@PathVariable("token") String token, @CurrentUserId Long loginUserId) {
    service.revokeInvitation(token, loginUserId);
  }
}
