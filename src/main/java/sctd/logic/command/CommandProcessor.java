package sctd.logic.command;

import sctd.model.GameUnit;

/**
 * @author Alexander Shabanov
 */
public interface CommandProcessor {

  void move(GameUnit unit, double targetX, double targetY);

  void patrol(GameUnit unit, double targetX, double targetY);

  void standStill(GameUnit unit);
}
