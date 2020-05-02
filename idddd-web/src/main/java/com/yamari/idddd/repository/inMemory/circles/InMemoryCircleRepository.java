package com.yamari.idddd.repository.inMemory.circles;

import com.yamari.idddd.domain.models.circles.Circle;
import com.yamari.idddd.domain.models.circles.CircleId;
import com.yamari.idddd.domain.models.circles.CircleName;
import com.yamari.idddd.domain.models.circles.ICircleRepository;
import com.yamari.idddd.domain.models.users.MailAddress;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;
import com.yamari.idddd.domain.models.users.UserName;
import com.yamari.idddd.repository.RepositoryException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCircleRepository implements ICircleRepository {

  public Map<CircleId, Circle> circleStore = new HashMap<>();

  @Override
  public void save(Circle circle) throws RepositoryException {
    // 保存時はディープコピー
    circleStore.put(circle.getId(), cloneCircle(circle));
  }

  @Override
  public Circle find(CircleId id) throws RepositoryException {
    return circleStore.values().stream()
        .filter((i -> id.getValue().equals(i.getId().getValue())))
        .findFirst()
        .map(this::cloneCircle)
        .orElse(null);
  }

  @Override
  public Circle find(CircleName name) throws RepositoryException {
    return circleStore.values().stream()
        .filter((i -> name.getValue().equals(i.getName().getValue())))
        .findFirst()
        .map(this::cloneCircle)
        .orElse(null);
  }

  private Circle cloneCircle(Circle circle) {
    User owner = circle.getOwner();
    User clonedOwner =
        new User(
            new UserId(owner.getId().getValue()),
            new UserName(owner.getName().getValue()),
            new MailAddress(owner.getMailAddress().getValue()));
    List<User> clonedMembers = new ArrayList<>(circle.getMembers());

    return new Circle(
        new CircleId(circle.getId().getValue()),
        new CircleName(circle.getName().getValue()),
        clonedOwner,
        clonedMembers);
  }
}
