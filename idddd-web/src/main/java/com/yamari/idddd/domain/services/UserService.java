package com.yamari.idddd.domain.services;

import com.yamari.idddd.domain.models.users.IUserRepository;
import com.yamari.idddd.domain.models.users.User;

public class UserService {

  private IUserRepository userRepository;

  public UserService(IUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * ユーザの重複確認を行う。
   *
   * @param user
   * @return 重複していればtrue, 重複していなければfalse
   */
  public boolean exists(User user) {
    User found = userRepository.find(user.name);

    return found != null;
  }
}
