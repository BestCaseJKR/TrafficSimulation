package model.TrafficGrid;
/**
 * Basic class to represent the boundaries of a grid.
 * Could be used to represent the dimensions of a grid 
 * or a single point/dimension.
 * @author johnreagan
 *
 */
public abstract class GridDimension {
	/**
	 * the row or number of rows in the grid
	 */
	private int row;
	/**
	 * return the number of rows
	 * @return
	 */
	public int getRow() {
		return row;
	}
	/**
	 * Set the row variable
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}
	/**
	 * The number of columns or column in the grid
	 */
	private int column;
	/**
	 * Return the column var
	 * @return
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * set the column var
	 * @param column
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	/**
	 * Constructor. Create the GridDimensions object with the supplied values
	 * @param row
	 * @param column
	 */
	public GridDimension(int row, int column) {
		this.setRow(row);
		this.setColumn(column);
	}
	
}
