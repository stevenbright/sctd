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

  private List<GameUnit> selectedUnits = new ArrayList<>(100);

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

  public GameUnit select(double x, double y, boolean allowMultiSelection) {
    final int size = units.size();

    GameUnit result = null;

    //noinspection ForLoopReplaceableByForEach
    for (int i = 0; i < size; ++i) {
      final GameUnit unit = units.get(i);
      if (unit.containsPoint(x, y)) {
        //unit.setSelected(true);
        result = unit;
        break;
      }
    }

    // remove previous selection
    if (allowMultiSelection) {
      for (final GameUnit unit : selectedUnits) {
        if (unit == result) {
          result = null; // disallow duplicate unit in selection list
          break;
        }
      }
    } else {
      // clear selection of previous units
      for (final GameUnit unit : selectedUnits) {
        unit.setSelected(false);
      }

      selectedUnits.clear();
    }

    // finally mark unit as selected
    if (result != null) {
      result.setSelected(true);
      selectedUnits.add(result);
    }

    return result;
  }

  public List<GameUnit> moveSelectedTo(double x, double y) {
    for (final GameUnit unit : selectedUnits) {
      final double halfSize = unit.getSize() / 2.0;
      unit.getCommands().newCommand(new MoveCommand(x - halfSize, y - halfSize));
    }

    return selectedUnits;
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
