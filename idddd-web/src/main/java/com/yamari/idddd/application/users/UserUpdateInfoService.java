package com.yamari.idddd.application.users;

import com.yamari.idddd.domain.models.users.IUserRepository;
import com.yamari.idddd.domain.models.users.MailAddress;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;
import com.yamari.idddd.domain.models.users.UserName;
import com.yamari.idddd.domain.services.UserService;

public class UserUpdateInfoService {

  private IUserRepository userRepository;
  private UserService userService;

  public UserUpdateInfoService(IUserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  public void handle(UserUpdateCommand command)
      throws UserNotFoundException, CannotResisterUserException {
    UserId targetId = new UserId(command.id);
    User user = userRepository.find(targetId);
    if (user == null) {
      throw new UserNotFoundException(targetId);
    }

    String name = command.name;
    if (name != null) {
      UserName targetName = new UserName(name);
      user.changeName(targetName);
      if (userService.exists(user)) {
        throw new CannotResisterUserException(user, "ÉÜÅ[ÉUÇÕÇ∑Ç≈Ç…ë∂ç›ÇµÇƒÇ¢Ç‹Ç∑ÅB");
      }

    }

    String mailAddress = command.mailAddress;
    if (mailAddress != null) {
      MailAddress targetMailAddress = new MailAddress(mailAddress);
      user.changeMailAddress(targetMailAddress);
    }

    userRepository.save(user);
  }
}
