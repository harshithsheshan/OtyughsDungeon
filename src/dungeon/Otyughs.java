package dungeon;

/**
 * Otyughs are demons in the maze which are smelly and can eat a player up. They move randomly
 * around the maze and can attack a player.
 */
public class Otyughs implements Demon {
  private final int id;
  private int health;

  /**
   * Constructor of Otyugh which assigns a unique ID and assigns the health when created.
   *
   * @param id Unique Number given to the otyugh.
   */
  public Otyughs(int id) {
    if (id < 0) {
      throw new IllegalArgumentException();
    }
    this.health = 2;
    this.id = id;
  }

  /**
   * When an arrow strikes a demon it takes hit and the health of the demon decreases.
   *
   * @return true if otyugh killed.
   */
  @Override
  public boolean takeHit() {
    health--;
    return health <= 0;
  }

  /**
   * Returns the health of the demon.
   */
  @Override
  public int getHealth() {
    return health;
  }

  /**
   * Return the description of the otyugh with its ID and Health.
   *
   * @return String with the description.
   */
  @Override
  public String toString() {
    return String.format("ID: %d\thealth: %d", id, health);
  }
}
