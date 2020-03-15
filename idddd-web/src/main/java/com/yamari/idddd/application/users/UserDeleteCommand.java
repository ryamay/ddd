package com.yamari.idddd.application.users;

public class UserDeleteCommand {

  // 削除対象のユーザIDはコンストラクタでのみ指定可能。
  public UserDeleteCommand(String id) {
    this.id = id;
  }

  private String id;

  public String getId() {
    return this.id;
  }
}
