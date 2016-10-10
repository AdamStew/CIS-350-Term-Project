package package1;

import static org.junit.Assert.*;

import org.junit.Test;

public class CellTest {

<<<<<<< HEAD
  Cell testCell = new Cell();


  @Test
  public void isMineTest() {
    fail("Not yet implemented");
  }

=======
	@Test
	public void isMineTest() {
	  assertEquals( false, testCell.isMine());
	  }
	
	@Test
	public void isFlaggedTest() {
	  assertEquals( false, testCell.isFlagged());
	}
	
	@Test
	public void isExposedTest() {
	  assertEquals( false, testCell.isExposed());
	}
	
	public void setMineTest() {
	  testCell.setMine(true);
	  assertEquals( true, testCell.isMine());
	}
	
	public void setExposedTest(){
	  testCell.setExposed(true);
	  assertEquals( true, testCell.isExposed());
	}
	
	public void setFlaggedTest() {
	  testCell.setFlagged(true);
	  assertEquals(true, testCell.isFlagged());
	}
	
	public void cellConstructorTest(){
	  Cell testCell2 = new Cell( 10, false, false, true);
	  assertEquals(false, testCell2.isFlagged());
	  assertEquals( false, testCell2.isExposed());
	  assertEquals( true, testCell2.isMine());
	  assertEquals( 10, testCell2.getNeighbors());
	}
>>>>>>> refs/remotes/origin/master
}
