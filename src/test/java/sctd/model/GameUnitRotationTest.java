package sctd.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests rotation functionality.
 *
 * @author Alexander Shabanov
 */
public final class GameUnitRotationTest {
  private static final double EPSILON = 0.000001;

  @Test
  public void shouldRotateClockwiseInFirstQuarter() {
    // Given:
    final double sourceAngle = Math.PI / 4;
    final double targetAngle = 0;
    final double angularVelocity = Math.PI / 8;

    // When:
    final double interimAngle = getRotationAngle(sourceAngle, targetAngle, angularVelocity);

    // Then:
    assertEquals(angularVelocity, interimAngle, EPSILON);
  }

  @Test
  public void shouldRotateCounterClockwiseInFirstQuarter() {
    // Given:
    final double sourceAngle = Math.PI / 4;
    final double targetAngle = Math.PI / 2;
    final double angularVelocity = Math.PI / 8;

    // When:
    final double interimAngle = getRotationAngle(sourceAngle, targetAngle, angularVelocity);

    // Then:
    assertEquals(angularVelocity + sourceAngle, interimAngle, EPSILON);
  }

  @Test
  public void shouldRotateClockwiseInFirstAndFourthQuarter() {
    // Given:
    final double sourceAngle = Math.PI / 4;
    final double targetAngle = 7 * Math.PI / 4;
    final double angularVelocity = Math.PI / 8;

    // When:
    final double interimAngle = getRotationAngle(sourceAngle, targetAngle, angularVelocity);

    // Then:
    assertEquals(angularVelocity, interimAngle, EPSILON);
  }

  @Test
  public void shouldRotateCounterclockwiseInFirstAndFourthQuarter() {
    // Given:
    final double sourceAngle = 7 * Math.PI / 4;
    final double targetAngle = Math.PI / 4;
    final double angularVelocity = Math.PI / 8;

    // When:
    final double interimAngle = getRotationAngle(sourceAngle, targetAngle, angularVelocity);

    // Then:
    assertEquals(sourceAngle + angularVelocity, interimAngle, EPSILON);
  }

  private static double getRotationAngle(double sourceAngle, double targetAngle, double angularVelocity) {
    final GameUnit gameUnit = new GameUnit();
    gameUnit.setAngle(sourceAngle);
    gameUnit.setAngularVelocity(angularVelocity);

    final boolean rotationCompleted = gameUnit.rotateToAngle(targetAngle);
    assertFalse("Rotation should not be completed", rotationCompleted);

    return gameUnit.getAngle();
  }
}
