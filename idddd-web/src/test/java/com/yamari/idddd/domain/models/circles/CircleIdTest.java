package com.yamari.idddd.domain.models.circles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

public class CircleIdTest {

  @Test
  public void testNewSuccess() {
    String input = "newId";
    CircleId targetId = new CircleId(input);

    assertThat(targetId.getValue()).as("正常にオブジェクトが生成されることを確認").isEqualTo(input);
  }

  @Test
  public void testNewFailureNull() {
    String input = null;
    assertThatThrownBy(() -> new CircleId(input))
        .as("nullは許容しない")
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("サークルIDは必ず入力してください。");
  }

  @Test
  public void testNewFailureEmpty() {
    String input = "";
    assertThatThrownBy(() -> new CircleId(input))
        .as("空文字列は許容しない")
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("サークルIDは必ず入力してください。");
  }
}
