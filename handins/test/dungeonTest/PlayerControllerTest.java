package gutest;

import java.io.StringReader;
import controller.PlayerController;
import dungeon.Dungeon;
import dungeon.KruskalDungeon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * To test the methods of the controller and its functionalities to check how it handles different
 * user inputs and extreme cases.
 */
public class PlayerControllerTest {
  PlayerController c;
  Appendable out;
  Readable input;
  String commonOut;

  @Before
  public void setup() {
    commonOut = "\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\t\t\tWelcome to the Dungeon!\n"
        + "\tLets see If you can find a way out of it...\n"
        + "Destination :2\t3\n"
        + "You are in a cave\n"
        + "         ⇅  \n"
        + "⇆⮹⇆⮹⇆⮹ ⮹⇆⮹⇆⮹⇆\n"
        + " ⇅     ⇅   ⇅\n"
        + "\n"
        + " ⮹⇆█ ⮹⇆⮹ ⮹ ⮹ \n"
        + " ⇅ ⇅     ⇅  \n"
        + "\n"
        + "⇆⮹ ⮹⇆⮹ ⮹⇆⮹⇆⮹⇆\n"
        + "     ⇅ ⇅ ⇅ ⇅\n"
        + "\n"
        + "⇆⮹⇆⮹⇆⮹ ⮹ ⮹⇆⮹⇆\n"
        + "   ⇅   ⇅    \n"
        + "\n"
        + "⇆⮹ ⮹⇆⮹⇆⮹⇆⮹⇆⮹⇆\n"
        + "   ⇅   ⇅    \n"
        + "\n"
        + "⇆⮹ ⮹⇆⮹⇆⮹⇆⮹⇆⮹⇆\n"
        + "         ⇅  \n"
        + "Starting at\t1 1\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\n"
        + "Current location :1 1\n"
        + "1 Arrows available\n"
        + "\n"
        + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n";

  }

  /**
   * To test Controller with an invalid model throws IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalModel() {
    out = new StringBuilder();
    c = new PlayerController(input, out);
    c.playGame(null);
  }

  /**
   * To test controller when choosing move and a valid move.
   */
  @Test
  public void testChoosingMoveValid() {
    input = new StringReader("m s q");
    out = new StringBuilder();
    c = new PlayerController(input, out);
    Dungeon d1 = new KruskalDungeon(6, 6, 4, true, 80, 3, -1);
    c.playGame(d1);
    Assert.assertEquals(commonOut
        + "Possible moves : S  W \n" + "Where to next ?\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\n" + "Current location :2 1\n" + "1 Arrows available\n" + "\n"
        + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
        + "Quitting game...", out.toString());
  }

  /**
   * To test controller when choosing move and an invalid move.
   */
  @Test
  public void testChoosingMoveInvalid() {
    input = new StringReader("m n q");
    out = new StringBuilder();
    c = new PlayerController(input, out);
    Dungeon d1 = new KruskalDungeon(6, 6, 4, true, 80, 3, -1);
    c.playGame(d1);
    Assert.assertEquals(commonOut
        + "Possible moves : S  W \n" + "Where to next ?\n" + "Move not possible.\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\n" + "Current location :1 1\n" + "1 Arrows available\n" + "\n"
        + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
        + "Quitting game...", out.toString());

  }

  /**
   * To test controller when player moves reaches the end and eaten by monster.
   */
  @Test
  public void testMoveToEnd() {
    input = new StringReader("m e m e m e m s m s");
    out = new StringBuilder();
    c = new PlayerController(input, out);
    Dungeon d = new KruskalDungeon(6, 6, 4, true, 80, 1, 0);
    c.playGame(d);
    Assert.assertEquals("Got eaten by the monster at the end !\n"
        + " Game Over", out.toString().substring(out.toString().length() - 48));
  }

