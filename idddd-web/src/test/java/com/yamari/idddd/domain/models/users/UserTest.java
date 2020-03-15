package com.yamari.idddd.domain.models.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class UserTest {

  public User testTarget = new User(new UserName("test"), new MailAddress("test@example.com"));

  @Test
  public void successWithNameAndMailAddress() {
    UserName name = new UserName("test");
    MailAddress address = new MailAddress("test@example.com");
    User target = new User(name, address);

    assertThat(target.id).isNotNull();
    assertThat(target.name).isEqualTo(name);
    assertThat(target.mailAddress).isEqualTo(address);
  }

  @Test
  public void successWithIdAndNameAndMailAddress() {
    UserId id = new UserId("testId");
    UserName name = new UserName("testName");
    MailAddress address = new MailAddress("test@example.com");
    User target = new User(id, name, address);

    assertThat(target.id).isEqualTo(id);
    assertThat(target.name).isEqualTo(name);
    assertThat(target.mailAddress).isEqualTo(address);
  }

  @Test
  public void failureWithNullId() {
    UserId id = null;
    UserName name = new UserName("testName");
    MailAddress address = new MailAddress("test@example.com");
    assertThatThrownBy(() -> new User(id, name, address))
        .isInstanceOf(IllegalArgumentException.class).hasMessage("idは必ず入力してください。");
  }

  @Test
  public void successChangeName() {
    UserName changedName = new UserName("test_changed");
    testTarget.changeName(changedName);

    assertThat(testTarget.name).isEqualTo(changedName);
  }

  @Test
  public void failureChangeName() {
    UserName changedName = null;
    assertThatThrownBy(() -> testTarget.changeName(changedName))
        .isInstanceOf(IllegalArgumentException.class).hasMessage("nameは必ず入力してください。");
  }

  @Test
  public void successChangeMailAddress() {
    MailAddress changedAddress = new MailAddress("testchanged@example.com");
    testTarget.changeMailAddress(changedAddress);

    assertThat(testTarget.mailAddress).isEqualTo(changedAddress);
  }

  @Test
  public void failureChangeMailAddress() {
    MailAddress changedAddress = null;
    assertThatThrownBy(() -> testTarget.changeMailAddress(changedAddress))
        .isInstanceOf(IllegalArgumentException.class).hasMessage("mailAddressは必ず入力してください。");
  }

  @Test
  public void testEquals() {
    UserName name = new UserName("test");
    MailAddress address = new MailAddress("test@example.com");
    User target = new User(name, address);

    assertTrue(target.equals(target));
    assertFalse(target.equals(null));
    assertFalse(target.equals(address));

    // 同じUserIdの場合はtrue
    User sameIdUser = new User(new UserId(target.id.getValue()), new UserName("sameId"),
        new MailAddress("sameid@example.com"));

    assertTrue(target.equals(sameIdUser));

    // 異なるUserIdの場合はfalse
    User differentIdUser = new User(new UserId(target.id.getValue() + "diff"), new UserName("diff"),
        new MailAddress("diff@example.com"));

    assertFalse(target.equals(differentIdUser));
  }


}
