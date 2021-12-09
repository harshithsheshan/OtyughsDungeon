package dungeon;

import java.util.List;

/**
 * Interface to denote a node in the dungeon. It is the building block of the dungeon and a set
 * of nodes arranged together and connected forms a dungeon.
 */
public interface Node {

  /**
   * To Place an arrow in the node. adds an arrow to the list of arrows in the node.
   */
  void placeArrow();

  /**
   * when a demon is shot on the method is used to determine if the demon is dead or injured.
   * @return 1 if injured and 2 if killed.
   */
  int[] hitDemon();

  /**
   * To check if the player is in the location.
   * @return returns true id yes and false if not.
   */
  boolean hasPLayer();

  /**
   * To get the number of arrows available in the node.
   * @return integer number.
   */
  int getArrows();

  /**
   * To check if the node has a demon in it.
   * @return true if the node has a demon and false if not.
   */
  boolean hasDemon();

  /**
   * when a player picks arrow this method is called to reduce/remove the arrows from the dungeon.
   * @return the number of arrows picked.
   */
  int pickArrows();

  /**
   *  Used to print the dungeon in a visual representation.
   */
  String toStringNorth();

  /**
   *  Used to print the dungeon in a visual representation.
   */
  String toStringSouth();

  /**
   *  Used to print the dungeon in a visual representation.
   */
  String toStringFirstCol();

  /**
   * To mark the type of node based on the number of entrances it has. i.e. tunnel if entrances=2
   * and cave otherwise.
   */
  void markType(String type);

  /**
   * To place a player in the node i.e. mark that player is in the node.
   */
  void placePlayer();

  /**
   * To check if the Node is of tunnel type.
   * @return true if a tunnel.
   */
  boolean isTunnel();

  /**
   * To place an otyugh in the node and add it to the list of otyughs at the node.
   * @param id unique id given to the otyugh.
   */
  void placeOtyugh(int id);

  /**
   *  To remove/ unmark a player from the node once the player moves to another location.
   */
  void removePlayer();

  /**
   * To connect node to the north neighbour node.
   */
  void connectNorth(Node n);

  /**
   * To connect node to the south neighbour node.
   */
  void connectSouth(Node n);

  /**
   * To connect node to the East neighbour node.
   */
  void connectEast(Node n);

  /**
   * To connect node to the West neighbour node.
   */
  void connectWest(Node n);

  /**
   * To get the north neighbour of the node.
   */
  Node getNorth();

  /**
   * To get the East neighbour of the node.
   */
  Node getEast();

  /**
   * To get the South neighbour of the node.
   */
  Node getSouth();

  /**
   * To get the West neighbour of the node.
   */
  Node getWest();

  /**
   * To get the id assigned to the node.
   */
  int getId();

  /**
   * To get the List of treasures at the location.
   */
  List<Treasure> getTreasures();

  /**
   * To remove the treasure from the node after the player picks it.
   */
  void removeTreasure();

  /**
   * To get the list of demons at the location.
   * @return List of demons.
   */
  List<Demon> getDemons();

  /**
   * To fill the node with treasure.
   */
  void fillTreasure();

  /**
   * To get the number of treasures at the location.
   */
  int getTreasureNum();

  /**
   * To get the number of entrances to a node.
   */
  int getEntrances();

  /**
   * To get the type of node. i.e. Project3.Node/cave/tunnel.
   */
  String getType();

  /**
   * Method to set the node to have a pit.
   */
  void setPit();

  /**
   * Method to check if the node has a pit in it.
   * @return true ot false
   */
  boolean hasPit();

  /**
   * Method to remove the pit from the node when bridged.
   */
  void removePit();

  /**
   * Method to set a thief in the node.
   */
  void setThief();

  /**
   * Method to check if the node has a thief in it.
   * @return trie or false
   */
  boolean hasThief();
}
