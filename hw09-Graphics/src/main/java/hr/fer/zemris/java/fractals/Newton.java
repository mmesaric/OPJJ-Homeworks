package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * This class represents the implementation of Newton-Raphson iteration
 * fractals. User is prompted to input complex numbers which represent the roots
 * of complex polynomial. After all desired roots are given, type "done" to
 * start the execution of fractal generation. Fractal generation is solved by
 * multi threading based on number of available processors.
 * 
 * @author Marko Mesarić
 *
 */
public class Newton {

	/**
	 * Constant defining the convergence threshold.
	 */
	private static final double CONVERGENCE_THRESHOLD = 10E-3;

	/**
	 * Main method which starts the execution of program and prompts complex numbers
	 * for input then proceeds to parse them and starts the execution of fractal
	 * generation.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		Scanner scanner = new Scanner(System.in);
		int counter = 0;

		List<Complex> list = new ArrayList<>();

		while (true) {
			System.out.print("Root " + ++counter + "> ");
			String line = scanner.nextLine();

			if (line.isEmpty()) {
				continue;
			}

			if (line.equals("done")) {
				if (list.size() >= 2) {
					break;
				}
				System.out.println("Input at least 2 complex numbers.");
				counter--;
				continue;
			}

			try {
				list.add(parseComplex(line));
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid complex number syntax.");
				counter--;
			}
		}
		scanner.close();

		Complex[] roots = new Complex[list.size()];

		for (int i = 0; i < list.size(); i++) {
			roots[i] = list.get(i);
		}

		FractalViewer.show(new FractalProducer(new ComplexRootedPolynomial(roots)));
	}

	/**
	 * Auxiliary method used for parsing complex number from user input line.
	 * 
	 * @param line
	 *            input line
	 * @return Complex number generated from line
	 */
	public static Complex parseComplex(String line) {

		if (line.isEmpty()) {
			throw new IllegalArgumentException("Complex number can't be empty.");
		}

		String real = "";
		String imaginary = "";

		line = line.trim();

		int numberOfPlus = line.length() - line.replace("+", "").length();
		int numberOfMinus = line.length() - line.replace("-", "").length();

		if (line.split("\\s+").length == 1 && numberOfPlus == 0
				&& ((line.contains("-") && line.startsWith("-") && numberOfMinus == 1) || !line.contains("-"))) {

			if (line.contains("i")) {
				if (line.equals("i")) {
					imaginary = "1";
				} else if (line.equals("-i")) {
					imaginary = "-1";
				} else if (line.startsWith("i")) {
					imaginary = line.substring(1, line.length());
				} else if (line.startsWith("-") && line.charAt(1) == 'i') {
					imaginary += "-";
					imaginary += line.substring(2, line.length());
				}
			} else {
				real = line;
			}

		} else if (line.startsWith("-") && line.contains("+")) {

			String[] splitLine = line.split("\\+");

			if (splitLine.length == 2) {

				real = splitLine[0].trim();
				imaginary = splitLine[1].trim();

				if (imaginary.equals("i")) {
					imaginary = "1";
				} else if (imaginary.equals("-i")) {
					imaginary = "-1";
				} else if (imaginary.startsWith("i")) {
					imaginary = imaginary.substring(1, imaginary.length());
				} else if (imaginary.startsWith("-") && imaginary.charAt(1) == 'i') {
					imaginary += "-";
					imaginary += imaginary.substring(2, imaginary.length());
				}
			}
		} else if (line.startsWith("-") && (line.length() - line.replace("-", "").length()) == 2) {

			line = line.substring(1, line.length());
			String[] splitLine = line.split("\\-");

			if (splitLine.length == 2) {

				real = "-" + splitLine[0].trim();
				line = "-" + splitLine[1].trim();

				if (line.equals("i")) {
					imaginary = "1";
				} else if (line.equals("-i")) {
					imaginary = "-1";
				} else if (line.startsWith("i")) {
					imaginary = line.substring(1, line.length());
				} else if (line.startsWith("-") && line.charAt(1) == 'i') {
					imaginary += "-";
					imaginary += line.substring(2, line.length());
				}
			}
		} else if (Character.isDigit(line.charAt(0)) && line.contains("+")) {

			String[] splitLine = line.split("\\+");

			if (splitLine.length == 2) {

				real = splitLine[0].trim();
				imaginary = splitLine[1].trim();

				if (imaginary.equals("i")) {
					imaginary = "1";
				} else if (imaginary.startsWith("i")) {
					imaginary = imaginary.substring(1, imaginary.length());
				}
			}
		} else if (Character.isDigit(line.charAt(0)) && line.contains("-")) {

			String[] splitLine = line.split("\\-");

			if (splitLine.length == 2) {

				real = splitLine[0].trim();
				line = "-" + splitLine[1].trim();

				if (line.equals("i")) {
					imaginary = "1";
				} else if (line.equals("-i")) {
					imaginary = "-1";
				} else if (line.startsWith("i")) {
					imaginary = line.substring(1, line.length());
				} else if (line.startsWith("-") && line.charAt(1) == 'i') {
					imaginary += "-";
					imaginary += line.substring(2, line.length());
				}
			}
		} else {
			throw new IllegalArgumentException("Invalid syntax for complex number.");
		}

		double realNumber;
		double imaginaryNumber;

		if (real.isEmpty() && imaginary.isEmpty()) {
			realNumber = 0;
			imaginaryNumber = 0;
		} else if (real.isEmpty() && !imaginary.isEmpty()) {
			realNumber = 0;
			imaginaryNumber = Double.parseDouble(imaginary);
		} else if (!real.isEmpty() && imaginary.isEmpty()) {
			realNumber = Double.parseDouble(real);
			imaginaryNumber = 0;
		} else {
			realNumber = Double.parseDouble(real);
			imaginaryNumber = Double.parseDouble(imaginary);
		}

		return new Complex(realNumber, imaginaryNumber);

	}

