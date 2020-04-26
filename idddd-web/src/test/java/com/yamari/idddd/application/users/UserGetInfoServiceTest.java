package com.yamari.idddd.application.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.fail;

import com.yamari.idddd.domain.models.users.MailAddress;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;
import com.yamari.idddd.domain.models.users.UserName;
import com.yamari.idddd.repository.inMemory.users.InMemoryUserRepository;
import org.junit.Before;
import org.junit.Test;

public class UserGetInfoServiceTest {
  public InMemoryUserRepository userRepository = new InMemoryUserRepository();
  public UserGetInfoService targetService = new UserGetInfoService(userRepository);

  private static final UserId EXISTS_ID = new UserId("000000");
  private static final UserName EXISTS_NAME = new UserName("exists");
  private static final MailAddress EXISTS_ADDRESS = new MailAddress("exists@example.com");

  @Before
  public void initializeRepository() {
    userRepository.store.clear();
    // DBに存在するユーザを設定
    User existsUser = new User(EXISTS_ID, EXISTS_NAME, EXISTS_ADDRESS);
    userRepository.store.put(EXISTS_ID, existsUser);
  }

  @Test
  public void testHandleWithExistingUser() {
    String targetUserId = EXISTS_ID.getValue();
    UserGetCommand command = new UserGetCommand(targetUserId);
    UserData fetchedUser = null;
    try {
      fetchedUser = targetService.handle(command);
    } catch (Exception e) {
      // 例外が発生した場合はテスト失敗
      fail();
    }

    // ユーザ情報の取得が正常に行われたか確認。
    assertThat(fetchedUser.getId()).isEqualTo(targetUserId);
    assertThat(fetchedUser.getName()).isEqualTo(EXISTS_NAME.getValue());
    assertThat(fetchedUser.getMailAddress()).isEqualTo(EXISTS_ADDRESS.getValue());
  }

  @Test
  public void testHandleWithAbsentUser() {
    String targetUserId = "999999";
    UserGetCommand command = new UserGetCommand(targetUserId);

    assertThatThrownBy(() -> targetService.handle(command))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessage("UserId:999999のユーザが存在しませんでした。");
  }
}
