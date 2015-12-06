package sctd.util;

/**
 * Fast trigonometric functions, operating on Circle, divided by <code>DEG</code> parts.
 * The integer value taken by functions like {@link #sin(int)} or {@link #cos(int)} is an angle index, counting from
 * zero, inclusive to {@link #DEG}, exclusive.
 * <p>
 * To some extent it is equivalent of the standard 360-degree scale, but here
 * we use {@link #DEG}-degree scale instead.
 * </p>
 *
 * @author Alexander Shabanov
 */
public final class FastTrigonometry extends DiscreteAngle {
  private FastTrigonometry() {} // hidden

  private static final double[] SIN_TABLE = new double[QUARTER_DEG];
  private static final double[] COS_TABLE = new double[QUARTER_DEG];

  // found by experimentation, this value is heavily dependent on DEG
  private static final double ATAN_TABLE_MULTIPLIER = 50.0;
  private static final int[] ATAN_TABLE;
  private static final double MAX_ATAN_VALUE;

  static {
    // cos and sin table
    // NOTE: for space efficiency this can iterate only to QUARTER_DEG, but sin and cos code will be more complicated
    for (int i = 0; i < QUARTER_DEG; ++i) {
      final double angle = toRadians(i);
      SIN_TABLE[i] = Math.sin(angle);
      COS_TABLE[i] = Math.cos(angle);
    }

    // atan table
    {
      MAX_ATAN_VALUE = Math.tan(toRadians(QUARTER_DEG - 1));
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

  public static double sin(int angleIndex) {
    if (angleIndex <= QUARTER_DEG) {
      return angleIndex == QUARTER_DEG ? 1.0 : SIN_TABLE[angleIndex];
    } else if (angleIndex <= HALF_DEG) {
      return angleIndex == HALF_DEG ? 0.0 : SIN_TABLE[HALF_DEG - angleIndex];
    } else if (angleIndex <= THREE_QUARTERS_DEG) {
      return angleIndex == THREE_QUARTERS_DEG ? -1.0 : -SIN_TABLE[angleIndex - HALF_DEG];
    }
    return -SIN_TABLE[DEG - angleIndex];
  }

  public static double cos(int angleIndex) {
    if (angleIndex <= QUARTER_DEG) {
      return angleIndex == QUARTER_DEG ? 0.0 : COS_TABLE[angleIndex];
    } else if (angleIndex <= HALF_DEG) {
      return angleIndex == HALF_DEG ? -1.0 : -COS_TABLE[HALF_DEG - angleIndex];
    } else if (angleIndex <= THREE_QUARTERS_DEG) {
      return angleIndex == THREE_QUARTERS_DEG ? 0.0 : -COS_TABLE[angleIndex - HALF_DEG];
    }
    return COS_TABLE[DEG - angleIndex];
  }

  /**
   * Return angle index which is equivalent to arctangent of the given value.
   *
   * @param value Positive value or zero
   * @return A value between zero and QUARTER_DEG (or PI/2)
   */
  public static int atan(double value) {
    // value should be positive or zero
    assert value >= 0.0;
    if (value > MAX_ATAN_VALUE) {
      return QUARTER_DEG;
    }

    return ATAN_TABLE[(int) (value * ATAN_TABLE_MULTIPLIER)];
  }

  public static int atan2(double y, double x) {

    if (y > 0.0) {
      if (x == 0.0) {
        return QUARTER_DEG;
      }

      if (x < 0.0) {
        x = -x;
        final double v = (Math.sqrt(y * y + x * x) - x) / y;
        return HALF_DEG - 2 * atan(v);
      }

      final double v = (Math.sqrt(y * y + x * x) - x) / y;
      return 2 * atan(v);
    } else if (y < 0.0) {
      if (x == 0.0) {
        return THREE_QUARTERS_DEG;
      }

      if (x < 0.0) {
        x = -x;
        y = -y;
        final double v = (Math.sqrt(y * y + x * x) - x) / y;
        return HALF_DEG + 2 * atan(v);
      }

      y = -y;
      final double v = (Math.sqrt(y * y + x * x) - x) / y;
      return DEG - 2 * atan(v);
    } else if (x > 0.0) {
      return 0;
    } else if (x < 0.0) {
      return HALF_DEG;
    } else {
      throw new IllegalArgumentException("Both x and y can't be zero");
    }
  }
}
