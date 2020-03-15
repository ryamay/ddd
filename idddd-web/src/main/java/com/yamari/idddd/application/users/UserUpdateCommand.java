package com.yamari.idddd.application.users;

public class UserUpdateCommand {


  public UserUpdateCommand(String id, String name, String mailAddress) {
    this.id = id;
  }

  public String id;
  // setされると更新
  public String name;
  // setされると更新
  public String mailAddress;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMailAddress() {
    return mailAddress;
  }

  public void setMailAddress(String mailAddress) {
    this.mailAddress = mailAddress;
  }

}
