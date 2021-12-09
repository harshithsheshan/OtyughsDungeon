package controller;

import dungeon.Dungeon;

/**
 * Controller to manage the game of Dungeon. It acts as the interface between a player and the
 * model. It is responsible to take input from the screen and perform corresponding moves in
 * the dungeon.
 */
public interface Controller {

  /**
   * The play game function implements the game and starts the game with player at the start
   * of the dungeon.
   * @param d Dungeon model used.
   */
  void playGame(Dungeon d);

}
