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
  private final List<GameUnit> units = new ArrayList<>(100);
  private int idCounter = 1;

  private GameUnit selectedUnit;

  public List<GameUnit> getUnits() {
    return units;
  }

  public GameUnit addUnit(double x, double y, double size, int spriteId) {
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
      if (unit.isPointWithin(x, y)) {
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
    unit.moveTo(targetX, targetY);
  }

  @Override
  public void patrol(GameUnit unit, double targetX, double targetY) {
    unit.moveTo(targetX, targetY);
  }

  @Override
  public void standStill(GameUnit unit) {
    unit.incIdleTimer();
  }
}
