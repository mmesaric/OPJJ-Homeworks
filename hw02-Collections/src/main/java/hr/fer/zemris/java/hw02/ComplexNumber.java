package hr.fer.zemris.java.hw02;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.atan2;
import static java.lang.Math.PI;;

/**
 * This class represents the implementation of solution for working with complex
 * numbers. Once created, the complex number is unmodifiable. Each method which
 * performs some kind of modification returns a new instance of ComplexNumber
 * class which represents the modified complex number. This class manages 4
 * private variables. Real is used to store real part of the complex number and
 * imaginary is used to stored the imaginary part of given complex number.
 * Magnitude represents the module of current complex number and angle is
 * expressed in radians, from 0 to 2PI.
 * 
 * @author Marko Mesarić
 *
 */
public class ComplexNumber {

	private double real;
	private double imaginary;
	private double magnitude;
	private double angle;

	/**
	 * Constructor which accepts two arguments, real and imaginary part and sets
	 * them accordingly. Magnitude is calculated from a known module formula. Angle
	 * is calculated based on return value of atan2 method which returns values from
	 * [-PI, PI].
	 * 
	 * @param real
	 *            real part of the complex number
	 * @param imaginary
	 *            imaginary part of the complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
		this.magnitude = sqrt(pow(real, 2) + pow(imaginary, 2));

		double atan = atan2(imaginary, real);
		if (atan >= 0 && atan <= 2 * PI) {
			this.angle = atan;
		} else {
			this.angle = atan + 2 * PI;
		}
	}

	// FACTORY METHODS

	/**
	 * Creates a new ComplexNumber object with imaginary part being zero.
	 * 
	 * @param real
	 *            real part of complex number
	 * @return new instance of ComplexNumber
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);

	}

	/**
	 * Creates a new ComplexNumber object with real part being zero.
	 * 
	 * @param imaginary
	 *            imaginary part of complex number
	 * @return new instance of ComplexNumber
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Creates a new ComplexNumber object for given magnitude and angle.
	 * 
	 * @param magnitude
	 *            given magnitude value
	 * @param angle
	 *            given angle value
	 * @return new instance of ComplexNumber
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double real = magnitude * cos(angle);
		double imaginary = magnitude * sin(angle);

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Method which parses given String and returns a new ComplexNumber object.
	 * Accepts strings such as: "3.51", "-3.17", "-2.71i", "i", "1", "-2.71-3.15i"),
	 * 
	 * @param s
	 *            complex number which is to be parsed
	 * @return new Instance of ComplexNumber based on parsing
	 */
	public static ComplexNumber parse(String s) {

		if (s.equals("i")) {
			return fromImaginary(1);
		}
		if (!s.contains("i")) {
			double real = getDoubleValue(s);
			return fromReal(real);
		}

		char[] asCharArray = s.toCharArray();

		int countMinus = s.length() - s.replace("-", "").length();
		int countPlus = s.length() - s.replace("+", "").length();

		if ((countMinus == 1 && s.endsWith("i") && countPlus == 0 && s.startsWith("-"))
				|| (countMinus == 0 && s.endsWith("i") && countPlus == 0)) {
			double imaginary = getDoubleValue(s.substring(0, s.length() - 1));
			return fromImaginary(imaginary);
		}

		return parseRealAndComplex(asCharArray, s);

	}

