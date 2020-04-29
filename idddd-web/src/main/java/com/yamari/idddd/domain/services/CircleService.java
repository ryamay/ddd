package com.yamari.idddd.domain.services;

import com.yamari.idddd.domain.models.circles.Circle;
import com.yamari.idddd.domain.models.circles.ICircleRepository;

public class CircleService {

  private final ICircleRepository circleRepository;

  public CircleService(ICircleRepository circleRepository) {
    this.circleRepository = circleRepository;
  }

  /**
   * サークルの重複確認を行う。
   *
   * @param circle 重複確認をするサークル
   * @return 重複していればtrue, 重複していなければfalse
   */
  public boolean exists(Circle circle) {
    Circle found = circleRepository.find(circle.name);
    return found != null;
  }
}
