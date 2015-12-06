package sctd.model;

import sctd.logic.command.queue.GameCommandQueue;

import java.awt.geom.Rectangle2D;

import static sctd.util.DbgFastTrigonometry.*;

/**
 * @author Alexander Shabanov
 */
public final class GameUnit {
  private static final int DEFAULT_STOP_TIMER_VALUE = 2;

  private static final int MAX_IDLE_TIMER_VALUE = 256;

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
  
  // orientation
  private int angle;
  private int angularVelocity;

  // nicer movement (stop when command completed)
  private int stopTimer;
  private boolean justStopped;

  // idle timer
  private int idleTimer;

  // selection
  private boolean selected;

  // game commands
  private final GameCommandQueue commands = new GameCommandQueue();

  //
  // Ctor
  //

  public GameUnit() {
    angularVelocity = HALF_DEG / 16;
  }

  //
  // Getters
  //

  public int getId() {
    return id;
  }

  public int getLife() {
    return life;
  }

  public int getShields() {
    return shields;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }

  public double getSize() {
    return size;
  }

  public int getSpriteId() {
    return spriteId;
  }

  public int getStopTimer() {
    return stopTimer;
  }

  public boolean isJustStopped() {
    return justStopped;
  }

  public int getIdleTimer() {
    return idleTimer;
  }

  public boolean isSelected() {
    return selected;
  }

  public GameCommandQueue getCommands() {
    return commands;
  }

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

  public void setAngle(int angle) {
    if (angle < 0 || angle >= DEG) {
      throw new IllegalArgumentException("angle " + angle + " is out of bounds");
    }
    this.angle = angle;
  }

  public void setAngularVelocity(int angularVelocity) {
    this.angularVelocity = angularVelocity;
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

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public boolean containsPoint(double px, double py) {
    return (px > x) && (px < (x + size)) && (py > y) && (py < (y + size));
  }

  public boolean intersects(double x, double y, double w, double h) {
    final double left = getX();
    final double top = getY();
    final double width = getSize();
    final double height = getSize();
    return (x + w > left &&
        y + h > top &&
        x < left + width &&
        y < top + height);
  }

  public boolean moveTo(double targetX, double targetY) {
    this.idleTimer = 0;

    // unit already arrived?
    if (this.isJustStopped()) {
      if (this.getStopTimer() > 0) {
        this.setStopTimer(this.getStopTimer() - 1);
      } else {
        // stop timer is out, complete current movement command
        this.getCommands().completeCommand();
        this.setJustStopped(false); // continue to the next command
      }

      return true; // arrived
    }


    final double dx = targetX - this.getX();
    final double dy = targetY - this.getY();
    final double dist = Math.sqrt(dy * dy + dx * dx);

    // calculate speed at the current point
    this.accellerate(dist);

    // unit has arrived? - if yes, stop it
    if (dist <= this.currentVelocity) {
      this.setCurrentVelocity(0);
      this.setCoordinates(targetX, targetY, this.getZ());
      this.setStopTimer(DEFAULT_STOP_TIMER_VALUE);
      this.setJustStopped(true);
      return true; // arrived
    }

    // is unit faces the right direction? - if no, rotate it
    final int angle = atan2(dy, dx);
    if (!this.rotateToAngle(angle)) {
      return false;
    }

    // move unit to the target coordinates
    final double newX = x + currentVelocity * cos(angle);
    final double newY = y + currentVelocity * sin(angle);
    this.setCoordinates(newX, newY, z);

    return false; // unit is on its way
  }

  public int getAngleAsDiscrete() {
    return getAngle();
  }

  /**
   * Returns angle converted to radians.
   *
   * @return Angle in radians
   */
  public double getAngleAsRadians() {
    return toRadians(getAngle());
  }

  public void incIdleTimer() {
    idleTimer = (idleTimer + 1) % MAX_IDLE_TIMER_VALUE;
  }

  //
  // Private
  //

  private int getAngle() {
    return angle;
  }

  // VisibleForTesting
  public boolean rotateToAngle(int destinationAngle) {
    if (angularVelocity == 0) {
      // rotate immediately
      setAngle(destinationAngle);
      return true;
    }

    // TODO: simplify
    // calculate rotation angle
    int rotationAngle = destinationAngle - angle;
    if (rotationAngle > HALF_DEG) {
      // fixup needed - rotate clockwise
      rotationAngle = rotationAngle - DEG;
    } else if (rotationAngle < -HALF_DEG) {
      // fixup needed - rotate counterclockwise
      rotationAngle = DEG + rotationAngle;
    }

    // set angle and determine if angle is too small
    if (rotationAngle >= 0) {
      if (rotationAngle < angularVelocity) {
        setAngle(destinationAngle);
        return true;
      }
      setAngle((angle + angularVelocity) % DEG);
    } else {
      if ((-rotationAngle) < angularVelocity) {
        setAngle(destinationAngle);
        return true;
      }
      setAngle((DEG + angle - angularVelocity) % DEG);
    }

    return false;
  }
}
