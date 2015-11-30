package sctd;

import sctd.model.GameUnit;

import javax.annotation.Generated;

/**
 * Hello world!
 */
public final class App {

  public static void main(String[] args) {
    System.out.println("== SCTD ==");
    dumpUnit();
  }

  //
  // Private
  //

  @Generated("skip")
  private static void dumpUnit() {
    final GameUnit unit = new GameUnit();
    unit.setId(1);
    System.out.println("Object=" + unit);
  }
}

