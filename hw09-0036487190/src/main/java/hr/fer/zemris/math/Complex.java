package hr.fer.zemris.math;

import static java.lang.Math.sqrt;
import java.util.Objects;
import static java.lang.Math.pow;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.PI;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the implementation of Complex number model. Once
 * generated, object and it's values are unmodifiable. Each method used for
 * complex number transformations returns new object without modifying this
 * object. Used in complex polynomials.
 * 
 * @author Marko Mesarić
 *
 */
public class Complex {

	/**
	 * Constant representing the 0+i0 complex number
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * Constant representing the 1+i0 complex number
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * Constant representing the -1+i0 complex number
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * Constant representing the 0+i1 complex number
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * Constant representing the 0-i1 complex number
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Real part of complex number
	 */
	private final double re;
	/**
	 * Imaginary part of complex number
	 */
	private final double im;

	/**
	 * Default constructor which sets real and imaginary part to zero.
	 */
	public Complex() {
		this.re = 0;
		this.im = 0;
	}

	/**
	 * Default constructor which sets the real and imaginary part to given values
	 * 
	 * @param re
	 * @param im
	 */
	public Complex(double re, double im) {
		super();
		this.re = re;
		this.im = im;
	}

	/**
	 * Method used for calculating the module of this complex number
	 * 
	 * @return module
	 */
	public double module() {
		return sqrt(pow(re, 2) + pow(im, 2));
	}

	/**
	 * Method for multiplying complex numbers
	 * 
	 * @throws NullPointerException
	 *             if other complex number if null
	 * @param other
	 *            with which this number is multiplied
	 * @return result of multiplication
	 */
	public Complex multiply(Complex other) {
		Objects.requireNonNull(other, "Other vector to be multiplied with cannot be null.");

		double real = (this.re * other.re) - (this.im * other.im);
		double imaginary = (this.re * other.im) + (this.im * other.re);

		return new Complex(real, imaginary);
	}

	/**
	 * Method for dividing complex numbers
	 * 
	 * @throws NullPointerException
	 *             if other complex number if null
	 * @param other
	 *            with which this number is divided
	 * @return result of division
	 */
	public Complex divide(Complex other) {
		Objects.requireNonNull(other, "Other vector to be divided with cannot be null.");

		Complex conjugate = new Complex(other.re, -other.im);

		Complex numerator = this.multiply(conjugate);
		Complex denominator = other.multiply(conjugate);

		Complex dividedComplexNumber = new Complex(numerator.re / denominator.re, numerator.im / denominator.re);
		return dividedComplexNumber;
	}

	/**
	 * Method for addition of complex numbers
	 * 
	 * @throws NullPointerException
	 *             if other complex number if null
	 * @param other
	 *            with which this number if added
	 * @return result of addition
	 */
	public Complex add(Complex other) {
		Objects.requireNonNull(other, "Other vector to be added with cannot be null.");

		return new Complex(re + other.re, im + other.im);
	}

	/**
	 * Method for subtraction of complex numbers
	 * 
	 * @throws NullPointerException
	 *             if other complex number if null
	 * @param other
	 *            with which this number if subtracted
	 * @return result of subtraction
	 */
	public Complex sub(Complex other) {
		Objects.requireNonNull(other, "Other vector to be subtracted with cannot be null.");

		return new Complex(re - other.re, im - other.im);
	}

	/**
	 * Method for negation of this complex number
	 * 
	 * @return negated complex number
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}

	/**
	 * Method for calculating power of this complex number
	 * 
	 * @throws IllegalArgumentException
	 *             if n is less than 0
	 * @param n
	 *            power to be calculated
	 * @return result of power
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("When calculating power, n cannot be less than 0.");
		}
		if (n == 0) {
			return new Complex(1, 0);
		}

		double powerMagnitude = pow(this.module(), n);
		double otherReal = powerMagnitude * cos(n * atan2(im, re));
		double otherImaginary = powerMagnitude * sin(n * atan2(im, re));

		return new Complex(otherReal, otherImaginary);
	}

	/**
	 * Method for calculating n'th root of this complex number
	 * 
	 * @throws IllegalArgumentException
	 *             if n is less than 0
	 * @param n
	 *            n'th root to be calculated
	 * @return list of complex numbers which represent roots
	 */
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("n has to be greater than 0 when calculating root");
		}

		List<Complex> roots = new ArrayList<>();

		if (n == 1) {
			roots.add(new Complex(re, im));
			return roots;
		}

		double magnitude = module();
		double angle = atan2(re, im);

		for (int i = 0; i < n; i++) {
			double real = pow(magnitude, (double) 1 / n) * cos((angle + 2 * PI * i) / n);
			double imaginary = pow(magnitude, (double) 1 / n) * sin((angle + 2 * PI * i) / n);
			roots.add(new Complex(real, imaginary));
		}

		return roots;
	}

	/**
	 * Getter for real number
	 * @return real number
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Getter for imaginary number
	 * @return imaginary number
	 */
	public double getIm() {
		return im;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + String.format("%.6f", re) + ", " + String.format("%.6f", im) + ")";
	}
}
