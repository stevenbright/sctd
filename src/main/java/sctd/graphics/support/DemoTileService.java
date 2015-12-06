package sctd.graphics.support;

import sctd.graphics.Viewport;
import sctd.graphics.TileService;
import sctd.model.GameField;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author Alexander Shabanov
 */
public final class DemoTileService implements TileService {
  private static final Color DARK_GREEN = new Color(10, 95, 10);

  private final Viewport viewport;

  public DemoTileService(Viewport viewport) {
    this.viewport = viewport;
  }

  @Override
  public void drawGameField(Graphics2D g2d, GameField field) {
    final int viewportX = viewport.getViewportX();
    final int viewportY = viewport.getViewportY();

    // find bounding tile indexes to draw
    final int leftOffset = viewportX % field.getTileWidth();
    final int topOffset = viewportY % field.getTileHeight();
    final int leftIndex = viewportX / field.getTileWidth();
    final int topIndex = viewportY / field.getTileHeight();

    final int rightIndex = Math.min(leftIndex + viewport.getViewportWidth() / field.getTileWidth() + 1,
        field.getHorizontalTileCount());

    final int bottomIndex = Math.min(topIndex + viewport.getViewportHeight() / field.getTileHeight() + 1,
        field.getVerticalTileCount());

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
