package demo;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static demo.ScUnit.Prototype.Flags.BIOLOGICAL;

/**
 * Represents data about sc unit
 *
 * @author Alexander Shabanov
 */
class ScUnit {

  static class Prototype {

    enum Flags {
      FLYER,
      INDESTRUCTIBLE,
      HALLUCINATION,
      BIOLOGICAL
    }

    enum SizeTrait {
      SMALL,
      MEDIUM,
      LARGE
    }

    String name = "Marine";

    SizeTrait sizeTrait = SizeTrait.SMALL;

    int maxLife = 50;
    int lifeArmor = 0;
    int lifeRegenerateRate = 0;

    int maxShield = 0;
    int shieldArmor = 0;
    int shieldRegenerateRate = 0;

    Set<Flags> flags = EnumSet.of(BIOLOGICAL);

    int supplyUsed = 1;
  }

  Prototype prototype;

  int life = 40;
  int shield = 0;
  List<ScWeapon> weapons = Collections.singletonList(ScWeapon.GAUSS_RIFLE);

  int x;
  int y;

  int acceleration = 0;

  // Extra - starcraft-specific supportive variables
  int stasisCooldownTimer = 0; // for units caught with stasis effect
  int recallCooldownTimer = 0; // for units under the effect of teleportation
}
