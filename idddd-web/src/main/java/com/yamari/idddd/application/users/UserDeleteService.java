package com.yamari.idddd.application.users;

import com.yamari.idddd.domain.models.users.IUserRepository;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;

public class UserDeleteService {

  IUserRepository userRepository;

  public UserDeleteService(IUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void handle(UserDeleteCommand command) {
    UserId targetId = new UserId(command.getId());
    User user = userRepository.find(targetId);

    if (user == null) {
      // íœ‘ÎÛ‚Ìƒ†[ƒU‚ªŒ©‚Â‚©‚ç‚È‚¢ê‡‚Í‘Ş‰ï¬Œ÷‚Æ‚·‚é
      return;
    }

    userRepository.delete(user);
  }
}
