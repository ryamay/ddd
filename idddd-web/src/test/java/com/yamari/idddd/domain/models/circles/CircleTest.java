package com.yamari.idddd.domain.models.circles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.yamari.idddd.domain.models.users.MailAddress;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;
import com.yamari.idddd.domain.models.users.UserName;
import java.util.ArrayList;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

public class CircleTest {

  private CircleId id = new CircleId("target");
  private CircleName name = new CircleName("targetName");
  private MailAddress address = new MailAddress("target@example.com");

  private User owner =
      new User(
          new UserId("owner"), new UserName("ownerName"), new MailAddress("owner@example.com"));
  private User member =
      new User(
          new UserId("member"), new UserName("memberName"), new MailAddress("member@example.com"));

  @Test
  public void successWithNonNullArguments() {
    ArrayList<User> members = new ArrayList<>();
    members.add(member);
    Circle target = new Circle(id, name, owner, members);

    assertThat(target.id).isEqualTo(id);
    assertThat(target.name).isEqualTo(name);
    assertThat(target.owner).isEqualTo(owner);
    assertThat(target.members).isInstanceOf(ArrayList.class);
    assertThat(target.members.get(0)).isEqualTo(member);
  }

  @Test
  public void failureWithNullId() {
    assertThatThrownBy(() -> new Circle(null, name, owner, new ArrayList<>()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("idは必ず入力してください。");
  }

  @Test
  public void failureWithNullName() {
    assertThatThrownBy(() -> new Circle(id, null, owner, new ArrayList<>()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("nameは必ず入力してください。");
  }

  @Test
  public void failureWithNullOwner() {
    assertThatThrownBy(() -> new Circle(id, name, null, new ArrayList<>()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ownerは必ず入力してください。");
  }

  @Test
  public void failureWithNullMembers() {
    assertThatThrownBy(() -> new Circle(id, name, owner, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("membersは必ず入力してください。");
  }

  @Test
  public void testEquals() {
    SoftAssertions softly = new SoftAssertions();

    Circle target = new Circle(id, name, owner, new ArrayList<>());

    softly.assertThat(target.equals(target)).as("自身と比較するとtrue").isTrue();
    softly.assertThat(target.equals(null)).as("nullと比較するとfalse").isFalse();
    softly.assertThat(target.equals(address)).as("他クラスと比較するとfalse").isFalse();

    Circle sameIdCircle =
        new Circle(
            new CircleId("target"), new CircleName("different"), owner, new ArrayList<User>());

    softly.assertThat(target.equals(sameIdCircle)).as("同じCircleIdの場合はtrue").isTrue();

    Circle differentIdCircle =
        new Circle(new CircleId("target" + "diff"), name, owner, new ArrayList<>());
    softly.assertThat(target.equals(differentIdCircle)).as("異なるUserIdの場合はfalse").isFalse();

    softly.assertAll();
  }
}
