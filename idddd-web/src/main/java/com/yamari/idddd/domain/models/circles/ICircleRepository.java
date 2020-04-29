package com.yamari.idddd.domain.models.circles;

public interface ICircleRepository {

  void save(Circle circle);

  Circle find(CircleId id);

  Circle find(CircleName name);
}
