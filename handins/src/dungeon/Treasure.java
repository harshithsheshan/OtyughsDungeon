package dungeon;

import java.util.Random;

/**
 * Enum Project3.Treasure to denote a treasure which can be placed in caves and can be collected
 * by player.It is mainly of three types and gets randomly assigned on calling the method
 * associated with it.
 */
public enum Treasure {
  RUBY, SAPPHIRE, DIAMOND;

  /**
   * Function to get a random type of treasure from the enumeration.
   *
   * @param seed for testing purpose.
   * @return treasure.
   */
  public static Treasure getRandomTreasure(int seed) {
    if (seed < 0) {
      throw new IllegalArgumentException();
    }
    Random random;
    if (seed != 0) {
      random = new Random(seed);
    } else {
      random = new Random();
    }
    //System.out.println(values()[random.nextInt(values().length)]);
    return values()[random.nextInt(values().length)];
  }
}
