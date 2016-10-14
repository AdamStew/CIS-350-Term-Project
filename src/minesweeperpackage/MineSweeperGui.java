package minesweeperpackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.MenuElement;

/**
 * @author Kate McGowan, Adam Stewart, Sierra Ellison
 * 
 * @version 1.0
 * 
 * 
 */

public class MineSweeperGui<JMenuBar> extends JPanel {
  private JButton[][] board;
  private Cell cell;
  private JButton quitButton;
  private JButton resetButton;
  private JButton minesButton;
  private JPanel buttonPanel;
  private JPanel gamePanel;
  private ImageIcon smiley;
  private ImageIcon mine;
  private ImageIcon flag;
  private MineSweeperGame game;

  /**
   * Constructor initializing game and GUI.
   */
  public MineSweeperGui() {
    game = new MineSweeperGame();

    smiley = new ImageIcon("smiley.gif");
    mine = new ImageIcon("mine.jpg");
    flag = new ImageIcon("flag.png");

    quitButton = new JButton("Quit");
    quitButton.setFont(new Font("Arial", Font.PLAIN, 10));
    quitButton.addActionListener(new ButtonListener());

    resetButton = new JButton(smiley);
    resetButton.addActionListener(new ButtonListener());

    minesButton = new JButton("Mines");
    minesButton.addActionListener(new ButtonListener());
    minesButton.setFont(new Font("Arial", Font.PLAIN, 10));

    buttonPanel = new JPanel();
    buttonPanel.add(quitButton);
    buttonPanel.add(resetButton);
    buttonPanel.add(minesButton);

    gamePanel = new JPanel();
    gamePanel.setLayout(new GridLayout(game.getRows(), game.getCols()));
    gamePanel.setBackground(Color.gray);
    board = new JButton[game.getRows()][game.getCols()];
    createButtons();
    setLayout(new BorderLayout());
    add(buttonPanel, BorderLayout.NORTH);
    add(gamePanel, BorderLayout.CENTER);

  }

  public MineSweeperGame getGame() {
    return game;
  }

  /**
   * A method to create the grid of buttons.
   */
  private void createButtons() {
    for (int row = 0; row < game.getRows(); row++) {
      for (int col = 0; col < game.getCols(); col++) {
        // cannot be enabled with ImageIcon
        board[row][col] = new JButton();
        board[row][col].setPreferredSize(new Dimension(40, 40));
        board[row][col].addActionListener(new ButtonListener());
        board[row][col].addMouseListener(new ButtonListener());
        gamePanel.add(board[row][col]);

      }
    }
  }

  /**
   * A method that displays the game board.
   */
  private void display() {
    for (int row = 0; row < game.getRows(); row++) {

      for (int col = 0; col < game.getCols(); col++) {
        Cell cell2 = new Cell();
        cell2 = game.getCell(row, col);

        if (cell2.isExposed()) {
          board[row][col].setEnabled(false);

        } else {

          board[row][col].setEnabled(true);
        }

        if (cell2.isExposed()) {
          int nc = game.getNeighborCount(row, col);
          if (cell2.isMine()) {
            board[row][col].setIcon(mine);

          } else {
            board[row][col].setText("" + nc);
            board[row][col].setFont(new Font("Arial", Font.PLAIN, 11));
          }
        }
      }
    }
  }

  /**
   * A method that resets the button text.
   */
  private void resetButtonText() {
    for (int row = 0; row < game.getRows(); row++) {
      for (int col = 0; col < game.getCols(); col++) {
        board[row][col].setText("");
        board[row][col].setIcon(null);
      }
    }
  }

  /**
   * A method that is called when a button is clicked.
   */
  private class ButtonListener implements ActionListener, MouseListener {
    public void actionPerformed(ActionEvent event) {

      JComponent buttonPressed = (JComponent) event.getSource();

      for (int row = 0; row < game.getRows(); row++) {
        for (int col = 0; col < game.getCols(); col++) {
          if (board[row][col] == event.getSource() && game.checkFlagged(row, col) == false) {
            game.select(row, col);
            game.flood(row, col);
            if (game.getGameStatus() == 0) {
              JOptionPane.showMessageDialog(null, "You hit a mine. Game Over.");
            } else if (game.getGameStatus() == 1) {
              JOptionPane.showMessageDialog(null, "Congratulations! You won the game.");

            }

          }
        }

        if (buttonPressed == resetButton) {
          game.reset();
          resetButtonText();
        }

        if (buttonPressed == quitButton) {
          int response = JOptionPane.showConfirmDialog(null,
              "Are you sure you want to quit the game?", "Quit", JOptionPane.YES_NO_OPTION);
          if (response == JOptionPane.YES_OPTION) {
            System.exit(1);
          } else {
            return;
          }

        }

        if (buttonPressed == minesButton) {
          for (int row2 = 0; row2 < game.getRows(); row2++) {
            for (int col2 = 0; col2 < game.getCols(); col2++) {
              cell = game.getCell(row2, col2);
              if (cell.isMine()) {
                board[row2][col2].setIcon(mine);
              }
            }
          }
        }
        display();

      }

    }

    @Override
    public void mouseClicked(MouseEvent event) {
      // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent event) {
      // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent event) {
      // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent event) {
      // TODO Auto-generated method stub

    }

    public void mousePressed(MouseEvent event) {
      for (int row = 0; row < game.getRows(); row++) {
        for (int col = 0; col < game.getCols(); col++) {
          if (event.getButton() == MouseEvent.BUTTON3) {
            if (board[row][col] == event.getSource()) {
              if (game.checkFlagged(row, col) == false) {
                game.flag(row, col);
                board[row][col].setIcon(flag);
              } else {
                game.unflag(row, col);
                board[row][col].setIcon(null);
              }
            }
          }
        }
      }
    }
  }
}
