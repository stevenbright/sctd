package sctd.util;

/**
 * Represents common screen parameters of the started game.
 *
 * @author Alexander Shabanov
 */
public final class ScreenParameters {
  private ScreenParameters() {}

  private static final int WIDTH = 1024;
  private static final int HEIGHT = 768;

  public static int getWidth() {
    return WIDTH;
  }

  public static int getHeight() {
    return HEIGHT;
  }
}
