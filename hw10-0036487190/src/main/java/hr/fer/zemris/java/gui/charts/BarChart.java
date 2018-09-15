package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * This class models a single bar chart graph by defining the values of bars,
 * axis descriptions, minimum and maximum y coordinate values and step between
 * two closest y values.
 * 
 * @author Marko Mesarić
 *
 */
public class BarChart {

	/**
	 * list of (x,y) values
	 */
	private List<XYValue> values;
	/**
	 * x axis description
	 */
	private String xDescription;
	/**
	 * y axis description
	 */
	private String yDescription;
	/**
	 * minimum y value
	 */
	private int minY;
	/**
	 * maximum y value
	 */
	private int maxY;
	/**
	 * y step
	 */
	private int yDelta;

	/**
	 * Default constructor
	 * 
	 * @param values
	 *            {@link #values}
	 * @param xDescription
	 *            {@link #xDescription}
	 * @param yDescription
	 *            {@link #yDescription}
	 * @param minY
	 *            {@link #minY}
	 * @param maxY
	 *            {@link #maxY}
	 * @param yDelta
	 *            {@link #yDelta}
	 */
	public BarChart(List<XYValue> values, String xDescription, String yDescription, int minY, int maxY, int yDelta) {
		super();
		this.values = values;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.minY = minY;
		this.maxY = maxY;
		this.yDelta = yDelta;
	}

	/**
	 * Getter for list of (x,y) values
	 * 
	 * @return {@link #values}
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Getter for x axis description
	 * 
	 * @return {@link #xDescription}
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * Getter for y axis description
	 * 
	 * @return {@link #yDescription}
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Getter for minimum y value
	 * 
	 * @return {@link #minY}
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Getter for maximum y value
	 * 
	 * @return {@link #maxY}
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Getter for y step
	 * 
	 * @return {@link #yDelta}
	 */
	public int getYDelta() {
		return yDelta;
	}

}
