package gutest;

import dungeon.Dungeon;
import dungeon.KruskalDungeon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * To test the methods and functionalities of a Kruskal's dungeon. Includes tests for object
 * creation and to check if the maze is created correctly.
 */
public class KruskalDungeonTest {

  KruskalDungeon d;

  @Before
  public void setup() {
    d = new KruskalDungeon(6, 8, 4, true, 80, 1, -1);
  }

  /**
   * To test the creation of potential paths.
   */
  @Test
  public void createPaths() {
    int pre = d.getPotentialPaths();
    Assert.assertEquals(96, pre);

  }

  /**
   * To test the movement of player in all 4 directions.
   */
  @Test
  public void moveFourDirections() {
    System.out.println(d.toString());
    int[] preMove = d.getPlayerLocation();
    System.out.println(d.getEnd()[0] + " " + d.getEnd()[1]);
    //System.out.println(d.toString());
    d.movePlayerWest();
    d.movePlayerEast();
    d.movePlayerNorth();
    d.movePlayerSouth();
    int[] postMove = d.getPlayerLocation();
    Assert.assertEquals(preMove[0], postMove[0]);
  }

  /**
   * To test the movement of player to south when possible.
   */
  @Test
  public void movePlayerSouth() {
    int[] preMove = d.getPlayerLocation();
    d.movePlayerSouth();
    int[] postMove = d.getPlayerLocation();
    Assert.assertEquals(preMove[0], postMove[0] + 5);

  }

  /**
   * To test the movement of player to north when possible.
   */
  @Test
  public void movePlayerNorth() {
    d.movePlayerSouth();
    int[] preMove = d.getPlayerLocation();
    d.movePlayerNorth();
    int[] postMove = d.getPlayerLocation();
    Assert.assertEquals(preMove[0], postMove[0] - 5);

  }

  /**
   * To test the movement of player to East when possible.
   */
  @Test
  public void movePlayerEast() {

    int[] preMove = d.getPlayerLocation();
    System.out.println(d.toString());
    d.movePlayerWest();
    d.movePlayerEast();
    int[] postMove = d.getPlayerLocation();
    Assert.assertEquals(preMove[1], postMove[1]);
  }

  /**
   * To test the movement of player to West when possible.
   */
  @Test
  public void movePlayerWest() {
    System.out.println(d.toString());
    int[] preMove = d.getPlayerLocation();
    d.movePlayerWest();
    int[] postMove = d.getPlayerLocation();
    Assert.assertEquals(preMove[1], postMove[1] + 1);
  }

  /**
   * To test that treasure is available in the specified percentage of caves.
   */
  @Test
  public void testTreasure() {
    //System.out.println(d.getTreasuredCaves());
    //System.out.println(d.getCaveList());
    //System.out.println();
    Assert.assertTrue(
        ((float) d.getTreasuredCaves() / d.getCaveListSize()) * 100 - d.getTreasureP()
            < 5);
  }

  /**
   * To test that the total number of potential paths is correct and is same as expected.
   */
  @Test
  public void testGetPotentialPath() {
    Assert.assertEquals(96, d.getPotentialPaths());
  }

  /**
   * To test that description of a player whose name,treasures and location is displayed.
   */
  @Test
  public void describePlayer() {
    Assert.assertEquals("Name: Arnold treasures= [] Arrows= 3 location= 5 1",
        d.describePlayer());
  }

  /**
   * To test if a player picks treasure when available in the cave.
   */
  @Test
  public void pickTreasure() {
    d.movePlayerNorth();
    d.movePlayerWest();
    d.pickTreasure();
    Assert.assertEquals(
        "Name: Arnold treasures= [RUBY] Arrows= 3 location= 4 0",
        d.describePlayer());
  }


  /**
   * To test that the start and end are at least at a distance of 5 units.
   */
  @Test
  public void startEndDistance() {
    int[] start;
    int[] end;
    start = d.getStart();
    end = d.getEnd();
    //System.out.println(d.distance(start, end));
    Assert.assertTrue(d.distance(start, end) >= 5);
  }

  /**
   * To test that player location is accurate.
   */
  @Test
  public void getPlayerLocation() {
    Assert.assertEquals(5, d.getPlayerLocation()[0]);
    Assert.assertEquals(1, d.getPlayerLocation()[1]);
  }


  /**
   * To test if all nodes are connected. There exists at least one path from one node to another.
   */
  @Test
  public void testAllNodesReachable() {
    Assert.assertEquals(0, d.getUnreachableCount());
  }

  /**
   * To test that player starts at the Dungeon start location.
   */
  @Test
  public void playerStartsFromDungeonStart() {
    Dungeon test = new KruskalDungeon(4, 6, 4, true, 50, 1, -1);
    Assert.assertEquals(test.getStart()[0], test.getPlayerLocation()[0]);
    Assert.assertEquals(test.getStart()[1], test.getPlayerLocation()[1]);
  }

}