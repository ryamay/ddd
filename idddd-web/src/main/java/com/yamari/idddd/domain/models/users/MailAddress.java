package com.yamari.idddd.domain.models.users;

import java.util.regex.Pattern;

public class MailAddress {
  private static final Pattern emailPattern =
      Pattern.compile("/^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$/");
  private String value;

  public MailAddress(String value) {
    if (value == null || value.length() == 0) {
      throw new IllegalArgumentException("メールアドレスは必ず入力してください。");
    } else if (!emailPattern.matcher(value).matches()) {
      throw new IllegalArgumentException("不正なメールアドレスです。");
    }

    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

}
