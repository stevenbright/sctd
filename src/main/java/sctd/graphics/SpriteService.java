package sctd.graphics;

import sctd.model.GameUnit;

import java.awt.*;

/**
 * @author Alexander Shabanov
 */
public interface SpriteService {

  void drawUnit(GameUnit unit, Graphics2D g2d);
}
