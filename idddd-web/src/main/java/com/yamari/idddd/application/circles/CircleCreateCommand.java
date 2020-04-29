package com.yamari.idddd.application.circles;

public class CircleCreateCommand {

  private final String userId;
  private final String circleName;

  public CircleCreateCommand(String userId, String circleName) {
    this.userId = userId;
    this.circleName = circleName;
  }

  public String getUserId() {
    return userId;
  }

  public String getCircleName() {
    return circleName;
  }
}
