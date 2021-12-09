package dungeon;

/**
 * Class which has methods to move around the dungeon weather wrapping or not.
 */
public class MovableImpl implements Movable {

  protected int[] location;

  /**
   * Constructor to initialize the movable object with initial location.
   *
   * @param location array of row and column number
   */
  public MovableImpl(int[] location) {
    if (location[0] < 0 || location[1] < 0) {
      throw new IllegalArgumentException();
    }
    this.location = location;
  }

  /**
   * To get the current location of the player. returns an array with row and column of location.
   */
  @Override
  public int[] getLocation() {
    int[] res = location.clone();
    return (res);
  }

  /**
   * To make the player move south from the current location if possible.
   */
  @Override
  public void moveSouth() {
    location[0]++;
  }

  /**
   * To make the player move south from the current location if possible and
   * when wrapping is enabled.
   */
  @Override
  public void moveSouthWrapping(int rows) {
    if (rows < 0) {
      throw new IllegalArgumentException();
    }
    location[0] -= rows;
  }

  /**
   * To make the player move West from the current location if possible.
   */
  @Override
  public void moveWest() {
    location[1]--;
  }

  /**
   * To make the player move West from the current location if possible and
   * when wrapping is enabled.
   */
  @Override
  public void moveWestWrapping(int cols) {
    if (cols < 0) {
      throw new IllegalArgumentException();
    }
    location[1] += cols;
  }

  /**
   * To make the player move East from the current location if possible.
   */
  @Override
  public void moveEast() {
    location[1]++;
  }

  /**
   * To make the player East south from the current location if possible and
   * when wrapping is enabled.
   */
  @Override
  public void moveEastWrapping(int cols) {
    if (cols < 0) {
      throw new IllegalArgumentException();
    }
    location[1] -= cols;
  }

  /**
   * To make the player move North from the current location if possible.
   */
  @Override
  public void moveNorth() {
    location[0]--;
  }

  /**
   * To make the player move North from the current location if possible and
   * when wrapping is enabled.
   */
  @Override
  public void moveNorthWrapping(int rows) {
    if (rows < 0) {
      throw new IllegalArgumentException();
    }
    location[0] += rows;
  }
}
