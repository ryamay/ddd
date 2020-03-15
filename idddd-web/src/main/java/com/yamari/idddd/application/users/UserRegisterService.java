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

  public void register(UserRegisterCommand command) throws CannotResisterUserException {
    User user =
        new User(new UserName(command.getName()), new MailAddress(command.getMailAddress()));

    if (userService.exists(user)) {
      throw new CannotResisterUserException(user, "ÉÜÅ[ÉUÇÕä˘Ç…ë∂ç›ÇµÇƒÇ¢Ç‹Ç∑ÅB");
    }

    userRepository.save(user);
  }
}
