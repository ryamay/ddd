package com.yamari.idddd.application.users;

import com.yamari.idddd.domain.models.users.IUserFactory;
import com.yamari.idddd.domain.models.users.IUserRepository;
import com.yamari.idddd.domain.models.users.MailAddress;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserName;
import com.yamari.idddd.domain.services.UserService;
import org.springframework.transaction.annotation.Transactional;

public class UserRegisterService {

  private IUserRepository userRepository;
  private UserService userService;
  private IUserFactory userFactory;

  public UserRegisterService(
      IUserRepository userRepository, UserService userService, IUserFactory userFactory) {
    this.userRepository = userRepository;
    this.userService = userService;
    this.userFactory = userFactory;
  }

  @Transactional
  public void handle(UserRegisterCommand command) throws Exception {
    User user =
        userFactory.create(
            new UserName(command.getName()), new MailAddress(command.getMailAddress()));

    if (userService.exists(user)) {
      throw new CannotResisterUserException(user, "ÉÜÅ[ÉUÇÕä˘Ç…ë∂ç›ÇµÇƒÇ¢Ç‹Ç∑ÅB");
    }

    userRepository.save(user);
  }
}
