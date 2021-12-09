package dungeon;

import controller.Controller;
import controller.PlayerController;
import controller.ViewController;
import controller.GuiControllerInterface;
import view.DungeonView;
import view.ViewInterface;

import java.io.InputStreamReader;

/**
 * The driver of the function which checks the arguments and decides weather to run the text based
 * or GUI game.
 */
public class ViewDriver {

  /**
   * Main method of the driver which is run when the program is run.
   *
   * @param args Program arguments
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      ViewInterface view = new DungeonView();
      GuiControllerInterface dController = new ViewController(view);
      view.setListener(dController, true);
      dController.playGame();
    } else {
      Readable input = new InputStreamReader(System.in);
      Appendable output = System.out;
      Controller c = new PlayerController(input, output);

      Dungeon dungeonWrapping;
      dungeonWrapping = new KruskalDungeon(Integer.parseInt(args[0]),
          Integer.parseInt(args[1]), Integer.parseInt(args[2]), Boolean.parseBoolean(args[3]),
          Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
      c.playGame(dungeonWrapping);
    }
  }
}
