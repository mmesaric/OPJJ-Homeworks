package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class acts as "collection" of prime numbers. Prime numbers are generated
 * one by one, by calling the "next()" method. Since the class implements
 * iterable, collection can be used to iterate through in for each loop.
 * Iterator solution is implemented as private nested class which implements
 * iterator interface.
 * 
 * @author Marko Mesarić
 *
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * first n number of primes to be generated.
	 */
	private int numberOfPrimes;

	/**
	 * Constructor which gets the value of how many first primes are to be generated
	 * 
	 * @param numberOfPrimes
	 *            value of how many first primes are to be generated
	 */
	public PrimesCollection(int numberOfPrimes) {
		this.numberOfPrimes = numberOfPrimes;
	}

	/**
	 * Method which returns a new instance of Prime iterator class.
	 * 
	 * @return new Iterator parameterized by Integer
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new PrimeIterator();
	}

	/**
	 * Private nested class which offers the implementation of Iterator class who's
	 * method's are called for retrieving next value in collection.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	private class PrimeIterator implements Iterator<Integer> {

		/**
		 * Constant representing the first prime number
		 */
		private static final int STARTING_PRIME = 2;

		/**
		 * Attribute used for storing current prime number
		 */
		private int primeValue;
		/**
		 * Attribute used for checking if given number of primes was generated
		 */
		private int counter;

		/**
		 * Constructor which sets the initial prime number to first and counter to zero.
		 */
		public PrimeIterator() {
			primeValue = STARTING_PRIME;
			counter = 0;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {

			return primeValue < Integer.MAX_VALUE && counter < numberOfPrimes;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Integer next() {

			if (!hasNext()) {
				throw new NoSuchElementException("No more prime numbers (You asked for first " + numberOfPrimes + ")");
			}

			if (primeValue == STARTING_PRIME) {
				counter++;
				return primeValue++;
			}

			for (int i = primeValue;; i++) {
				boolean primeCheck = true;

				for (int j = 2; j <= Math.sqrt(i); j++) {

					if (i % j == 0) {
						primeCheck = false;
						break;
					}
				}
				if (primeCheck) {
					primeValue = i;
					counter++;
					return primeValue++;
				}
			}

		}
	}
}
