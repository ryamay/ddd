package com.yamari.idddd.domain.models.users;

import java.util.Objects;

public class UserId {

  private String value;

  public UserId(String value) {
    if (value == null || value.length() == 0) {
      throw new IllegalArgumentException("ユーザIDは必ず入力してください。");
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
    UserId userId = (UserId) o;
    return value.equals(userId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
