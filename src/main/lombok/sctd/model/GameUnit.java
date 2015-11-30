package sctd.model;

import lombok.Getter;
import lombok.ToString;
import sctd.logic.command.queue.GameCommandQueue;

/**
 * @author Alexander Shabanov
 */
@Getter
@ToString
public class GameUnit {
  private int id;

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
  private double currentSpeed = 0.0;
  private double maximumSpeed = 4.0;
  private double acceleration = 0.8;

  // orientation
  private double angle;

  // nicer movement (stop when command completed)
  private int stopTimer;
  private boolean justStopped;

  // game commands
  private final GameCommandQueue commands = new GameCommandQueue();

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
      currentSpeed = maximumSpeed; // instant acceleration
      return;
    }

    final double deceleration = -acceleration;
    final double decelerationDistance = Math.abs((currentSpeed * currentSpeed) / (2 * deceleration));
    if (distance < decelerationDistance) {
      // decelerate when coming closer
      currentSpeed = Math.max(0.0, currentSpeed + deceleration);
      return;
    }

    // deceleration is not needed - accellerate instead
    if (currentSpeed < maximumSpeed) {
      currentSpeed = Math.max(currentSpeed + acceleration, maximumSpeed);
    }
  }

  public void setCurrentSpeed(double speed) {
    this.currentSpeed = speed;
  }

  public void setAngle(double angle) {
    this.angle = angle;
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
