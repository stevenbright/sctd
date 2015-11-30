package sctd.logic.command;

/**
 * @author Alexander Shabanov
 */
public abstract class CoordinateBasedCommand extends GameCommand {
  private final int targetX;
  private final int targetY;

  public CoordinateBasedCommand(int targetX, int targetY) {
    this.targetX = targetX;
    this.targetY = targetY;
  }

  public int getTargetX() {
    return targetX;
  }

  public int getTargetY() {
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
