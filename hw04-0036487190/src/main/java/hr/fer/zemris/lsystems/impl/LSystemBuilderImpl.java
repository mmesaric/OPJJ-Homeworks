package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * This class implements the support for LSystem configuration. It implements
 * LSystemBuilder which offers methods for setting up the properties needed to
 * display the Fractal. System can be set up by calling specific interface
 * methods which behavior is overridden in this class methods or by passing an
 * array of strings to configureFromText method. If none of these approaches is
 * used, default system values are set. When system is set up and build method
 * is called, it creates an instance of nested class which offers methods for
 * generating the production and drawing the fractal
 * 
 * @author Marko Mesarić
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * Private nested class which implements LSystem interface. Responsible for
	 * generating the production characters on n'th level and creating initial
	 * context and turtle state.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	private class LSystemGenerator implements LSystem {

		/**
		 * This method is used for creating context and initial turtle state which is
		 * pushed on said context. It proceeds by iterating through calculated
		 * production characters and and executing corresponding commands based on
		 * production character which was read.
		 * 
		 * @param level
		 *            given degree of fractal
		 * @param painter
		 *            reference to object used for drawing lines
		 */
		@Override
		public void draw(int level, Painter painter) {

			Context context = new Context();

			// Vector2D startingDir = startingDirection.rotated(angle);

			// startingDirection.rotate(angle);

			TurtleState turtleState = new TurtleState(origin, new Vector2D(1, 0).rotated(angle), defaultColor,
					unitLength * Math.pow(unitLengthScaler, level));

			context.pushState(turtleState);

			char[] productionCharArray = generate(level).toCharArray();

			for (char symbol : productionCharArray) {

				Command command = (Command) commands.get(symbol);

				if (command != null) {
					command.execute(context, painter);
				}
			}

		}

		/**
		 * Method used for generating production on given level based on C++
		 * implementation described in "Interaktivna računalna grafika kroz primjere u
		 * OpenGL-u" by Marko Čupić and Željka Mihajlović, 2nd of March, 2016. Page 296,
		 * method "lSustav". In each iteration, the method passes through currently
		 * generated array and for each character replaces the symbol with it's
		 * production rule it such exists. If there is no production rules for that
		 * character, it is copied.
		 * 
		 * @return String production on given level
		 * @param level
		 *            current level of fractal
		 */
		@Override
		public String generate(int level) {

			StringBuilder productionBuilderTotal = new StringBuilder();
			productionBuilderTotal.append(axiom);

			for (int i = 0; i < level; i++) {

				StringBuilder productionBuilder = new StringBuilder();

				for (int j = 0; j < productionBuilderTotal.length(); j++) {

					char symbol = productionBuilderTotal.charAt(j);
					String productionRule = (String) productions.get(symbol);

					if (productionRule == null) {
						productionBuilder.append(symbol);
					} else {
						productionBuilder.append(productionRule);
					}

				}
				productionBuilderTotal = productionBuilder;
			}
			return productionBuilderTotal.toString();
		}
	}

	/**
	 * Dictionary used for storing commands
	 */
	private Dictionary commands;
	/**
	 * Dictionary used for storing productions
	 */
	private Dictionary productions;
	/**
	 * Vector used for origin point
	 */
	private Vector2D origin;
	/**
	 * attribute used for storing angle value
	 */
	private double angle;
	/**
	 * attribute used for storing unit length
	 */
	private double unitLength;
	/**
	 * attribute used for storing initial unit length scaler
	 */
	private double unitLengthScaler;
	/**
	 * attribute used for storing axiom String
	 */
	private String axiom;

	/**
	 * constant used for defining default unit length
	 */
	private static final double defaultUnitLength = 0.1;
	/**
	 * constant used for defining default unit length scaler
	 */
	private static final double defaultUnitLengthScaler = 1;
	/**
	 * constant used for defining default x coordinate of origin
	 */
	private static final double defaultOriginX = 0;
	/**
	 * constant used for defining default y coordinate of origin
	 */
	private static final double defaultOriginY = 0;
	/**
	 * constant used for defining default vector angle
	 */
	private static final double defaultAngle = 0;
	/**
	 * default color constant
	 */
	private static final Color defaultColor = Color.BLACK;

	/**
	 * Default constructor which initializes dictionaries used for storing
	 * registered commands and productions and for setting the initial starting
	 * direction vector to 1,0 which is later rotated depending on angle value
	 */
	public LSystemBuilderImpl() {

		commands = new Dictionary();
		productions = new Dictionary();
		// startingDirection = new Vector2D(1, 0);
	}

	/**
	 * Method which sets the values of attributes to default value in case of empty
	 * commands and productions dictionaries. Creates an instance of nested class
	 * which implements LSystem and returns it.
	 * 
	 * @return instantiated LSystem object
	 */
	@Override
	public LSystem build() {

		if (commands.isEmpty() && productions.isEmpty()) {

			unitLength = defaultUnitLength;
			unitLengthScaler = defaultUnitLengthScaler;
			origin = new Vector2D(defaultOriginX, defaultOriginY);
			angle = defaultAngle;
			axiom = "";
		}

		return new LSystemGenerator();
	}

	/**
	 * Method used for configuring LSystem from array of Strings according to given
	 * rules.
	 * 
	 * @throws IllegalArgumentException
	 *             in case of invalid parameter
	 * @param lines
	 *            array of strings which is to be parsed
	 * @return this instance of LSystemBuilder
	 * 
	 */
	@Override
	public LSystemBuilder configureFromText(String[] lines) {

		for (int i = 0; i < lines.length; i++) {

			String[] splitLines = lines[i].split("\\s+");

			if (splitLines[0].equals("")) {
				continue;
			}

			else if (splitLines.length == 2) {
				checkTwoParametersLine(splitLines);
			}

			else if (splitLines.length == 3) {
				checkThreeParametersLine(splitLines);
			}

			else if (splitLines.length == 4) {
				checkFourParametersLine(splitLines);
			} else {
				throw new IllegalArgumentException("Invalid LSystem setup from lines.");
			}

		}

		return this;
	}

	/**
	 * Auxiliary method used for parsing text input in case of four Strings after
	 * splitting the line by spaces. Checks for possible recognized values used for
	 * configuring system. If none such values are found, exception of invalid input
	 * is thrown. Recognized values are "unitLengthDegreeScaler" and "command".
	 * 
	 * @throws IllegalArgumentException
	 *             in case of invalid line
	 * @param splitLines
	 *            array of strings consisting of a single line split by spaces
	 */
	private void checkFourParametersLine(String[] splitLines) {

		switch (splitLines[0]) {

		case "unitLengthDegreeScaler":

			double firstOperand = Double.parseDouble(splitLines[1]);
			double secondOperand = Double.parseDouble(splitLines[3]);

			this.unitLengthScaler = firstOperand / secondOperand;
			break;

		case "command":

			char symbol = splitLines[1].charAt(0);

			switch (splitLines[2]) {
			case "draw":
				commands.put(symbol, new DrawCommand(Double.parseDouble(splitLines[3])));
				break;
			case "skip":
				commands.put(symbol, new SkipCommand(Double.parseDouble(splitLines[3])));
				break;
			case "scale":
				commands.put(symbol, new ScaleCommand(Double.parseDouble(splitLines[3])));
				break;
			case "rotate":
				commands.put(symbol, new RotateCommand(Double.parseDouble(splitLines[3])));
				break;
			case "color":

				String color = splitLines[3];
				if (color.startsWith("#")) {
					commands.put(symbol, new ColorCommand(Color.decode(splitLines[3])));
				} else {
					commands.put(symbol, new ColorCommand(Color.decode("#" + splitLines[3])));
				}
				break;
			default:
				throw new IllegalArgumentException("Invalid LSystem setup from lines.");
			}

			break;
		default:
			throw new IllegalArgumentException("Invalid LSystem setup from lines.");
		}
	}

	/**
	 * Auxiliary method used for parsing text input in case of four Strings after
	 * splitting the line by spaces. Checks for possible recognized values used for
	 * configuring system. If none such values are found, exception of invalid input
	 * is thrown. Recognized values are "origin", "production" and "command".
	 * 
	 * @throws IllegalArgumentException
	 *             in case of invalid line or invalid x or y coordinate value
	 * @param splitLines
	 *            array of strings consisting of a single line split by spaces
	 * 
	 */
	private void checkThreeParametersLine(String[] splitLines) {

		switch (splitLines[0]) {
		case "origin":

			double x = Double.parseDouble(splitLines[1]);
			double y = Double.parseDouble(splitLines[2]);

			if (x < 0 || x > 1 || y < 0 || y > 1) {
				throw new IllegalArgumentException("Origin X and Y coordinates must be in [0,1] range.");
			}

			origin = new Vector2D(x, y);
			break;
		case "production":
			productions.put(splitLines[1].charAt(0), splitLines[2]);
			break;
		case "command":
			char symbol = splitLines[1].charAt(0);

			switch (splitLines[2]) {
			case "push":
				commands.put(symbol, new PushCommand());
				break;
			case "pop":
				commands.put(symbol, new PopCommand());
				break;
			default:
				throw new IllegalArgumentException("Invalid LSystem setup from lines.");
			}
			break;
		default:
			throw new IllegalArgumentException("Invalid LSystem setup from lines.");
		}

	}

	/**
	 * Auxiliary method used for parsing text input in case of four Strings after
	 * splitting the line by spaces. Checks for possible recognized values used for
	 * configuring system. If none such values are found, exception of invalid input
	 * is thrown. Recognized values are "angle", "unitLength",
	 * "unitLengthDegreeScaler" and "axiom".
	 * 
	 * @throws IllegalArgumentException
	 *             in case of invalid line or invalid angle value
	 * @param splitLines
	 *            array of strings consisting of a single line split by spaces
	 * 
	 */
	public void checkTwoParametersLine(String[] splitLine) {

		switch (splitLine[0]) {
		case "angle":

			double angle = Double.parseDouble(splitLine[1]);
			if (angle < -360 || angle > 360) {
				throw new IllegalArgumentException("Angle must be in [-360, 360] range.");
			}
			this.angle = angle;
			break;
		case "unitLength":
			this.unitLength = Double.parseDouble(splitLine[1]);
			break;
		case "unitLengthDegreeScaler":
			this.unitLengthScaler = Double.parseDouble(splitLine[1]);
			break;
		case "axiom":
			this.axiom = splitLine[1];
			break;
		default:
			throw new IllegalArgumentException("Invalid LSystem setup from lines.");
		}
	}

	/**
	 * Method used for storing commands in dictionary. Symbol is used as a key
	 * value. String "command" is parsed and based on it's value, corresponding
	 * Command object is created and stored as value for given key.
	 * 
	 * @return this instance of LSystemBuilder
	 * @param symbol
	 *            used as key when storing command to dictionary
	 * @param command
	 *            command which is parsed and stored in dictionary
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String command) {

		String[] splitCommand = command.split("\\s+");

		if (splitCommand.length == 2) {

			switch (splitCommand[0]) {
			case "draw":
				commands.put(symbol, new DrawCommand(Double.parseDouble(splitCommand[1])));
				break;
			case "skip":
				commands.put(symbol, new SkipCommand(Double.parseDouble(splitCommand[1])));
				break;
			case "scale":
				commands.put(symbol, new ScaleCommand(Double.parseDouble(splitCommand[1])));
				break;
			case "rotate":
				commands.put(symbol, new RotateCommand(Double.parseDouble(splitCommand[1])));
				break;
			case "color":

				String color = splitCommand[1];
				if (color.startsWith("#")) {
					commands.put(symbol, new ColorCommand(Color.decode(splitCommand[1])));
				} else {
					commands.put(symbol, new ColorCommand(Color.decode("#" + splitCommand[1])));
				}

				break;
			default:
				throw new IllegalArgumentException("Invalid command");
			}

		} else if (splitCommand.length == 1) {

			switch (splitCommand[0]) {
			case "push":
				commands.put(symbol, new PushCommand());
				break;
			case "pop":
				commands.put(symbol, new PopCommand());
				break;
			default:
				throw new IllegalArgumentException("Invalid command");
			}

		} else {
			throw new IllegalArgumentException("Invalid command");
		}

		return this;
	}

	/**
	 * Method used for storing productions to dictionary. Symbol is the key and
	 * production is the value.
	 * 
	 * @param symbol
	 *            production rule for given symbol
	 * @param production
	 *            production to be stored
	 * @return this instance of LSystemBuilder
	 * 
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {

		productions.put(symbol, production);

		return this;
	}

	/**
	 * Method used for setting the initial value of angle to given value.
	 * 
	 * @throws IllegalArgumentException
	 *             in case of invalid angle value
	 * @param angle
	 *            given angle value to be set
	 * @return this instance of LSystemBuilder
	 * 
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {

		if (angle < -360 || angle > 360) {
			throw new IllegalArgumentException("Angle must be in [-360, 360] range.");
		}

		this.angle = angle;
		return this;
	}

	/**
	 * Method used for setting the value of axiom to given value.
	 * 
	 * @param axiom
	 *            given axiom value to be set
	 * @return this instance of LSystemBuilder
	 * 
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;

		return this;
	}

	/**
	 * Method used for setting the initial value of origin to given coordinate
	 * values.
	 * 
	 * @throws IllegalArgumentException
	 *             in case of invalid coordinate values
	 * @param x
	 *            x coordinate of origin
	 * @param y
	 *            y coordinate of origin
	 * @return this instance of LSystemBuilder
	 * 
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {

		if (x < 0 || x > 1 || y < 0 || y > 1) {
			throw new IllegalArgumentException("X and Y coordinates must be in [0,1] range.");
		}

		this.origin = new Vector2D(x, y);
		return this;

	}

	/**
	 * Method used for setting the initial value of unitLength to given value.
	 * 
	 * @param unitLength
	 *            given unit length value to be set
	 * @return this instance of LSystemBuilder
	 * 
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;

		return this;
	}

	/**
	 * Method used for setting the initial value of unitLengthDegreeScaler to given
	 * value.
	 * 
	 * @param unitLength
	 *            given unit length degree scaler value to be set
	 * @return this instance of LSystemBuilder
	 * 
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthScaler) {
		this.unitLengthScaler = unitLengthScaler;

		return this;
	}

}
