package hr.fer.zemris.java.gui.layouts;

/**
 * Class which models a single constraint in CalcLayout by defining read-only
 * row and column value.
 * 
 * @author Marko Mesarić
 *
 */
public class RCPosition {

	/**
	 * Number of row
	 */
	private final int row;
	/**
	 * Number of column
	 */
	private final int column;

	/**
	 * Default constructor which does the initial variable initialization.
	 * 
	 * @param row
	 *            number of row
	 * @param column
	 *            number of column
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * Getter for row value
	 * 
	 * @return row number
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Getter for column value
	 * 
	 * @return column number
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	
}
