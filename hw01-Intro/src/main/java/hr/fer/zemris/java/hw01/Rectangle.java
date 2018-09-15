package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * This class consists exclusively of static methods which define operations
 * used for calculating area and perimeter of Rectangle according to user input
 * defining length and width.
 * 
 * @author Marko Mesaric
 *
 */
public class Rectangle {

	/**
	 * This class handles user input depending on different ways which the program
	 * can be run. In case of wrong number of arguments with which the program is
	 * called, it notifies the user of error and stops the execution. Width and
	 * length can be passed from console when running the program or passed as user
	 * input after program starts.
	 * 
	 * @param args
	 *            String array of input arguments passed from console, 0 or 2
	 */
	public static void main(String[] args) {
		double a = 0.0, b = 0.0;

		if (args.length == 2) {
			a = provjeriKomandne(args[0]);
			b = provjeriKomandne(args[1]);
		}

		else if (args.length == 0) {
			Scanner scanner = new Scanner(System.in);
			a = ucitajUnos(scanner, "širinu");
			b = ucitajUnos(scanner, "visinu");

			scanner.close();
		}

		else {
			System.err.println("Krivi broj argumenata!");
			System.exit(1);
		}

		izracunajIspisi(a, b);
	}

	/**
	 * Method used for calling the area and perimeter calculation methods and
	 * printing the values respectively.
	 * 
	 * @param a
	 *            width
	 * @param b
	 *            length
	 */
	private static void izracunajIspisi(double a, double b) {

		double povrsina = izracunajPovrsinu(a, b);
		double opseg = izracunajOpseg(a, b);

		System.out.println(
				"Pravokutnik sirine " + a + " i visine " + b + " ima površinu " + povrsina + " te opseg " + opseg);
	}

	/**
	 * Method responsible for checking the validity of values passed as arguments
	 * from console. In case of wrong values stops the execution of the program with
	 * a corresponding message
	 * 
	 * @param input
	 * @return value of length or width as double
	 */
	private static double provjeriKomandne(String input) {
		double pom = 0.0;

		try {
			pom = Double.parseDouble(input);
		} catch (NumberFormatException ex) {
			System.err.println("\'" + input + "\' " + "se ne može protumačiti kao broj");
			System.exit(1);
		}

		if (pom < 0) {
			System.err.println("Unijeli ste negativnu vrijednost");
			System.exit(1);
		}
		if (pom == 0) {
			System.err.println("Duljina stranice ne moze biti 0!");
			System.exit(1);
		}

		return pom;
	}

	/**
	 * Method responsible for consuming user input and checking it's validity. In
	 * case of wrong values, notifies the user of wrong type of input and prompts
	 * for different value.
	 * 
	 * @param scanner
	 *            reference to Scanner object handling user input
	 * @param opis
	 *            width or length
	 * @return value of width or length as double
	 */
	private static double ucitajUnos(Scanner scanner, String opis) {
		double pom = 0.0;
		while (true) {
			System.out.println("Unesite " + opis);
			String redak = scanner.next();

			try {
				pom = Double.parseDouble(redak);

				if (pom < 0) {
					System.out.println("Unijeli ste negativnu vrijednost");
					continue;
				}
				if (pom == 0) {
					System.out.println("Duljina stranice ne moze biti 0!");
					continue;
				}
				break;
			} catch (NumberFormatException ex) {
				System.out.println("\'" + redak + "\' " + "se ne može protumačiti kao broj");
				continue;
			}
		}

		return pom;
	}

	/**
	 * Method which calculates rectangle perimeter based on passed width and length.
	 * 
	 * @param a
	 *            width
	 * @param b
	 *            length
	 * @return perimiter value
	 */
	public static double izracunajOpseg(double a, double b) {
		return 2 * (a + b);
	}

	/**
	 * Method which calculates rectangle area based on passed width and length.
	 * 
	 * @param a
	 *            width
	 * @param b
	 *            length
	 * @return area value
	 */
	public static double izracunajPovrsinu(double a, double b) {
		return a * b;
	}
}
