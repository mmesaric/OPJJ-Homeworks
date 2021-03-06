package hr.fer.zemris.java.hw06.demo2;

/**
 * Class used for second example demonstration of outputting the first N prime
 * numbers using the PrimesCollection implementation done in this homework
 * according to homework guide directions.
 * 
 * @author Marko Mesarić
 *
 */
public class PrimesDemo2 {

	public static void main(String[] args) {

		PrimesCollection primesCollection = new PrimesCollection(2);

		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}

}
