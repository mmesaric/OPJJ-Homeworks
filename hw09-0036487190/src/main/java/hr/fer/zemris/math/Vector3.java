package hr.fer.zemris.math;

import static java.lang.Math.sqrt;
import java.util.Objects;
import static java.lang.Math.pow;

/**
 * This class represents the implementation of a 3D vector model which is based
 * on x,y,z coordinates. It offers the usual operations performed on 3D vectors
 * such as addition, subtraction, dot product of 2 vectors, cross product of 2
 * vectors, scaling etc. This object and its values are unmodifiable.
 * 
 * @author Marko Mesarić
 *
 */
public class Vector3 {

	/**
	 * x coordinate of this vector
	 */
	private final double x;
	/**
	 * y coordinate of this vector
	 */
	private final double y;
	/**
	 * z coordinate of this vector
	 */
	private final double z;

	/**
	 * Default constructor
	 * 
	 * @param x
	 *            given x coordinate
	 * @param y
	 *            given y coodinate
	 * @param z
	 *            given z coordinate
	 */
	public Vector3(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Method which calculates and returns the norm of this vector.
	 * 
	 * @return vector norm
	 */
	public double norm() {
		return sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
	}

	/**
	 * Method which calculates and returns normalized vector of this vector
	 * 
	 * @return normalized vector
	 */
	public Vector3 normalized() {
		double norm = norm();

		if (norm == 0) {
			return new Vector3(0, 0, 0);
		}
		return new Vector3(x / norm, y / norm, z / norm);
	}

	/**
	 * Method which performs the addition of this and other vector
	 * 
	 * @throws NullPointerException
	 *             in case of other vector being null
	 * @param other
	 *            vector to be added
	 * @return result of addition vector
	 */
	public Vector3 add(Vector3 other) {
		Objects.requireNonNull(other, "Other vector to be added can't be null");

		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	/**
	 * Method which performs the subtraction of this and other vector
	 * 
	 * @throws NullPointerException
	 *             in case of other vector being null
	 * @param other
	 *            vector to be subtracted
	 * @return result of subtraction vector
	 */
	public Vector3 sub(Vector3 other) {
		Objects.requireNonNull(other, "Other vector to be subtracted can't be null");

		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	/**
	 * Method which performs the dot product of this and other vector
	 * 
	 * @throws NullPointerException
	 *             in case of other vector being null
	 * @param other
	 *            vector used in dot product along this one
	 * @return result of dot product
	 */
	public double dot(Vector3 other) {
		Objects.requireNonNull(other, "Other vector can't be null when calculating dot product.");

		return x * other.x + y * other.y + z * other.z;
	}

	/**
	 * Method which performs the cross product of this and other vector
	 * 
	 * @throws NullPointerException
	 *             in case of other vector being null
	 * @param other
	 *            vector used in cross product along this one
	 * @return result of cross product
	 */
	public Vector3 cross(Vector3 other) {
		Objects.requireNonNull(other, "Other vector can't be null when calculating cross product.");

		double crossX = y * other.z - z * other.y;
		double crossY = z * other.x - x * other.z;
		double crossZ = x * other.y - y * other.x;

		return new Vector3(crossX, crossY, crossZ);
	}

	/**
	 * Method which performs the scaling of this vector with passed value and
	 * returns the newly generated, scaled vector
	 * 
	 * @param s
	 *            scaling
	 * @return scaled vector
	 */
	public Vector3 scale(double s) {

		return new Vector3(x * s, y * s, z * s);
	}

	/**
	 * Method which returns the cos angle between this and passed vector
	 * 
	 * @param other other vector
	 * @return value of cos(angle)
	 */
	public double cosAngle(Vector3 other) {
		Objects.requireNonNull(other, "Other vector can't be null when calculating cos value of two vectors' angles.");

		double dot = dot(other);
		double divideWith = this.norm() * other.norm();

		if (divideWith == 0) {
			throw new IllegalArgumentException("Can't devide with zero when calculating cos value.");
		}

		return (dot / divideWith);
	}

	/**
	 * Getter for x coordinate
	 * @return x coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter for y coordinate
	 * @return y coordinate
	 */
	public double getY() {
		return y;
	}

	/**
	 * Getter for z coordinate
	 * @return z coordinate
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Returns array of coordinates
	 * @return array of coordinates
	 */
	public double[] toArray() {

		return new double[] { x, y, z };
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return "(" + String.format("%.6f", x) + ", " + String.format("%.6f", y) + ", " + String.format("%.6f", z) + ")";
	}
}
