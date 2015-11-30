package sctd.demo;

import sctd.main.GameLoopCallback;
import sctd.main.MainGameWindow;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author Alexander Shabanov
 */
public final class MainGameWindowSnowDemo implements GameLoopCallback {
  private int x = 50;
  private int y = 50;
  private int w = 10;
  private int delta = 1;

  public static void main(String[] args) {
    final MainGameWindow mainGameWindow = new MainGameWindow(new MainGameWindowSnowDemo());
    mainGameWindow.startLoop();
  }

  @Override
  public void draw(Graphics2D g2d) {
    // ensure loop is small enough
    g2d.setPaint(Color.YELLOW);
    final double halfWidth = w / 2;
    g2d.fill(new Rectangle2D.Double(x - halfWidth, y - halfWidth, w, w));
    x = x + delta;
    if (x > 700) {
      delta = -1;
    } else  if (x < 50) {
      delta = 1;
    }
  }
}
