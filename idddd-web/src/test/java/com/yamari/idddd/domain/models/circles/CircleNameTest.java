package com.yamari.idddd.domain.models.circles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

public class CircleNameTest {

  @Test
  public void successWithThreeCharacters() {
    String input = "123";
    CircleName targetName = new CircleName(input);

    assertThat(targetName.getValue()).as("3文字のサークル名は許容").isEqualTo(input);
  }

  @Test
  public void successWith20Characters() {
    String input = "12345678901234567890";
    CircleName targetName = new CircleName(input);

    assertThat(targetName.getValue()).as("20文字のサークル名は許容").isEqualTo(input);
  }

  @Test
  public void failureWithNull() {
    String input = null;
    assertThatThrownBy(() -> new CircleName(input))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(("サークル名は必ず入力してください。"));
  }

  @Test
  public void failureWithTwoCharacters() {
    String input = "12";
    assertThatThrownBy(() -> new CircleName(input))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("サークル名は3文字以上です。");
  }

  @Test
  public void failureWith21Characters() {
    String input = "123456789012345678901";
    assertThatThrownBy(() -> new CircleName(input))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("サークル名は20文字以下です。");
  }
}
