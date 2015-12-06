package sctd.util;

/**
 * @author Alexander Shabanov
 */
public final class NumericUtil {
  private NumericUtil() {}

  /**
   * Returns the same value unless it is negative. Returns zero if it is.
   *
   * @param val Value
   * @return Positive or zero integer
   */
  public static int positiveOrZero(int val) {
    return val < 0 ? 0 : val;
  }

  public static int lessThan(int val, int maximumValue) {
    return val < maximumValue ? val : maximumValue;
  }
}