  /**
   * To test controller when player moves,shoots and kill Otyugh at end to win the game.
   */
  @Test
  public void winGame() {
    input = new StringReader("m e m e s e 1 s e 1 m e ");
    out = new StringBuilder();
    c = new PlayerController(input, out);
    Dungeon d = new KruskalDungeon(6, 6, 4, true, 80, 1, 0);
    //System.out.println(d.toString());
    //System.out.println(d.getEnd()[0]+" "+d.getEnd()[1]);
    c.playGame(d);
    Assert.assertEquals("\nGame won without a scar!",
        out.toString().substring(out.toString().length() - 25));
  }

  /**
   * To test controller when player moves,shoots and injures Otyugh.
   */
  @Test
  public void injuresOtyugh() {
    input = new StringReader("m e m e s e 1 q ");
    out = new StringBuilder();
    c = new PlayerController(input, out);
    Dungeon d = new KruskalDungeon(6, 6, 4, true, 80, 1, 0);
    //System.out.println(d.toString());
    //System.out.println(d.getEnd()[0]+" "+d.getEnd()[1]);
    c.playGame(d);
    Assert.assertEquals("Target Injured but still lives...\n"
            + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
            + "\n" + "Getting a Strong pungent Scent ! Stay alert\n" + "Current location :2 5\n"
            + "\n"
            + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
            + "Quitting game...",
        out.toString().substring(out.toString().length() - 281));
  }

  /**
   * To test controller when player moves,shoots and Misses Otyugh.
   */
  @Test
  public void shootMissOtyugh() {
    input = new StringReader("m e m e s w 1 q ");
    out = new StringBuilder();
    c = new PlayerController(input, out);
    Dungeon d = new KruskalDungeon(6, 6, 4, true, 80, 1, 0);
    //System.out.println(d.toString());
    //System.out.println(d.getEnd()[0]+" "+d.getEnd()[1]);
    c.playGame(d);
    Assert.assertEquals("Missed the target.\n"
            + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
            + "\n" + "Getting a Strong pungent Scent ! Stay alert\n" + "Current location :2 5\n"
            + "\n"
            + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
            + "Quitting game...",
        out.toString().substring(out.toString().length() - 266));
  }


  /**
   * To test controller when checking player description at the moment.
   */
  @Test
  public void playerDescription() {
    input = new StringReader("i q");
    out = new StringBuilder();
    c = new PlayerController(input, out);
    Dungeon d = new KruskalDungeon(6, 6, 4, true, 80, 1, -1);
    c.playGame(d);
    Assert.assertEquals(commonOut
            + "Name: Arnold treasures= [] Arrows= 3 location= 1 1\n"
            + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
            + "\n" + "Current location :1 1\n" + "1 Arrows available\n"
            + "\nWhat do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)"
            + "\t\n" + "Quitting game...",
        out.toString());
  }

  /**
   * To test controller when quitting the game.
   */
  @Test
  public void testQuit() {
    input = new StringReader("q");
    out = new StringBuilder();
    c = new PlayerController(input, out);
    Dungeon d = new KruskalDungeon(6, 6, 4, true, 80, 1, -1);
    c.playGame(d);
    Assert.assertEquals(commonOut
            + "Quitting game...",
        out.toString());
  }

  /**
   * To test controller when choosing an invalid option.
   */
  @Test
  public void testInvalidMainOption() {
    input = new StringReader("e q");
    out = new StringBuilder();
    c = new PlayerController(input, out);
    Dungeon d = new KruskalDungeon(6, 6, 4, true, 80, 1, -1);
    c.playGame(d);
    Assert.assertEquals(commonOut + "Illegal Choice. Choose from available options.\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\n" + "Current location :1 1\n" + "1 Arrows available\n" + "\n"
        + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
        + "Quitting game...", out.toString());
  }

  /**
   * To test controller when picking arrows when available.
   */
  @Test
  public void testPickingArrowValid() {
    input = new StringReader("p a q");
    out = new StringBuilder();
    c = new PlayerController(input, out);
    Dungeon d = new KruskalDungeon(6, 6, 4, true, 80, 1, -1);
    c.playGame(d);
    Assert.assertEquals(commonOut + "What do you want to pick ?(Treasure: T Arrows: A)\n"
        + "1 Arrows available\n"
        + "\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\n" + "Current location :1 1\n" + "\n"
        + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
        + "Quitting game...", out.toString());
  }

