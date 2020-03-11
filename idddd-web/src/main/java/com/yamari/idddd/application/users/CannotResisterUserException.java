package com.yamari.idddd.application.users;

import com.yamari.idddd.domain.models.users.User;

public class CannotResisterUserException extends Exception {

  private static final long serialVersionUID = 1L;

  public CannotResisterUserException(User user, String message) {
    super(message + "userName:" + user.name.getValue() + ", userId:" + user.id.getValue());
  }
}
