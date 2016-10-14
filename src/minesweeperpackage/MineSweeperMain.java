package minesweeperpackage;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

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

  private static JMenuBar menuBar;
  private static JMenu menu;

  public static void main(String[] args) {
    JFrame frame = new JFrame("Mine Sweeper");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    MineSweeperGui gui = new MineSweeperGui();

    menuBar = new JMenuBar();
    menu = new JMenu("Menu");
    menuBar.add(menu);
    frame.add(gui);
    frame.pack();
    frame.setJMenuBar(menuBar);
    frame.setVisible(true);
  }
}
