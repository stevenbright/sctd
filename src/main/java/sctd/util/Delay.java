package sctd.util;

/**
 * Utility for introducing delays.
 *
 * @author Alexander Shabanov
 */
public final class Delay {
  private Delay() {}

  public static void sleep(long delayMillis) {
    try {
      Thread.sleep(delayMillis);
    } catch (Exception ignored) {
      Thread.interrupted();
    }
  }
}
