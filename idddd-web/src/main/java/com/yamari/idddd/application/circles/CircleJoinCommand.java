package com.yamari.idddd.application.circles;

public class CircleJoinCommand {

  private final String userId;
  private final String circleId;

  public CircleJoinCommand(String userId, String circleId) {
    this.userId = userId;
    this.circleId = circleId;
  }

  public String getUserId() {
    return userId;
  }

  public String getCircleId() {
    return circleId;
  }
}
