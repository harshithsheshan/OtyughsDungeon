package dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * To depict a player in the dungeon. A player has a name and location. He can collect treasure
 * when he enters a dungeon with treasure. Extends properties of movable.
 */
public class PlayerImpl extends MovableImpl implements GameCharacter {
  private final String name;
  private List<Treasure> treasures;
  private int arrows;


  /**
   * Constructor to initialize the player with initial values. Name is chosen on random from a
   * list of random names. and player is positioned at the start of the maze.
   */
  public PlayerImpl(String name, int[] location) {
    super(location);
    if (name == null || location[0] < 0 || location[1] < 0) {
      throw new IllegalArgumentException();
    }
    this.name = name;
    this.treasures = new ArrayList<Treasure>();
    //this.location = location;
    this.arrows = 3;
  }

  /**
   * To describe the player with name contents of backpack and location in the maze.
   *
   * @return String of the description.
   */
  @Override
  public String toString() {
    return String.format("Name: %s treasures= %s Arrows= %d location= %d %d",
        name,
        treasures, arrows,
        super.location[0], super.location[1]);
  }

  /**
   * Method to enable player to pick treasure from a node.
   *
   * @param t Treasure
   * @throws IllegalStateException if treasure object is null.
   */
  @Override
  public void pickTreasure(Treasure t) throws IllegalStateException {
    if (t == null) {
      throw new IllegalArgumentException();
    }
    //System.out.println("Project3.Treasure picked");
    treasures.add(t);
  }

  /**
   * Arrow used reduces the total number of arrows that the player has after shooting an arrow.
   */
  @Override
  public void arrowUsed() {
    this.arrows--;
  }

  /**
   * Returns the total number of arrows the player has.
   *
   * @return integer number of arrows.
   */
  @Override
  public int getArrows() {
    return arrows;
  }

  /**
   * Method to allow the player to pick arrow from the location.
   *
   * @param count Specifies the number of arrows to be picked.
   */
  @Override
  public void pickArrows(int count) {
    if (count < 0) {
      throw new IllegalArgumentException();
    }
    this.arrows += count;
  }

  @Override
  public int getTreasures() {
    return treasures.size();
  }

  @Override
  public void useTreasure() {
    treasures.remove(treasures.size() - 1);
  }

  @Override
  public List<Treasure> getTreasuresList() {
    return new ArrayList<Treasure>(treasures);
  }

  @Override
  public void loseAllTreasure() {
    treasures.clear();
  }
}
