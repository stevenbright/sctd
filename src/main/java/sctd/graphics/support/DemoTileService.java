package sctd.graphics.support;

import sctd.graphics.TileService;
import sctd.model.GameField;
import sctd.util.ScreenParameters;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author Alexander Shabanov
 */
public final class DemoTileService implements TileService {
  private static final Color DARK_GREEN = new Color(10, 95, 10);


  @Override
  public void drawGameField(Graphics2D g2d, GameField field, int viewportX, int viewportY) {
    // find bounding tile indexes to draw
    final int leftOffset = viewportX % field.getTileWidth();
    final int topOffset = viewportY % field.getTileHeight();
    final int leftIndex = viewportX / field.getTileWidth();
    final int topIndex = viewportY / field.getTileHeight();
    final int rightIndex = leftIndex + ScreenParameters.getWidth() / field.getTileWidth() + 1;
    final int bottomIndex = topIndex + ScreenParameters.getHeight() / field.getTileHeight() + 1;

    int y = -topOffset;
    for (int j = topIndex; j < bottomIndex; ++j) {
      int x = -leftOffset;
      for (int i = leftIndex; i < rightIndex; ++i) {
        final GameField.Tile tile = field.getTile(i, j);
        final String debugText = tile == GameField.Tile.EMPTY ?
            ((i % 5 == 0) && (j % 5 == 0) ? "" + i + "x" + j : null) :
            null;

        drawTile(g2d, field, tile, x, y, debugText);
        x += field.getTileWidth();
      }

      y += field.getTileHeight();
    }
  }

  //
  // Private
  //

  private static void drawTile(Graphics2D g2d, GameField field, GameField.Tile tile, int x, int y, String debugText) {
    switch (tile) {
      case DARK_GRAY:
        g2d.setPaint(Color.DARK_GRAY);
        g2d.fill(new Rectangle2D.Double(x, y, field.getTileWidth(), field.getTileHeight()));
        break;

      default:
        g2d.setPaint(DARK_GREEN);
        g2d.drawLine(x, y, x + field.getTileWidth() - 1, y);
        g2d.drawLine(x, y, x, y + field.getTileHeight() - 1);
        if (debugText != null) {
          final Rectangle2D textBounds = g2d.getFontMetrics().getStringBounds(debugText, g2d);
          int textX = (field.getTileWidth() - (int) textBounds.getWidth()) / 2;
          //int textY = (field.getTileHeight() + (int) textBounds.getHeight()) / 2;
          int textY = field.getTileHeight() / 2;
          g2d.drawString(debugText, x + textX, y + textY);
        }
    }
  }
}
