package sctd.graphics;

import sctd.model.GameField;

import java.awt.*;

/**
 * @author Alexander Shabanov
 */
public interface TileService {
  void drawGameField(Graphics2D g2d, GameField gameField);
}
