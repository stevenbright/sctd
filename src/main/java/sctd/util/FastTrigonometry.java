package sctd.util;

/**
 * Fast trigonometric functions, operating on Circle, divided by 256 parts.
 *
 * @author Alexander Shabanov
 */
public final class FastTrigonometry {

  /**
   * An equivalent of <code>PI / 2</code>.
   */
  public static final int QUARTER_N = 64;

  /**
   * An equivalent of <code>PI</code>.
   */
  public static final int HALF_N = 2 * QUARTER_N;

  /**
   * An equivalent of <code>3 * PI / 2</code>.
   */
  public static final int THREE_QUARTERS_N = HALF_N + QUARTER_N;

  /**
   * Count of sectors in circle, minimal angle is 2*PI/N
   * An equivalent of <code>2 * PI</code>.
   */
  public static final int N = THREE_QUARTERS_N + QUARTER_N;

  // Inter

  private static final double[] SIN_TABLE = new double[QUARTER_N];
  private static final double[] COS_TABLE = new double[QUARTER_N];

  // found by experimentation, this value is heavily dependent on N
  private static final double ATAN_TABLE_MULTIPLIER = 50.0;
  private static final int[] ATAN_TABLE;
  private static final double MAX_ATAN_VALUE;

  static {
    // cos and sin table
    // NOTE: for space efficiency this can iterate only to QUARTER_N, but sin and cos code will be more complicated
    for (int i = 0; i < QUARTER_N; ++i) {
      final double angle = toRadians(i);
      SIN_TABLE[i] = Math.sin(angle);
      COS_TABLE[i] = Math.cos(angle);
    }

    // atan table
    {
      MAX_ATAN_VALUE = Math.tan(toRadians(QUARTER_N - 1));
      final int atanTableSize = 2 + (int) (MAX_ATAN_VALUE * ATAN_TABLE_MULTIPLIER);
      ATAN_TABLE = new int[atanTableSize];

      double nextAngle = 0.0;
      double midAngle = 0.0;
      int nextAngleIndex = 0;
      for (int i = 0; i < atanTableSize; ++i) {
        final double actualAngle = Math.atan((i + 1) / ATAN_TABLE_MULTIPLIER);
        if (actualAngle >= nextAngle) {
          final double curAngle = nextAngle;
          ++nextAngleIndex;
          nextAngle = toRadians(nextAngleIndex);
          midAngle = (curAngle + nextAngle) / 2.0;
        }

        final int atanAngleIndex = nextAngleIndex - (actualAngle < midAngle ? 1 : 0);
        ATAN_TABLE[i] = atanAngleIndex;
      }
    }
  }

  public static double toRadians(int angleIndex) {
    return (2.0 * angleIndex * Math.PI) / N;
  }

  public static double sin(int angleIndex) {
    if (angleIndex <= QUARTER_N) {
      return angleIndex == QUARTER_N ? 1.0 : SIN_TABLE[angleIndex];
    } else if (angleIndex <= HALF_N) {
      return angleIndex == HALF_N ? 0.0 : SIN_TABLE[HALF_N - angleIndex];
    } else if (angleIndex <= THREE_QUARTERS_N) {
      return angleIndex == THREE_QUARTERS_N ? -1.0 : -SIN_TABLE[angleIndex - HALF_N];
    }
    return -SIN_TABLE[N - angleIndex];
  }

  public static double cos(int angleIndex) {
    if (angleIndex <= QUARTER_N) {
      return angleIndex == QUARTER_N ? 0.0 : COS_TABLE[angleIndex];
    } else if (angleIndex <= HALF_N) {
      return angleIndex == HALF_N ? -1.0 : -COS_TABLE[HALF_N - angleIndex];
    } else if (angleIndex <= THREE_QUARTERS_N) {
      return angleIndex == THREE_QUARTERS_N ? 0.0 : -COS_TABLE[angleIndex - HALF_N];
    }
    return COS_TABLE[N - angleIndex];
  }

  /**
   * Return angle index which is equivalent to arctangent of the given value.
   *
   * @param value Positive value or zero
   * @return A value between zero and QUARTER_N (or PI/2)
   */
  public static int atan(double value) {
    // value should be positive or zero
    assert value >= 0.0;
    if (value > MAX_ATAN_VALUE) {
      return QUARTER_N;
    }

    return ATAN_TABLE[(int) (value * ATAN_TABLE_MULTIPLIER)];
  }

  public static int atan2(double y, double x) {

    if (y > 0.0) {
      if (x == 0.0) {
        return QUARTER_N;
      }

      if (x < 0.0) {
        x = -x;
        final double v = (Math.sqrt(y * y + x * x) - x) / y;
        return HALF_N - 2 * atan(v);
      }

      final double v = (Math.sqrt(y * y + x * x) - x) / y;
      return 2 * atan(v);
    } else if (y < 0.0) {
      if (x == 0.0) {
        return THREE_QUARTERS_N;
      }

      if (x < 0.0) {
        x = -x;
        y = -y;
        final double v = (Math.sqrt(y * y + x * x) - x) / y;
        return HALF_N + 2 * atan(v);
      }

      y = -y;
      final double v = (Math.sqrt(y * y + x * x) - x) / y;
      return N - 2 * atan(v);
    } else if (x > 0.0) {
      return 0;
    } else if (x < 0.0) {
      return HALF_N;
    } else {
      throw new IllegalArgumentException("Both x and y can't be zero");
    }
  }
}
