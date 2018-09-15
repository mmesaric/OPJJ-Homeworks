package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * This class consists exclusively of static methods used for calculating
 * factorial of whole number passed as user input.
 * 
 * @author Marko Mesaric
 *
 */
public class Factorial {

	/**
	 * Main method responsible for consuming user input, checking it's validity
	 * (range is [1,20]) and calling dedicated method for Factorial calculation. In
	 * case of a wrong type or value of passed input, notifies the user and
	 * continues prompting for input and calculating factorial until "kraj" is
	 * passed as input.
	 * 
	 * @param args
	 *            String array of input arguments passed from console
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("Unesite broj:");
			String line = scanner.next();
			long inputNumber = 0;
			long factorial;

			if (line.equals("kraj")) {
				System.out.println("Doviđenja");
				break;
			}

			else {
				try {
					inputNumber = Long.parseLong(line);
					if (inputNumber < 1 || inputNumber > 20) {
						System.out.println("\'" + line + "\' " + "nije u dozvoljenom rasponu");
						continue;
					}
				} catch (NumberFormatException ex) {
					System.out.println("\'" + line + "\' " + " nije cijeli broj");
					continue;
				}

				factorial = calculateFactorial(inputNumber);
				System.out.println(inputNumber + "!=" + factorial);
			}
		}

		scanner.close();
	}

	/**
	 * Method which calculates the factorial of given number using recursion.
	 * 
	 * @param inputNumber
	 *            of which factorial is to be calculated
	 * @return factorial of the given number, or -1 if inputNumber is <0
	 */
	public static long calculateFactorial(long inputNumber) {
		if (inputNumber < 0) {
			return -1;
		}

		if (inputNumber == 0 || inputNumber == 1) {
			return 1;
		}
		return inputNumber * calculateFactorial(inputNumber - 1);
	}

}
