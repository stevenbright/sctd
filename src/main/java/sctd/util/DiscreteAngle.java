package sctd.util;

/**
 * @author Alexander Shabanov
 */
public class DiscreteAngle {
  DiscreteAngle() {}

  /**
   * An equivalent of <code>PI / 2</code>.
   */
  public static final int QUARTER_DEG = 96; // 64 - 128 - 256

  /**
   * An equivalent of <code>PI</code>.
   */
  public static final int HALF_DEG = QUARTER_DEG + QUARTER_DEG;

  /**
   * An equivalent of <code>3 * PI / 2</code>.
   */
  public static final int THREE_QUARTERS_DEG = HALF_DEG + QUARTER_DEG;

  /**
   * Count of sectors in circle, minimal angle is 2*PI/DEG
   * An equivalent of <code>2 * PI</code>.
   */
  public static final int DEG = THREE_QUARTERS_DEG + QUARTER_DEG;

  private static final double HALF_ANGLE = Math.PI / DEG;

  private static final double ONE_ANGLE = 2.0 * HALF_ANGLE;

  public static double toRadians(int angleIndex) {
    return angleIndex * ONE_ANGLE;
  }

  public static int fromRadians(double value) {
    final double dp = Math.PI * 2.0;

    // normalize overflow values
    while (value >= dp) {
      value -= dp;
    }

    // normalize underflow values
    while (value < -dp) {
      value += dp;
    }

    // convert negative to positive values
    if (value < 0.0) {
      value = dp + value;
    }

    final int n = (int) ((value + HALF_ANGLE) * HALF_DEG / Math.PI);
    if (n == DEG) {
      return 0;
    }

    return n;
  }
}
