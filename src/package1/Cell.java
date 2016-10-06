package package1;
//Hello
/*****************************************************************
 * 
 * @author Kate McGowan, Adam Stewart, Sierra Ellison
 * 
 * @version 1.0
 * 
 *****************************************************************/

public class Cell {

  private int neighborCount;
  private boolean isFlagged;
  private boolean isExposed;
  private boolean isMine;

<<<<<<< HEAD
  /**
   * A constructor that initializes a default cell.
   */
  public Cell() {
    this.neighborCount = 0;
    this.isFlagged = false;
    this.isExposed = false;
    this.isMine = false;
  }
=======
	/*****************************************************************
	 * A constructor that initializes a default cell
	 *****************************************************************/
	public Cell() {
		this.neighborCount = 0;
		this.isFlagged = false;
		this.isExposed = false;
		this.isMine = false;
	}
>>>>>>> branch 'master' of https://github.com/AdamStew/CIS-350-Term-Project.git

<<<<<<< HEAD
  /**
   * A constructor that initializes a cell based on the parameters.
   * 
   * @param mineCount - the desired mine count
   * @param isFlagged - the desired flag status
   * @param isExposed - the desired exposure status
   * @param isMine - the desired mine status
   */
  public Cell(int mineCount, boolean isFlagged, boolean isExposed, boolean isMine) {
    this.neighborCount = mineCount;
    this.isFlagged = isFlagged;
    this.isExposed = isExposed;
    this.isMine = isMine;
  }
=======
	/*****************************************************************
	 * A constructor that initializes a cell based on the parameters
	 * 
	 * @param mineCount
	 *            the desired mine count
	 * @param isFlagged
	 *            the desired flag status
	 * @param isExposed
	 *            the desired exposure status
	 * @param isMine
	 *            the desired mine status
	 *****************************************************************/
	public Cell(int mineCount, boolean isFlagged, boolean isExposed, boolean isMine) {
		this.neighborCount = mineCount;
		this.isFlagged = isFlagged;
		this.isExposed = isExposed;
		this.isMine = isMine;
	}
>>>>>>> branch 'master' of https://github.com/AdamStew/CIS-350-Term-Project.git

<<<<<<< HEAD
  /**
   * Returns the exposure status of the cell.
   */
  public boolean isExposed() {
    return this.isExposed;
  }
=======
	/*****************************************************************
	 * Returns the exposure status of the cell
	 *****************************************************************/
	public boolean isExposed() {
		return this.isExposed;
	}
>>>>>>> branch 'master' of https://github.com/AdamStew/CIS-350-Term-Project.git

<<<<<<< HEAD
  /**
   * Sets the exposure status of the cell.
   * 
   * @param exposed - a boolean value determining exposure
   */
  public void setExposed(boolean exposed) {
    this.isExposed = exposed;
  }
=======
	/*****************************************************************
	 * Sets the exposure status of the cell
	 * 
	 * @param exposed
	 *            a boolean value determining exposure
	 *****************************************************************/
	public void setExposed(boolean exposed) {
		this.isExposed = exposed;
	}
>>>>>>> branch 'master' of https://github.com/AdamStew/CIS-350-Term-Project.git

<<<<<<< HEAD
  /**
   * Returns the mine status of the cell.
   */
  public boolean isMine() {
    return this.isMine;
  }
=======
	/*****************************************************************
	 * Returns the mine status of the cell
	 *****************************************************************/
	public boolean isMine() {
		return this.isMine;
	}
>>>>>>> branch 'master' of https://github.com/AdamStew/CIS-350-Term-Project.git

<<<<<<< HEAD
  /**
   * Sets the mine status of the cell.
   * 
   * @param mine - a boolean value determining mine status
   */
  public boolean setMine(boolean mine) {
    this.isMine = mine;
    return this.isMine;
  }
=======
	/*****************************************************************
	 * Sets the mine status of the cell
	 * 
	 * @param mine
	 *            a boolean value determining mine status
	 *****************************************************************/
	public boolean setMine(boolean mine) {
		this.isMine = mine;
		return this.isMine;
	}
>>>>>>> branch 'master' of https://github.com/AdamStew/CIS-350-Term-Project.git

<<<<<<< HEAD
  /**
   * Returns the flag status of the cell.
   */
  public boolean isFlagged() {
    return this.isFlagged;
  }
=======
	/*****************************************************************
	 * Returns the flag status of the cell
	 *****************************************************************/
	public boolean isFlagged() {
		return this.isFlagged;
	}
>>>>>>> branch 'master' of https://github.com/AdamStew/CIS-350-Term-Project.git

<<<<<<< HEAD
  /**
   * Sets the flag status of the cell.
   * 
   * @param flagged - a boolean value determining flag status
   */
  public void setFlagged(boolean flagged) {
    this.isFlagged = flagged;
  }
=======
	/*****************************************************************
	 * Sets the flag status of the cell
	 * 
	 * @param flagged
	 *            a boolean value determining flag status
	 *****************************************************************/
	public void setFlagged(boolean flagged) {
		this.isFlagged = flagged;
	}
>>>>>>> branch 'master' of https://github.com/AdamStew/CIS-350-Term-Project.git

<<<<<<< HEAD
  /**
   * Increments the cell's neighbor count.
   * 
   * @param neighbors - number of neighbors to increment
   */
  public void addNeighbors(int neighbors) {
    neighborCount += neighbors;
  }
=======
	/*****************************************************************
	 * Increments the cell's neighbor count
	 * 
	 * @param neighbors
	 *            number of neighbors to increment
	 *****************************************************************/
	public void addNeighbors(int neighbors) {
		neighborCount += neighbors;
	}
>>>>>>> branch 'master' of https://github.com/AdamStew/CIS-350-Term-Project.git

<<<<<<< HEAD
  /**
   * Sets the cell's neighbor count to zero.
   */
  public void zeroNeighborCount() {
    neighborCount = 0;
  }
=======
	/*****************************************************************
	 * Sets the cell's neighbor count to zero
	 *****************************************************************/
	public void zeroNeighborCount() {
		neighborCount = 0;
	}
>>>>>>> branch 'master' of https://github.com/AdamStew/CIS-350-Term-Project.git

<<<<<<< HEAD
  /**
   * Returns the neighbor count of the cell.
   */
  public int getNeighbors() {
    return neighborCount;
  }
=======
	/*****************************************************************
	 * Returns the neighbor count of the cell
	 *****************************************************************/
	public int getNeighbors() {
		return neighborCount;
	}

>>>>>>> branch 'master' of https://github.com/AdamStew/CIS-350-Term-Project.git
}
