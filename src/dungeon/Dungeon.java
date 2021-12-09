package dungeon;

import java.util.List;

/**
 * Project3.Dungeon interface to depict a dungeon with caves connected by tunnels.
 * Caves consist of treasures and player has to navigate through the dungeon to reach the end.
 */
public interface Dungeon {


  /**
   * To check if there is a pit in the vicinity of the player.
   * @return true if pit found at distance 1.
   */
  boolean hasPitNearby();

  /**
   * To check if game is over. Returns 1 when you manage to escape a monster.
   * returns 2 when you get caught by a monster.
   */
  int isGameOver(int seed);


  /**
   * To get thr number of arrows with the player in the dungeon.
   *
   * @return Integer number of arrows.
   */
  int getPlayerArrows();


  /**
   * The method which allows a player to shoot an arrow by specifying the distance and direction
   * that the arrow must travel.
   *
   * @param direction N E S W one of the directions.
   * @param distance  Distance specifies the distance that player feels the otyugh is at.
   * @return 1 if otyugh injured, 2 if otyugh killed and 0 if no harm done.
   */
  int[] shoot(char direction, int distance);

  /**
   * Method to shoot a poisoned arrow towards a direction and distance specified.
   * @param direction direction of shot
   * @param distance distance of shot
   * @return integer array
   */
  int[] poisonShot(char direction, int distance);

  /**
   * Function to create potential paths(edges) in the maze where each potential path is an edge.
   * The number of potential paths for n nodes is given by (n*2)-(n+m).
   */
  void createPaths();

  /**
   * Returns a string with the directions of freedom to a player i.e. the openings available to a
   * player to move.
   *
   */
  String getMovable();


  /**
   * Returns a string with the directions of freedom to a player i.e. the openings available to a
   * player to move.
   *
   */
  String getMovable(int loc) throws IllegalArgumentException;

  /**
   * Get movable for a particular location.
   * @param loc location in the dungeon
   * @return String with movable directions.
   */
  String getMovable(int[] loc);

  /**
   * To return the start of the dungeon in the form of an arrow of 2 integers row number and
   * column number.
   */
  int[] getStart();

  /**
   * To return the end of the dungeon in the form of an arrow of 2 integers row number and
   * column number.
   */
  int[] getEnd();

  /**
   * to get the number of caves in the dungeon.
   *
   * @return integer size of caveList
   */
  int getCaveListSize();

  /**
   * To get the number of caves with treasures in them.
   *
   * @return integer count of caves with one or more treasure in them.
   */
  int getTreasuredCaves();


  /**
   * To move the player in the mentioned direction.
   * @param direction direction
   * @return true or false to determine if reached destination.
   */
  boolean movePlayer(String direction);

  /**
   * Compute the distance between two Nodes in the Maze using manhattan distance.
   */
  int distance(int[] a, int[] b);

  /**
   * To get the treasure percentage in the dungeon. It's the treasure percentage passed when
   * creating the dungeon.
   */
  float getTreasureP();

  /**
   * To make a player pick the treasure at the location if it is available.
   */
  boolean pickTreasure();

  /**
   * To get the player location ID of the node in which the player is located at.
   * @return ID of the node.
   */
  int getPlayerLocationId();

  /**
   * Default method To move the player south if possible by checking if there exists a
   * connection to the south.
   */
  boolean movePlayerSouth();

  /**
   * To move the player south if possible by checking if there exists a connection to the south.
   */
  boolean movePlayerSouth(Movable p);

  /**
   * Default method
   * To move the player North if possible by checking if there exists a connection to the North.
   */
  boolean movePlayerNorth();

  /**
   * To move the player North if possible by checking if there exists a connection to the North.
   */
  boolean movePlayerNorth(Movable p);

  /**
   * Default method
   * To move the player West if possible by checking if there exists a connection to the North.
   */
  boolean movePlayerWest();

  /**
   * To move the player West if possible by checking if there exists a connection to the West.
   */
  boolean movePlayerWest(Movable p);

  /**
   * To check if End has Otyugh.
   * @return true if it has and false if it doesn't.
   */
  boolean endHasOtyugh();

  /**
   * Default method
   * To move the player East if possible by checking if there exists a connection to the North.
   */
  boolean movePlayerEast();

  /**
   * To move the player East if possible by checking if there exists a connection to the East.
   */
  boolean movePlayerEast(Movable p);

  /**
   * To get Number of Otyughs in the dungeon for testing.
   * @return Integer number of Otyughs
   */
  int getNumberOfOtyughs();



  /**
   * To get the count of unreachable nodes in the dungeon.
   */
  int getUnreachableCount();

  /**
   * To get the description of a player which includes the name,location and list of treasures.
   */
  String describePlayer();

  /**
   * To get the player location in the form a 2d array with row and column number.
   */
  int[] getPlayerLocation();


  /**
   * To get the total number of potential paths generated/possible in the dungeon.
   */
  int getPotentialPaths();

  /**
   * To get the pungency of a location by scanning the otyughs nearby.
   * @return pungency as integer 1 for weak, 2 for strong.
   */
  int scanOtyugh();

  /**
   * To get the contents of the Node to be displayed to the user.
   * @return String of contents
   */
  String getContents();

  /**
   * To get the List of treasures in the node.
   * @return String of treasures.
   */
  String getTreasures();

  /**
   * Method to pick an arrow in the Node when available.
   * @return true if successful and false if unsuccessful.
   */
  boolean pickArrows();

  /**
   * To get the list of otyughs in the caves of dungeon. For testing purpose.
   * @return List as string.
   */
  String getOtyughs();

  /**
   * Method to get number of rows in the dungeon.
   * @return integer rows.
   */
  int getRows();

  /**
   * Method to get number of cols in the dungeon.
   * @return integer columns.
   */
  int getCols();

  /**
   * Method to get the interconnectivity of the dungeon..
   * @return integer columns.
   */
  int getInterConnectivity();

  /**
   * Method to get the wrapping of the dungeon.
   * @return True or false
   */
  boolean getWrapping();

  /**
   * to check if the player location is a pit.
   * @return true if yes otherwise false.
   */
  boolean checkPit();

  /**
   * methid to get the treasure which player has.
   * @return integer number of treasures.
   */
  int getPlayerTreasures();

  /**
   * bridge the pit by using the treasures.
   */
  void bridgeThePit();

  /**
   * Remove the pit after bridging it.
   */
  void removePit();

  /**
   * To get the list of treasures that the player has.
   * @return Treasure list
   */
  List<Treasure> getPlayerTreasuresList();

  /**
   * Method to get the treasures in the node which the player is present.
   * @return the number of treasures available.
   */
  int getNodeTreasures();

  /**
   * Method to Get the arrows present in the node.
   * @return number of arrows in the node.
   */
  int getNodeArrows();

  /**
   * Method to check if the current player node has a thief in it.
   * @return true if yes or false if not
   */
  boolean nodeHasThief();

  /**
   * Method to be called when a thief robs the player of the treasure.
   */
  void getRobbedByThief();

  /**
   * Method to check if the current node has a ninja in it.
   * @return true or false
   */
  boolean nodeHasNinja();

  /**
   * Method used by the tests to check if a pit was added to the dungeon.
   * @return true/false
   */
  boolean pitAdded();

  /**
   * Method used by the tests to check if a pit was added to the dungeon.
   * @return true/false
   */
  boolean ninjaAdded();

  /**
   * Method used by the tests to check if a pit was added to the dungeon.
   * @return true/false
   */
  boolean thiefAdded();

  /**
   * Method used by the tests to check if Ninja moves randomly.
   * @return true/false
   */
  boolean ninjaMovable();
}
