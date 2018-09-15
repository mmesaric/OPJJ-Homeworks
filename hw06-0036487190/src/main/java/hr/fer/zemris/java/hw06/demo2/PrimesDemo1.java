package hr.fer.zemris.java.hw06.demo2;

/**
 * Class used for first example demonstration of outputting the first N prime
 * numbers using the PrimesCollection implementation done in this homework
 * according to homework guide directions.
 * 
 * @author Marko Mesarić
 *
 */
public class PrimesDemo1 {

	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}

	}
}
