package dungeon;

import java.util.List;

/**
 * Mock Model used for controller testing where the model doesn't exist. Its very important way of
 * testing the controller in isolation which finds out the behaviour.
 */
public class MockModel implements Dungeon {


  /**
   * To check if there is a pit in the vicinity of the player.
   *
   * @return true if pit found at distance 1.
   */
  @Override
  public boolean hasPitNearby() {
    return false;
  }

  /**
   * To check if game is over. Returns 1 when you manage to escape a monster.
   * returns 2 when you get caught by a monster.
   */
  @Override
  public int isGameOver(int seed) {
    return 0;
  }

  /**
   * To get thr number of arrows with the player in the dungeon.
   *
   * @return Integer number of arrows.
   */
  @Override
  public int getPlayerArrows() {
    return 0;
  }

  /**
   * The method which allows a player to shoot an arrow by specifying the distance and direction
   * that the arrow must travel.
   *
   * @param direction N E S W one of the directions.
   * @param distance  Distance specifies the distance that player feels the otyugh is at.
   * @return 1 if otyugh injured, 2 if otyugh killed and 0 if no harm done.
   */
  @Override
  public int[] shoot(char direction, int distance) {
    return new int[0];
  }

  /**
   * Method to shoot a poisoned arrow towards a direction and distance specified.
   *
   * @param direction direction of shot
   * @param distance  distance of shot
   * @return integer array
   */
  @Override
  public int[] poisonShot(char direction, int distance) {
    return new int[0];
  }

  /**
   * Function to create potential paths(edges) in the maze where each potential path is an edge.
   * The number of potential paths for n nodes is given by (n*2)-(n+m).
   */
  @Override
  public void createPaths() {
    //Does nothing
  }

  /**
   * Returns a string with the directions of freedom to a player i.e. the openings available to a
   * player to move.
   */
  @Override
  public String getMovable() {
    return null;
  }

  /**
   * Returns a string with the directions of freedom to a player i.e. the openings available to a
   * player to move.
   */
  @Override
  public String getMovable(int loc) throws IllegalArgumentException {
    return null;
  }

  /**
   * Get movable for a particular location.
   *
   * @param loc location in the dungeon
   * @return String with movable directions.
   */
  @Override
  public String getMovable(int[] loc) {
    return null;
  }

  /**
   * To return the start of the dungeon in the form of an arrow of 2 integers row number and
   * column number.
   */
  @Override
  public int[] getStart() {
    return new int[0];
  }

  /**
   * To return the end of the dungeon in the form of an arrow of 2 integers row number and
   * column number.
   */
  @Override
  public int[] getEnd() {
    return new int[0];
  }

  /**
   * to get the number of caves in the dungeon.
   *
   * @return integer size of caveList
   */
  @Override
  public int getCaveListSize() {
    return 0;
  }

  /**
   * To get the number of caves with treasures in them.
   *
   * @return integer count of caves with one or more treasure in them.
   */
  @Override
  public int getTreasuredCaves() {
    return 0;
  }

  /**
   * To move the player in the mentioned direction.
   *
   * @param direction direction
   * @return true or false to determine if reached destination.
   */
  @Override
  public boolean movePlayer(String direction) {
    return false;
  }

  /**
   * Compute the distance between two Nodes in the Maze using manhattan distance.
   */
  @Override
  public int distance(int[] a, int[] b) {
    return 0;
  }

  /**
   * To get the treasure percentage in the dungeon. It's the treasure percentage passed when
   * creating the dungeon.
   */
  @Override
  public float getTreasureP() {
    return 0;
  }

  /**
   * To make a player pick the treasure at the location if it is available.
   */
  @Override
  public boolean pickTreasure() {
    return false;
  }

  /**
   * To get the player location ID of the node in which the player is located at.
   *
   * @return ID of the node.
   */
  @Override
  public int getPlayerLocationId() {
    return 0;
  }

  /**
   * Default method To move the player south if possible by checking if there exists a
   * connection to the south.
   */
  @Override
  public boolean movePlayerSouth() {
    return false;
  }

  /**
   * To move the player south if possible by checking if there exists a connection to the south.
   */
  @Override
  public boolean movePlayerSouth(Movable p) {
    return false;
  }

  /**
   * Default method
   * To move the player North if possible by checking if there exists a connection to the North.
   */
  @Override
  public boolean movePlayerNorth() {
    return false;
  }

  /**
   * To move the player North if possible by checking if there exists a connection to the North.
   */
  @Override
  public boolean movePlayerNorth(Movable p) {
    return false;
  }

  /**
   * Default method
   * To move the player West if possible by checking if there exists a connection to the North.
   */
  @Override
  public boolean movePlayerWest() {
    return false;
  }

  /**
   * To move the player West if possible by checking if there exists a connection to the West.
   */
  @Override
  public boolean movePlayerWest(Movable p) {
    return false;
  }

  /**
   * To check if End has Otyugh.
   *
   * @return true if it has and false if it doesn't.
   */
  @Override
  public boolean endHasOtyugh() {
    return false;
  }

