package com.yamari.idddd.application.users;

import com.yamari.idddd.domain.models.users.IUserRepository;
import com.yamari.idddd.domain.models.users.MailAddress;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserName;
import com.yamari.idddd.domain.services.UserService;

public class UserRegisterService {

  private IUserRepository userRepository;
  private UserService userService;

  public UserRegisterService(IUserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  public void register(String userName, String mailAddress) throws CannotResisterUserException {
    // TODO: Userの変更がApplicationServiceに影響しないようにしたい。→UserRegisterCommandを作成する。
    User user = new User(new UserName(userName), new MailAddress(mailAddress));

    if (userService.exists(user)) {
      throw new CannotResisterUserException(user, "ユーザは既に存在しています。");
    }

    userRepository.save(user);
  }
}
