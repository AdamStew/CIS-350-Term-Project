package minesweeperpackage;

import javax.swing.JFrame;

/**
 * 
 * @author Kate McGowan, Adam Stewart, Sierra Ellison
 * @version 1.0
 *
 */

public class MineSweeperMain {

  /**
   * Main method that displays the game.
   * 
   * @param args
   *          takes an argument to run the program
   */
  public static void main(String[] args) {
    JFrame frame = new JFrame("Mine Sweeper");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    MineSweeperGui gui = new MineSweeperGui();
    frame.add(gui);
    frame.pack();
    frame.setVisible(true);
  }
}
