package sctd.model;

import org.junit.Ignore;
import org.junit.Test;
import sctd.util.DiscreteAngle;
import sctd.util.FastTrigonometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests rotation functionality.
 *
 * @author Alexander Shabanov
 */
@Ignore
public final class GameUnitRotationTest {

  @Test
  public void shouldRotateClockwiseInFirstQuarter() {
    // Given:
    final int sourceAngle = FastTrigonometry.HALF_DEG / 4;
    final int targetAngle = 0;
    final int angularVelocity = FastTrigonometry.HALF_DEG / 8;

    // When:
    final int interimAngle = getRotationAngle(sourceAngle, targetAngle, angularVelocity);

    // Then:
    assertEquals(angularVelocity, interimAngle);
  }

  @Test
  public void shouldRotateCounterClockwiseInFirstQuarter() {
    // Given:
    final int sourceAngle = FastTrigonometry.HALF_DEG / 4;
    final int targetAngle = FastTrigonometry.HALF_DEG / 2;
    final int angularVelocity = FastTrigonometry.HALF_DEG / 8;

    // When:
    final int interimAngle = getRotationAngle(sourceAngle, targetAngle, angularVelocity);

    // Then:
    assertEquals(angularVelocity + sourceAngle, interimAngle);
  }

  @Test
  public void shouldRotateClockwiseInFirstAndFourthQuarter() {
    // Given:
    final int sourceAngle = FastTrigonometry.HALF_DEG / 4;
    final int targetAngle = 7 * FastTrigonometry.HALF_DEG / 4;
    final int angularVelocity = FastTrigonometry.HALF_DEG / 8;

    // When:
    final int interimAngle = getRotationAngle(sourceAngle, targetAngle, angularVelocity);

    // Then:
    assertEquals(angularVelocity, interimAngle);
  }

  @Test
  public void shouldRotateCounterclockwiseInFirstAndFourthQuarter() {
    // Given:
    final int sourceAngle = 7 * FastTrigonometry.HALF_DEG / 4;
    final int targetAngle = FastTrigonometry.HALF_DEG / 4;
    final int angularVelocity = FastTrigonometry.HALF_DEG / 8;

    // When:
    final int interimAngle = getRotationAngle(sourceAngle, targetAngle, angularVelocity);

    // Then:
    assertEquals(sourceAngle + angularVelocity, interimAngle);
  }

  private static int getRotationAngle(int sourceAngle, int targetAngle, int angularVelocity) {
    final GameUnit gameUnit = new GameUnit();
    gameUnit.setAngle(sourceAngle);
    gameUnit.setAngularVelocity(angularVelocity);

    final boolean rotationCompleted = gameUnit.rotateToAngle(targetAngle);
    assertFalse("Rotation should not be completed", rotationCompleted);

    return gameUnit.getAngleAsDiscrete();
  }
}
