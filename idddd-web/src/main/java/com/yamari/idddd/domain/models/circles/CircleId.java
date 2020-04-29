package com.yamari.idddd.domain.models.circles;

import java.util.Objects;

public class CircleId {

  private String value;

  public CircleId(String value) {
    if (value == null || value.length() == 0) {
      throw new IllegalArgumentException("サークルIDは必ず入力してください。");
    }
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CircleId circleId = (CircleId) o;
    return value.equals(circleId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
