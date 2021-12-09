package view;

import controller.GuiControllerInterface;
import controller.ViewController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;


/**
 * Class for the View which extends JFrame and has multiple Panels. It mainly has a main panel
 * is used to display the dungeon and an information panel which is used to display the
 * information corresponding to the dungeon.
 */
public class DungeonView extends JFrame implements ViewInterface {

  private JPanel main;
  private ControlPanel controls;
  private JPanel maze;
  private JLabel playerTreasure;
  private JLabel playerArrows;
  private JLabel nodeArrows;
  private JLabel nodeTreasures;
  private JLabel contents;
  private GuiControllerInterface listener;
  private JButton temp;
  private BufferedImage combined;
  private Graphics g;
  private Image iconA;
  private JFrame pitChoice;
  private JLabel pungency;
  private JLabel pitSense;
  private ImageIcon img;


  /**
   * Default constructor for the class which is used to create a dummy view initially.
   */
  public DungeonView() {
    super("Dungeon");
    this.setVisible(false);
    dispose();
  }

  /**
   * Parametrized constructor for the class which is used to create the actual dungeon by taking
   * user input from the argument JFrame.
   *
   * @param row  The number of rows in the dungeon model
   * @param cols The number of colums in the dungeon model
   */
  public DungeonView(int row, int cols) {
    super("Dungeon");
    if (row < 0 || cols < 0) {
      throw new IllegalArgumentException();
    }
    setLayout(new BorderLayout());
    welcome();
    JMenuBar menu = new JMenuBar();
    combined = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    menu.setSize(new Dimension(getWidth(), 40));
    JMenu restart = new JMenu("Restart");
    JMenuItem sameDungeon = new JMenuItem("Keep Arguments");
    JMenuItem newDungeon = new JMenuItem("New Arguments");
    restart.add(sameDungeon);
    sameDungeon.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.restartSameDungeon();
      }
    });
    menu.add(Box.createHorizontalGlue());
    newDungeon.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.restartNewDungeon();
      }
    });
    restart.add(newDungeon);
    menu.add(restart, ComponentOrientation.LEFT_TO_RIGHT);
    add(menu, BorderLayout.PAGE_START);
    main = new JPanel();
    main.setSize(600, 600);
    main.setBackground(Color.BLACK);
    this.setSize(800, 800);
    Dimension d = this.getSize();
    maze = new JPanel(new GridLayout(row, cols));
    maze.setBackground(Color.BLACK);
    int count = 1;
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < cols; j++) {
        JButton temp = new JButton(Integer.toString(count));
        temp.setPreferredSize(new Dimension(100, 100));

        maze.add(temp);

        count++;
      }
    }
    JPanel information = new JPanel();
    information.setPreferredSize(new Dimension(getWidth(), 100));
    information.setLayout(new GridLayout(1, 3));
    JLabel sensors = new JLabel();
    sensors.setLayout(new BoxLayout(sensors, BoxLayout.Y_AXIS));
    sensors.add(new JTextField("Sensors:"));
    loadImg("blank");
    pungency = new JLabel("No Smell", img, SwingConstants.LEFT);
    sensors.add(pungency);
    loadImg("blank");
    pitSense = new JLabel("No Pits Around", img, SwingConstants.LEFT);
    sensors.add(pitSense);
    JLabel backpack = new JLabel();
    backpack.setLayout(new BoxLayout(backpack, BoxLayout.Y_AXIS));
    backpack.add(new JTextField("Backpack:"));
    JLabel dungeonContents = new JLabel();
    dungeonContents.setLayout(new BoxLayout(dungeonContents, BoxLayout.Y_AXIS));
    dungeonContents.add(new JTextField("Dungeon Contents"));
    loadImg("gems");
    playerTreasure = new JLabel("X 0", img, SwingConstants.LEFT);
    nodeTreasures = new JLabel("X 0", img, SwingConstants.LEFT);
    backpack.add(playerTreasure);
    dungeonContents.add(nodeTreasures);
    loadImg("arrows");
    playerArrows = new JLabel("X 0", img, SwingConstants.LEFT);
    nodeArrows = new JLabel("X 0", img, SwingConstants.LEFT);
    backpack.add(playerArrows);
    dungeonContents.add(nodeArrows);
    information.add(backpack);
    information.add(dungeonContents);
    information.add(sensors);
    main.add(maze);
    JScrollPane scroll = new JScrollPane(main);
    add(scroll, BorderLayout.CENTER);
    add(information, BorderLayout.PAGE_END);
    pack();
    this.setVisible(true);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  /**
   * Method used to load image for displaying image as an icon for various alerts.
   *
   * @param load image name
   */
  private void loadImg(String load) {
    try {
      iconA = ImageIO.read(new File("res/images/" + load + ".png"));
    } catch (IOException e) {
      throw new IllegalStateException("Image not found");
    }
    img = new ImageIcon(iconA.getScaledInstance(30, 30, Image.SCALE_DEFAULT));
  }


  @Override
  public void setListener(GuiControllerInterface listener, boolean newD) {
    if (listener == null) {
      throw new IllegalArgumentException();
    }
    this.listener = listener;
    if (!newD) {
      controls.dispose();
      this.setVisible(false);
    }
    Argument argument = new Argument(listener);

  }


  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */
  @Override
  public void addClickListener(ViewController listener) {
    // Did not require the use of it.
  }


  private void markOtyugh(int loc) {
    temp = (JButton) maze.getComponent(loc - 1);
    String m = listener.getMovable(loc).replaceAll("\\s", "");
    overlay("otyugh", m);

    temp.setText("");
    temp.setHorizontalAlignment(SwingConstants.CENTER);
    temp.setVisible(true);
    temp.setToolTipText("Otyugh Location in the Dungeon");
  }

  /**
   * Refresh the view to reflect any changes in the game state. Used when the dungeon needs to be
   * repainted after a change in the game state in the model.
   */
  @Override
  public void refresh() {
    this.repaint();
  }

  /**
   * Make the view visible to start the game session. Makes the visibility known to all and is used
   * for debugging.
   */
  @Override
  public void makeVisible() {
    setVisible(true);
  }

  /**
   * Make the view invisible to start the game session. To make the dungeon invisible before the
   * start of the game.
   */
  @Override
  public void makeInvisible() {
    int n = 0;
    try {
      while (n < maze.getComponentCount()) {
        maze.getComponent(n).setVisible(false);
        n++;
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalStateException("Out of bounds");
    }

  }

  @Override
  public void unmarkPlayer(int playerLocationId, String movable) {
    if (playerLocationId < 0 || movable == null) {
      throw new IllegalArgumentException();
    }
    temp = (JButton) maze.getComponent(playerLocationId - 1);
    String m = movable.replaceAll("\\s", "");
    //System.out.println(n + " " + m);
    iconA = new ImageIcon("res/images/color-cells/" + m + ".png").getImage()
        .getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    temp.setText("");
    temp.setIcon(new ImageIcon(iconA));
    maze.getComponent(playerLocationId - 1).setVisible(true);
  }

  @Override
  public void createControlPanel() {
    controls = new ControlPanel(listener);
  }


  @Override
  public void notifyShootResult(int[] res) {
    if (res[0] < 0 || res[1] < 0) {
      throw new IllegalArgumentException();
    }
    ImageIcon icon = new ImageIcon("res/images/otyugh.png");
    if (res[0] == 1) {
      play("arrowStrike");
      play("screamOtyugh");
      markOtyugh(res[1]);
      JOptionPane.showMessageDialog(null, "Otyugh Injured ", "Alert",
          JOptionPane.INFORMATION_MESSAGE,
          icon);
    } else if (res[0] == 2) {
      play("arrowStrike");
      play("killed");
      removeOtyugh(res[1]);
      //JOptionPane.showMessageDialog(null, "Otyugh Killed");
      JOptionPane.showMessageDialog(null, "Otyugh Killed ", "Alert",
          JOptionPane.INFORMATION_MESSAGE,
          icon);
    } else {
      icon = new ImageIcon("res/images/missedTarget.png");
      play("missedTarget");
      JOptionPane.showMessageDialog(null, "Target Missed ", "Alert",
          JOptionPane.INFORMATION_MESSAGE,
          icon);
    }
  }

  /**
   * Method used to remove a dead otyugh . It is called when an otyugh is killed by the user
   * by shooting an arrow or a poisoned arrow.
   *
   * @param res location
   */
  private void removeOtyugh(int res) {
    if (res < 0) {
      throw new IllegalArgumentException();
    }
    temp = (JButton) maze.getComponent(res - 1);
    String m = listener.getMovable(res).replaceAll("\\s", "");
    //System.out.println(n + " " + m);
    iconA = new ImageIcon("res/images/color-cells/" + m + ".png").getImage()
        .getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    temp.setText("");
    temp.setIcon(new ImageIcon(iconA));
    maze.getComponent(res - 1).setVisible(true);
  }


  /**
   * Method used to play an audio clip when called. Used for effects during alerts and functionality
   * of the gameplay.
   *
   * @param sound the file name of the clip to be played.
   */
  private void play(String sound) {
    if (sound == null) {
      throw new IllegalArgumentException();
    }

    try {
      AudioInputStream audioInputStream =
          AudioSystem.getAudioInputStream(
              new File("res/sounds/" + sound + ".wav").getAbsoluteFile());
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.start();
    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
      throw new IllegalStateException("No audio file found");
    }
  }


  @Override
  public void notifyResult(String res, boolean win) {
    if (res == null) {
      throw new IllegalArgumentException();
    }
    try {
      if (win) {
        play("win");
        iconA = ImageIO.read(new File("res/images/win.png"));
      } else {
        play("gameOver");
        iconA = ImageIO.read(new File("res/images/eaten.png"));
      }
    } catch (IOException e) {
      throw new IllegalStateException("No image found");
    }
    img = new ImageIcon(iconA.getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    JOptionPane.showMessageDialog(null, res, "Game Result",
        JOptionPane.INFORMATION_MESSAGE,
        img);
    controls.dispose();
    controls.dispose();
    dispose();

  }

  @Override
  public void notifyPoisonShotResult(int[] poisonShot) {
    if (poisonShot[0] < 0 || poisonShot[1] < 0) {
      throw new IllegalArgumentException();
    }
    ImageIcon icon = new ImageIcon("res/images/otyugh.png");

    if (poisonShot[0] == 2) {
      play("arrowStrike");
      play("killedOtyugh");
      removeOtyugh(poisonShot[1]);
      JOptionPane.showMessageDialog(null,
          "Otyugh Killed with the poisoned Arrow ", "Alert",
          JOptionPane.INFORMATION_MESSAGE,
          icon);
    } else {
      icon = new ImageIcon("res/images/missedTarget.png");
      play("missedTarget");
      JOptionPane.showMessageDialog(null,
          "Target Missed. Lost 3 arrows...", "Alert",
          JOptionPane.INFORMATION_MESSAGE,
          icon);
    }
    listener.updateInformation();
  }

  @Override
  public void notifyMoveNotPossible() {
    JOptionPane.showMessageDialog(null, "No Opening in the direction",
        null, JOptionPane.ERROR_MESSAGE);

  }

  /**
   * Method to notify the pungency to the user. Uses thr information from the controller
   * to display the pungency and alert the player of the level of pungency.
   *
   * @param pungency level of pungency.
   */
  private void notifyPungency(int pungency) {
    if (pungency < 0 || pungency > 2) {
      throw new IllegalArgumentException();
    }
    if (pungency == 1) {
      play("stench");
      this.pungency.setText("Faint Smell");
      loadImg("stench01");
      this.pungency.setIcon(img);
    } else if (pungency == 2) {
      play("stench");
      loadImg("stench02");
      this.pungency.setText("Pungent Smell");
      this.pungency.setIcon(img);
    } else {
      this.pungency.setText("No Smell");
      loadImg("blank");
      this.pungency.setIcon(img);
    }
  }

  /**
   * To play alert sound for different alerts received during the gameplay.
   */
  private void playAlert() {
    try {
      AudioInputStream audioInputStream =
          AudioSystem.getAudioInputStream(new File("res/sounds/alert.wav").getAbsoluteFile());
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.start();
    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
      throw new IllegalStateException("No audio file found");
    }
  }

  @Override
  public void notifyNoArrowToPick() {

    JOptionPane.showMessageDialog(null, "No Arrow to pick", null, JOptionPane.ERROR_MESSAGE);
    playAlert();

  }

  @Override
  public void notifyNoTreasureToPick() {
    JOptionPane.showMessageDialog(null, "No Treasure to pick",null, JOptionPane.ERROR_MESSAGE);
    playAlert();
  }


  @Override
  public void markPLayer(int playerLocationId, String movable, int pungency, boolean res,
                         boolean hasPitNearby) {
    if (playerLocationId < 0 || movable == null || pungency < 0 || pungency > 2) {
      throw new IllegalArgumentException();
    }

    temp = (JButton) maze.getComponent(playerLocationId - 1);

    String m = movable.replaceAll("\\s", "");
    overlay("player", m);
    temp.setText("");
    temp.setHorizontalAlignment(SwingConstants.CENTER);
    temp.setVisible(true);
    temp.setToolTipText("Player Location in the Dungeon");

    main.setAutoscrolls(true);
    if (!res) {
      notifyPungency(pungency);
      notifyPit(hasPitNearby);
    }
  }

  /**
   * To display that there is a pit nearby to the user.
   *
   * @param hasPitNearby true or false.
   */
  private void notifyPit(boolean hasPitNearby) {
    if (hasPitNearby) {
      pitSense.setText("Has Pit Nearby !");
      loadImg("pit");
    } else {
      pitSense.setText("No Pits Nearby !");
      loadImg("blank");
    }
    pitSense.setIcon(img);
  }

  /**
   * Used to overlay two images one on the other.
   *
   * @param image1 top image.
   * @param image2 base layer image.
   */
  private void overlay(String image1, String image2) {
    if (image1 == null || image2 == null) {
      throw new IllegalArgumentException();
    }
    combined = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    Graphics g = combined.getGraphics();
    try {
      BufferedImage overlay = ImageIO.read(new File("res/images/" + image1 + ".png"));
      BufferedImage node = ImageIO.read(new File("res/images/color-cells/" + image2 + ".png"));
      g.drawImage(node, 0, 0, 100, 100, this);
      g.drawImage(overlay, 20, 20, 50, 50, this);
      g.dispose();
      temp.setIcon(new ImageIcon(combined.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
    } catch (IOException e) {
      throw new IllegalStateException("Image not found");
    }
  }


  @Override
  public void updateInformation(int treasures, int arrows, int dTreasures, int dArrows) {
    if (treasures < 0 || arrows < 0 || dTreasures < 0 || dArrows < 0) {
      throw new IllegalArgumentException();
    }
    playerTreasure.setText("X\t " + treasures);
    playerArrows.setText("X\t" + arrows);
    nodeTreasures.setText("X\t" + dTreasures);
    nodeArrows.setText("X\t" + dArrows);

  }

  @Override
  public void welcome() {
    JOptionPane.showMessageDialog(null,
        "<html><b>Welcome To Harshith's Dungeon!</b></html>\n"
            + "Lets go over the rules and features of the game as we start our journey!\n\n"
            + "<html><b>Objective of the game:</b></html> \nManage to navigate through "
            + "the Dungeon moving "
            + "from the start to the destination.\n\n<html><b>Hurdles on the path:</b></html> "
            + "\n Dungeon is filled with"
            + "stinking monsters called Otyughs which can be sensed by their pungent smell."
            + " Keep an eye on the sensor at the bottom.\n The Dungeon also has some puts which "
            + "you will be sucked into, Only way to get out and bridge the pit is to trade off "
            + "with the Jewels you have collected.\n The Dungeon also has a Thief who will take "
            + "all your treasure when encountered \n The Dungeon has a sneaky Ninja who will sneak"
            + "on to you and only way out is to have a duel with a 50/50 chance to win \n\n"
            + "<html><b>Player Capabilities:</b></html>\n Player starts with 3 arrows and can "
            + "shoot in any open "
            + "direction\n As player navigates the dungeon the nodes start becoming visible."
            + "\n Player can sense the surrounding to find the pungency and sense a pit around him"
            + "\n Player can also use a Poison shot to kill an otyugh in one shot which takes "
            + "3 normal arrows \nIf player tries to enter the end node with an injured Otyugh"
            + ",he has a 50/50 chance to win the game escaping the monster.\n The game alerts"
            + " using sound when it senses an otyugh nearby.\n\n"
            + "<html><b>Controls:</b></html>"
            + "\nChoose a Direction: By either using the virtual "
            + "controller or direction key\n Move: use the Virtual Controller button move "
            + "or m key After choosing a direction\nShoot: Click on shoot button on the "
            + "Virtual Controller or use s key after selecting a direction and setting \n"
            + "the distance to shoot. Same for Poison shoot with space as key\n");

  }

  @Override
  public void drawDungeon(List<String> movable) {
    if (movable == null) {
      throw new IllegalArgumentException();
    }
    int n = 0;
    String m = "";
    while (n < maze.getComponentCount()) {
      temp = (JButton) maze.getComponent(n);
      m = movable.get(n).replaceAll("\\s", "");
      //System.out.println(n + " " + m);
      Image iconA = new ImageIcon("res/images/color-cells/" + m + ".png").getImage()
          .getScaledInstance(100, 100, Image.SCALE_SMOOTH);
      temp.setText("");
      temp.setIcon(new ImageIcon(iconA));
      maze.getComponent(n).setVisible(true);
      n++;
    }

  }

  @Override
  public void removeVisibility() {
    controls.setVisible(false);
    this.setVisible(false);
  }

  @Override
  public boolean askUserChoicePit() {
    loadImg("pit");
    int res = JOptionPane.showOptionDialog(new JFrame(),
        "<html>Oh No! Its a Pit, Do you wan to use the Treasures to save yourself from "
            + "getting sucked into the pit?</html>",
        "Pit",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, img,
        new Object[] {"Yes", "No"}, JOptionPane.YES_OPTION);
    return res == JOptionPane.YES_OPTION;
  }

  @Override
  public void notifySuckedIntoPit() {
    play("gameOver");
    JOptionPane.showMessageDialog(null, "You were sucked into a pit.", "Game Over",
        JOptionPane.ERROR_MESSAGE,
        new ImageIcon("res/images/pit.png"));
    listener.quit();

  }


  @Override
  public void notifyThief() {
    JOptionPane.showMessageDialog(null, "You were robbed by a Thief.", "Robbed",
        JOptionPane.INFORMATION_MESSAGE,
        new ImageIcon("res/images/thief.png"));
  }


  @Override
  public void notifyNinja() {
    play("fight");
    JOptionPane.showMessageDialog(null,
        "<html>A Ninja warrior managed to sneak on to you.<br> Only way out is to "
            + "fight him..</html>", "Ninja!", JOptionPane.INFORMATION_MESSAGE,
        new ImageIcon("res/images/fight.png"));
  }

  @Override
  public void notifyPlayerDefeatsNinja() {
    play("killed");
    JOptionPane.showMessageDialog(null, "You have Defeated Ninja.", "Victory!",
        JOptionPane.INFORMATION_MESSAGE,
        new ImageIcon("res/images/player.png"));
  }

  @Override
  public void notifyNinjaWins() {
    play("gameOver");
    JOptionPane.showMessageDialog(null, "Ninja killed you...", "Game Over!",
        JOptionPane.INFORMATION_MESSAGE,
        new ImageIcon("res/images/ninja.png"));

  }

  @Override
  public void notifyNoArrowsToShoot() {
    JOptionPane.showMessageDialog(null, "No Arrows to shoot!", "Game Over!",
        JOptionPane.ERROR_MESSAGE,
        new ImageIcon("res/images/quiver.png"));
  }


  @Override
  public String getDirection() {
    if (controls.up.isSelected()) {
      return "N";
    } else if (controls.right.isSelected()) {
      return "E";
    } else if (controls.left.isSelected()) {
      return "W";
    } else if (controls.down.isSelected()) {
      return "S";
    }
    throw new IllegalStateException();
  }

  @Override
  public void addListener(ViewController viewController) {
    if (viewController == null) {
      throw new IllegalArgumentException();
    }
    this.listener = viewController;
  }


}
