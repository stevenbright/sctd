package sctd.logic.command;

import sctd.model.GameUnit;

/**
 * @author Alexander Shabanov
 */
public interface CommandProcessor {

  void move(GameUnit unit, int targetX, int targetY);

  void patrol(GameUnit unit, int targetX, int targetY);

  void standStill(GameUnit unit);
}