	/**
	 * Help method which parses complex strings in which are present both real and
	 * imaginary parts of a complex number. It iterates char by char through given
	 * string and acts accordingly. After both real and imaginary parts are parsed,
	 * method creates a new ComplexNumber object and returns it.
	 * 
	 * @param asCharArray
	 *            char array of given string
	 * @param s
	 *            given string which is to be parsed
	 * @return new instance of complex number
	 */
	private static ComplexNumber parseRealAndComplex(char[] asCharArray, String s) {

		double real = 0;
		double imaginary = 0;
		boolean startsWithMinus = false;
		boolean imaginaryIsNegative = false;
		boolean realCheck = true;

		for (int i = 0; i < asCharArray.length; i++) {

			char current = asCharArray[i];

			if (!Character.isDigit(current)) {

				if (asCharArray[0] == '-' && !startsWithMinus) {
					startsWithMinus = true;
				} else if (current == '-') {
					imaginaryIsNegative = true;
				}
				continue;
			}

			int j;
			for (j = i;; j++) {
				char c = asCharArray[j];
				// if (!Character.isDigit(c) || c!='.') {
				if (!((c >= '0' && c <= '9') || c == '.')) {
					break;
				}
			}

			if (realCheck) {
				i = j - 1;
				real = getDoubleValue(s.substring(0, i + 1));
				realCheck = false;
			} else {
				if (imaginaryIsNegative == true) {

					imaginary = getDoubleValue(s.substring(i - 1, j));
					i = j - 1;
				} else {
					imaginary = getDoubleValue(s.substring(i, j));
					i = j - 1;

				}
				break;
			}
			continue;
		}

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Method which generates value as type double from given substring passed from
	 * parse method. In case of wrong complex number definition, notifies the user
	 * and stops the execution of the program.
	 * 
	 * @param substring
	 *            to be cast into double.
	 * @return double value of given substring
	 */
	private static double getDoubleValue(String substring) {

		double value = 0;
		try {
			value = Double.parseDouble(substring);
		} catch (NumberFormatException e) {
			System.out.println("Wrong complex number definition.");
			System.exit(1);
		}

		return value;

	}

	// CALCULATION METHODS

	/**
	 * Adds the given ComplexNumber c to this complex number and returns the result
	 * of the calculation as new ComplexNumber object
	 * 
	 * @param c
	 *            other complexNumber to be added to this one
	 * @return new ComplexNumber object
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}

	/**
	 * Subtracts this complex number with the given ComplexNumber reference c and
	 * returns the result of the calculation as new ComplexNumber object
	 * 
	 * @param c
	 *            other complexNumber to be subtracted with this one
	 * @return new ComplexNumber object
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Multiplies the given ComplexNumber c with this complex number and returns the
	 * result of the calculation as new ComplexNumber object
	 * 
	 * @param c
	 *            other complexNumber to be multiplied with this one
	 * @return new ComplexNumber object
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double real = (this.real * c.real) - (this.imaginary * c.imaginary);
		double imaginary = (this.real * c.imaginary) + (this.imaginary * c.real);

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Divides this complex number with the given ComplexNumber reference c and
	 * returns the result of the calculation as new ComplexNumber object
	 * 
	 * @param c
	 *            other complexNumber with which this complex number is divided
	 * @return new ComplexNumber object
	 */
	public ComplexNumber div(ComplexNumber c) {
		ComplexNumber conjugate = new ComplexNumber(c.real, -c.imaginary);

		ComplexNumber numerator = this.mul(conjugate);
		ComplexNumber denominator = c.mul(conjugate);

		ComplexNumber dividedComplexNumber = new ComplexNumber(numerator.getReal() / denominator.getReal(),
				numerator.getImaginary() / denominator.getReal());
		return dividedComplexNumber;
	}

	/**
	 * This method calculates the n'th power of this complex number and returns the
	 * generated complex number. n has to be greater or equal to zero, otherwise
	 * IllegalArgumentException is thrown.
	 * 
	 * @param n
	 *            power
	 * @return generated ComplexNumber object
	 */
	public ComplexNumber power(int n) {

		if (n < 0) {
			throw new IllegalArgumentException("Power can't be less than 0");
		}

		if (n == 0) {
			return new ComplexNumber(1, 0);
		}

		double powerMagnitude = pow(this.magnitude, n);
		double otherReal = powerMagnitude * cos(n * angle);
		double otherImaginary = powerMagnitude * sin(n * angle);

		return new ComplexNumber(otherReal, otherImaginary);
	}

	/**
	 * This method calculates the first n roots of this complex number based on well
	 * known formula and returns the generated array of ComplexNumber object
	 * references. Size of the array is equal to n. n has to be greater than zero, otherwise
	 * IllegalArgumentException is thrown.
	 * 
	 * @param n
	 *            first n roots
	 * @return generated array of ComplexNumber object references
	 */
	public ComplexNumber[] root(int n) {
		
		if (n <= 0) {
			throw new IllegalArgumentException("n has to be greater than 0 when calculating root");
		}

		ComplexNumber[] roots = new ComplexNumber[n];

		if (n == 1) {
			roots[0] = new ComplexNumber(real, imaginary);
			return roots;
		}

		for (int i = 0; i < n; i++) {
			double real = pow(magnitude, (double) 1 / n) * cos((angle + 2 * PI * i) / n);
			double imaginary = pow(magnitude, (double) 1 / n) * sin((angle + 2 * PI * i) / n);
			roots[i] = new ComplexNumber(real, imaginary);
		}

		return roots;
	}

	// GETTER METHODS

	/**
	 * Gets the real value from this complex number
	 * 
	 * @return real part of this complex number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Gets the imaginary value from this complex number
	 * 
	 * @return imaginary part of this complex number
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Gets the magnitude from this complex number
	 * 
	 * @return magnitude of this complex number
	 */
	public double getMagnitude() {
		return magnitude;
	}

	/**
	 * Gets the angle from this complex number
	 * 
	 * @return angle of this complex number
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * This method overrides the default toString method and it is used for defining
	 * format when printing this object.
	 */
	public String toString() {
		if (real == 0) {
			return String.format("%.2f", imaginary) + "i";
		}
		if (imaginary == 0) {
			return String.format("%.2f", real) + "";
		}
		if (imaginary < 0) {
			return String.format("%.2f", real) + " - " + String.format("%.2f", -imaginary) + "i";
		}

		return String.format("%.2f", real) + " + " + String.format("%.2f", imaginary) + "i";
	}
}
