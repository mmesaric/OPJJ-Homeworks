package hr.fer.zemris.java.hw16.jvdraw.object;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.editor.CircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.editor.GeometricalObjectEditor;

/**
 * This class models the circle geometric object.
 * 
 * @author Marko Mesarić
 *
 */
public class Circle extends GeometricalObject {

	/**
	 * Center x coordinate
	 */
	private int x;
	/**
	 * Center y coordinate
	 */
	private int y;
	/**
	 * Radius value
	 */
	private int r;

	/**
	 * foreground color
	 */
	private Color fgColor;

	/**
	 * 
	 * @param x {@link #x}
	 * @param y {@link #y}
	 * @param r {@link #r}
	 * @param fgColor {@link #fgColor}
	 */
	public Circle(int x, int y, int r, Color fgColor) {
		super();
		this.x = x;
		this.y = y;
		this.r = r;
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
	 * Getter for x coordinate
	 * @return {@link #x}
	 */
	public int getX() {
		return x;
	}

	/**
	 * Setter for x coordinate
	 * @param x {@link #x}
	 */
	public void setX(int x) {
		this.x = x;
		notifyObservers();
	}

	/**
	 * Getter for y coordinate
	 * @return {@link #y}
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setter for y coordinate
	 * @param y {@link #y}
	 */
	public void setY(int y) {
		this.y = y;
		notifyObservers();
	}

	/**
	 * Getter for radius value
	 * @return {@link #r}
	 */
	public int getR() {
		return r;
	}

	/**
	 * Setter for radius
	 * @param r {@link #r}
	 */
	public void setR(int r) {
		this.r = r;
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
		return "Circle (" + x + "," + y + "), " + r;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}
}
