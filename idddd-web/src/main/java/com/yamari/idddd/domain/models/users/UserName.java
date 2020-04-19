package com.yamari.idddd.domain.models.users;

import java.util.Objects;

public class UserName {

  private String value;

  public UserName(String value) {
    if (value == null) {
      throw new IllegalArgumentException("ユーザ名は必ず入力してください。");
    }
    if (value.length() < 3) {
      throw new IllegalArgumentException("ユーザ名は3文字以上です。");
    }
    if (value.length() > 20) {
      throw new IllegalArgumentException("ユーザ名は20文字以下です。");
    }
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserName userName = (UserName) o;
    return value.equals(userName.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
