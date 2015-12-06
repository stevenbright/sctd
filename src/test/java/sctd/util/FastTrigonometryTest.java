package sctd.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexander Shabanov
 */
public final class FastTrigonometryTest {

  @Test
  public void shouldCalculateSinAndCos() {
    verifySinAndCos(0, 0.0);

    verifySinAndCos(FastTrigonometry.HALF_N / 8, Math.PI / 8);
    verifySinAndCos(FastTrigonometry.HALF_N / 4, Math.PI / 4);
    verifySinAndCos(3 * FastTrigonometry.HALF_N / 8, 3 * Math.PI / 8);
    verifySinAndCos(FastTrigonometry.QUARTER_N, Math.PI / 2);

    verifySinAndCos(7 * FastTrigonometry.HALF_N / 8, 7 * Math.PI / 8);
    verifySinAndCos(3 * FastTrigonometry.HALF_N / 4, 3 * Math.PI / 4);
    verifySinAndCos(5 * FastTrigonometry.HALF_N / 8, 5 * Math.PI / 8);
    verifySinAndCos(FastTrigonometry.HALF_N, Math.PI);

    verifySinAndCos(11 * FastTrigonometry.HALF_N / 8, 11 * Math.PI / 8);
    verifySinAndCos(5 * FastTrigonometry.HALF_N / 4, 5 * Math.PI / 4);
    verifySinAndCos(9 * FastTrigonometry.HALF_N / 8, 9 * Math.PI / 8);
    verifySinAndCos(FastTrigonometry.THREE_QUARTERS_N, 3 * Math.PI / 2);

    verifySinAndCos(15 * FastTrigonometry.HALF_N / 8, 15 * Math.PI / 8);
    verifySinAndCos(7 * FastTrigonometry.HALF_N / 4, 7 * Math.PI / 4);
    verifySinAndCos(13 * FastTrigonometry.HALF_N / 8, 13 * Math.PI / 8);
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
      assertEquals("Mismatch angle for i=" + i, angle, FastTrigonometry.toRadians(FastTrigonometry.atan2(y, x)), 0.05);
    }
  }

  //
  // Private
  //

  private static void verifySinAndCos(int angleIndex, double angleRadians) {
    final double ep = 0.0001;
    assertEquals("Mismatch for sin#" + angleIndex, Math.sin(angleRadians), FastTrigonometry.sin(angleIndex), ep);
    assertEquals("Mismatch for cos#" + angleIndex, Math.cos(angleRadians), FastTrigonometry.cos(angleIndex), ep);
  }
}
