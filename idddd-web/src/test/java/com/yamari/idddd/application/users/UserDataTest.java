package com.yamari.idddd.application.users;

import static org.assertj.core.api.Assertions.assertThat;

import com.yamari.idddd.domain.models.users.MailAddress;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;
import com.yamari.idddd.domain.models.users.UserName;
import org.junit.Test;

public class UserDataTest {

  @Test
  public void testUserDataWithString() {
    String id = "id";
    String name = "name";
    String mailAddress = "address@example.com";
    UserData target = new UserData(id, name, mailAddress);

    assertThat(target.getId()).isEqualTo(id);
    assertThat(target.getName()).isEqualTo(name);
    assertThat(target.getMailAddress()).isEqualTo(mailAddress);
  }

  @Test
  public void testUserDataWithUserDomainModel() {
    String id = "id";
    String name = "name";
    String mailAddress = "address@example.com";

    User user = new User(new UserId(id), new UserName(name), new MailAddress(mailAddress));

    UserData target = new UserData(user);

    assertThat(target.getId()).isEqualTo(id);
    assertThat(target.getName()).isEqualTo(name);
    assertThat(target.getMailAddress()).isEqualTo(mailAddress);
  }
}
