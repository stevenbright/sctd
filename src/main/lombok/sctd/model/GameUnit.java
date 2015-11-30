package sctd.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Alexander Shabanov
 */
@Getter
@Setter
@ToString
public class GameUnit {
  private int id;

  private int life;
  private int shields;

  // coordinates
  private int x;
  private int y;
  private int z;
}
