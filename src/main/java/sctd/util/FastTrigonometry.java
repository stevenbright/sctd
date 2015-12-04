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

  private static final double[] SIN_TABLE = new double[N];
  private static final double[] COS_TABLE = new double[N];

  private static final double ATAN_TABLE_MULTIPLIER = 1000.0;
  private static final int[] ATAN_TABLE;
  private static final double MAX_ATAN_VALUE;

  static {
    // cos and sin table
    // NOTE: for space efficiency this can iterate only to QUARTER_N, but sin and cos code will be more complicated
    for (int i = 0; i < N; ++i) {
      final double angle = toRadians(i);
      SIN_TABLE[i] = Math.sin(angle);
      COS_TABLE[i] = Math.cos(angle);
    }

    // atan table
    {
      MAX_ATAN_VALUE = Math.tan(toRadians(QUARTER_N - 1));
      final int atanTableSize = 2 + (int) (MAX_ATAN_VALUE * ATAN_TABLE_MULTIPLIER);
      ATAN_TABLE = new int[atanTableSize];

      int currentAngleIndex = 0;
      double nextTangentMult = 0.0;
      double medianAngleMult = 0.0;
      for (int i = 0; i < atanTableSize; ++i) {
        final double atanMult = Math.atan(i / ATAN_TABLE_MULTIPLIER);
        if (atanMult > nextTangentMult || i == 0) {
          final double prevTangentMult = nextTangentMult;
          nextTangentMult = Math.tan(toRadians(++currentAngleIndex));
          medianAngleMult = (prevTangentMult + nextTangentMult) / 2.0;
        }

        final int atanAngleIndex = (atanMult < medianAngleMult) ? currentAngleIndex - 1 : currentAngleIndex;
        ATAN_TABLE[i] = atanAngleIndex;
      }
    }
  }

  public static double toRadians(int angleIndex) {
    return (2.0 * angleIndex * Math.PI) / N;
  }

  public static double sin(int angleIndex) {
    return SIN_TABLE[angleIndex];

    // NOTE: can operate with quarter only:
//    if (angleIndex <= QUARTER_N) {
//      return angleIndex == QUARTER_N ? 1.0 : SIN_TABLE[angleIndex];
//    } else if (angleIndex <= HALF_N) {
//      return angleIndex == HALF_N ? 0.0 : SIN_TABLE[HALF_N - angleIndex];
//    } else if (angleIndex <= THREE_QUARTERS_N) {
//      return angleIndex == THREE_QUARTERS_N ? -1.0 : -SIN_TABLE[angleIndex - HALF_N];
//    }
//    return -SIN_TABLE[N - angleIndex];
  }

  public static double cos(int angleIndex) {
    return COS_TABLE[angleIndex];

    // NOTE: can operate with quarter only:
//    if (angleIndex <= QUARTER_N) {
//      return angleIndex == QUARTER_N ? 0.0 : COS_TABLE[angleIndex];
//    } else if (angleIndex <= HALF_N) {
//      return angleIndex == HALF_N ? -1.0 : -COS_TABLE[HALF_N - angleIndex];
//    } else if (angleIndex <= THREE_QUARTERS_N) {
//      return angleIndex == THREE_QUARTERS_N ? 0.0 : -COS_TABLE[angleIndex - HALF_N];
//    }
//    return COS_TABLE[N - angleIndex];
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


}
