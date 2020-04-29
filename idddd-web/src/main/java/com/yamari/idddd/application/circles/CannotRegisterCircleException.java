package com.yamari.idddd.application.circles;

import com.yamari.idddd.domain.models.circles.Circle;

public class CannotRegisterCircleException extends Exception {

  public CannotRegisterCircleException(Circle circle, String message) {
    super(
        message
            + "(circleName:"
            + circle.name.getValue()
            + ", circleId:"
            + circle.id.getValue()
            + ")");
  }
}
