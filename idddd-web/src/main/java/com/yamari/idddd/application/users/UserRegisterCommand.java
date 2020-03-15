package com.yamari.idddd.application.users;

public class UserRegisterCommand {

  // 作成するユーザの名前・メールアドレスはコンストラクタでのみ指定可能
  public UserRegisterCommand(String name, String mailAddress) {
    this.name = name;
    this.mailAddress = mailAddress;
  }

  private String name;
  private String mailAddress;

  String getName() {
    return this.name;
  }

  String getMailAddress() {
    return this.mailAddress;
  }
}
