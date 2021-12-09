package gutest;

import dungeon.Dungeon;
import dungeon.KruskalDungeon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * To test the methods and functionalities of project 4 which involve addition of Otyughs,
 * functionality of shooting, smell of an Otyugh and Arrows.
 */
public class TestProject4 {
  KruskalDungeon d;

  @Before
  public void setup() {
    d = new KruskalDungeon(6, 6, 4, true, 80, 3, -1);
  }

  /**
   * To test the number of Otyughs in the dungeon.
   */
  @Test
  public void testNumberOfOtyughs() {

    Assert.assertEquals(3, d.getNumberOfOtyughs());
  }

  /**
   * To test that End always has an Otyugh.
   */
  @Test
  public void testOtyughAtEnd() {
    Assert.assertTrue(d.endHasOtyugh());
  }

  /**
   * To test Illegal number of otyughs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidNumberOfOtyughs() {
    Dungeon d1 = new KruskalDungeon(6, 6, 4, true, 80, 0, -1);
  }

  /**
   * To test illegal number of Otyughs i.e. more than the nodes.
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidNumberOfOtyughs2() {
    Dungeon d1 = new KruskalDungeon(6, 6, 4, true, 80, 37, -1);
  }

  /**
   * To test the pungency when otyugh is not in the vicinity(2 units) of the player.
   */
  @Test
  public void smellOtyughLevel0() {
    Dungeon d1 = new KruskalDungeon(6, 6, 4, true, 80, 3, 2);
    d1.movePlayerEast();
    d1.movePlayerSouth();
    d1.movePlayerWest();
    d1.movePlayerSouth();

    Assert.assertEquals(0, d1.scanOtyugh());
  }

  /**
   * To test the pungency when one otyugh is in the vicinity of the player at a distance of 2 units.
   */
  @Test
  public void smellOtyughLevel1() {
    Dungeon d1 = new KruskalDungeon(6, 6, 4, true, 80, 3, 2);
    d1.movePlayerEast();
    d1.movePlayerSouth();
    d1.movePlayerWest();
    Assert.assertEquals(1, d1.scanOtyugh());


  }

  /**
   * To test the pungency when one otyugh is in the vicinity of the player at a distance of 1 units.
   */
  @Test
  public void smellOtyughLevel2() {
    Dungeon d1 = new KruskalDungeon(6, 6, 4, true, 80, 3, 2);
    d1.movePlayerEast();

    Assert.assertEquals(2, d1.scanOtyugh());
  }

  /**
   * To test for smell when  multiple otyughs present at 2 units. In this case Otyughs present at
   * Node 34 and 36. We try to find the pungency of smell from location 27 which is 2 units from
   * both.
   */
  @Test
  public void smellOtyughLevelMultipleOtyughs() {
    Dungeon d1 = new KruskalDungeon(6, 8, 4, true, 80, 3, 1);
    d1.movePlayerEast();
    d1.movePlayerEast();
    Assert.assertEquals(2, d1.scanOtyugh());
  }

  /**
   * To test that player starts with 3 arrows.
   */
  @Test
  public void testPlayerStartsWith3Arrows() {
    Dungeon d1 = new KruskalDungeon(6, 8, 4, true, 80, 3, 1);
    Assert.assertEquals(3, d1.getPlayerArrows());
  }

  /**
   * To test that player loses an arrow on shooting.
   */
  @Test
  public void testPlayerLosesArrowOnShooting() {
    Dungeon d1 = new KruskalDungeon(6, 8, 4, true, 80, 3, 1);
    int preShoot = d1.getPlayerArrows();
    //System.out.println(d1.getMovable());
    d1.shoot('E', 2);
    Assert.assertEquals(d1.getPlayerArrows(), preShoot - 1);
  }

  /**
   * To test tha player picks Arrow.
   */
  @Test
  public void testPlayerPicksArrow() {
    Dungeon d1 = new KruskalDungeon(6, 8, 4, true, 80, 3, 1);
    Assert.assertEquals("Available Treasures:RUBY\t\n"
        + "1 Arrows available\n", d1.getContents());
    d1.pickArrows();
    Assert.assertEquals("Available Treasures:RUBY\t\n", d1.getContents());
    Assert.assertEquals(4, d1.getPlayerArrows());
  }

  /**
   * Trying to pick arrows when not available.
   */
  @Test
  public void testPlayerCannotPickArrows() {
    Dungeon d1 = new KruskalDungeon(6, 8, 4, true, 20, 3, 1);
    Assert.assertEquals("Available Treasures:RUBY\t\n", d1.getContents());
    d1.pickArrows();
  }

  /**
   * Test to check if one arrow injures the Otyugh.
   * shoot() function returns 1 if the arrow injured otyugh. 2 if the arrow kills the otyugh.
   */
  @Test
  public void testPlayerInjuringOtyugh() {
    Dungeon d1 = new KruskalDungeon(6, 8, 4, true, 20, 3, 3);
    Assert.assertEquals(1, d1.shoot('W', 1)[0]);
  }

  /**
   * Test to check if one arrow Kills the Otyugh.
   * shoot() function returns 1 if the arrow injured otyugh. 2 if the arrow kills the otyugh.
   */
  @Test
  public void testPlayerKillingOtyugh() {
    Dungeon d1 = new KruskalDungeon(6, 8, 4, true, 20, 3, 3);
    Assert.assertEquals(1, d1.shoot('W', 1)[0]);
    Assert.assertEquals(2, d1.shoot('W', 1)[0]);

  }

  /**
   * To test that arrow gets blocked in cave. Returns 0 if No harm done or arrow gets blocked.
   */
  @Test
  public void testArrowBlocked() {
    Dungeon d1 = new KruskalDungeon(6, 8, 4, true, 20, 3, 3);
    Assert.assertEquals(0, d1.shoot('E', 2)[0]);
  }

  /**
   * To test that arrow doesn't get blocked in a tunnel.
   * ⮹ ⮹ █⇆⮹⇆⮹⇆⮹⇆⮹⇆⮹
   * ⇅   ⇅ ⇅     ⇅
   * ⮹ M⇆⮹
   * M is the monster and when we shoot in the direction of South the arrow travels in
   * the same direction as of the Tunnel and shoots the otyugh.
   */
  @Test
  public void testArrowTunnel() {
    Dungeon d1 = new KruskalDungeon(6, 8, 4, true, 20, 3, 5);
    d1.movePlayerEast();
    d1.movePlayerNorth();
    d1.movePlayerNorth();
    d1.movePlayerNorth();
    d1.movePlayerWest();
    d1.movePlayerWest();
    d1.movePlayerNorth();
    d1.movePlayerWest();
    d1.movePlayerNorth();
    d1.movePlayerWest();
    d1.movePlayerWest();
    d1.movePlayerWest();
    d1.movePlayerWest();
    Assert.assertEquals(1, d1.shoot('S', 2)[0]);
  }


}
