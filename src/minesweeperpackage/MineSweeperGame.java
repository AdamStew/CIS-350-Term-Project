package minesweeperpackage;

import java.util.Random;

import javax.swing.JOptionPane;

/**
 * A class contains all functions of the Mine Sweeper game itself.
 * 
 * @author Kate McGowan, Adam Stewart, Sierra Ellison
 * 
 * @version 1.0
 */
public class MineSweeperGame {

  private int mineCount;
  private Cell[][] board;
  private int rows;
  private int cols;

  /**
   * Constructor that creates the board/game and sets it to easy by default.
   */
  public MineSweeperGame() {
    // This starts the game with easy-mode initiated by default.
    this.rows = 9;
    this.cols = 9;
    this.mineCount = 10;
    reset();
  }

  /**
   * A method that resets the board to a new game.
   */
  public void reset() {

    board = new Cell[this.rows][this.cols];

    for (int row = 0; row < this.rows; row++) {
      for (int col = 0; col < this.cols; col++) {
        board[row][col] = new Cell(0, false, false, false);
      }
    }

    Random random = new Random();
    int count = 0;
    while (count < this.mineCount) {
      int col = random.nextInt(cols);
      int row = random.nextInt(rows);
      if (!board[row][col].isMine()) {
        board[row][col].setMine(true);
        count++;
      }

    }
  }

  /**
   * A method that returns a desired call based on its' row and column.
   * 
   * @param row
   *          - An integer determining what row to look for.
   * 
   * @param col
   *          - An integer determining what column to look for.
   */
  public Cell getCell(int row, int col) {
    return board[row][col];
  }

  /**
   * A method that returns the total amount of mines.
   * 
   * @return An integer value of the amount of mines.
   */
  public int getMineCount() {
    return this.mineCount;
  }

  public void setMineCount(int mineCount) {
    this.mineCount = mineCount;
  }

  /**
   * A method that returns the number of the row.
   * 
   * @return An integer value of what numbered row you're in.
   */
  public int getRows() {
    return this.rows;
  }

  public void setRows(int rowCount) {
    this.rows = rowCount;
  }

  /**
   * A method that returns the number of the column.
   * 
   * @return An integer value of what numbered column you're in.
   */
  public int getCols() {
    return this.cols;
  }

  public void setCols(int colCount) {
    this.cols = colCount;
  }

  /**
   * A method that adds up number of mines on cells around it.
   * 
   * @param row
   *          - An integer determining what row to look for.
   * 
   * @param col
   *          - An integer determining what column to look for.
   * 
   * @return An integer value of the total amount of mines surrounding a given cell.
   */
  public int getNeighborCount(int row, int col) {
    board[row][col].zeroNeighborCount();
    // upper left square
    if ((row - 1 < getRows() && row - 1 >= 0) && (col - 1 < getCols() && col - 1 >= 0)) {
      if (board[row - 1][col - 1].isMine()) {
        board[row][col].addNeighbors(1);
      }
    }
    // upper middle square
    if ((row - 1 < getRows() && row - 1 >= 0) && (col < getCols() && col >= 0)) {
      if (board[row - 1][col].isMine()) {
        board[row][col].addNeighbors(1);
      }
    }
    // upper right square
    if ((row - 1 < getRows() && row - 1 >= 0) && (col + 1 < getCols() && col + 1 >= 0)) {
      if (board[row - 1][col + 1].isMine()) {
        board[row][col].addNeighbors(1);
      }
    }
    // middle left square
    if ((row < getRows() && row >= 0) && (col - 1 < getCols() && col - 1 >= 0)) {
      if (board[row][col - 1].isMine()) {
        board[row][col].addNeighbors(1);
      }
    }
    // middle right square
    if ((row < getRows() && row >= 0) && (col + 1 < getCols() && col + 1 >= 0)) {
      if (board[row][col + 1].isMine()) {
        board[row][col].addNeighbors(1);
      }
    }
    // lower left square
    if ((row + 1 < getRows() && row + 1 >= 0) && (col - 1 < getCols() && col - 1 >= 0)) {
      if (board[row + 1][col - 1].isMine()) {
        board[row][col].addNeighbors(1);
      }
    }
    // lower middle square
    if ((row + 1 < getRows() && row + 1 >= 0) && (col < getCols() && col >= 0)) {
      if (board[row + 1][col].isMine()) {
        board[row][col].addNeighbors(1);
      }
    }
    // lower right square
    if ((row + 1 < getRows() && row + 1 >= 0) && (col + 1 < getCols() && col + 1 >= 0)) {
      if (board[row + 1][col + 1].isMine()) {
        board[row][col].addNeighbors(1);
      }
    }
    int neighborCount = board[row][col].getNeighbors();

    return neighborCount;
  }

  /**
   * A method that triggers the surrounding cells that contain a zero neighbor count.
   * 
   * @param row
   *          - An integer determining what row to look for.
   * 
   * @param col
   *          - An integer determining what column to look for.
   */
  public void flood(int row, int col) {
    if (board[row][col].isFlagged()) {
      return;
    }
    if (!board[row][col].isExposed()) {
      board[row][col].setExposed(true);

      if (board[row][col].isMine()) {
        return;
      }
      if (getNeighborCount(row, col) == 0) {
        for (int i = -1; i < 2; i++) {
          for (int j = -1; j < 2; j++) {
            if (row + i < getRows() && row + i >= 0 && col + j < getCols() && col + j >= 0) {
              flood(row + i, col + j);
            }
          }
        }
      }
    }
  }

  /**
   * A method that triggers the selected cell (that is clicked on).
   * 
   * @param row
   *          - An integer determining what row to look for.
   * 
   * @param col
   *          - An integer determining what column to look for.
   */
  public void select(int row, int col) {
    if (board[row][col].isFlagged()) {
      return;
    }
    if (board[row][col].isMine()) {
      board[row][col].setExposed(true);
      return;
    } else {
      if (getNeighborCount(row, col) == 0) {
        flood(row, col);
      }
    }
    board[row][col].setExposed(true);
  }

  /**
   * A method that sets the selected cell to be flagged.
   * 
   * @param row
   *          - An integer determining what row to look for.
   * 
   * @param col
   *          - An integer determining what column to look for.
   */
  public void flag(int row, int col) {
    board[row][col].setFlagged(true);
  }

  /**
   * A method that sets the selected cell to not be flagged anymore.
   * 
   * @param row
   *          - An integer determining what row to look for.
   * 
   * @param col
   *          - An integer determining what column to look for.
   */
  public void unflag(int row, int col) {
    board[row][col].setFlagged(false);
  }

  /**
   * A method that checks to see if it is flagged or not.
   * 
   * @param row
   *          - An integer determining what row to look for.
   * 
   * @param col
   *          - An integer determining what column to look for.
   */
  public boolean checkFlagged(int row, int col) {
    if (board[row][col].isFlagged() == true) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * A method that returns the current game status.
   */
  public int getGameStatus() {
    int count = 0;
    for (int row = 0; row < getRows(); row++) {
      for (int col = 0; col < getCols(); col++) {
        if (board[row][col].isMine() && board[row][col].isExposed()) {
          return 0;
        } else if (board[row][col].isExposed()) {
          count++;
        }
      }

      int nonMines = (this.rows * this.cols) - getMineCount();
      if (count == nonMines) {
        return 1;
      }

    }
    return -1;
  }
}
