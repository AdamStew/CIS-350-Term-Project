package minesweeperpackage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class CellTest {

  Cell testCell = new Cell();

  @Test
  public void isMineTest() {
    fail("Not yet implemented");
  }

  @Test
  public void isMineTest2() {
    assertEquals(false, testCell.isMine());
  }

  @Test
  public void isFlaggedTest() {
    assertEquals(false, testCell.isFlagged());
  }

  @Test
  public void isExposedTest() {
    assertEquals(false, testCell.isExposed());
  }

  @Test
  public void setMineTest() {
    testCell.setMine(true);
    assertEquals(true, testCell.isMine());
  }

  @Test
  public void setExposedTest() {
    testCell.setExposed(true);
    assertEquals(true, testCell.isExposed());
  }

  @Test
  public void setFlaggedTest() {
    testCell.setFlagged(true);
    assertEquals(true, testCell.isFlagged());
  }

  @Test
  public void cellConstructorTest() {
    Cell testCell2 = new Cell(10, false, false, true);
    assertEquals(false, testCell2.isFlagged());
    assertEquals(false, testCell2.isExposed());
    assertEquals(true, testCell2.isMine());
    assertEquals(10, testCell2.getNeighbors());
  }

}
