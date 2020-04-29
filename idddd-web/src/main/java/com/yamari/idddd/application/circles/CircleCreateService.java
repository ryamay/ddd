package com.yamari.idddd.application.circles;

import com.yamari.idddd.application.users.UserNotFoundException;
import com.yamari.idddd.domain.models.circles.Circle;
import com.yamari.idddd.domain.models.circles.CircleName;
import com.yamari.idddd.domain.models.circles.ICircleFactory;
import com.yamari.idddd.domain.models.circles.ICircleRepository;
import com.yamari.idddd.domain.models.users.IUserRepository;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;
import com.yamari.idddd.domain.services.CircleService;
import org.springframework.transaction.annotation.Transactional;

public class CircleCreateService {

  private final ICircleRepository circleRepository;
  private final ICircleFactory circleFactory;
  private final CircleService circleService;
  private final IUserRepository userRepository;

  public CircleCreateService(
      ICircleRepository circleRepository,
      ICircleFactory circleFactory,
      CircleService circleService,
      IUserRepository userRepository) {
    this.circleRepository = circleRepository;
    this.circleFactory = circleFactory;
    this.circleService = circleService;
    this.userRepository = userRepository;
  }

  @Transactional
  public void handle(CircleCreateCommand command)
      throws UserNotFoundException, CannotRegisterCircleException {

    UserId ownerId = new UserId(command.getUserId());
    User owner = userRepository.find(ownerId);
    if (owner == null) {
      throw new UserNotFoundException(ownerId, "サークルのオーナーとなるユーザが見つかりませんでした。");
    }

    CircleName circleName = new CircleName(command.getCircleName());
    Circle circle = circleFactory.create(circleName, owner);
    if (circleService.exists(circle)) {
      throw new CannotRegisterCircleException(circle, "サークルは既に存在しています。");
    }

    circleRepository.save(circle);
  }
}