	/**
	 * Class which offers the concrete implementation of fractal generation.
	 * Iterates through all pixels (1 pixel is (x,y)) from given block and performs
	 * the necessary calculations for generating fractal based on roots and created
	 * complex polynomial.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	public static class FractalGenerator implements Callable<Void> {

		/**
		 * Minimum real value
		 */
		double reMin;
		/**
		 * Maximum real value
		 */
		double reMax;
		/**
		 * Minimum imaginary value
		 */
		double imMin;
		/**
		 * Maximum imaginary value
		 */
		double imMax;
		/**
		 * Width in pixels
		 */
		int width;
		/**
		 * Height in pixels
		 */
		int height;
		/**
		 * Minimum y
		 */
		int yMin;
		/**
		 * Maximum y
		 */
		int yMax;
		/**
		 * Minimum x
		 */
		int xMin;
		/**
		 * Maximum x
		 */
		int xMax;
		/**
		 * Maximum number of iterations
		 */
		int m;
		/**
		 * array responsible for coloring fractal
		 */
		short[] data;
		/**
		 * Rooted polynomial from given roots
		 */
		ComplexRootedPolynomial polynomial;
		/**
		 * Derivative of polynomial
		 */
		ComplexPolynomial derived;

		/**
		 * Default constructor
		 * 
		 * @param reMin
		 *            given minimum real number
		 * @param reMax
		 *            given maximum real number
		 * @param imMin
		 *            given minimum imaginary number
		 * @param imMax
		 *            given maximum imaginary number
		 * @param width
		 *            given width
		 * @param height
		 *            given height
		 * @param yMin
		 *            given minimum y coordinate
		 * @param yMax
		 *            given maximum y coordinate
		 * @param xMin
		 *            given minimum x coordinate
		 * @param xMax
		 *            given maximum x coordinate
		 * @param m
		 *            maximum number of iterations
		 * @param data
		 *            array for coloring
		 * @param polynomial
		 *            polynomial to be represented
		 */
		public FractalGenerator(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int xMin, int xMax, int m, short[] data, ComplexRootedPolynomial polynomial) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.xMin = xMin;
			this.xMax = xMax;
			this.m = m;
			this.data = data;
			this.polynomial = polynomial;
			this.derived = polynomial.toComplexPolynom().derive();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Void call() {

			int offset = yMin * width;
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					Complex c = mapToComplexPlane(x, y);
					Complex zn = c;

					int iter = 0;
					double module;
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex fraction = null;
						fraction = numerator.divide(denominator);
						Complex zn1 = zn.sub(fraction);
						module = zn1.sub(zn).module();
						zn = zn1;
						iter++;
					} while (module > CONVERGENCE_THRESHOLD && iter < m);

					int index = polynomial.indexOfClosestRootFor(zn, 10E-1);
					if (index == -1) {
						data[offset++] = 0;
					} else {
						data[offset++] = (short) index;
					}
				}

			}
			return null;
		}

		/**
		 * Auxiliary method used for mapping current coordinates to complex plane
		 * 
		 * @param x
		 *            current x coordinate
		 * @param y
		 *            current y coordinate
		 * @return Complex number from given coordinates
		 */
		private Complex mapToComplexPlane(int x, int y) {
			double real = x / (width - 1.0) * (reMax - reMin) + reMin;
			double imaginary = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;

			return new Complex(real, imaginary);
		}

	}

	/**
	 * Class which offers the implementation of setting up the configuration of
	 * multithreaded fractal generation. Defines the number of process on which
	 * multi-thread generation is based.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	public static class FractalProducer implements IFractalProducer {

		/**
		 * Pool of all threads used for fractal generation
		 */
		private ExecutorService pool;
		/**
		 * Rooted complex polynomial which is to be generated
		 */
		private ComplexRootedPolynomial polynom;
		/**
		 * Number of processes based on available JVM processors
		 */
		private int numberOfProces;

		/**
		 * Default constructor
		 * 
		 * @param polynom
		 */
		public FractalProducer(ComplexRootedPolynomial polynom) {
			super();
			this.numberOfProces = Runtime.getRuntime().availableProcessors();
			this.pool = Executors.newFixedThreadPool(numberOfProces, new ThreadFactory() {
				
				@Override
				public Thread newThread(Runnable r) {
					Thread worker = new Thread(r);
					worker.setDaemon(true);
					return worker;
				}
			});
			this.polynom = polynom;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {

			System.out.println("Starting the calculation..");

			int m = 16 * 16 * 16;
			short[] data = new short[width * height];
			final int brojTraka = 8 * numberOfProces;
			int numberOfYOnBar = height / brojTraka;
			int numberOfXOnBar = width / brojTraka;

			List<Future<Void>> results = new ArrayList<>();

			for (int i = 0; i < brojTraka; i++) {
				int yMin = i * numberOfYOnBar;
				int yMax = (i + 1) * numberOfYOnBar - 1;
				int xMin = i * numberOfXOnBar;
				int xMax = (i + 1) * numberOfXOnBar - 1;
				if (i == brojTraka - 1) {
					yMax = height - 1;
					xMax = width - 1;
				}
				FractalGenerator job = new FractalGenerator(reMin, reMax, imMin, imMax, width, height, yMin, yMax, xMin,
						xMax, m, data, polynom);
				results.add(pool.submit(job));
			}
			for (Future<Void> posao : results) {
				try {
					posao.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			System.out.println("Calculation completed.");
			observer.acceptResult(data, (short) (polynom.toComplexPolynom().order() + 1), requestNo);
		}

	}

}
