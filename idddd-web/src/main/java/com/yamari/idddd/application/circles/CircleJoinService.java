package com.yamari.idddd.application.circles;

import com.yamari.idddd.application.users.UserNotFoundException;
import com.yamari.idddd.domain.models.circles.Circle;
import com.yamari.idddd.domain.models.circles.CircleId;
import com.yamari.idddd.domain.models.circles.ICircleFactory;
import com.yamari.idddd.domain.models.circles.ICircleRepository;
import com.yamari.idddd.domain.models.users.IUserRepository;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;
import com.yamari.idddd.domain.services.CircleService;
import org.springframework.transaction.annotation.Transactional;

public class CircleJoinService {

  private final ICircleRepository circleRepository;
  private final IUserRepository userRepository;

  public CircleJoinService(
      ICircleRepository circleRepository,
      ICircleFactory circleFactory,
      CircleService circleService,
      IUserRepository userRepository) {
    this.circleRepository = circleRepository;
    this.userRepository = userRepository;
  }

  @Transactional
  public void handle(CircleJoinCommand command)
      throws UserNotFoundException, CircleNotFoundException, CircleFullException {

    UserId memberId = new UserId(command.getUserId());
    User member = userRepository.find(memberId);
    if (member == null) {
      throw new UserNotFoundException(memberId);
    }

    CircleId circleId = new CircleId(command.getCircleId());
    Circle circle = circleRepository.find(circleId);
    if (circle == null) {
      throw new CircleNotFoundException(circleId);
    }

    // FIXME:サークルのオーナーを含めて30人以内か確認（ロジック漏れ出し）
    if (circle.members.size() >= 29) {
      throw new CircleFullException(circleId);
    }

    circle.members.add(member);
    circleRepository.save(circle);
  }
}
