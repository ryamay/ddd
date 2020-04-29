package com.yamari.idddd.domain.models.circles;

import com.yamari.idddd.domain.models.users.User;

public interface ICircleFactory {

  Circle create(CircleName name, User owner);
}
