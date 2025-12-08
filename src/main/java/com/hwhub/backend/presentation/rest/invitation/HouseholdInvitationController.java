package com.hwhub.backend.presentation.rest.invitation;

import com.hwhub.backend.application.service.HouseholdInvitationService;
import com.hwhub.backend.domain.model.HouseholdInvitationModel;
import com.hwhub.backend.presentation.rest.invitation.dto.InvitationResponce;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/household-invitations")
@RequiredArgsConstructor
public class HouseholdInvitationController {

  private final HouseholdInvitationService service;

  @GetMapping("/{token}")
  public InvitationResponce getInvitation(@PathVariable String token) {
    HouseholdInvitationModel model = service.getInvitation(token);
    return InvitationResponce.fromModel(model);
  }

  @PostMapping("/{token}/accept")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void accept(@PathVariable String token, Authentication authentication) {

    Long loginUserId = Long.valueOf(authentication.getName());
    service.acceptInvitation(token, loginUserId);
  }

  @PostMapping("/{token}/decline")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void decline(@PathVariable String token, Authentication authentication) {
    Long loginUserId = Long.valueOf(authentication.getName());
    service.declineInvitation(token, loginUserId);
  }

  @PostMapping("/{token}/revoke")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void revoke(@PathVariable String token, Authentication authentication) {
    Long loginUserId = Long.valueOf(authentication.getName());
    service.revokeInvitation(token, loginUserId);
  }
}
