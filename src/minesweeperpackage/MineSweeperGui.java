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

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

import java.util.Timer;

/**
 * @author Kate McGowan, Adam Stewart, Sierra Ellison
 * 
 * @version 1.0
 * 
 * 
 */

@SuppressWarnings("serial")
public class MineSweeperGui extends JFrame implements ActionListener, MouseListener {
  private JButton[][] board;
  private Cell cell;
  private JMenuBar menuBar;
  private JMenu menu;
  private JMenu options;
  private JMenuItem customGame;
  private JMenuItem difficultyGame;
  private JMenuItem quitGame;
  private JMenuItem minesGame;
  private JButton resetButton;
  private JButton minesButton;
  private JPanel buttonPanel;
  private JPanel gamePanel;
  private JPanel timerPanel;
  private ImageIcon smiley;
  private ImageIcon mine;
  private ImageIcon flag;
  private MineSweeperGame game;
  private JOptionPane diff;
  private static boolean mineFlag;
  private JLabel winLabel;
  private JLabel loseLabel;
  private JLabel mineCountLabel;
  private int wins;
  private int losses;
  private Timer time;

  /**
   * Constructor initializing game and GUI.
   */
  public MineSweeperGui() {
    game = new MineSweeperGame();
    
    menuBar = new JMenuBar();
    menu = new JMenu("Menu");
    options = new JMenu("Options");
    
    difficultyGame = new JMenuItem("Select Difficulty");
    customGame = new JMenuItem("Custom Game");
    quitGame = new JMenuItem("Quit Game");
    minesGame = new JMenuItem("Toggle Mines");
    menuBar.add(menu);
    menu.add(difficultyGame);
    menu.add(customGame);
    menu.add(quitGame);
    menuBar.add(options);
    options.add(minesGame);

    setLookAndFeel();

    wins = 0;
    losses = 0;
    winLabel = new JLabel("Wins: " + wins);
    loseLabel = new JLabel("Losses: " + losses);
    mineCountLabel = new JLabel("Mine Count: " + game.mineCount());

    smiley = new ImageIcon("smiley.gif");
    mine = new ImageIcon("mine.png");
    flag = new ImageIcon("flag.png");

    resetButton = new JButton(smiley);
    resetButton.addActionListener(this);

    //minesButton = new JButton("Mines");
    //minesButton.addActionListener(this);
    //minesButton.setFont(new Font("Arial", Font.PLAIN, 10));

    difficultyGame.addActionListener(this);
    customGame.addActionListener(this);
    quitGame.addActionListener(this);
    minesGame.addActionListener(this);

    buttonPanel = new JPanel();
    buttonPanel.add(winLabel);
    buttonPanel.add(resetButton);
    buttonPanel.add(loseLabel);
    //buttonPanel.add(minesButton);

    // board = new JButton[game.getRows()][game.getCols()];
    createButtons();
    setLayout(new BorderLayout(0, 0));
    add(mineCountLabel, BorderLayout.SOUTH);
    add(buttonPanel, BorderLayout.NORTH);
    add(gamePanel, BorderLayout.CENTER);

    time = new Timer("time");
<<<<<<< HEAD
    // timerPanel.action(time);

=======
    //timerPanel.action(time);
    
    //setResizable(true);
>>>>>>> refs/remotes/origin/master
    setJMenuBar(menuBar);
    setVisible(true);
    setSize(400, 500);
  }
  
  public static void main(String [] args){
    new MineSweeperGui();
  }

  /**
   * Get rid of Java's default look and feel and use the system defined one instead.
   */
  private static void setLookAndFeel() {
    try {
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
    } catch (Exception except) {
      except.printStackTrace();
    }
  }

  /**
   * A method that updates the wins and losses counts.
   */
  private void updateLabels() {
    winLabel.setText("Wins: " + wins);
    loseLabel.setText("Losses: " + losses);
    mineCountLabel.setText("Mine Count: " + game.mineCount());
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
        board[row][col].addActionListener(this);
        board[row][col].addMouseListener(this);
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
    
    int colSize;
    int rowSize;
    
    if (game.getCols() > 7) {
      colSize = game.getCols() * 40;
    } else {
      colSize = 300;
    }
    
    if (game.getRows() > 7) {
      rowSize = game.getRows() * 50;
    } else {
      rowSize = game.getRows() * 80;
    }
    
    setSize(colSize, rowSize);
    game.reset();
    remove(gamePanel);
    createButtons();
    display();
    add(gamePanel);
    repaint();
    revalidate();
  }

