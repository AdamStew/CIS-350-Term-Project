package package1;

import javax.swing.JFrame;

/**
 * 
 * @author Kate McGowan, Adam Stewart, Sierra Ellison
 * @version 1.0
 *
 */

public class MineSweeperMain {

  public static void main(String args[]) {
    JFrame frame = new JFrame("Mine Sweeper");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    MineSweeperGUI GUI = new MineSweeperGUI();
    frame.add(GUI);
    frame.pack();
    frame.setVisible(true);
  }
}
