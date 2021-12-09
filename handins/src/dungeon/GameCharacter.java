package dungeon;

import java.util.List;

/**
 * Character is the main focus point in the game and has the objective to move from start to end
 * moving and overcoming obstacles in the maze.
 */
public interface GameCharacter extends Movable {

  /**
   * To pick the Treasure available at a location. It picks the first available treasure.
   * @param t Treasure
   * @throws IllegalStateException if Treasure not available.
   */
  void pickTreasure(Treasure t) throws IllegalStateException;

  /**
   * Method to decrement the number of arrows left after shooting an arrow.
   */
  void arrowUsed();

  /**
   * Returns the total number of arrows left with the player.
   * @return Int Number of arrows.
   */
  int getArrows();

  /**
   * Enables the user to pick up the arrow at the location.
   * @param count Specifies the number of arrows to pick.
   */
  void pickArrows(int count);

  /**
   * Method to get the number of treasures with the character.
   * @return integer number
   */
  int getTreasures();

  /**
   * Method to use a treasure to bridge the pit.
   */
  void useTreasure();

  /**
   * Method to get the list of treasures that the character has.
   * @return List of Treasures.
   */
  List<Treasure> getTreasuresList();

  /**
   * Method to lose all treasure to the thief.
   */
  void loseAllTreasure();
}