  /**
   * A method that allows the user to select a pre-determined difficulty.
   */
  public void difficulty() {
    JPanel radioPanel = new JPanel(); // Creates panel.

    // Radio buttons going into our panel.
    JRadioButton easy = new JRadioButton("Beginner (9x9 10 Mines)", true);
    JRadioButton medium = new JRadioButton("Intermediate (16x16 30 Mines)");
    JRadioButton hard = new JRadioButton("Advanced (16x30 99 Mines)");

    ButtonGroup group = new ButtonGroup(); // This makes it so you can't select more than one box.
    group.add(easy);
    group.add(medium);
    group.add(hard);

    // Designing panel.
    BoxLayout boxLayout = new BoxLayout(radioPanel, BoxLayout.Y_AXIS);
    radioPanel.add(new JLabel("Select Difficulty"));
    radioPanel.add(easy);
    radioPanel.add(medium);
    radioPanel.add(hard);
    radioPanel.setLayout(boxLayout);

    int result = diff.showConfirmDialog(null, radioPanel, null, JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.INFORMATION_MESSAGE);

    // Options
    if (result == JOptionPane.YES_OPTION) {
      if (easy.isSelected()) {
        game.setCols(9);
        game.setRows(9);
        game.setMineCount(10);
        setSize(400, 500);
      }
      if (medium.isSelected()) {
        game.setCols(16);
        game.setRows(16);
        game.setMineCount(30);
        setSize(640, 800);
      }
      if (hard.isSelected()) {
        game.setCols(30);
        game.setRows(16);
        game.setMineCount(99);
        setSize(1200, 800);
      }
      game.reset();
      remove(gamePanel);
      createButtons();
      display();
      add(gamePanel);
      repaint();
      revalidate();
    }
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
  
  @Override
  public void actionPerformed(ActionEvent event) {

    JComponent buttonPressed = (JComponent) event.getSource();

    for (int row = 0; row < game.getRows(); row++) {
      for (int col = 0; col < game.getCols(); col++) {
        if (board[row][col] == event.getSource() && game.checkFlagged(row, col) == false) {
          game.select(row, col);
          game.flood(row, col);
          if (game.getGameStatus() == 0) {
            // Losing Sound Effect
            String sound = "Sad_Trombone.wav";
            AudioInputStream audioInputStream = null;
            try {
              audioInputStream = AudioSystem
                  .getAudioInputStream(new File(sound).getAbsoluteFile());
            } catch (UnsupportedAudioFileException | IOException except) {
              // TODO Auto-generated catch block
              except.printStackTrace();
            }
            Clip clip = null;
            try {
              clip = AudioSystem.getClip();
            } catch (LineUnavailableException except) {
              // TODO Auto-generated catch block
              except.printStackTrace();
            }
            try {
              if (clip != null) {
                clip.open(audioInputStream);
              }
            } catch (LineUnavailableException | IOException except) {
              // TODO Auto-generated catch block
              except.printStackTrace();
            }
            if (clip != null) {
              clip.start();
            }

            JOptionPane.showMessageDialog(null, "You hit a mine. Game Over.");
            losses++;
          } else if (game.getGameStatus() == 1) {
            // Winning Sound Effect
            String sound = "Ta_Da.wav";
            AudioInputStream audioInputStream = null;
            try {
              audioInputStream = AudioSystem
                  .getAudioInputStream(new File(sound).getAbsoluteFile());
            } catch (UnsupportedAudioFileException | IOException except) {
              // TODO Auto-generated catch block
              except.printStackTrace();
            }
            Clip clip = null;
            try {
              clip = AudioSystem.getClip();
            } catch (LineUnavailableException except) {
              // TODO Auto-generated catch block
              except.printStackTrace();
            }
            try {
              if (clip != null) {
                clip.open(audioInputStream);
              }
            } catch (LineUnavailableException | IOException except) {
              // TODO Auto-generated catch block
              except.printStackTrace();
            }
            if (clip != null) {
              clip.start();
            }

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
        
    }

    if (buttonPressed == customGame) {
      custom();
    }

    if (buttonPressed == difficultyGame) {
      difficulty();
    }
    
    if (buttonPressed == quitGame) {
      int response = JOptionPane.showConfirmDialog(null,
          "Are you sure you want to quit the game?", "Quit", JOptionPane.YES_NO_OPTION);
      if (response == JOptionPane.YES_OPTION) {
        Runtime.getRuntime().halt(0);
      } else {
        return;
      }
    }
    
    if (buttonPressed == minesGame) {
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
      updateLabels();
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
            updateLabels();
          }
        }
      }
    }
  }

}
