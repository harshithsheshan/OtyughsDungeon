package view;

import controller.guiControllerInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;

/**
 * Class which creates a controller for the game and is used to play the game and move the player
 * around and perform all activities. It also takes Key inputs from keyboard.
 */
public class ControlPanel extends JFrame implements KeyEventDispatcher {

  JPanel controls;
  JToggleButton up;
  JToggleButton down;
  JToggleButton left;
  JToggleButton right;
  JButton shoot;
  JButton pickupArrow;
  JButton pickupTreasure;
  JButton move;
  JButton distance;
  ItemListener itemListenerUp;
  ItemListener itemListenerDown;
  ItemListener itemListenerRight;
  ItemListener itemListenerLeft;
  guiControllerInterface listener;
  JSpinner dist;
  Dimension dim;
  JButton temp;
  JToggleButton temp1;
  Image iconA;
  JButton poisonShoot;


  /**
   * Creates a new <code>JPanel</code> with a double buffer
   * and a flow layout.
   */
  public ControlPanel(guiControllerInterface listener) {
    super("Control Panel");
    if (listener == null) {
      throw new IllegalArgumentException();
    }
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    manager.addKeyEventDispatcher(this);
    this.listener = listener;
    dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.setLocation(dim.width / 2 - this.getSize().width / 2,
        dim.height / 2 - this.getSize().height / 2);
    controls = new JPanel();

    controls.setLayout(new GridLayout(2, 6));
    int count = 0;
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 6; j++) {
        if (count == 1 || count == 6 || count == 7 || count == 8) {
          temp1 = new JToggleButton();
          temp1.setPreferredSize(new Dimension(100, 100));
          controls.add(temp1);
        } else {
          temp = new JButton();
          temp.setPreferredSize(new Dimension(100, 100));
          controls.add(temp);
        }
        count++;
      }
    }
    itemListenerUp = new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        int state = e.getStateChange();
        if (state == ItemEvent.SELECTED) {
          up.setBackground(Color.GREEN);
          down.setSelected(false);
          right.setSelected(false);
          left.setSelected(false);
        }
      }
    };
    itemListenerDown = new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        int state = e.getStateChange();
        if (state == ItemEvent.SELECTED) {
          down.setBackground(Color.GREEN);
          up.setSelected(false);
          right.setSelected(false);
          left.setSelected(false);
        }
      }
    };
    itemListenerRight = new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        int state = e.getStateChange();
        if (state == ItemEvent.SELECTED) {
          right.setBackground(Color.GREEN);
          down.setSelected(false);
          up.setSelected(false);
          left.setSelected(false);
        }
      }
    };
    itemListenerLeft = new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        int state = e.getStateChange();
        if (state == ItemEvent.SELECTED) {
          left.setBorderPainted(true);
          down.setSelected(false);
          right.setSelected(false);
          up.setSelected(false);
        }
      }
    };
    up = (JToggleButton) controls.getComponent(1);
    iconA = new ImageIcon("res/images/up.png").getImage()
        .getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    up.addItemListener(itemListenerUp);
    up.setIcon(new ImageIcon(iconA));
    down = (JToggleButton) controls.getComponent(7);
    iconA = new ImageIcon("res/images/down.png").getImage()
        .getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    down.setIcon(new ImageIcon(iconA));
    down.addItemListener(itemListenerDown);
    left = (JToggleButton) controls.getComponent(6);
    iconA = new ImageIcon("res/images/left.png").getImage()
        .getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    left.setIcon(new ImageIcon(iconA));
    left.addItemListener(itemListenerLeft);
    right = (JToggleButton) controls.getComponent(8);
    iconA = new ImageIcon("res/images/right.png").getImage()
        .getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    right.setIcon(new ImageIcon(iconA));
    right.addItemListener(itemListenerRight);
    dist = new JSpinner(new SpinnerNumberModel(1, 1, 2, 1));
    poisonShoot = (JButton) controls.getComponent(5);
    iconA = new ImageIcon("res/images/poisonedArrow.png").getImage()
        .getScaledInstance(80, 80, Image.SCALE_SMOOTH);
    poisonShoot.setIcon(new ImageIcon(iconA));
    poisonShoot.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {

          int d = (int) dist.getValue();
          if (d > 2 || d < 1) {
            JOptionPane.showMessageDialog(null, "Choose a legal distance ",
                null, JOptionPane.ERROR_MESSAGE);

          } else {
            if (up.isSelected()) {
              listener.poisonShoot("N", d);
            } else if (down.isSelected()) {
              listener.poisonShoot("S", d);
            } else if (left.isSelected()) {
              listener.poisonShoot("W", d);
            } else if (right.isSelected()) {
              listener.poisonShoot("E", d);
            } else {
              JOptionPane.showMessageDialog(null, "Choose a Direction to shoot. ",
                  null, JOptionPane.ERROR_MESSAGE);
            }
            listener.updateInformation();
          }
        } catch (IllegalStateException ioe) {
          JOptionPane.showMessageDialog(null, "Shooting in the direction not possible.. " ,
              null, JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    distance = (JButton) controls.getComponent(3);
    distance.setLayout(new FlowLayout());
    distance.add(new JLabel("<html>Shooting <br>distance:</html>"));
    distance.add(dist);
    shoot = (JButton) controls.getComponent(4);
    iconA = new ImageIcon("res/images/shoot.png").getImage()
        .getScaledInstance(80, 80, Image.SCALE_SMOOTH);
    shoot.setIcon(new ImageIcon(iconA));
    shoot.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        shootArrow();
      }
    });
    pickupArrow = (JButton) controls.getComponent(10);
    iconA = new ImageIcon("res/images/pickarrow.png").getImage()
        .getScaledInstance(80, 80, Image.SCALE_SMOOTH);
    pickupArrow.setIcon(new ImageIcon(iconA));
    pickupArrow.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.pickArrow();
      }
    });
    pickupTreasure = (JButton) controls.getComponent(11);
    iconA = new ImageIcon("res/images/treasure.png").getImage()
        .getScaledInstance(80, 80, Image.SCALE_SMOOTH);
    pickupTreasure.setIcon(new ImageIcon(iconA));
    pickupTreasure.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.pickTreasure();
      }
    });
    move = (JButton) controls.getComponent(9);
    iconA = new ImageIcon("res/images/move.png").getImage()
        .getScaledInstance(80, 80, Image.SCALE_SMOOTH);
    move.setIcon(new ImageIcon(iconA));
    move.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        movePlayer();
      }
    });
    add(controls);
    pack();
    setVisible(true);
  }

  /**
   * used to move player when the button is pressed or key is pressed.
   */
  private void movePlayer() {
    try {
      if (up.isSelected()) {
        listener.move("N");
      } else if (down.isSelected()) {
        listener.move("S");
      } else if (left.isSelected()) {
        listener.move("W");
      } else if (right.isSelected()) {
        listener.move("E");
      } else {
        JOptionPane.showMessageDialog(null,
            "Choose a Direction to move. ",null, JOptionPane.ERROR_MESSAGE);
      }
      listener.updateInformation();
    } catch (IllegalArgumentException iae) {
      JOptionPane.showMessageDialog(null,
          "No opening in the direction chosen ",null, JOptionPane.ERROR_MESSAGE);
    }
  }


  /**
   * This method is called by the current KeyboardFocusManager requesting
   * that this KeyEventDispatcher dispatch the specified event on its behalf.
   */
  @Override
  public boolean dispatchKeyEvent(KeyEvent e) {
    if (e == null) {
      throw new IllegalArgumentException();
    }
    if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_UP) {
      up.setSelected(true);
    } else if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_DOWN) {
      down.setSelected(true);
    } else if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_RIGHT) {
      right.setSelected(true);
    } else if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_LEFT) {
      left.setSelected(true);
    } else if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_A) {
      listener.pickArrow();
    } else if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_T) {
      listener.pickTreasure();
    } else if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_M) {
      movePlayer();
    } else if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_SPACE) {
      shootPoison();
    } else if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_S) {
      shootArrow();
    }
    return false;
  }

  /**
   * Method to shoot poison arrow when the key is pressed or button is pressed.
   */
  private void shootPoison() {
    try {
      int d = (int) dist.getValue();
      if (d > 2 || d < 1) {
        JOptionPane.showMessageDialog(null, "Choose a legal distance ");

      } else {
        if (up.isSelected()) {
          listener.poisonShoot("N", d);
        } else if (down.isSelected()) {
          listener.poisonShoot("S", d);
        } else if (left.isSelected()) {
          listener.poisonShoot("W", d);
        } else if (right.isSelected()) {
          listener.poisonShoot("E", d);
        } else {
          JOptionPane.showMessageDialog(null, "Choose a Direction to shoot. ");
        }
      }
    } catch (IllegalStateException ioe) {
      JOptionPane.showMessageDialog(null, "Shooting in the direction not possible.. ");
    }
  }

  /**
   * Method to shoot an arrow when the key is pressed or button is pressed.
   */
  private void shootArrow() {
    try {
      int d = (int) dist.getValue();
      if (d > 2 || d < 1) {
        JOptionPane.showMessageDialog(null, "Choose a legal distance ");

      } else {
        if (up.isSelected()) {
          listener.shoot("N", d);
        } else if (down.isSelected()) {
          listener.shoot("S", d);
        } else if (left.isSelected()) {
          listener.shoot("W", d);
        } else if (right.isSelected()) {
          listener.shoot("E", d);
        } else {
          JOptionPane.showMessageDialog(null, "Choose a Direction to shoot. ");
        }
      }
    } catch (IllegalStateException ioe) {
      JOptionPane.showMessageDialog(null, "Shooting in the direction not possible.. ");
    }

  }


}



