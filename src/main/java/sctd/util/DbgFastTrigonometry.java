package sctd.util;

/**
 * Safer but extremely slower alternative to {@link FastTrigonometry}.
 *
 * @author Alexander Shabanov
 */
public final class DbgFastTrigonometry extends DiscreteAngle {
  private DbgFastTrigonometry() {} // hidden

  public static double sin(int value) {
    return Math.sin(toRadians(value));
  }

  public static double cos(int value) {
    return Math.cos(toRadians(value));
  }

  public static int atan(double value) {
    return fromRadians(Math.atan(value));
  }

  public static int atan2(double y, double x) {
    return fromRadians(Math.atan2(y, x));
  }
}
