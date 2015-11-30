package sctd.logic.command;

import sctd.model.GameUnit;

/**
 * @author Alexander Shabanov
 */
public final class PatrolCommand extends CoordinateBasedCommand {
  public PatrolCommand(double targetX, double targetY) {
    super(targetX, targetY);
  }

  @Override
  public void execute(GameUnit unit, CommandProcessor dispatcher) {
    dispatcher.patrol(unit, getTargetX(), getTargetY());
  }
}
