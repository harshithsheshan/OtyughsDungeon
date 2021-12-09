package controller;

import dungeon.Dungeon;

/**
 * Controller to manage the game of Dungeon. It acts as the interface between a player and the
 * model. It is responsible to take input from the screen and perform corresponding moves in
 * the dungeon.
 */
public interface guiControllerInterface {


  /**
   * The play game function implements the game and starts the game with player at the start
   * of the dungeon. It is used to go about the gameplay of a game similar to pacman.
   */
  void playGame();

  /**
   * The method which initializes the model with arguments and creates a dungeon. the dungeon takes
   * 6 arguments to create a model of the dungeon.
   *
   * @param parseInt         rows
   * @param parseInt1        cols
   * @param parseInt2        interconnectivity
   * @param wrappingArgument wrapping
   * @param parseInt3        treasure percentage
   * @param parseInt4        Number of otyughs
   */
  void makeDungeon(int parseInt, int parseInt1, int parseInt2, boolean wrappingArgument,
                   int parseInt3, int parseInt4);

  /**
   * Method which returns the movable locations as a text for a particular location.
   * It finds the openings in the cave and returns a string which have the directions as characters
   * in it.
   *
   * @param loc Location id for which the movable is requested
   * @return String containing Directions.
   */
  String getMovable(int loc);


  /**
   * Method which returns the movable locations as a text for a particular location.
   * It finds the openings in the cave and returns a string which have the directions as characters
   * in it.
   *
   * @param loc Location for which the movable is requested
   * @return String containing Directions.
   */
  String getMovable(int[] loc);

  /**
   * Method to move the player from one node to the other in the dungeon model. The controller
   * takes input from the user using the view and then calls the corresponding method in the model
   * to move the player in the direction specified by the user.
   *
   * @param n gives the direction to move in.
   */
  void move(String n);

  /**
   * Method to shoot an arrow in the dungeon model. The controller takes input from the user using
   * the view and calls the corresponding shoot method in the model to shoot the player in the
   * direction specified.
   *
   * @param n Direction
   * @param d Distance
   */
  void shoot(String n, int d);

  /**
   * Method to update the information in the information Panel of the view with realtime
   * data from the model. It takes the information from the model and passes it onto the view to
   * be displayed to the user of the game.
   */
  void updateInformation();

  /**
   * Method to make the player pick an arrow from a location when it is available in the Model.
   * It takes input from the user using the view and calls the corresponding method in the model
   * to pick an arrow.
   */
  void pickArrow();

  /**
   * Method to make the player pick a Treasure from a location when it is available in the Model.
   *  It takes input from the user using the view and calls the corresponding method in the model
   *  to pick a Treasure from the node.
   */
  void pickTreasure();

  /**
   * Method to shoot a poisoned arrow in the direction specified by the player in the model.
   * The controller takes input from the user using view and calls the corresponding shoot method
   * in the model to shoot the player using a poisoned arrow in the direction specified.
   * @param n direction
   * @param d distance
   */
  void poisonShoot(String n, int d);

  /**
   * Method to restart the game with the same dungeon. Takes user input from the user and restarts
   * the game from the start using the same arguments that user provided last time.
   */
  void restartSameDungeon();

  /**
   * Method to restart the game by taking arguments again.Takes user input from the user and
   * restarts the game from the start taking in new set of arguments for the dungeon.
   */
  void restartNewDungeon();

  /**
   * Quit game when user wants to quit the game in the middle. When called the game shuts down
   * without a result for the game in the state it is.
   */
  void quit();
}
