package com.hwhub.backend.domain.model.notification;

import com.hwhub.backend.domain.enums.NotificationLinkType;
import java.util.Objects;

public record NotificationLink(NotificationLinkType linkType, Long linkId) {
  public NotificationLink {
    Objects.requireNonNull(linkType, "linkType is required");

    if (linkType == NotificationLinkType.NONE) {
      if (linkId != null) {
        throw new IllegalArgumentException("linkId must be null when linkType is NONE");
      }
    }
    // NONE以外は linkId null 許容
  }

  public static NotificationLink none() {
    return new NotificationLink(NotificationLinkType.NONE, null);
  }
}
