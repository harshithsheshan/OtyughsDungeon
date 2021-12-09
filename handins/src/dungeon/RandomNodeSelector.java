package dungeon;

import java.util.Random;

/**
 * Random node Selector class which is used to select a random node for selecting the start and end.
 */
public class RandomNodeSelector {

  /**
   * Function to return a random node from the grid of n rows and n columns.
   */
  public static int[] getRandomNode(int nRow, int nCols, int seed) {
    Random random;
    random = new Random(seed);
    int[] node = new int[2];
    node[0] = random.nextInt(nRow - 1);
    node[1] = (random.nextInt(nCols - 1));
    return node;
  }
}
