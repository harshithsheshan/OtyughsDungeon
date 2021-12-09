package view;

import controller.GuiControllerInterface;
import controller.ViewController;

import java.io.IOException;
import java.util.List;

/**
 * MockView class is a mock view which is used to check the controller. This just appends the output
 * into an appendable to compare and test.
 */
public class MockView implements ViewInterface {

  GuiControllerInterface listener;
  Appendable out;

  /**
   * Constructor to initialize the appendable to check the output against.
   *
   * @param out appendable object
   */
  public MockView(Appendable out) {
    this.out = out;
  }

  /**
   * used to set the listener for the view.
   *
   * @param listener listener reference.
   * @param newD     to inform if its a new dungeon.
   */
  @Override
  public void setListener(GuiControllerInterface listener, boolean newD) {
    this.listener = listener;
    try {
      out.append("setListener");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */
  @Override
  public void addClickListener(ViewController listener) {
    try {
      out.append("addClickListener");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to Refresh the view to reflect any changes in the game state.
   */
  @Override
  public void refresh() {
    try {
      out.append("refresh");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to Make the view visible to start the game session.
   */
  @Override
  public void makeVisible() {
    try {
      out.append("makeVisible");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to Make the dungeon elements invisible .
   */
  @Override
  public void makeInvisible() {
    try {
      out.append("makeInvisible");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Methid to create a new Dungeon model by taking arguments from the user.
   *
   * @param playerLocationId int
   * @param movable          String
   * @param pungency         int
   * @param res              boolean
   * @param hasPitNearby     boolean
   */
  @Override
  public void markPLayer(int playerLocationId, String movable, int pungency, boolean res,
                         boolean hasPitNearby) {
    try {
      out.append("markPLayer");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to update the information in the view using the data from the model regarding node and
   * player.
   */
  @Override
  public void updateInformation(int treasures, int arrows, int dTreasures, int dArrows) {
    try {
      out.append("updateInformation");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to display a welcome message to the user.
   */
  @Override
  public void welcome() {
    try {
      out.append("welcome");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to draw the dungeon in the view representing a grid of rows* cols.
   */
  @Override
  public void drawDungeon(List<String> movable) {
    try {
      out.append("drawDungeon");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to get the Directions of movable nodes from the current location.
   */
  @Override
  public String getDirection() {
    try {
      out.append("getDirection");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
    return null;
  }

  /**
   * Method to add listener to the current view.
   */
  @Override
  public void addListener(ViewController viewController) {
    try {
      out.append("addListener");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }

  }

  /**
   * Method to unmark a player from the dungeon once he moves to a different dungeon.
   *
   * @param playerLocationId Location
   * @param movable          movable directions
   */
  @Override
  public void unmarkPlayer(int playerLocationId, String movable) {
    try {
      out.append("unmarkPlayer");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to create and initialise the control panel.
   */
  @Override
  public void createControlPanel() {
    try {
      out.append("createControlPanel");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method To show the result of shooting to the user.
   */
  @Override
  public void notifyShootResult(int[] res) {
    try {
      out.append("notifyShootResult");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to display the alert when there are no arrows to pick and player tries to pick arrow.
   */
  @Override
  public void notifyNoArrowToPick() {
    try {
      out.append("notifyNoArrowToPick");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to display alert when there is no treasure to pick and player tries to pick treasure.
   */
  @Override
  public void notifyNoTreasureToPick() {
    try {
      out.append("notifyNoTreasureToPick");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to notify the result of the game weather its a win or a Game Over condition.
   *
   * @param s   result text.
   * @param win flag for win.
   */
  @Override
  public void notifyResult(String s, boolean win) {
    try {
      out.append("notifyResult");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to disply the results of a poison shot.
   */
  @Override
  public void notifyPoisonShotResult(int[] poisonShot) {
    try {
      out.append("notifyPoisonShotResult");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to notify when player tries to move in a direction not possible.
   */
  @Override
  public void notifyMoveNotPossible() {
    try {
      out.append("notifyMoveNotPossible");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to remove visibility of a node in the dungeon.
   */
  @Override
  public void removeVisibility() {
    try {
      out.append("removeVisibility");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to ask user for choice and return to the listener regarding pit.
   */
  @Override
  public boolean askUserChoicePit() {
    try {
      out.append("askUserChoicePit");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
    return false;
  }

  /**
   * Method to notify the user when the player is sucked into the pit and game is over.
   */
  @Override
  public void notifySuckedIntoPit() {
    try {
      out.append("notifySuckedIntoPit");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to notify when a thief is encountered.
   */
  @Override
  public void notifyThief() {
    try {
      out.append("notifyThief");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to notify when the ninja and player clash in the same dungeon.
   */
  @Override
  public void notifyNinja() {
    try {
      out.append("notifyNinja");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to notify the user when player defeats the ninja.
   */
  @Override
  public void notifyPlayerDefeatsNinja() {
    try {
      out.append("notifyPlayerDefeatsNinja");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to notify the user when Ninja kills the player and Game is over.
   */
  @Override
  public void notifyNinjaWins() {
    try {
      out.append("notifyNinjaWins");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to notify the user when they try to shoot with no arrow left to shoot.
   */
  @Override
  public void notifyNoArrowsToShoot() {
    try {
      out.append("notifyNoArrowsToShoot");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }


}
