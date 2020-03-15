package com.yamari.idddd.application.users;

import com.yamari.idddd.domain.models.users.IUserRepository;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;

public class UserGetInfoService {

  IUserRepository userRepository;

  public UserGetInfoService(IUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserData handle(UserGetCommand command) throws UserNotFoundException {
    UserId targetId = new UserId(command.getId());
    User user = userRepository.find(targetId);

    if (user == null) {
      throw new UserNotFoundException(targetId);
    }

    // 外部にドメインオブジェクトを公開しないよう、
    // DTOに詰め替えてreturnする。
    // userのプロパティが増減しても対応可能。
    return new UserData(user);
  }
}
