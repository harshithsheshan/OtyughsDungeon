package view;

import controller.guiControllerInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;


/**
 * A class which is responsible to take use arguments for creating the dungeon.
 */
public class Argument extends JFrame {
  JLabel message;
  JSpinner row;
  JSpinner col;
  JSpinner inter;
  JRadioButton wrapping;
  JRadioButton nonWrapping;
  JSpinner treasure;
  JSpinner otyugh;
  JButton submit;

  guiControllerInterface listener;

  /**
   * Constructor for the class which initializes the object by providing a listener.
   *
   * @param listener controller.
   */
  public Argument(guiControllerInterface listener) {
    if (listener == null) {
      throw new IllegalArgumentException();
    }
    this.listener = listener;
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    message = new JLabel("Please enter the  to start");
    add(message);
    message = new JLabel("Rows:");
    add(message);
    row = new JSpinner(new SpinnerNumberModel(5, 5, 100, 1));

    //row = new JTextField();
    add(row);
    message = new JLabel("Cols:");
    add(message);
    col = new JSpinner(new SpinnerNumberModel(5, 5, 100, 1));
    add(col);
    //6 8 4 true 40 3 0
    message = new JLabel("Interconnectivity:");
    add(message);
    inter = new JSpinner(new SpinnerNumberModel(5, 5, 100, 1));
    add(inter);
    message = new JLabel("Style:");
    wrapping = new JRadioButton("Wrapping");

    nonWrapping = new JRadioButton("Non-Wrapping");
    wrapping = new JRadioButton("Wrapping");
    ButtonGroup group = new ButtonGroup();
    group.add(nonWrapping);
    group.add(wrapping);

    add(message);
    add(wrapping);
    add(nonWrapping);
    message = new JLabel("Treasure and Arrow percentage:");
    add(message);
    treasure = new JSpinner(new SpinnerNumberModel(40, 10, 100, 5));
    add(treasure);
    message = new JLabel("Number of Otyughs:");
    add(message);
    otyugh = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
    add(otyugh);

    submit = new JButton("Submit");
    submit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        boolean wrappingArgument;
        if (e.getSource() == submit) {
          wrappingArgument = wrapping.isSelected();
          listener.makeDungeon((int) row.getValue(), (int) col.getValue(),
              (int) inter.getValue(), wrappingArgument,
              (int) treasure.getValue(), (int) otyugh.getValue());
          //listener.createControls();
          dispose();


        }

      }

    });
    add(submit);
    pack();
    setVisible(true);

  }


}