  /**
   * To test Controller when picking arrow/Treasure when not available.
   */
  @Test
  public void testPickingInvalid() {
    input = new StringReader(" p q");
    out = new StringBuilder();
    c = new PlayerController(input, out);
    Dungeon d = new KruskalDungeon(6, 6, 4, true, 30, 1, -1);
    c.playGame(d);
    Assert.assertEquals("\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\t\t\tWelcome to the Dungeon!\n"
        + "\tLets see If you can find a way out of it...\n"
        + "Destination :2\t3\n"
        + "You are in a cave\n"
        + "         ⇅  \n"
        + "⇆⮹⇆⮹⇆⮹ ⮹⇆⮹⇆⮹⇆\n"
        + " ⇅     ⇅   ⇅\n"
        + "\n"
        + " ⮹⇆█ ⮹⇆⮹ ⮹ ⮹ \n"
        + " ⇅ ⇅     ⇅  \n"
        + "\n"
        + "⇆⮹ ⮹⇆⮹ ⮹⇆⮹⇆⮹⇆\n"
        + "     ⇅ ⇅ ⇅ ⇅\n"
        + "\n"
        + "⇆⮹⇆⮹⇆⮹ ⮹ ⮹⇆⮹⇆\n"
        + "   ⇅   ⇅    \n"
        + "\n"
        + "⇆⮹ ⮹⇆⮹⇆⮹⇆⮹⇆⮹⇆\n"
        + "   ⇅   ⇅    \n"
        + "\n"
        + "⇆⮹ ⮹⇆⮹⇆⮹⇆⮹⇆⮹⇆\n"
        + "         ⇅  \n"
        + "Starting at\t1 1\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\n"
        + "Current location :1 1\n"
        + "\nWhat do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
        + "Nothing to pick\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\n"
        + "Current location :1 1\n"
        + "\n"
        + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
        + "Quitting game...", out.toString());
  }

  /**
   * Test controller when shooting in blocked direction.
   */
  @Test
  public void shootBlocked() {
    input = new StringReader("S W 2 q");
    out = new StringBuilder();
    c = new PlayerController(input, out);
    Dungeon d = new KruskalDungeon(6, 6, 4, true, 80, 1, -1);
    c.playGame(d);
    Assert.assertEquals(commonOut + "Please provide the direction to shoot an"
        + " arrow S  W \t\n" + " Enter the distance the arrow must travel:\t\n"
        + "Shooting in the direction not possibleMissed the target.\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\n" + "Current location :1 1\n" + "1 Arrows available\n" + "\n"
        + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
        + "Quitting game...", out.toString());
  }


  /**
   * To test controller when treasure is available and up for picking.
   */
  @Test
  public void treasurePickAvailable() {
    input = new StringReader("m n m w p t q");
    out = new StringBuilder();
    c = new PlayerController(input, out);

    Dungeon d = new KruskalDungeon(6, 8, 4, true, 80, 1, -1);
    c.playGame(d);
    Assert.assertEquals("\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\t\t\tWelcome to the Dungeon!\n" + "\tLets see If you can find a way out of it...\n"
        + "Destination :0\t3\n" + "You are in a cave\n" + "   ⇅ ⇅ ⇅ ⇅ ⇅   ⇅\n"
        + " ⮹ ⮹ ⮹⇆⮹⇆⮹⇆⮹⇆⮹⇆⮹ \n" + " ⇅   ⇅ ⇅     ⇅  \n" + "\n" + " ⮹ ⮹⇆⮹ ⮹⇆⮹ ⮹⇆⮹⇆⮹ \n"
        + " ⇅     ⇅   ⇅ ⇅ ⇅\n" + "\n" + "⇆⮹⇆⮹⇆⮹⇆⮹ ⮹ ⮹ ⮹ ⮹⇆\n" + "   ⇅     ⇅   ⇅  \n" + "\n"
        + " ⮹⇆⮹⇆⮹⇆⮹⇆⮹ ⮹ ⮹ ⮹ \n" + "   ⇅ ⇅ ⇅ ⇅ ⇅   ⇅\n" + "\n" + " ⮹⇆⮹ ⮹ ⮹⇆⮹ ⮹⇆⮹⇆⮹ \n"
        + "   ⇅   ⇅   ⇅ ⇅  \n" + "\n" + " ⮹⇆█ ⮹⇆⮹ ⮹⇆⮹ ⮹ ⮹ \n" + "   ⇅ ⇅ ⇅ ⇅ ⇅   ⇅\n"
        + "Starting at\t5 1\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\n" + "Current location :5 1\n" + "\n"
        + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
        + "Possible moves : N  S  W \n"
        + "Where to next ?\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\n"
        + "Current location :4 1\n"
        + "\n"
        + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
        + "Possible moves : N  S  W \n"
        + "Where to next ?\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\n" + "Current location :4 0\n" + "Available Treasures:RUBY\t\n" + "\n"
        + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
        + "What do you want to pick ?(Treasure: T Arrows: A)\n"
        + "Available Treasures:RUBY\t\n" + "\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\n" + "Current location :4 0\n" + "\n"
        + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
        + "Quitting game...", out.toString());

  }

