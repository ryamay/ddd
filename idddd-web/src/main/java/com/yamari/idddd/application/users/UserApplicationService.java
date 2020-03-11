package com.yamari.idddd.application.users;

import com.yamari.idddd.domain.models.users.IUserRepository;
import com.yamari.idddd.domain.models.users.MailAddress;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;
import com.yamari.idddd.domain.models.users.UserName;
import com.yamari.idddd.domain.services.UserService;

public class UserApplicationService {

  private IUserRepository userRepository;
  private UserService userService;

  public UserApplicationService(IUserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  public void register(String userName, String mailAddress) throws Exception {
    // TODO: Userの変更がApplicationServiceに影響しないようにしたい。
    User user = new User(new UserName(userName), new MailAddress(mailAddress));

    if (userService.exists(user)) {
      throw new Exception(userName + "は既に存在しています。");
    }

    userRepository.save(user);
  }

  public UserData get(String userId) {
    UserId targetId = new UserId(userId);
    User user = userRepository.find(targetId);

    // 外部にドメインオブジェクトを公開しないよう、
    // DTOに詰め替えてreturnする。
    // userのプロパティが増減しても対応可能。
    return (user == null) ? null : new UserData(user);
  }
}
