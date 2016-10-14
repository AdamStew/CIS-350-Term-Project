package minesweeperpackage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class CellTest {

  Cell testCell = new Cell();

  @Test
  public void isMineTest() {
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
  
  @Test 
  public void zeroNeighborCountTest() {
    Cell testCell3 = new Cell(50, false, false, true);
    testCell3.zeroNeighborCount();
    assertEquals(0, testCell3.getNeighbors());
  }
  
  @Test
  public void addNeighborsTest(){
    
  }

}
