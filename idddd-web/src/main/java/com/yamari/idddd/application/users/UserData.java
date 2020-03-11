package com.yamari.idddd.application.users;

import com.yamari.idddd.domain.models.users.User;

public class UserData {
  private String id;
  private String name;
  private String mailAddress;

  public UserData(String id, String name, String mailAddress) {
    this.id = id;
    this.name = name;
    this.mailAddress = mailAddress;
  }

  // Userのプロパティが増えても、
  // 利用元で変更の必要が生じないようにするためのコンストラクタ
  public UserData(User source) {
    this.id = source.id.getValue();
    this.name = source.name.getValue();
    this.mailAddress = source.mailAddress.getValue();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getMailAddress() {
    return mailAddress;
  }
}
