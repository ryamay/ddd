package com.yamari.idddd.application.users;

import com.yamari.idddd.domain.models.users.IUserRepository;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;

public class UserGetInfoService {

  IUserRepository userRepository;

  public UserGetInfoService(IUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserData get(String userId) {
    UserId targetId = new UserId(userId);
    User user = userRepository.find(targetId);

    // 外部にドメインオブジェクトを公開しないよう、
    // DTOに詰め替えてreturnする。
    // userのプロパティが増減しても対応可能。
    // TODO:指定したユーザが存在しない場合、UserNotFoundExceptionをthrowする。
    return (user == null) ? null : new UserData(user);
  }
}
