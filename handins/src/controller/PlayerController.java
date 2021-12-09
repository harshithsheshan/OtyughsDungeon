package controller;

import dungeon.Dungeon;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Controller class to connect the model with the user commands given in the console. Takes the
 * input from the text based view and performs corresponding functions in the model.
 */
public class PlayerController implements Controller {

  private final Appendable out;
  private final Readable input;

  /**
   * Constructor for the controller. Takes in the appendable and readable to be initialized which
   * act as the view from which user input is taken and the output is displayed.
   *
   * @param in  the source to read from
   * @param out the target to print to
   */
  public PlayerController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    this.input = in;
  }

  /**
   * To generate a line separator in the appendable. Just used for separating parts of the output
   * into chunks for easier understanding and visual effect.
   */
  private void line() {
    try {
      out.append("\n").append("―".repeat(80)).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append Failed");
    }
  }

  /**
   * To check if the game is over i.e. after a move to check if the monster ate the player or
   * did the player manage to escape. Acts as a determinant for winning when a player enters the
   * end node when the dungeon is injured.
   *
   * @param d Dungeon object used for referencing
   * @return 1 if game is over and false if It's still on
   */
  private int checkGameOver(Dungeon d) {
    return d.isGameOver(1);
  }

  /**
   * To add comments and declare the result if the move is the final move. Used to add
   * the final comments to the output view upon game completion or Game Over condition.
   *
   * @param d Dungeon object for reference.
   */
  private void addFinalMoveComments(Dungeon d) {
    try {
      if (checkGameOver(d) == 1) {
        out.append("\nManaged to escape the stinker !\n Game won");
      } else if (checkGameOver(d) == 2) {
        out.append("\nGot eaten by the monster at the end !\n Game Over");
      } else {
        out.append("\nGame won without a scar!");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append Failed");
    }
  }

  /**
   * To check the status of the game and return if the game is over or not and adding comments.
   * Used to add comments to the view after each move, pickup or shoot.
   *
   * @param d Dungeon object
   * @return true for game is over and false for game is on.
   */
  private boolean addComments(Dungeon d) {
    try {
      if (checkGameOver(d) == 1) {
        out.append("\nManaged to escape the stinker !");
        return false;
      } else if (checkGameOver(d) == 2) {
        out.append("\nGot eaten by the monster at the end !\n Game Over");
        return true;
      } else {
        return false;
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed");
    }
  }


  /**
   * Method to move the player by asking the user for his choice of directions. Uses the direction
   * user provides in the view and the distance to call the method in the model to shoot.
   *
   * @param d    Dungeon object
   * @param scan scanner object to take user input
   * @return true if the
   */
  private boolean move(Dungeon d, Scanner scan) {
    char moveDirection;
    try {

      //out.append("\nPungency:\t").append(String.valueOf(d.scanOtyugh()));

      out.append("\nPossible moves :").append(d.getMovable());
      out.append("\nWhere to next ?");
      moveDirection = scan.next().charAt(0);
      //System.out.println(moveDirection);

      switch (moveDirection) {
        case 'N':
        case 'n':
          if (d.movePlayerNorth()) {
            addFinalMoveComments(d);
            return true;
          } else {
            return addComments(d);
          }
        case 'S':
        case 's':
          if (d.movePlayerSouth()) {
            addFinalMoveComments(d);
            return true;
          } else {
            return addComments(d);
          }
        case 'W':
        case 'w':
          if (d.movePlayerWest()) {
            addFinalMoveComments(d);
            return true;
          } else {
            return addComments(d);
          }

        case 'E':
        case 'e':
          if (d.movePlayerEast()) {
            addFinalMoveComments(d);
            return true;
          } else {
            return addComments(d);
          }
        default:
          out.append("Move no possible");
          return false;
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed");
    }
  }

  /**
   * To Check for pungency from the current location of the player. When a player moves to a new
   * location it calls the method in the model to get the pungency of the node and displays the
   * same in the view.
   *
   * @param d Model
   */
  private void sniff(Dungeon d) {
    try {
      int pungency = d.scanOtyugh();
      if (pungency == 2) {
        out.append("\nGetting a Strong pungent Scent ! Stay alert");
      } else if (pungency == 1) {
        out.append("\nGetting a faint  Scent !");
      }
    } catch (IOException e) {
      throw new IllegalStateException("append failed");
    }
  }

  /**
   * Menu of shoot which allows shooting specifying the direction and distance. Used to give and
   * take user choice of direction and distance when shoot option is selected.
   *
   * @param d    Model
   * @param scan Scanner from where input is taken
   */
  private void shoot(Dungeon d, Scanner scan) {
    try {
      if (d.getPlayerArrows() <= 0) {
        out.append("\nNo Arrows to shoot! Better escape...");
        return;
      }
      out.append(
          "\nPlease provide the direction to shoot an arrow").append(d.getMovable()).append("\t");
      char direction = scan.next().charAt(0);
      direction = Character.toUpperCase(direction);
      if (!("N S W E").contains(Character.toString(direction))) {
        out.append("\nNot a valid direction...");
        return;
      }
      if (!d.getMovable().contains(Character.toString(direction))) {
        out.append("\nDirection chosen is blocked. ");
        return;
      }
      out.append("\n Enter the distance the arrow must travel:\t");
      int distance = scan.nextInt();
      if (distance < 0 || distance > 2) {
        out.append("\nTarget out of reach...");
      }
      int[] res = new int[2];
      try {
        res = d.shoot(direction, distance);
      } catch (IllegalStateException ise) {
        out.append("\nShooting in the direction not possible");
      }
      if (res[0] == 1) {
        out.append("\nTarget Injured but still lives...");
      } else if (res[0] == 2) {
        out.append("Target Killed! Well Done...");
      } else {
        out.append("Missed the target.");
      }

    } catch (IOException e) {
      throw new IllegalStateException("Append Failed");
    }
  }

  /**
   * Method which enables to pick objects(Treasures / Arrows) from the location. Used to give and
   * take user choice of what to pick when pick option is selected.
   *
   * @param d    Model
   * @param scan Scanner object
   */
  private void pickup(Dungeon d, Scanner scan) {
    try {
      if (d.getContents().length() == 0) {
        out.append("\nNothing to pick");
        return;
      }
      out.append("\nWhat do you want to pick ?(Treasure: T Arrows: A)\n");
      out.append(d.getContents());
      char choice;
      choice = scan.next().charAt(0);
      switch (choice) {
        case 't':
        case 'T':
          if (!d.pickTreasure()) {
            out.append("\nNo Treasure to pick");
          }
          break;
        case 'a':
        case 'A':
          if (!d.pickArrows()) {
            out.append("\nNo Arrows to pick");
          }
          break;
        default:
          out.append("Invalid choice");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed");
    }
  }

  /**
   * Returns the description of the player which gives the current stats of the player. Calls the
   * method in the model which gets the player description from the model and adds it to the view.
   *
   * @param d model.
   */
  private void playerStat(Dungeon d) {
    try {
      out.append("\n").append(d.describePlayer());
    } catch (IOException e) {
      throw new IllegalStateException("Append Failed");
    }
  }

  /**
   * Method which describes the node with details of the contents.  Calls the method in the model
   * which gets the node Description from the model and adds it to the view.
   *
   * @param d Model
   */
  private void describeNode(Dungeon d) {
    try {
      out.append("\nCurrent location :").append(String.valueOf(d.getPlayerLocation()[0]))
          .append(" ")
          .append(String.valueOf(d.getPlayerLocation()[1]));
      out.append("\n").append(d.getContents());

    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }
  }

  /**
   * To a play a game of Dungeon. we use the following method which lets you play till the
   * game reaches the end i.e. player dies or reaches end.
   *
   * @param d a non-null KruskalDungeon Model
   */
  @Override
  public void playGame(Dungeon d) {
    if (d == null) {
      throw new IllegalArgumentException();
    }
    Scanner scan = new Scanner(input);
    try {
      //out.append(d.getOtyughs());
      line();
      //Welcome message to the start of the application.
      out.append(
          "\t\t\tWelcome to the Dungeon!\n\tLets see If you can find a way out of it...");
      //Printing out the Destination location.
      out.append("\nDestination :").append(String.valueOf(d.getEnd()[0])).append("\t")
          .append(String.valueOf(d.getEnd()[1]));
      out.append("\nYou are in a cave");
      out.append(d.toString());
      int[] start = d.getStart();
      //Printing the starting location of the player.
      out.append("\nStarting at\t").append(String.valueOf(start[0])).append(" ")
          .append(String.valueOf(start[1]));
      char choice;
      //Loop till end of the game i.e. player wins or gets eaten.
      while (checkGameOver(d) != 2) {
        try {
          line();
          //To check the surrounding of the player to sniff out the otyughs around him.
          sniff(d);
          //To describe the node in which the player is with details of the contents of the
          // location.
          describeNode(d);
          //To ask the user his choice of task to perform.
          out.append(
              "\nWhat do you want to do ?( M: Move, P: Pickup, S: Shoot, I: Player Stats Q: "
                  + "Quit)\t");
          choice = scan.next().charAt(0);
          switch (choice) {
            case 'm':
            case 'M':
              // To move the player
              if (move(d, scan)) {
                return;
              }
              break;
            case 'p':
            case 'P':
              //To make the player pickup the contents of the location node.
              pickup(d, scan);
              break;
            case 's':
            case 'S':
              //To make the player shoot in a direction towards a monster.
              shoot(d, scan);
              break;
            case 'I':
            case 'i':
              //To display the player stats which contain the name, contents of backpack and
              // location.
              playerStat(d);
              break;
            case 'Q':
            case 'q':
              out.append("\nQuitting game...");
              return;
            default:
              out.append("\nIllegal Choice. Choose from available options.");
          }

        } catch (IllegalArgumentException e) {
          out.append("\nMove not possible.");
        } catch (NoSuchElementException nse) {
          try {
            out.append("\nIllegal Direction");
          } catch (IOException e) {
            throw new IllegalStateException("Append failed");
          }
        } catch (IOException e) {
          throw new IllegalStateException("Append failed");
        }
      }
    } catch (NoSuchElementException nse) {
      try {
        out.append("\nIllegal Direction");
      } catch (IOException e) {
        throw new IllegalStateException("Append failed");
      }
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }


  }
}
