package sctd.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexander Shabanov
 */
public final class FastTrigonometryTest {

  @Test
  public void shouldMatchQuarters() {
    matchSinAndCos(0, 0.0);

    matchSinAndCos(FastTrigonometry.HALF_N / 8, Math.PI / 8);
    matchSinAndCos(FastTrigonometry.HALF_N / 4, Math.PI / 4);
    matchSinAndCos(3 * FastTrigonometry.HALF_N / 8, 3 * Math.PI / 8);
    matchSinAndCos(FastTrigonometry.QUARTER_N, Math.PI / 2);

    matchSinAndCos(7 * FastTrigonometry.HALF_N / 8, 7 * Math.PI / 8);
    matchSinAndCos(3 * FastTrigonometry.HALF_N / 4, 3 * Math.PI / 4);
    matchSinAndCos(5 * FastTrigonometry.HALF_N / 8, 5 * Math.PI / 8);
    matchSinAndCos(FastTrigonometry.HALF_N, Math.PI);

    matchSinAndCos(11 * FastTrigonometry.HALF_N / 8, 11 * Math.PI / 8);
    matchSinAndCos(5 * FastTrigonometry.HALF_N / 4, 5 * Math.PI / 4);
    matchSinAndCos(9 * FastTrigonometry.HALF_N / 8, 9 * Math.PI / 8);
    matchSinAndCos(FastTrigonometry.THREE_QUARTERS_N, 3 * Math.PI / 2);

    matchSinAndCos(15 * FastTrigonometry.HALF_N / 8, 15 * Math.PI / 8);
    matchSinAndCos(7 * FastTrigonometry.HALF_N / 4, 7 * Math.PI / 4);
    matchSinAndCos(13 * FastTrigonometry.HALF_N / 8, 13 * Math.PI / 8);
  }

  @Test
  public void shouldCalculateAtan() {
    final double epsilon = 0.03;
    for (double i = 0.0; i < 100.0; i = i + 0.001) {
      final double actualAtan = Math.atan(i);
      final double fastMathAtan = FastTrigonometry.toRadians(FastTrigonometry.atan(i));
      assertEquals("Atan doesn't match for i=" + i, actualAtan, fastMathAtan, epsilon);
    }
  }

  @Test
  public void shouldCalculateAtan2() {
    for (int i = 0; i < FastTrigonometry.N; ++i) {
      final double angle = FastTrigonometry.toRadians(i);
      final double r = 10;
      final double x = r * Math.cos(angle);
      final double y = r * Math.sin(angle);
      final double signedAtan2Value = Math.atan2(y, x);
      final double expectedAtan2Value = signedAtan2Value < 0 ? (2 * Math.PI + signedAtan2Value) : signedAtan2Value;
      assertEquals(angle, expectedAtan2Value, 0.0001);
      if (i <= FastTrigonometry.QUARTER_N) {
        assertEquals("Mismatch angle for i=" + i, angle, FastTrigonometry.toRadians(FastTrigonometry.atan2(y, x)), 0.05);
      }
    }
  }

  //
  // Private
  //

  private static void matchSinAndCos(int angleIndex, double angleRadians) {
    final double epsilon = 0.0001;
    assertEquals("Mismatch for sn " + angleIndex, Math.sin(angleRadians), FastTrigonometry.sin(angleIndex), epsilon);
    assertEquals("Mismatch for cs " + angleIndex, Math.cos(angleRadians), FastTrigonometry.cos(angleIndex), epsilon);
  }
}