  /**
   * Default method
   * To move the player East if possible by checking if there exists a connection to the North.
   */
  @Override
  public boolean movePlayerEast() {
    return false;
  }

  /**
   * To move the player East if possible by checking if there exists a connection to the East.
   */
  @Override
  public boolean movePlayerEast(Movable p) {
    return false;
  }

  /**
   * To get Number of Otyughs in the dungeon for testing.
   *
   * @return Integer number of Otyughs
   */
  @Override
  public int getNumberOfOtyughs() {
    return 0;
  }

  /**
   * To get the count of unreachable nodes in the dungeon.
   */
  @Override
  public int getUnreachableCount() {
    return 0;
  }

  /**
   * To get the description of a player which includes the name,location and list of treasures.
   */
  @Override
  public String describePlayer() {
    return null;
  }

  /**
   * To get the player location in the form a 2d array with row and column number.
   */
  @Override
  public int[] getPlayerLocation() {
    return new int[0];
  }

  /**
   * To get the total number of potential paths generated/possible in the dungeon.
   */
  @Override
  public int getPotentialPaths() {
    return 0;
  }

  /**
   * To get the pungency of a location by scanning the otyughs nearby.
   *
   * @return pungency as integer 1 for weak, 2 for strong.
   */
  @Override
  public int scanOtyugh() {
    return 0;
  }

  /**
   * To get the contents of the Node to be displayed to the user.
   *
   * @return String of contents
   */
  @Override
  public String getContents() {
    return null;
  }

  /**
   * To get the List of treasures in the node.
   *
   * @return String of treasures.
   */
  @Override
  public String getTreasures() {
    return null;
  }

  /**
   * Method to pick an arrow in the Node when available.
   *
   * @return true if successful and false if unsuccessful.
   */
  @Override
  public boolean pickArrows() {
    return false;
  }

  /**
   * To get the list of otyughs in the caves of dungeon. For testing purpose.
   *
   * @return List as string.
   */
  @Override
  public String getOtyughs() {
    return null;
  }

  /**
   * Method to get number of rows in the dungeon.
   *
   * @return integer rows.
   */
  @Override
  public int getRows() {
    return 0;
  }

  /**
   * Method to get number of cols in the dungeon.
   *
   * @return integer columns.
   */
  @Override
  public int getCols() {
    return 0;
  }

  /**
   * Method to get the interconnectivity of the dungeon..
   *
   * @return integer columns.
   */
  @Override
  public int getInterConnectivity() {
    return 0;
  }

  /**
   * Method to get the wrapping of the dungeon.
   *
   * @return True or false
   */
  @Override
  public boolean getWrapping() {
    return false;
  }

  /**
   * to check if the player location is a pit.
   *
   * @return true if yes otherwise false.
   */
  @Override
  public boolean checkPit() {
    return false;
  }

  /**
   * methid to get the treasure which player has.
   *
   * @return integer number of treasures.
   */
  @Override
  public int getPlayerTreasures() {
    return 0;
  }

  /**
   * bridge the pit by using the treasures.
   */
  @Override
  public void bridgeThePit() {
    //returns nothing
  }

  /**
   * Remove the pit after bridging it.
   */
  @Override
  public void removePit() {
    //returns nothing
  }

  /**
   * To get the list of treasures that the player has.
   *
   * @return Treasure list
   */
  @Override
  public List<Treasure> getPlayerTreasuresList() {
    return null;
  }

  /**
   * Method to get the treasures in the node which the player is present.
   *
   * @return the number of treasures available.
   */
  @Override
  public int getNodeTreasures() {
    return 0;
  }

  /**
   * Method to Get the arrows present in the node.
   *
   * @return number of arrows in the node.
   */
  @Override
  public int getNodeArrows() {
    return 0;
  }

  /**
   * Method to check if the current player node has a thief in it.
   *
   * @return true if yes or false if not
   */
  @Override
  public boolean nodeHasThief() {
    return false;
  }

  /**
   * Method to be called when a thief robs the player of the treasure.
   */
  @Override
  public void getRobbedByThief() {
    //returns nothing
  }

  /**
   * Method to check if the current node has a ninja in it.
   *
   * @return true or false
   */
  @Override
  public boolean nodeHasNinja() {
    return false;
  }

  /**
   * Method used by the tests to check if a pit was added to the dungeon.
   *
   * @return true/false
   */
  @Override
  public boolean pitAdded() {
    return false;
  }

  /**
   * Method used by the tests to check if a pit was added to the dungeon.
   *
   * @return true/false
   */
  @Override
  public boolean ninjaAdded() {
    return false;
  }

  /**
   * Method used by the tests to check if a pit was added to the dungeon.
   *
   * @return true/false
   */
  @Override
  public boolean thiefAdded() {
    return false;
  }

  /**
   * Method used by the tests to check if Ninja moves randomly.
   *
   * @return true/false
   */
  @Override
  public boolean ninjaMovable() {
    return false;
  }
}
