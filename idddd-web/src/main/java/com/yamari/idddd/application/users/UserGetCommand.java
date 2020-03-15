package com.yamari.idddd.application.users;

public class UserGetCommand {

  // 取得対象のユーザIDはコンストラクタでのみ指定可能。
  public UserGetCommand(String id) {
    this.id = id;
  }

  private String id;

  public String getId() {
    return this.id;
  }
}
