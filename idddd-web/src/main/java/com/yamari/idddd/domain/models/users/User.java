package com.yamari.idddd.domain.models.users;

import java.util.Objects;
import java.util.UUID;

public class User {
  public UserId id;
  public UserName name;
  public MailAddress mailAddress;

  public User(UserName name, MailAddress mailAddress) {
    this.id = new UserId(UUID.randomUUID().toString());
    this.changeName(name);
    this.changeMailAddress(mailAddress);
  }

  public User(UserId id, UserName name, MailAddress mailAddress) {
    if (id == null) {
      throw new IllegalArgumentException("idは必ず入力してください。");
    }

    this.id = id;
    this.changeName(name);
    this.changeMailAddress(mailAddress);
  }

  public void changeName(UserName name) {
    if (name == null) {
      throw new IllegalArgumentException("nameは必ず入力してください。");
    }
    this.name = name;
  }

  public void changeMailAddress(MailAddress mailAddress) {
    if (mailAddress == null) {
      throw new IllegalArgumentException("mailAddressは必ず入力してください。");
    }
    this.mailAddress = mailAddress;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof User)) {
      return false;
    }

    User other = (User) obj;
    return Objects.equals(id, other.id);
  }
}
