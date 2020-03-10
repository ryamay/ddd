package com.yamari.idddd.application.users;

public class UserData {
  private String id;
  private String name;

  public UserData(String id, String name) {
    this.id = id;
    this.name = name;
  }

  protected String getId() {
    return id;
  }

  protected String getName() {
    return name;
  }
}
