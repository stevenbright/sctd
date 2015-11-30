package sctd.model;

import lombok.Getter;
import lombok.ToString;
import sctd.logic.command.queue.GameCommandQueue;

/**
 * @author Alexander Shabanov
 */
@Getter
@ToString
public final class GameUnit {
  private static final double TWO_PI = Math.PI * 2.0; // 2*pi or 360 degrees

  private int id;

  // health
  private int life;
  private int shields;

  // geometry parameters
  private double x;
  private double y;
  private double z;

  // ui parameters
  private double size;
  private int spriteId;

  // movement
  private double currentVelocity = 0.0;
  private double maximumVelocity = 4.0;
  private double acceleration = 0.8;
  private double angularVelocity = Math.PI / 18; // 10 degrees

  // orientation
  private double angle;

  // nicer movement (stop when command completed)
  private int stopTimer;
  private boolean justStopped;

  // game commands
  private final GameCommandQueue commands = new GameCommandQueue();

  //
  // Mutators
  //

  public void setId(int id) {
    this.id = id;
  }

  public void setCoordinates(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public void setSize(double size) {
    this.size = size;
  }

  public void setSpriteId(int spriteId) {
    this.spriteId = spriteId;
  }

  public void accellerate(double distance) {
    if (acceleration == 0.0) {
      currentVelocity = maximumVelocity; // instant acceleration
      return;
    }

    // TODO: at the moment acceleration and deceleration change velocity in a linear way - it'd be better to use
    // TODO: (contd.)   easing (e.g. easeOutQuad)
    // TODO: (contd.)   http://stackoverflow.com/questions/5916058/jquery-easing-function-variables-comprehension
    // TODO: (contd.)   http://api.jqueryui.com/easings/
    final double decelerationDistance = (currentVelocity * currentVelocity) / (2 * acceleration);
    if (distance < decelerationDistance) {
      // decelerate when coming closer
      currentVelocity = Math.max(0.0, currentVelocity - acceleration);
      return;
    }

    // deceleration is not needed - accellerate instead
    if (currentVelocity < maximumVelocity) {
      currentVelocity = Math.max(currentVelocity + acceleration, maximumVelocity);
    }
  }

  public void setCurrentVelocity(double speed) {
    this.currentVelocity = speed;
  }

  public void setMaximumVelocity(double maximumVelocity) {
    this.maximumVelocity = maximumVelocity;
  }

  public void setAngle(double angle) {
    this.angle = angle > TWO_PI ? angle - TWO_PI : angle;
  }

  public boolean rotateToAngle(double angle) {
    if (angularVelocity == 0) {
      // rotate immediately
      setAngle(angle);
      return true;
    }

    double deltaAngle = angle - this.angle;
    if (Math.abs(deltaAngle) <= angularVelocity) {
      setAngle(angle);
      return true;
    }

    if (deltaAngle > 0.0) {
      // rotate counter clockwise
      this.setAngle(this.angle + angularVelocity);
    } else {
      // rotate clockwise
      this.setAngle(this.angle - angularVelocity);
    }
    return false;
  }

  public void setStopTimer(int stopTimer) {
    this.stopTimer = stopTimer;
  }

  public void setJustStopped(boolean justStopped) {
    this.justStopped = justStopped;
  }

  public void setAcceleration(double value) {
    assert value >= 0.0;
    this.acceleration = value;
  }
}
