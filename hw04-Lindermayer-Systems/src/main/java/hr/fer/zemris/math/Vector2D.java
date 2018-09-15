package hr.fer.zemris.math;

import static java.lang.Math.PI;

/**
 * This class represents the model of a simple radius vector consisting of two
 * real number components, X and Y coordinate of this vector. This
 * implementation offers simple methods for different manipulations with current
 * vector (such as translation, rotation and scaling). Depending on method,
 * current vector object is modified or new Vector object is returned
 * 
 * @author Marko Mesarić
 *
 */
public class Vector2D {

	/**
	 * X coordinate of this vector
	 */
	private double x;
	/**
	 * Y coordinate of this vector
	 */
	private double y;

	/**
	 * Minimum angle value constant
	 */
	private static final int MIN_ANGLE = -360;
	/**
	 * Maximum angle value constant
	 */
	private static final int MAX_ANGLE = 360;

	/**
	 * Constructor which sets coordinates to given values
	 * 
	 * @param x
	 *            given x coordinate to be set
	 * @param y
	 *            given y coordinate to be set
	 */
	public Vector2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Method used for modifying current vector object with given offset value with
	 * which this vector is translated
	 * 
	 * @throws IllegalArgumentException
	 *             in case of offset being a null reference
	 * @param offset
	 *            value with which this vector is translated
	 */
	public void translate(Vector2D offset) {
		if (offset == null) {
			throw new IllegalArgumentException("Can't translate with null vector as parameter");
		}

		x += offset.x;
		y += offset.y;
	}

	/**
	 * Method used for generating new vector object with given offset value with
	 * which this new vector is translated without modifying this vector object
	 * 
	 * @throws IllegalArgumentException
	 *             in case of offset being a null reference
	 * @param offset
	 *            value with which new vector is translated
	 */
	public Vector2D translated(Vector2D offset) {
		if (offset == null) {
			throw new IllegalArgumentException("Can't translate with null vector as parameter");
		}

		return new Vector2D(x + offset.x, y + offset.y);
	}

	/**
	 * Method used for rotating this vector object by given angle
	 * 
	 * @throws IllegalArgumentException
	 *             in case of invalid angle
	 * @param angle
	 *            given angle value with which this vector is to be rotated
	 */
	public void rotate(double angle) {

		if (angle < MIN_ANGLE || angle > MAX_ANGLE) {
			throw new IllegalArgumentException("Angle must be in [-360,360] range. Was " + angle);
		}

		double angleInRadians = angle * (PI / 180);

		double pomX = (x * Math.cos(angleInRadians)) - (y * Math.sin(angleInRadians));
		double pomY = (x * Math.sin(angleInRadians)) + (y * Math.cos(angleInRadians));
		this.x = pomX;
		this.y = pomY;

	}

	/**
	 * Method used for creating new Vector object which is rotated by given angle
	 * value
	 * 
	 * @throws IllegalArgumentException
	 *             in case of invalid angle
	 * @param angle
	 *            given angle value with which new vector object is to be rotated
	 */
	public Vector2D rotated(double angle) {

		if (angle < MIN_ANGLE || angle > MAX_ANGLE) {
			throw new IllegalArgumentException("Angle must be in [-360,360] range. Was " + angle);
		}

		double angleInRadians = angle * (PI / 180);

		double pomX = (x * Math.cos(angleInRadians)) - (y * Math.sin(angleInRadians));
		double pomY = (x * Math.sin(angleInRadians)) + (y * Math.cos(angleInRadians));
		this.x = pomX;
		this.y = pomY;

		return new Vector2D(x, y);
	}

	/**
	 * Method used for scaling this vector object by given value.
	 * 
	 * @param scaler
	 *            value which is used for scaling
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}

	/**
	 * Method used for scaling new vector object by given value.
	 * 
	 * @param scaler
	 *            value which is used for scaling
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(scaler * x, scaler * y);
	}

	/**
	 * Method used for creating a new Vector object with current values
	 * 
	 * @return new Vector object
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}

	/**
	 * Getter method for X coordinate.
	 * 
	 * @return x coordinate value
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter method for Y coordinate.
	 * 
	 * @return y coordinate value
	 */
	public double getY() {
		return y;
	}

}
