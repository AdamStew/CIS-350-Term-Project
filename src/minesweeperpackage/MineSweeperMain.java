package minesweeperpackage;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 
 * @author Kate McGowan, Adam Stewart, Sierra Ellison
 * @version 1.0
 *
 */
public class MineSweeperMain {

  private static JMenuBar menuBar;
  private static JMenu menu;
  private static JMenuItem customGame;

  /**
   * Main method that displays the game.
   * 
   * @param args
   *          takes an argument to run the program
   */
  public static void main(String[] args) {
    JFrame frame = new JFrame("Mine Sweeper");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    menuBar = new JMenuBar();
    menu = new JMenu("Menu");
    customGame = new JMenuItem("Custom Game");
    menuBar.add(menu);
    menu.add(customGame);

    MineSweeperGui gui = new MineSweeperGui(customGame);
    frame.add(gui);
    frame.pack();
    frame.setJMenuBar(menuBar);
    frame.setResizable(true);
    frame.setVisible(true);

  }
}
