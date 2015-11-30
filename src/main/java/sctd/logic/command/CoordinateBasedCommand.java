package sctd.logic.command;

/**
 * @author Alexander Shabanov
 */
public abstract class CoordinateBasedCommand extends GameCommand {
  private final double targetX;
  private final double targetY;

  public CoordinateBasedCommand(double targetX, double targetY) {
    this.targetX = targetX;
    this.targetY = targetY;
  }

  public double getTargetX() {
    return targetX;
  }

  public double getTargetY() {
    return targetY;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "targetX=" + targetX +
        ", targetY=" + targetY +
        '}';
  }
}
