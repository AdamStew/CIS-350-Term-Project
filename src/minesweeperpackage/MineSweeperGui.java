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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Kate McGowan, Adam Stewart, Sierra Ellison
 * 
 * @version 1.0
 * 
 * 
 */

@SuppressWarnings("serial")
public class MineSweeperGui extends JPanel {
  private JButton[][] board;
  private Cell cell;
  private JMenuItem customItem;
  private JButton quitButton;
  private JButton resetButton;
  private JButton minesButton;
  private JPanel buttonPanel;
  private JPanel gamePanel;
  private ImageIcon smiley;
  private ImageIcon mine;
  private ImageIcon flag;
  private MineSweeperGame game;
  private static boolean mineFlag;
  private JLabel winLabel;
  private JLabel loseLabel;
  private int wins;
  private int losses;

  /**
   * Constructor initializing game and GUI.
   */
  public MineSweeperGui(JMenuItem customItem) {
    game = new MineSweeperGame();

    winLabel = new JLabel("Wins: " + wins);
    loseLabel = new JLabel("Losses: " + losses);

    smiley = new ImageIcon("smiley.gif");
    mine = new ImageIcon("mine.png");
    flag = new ImageIcon("flag.png");

    quitButton = new JButton("Quit");
    quitButton.setFont(new Font("Arial", Font.PLAIN, 10));
    quitButton.addActionListener(new ButtonListener());

    resetButton = new JButton(smiley);
    resetButton.addActionListener(new ButtonListener());

    minesButton = new JButton("Mines");
    minesButton.addActionListener(new ButtonListener());
    minesButton.setFont(new Font("Arial", Font.PLAIN, 10));

    this.customItem = customItem;
    this.customItem.addActionListener(new ButtonListener());

    buttonPanel = new JPanel();
    buttonPanel.add(quitButton);
    buttonPanel.add(winLabel);
    buttonPanel.add(resetButton);
    buttonPanel.add(loseLabel);
    buttonPanel.add(minesButton);

    // board = new JButton[game.getRows()][game.getCols()];
    createButtons();
    setLayout(new BorderLayout());
    add(buttonPanel, BorderLayout.NORTH);
    add(gamePanel, BorderLayout.CENTER);

  }

  /**
   * A method that updates the wins and losses counts.
   */
  private void updateLabels() {
    winLabel.setText("Wins: " + wins);
    loseLabel.setText("Losses: " + losses);
  }

  public MineSweeperGame getGame() {
    return game;
  }

  /**
   * A method to create the grid of buttons.
   */
  private void createButtons() {
    gamePanel = new JPanel();
    gamePanel.setLayout(new GridLayout(game.getRows(), game.getCols()));
    // gamePanel.setBackground(Color.gray);

    board = new JButton[game.getRows()][game.getCols()];
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
        Cell cell2 = game.getCell(row, col);

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
            board[row][col].setFont(new Font("Arial", Font.PLAIN, 10));
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
   * A method that allows the user to create a custom game.
   */
  public void custom() {
    String row = JOptionPane.showInputDialog(null, "Enter the desired number of rows (Min. 3):");
    int oldRow = game.getRows();
    int oldCol = game.getCols();

    if (row != null) {
      if (checkForNumbers(row) == false || row.isEmpty() || Integer.parseInt(row) < 3
          || Integer.parseInt(row) > 30) {
        JOptionPane.showMessageDialog(null, "Invalid input. Rows set to default.");
        game.setRows(9);
      } else {
        game.setRows(Integer.parseInt(row));
      }

      String col = JOptionPane.showInputDialog(null,
          "Enter the desired number of columns (Min. 3):");
      if (col != null) {
        if (checkForNumbers(col) == false || col.isEmpty() || Integer.parseInt(col) < 3
            || Integer.parseInt(col) > 30) {
          JOptionPane.showMessageDialog(null, "Invalid input. Columns set to default.");
          game.setCols(9);
        } else {
          game.setCols(Integer.parseInt(col));
        }

        String mines = JOptionPane.showInputDialog(null, "Enter the desired mine count:");
        if (mines != null) {
          if (checkForNumbers(mines) == false || mines.isEmpty()
              || Integer.parseInt(mines) > (game.getRows() * game.getCols())) {
            JOptionPane.showMessageDialog(null, "Invalid input. Mine count set to default.");
            game.setMineCount(9);
          } else {
            game.setMineCount(Integer.parseInt(mines));
          }
        } else {
          game.setRows(oldRow);
          game.setCols(oldCol);
        }
      } else {
        game.setRows(oldRow);
      }
    }
    game.reset();
    remove(gamePanel);
    createButtons();
    display();
    add(gamePanel);
    repaint();
    revalidate();
  }

  /**
   * A method to check if a string is comprised of only numbers.
   * 
   * @param input
   *          the text to be checked
   * @return true/false depending on whether the string contained only digits
   */
  private boolean checkForNumbers(String input) {
    char[] array = input.toCharArray();
    for (int i = 0; i < array.length; i++) {
      if (Character.isDigit(array[i]) == false) {
        return false;
      } else {
        return true;
      }
    }
    return true;
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
              losses++;
            } else if (game.getGameStatus() == 1) {
              JOptionPane.showMessageDialog(null, "Congratulations! You won the game.");
              wins++;
            }
          }
        }
        updateLabels();

        display();

        if (buttonPressed == resetButton) {
          game.reset();
          resetButtonText();
          mineFlag = false;
        }

        if (buttonPressed == quitButton) {
          int response = JOptionPane.showConfirmDialog(null,
              "Are you sure you want to quit the game?", "Quit", JOptionPane.YES_NO_OPTION);
          if (response == JOptionPane.YES_OPTION) {
            Runtime.getRuntime().halt(0);
          } else {
            return;
          }
        }
      }

      if (buttonPressed == minesButton) {
        if (mineFlag == false) {
          for (int row2 = 0; row2 < game.getRows(); row2++) {
            for (int col2 = 0; col2 < game.getCols(); col2++) {
              cell = game.getCell(row2, col2);
              if (cell.isMine()) {
                board[row2][col2].setIcon(mine);
              }
            }
          }
          mineFlag = true;
        } else {
          for (int row2 = 0; row2 < game.getRows(); row2++) {
            for (int col2 = 0; col2 < game.getCols(); col2++) {
              cell = game.getCell(row2, col2);
              if (cell.isMine()) {
                board[row2][col2].setIcon(null);
              }
            }
          }
          mineFlag = false;
        }
        display();
      }

      if (buttonPressed == customItem) {
        custom();
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
