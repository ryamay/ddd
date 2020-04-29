package com.yamari.idddd.application.circles;

import com.yamari.idddd.domain.models.circles.CircleId;

public class CircleNotFoundException extends Exception {

  public CircleNotFoundException(CircleId circleId) {
    super("サークルが見つかりませんでした。(circleId:" + circleId.getValue() + ")");
  }
}
