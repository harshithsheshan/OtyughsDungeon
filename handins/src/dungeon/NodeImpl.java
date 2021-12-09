package dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to implement the node with object fields and methods required by the node in a dungeon
 * which can be of n*m in dimensions and has n*m nodes arranged in a 2d grid.
 */
public class NodeImpl implements Node {
  private Node north;
  private Node south;
  private Node east;
  private Node west;
  private int id;
  private List<Treasure> treasures;
  boolean hasPlayer;
  private String type;
  boolean hasDemon;
  private int arrows;
  private List<Demon> demon;
  boolean hasPit;
  boolean hasThief;


  /**
   * Constructor to initialize the object with initial values when its created.
   *
   * @param count gives the id to the node on creation.
   */
  public NodeImpl(int count) {
    if (count < 0) {
      throw new IllegalArgumentException();
    }
    this.treasures = new ArrayList<Treasure>();
    this.north = null;
    this.east = null;
    this.south = null;
    this.west = null;
    this.id = count;
    this.type = "node";
    this.hasPlayer = false;
    this.arrows = 0;
    this.hasDemon = false;
    this.demon = new ArrayList<Demon>();
    this.hasPit = false;
    this.hasThief = false;
    //System.out.println(getDemons());

  }

  /**
   * Copy constructor used to copy another node details.
   *
   * @param a the node from which values are copied.
   */
  public NodeImpl(Node a) {
    if (a == null) {
      throw new IllegalArgumentException();
    }
    treasures = new ArrayList<Treasure>(a.getTreasures());
    this.north = a.getNorth();
    this.east = a.getEast();
    this.south = a.getSouth();
    this.west = a.getWest();
    this.id = a.getId();
    this.hasDemon = a.hasDemon();
    demon = new ArrayList<>();
  }

  /**
   * To Place an arrow in the node. adds an arrow to the list of arrows in the node.
   */
  @Override
  public void placeArrow() {
    this.arrows++;

  }

  /**
   * when a demon is shot on the method is used to determine if the demon is dead or injured.
   *
   * @return 1 if injured and 2 if killed.
   */
  @Override
  public int[] hitDemon() {
    int[] res = new int[2];
    res[1] = id;
    if (demon.get(0).takeHit()) {
      demon.remove(0);
      if (demon.size() == 0) {
        hasDemon = false;
      }
      res[0] = 2;
      return res;
    }
    res[0] = 1;
    return res;
  }

  /**
   * Returns if the node has player in it.
   */
  @Override
  public boolean hasPLayer() {
    return hasPlayer;
  }

  /**
   * To get the number of arrows available in the node.
   *
   * @return integer number.
   */
  @Override
  public int getArrows() {
    return arrows;
  }

  /**
   * To check if the node has a demon in it.
   *
   * @return true if the node has a demon and false if not.
   */
  @Override
  public boolean hasDemon() {

    return hasDemon;
  }

  /**
   * when a player picks arrow this method is called to reduce/remove the arrows from the dungeon.
   *
   * @return the number of arrows picked.
   */
  @Override
  public int pickArrows() {
    int res = arrows;
    this.arrows = 0;
    return res;
  }

  /**
   * Used to print the dungeon in a visual representation.
   */
  @Override
  public String toStringNorth() {
    StringBuilder str = new StringBuilder();
    //System.out.println(this.north);
    if (this.north != null) {
      str.append(String.format("%s", " ⇅"));
    } else {
      str.append(String.format("%s", "  "));
    }
    return str.toString();
  }

  /**
   * Used to print the dungeon in a visual representation.
   */
  @Override
  public String toStringSouth() {
    StringBuilder str = new StringBuilder();
    if (this.south != null) {
      str.append(String.format("%c", '⇅'));
    } else {
      str.append(String.format("%c", ' '));
    }
    return str.toString();
  }

  /**
   * To mark the type of node based on the number of entrances it has. i.e. tunnel if entrances=2
   * and cave otherwise.
   */
  @Override
  public String toStringFirstCol() {
    StringBuilder str = new StringBuilder();
    char n = '⮹';
    if (hasPlayer) {
      n = '█';
    }
    String westConnect = this.west != null ? "⇆" : " ";
    String eastConnect = this.east != null ? "⇆" : " ";
    str.append(String.format("%s%c%s", westConnect, n, eastConnect));
    return str.toString();
  }


