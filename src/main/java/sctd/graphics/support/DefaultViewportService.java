package sctd.graphics.support;

import sctd.graphics.Viewport;
import sctd.model.GameField;
import sctd.util.NumericUtil;
import sctd.util.ScreenParameters;

/**
 * @author Alexander Shabanov
 */
public final class DefaultViewportService implements Viewport {
  private static final int SCREEN_SCROLL_SPEED = 8;

  private int viewportX;
  private int viewportY;
  private final int viewportWidth;
  private final int viewportHeight;

  private final int maxViewportX;
  private final int maxViewportY;

  public DefaultViewportService(GameField field, int viewportWidth, int viewportHeight) {
    this.viewportWidth = viewportWidth;
    this.viewportHeight = viewportHeight;

    maxViewportX = NumericUtil.positiveOrZero(field.getWidth() - ScreenParameters.getWidth());
    maxViewportY = NumericUtil.positiveOrZero(field.getHeight() - ScreenParameters.getHeight());
  }

  @Override
  public int getViewportX() {
    return viewportX;
  }

  @Override
  public int getViewportY() {
    return viewportY;
  }

  @Override
  public int getViewportWidth() {
    return viewportWidth;
  }

  @Override
  public int getViewportHeight() {
    return viewportHeight;
  }

  @Override
  public void scrollUp() {
    viewportY = NumericUtil.positiveOrZero(viewportY - SCREEN_SCROLL_SPEED);
  }

  @Override
  public void scrollDown() {
    viewportY = NumericUtil.lessThan(viewportY + SCREEN_SCROLL_SPEED, maxViewportY);
  }

  @Override
  public void scrollLeft() {
    viewportX = NumericUtil.positiveOrZero(viewportX - SCREEN_SCROLL_SPEED);
  }

  @Override
  public void scrollRight() {
    viewportX = NumericUtil.lessThan(viewportX + SCREEN_SCROLL_SPEED, maxViewportX);
  }
}
