package sctd.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexander Shabanov
 */
public final class DbgFastTrigonometryTest {

  @Test
  public void shouldConvertToAndFromRadians() {
    final double epsilon = 0.0000001;
    for (int i = 0; i < DbgFastTrigonometry.DEG; ++i) {
      final double radians = DbgFastTrigonometry.toRadians(i);
      assertEquals("Mismatch angle for i=" + i,
          radians,
          DbgFastTrigonometry.toRadians(DbgFastTrigonometry.fromRadians(radians)),
          epsilon);
    }
  }

  @Test
  public void shouldCalculateSinAndCos() {
    verifySinAndCos(0, 0.0);

    verifySinAndCos(DbgFastTrigonometry.HALF_DEG / 8, Math.PI / 8);
    verifySinAndCos(DbgFastTrigonometry.HALF_DEG / 4, Math.PI / 4);
    verifySinAndCos(3 * DbgFastTrigonometry.HALF_DEG / 8, 3 * Math.PI / 8);
    verifySinAndCos(DbgFastTrigonometry.QUARTER_DEG, Math.PI / 2);

    verifySinAndCos(7 * DbgFastTrigonometry.HALF_DEG / 8, 7 * Math.PI / 8);
    verifySinAndCos(3 * DbgFastTrigonometry.HALF_DEG / 4, 3 * Math.PI / 4);
    verifySinAndCos(5 * DbgFastTrigonometry.HALF_DEG / 8, 5 * Math.PI / 8);
    verifySinAndCos(DbgFastTrigonometry.HALF_DEG, Math.PI);

    verifySinAndCos(11 * DbgFastTrigonometry.HALF_DEG / 8, 11 * Math.PI / 8);
    verifySinAndCos(5 * DbgFastTrigonometry.HALF_DEG / 4, 5 * Math.PI / 4);
    verifySinAndCos(9 * DbgFastTrigonometry.HALF_DEG / 8, 9 * Math.PI / 8);
    verifySinAndCos(DbgFastTrigonometry.THREE_QUARTERS_DEG, 3 * Math.PI / 2);

    verifySinAndCos(15 * DbgFastTrigonometry.HALF_DEG / 8, 15 * Math.PI / 8);
    verifySinAndCos(7 * DbgFastTrigonometry.HALF_DEG / 4, 7 * Math.PI / 4);
    verifySinAndCos(13 * DbgFastTrigonometry.HALF_DEG / 8, 13 * Math.PI / 8);
  }

  @Test
  public void shouldCalculateAtan() {
    final double epsilon = 0.03;
    for (double i = 0.0; i < 100.0; i = i + 0.001) {
      final double actualAtan = Math.atan(i);
      final double fastMathAtan = DbgFastTrigonometry.toRadians(DbgFastTrigonometry.atan(i));
      assertEquals("Atan doesn't match for i=" + i, actualAtan, fastMathAtan, epsilon);
    }
  }

  @Test
  public void shouldCalculateAtan2() {
    for (int i = 0; i < DbgFastTrigonometry.DEG; ++i) {
      final double angle = DbgFastTrigonometry.toRadians(i);
      final double r = 10;
      final double x = r * Math.cos(angle);
      final double y = r * Math.sin(angle);
      final double signedAtan2Value = Math.atan2(y, x);
      final double expectedAtan2Value = signedAtan2Value < 0.0 ? (2 * Math.PI + signedAtan2Value) : signedAtan2Value;
      assertEquals(angle, expectedAtan2Value, 0.0001);
      assertEquals("Mismatch angle for i=" + i, angle,
          DbgFastTrigonometry.toRadians(DbgFastTrigonometry.atan2(y, x)), 0.05);
    }
  }

  //
  // Private
  //

  private static void verifySinAndCos(int angleIndex, double angleRadians) {
    final double ep = 0.0001;
    assertEquals("Mismatch for sin#" + angleIndex, Math.sin(angleRadians), DbgFastTrigonometry.sin(angleIndex), ep);
    assertEquals("Mismatch for cos#" + angleIndex, Math.cos(angleRadians), DbgFastTrigonometry.cos(angleIndex), ep);
  }
}
