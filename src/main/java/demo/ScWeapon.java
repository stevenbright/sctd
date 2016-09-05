package demo;

import java.util.EnumSet;
import java.util.Set;

import static demo.ScWeapon.Trait.AIR;
import static demo.ScWeapon.Trait.GROUND;

/**
 * @author Alexander Shabanov
 */
public class ScWeapon {
  enum Trait {
    AIR,
    GROUND
  }

  String name = "Gauss Rifle";
  Set<Trait> traits = EnumSet.of(AIR, GROUND);

  double damage = 6;
  double range = 4; // +1

  static final ScWeapon GAUSS_RIFLE = new ScWeapon();
}
