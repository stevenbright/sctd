package sctd.logic.dispatcher;

import sctd.logic.command.CommandProcessor;
import sctd.model.GameUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Shabanov
 */
public final class GameDispatcher implements CommandProcessor {
  private static final int DEFAULT_STOP_TIMER_VALUE = 2;

  private final List<GameUnit> units = new ArrayList<>(100);
  private int idCounter = 1;

  public List<GameUnit> getUnits() {
    return units;
  }

  public GameUnit addUnit(int x, int y, int size, int spriteId) {
    final GameUnit unit = new GameUnit();
    unit.setId(idCounter++);
    unit.setCoordinates(x, y, 0);
    unit.setSize(size);
    unit.setSpriteId(spriteId);
    units.add(unit);
    return unit;
  }

  public void tick() {
    final int size = units.size();
    //noinspection ForLoopReplaceableByForEach
    for (int i = 0; i < size; ++i) {
      final GameUnit unit = units.get(i);
      unit.getCommands().execute(unit, this);
    }
  }

  @Override
  public void move(GameUnit unit, int targetX, int targetY) {
    moveTo(unit, targetX, targetY);
  }

  @Override
  public void patrol(GameUnit unit, int targetX, int targetY) {
    moveTo(unit, targetX, targetY);
  }

  @Override
  public void standStill(GameUnit unit) {
    // TODO: decelleration?
  }

  //
  // Private
  //

  private boolean moveTo(GameUnit unit, int targetX, int targetY) {
    // unit already arrived?
    if (unit.isJustStopped()) {
      if (unit.getStopTimer() > 0) {
        unit.setStopTimer(unit.getStopTimer() - 1);
      } else {
        // stop timer is out, complete current movement command
        unit.getCommands().completeCommand();
        unit.setJustStopped(false); // continue to the next command
      }

      return true; // arrived
    }


    final double dx = targetX - unit.getX();
    final double dy = targetY - unit.getY();
    final double dist = Math.sqrt(dy * dy + dx * dx);

    // calculate speed at the current point
    unit.accellerate(dist);

    // unit has arrived? - if yes, stop it
    if (dist <= unit.getCurrentVelocity()) {
      unit.setCurrentVelocity(0);
      unit.setCoordinates(targetX, targetY, unit.getZ());
      unit.setStopTimer(DEFAULT_STOP_TIMER_VALUE);
      unit.setJustStopped(true);
      return true; // arrived
    }

    // is unit faces the right direction? - if no, rotate it
    final double angle = Math.atan2(dy, dx);
    if (!unit.rotateToAngle(angle)) {
      return false;
    }

    // move unit to the target coordinates
    final double newX = unit.getX() + unit.getCurrentVelocity() * Math.cos(angle);
    final double newY = unit.getY() + unit.getCurrentVelocity() * Math.sin(angle);
    unit.setCoordinates(newX, newY, unit.getZ());

    return false; // unit is on its way
  }
}
