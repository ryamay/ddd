package com.yamari.idddd.domain.models.circles;

import com.yamari.idddd.repository.RepositoryException;

public interface ICircleRepository {

  void save(Circle circle) throws RepositoryException;

  Circle find(CircleId id) throws RepositoryException;

  Circle find(CircleName name) throws RepositoryException;
}
