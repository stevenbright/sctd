package sctd.graphics.support;

import sctd.graphics.SpriteService;
import sctd.model.GameUnit;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * @author Alexander Shabanov
 */
public final class DemoSpriteService implements SpriteService {

  @Override
  public void drawUnit(GameUnit unit, Graphics2D g2d) {
    drawSelectionMark(unit, g2d);

    if (unit.getSpriteId() < 10) {
      drawAirplanes(unit, g2d);
    }
  }

  //
  // Private
  //

  private static void drawSelectionMark(GameUnit unit, Graphics2D g2d) {
    if (unit.isSelected()) {
      g2d.setPaint(Color.GREEN);
      final double gap = unit.getSize() / 4;
      g2d.draw(new Ellipse2D.Double(unit.getX(), unit.getY() + gap, unit.getSize(), unit.getSize() - gap));
    }
  }

  private static void drawAirplanes(GameUnit unit, Graphics2D g2d) {
    final Color bkColor;
    final Color triColor;
    switch (unit.getSpriteId()) {
      case 1:
        bkColor = Color.YELLOW;
        triColor = Color.GRAY;
        break;

      case 2:
        bkColor = Color.GREEN;
        triColor = Color.WHITE;
        break;

      case 3:
        bkColor = Color.WHITE;
        triColor = Color.RED;
        break;

      default:
        throw new UnsupportedOperationException();
    }

    // center coord
    final int size = (int) unit.getSize();
    final int cx = (int) (unit.getX() + size / 2.0);
    final int cy = (int) (unit.getY() + size / 2.0);

    g2d.rotate(unit.getAngle(), cx, cy);

    // prepare polygon array
    final int[] xp = new int[3];
    final int[] yp = new int[3];

    // inner rectangle (wings)
    {
      xp[0] = cx + size / 8; xp[1] = xp[2] = cx - size / 8;
      yp[0] = cy; yp[1] = cy - (3 * size) / 8; yp[2] = cy + (3 * size) / 8;

      g2d.setPaint(bkColor);
      g2d.fillPolygon(xp, yp, 3);
    }

    // outer rectangle (body)
    {
      xp[0] = cx + (size * 3) / 8; xp[1] = xp[2] = cx - (size * 3) / 8;
      yp[0] = cy; yp[1] = cy - size / 4; yp[2] = cy + size / 4;

      g2d.setPaint(triColor);
      g2d.fillPolygon(xp, yp, 3);
    }

    g2d.rotate(-unit.getAngle(), cx, cy); // restore angle
  }
}
