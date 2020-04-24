package com.yamari.idddd.application.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.fail;

import com.yamari.idddd.domain.models.users.MailAddress;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;
import com.yamari.idddd.domain.models.users.UserName;
import com.yamari.idddd.domain.services.UserService;
import com.yamari.idddd.repository.inMemory.InMemoryUserRepository;
import org.junit.Before;
import org.junit.Test;

public class UserUpdateInfoServiceTest {

  public InMemoryUserRepository userRepository = new InMemoryUserRepository();
  public UserService userService = new UserService(userRepository);

  public UserUpdateInfoService targetService =
      new UserUpdateInfoService(userRepository, userService);

  private static final UserId EXISTS_ID = new UserId("000000");
  private static final UserName EXISTS_NAME = new UserName("exists");
  private static final MailAddress EXISTS_ADDRESS = new MailAddress("exists@example.com");

  @Before
  public void initializeRepository() {
    // DBに存在するユーザを設定
    User existsUser = new User(EXISTS_ID, EXISTS_NAME, EXISTS_ADDRESS);
    userRepository.store.put(EXISTS_ID, existsUser);
  }

  @Before
  public void clearRepository() {
    userRepository.store.clear();
  }

  @Test
  public void testUpdateNameAndAddress() {
    String targetId = EXISTS_ID.getValue();
    UserUpdateCommand command = new UserUpdateCommand(targetId);
    String updatedName = "updated";
    String updatedAddress = "updatedaddress@example.com";
    command.setName(updatedName);
    command.setMailAddress(updatedAddress);

    try {
      targetService.handle(command);
    } catch (Exception e) {
      // 例外が発生した場合はテスト失敗
      fail(e.getMessage());
    }

    assertThat(userRepository.store.get(EXISTS_ID).id.getValue()).isEqualTo(EXISTS_ID.getValue());
    assertThat(userRepository.store.get(EXISTS_ID).name.getValue()).isEqualTo(updatedName);
    assertThat(userRepository.store.get(EXISTS_ID).mailAddress.getValue())
        .isEqualTo(updatedAddress);
  }

  @Test
  public void testUpdateNameOnly() {
    String targetId = EXISTS_ID.getValue();
    UserUpdateCommand command = new UserUpdateCommand(targetId);
    String updatedName = "updated";
    command.setName(updatedName);

    try {
      targetService.handle(command);
    } catch (Exception e) {
      // 例外が発生した場合はテスト失敗
      fail(e.getMessage());
    }

    assertThat(userRepository.store.get(EXISTS_ID).id.getValue()).isEqualTo(EXISTS_ID.getValue());
    assertThat(userRepository.store.get(EXISTS_ID).name.getValue()).isEqualTo(updatedName);
    assertThat(userRepository.store.get(EXISTS_ID).mailAddress.getValue())
        .isEqualTo(EXISTS_ADDRESS.getValue());
  }

  @Test
  public void testUpdateAddressOnly() {
    String targetId = EXISTS_ID.getValue();
    UserUpdateCommand command = new UserUpdateCommand(targetId);
    String updatedAddress = "updatedaddress@example.com";
    command.setMailAddress(updatedAddress);

    try {
      targetService.handle(command);
    } catch (Exception e) {
      // 例外が発生した場合はテスト失敗
      fail();
    }

    assertThat(userRepository.store.get(EXISTS_ID).id.getValue()).isEqualTo(EXISTS_ID.getValue());
    assertThat(userRepository.store.get(EXISTS_ID).name.getValue())
        .isEqualTo(EXISTS_NAME.getValue());
    assertThat(userRepository.store.get(EXISTS_ID).mailAddress.getValue())
        .isEqualTo(updatedAddress);
  }

  @Test
  public void testUpdateNameToExistingUserName() {
    String targetName = "wantToUpdate";
    User anotherUser = new User(new UserName(targetName), new MailAddress("test@example.com"));
    userRepository.store.put(anotherUser.id, anotherUser);

    String targetId = EXISTS_ID.getValue();
    UserUpdateCommand command = new UserUpdateCommand(targetId);
    String updatedName = targetName;
    command.setName(updatedName);

    assertThatThrownBy(() -> targetService.handle(command))
        .isInstanceOf(CannotResisterUserException.class)
        .hasMessageContaining("ユーザはすでに存在しています。");
  }

  @Test
  public void testHandleWithAbsentUser() {
    String targetId = "absentUserId";
    UserUpdateCommand command = new UserUpdateCommand(targetId);
    String updatedName = "updated";
    String updatedAddress = "updatedaddress@example.com";
    command.setName(updatedName);
    command.setMailAddress(updatedAddress);

    assertThatThrownBy(() -> targetService.handle(command))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessageContaining("UserId:" + targetId + "のユーザが存在しませんでした。");
  }
}
