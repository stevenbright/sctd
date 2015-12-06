package sctd.graphics.support;

import sctd.graphics.SpriteService;
import sctd.graphics.Viewport;
import sctd.model.GameUnit;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * @author Alexander Shabanov
 */
public final class DemoSpriteService implements SpriteService {
  private final Viewport viewport;

  public DemoSpriteService(Viewport viewport) {
    this.viewport = viewport;
  }

  @Override
  public void drawUnit(GameUnit unit, Graphics2D g2d) {
    if (!unit.intersects(viewport.getViewportX(), viewport.getViewportY(),
        viewport.getViewportWidth(), viewport.getViewportHeight())) {
      // skip drawing this unit
      return;
    }

    final double unitX = unit.getX() - viewport.getViewportX();
    final double unitY = unit.getY() - viewport.getViewportY();

    drawSelectionMark(unit, g2d, unitX, unitY);

    if (unit.getSpriteId() < 10) {
      drawAirplanes(unit, g2d, unitX, unitY);
    }
  }

  //
  // Private
  //

  private static void drawSelectionMark(GameUnit unit, Graphics2D g2d, double unitX, double unitY) {
    if (unit.isSelected()) {
      g2d.setPaint(Color.GREEN);
      final double gap = unit.getSize() / 4;
      g2d.draw(new Ellipse2D.Double(unitX, unitY + gap, unit.getSize(), unit.getSize() - gap));
    }
  }

  private void drawAirplanes(GameUnit unit, Graphics2D g2d, double unitX, double unitY) {
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
    final int cx = (int) (unitX + size / 2.0);
    final int cy = (int) (unitY + size / 2.0);

    final double angle = unit.getAngleAsRadians();
    g2d.rotate(angle, cx, cy);

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

    g2d.rotate(-angle, cx, cy); // restore angle
  }
}
