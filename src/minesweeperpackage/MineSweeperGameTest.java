package minesweeperpackage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MineSweeperGameTest {

  @Test
  public void testConstruct1() {
    MineSweeperGame game = new MineSweeperGame();
    assertEquals(game.getCols(), 9);
    assertEquals(game.getRows(), 9);
    assertEquals(game.getMineCount(), 10);
  }

  @Test
  public void testReset1() {
    MineSweeperGame game = new MineSweeperGame();
    game.select(1, 1);
    assertTrue(game.getCell(1, 1).isExposed());
    game.reset();
    assertFalse(game.getCell(1, 1).isExposed());
  }

  @Test
  public void testGetCell1() {
    MineSweeperGame game = new MineSweeperGame();
    assertFalse(game.getCell(1, 1).isFlagged());
  }

  @Test
  public void testGetCell2() {
    MineSweeperGame game = new MineSweeperGame();
    assertFalse(game.getCell(7, 8).isFlagged());
  }

  @Test
  public void testGetMineCount1() {
    MineSweeperGame game = new MineSweeperGame();
    assertEquals(game.getMineCount(), 10);
  }

  @Test
  public void testGetRows1() {
    MineSweeperGame game = new MineSweeperGame();
    assertEquals(game.getRows(), 9);
  }

  @Test
  public void testGetCols1() {
    MineSweeperGame game = new MineSweeperGame();
    assertEquals(game.getCols(), 9);
  }

  @Test
  public void testGetNeighborCount1() {
    MineSweeperGame game = new MineSweeperGame();
    assertTrue(game.getNeighborCount(1, 1) >= 0 && game.getNeighborCount(1, 1) <= 8);
  }

  @Test
  public void testSelect1() {
    MineSweeperGame game = new MineSweeperGame();
    assertFalse(game.getCell(1, 1).isExposed());
    if (!game.getCell(1, 1).isMine()) {
      game.select(1, 1);
      assertTrue(game.getCell(1, 1).isExposed());
    } else {
      assertFalse(game.getCell(1, 1).isExposed());
    }
  }

  @Test
  public void testSelect2() {
    MineSweeperGame game = new MineSweeperGame();
    assertFalse(game.getCell(7, 3).isExposed());
    if (!game.getCell(7, 3).isMine()) {
      game.select(7, 3);
      assertTrue(game.getCell(7, 3).isExposed());
    } else {
      assertFalse(game.getCell(7, 3).isExposed());
    }
  }

  @Test
  public void testFlag1() {
    MineSweeperGame game = new MineSweeperGame();
    game.flag(1, 1);
    assertTrue(game.getCell(1, 1).isFlagged());
  }

  @Test
  public void testFlag2() {
    MineSweeperGame game = new MineSweeperGame();
    game.flag(8, 5);
    assertTrue(game.getCell(8, 5).isFlagged());
  }

  @Test
  public void testUnFlag1() {
    MineSweeperGame game = new MineSweeperGame();
    game.flag(1, 1);
    assertTrue(game.getCell(1, 1).isFlagged());
    game.unflag(1, 1);
    assertFalse(game.getCell(1, 1).isFlagged());
  }

  @Test
  public void testUnFlag2() {
    MineSweeperGame game = new MineSweeperGame();
    game.flag(8, 5);
    assertTrue(game.getCell(8, 5).isFlagged());
    game.unflag(8, 5);
    assertFalse(game.getCell(8, 5).isFlagged());
  }

  @Test
  public void testCheckFlagged1() {
    MineSweeperGame game = new MineSweeperGame();
    assertFalse(game.checkFlagged(1, 1));
  }

  @Test
  public void testCheckFlagged2() {
    MineSweeperGame game = new MineSweeperGame();
    game.flag(2, 3);
    assertTrue(game.checkFlagged(2, 3));
  }

  @Test
  public void testGameStatus1() {
    MineSweeperGame game = new MineSweeperGame();
    assertEquals(game.getGameStatus(), -1);
  }

  @Test
  public void testGameStatus2() {
    MineSweeperGame game = new MineSweeperGame();
    for (int rows = 0; rows < game.getRows(); rows++) {
      for (int cols = 0; cols < game.getCols(); cols++) {
        if (game.getCell(rows, cols).isMine() == false) {
          game.select(rows, cols);
        }
      }
    }
    assertEquals(game.getGameStatus(), 1);
  }

  @Test
  public void testGameStatus3() {
    boolean broken = false;
    MineSweeperGame game = new MineSweeperGame();
    for (int rows = 0; rows < game.getRows(); rows++) {
      for (int cols = 0; cols < game.getCols(); cols++) {
        if (game.getCell(rows, cols).isMine() == true) {
          game.select(rows, cols);
          broken = true;
          break;
        }
      }
      if (broken) {
        break;
      }
    }
    assertEquals(game.getGameStatus(), 0);
  }
}
