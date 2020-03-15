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

  public void register(String userName, String mailAddress) throws CannotResisterUserException {
    // TODO: Userの変更がApplicationServiceに影響しないようにしたい。
    User user = new User(new UserName(userName), new MailAddress(mailAddress));

    if (userService.exists(user)) {
      throw new CannotResisterUserException(user, "ユーザは既に存在しています。");
    }

    userRepository.save(user);
  }

  public void update(UserUpdateCommand command)
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
        throw new CannotResisterUserException(user, "ユーザはすでに存在しています。");
      }

    }

    String mailAddress = command.mailAddress;
    if (mailAddress != null) {
      MailAddress targetMailAddress = new MailAddress(mailAddress);
      user.changeMailAddress(targetMailAddress);
    }

    userRepository.save(user);
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

  /**
   * commandで指定したユーザを削除する。
   *
   * @param command 削除条件を指定するcommand
   * @return 削除対象が見つからない場合は、退会成功と判断してreturnする。
   */
  public void delete(UserDeleteCommand command) {
    UserId targetId = new UserId(command.getId());
    User targetUser = userRepository.find(targetId);

    if (targetUser == null) {
      // 削除対象が見つからない場合は、退会成功としてreturnする
      return;
    }

    userRepository.delete(targetUser);
  }

}