  /**
   * Test the controller by trying to shoot with no arrows.
   */
  @Test
  public void testShootNoArrows() {
    input = new StringReader("s s 1 s s 1 s s 1 s q");
    out = new StringBuilder();
    c = new PlayerController(input, out);
    Dungeon d = new KruskalDungeon(6, 6, 4, true, 80, 1, -1);
    c.playGame(d);
    Assert.assertEquals(commonOut
        + "Please provide the direction to shoot an arrow S  W \t\n"
        + " Enter the distance the arrow must travel:\tMissed the target.\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\n"
        + "Current location :1 1\n"
        + "1 Arrows available\n"
        + "\n"
        + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
        + "Please provide the direction to shoot an arrow S  W \t\n"
        + " Enter the distance the arrow must travel:\tMissed the target.\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\n" + "Current location :1 1\n" + "1 Arrows available\n" + "\n"
        + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
        + "Please provide the direction to shoot an arrow S  W \t\n"
        + " Enter the distance the arrow must travel:\tMissed the target.\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\n" + "Current location :1 1\n" + "1 Arrows available\n" + "\n"
        + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
        + "No Arrows to shoot! Better escape...\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\n"
        + "Current location :1 1\n" + "1 Arrows available\n" + "\n"
        + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
        + "Quitting game...", out.toString());
  }

  /**
   * to test the controller with invalid Direction while shooting.
   */
  @Test
  public void testShootingInvalidDirection() {
    input = new StringReader("s a q");
    out = new StringBuilder();
    c = new PlayerController(input, out);
    Dungeon d = new KruskalDungeon(6, 6, 4, true, 80, 1, -1);
    c.playGame(d);
    Assert.assertEquals(commonOut + "Please provide the direction to shoot an arrow S  W \t\n"
        + "Not a valid direction...\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\n" + "Current location :1 1\n" + "1 Arrows available\n" + "\n"
        + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
        + "Quitting game...", out.toString());
  }

  /**
   * to test the controller with distance beyond reach while shooting.
   */
  @Test
  public void testShootingBeyondReach() {
    input = new StringReader("s w 4 q");
    out = new StringBuilder();
    c = new PlayerController(input, out);
    Dungeon d = new KruskalDungeon(6, 6, 4, true, 80, 1, -1);
    c.playGame(d);
    Assert.assertEquals(commonOut + "Please provide the direction to shoot an arrow S  W \t\n"
        + " Enter the distance the arrow must travel:\t\n"
        + "Target out of reach...\n"
        + "Shooting in the direction not possibleMissed the target.\n"
        + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n"
        + "\n" + "Current location :1 1\n" + "1 Arrows available\n" + "\n"
        + "What do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: Quit)\t\n"
        + "Quitting game...", out.toString());
  }


}


