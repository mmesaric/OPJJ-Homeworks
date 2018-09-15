package hr.fer.zemris.java.gui.charts;

/**
 * Class which models the single bar by defining x and y coordinate value.
 * 
 * @author Marko Mesarić
 *
 */
public class XYValue implements Comparable<XYValue> {

	/**
	 * x coordinate value
	 */
	private final int x;
	/**
	 * y coordinate value
	 */
	private final int y;

	/**
	 * Default constructor
	 * @param x {@link #x}
	 * @param y {@link #y}
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for x value
	 * @return {@link #x}
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter for y value
	 * @return {@link #y}
	 */
	public int getY() {
		return y;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(XYValue xyValue) {
		return Integer.valueOf(x).compareTo(Integer.valueOf(xyValue.x));
	}
	
	
}
