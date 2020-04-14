package com.yamari.idddd.application.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import com.yamari.idddd.domain.models.users.MailAddress;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;
import com.yamari.idddd.domain.models.users.UserName;
import com.yamari.idddd.domain.services.UserService;
import com.yamari.idddd.repository.inMemory.InMemoryUserRepository;

public class UserRegisterServiceTest {
  public InMemoryUserRepository userRepository = new InMemoryUserRepository();
  public UserService userService = new UserService(userRepository);

  public UserRegisterService targetService = new UserRegisterService(userRepository, userService);

  @Before
  public void initializeRepository() {
    userRepository.store.clear();
    // DBに存在するユーザを設定
    UserId existsId = new UserId("000000");
    UserName existsName = new UserName("exists");
    User existsUser = new User(existsId, existsName, new MailAddress("exists@example.com"));
    userRepository.store.put(existsId, existsUser);
  }

  @Test
  public void testHandleWithAbsentUser() {
    String registerUserName = "absent";
    String registerMailAddress = "absent@example.com";
    UserRegisterCommand command = new UserRegisterCommand(registerUserName, registerMailAddress);
    try {
      targetService.handle(command);
    } catch (Exception e) {
      // 例外が発生した場合はテスト失敗
      fail();
    }

    // ユーザ登録が正常に行われたか、repositoryから確認
    boolean isRegistered = userRepository.store.values().stream()
        .filter(i -> registerUserName.equals(i.name.getValue())).findFirst().isPresent();
    assertThat(isRegistered).isTrue();
  }

  @Test
  public void testHandleWithExistingUser() {
    String registerUserName = "exists";
    String registerMailAddress = "arbitrary@example.com";
    UserRegisterCommand command = new UserRegisterCommand(registerUserName, registerMailAddress);

    assertThatThrownBy(() -> {
      targetService.handle(command);
    }).isInstanceOf(CannotResisterUserException.class)
        .hasMessageContaining("ユーザは既に存在しています。" + "userName:" + registerUserName);



  }
}
