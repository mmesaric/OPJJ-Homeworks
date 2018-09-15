package hr.fer.zemris.math;

import java.util.Objects;

/**
 * This class offers the implementation of a Complex polynomial based on given
 * factors passed when creating the complex polynomial. It offers the operations
 * usually performed on complex polynomials, such as derivation, multiplication
 * and calculating the exact value for given complex number.
 * 
 * @author Marko Mesarić
 *
 */
public class ComplexPolynomial {

	/**
	 * Array used for storing factors
	 */
	private final Complex[] factors;

	/**
	 * Constructor which sets the polynomial factors to given value
	 * 
	 * @throws IllegalArgumentException
	 *             if no factors are passed
	 * @throws NullPointerException
	 *             if factors are null
	 * @param factors
	 *            factors to be set
	 */
	public ComplexPolynomial(Complex... factors) {
		Objects.requireNonNull(factors, "Reference to factors can't be null.");

		if (factors.length == 0) {
			throw new IllegalArgumentException("You have to pass at least one factor.");
		}

		this.factors = new Complex[factors.length];

		for (int i = 0; i < factors.length; i++) {
			this.factors[i] = factors[i];
		}
	}

	/**
	 * Method for calculating the order of polynomial
	 * 
	 * @return polynomial order
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Method performs the multiplication of this complex polynomial with given
	 * other polynomial and returns the newly generated polynomial.
	 * 
	 * @param p
	 *            other polynomial to be multiplied with this one.
	 * @return new polynomial which is the result of multiplication
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {

		Complex[] factors = new Complex[p.order() + this.order() + 1];
		Complex factorI;
		Complex factorJ;
		Complex currentValue;
		Complex multiplyResult;

		for (int i = 0; i < this.factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {

				factorI = this.factors[i];
				factorJ = p.factors[j];
				multiplyResult = factorI.multiply(factorJ);

				if (factors[i + j] == null) {
					currentValue = multiplyResult;
				} else {
					currentValue = factors[i + j];
					currentValue = currentValue.add(multiplyResult);
				}

				if (factors[i + j] == null) {
					factors[i + j] = this.factors[i].multiply(p.factors[j]);
				} else {
					factors[i + j] = factors[i + j].add(this.factors[i].multiply(p.factors[j]));
				}
			}
		}

		return new ComplexPolynomial(factors);
	}

	/**
	 * Method performs the derivation of this complex polynomial and returns the
	 * derivative
	 * 
	 * @return derived polynomial
	 */
	public ComplexPolynomial derive() {

		if (factors.length == 1) {
			return new ComplexPolynomial(new Complex[] { new Complex(0, 0) });
		}

		Complex[] deriveFactors = new Complex[factors.length - 1];

		for (int i = 1; i < factors.length; i++) {
			Complex current = factors[i];

			deriveFactors[i - 1] = new Complex(i, 0).multiply(current);
		}

		return new ComplexPolynomial(deriveFactors);

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

		Complex function = factors[0];

		for (int i = 1; i < factors.length; i++) {
			Complex current = factors[i];

			function = function.add(current.multiply(other.power(i)));

		}
		return function;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = factors.length-1; i > 0; i++) {
			if (i == 0) {
				builder.append(factors[i]);
			} else {
				builder.append("(" + factors[i] + ")").append("z" + (i - 1)).append("+");
			}
		}
		return builder.toString();
	}

}
