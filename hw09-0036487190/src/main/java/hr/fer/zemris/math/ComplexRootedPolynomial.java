package hr.fer.zemris.math;

import java.util.Objects;

/**
 * This class offers the implementation of a rooted Complex polynomial based on
 * given roots passed when creating the rooted complex polynomial. It offers the
 * methods for transforming the rooted polynomial to standard polynomial
 * representation and calculating the exact value for given complex number.
 * 
 * @author Marko Mesarić
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * Array for storing all roots of polynomial
	 */
	private final Complex[] roots;

	/**
	 * Constructor which sets the polynomial roots to given value
	 * 
	 * @throws IllegalArgumentException
	 *             if no roots are passed
	 * @throws NullPointerException
	 *             if roots are null
	 * @param roots
	 *            roots to be set
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		Objects.requireNonNull(roots, "Reference to roots can't be null.");

		if (roots.length == 0) {
			throw new IllegalArgumentException("You have to pass at least one root complex number.");
		}

		this.roots = new Complex[roots.length];

		for (int i = 0; i < roots.length; i++) {
			this.roots[i] = roots[i];
		}

	}

	/**
	 * Method used for calculating the exact value of polynomial for given complex
	 * number
	 * 
	 * @param other
	 *            concrete complex number
	 * @return calculated complex number
	 */
	public Complex apply(Complex other) {
		Objects.requireNonNull(other, "Can't apply Rooted Polynomial on null.");

		Complex function = other.sub(roots[0]);

		for (int i = 1; i < roots.length; i++) {
			Complex current = roots[i];

			function = function.multiply(other.sub(current));

		}
		return function;
	}

	/**
	 * Method which performs the transformation of rooted complex polynomial to
	 * standard complex polynomial based on factors.
	 * 
	 * @return complex polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {

		ComplexPolynomial polynomial = new ComplexPolynomial(Complex.ONE);

		for (int i = roots.length - 1; i >= 0; i--) {

			Complex root = roots[i];
			ComplexPolynomial factor = new ComplexPolynomial(root.negate(), Complex.ONE);
			polynomial = polynomial.multiply(factor);
		}

		return polynomial;
	}

	/**
	 * Method which finds and returns the index of closest root for passed complex
	 * number z based on given threshold
	 * 
	 * @param z
	 *            complex number
	 * @param treshold
	 *            given threshold
	 * @return index
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {

		double minModul = z.sub(roots[0]).module();
		int index = 0;
		for (int i = 0; i < roots.length; i++) {
			if (z.sub(roots[i]).module() < minModul) {
				minModul = z.sub(roots[i]).module();
				index = i;
			}
		}
		if (minModul < treshold) {
			return index + 1;
		} else {
			return -1;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "";
	}
}
