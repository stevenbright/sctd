package sctd.logic.dispatcher;

import sctd.logic.command.CommandProcessor;
import sctd.logic.command.MoveCommand;
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

  private GameUnit selectedUnit;

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

  public GameUnit select(double x, double y) {
    final int size = units.size();
    //noinspection ForLoopReplaceableByForEach
    for (int i = 0; i < size; ++i) {
      final GameUnit unit = units.get(i);
      final double ux = unit.getX();
      final double uy = unit.getY();
      final double us = unit.getSize();

      if ((x > ux) && (x < (ux + us)) && (y > uy) && (y < (uy + us))) {
        // remove previous selection
        if (selectedUnit != null) {
          selectedUnit.setSelected(false);
        }
        //System.out.println("Unit #" + unit.getId() + " has been selected");
        // mark this unit as selected
        unit.setSelected(true);
        selectedUnit = unit;
        return unit;
      }
    }

    return null;
  }

  public GameUnit moveSelectedTo(double x, double y) {
    final GameUnit unit = this.selectedUnit;
    if (unit != null) {
      final double halfSize = unit.getSize() / 2.0;
      unit.getCommands().newCommand(new MoveCommand(x - halfSize, y - halfSize));
    }
    return unit;
  }

  @Override
  public void move(GameUnit unit, double targetX, double targetY) {
    moveTo(unit, targetX, targetY);
  }

  @Override
  public void patrol(GameUnit unit, double targetX, double targetY) {
    moveTo(unit, targetX, targetY);
  }

  @Override
  public void standStill(GameUnit unit) {
    // TODO: idle move
  }

  //
  // Private
  //

  private boolean moveTo(GameUnit unit, double targetX, double targetY) {
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
