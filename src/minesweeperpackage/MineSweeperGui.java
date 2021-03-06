package minesweeperpackage;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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

/**
 * @author Kate McGowan, Adam Stewart, Sierra Ellison
 * 
 * @version 2.0
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
  private JMenuItem ranksGame;
  private JButton resetButton;
  private JPanel buttonPanel;
  private JPanel gamePanel;
  private JPanel mineCountPanel;
  private JPanel timerPanel;
  private JPanel botPanel;
  private ImageIcon smiley;
  private ImageIcon mine;
  private ImageIcon flag;
  private MineSweeperGame game;
  private JOptionPane diff;
  private boolean mineFlag;
  private boolean firstMove;
  private Timer timer;
  private JLabel timeLabel;
  private JLabel winLabel;
  private JLabel loseLabel;
  private JLabel mineCountLabel;
  private int wins;
  private int losses;

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
    ranksGame = new JMenuItem("Show Leaderboard");
    
    menuBar.add(menu);
    menu.add(difficultyGame);
    menu.add(customGame);
    menu.add(quitGame);
    menuBar.add(options);
    options.add(minesGame);
    options.add(ranksGame);

    setLookAndFeel();

    firstMove = true;

    wins = 0;
    losses = 0;
    winLabel = new JLabel("Wins: " + wins);
    loseLabel = new JLabel("Losses: " + losses);
    mineCountLabel = new JLabel("Mine Count: " + game.mineCount());
    timeLabel = new JLabel("Time: 0:00");

    smiley = new ImageIcon("smiley.gif");
    mine = new ImageIcon("mine.png");
    flag = new ImageIcon("flag.png");

    resetButton = new JButton(smiley);
    resetButton.addActionListener(this);

    difficultyGame.addActionListener(this);
    customGame.addActionListener(this);
    quitGame.addActionListener(this);
    minesGame.addActionListener(this);
    ranksGame.addActionListener(this);

    timer = new Timer();

    buttonPanel = new JPanel();
    buttonPanel.add(winLabel);
    buttonPanel.add(resetButton);
    buttonPanel.add(loseLabel);

    mineCountPanel = new JPanel();
    mineCountPanel.add(mineCountLabel);

    timerPanel = new JPanel();
    timerPanel.add(timeLabel);

    botPanel = new JPanel();
    botPanel.add(mineCountPanel, BorderLayout.WEST);
    botPanel.add(timerPanel, BorderLayout.EAST);

    createButtons();
    setLayout(new BorderLayout(0, 0));
    add(botPanel, BorderLayout.SOUTH);
    add(buttonPanel, BorderLayout.NORTH);
    add(gamePanel, BorderLayout.CENTER);

    setJMenuBar(menuBar);
    setVisible(true);
    setSize(400, 500);

  }
  
  /**
   * Runs the application.
   * @param args
   *          argument that runs the application.
   */
  
  public static void main(String[] args) {
    new MineSweeperGui();
    
    File file = new File("Leaders.txt");
    //Check to see if a leaderboard file exists.
    if (file.exists() && !file.isDirectory()) {
      //do nothing
    } else {
      //if it doesn't exist, create a default leaderboard.
      try {
        file.createNewFile();
      } catch (IOException except) {
        // TODO Auto-generated catch block
        except.printStackTrace();
      }
      
      DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
      Date date = new Date();
      
      try {
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.println("Sally                01000  " + dateFormat.format(date));
        writer.println("Jon                  00800  " + dateFormat.format(date));
        writer.println("Willy                00600  " + dateFormat.format(date));
        writer.println("Pat                  00400  " + dateFormat.format(date));
        writer.println("Rick                 00200  " + dateFormat.format(date));
        writer.close();
      } catch (FileNotFoundException except) {
        // TODO Auto-generated catch block
        except.printStackTrace();
      } catch (UnsupportedEncodingException except) {
        // TODO Auto-generated catch block
        except.printStackTrace();
      }
    }
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
    String row = JOptionPane.showInputDialog(null, "Enter the desired number of rows (4-30):");
    int oldRow = game.getRows();
    int oldCol = game.getCols();

    if (row != null) {
      if (checkForNumbers(row) == false || row.isEmpty() || Integer.parseInt(row) < 4
          || Integer.parseInt(row) > 30) {
        JOptionPane.showMessageDialog(null, "Invalid input. Rows set to default.");
        game.setRows(9);
      } else {
        game.setRows(Integer.parseInt(row));
      }

      String col = JOptionPane.showInputDialog(null, "Enter the desired number of columns (4-30):");
      if (col != null) {
        if (checkForNumbers(col) == false || col.isEmpty() || Integer.parseInt(col) < 4
            || Integer.parseInt(col) > 30) {
          JOptionPane.showMessageDialog(null, "Invalid input. Columns set to default.");
          game.setCols(9);
        } else {
          game.setCols(Integer.parseInt(col));
        }

        String mines = JOptionPane.showInputDialog(null, "Enter the desired mine count:");
        if (mines != null) {
          if (checkForNumbers(mines) == false || mines.isEmpty()
              || Integer.parseInt(mines) >= (game.getRows() * game.getCols())) {
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

    timer.cancel();
    setSize(colSize, rowSize);
    game.reset();
    remove(gamePanel);
    createButtons();
    display();
    add(gamePanel);
    repaint();
    revalidate();
    firstMove = true;
    timer = new Timer();
    timeLabel.setText("Time: 0:00");
    mineCountLabel.setText("Mine Count: " + game.mineCount());
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
      timer.cancel();
      game.reset();
      remove(gamePanel);
      createButtons();
      display();
      add(gamePanel);
      repaint();
      revalidate();
      firstMove = true;
      timer = new Timer();
      timeLabel.setText("Time: 0:00");
      mineCountLabel.setText("Mine Count: " + game.mineCount());
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
  
  private void displayRanks(File file) {
    String names = "NAME: \n"; //Result of our leaderboard, separated.
    String scores = "SCORE: \n";
    String dates = "DATE: \n";
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      String line;
      try {
        while ((line = br.readLine()) != null) { //Collects the names, scores, and dates separately.
          names = names + line.substring(0,21) + "\n"; 
          scores = scores + line.substring(21, 26) + "\n";
          dates = dates + line.substring(28, 47) + "\n";
        }
      } catch (IOException except) {
        // TODO Auto-generated catch block
        except.printStackTrace();
      }
    } catch (FileNotFoundException except) {
      // TODO Auto-generated catch block
      except.printStackTrace();
    }
    
    JLabel nameLabel = new JLabel();
    JLabel scoreLabel = new JLabel();
    JLabel dateLabel = new JLabel();
    
    //Have to do some gross HTML stuff, because that's how JLabels work apparently.
    nameLabel.setText("<html>" + names.replaceAll("<","&lt;").replaceAll(">", "&gt;")
        .replaceAll("\n", "<br/>") + "</html>");
    scoreLabel.setText("<html>" + scores.replaceAll("<","&lt;").replaceAll(">", "&gt;")
        .replaceAll("\n", "<br/>") + "</html>");
    dateLabel.setText("<html>" + dates.replaceAll("<","&lt;").replaceAll(">", "&gt;")
        .replaceAll("\n", "<br/>") + "</html>");
    
    JPanel leadPanel = new JPanel();
    leadPanel.add(nameLabel);
    leadPanel.add(scoreLabel);
    leadPanel.add(dateLabel);
    
    JOptionPane.showMessageDialog(null, leadPanel);
  }
  
  private void updateRanks(File file, int finalScore) { 
    
    boolean broke = false; //Will check to see if we broke our loop.
    int rank = 1; //Will keep track of what rank we are, if we are a high score.
    String name = ""; //This will hold our high score name.
    String stringScore = ""; //This will hold our final score, after it's formatted.
    
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        line = line.substring(21, 26); //Substring to the scores of each rank, so we can compare.
        if (finalScore > Integer.parseInt(line)) { //Goes in, if we have a better score.
          stringScore = String.format("%05d", finalScore);
          name = JOptionPane.showInputDialog(null, "Congrats on the high score! Enter "
              + "your name (max 20 charcters).");
          name = name.trim();
          //This will format the name so that it lines up with the others in a text file.
          if (name.length() > 20) {
            name = name.substring(0, 20) + " ";
          } else {
            while (name.length() <= 20) {
              name = name + " ";
            }
          }
          //Once we found a high score, we want to leave.
          broke = true;
          break;
        }
        //If we didn't find a score yet, check to see if we're a rank lower.
        rank++;
      }
    } catch (NumberFormatException except) {
      // TODO Auto-generated catch block
      except.printStackTrace();
    } catch (HeadlessException except) {
      // TODO Auto-generated catch block
      except.printStackTrace();
    } catch (IOException except) {
      // TODO Auto-generated catch block
      except.printStackTrace();
    }
    //If we found a high score, we want to collect the other four scores we're keeping.
    if (broke) {
      int index = 0;
      String rankA = "";
      String rankB = "";
      String rankC = "";
      String rankD = "";
      String line;
      
      try {
        BufferedReader br = new BufferedReader(new FileReader(file));
        
        try {
          //Collecting the other four scores.
          while ((line = br.readLine()) != null) {
            if (index == 0) {
              rankA = line;
            } else if (index == 1) {
              rankB = line;
            } else if (index == 2) {
              rankC = line;
            } else if (index == 3) {
              rankD = line;
            }
            index++;
          }
        } catch (IOException except) {
          // TODO Auto-generated catch block
          except.printStackTrace();
        }
      } catch (FileNotFoundException except) {
        // TODO Auto-generated catch block
        except.printStackTrace();
      }
      
      PrintWriter writer;
      try {
        writer = new PrintWriter(file, "UTF-8");
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        
        //If we have the best rank, we'll put it on the top.
        if (rank == 1) {
          writer.println(name + stringScore + "  " + dateFormat.format(date));
          writer.println(rankA);
          writer.println(rankB);
          writer.println(rankC);
          writer.println(rankD);
        } else if (rank == 2) { //Second best, second from the top.
          writer.println(rankA);
          writer.println(name + stringScore + "  " + dateFormat.format(date));
          writer.println(rankB);
          writer.println(rankC);
          writer.println(rankD);
        } else if (rank == 3) { //Third best, middle rank.
          writer.println(rankA);
          writer.println(rankB);
          writer.println(name + stringScore + "  " + dateFormat.format(date));
          writer.println(rankC);
          writer.println(rankD);
        } else if (rank == 4) { //Forth best, second from the bottom.
          writer.println(rankA);
          writer.println(rankB);
          writer.println(rankC);
          writer.println(name + stringScore + "  " + dateFormat.format(date));
          writer.println(rankD);
        } else if (rank == 5) { //Worst score goes on the very bottom.
          writer.println(rankA);
          writer.println(rankB);
          writer.println(rankC);
          writer.println(rankD);
          writer.println(name + stringScore + "  " + dateFormat.format(date));
        }
        writer.close();
      } catch (FileNotFoundException except) {
        // TODO Auto-generated catch block
        except.printStackTrace();
      } catch (UnsupportedEncodingException except) {
        // TODO Auto-generated catch block
        except.printStackTrace();
      }
      
    }
    
  }

  @Override
  public void actionPerformed(ActionEvent event) {

    JComponent buttonPressed = (JComponent) event.getSource();

    for (int row = 0; row < game.getRows(); row++) {
      for (int col = 0; col < game.getCols(); col++) {
        if (board[row][col] == event.getSource() && game.checkFlagged(row, col) == false) {
          if (firstMove) { // This makes it so you can't hit a mine on your very first pick.
            if (game.getCell(row, col).isMine()) {
              game.getCell(row, col).setMine(false);
              Random randomGenerator = new Random();
              int randRow = randomGenerator.nextInt(game.getRows());
              int randCol = randomGenerator.nextInt(game.getCols());
              while (game.getCell(randRow, randCol).isMine()
                  || (row == randRow && col == randCol)) {
                randRow = randomGenerator.nextInt(game.getRows());
                randCol = randomGenerator.nextInt(game.getCols());
              }
              game.getCell(randRow, randCol).setMine(true);
              board[row][col].setIcon(null);
              if (mineFlag) {
                board[randRow][randCol].setIcon(mine);
              }
            }
            firstMove = false;
            timer.schedule(new Updateclock(), 0, 1000);
          }
          game.select(row, col);
          game.flood(row, col);
          if (game.getGameStatus() == 0) {
            // Losing Sound Effect
            String sound = "Sad_Trombone.wav";
            AudioInputStream audioInputStream = null;
            try {
              audioInputStream = AudioSystem.getAudioInputStream(new File(sound).getAbsoluteFile());
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
            timer.cancel();
            JOptionPane.showMessageDialog(null, "You hit a mine. Game Over.");
            // disabling the buttons
            for (int i = 0; i < game.getRows(); i++) {
              for (int j = 0; j < game.getCols(); j++) {
                if (game.getCell(i,  j).isFlagged() == true) {
                  board[i][j].setIcon(null);
                }
                game.getCell(i, j).setExposed(true);
              }
            }
            board[row][col].setBackground(Color.PINK);
            losses++;
          } else if (game.getGameStatus() == 1) {
            // Winning Sound Effect
            String sound = "Ta_Da.wav";
            AudioInputStream audioInputStream = null;
            try {
              audioInputStream = AudioSystem.getAudioInputStream(new File(sound).getAbsoluteFile());
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

            // disabling the buttons
            for (int i = 0; i < game.getCols(); i++) {
              for (int j = 0; j < game.getRows(); j++) {
                game.getCell(i, j).setExposed(true);
              }
            }
            // All of this junk is making the score..
            timer.cancel();
            String time = String.valueOf(timeLabel.getText());
            time = time.substring(5).trim();
            String[] seconds = time.split(":");
            int timeScore = Integer.parseInt(seconds[0]) * 60 + Integer.parseInt(seconds[1]);
            int finalScore;
            if (timeScore == 0) {
              timeScore = 1;
            }
            if ((game.getRows() * game.getCols()) / 2 >= game.getMineCount()) {
              finalScore = (game.getRows() * game.getCols() * game.getMineCount() * 1000)
                  / timeScore;
            } else if ((game.getRows() * game.getCols() - 1) == game.getMineCount()) {
              finalScore = 10000; //This way, if the user "auto-wins" they don't get a great score.
            } else {
              finalScore = (game.getRows() * game.getCols()
                  * ((game.getRows() * game.getCols()) - game.getMineCount()) * 1000) / timeScore;
            }

            JOptionPane.showMessageDialog(null,
                "Congratulations! You won the game. \n Score : " + finalScore);
            wins++;
           
            File file = new File("Leaders.txt");
            updateRanks(file, finalScore);
            displayRanks(file);
          }
        }
      }
      updateLabels();

      display();

      if (buttonPressed == resetButton) {
        game.reset();
        timer.cancel();
        firstMove = true;
        mineFlag = false;
        
        for (int i = 0; i < game.getRows(); i++) {
          for (int j = 0; j < game.getCols(); j++) {
            board[i][j].setBackground(null);
          }
        }

        timer = new Timer();
        timeLabel.setText("Time: 0:00");
        mineCountLabel.setText("Mine Count: " + game.mineCount());
        resetButtonText();
      }

    }

    if (buttonPressed == customGame) {
      custom();
    }

    if (buttonPressed == difficultyGame) {
      difficulty();
    }

    if (buttonPressed == quitGame) {
      int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit the game?",
          "Quit", JOptionPane.YES_NO_OPTION);
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
              if (cell.isFlagged()) {
                board[row2][col2].setIcon(flag);
              } else {
                board[row2][col2].setIcon(null);
              }
            }
          }
        }
        mineFlag = false;
      }
      updateLabels();
      display();
    }
    
    if (buttonPressed == ranksGame) {
      File file = new File("Leaders.txt");
      displayRanks(file);
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

  /**
   * Enables right mouse clicks for mine flagging.
   * 
   * @param event
   *          the registered mouse click event
   */
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

  private class Updateclock extends TimerTask {

    private int seconds = 0;
    private int minutes = 0;

    @Override
    public void run() {
      EventQueue.invokeLater(new Runnable() {

        @Override
        public void run() {
          String formatted = String.format("%02d", seconds++);
          timeLabel.setText("Time: " + minutes + ":" + formatted);
          if (seconds > 59) {
            minutes++;
            seconds = 0;
          }
        }
      });
    }
  }
}
