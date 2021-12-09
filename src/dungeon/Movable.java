package dungeon;

/**
 * Project3.Player interface to denote a player in the dungeon. A player's objective is to
 * reach the end.
 */
public interface Movable {

  /**
   * To get the current location of the player. returns an array with row and column of location.
   */
  int[] getLocation();

  /**
   * To make the player move south from the current location if possible.
   */
  void moveSouth();

  /**
   * To make the player move south from the current location if possible and
   * when wrapping is enabled.
   */
  void moveSouthWrapping(int rows);

  /**
   * To make the player move West from the current location if possible and
   * when wrapping is enabled.
   */
  void moveWestWrapping(int cols);

  /**
   * To make the player move East from the current location if possible.
   */
  void moveEast();

  /**
   * To make the player East south from the current location if possible and
   * when wrapping is enabled.
   */
  void moveEastWrapping(int cols);

  /**
   * To make the player move North from the current location if possible.
   */
  void moveNorth();

  /**
   * To make the player move West from the current location if possible.
   */
  void moveWest();

  /**
   * To make the player move North from the current location if possible and
   * when wrapping is enabled.
   */
  void moveNorthWrapping(int rows);


}
