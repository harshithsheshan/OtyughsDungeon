package dungeon;

/**
 * Demon interface represents a monster in the Maze/Dungeon which feeds on fresh flesh. Player needs
 * to defeat at least one demon to win the game.
 */
public interface Demon {

  /**
   * When an arrow strikes a demon it takes hit and the health of the demon decreases.
   * @return true if otyugh killed.
   */
  boolean takeHit() ;

  /**
   * Returns the health of the demon.
   */
  int getHealth() ;

}
