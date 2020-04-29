package com.yamari.idddd.application.circles;

import com.yamari.idddd.domain.models.circles.CircleId;

public class CircleFullException extends Exception {

  public CircleFullException(CircleId circleId) {
    super("サークルの所属人数上限を超過してしまいます。(CircleId;" + circleId.getValue() + ")");
  }
}
