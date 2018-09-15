package hr.fer.zemris.java.hw16.jvdraw.object;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.editor.LineEditor;

/**
 * This class models the Line geometric object.
 * 
 * @author Marko Mesarić
 *
 */

public class Line extends GeometricalObject {

	/**
	 * Starting x coordinate
	 */
	private int x1;
	/**
	 * Ending x coordinate
	 */
	private int x2;
	/**
	 * Starting y coordinate
	 */
	private int y1;
	/**
	 * Ending y coordinate
	 */
	private int y2;

	/**
	 * foreground color
	 */
	private Color fgColor;

	/**
	 * 
	 * @param x1
	 *            {@link #x1}
	 * @param x2
	 *            {@link #x2}
	 * @param y1
	 *            {@link #y1}
	 * @param y2
	 *            {@link #y2}
	 * @param fgColor
	 *            foreground color
	 */
	public Line(int x1, int x2, int y1, int y2, Color fgColor) {
		super();
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.fgColor = fgColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	/**
	 * Getter for x1 coordinate
	 * @return {@link #x1}
	 */
	public int getX1() {
		return x1;
	}

	/**
	 * Setter for x1 coordinate
	 * @param x1 {@link #x1}
	 */
	public void setX1(int x1) {
		this.x1 = x1;
		notifyObservers();
	}

	/**
	 * Getter for x2 coordinate
	 * @return {@link #x2}
	 */
	public int getX2() {
		return x2;
	}

	/**
	 * Setter for x2 coordinate
	 * @param x2 {@link #x2}
	 */
	public void setX2(int x2) {
		this.x2 = x2;
		notifyObservers();
	}

	/**
	 * Getter for y1 coordinate
	 * @return {@link #y1}
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * Setter for y1 coordinate
	 * @param y1 {@link #y1}
	 */
	public void setY1(int y1) {
		this.y1 = y1;
		notifyObservers();
	}

	/**
	 * Getter for y2 coordinate
	 * @return {@link #y2}
	 */
	public int getY2() {
		return y2;
	}

	/**
	 * Setter for y2 coordinate
	 * @param y2 {@link #y2}
	 */
	public void setY2(int y2) {
		this.y2 = y2;
		notifyObservers();
	}

	/**
	 * Getter for foreground color 
	 * @return {@link #fgColor}
	 */
	public Color getFgColor() {
		return fgColor;
	}

	/**
	 * Setter for foreground color
	 * @param fgColor {@link #fgColor}
	 */
	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
		notifyObservers();
	}

	@Override
	public String toString() {
		return "Line (" + x1 + "," + y1 + ")-(" + x2 + "," + y2 + ")";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}
}
