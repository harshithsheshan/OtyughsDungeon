package controller;

import dungeon.Dungeon;
import dungeon.KruskalDungeon;
import view.DungeonView;
import view.ViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class for the controller with a Graphical View interface. Uses User input from the View and
 * performs corresponding operations in the model and displays the result back to the user via the
 * view.
 */
public class ViewController implements GuiControllerInterface {

  private Dungeon model;
  private ViewInterface view;
  private int row;
  private int col;
  private int iConnect;
  private boolean wrapping;
  private int treasureP;
  private int nOtyugh;

  /**
   * Constructor used to initialise the fields of the controller which is called when an object
   * of the type is initialised.
   *
   * @param view Gui Interface
   */
  public ViewController(ViewInterface view) {
    if (view == null) {
      throw new IllegalArgumentException();
    }
    this.view = view;
    this.view.makeVisible();
    view.addClickListener(this);
  }

  /**
   * Method which creates the basic layout of the dungeon with the specified number of rows and
   * columns when the view is created.
   */
  private void makeDungeon() {
    int[] loc = new int[2];
    List<String> movable = new ArrayList<>();
    for (int i = 0; i <= model.getRows(); i++) {
      for (int j = 0; j <= model.getCols(); j++) {
        loc[0] = i;
        loc[1] = j;
        movable.add(model.getMovable(loc));
      }
    }
    view.drawDungeon(movable);
    view.makeInvisible();
    view.markPLayer(model.getPlayerLocationId(), model.getMovable(), model.scanOtyugh(), false,
        model.hasPitNearby());
    view.addListener(this);
  }


  @Override
  public void makeDungeon(int row, int col, int inter, boolean wrappingArgument,
                          int treasure, int otyugh) {
    this.row = row;
    this.col = col;
    this.iConnect = inter;
    this.wrapping = wrappingArgument;
    this.treasureP = treasure;
    this.nOtyugh = otyugh;
    this.model = new KruskalDungeon(row, col, inter, wrappingArgument, treasure, otyugh, 1);
    view = new DungeonView(row, col);
    makeDungeon();
    updateInformation();
    view.addListener(this);
    view.createControlPanel();
  }

  /**
   * Method which returns the movable locations as a text for a particular location.
   *
   * @param loc Location for which the movable is requested
   * @return String containing Directions.
   */
  @Override
  public String getMovable(int loc) {
    return model.getMovable(loc);
  }


  /**
   * Method which returns the movable locations as a text for a particular location.
   *
   * @param loc Location for which the movable is requested
   * @return String containing Directions.
   */
  @Override
  public String getMovable(int[] loc) {
    return model.getMovable(loc);
  }

  /**
   * The play game function implements the game and starts the game with player at the start
   * of the dungeon.
   */
  @Override
  public void playGame() {
    //makeDungeon();
    //view.describe(model.describePlayer(), model.getContents());

  }



  @Override
  public void move(String n) {
    Random rand = new Random();
    boolean res = false;
    if (model.getMovable().contains(n)) {
      view.unmarkPlayer(model.getPlayerLocationId(), model.getMovable());
      //System.out.println("End :" + model.getEnd()[0] + " " + model.getEnd()[1]);
      if (model.movePlayer(n)) {
        res = true;
        if (model.isGameOver(rand.nextInt()) == 1) {
          view.notifyResult("Managed to escape the stinker !\n Game won!", true);
          System.exit(1);
        } else if (model.isGameOver(rand.nextInt()) == 2) {
          view.notifyResult("Got eaten by the monster at the end !\n Game Over", false);
          System.exit(1);
        } else {
          view.notifyResult("Game Won!", true);
          System.exit(1);
        }
      } else {
        if (model.checkPit()) {
          if (model.getPlayerTreasures() > 0) {
            if (view.askUserChoicePit()) {
              model.bridgeThePit();
              model.removePit();
              updateInformation();
            } else {
              view.notifySuckedIntoPit();
            }
          } else {
            view.notifySuckedIntoPit();
          }
        }
        if (model.nodeHasNinja()) {
          view.notifyNinja();
          Random random = new Random();
          if (random.nextInt(2) == 1) {
            view.notifyPlayerDefeatsNinja();
          } else {
            view.notifyNinjaWins();
            System.exit(1);
          }
        }
        if (model.nodeHasThief()) {
          view.notifyThief();
          model.getRobbedByThief();
        }
      }
      view.markPLayer(model.getPlayerLocationId(), model.getMovable(), model.scanOtyugh(), res,
          model.hasPitNearby());

      updateInformation();
    } else {
      view.notifyMoveNotPossible();
    }
  }


  @Override
  public void shoot(String n, int d) {
    if (model.getPlayerArrows() > 0) {
      int[] res = model.shoot(n.charAt(0), d);

      view.notifyShootResult(res);
      updateInformation();
    } else {
      view.notifyNoArrowsToShoot();
    }
  }

  @Override
  public void updateInformation() {
    view.updateInformation(model.getPlayerTreasures(), model.getPlayerArrows(),
        model.getNodeTreasures(), model.getNodeArrows());
  }

  @Override
  public void pickArrow() {
    if (model.pickArrows()) {
      updateInformation();
    } else {
      view.notifyNoArrowToPick();
    }
    updateInformation();
  }

  @Override
  public void pickTreasure() {
    if (model.pickTreasure()) {
      updateInformation();
    } else {
      view.notifyNoTreasureToPick();
    }
    updateInformation();
  }

  @Override
  public void poisonShoot(String n, int d) {
    view.notifyPoisonShotResult(model.poisonShot(n.charAt(0), d));
    updateInformation();
  }

  @Override
  public void restartSameDungeon() {
    view.removeVisibility();
    makeDungeon(row, col, iConnect, wrapping, treasureP, nOtyugh);

  }

  @Override
  public void restartNewDungeon() {
    view.setListener(this, false);


  }

  @Override
  public void quit() {
    System.exit(0);
  }


}
