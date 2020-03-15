package com.yamari.idddd.domain.models.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.Test;

public class UserIdTest {

  @Test
  public void testNewSuccess() {
    String input = "newId";
    UserId targetId = new UserId(input);

    assertThat(targetId.getValue()).as("正常にオブジェクトが生成されることを確認").isEqualTo(input);
  }

  @Test
  public void testNewFailureNull() {
    String input = null;
    assertThatThrownBy(() -> {
      new UserId(input);
    }).as("nullは許容しない").isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ユーザIDは必ず入力してください。");
  }

  @Test
  public void testNewFailureEmpty() {
    String input = "";
    assertThatThrownBy(() -> {
      new UserId(input);
    }).as("空文字列は許容しない").isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ユーザIDは必ず入力してください。");
  }
}
