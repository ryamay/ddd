package com.yamari.idddd.domain.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.yamari.idddd.domain.models.users.MailAddress;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;
import com.yamari.idddd.domain.models.users.UserName;
import com.yamari.idddd.repository.inMemory.users.InMemoryUserRepository;
import org.junit.Test;

public class UserServiceTest {

  public InMemoryUserRepository testRepository = new InMemoryUserRepository();

  public UserService targetService = new UserService(testRepository);

  @Test
  public void testExistsWithValidUser() {
    User savedUser =
        new User(new UserId("000000"), new UserName("saved"), new MailAddress("saved@example.com"));
    testRepository.save(savedUser);

    User targetUser =
        new User(
            new UserId("999999"), new UserName("target"), new MailAddress("target@example.com"));

    assertThat(targetService.exists(savedUser)).isTrue();
    assertThat(targetService.exists(targetUser)).isFalse();
  }

  @Test
  public void testExistsWithNull() {
    assertThatThrownBy(() -> targetService.exists(null)).isInstanceOf(NullPointerException.class);
  }
}
