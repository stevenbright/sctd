package sctd.graphics;

/**
 * @author Alexander Shabanov
 */
public interface Viewport {
  int getViewportX();
  int getViewportY();
  int getViewportWidth();
  int getViewportHeight();

  void scrollUp();
  void scrollDown();
  void scrollLeft();
  void scrollRight();
}
