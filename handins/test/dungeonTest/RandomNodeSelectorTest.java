package gutest;

import dungeon.RandomNodeSelector;
import org.junit.Assert;
import org.junit.Test;

/**
 * To test the random node selector class and its methods.
 */
public class RandomNodeSelectorTest {

  /**
   * To test the random node selector.
   */
  @Test
  public void getRandomNode() {
    int[] res = RandomNodeSelector.getRandomNode(4, 6, 1);
    Assert.assertEquals(0, res[0]);
    Assert.assertEquals(3, res[1]);
  }
}