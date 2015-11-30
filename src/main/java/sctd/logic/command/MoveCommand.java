package sctd.logic.command;

import sctd.model.GameUnit;

/**
 * @author Alexander Shabanov
 */
public final class MoveCommand extends CoordinateBasedCommand {
  public MoveCommand(int targetX, int targetY) {
    super(targetX, targetY);
  }

  @Override
  public void execute(GameUnit unit, CommandProcessor dispatcher) {
    dispatcher.move(unit, getTargetX(), getTargetY());
  }
}
