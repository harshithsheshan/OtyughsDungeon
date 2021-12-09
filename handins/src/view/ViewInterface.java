package view;

import controller.ViewController;
import controller.guiControllerInterface;

import java.util.List;

/**
 * A view for Dungeon: display the Maze and provide visual interface
 * for users.
 */
public interface ViewInterface {

  /**
   * used to set the listener for the ciew.
   * @param listener listener reference.
   * @param newD to inform if its a new dungeon.
   */
  void setListener(guiControllerInterface listener, boolean newD);

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */
  void addClickListener(ViewController listener);

  /**
   * Method to Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Method to Make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * Method to Make the dungeon elements invisible .
   */
  void makeInvisible();

  /**
   * Methid to create a new Dungeon model by taking arguments from the user.
   * @param playerLocationId int
   * @param movable String
   * @param pungency int
   * @param res boolean
   * @param hasPitNearby boolean
   */
  void markPLayer(int playerLocationId, String movable, int pungency, boolean res,
                  boolean hasPitNearby);


  /**
   * Method to update the information in the view using the data from the model regarding node and
   * player.
   */
  void updateInformation(int treasures, int arrows, int dTreasures, int dArrows);

  /**
   * Method to display a welcome message to the user.
   */
  void welcome();

  /**
   * Method to draw the dungeon in the view representing a grid of rows* cols.
   */
  void drawDungeon(List<String> movable);

  /**
   * Method to get the Directions of movable nodes from the current location.
   * @return String
   */
  String getDirection();

  /**
   * Method to add listener to the current view.
   * @param viewController controller
   */
  void addListener(ViewController viewController);

  /**
   * Method to unmark a player from the dungeon once he moves to a different dungeon.
   * @param playerLocationId Location
   * @param movable movable directions
   */
  void unmarkPlayer(int playerLocationId, String movable);

  /**
   * Method to create and initialise the control panel.
   */
  void createControlPanel();

  /**
   * Method To show the result of shooting to the user.
   */
  void notifyShootResult(int[] res);

  /**
   * Method to display the alert when there are no arrows to pick and player tries to pick arrow.
   */
  void notifyNoArrowToPick();

  /**
   * Method to display alert when there is no treasure to pick and player tries to pick treasure.
   */
  void notifyNoTreasureToPick();

  /**
   * Method to notify the result of the game weather its a win or a Game Over condition.
   * @param s result text.
   * @param win flag for win.
   */
  void notifyResult(String s, boolean win);

  /**
   * Method to disply the results of a poison shot.
   */
  void notifyPoisonShotResult(int[] poisonShot);

  /**
   * Method to notify when player tries to move in a direction not possible.
   */
  void notifyMoveNotPossible();

  /**
   * Method to rremove visibility of a node in the dungeon.
   */
  void removeVisibility();

  /**
   * Method to ask user for choice and return to the listener regarding pit.
   */
  boolean askUserChoicePit();

  /**
   * Method to notify the user when the player is sucked into the pit and game is over.
   */
  void notifySuckedIntoPit();

  /**
   * Method to notify when a thief is encountered.
   */
  void notifyThief();

  /**
   * Method to notify when the ninja and player clash in the same dungeon.
   */
  void notifyNinja();

  /**
   * Method to notify the user when player defeats the ninja.
   */
  void notifyPlayerDefeatsNinja();

  /**
   * Method to notify the user when Ninja kills the player and Game is over.
   */
  void notifyNinjaWins();

  /**
   * Method to notify the user when they try to shoot with no arrow left to shoot.
   */
  void notifyNoArrowsToShoot();
}

