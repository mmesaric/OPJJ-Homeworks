package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Class responsible for acting as model for specific Turtle state and
 * remembering the current turtle state.
 * 
 * @author Marko Mesarić
 *
 */
public class TurtleState {

	/**
	 * Current position of the turtle
	 */
	private Vector2D state;
	/**
	 * Direction vector for current turtle state
	 */
	private Vector2D direction;
	/**
	 * Color of the current turtle state
	 */
	private Color color;
	/**
	 * Effective step length
	 */
	private double delta;

	/**
	 * Constructor used for initializing attribute values of Turtle state to given
	 * values
	 * 
	 * @param state
	 *            current position of turtle as Vector2D
	 * @param direction
	 *            direction vector for current turtle position as Vector2D
	 * @param color
	 *            given color of this turtle state
	 * @param delta
	 *            effective step length for this turtle state
	 */
	public TurtleState(Vector2D state, Vector2D direction, Color color, double delta) {
		super();
		this.state = state;
		this.direction = direction;
		this.color = color;
		this.delta = delta;
	}

	/**
	 * Method used for getting a copy of the current turtle state
	 * 
	 * @return copy of this turtle state
	 */
	public TurtleState copy() {
		return new TurtleState(state.copy(), direction.copy(), color, delta);
	}

	/**
	 * Getter method for current state
	 * 
	 * @return current turtle state
	 */
	public Vector2D getState() {
		return state;
	}

	/**
	 * Getter method for direction vector
	 * 
	 * @return current direction vector
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Getter method for current color
	 * 
	 * @return turtle state color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Getter method for effective step length
	 * 
	 * @return effective step length of current state
	 */
	public double getDelta() {
		return delta;
	}

	/**
	 * Setter method for color value
	 * 
	 * @param color
	 *            given color value
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Setter method for state value;
	 * 
	 * @param state
	 *            given state to be set
	 */
	public void setState(Vector2D state) {
		this.state = state;
	}

	/**
	 * Setter method for direction vector
	 * 
	 * @param direction
	 *            given direction vector to be set
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Setter method for effective step length
	 * 
	 * @param delta
	 *            given effective step length to be set
	 */
	public void setDelta(double delta) {
		this.delta = delta;
	}

}