  /**
   * To mark the type of node based on the number of entrances it has. i.e. tunnel if entrances=2
   * and cave otherwise.
   */
  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    char n = '⮹';
    if (hasPlayer) {
      n = '█';
    }
    char eastConnect = this.east != null ? '⇆' : ' ';
    str.append(String.format("%c%c", n, eastConnect));
    return str.toString();

  }

  /**
   * To mark the type of node based on the number of entrances it has. i.e. tunnel if entrances=2
   * and cave otherwise.
   */
  @Override
  public void markType(String type) {
    if (type == null) {
      throw new IllegalArgumentException();
    }
    this.type = type;
  }


  /**
   * To place a player in the node i.e. mark that player is in the node.
   */
  @Override
  public void placePlayer() {
    hasPlayer = true;
  }

  /**
   * To check if the Node is of tunnel type.
   *
   * @return true if a tunnel.
   */
  @Override
  public boolean isTunnel() {
    return this.type.equals("Tunnel");
  }

  /**
   * To place an otyugh in the node and add it to the list of otyughs at the node.
   *
   * @param id unique id given to the otyugh.
   */
  @Override
  public void placeOtyugh(int id) {
    if (id < 0) {
      throw new IllegalArgumentException();
    }
    hasDemon = true;
    demon.add(new Otyughs(id));
  }

  /**
   * To remove/ unmark a player from the node once the player moves to another location.
   */
  @Override
  public void removePlayer() {
    hasPlayer = false;
  }

  /**
   * To connect node to the north neighbour node.
   */
  @Override
  public void connectNorth(Node n) {
    if (n == null) {
      throw new IllegalArgumentException();
    }
    this.north = n;
    //System.out.println(this.toString());
  }

  /**
   * To connect node to the south neighbour node.
   */
  @Override
  public void connectSouth(Node n) {
    if (n == null) {
      throw new IllegalArgumentException();
    }
    this.south = n;
  }

  /**
   * To connect node to the East neighbour node.
   */
  @Override
  public void connectEast(Node n) {
    if (n == null) {
      throw new IllegalArgumentException();
    }
    this.east = n;
  }

  /**
   * To connect node to the West neighbour node.
   */
  @Override
  public void connectWest(Node n) {
    if (n == null) {
      throw new IllegalArgumentException();
    }
    this.west = n;
  }

  /**
   * To get the north neighbour of the node.
   */
  @Override
  public Node getNorth() {
    return this.north;
  }

  /**
   * To get the East neighbour of the node.
   */
  @Override
  public Node getEast() {
    return this.east;
  }

  /**
   * To get the South neighbour of the node.
   */
  @Override
  public Node getSouth() {
    return this.south;
  }

  /**
   * To get the West neighbour of the node.
   */
  @Override
  public Node getWest() {
    return this.west;
  }

  /**
   * To get the id assigned to the node.
   */
  @Override
  public int getId() {
    return this.id;
  }

  /**
   * To get the List of treasures at the location.
   */
  @Override
  public List<Treasure> getTreasures() {
    List<Treasure> res = new ArrayList<Treasure>(treasures);
    return res;
  }

  /**
   * To remove the treasure from the node after the player picks it.
   */
  @Override
  public void removeTreasure() {
    treasures.remove(0);
  }

  /**
   * To get the list of demons at the location.
   *
   * @return List of demons.
   */
  @Override
  public List<Demon> getDemons() {
    List<Demon> res = new ArrayList<Demon>(demon);
    return res;
  }


  /**
   * To fill the node with treasure.
   */
  @Override
  public void fillTreasure() {
    treasures.add(Treasure.getRandomTreasure(1));
  }

  /**
   * To get the number of treasures at the location.
   */
  @Override
  public int getTreasureNum() {
    return treasures.size();
  }


  /**
   * To get the number of entrances to a node.
   */
  @Override
  public int getEntrances() {
    int count = 0;
    if (north != null) {
      count++;
    }
    if (south != null) {
      count++;
    }
    if (west != null) {
      count++;
    }
    if (east != null) {
      count++;
    }
    return count;
  }

  /**
   * To get the type of node. i.e. Project3.Node/cave/tunnel.
   */
  @Override
  public String getType() {
    return type;
  }

  @Override
  public void setPit() {
    this.hasPit = true;

  }

  @Override
  public boolean hasPit() {
    return hasPit;
  }

  @Override
  public void removePit() {
    hasPit = false;
  }

  @Override
  public void setThief() {
    //System.out.println(this.id);
    hasThief = true;
  }

  @Override
  public boolean hasThief() {
    return hasThief;
  }
}
