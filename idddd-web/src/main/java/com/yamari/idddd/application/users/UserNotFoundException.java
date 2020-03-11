package com.yamari.idddd.application.users;

import com.yamari.idddd.domain.models.users.UserId;

public class UserNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;

  public UserNotFoundException(UserId userId) {
    super("UserId:" + userId.getValue() + "ÇÃÉÜÅ[ÉUÇ™ë∂ç›ÇµÇ‹ÇπÇÒÇ≈ÇµÇΩÅB");
  }
}
