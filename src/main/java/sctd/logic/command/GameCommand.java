package sctd.logic.command;

import sctd.model.GameUnit;

/**
 * Abstract game command
 *
 * @author Alexander Shabanov
 */
public abstract class GameCommand {

  public abstract void execute(GameUnit unit, CommandProcessor dispatcher);
}
