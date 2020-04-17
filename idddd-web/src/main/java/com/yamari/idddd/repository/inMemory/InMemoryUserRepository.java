package com.yamari.idddd.repository.inMemory;

import com.yamari.idddd.domain.models.users.IUserRepository;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;
import com.yamari.idddd.domain.models.users.UserName;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserRepository implements IUserRepository {

  public Map<UserId, User> store = new HashMap<>();

  @Override
  public void save(User user) {
    // 保存時はディープコピー
    store.put(user.id, clone(user));
  }

  @Override
  public void delete(User user) {
    store.remove(user.id, user);
  }

  @Override
  public User find(UserName name) {
    return store.values().stream()
            .filter((i -> name.getValue().equals(i.name.getValue())))
            .findFirst()
            .map(this::clone)
            .orElse(null);
  }

  @Override
  public User find(UserId id) {
    return store.values().stream()
            .filter((i -> id.getValue().equals(i.id.getValue())))
            .findFirst()
            .map(this::clone)
            .orElse(null);
  }

  private User clone(User user) {
    return new User(user.id, user.name, user.mailAddress);
  }
}
