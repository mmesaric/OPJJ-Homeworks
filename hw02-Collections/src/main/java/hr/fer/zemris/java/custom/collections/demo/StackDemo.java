package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This class represents the implementation of command-line application which
 * accepts a single command-line argument, the expression which is to be
 * evaluated. Expression must be in postfix notation separated by one or more
 * spaces. The calculation process is solved using the ObjectStack.
 * 
 * @author Marko Mesarić
 *
 */
public class StackDemo {

	/**
	 * This method handles the user input and calls the other methods in case of
	 * only one command-line argument being passed. If any other number of arguments
	 * is passed, program notifies the user of error and stops the execution.
	 * 
	 * @param args
	 *            command line arguments passed when starting the program
	 */
	public static void main(String[] args) {

		if (args.length == 1) {

			String[] splitInput = args[0].split("\\s+");
			ObjectStack objectStack = new ObjectStack();

			for (String element : splitInput) {
				Integer object = checkIfNumber(element);

				if (object != null) {
					objectStack.push(object);
				}

				else {
					calculateOperation(objectStack, element);
				}
			}

			printResult(objectStack);
		}

		else {
			System.err.println("Program should be called with only 1 argument");
		}

	}

	/**
	 * Method responsible for checking if given String is an Integer.
	 * 
	 * @param element
	 *            element which is to be tested
	 * @return returns the integer value if element is a number, null otherwise.
	 */
	private static Integer checkIfNumber(String element) {

		try {
			Integer number = Integer.parseInt(element);
			return number;
		} catch (NumberFormatException e) {
			return null;
		}

	}

	/**
	 * Method which pops the elements from the stack and performs some mathematical
	 * operation based on given String operation. Result is then pushed on stack. In
	 * case of trying to divide by zero, user is notified of error and program stops
	 * the execution. If the number of values on stack is invalid, user is notified
	 * of invalid expression and program stops the execution.
	 * 
	 * @param objectStack
	 *            reference to the current instance of the ObjectStack
	 * @param operation
	 *            mathematical operation to be performed
	 */
	private static void calculateOperation(ObjectStack objectStack, String operation) {

		Integer operand1 = null;
		Integer operand2 = null;

		try {
			operand1 = (Integer) objectStack.pop();
			operand2 = (Integer) objectStack.pop();
		} catch (EmptyStackException e) {
			System.err.print("Your expression was not valid");
			System.exit(1);
		}

		switch (operation) {
		case "+":
			objectStack.push(operand2 + operand1);
			break;
		case "-":
			objectStack.push(operand2 - operand1);
			break;
		case "/":
			if (operand1 == 0) {
				System.err.println("Dividing by zero is not allowed!");
				System.exit(1);
			}
			objectStack.push(operand2 / operand1);
			break;
		case "*":
			objectStack.push(operand2 * operand1);
			break;
		case "%":
			objectStack.push(operand2 % operand1);
			break;
		}

	}

	/**
	 * Method responsible for printing the result of expression. In case of invalid
	 * number of values placed on stack, notifies the user of error and stops the
	 * execution.
	 * 
	 * @param objectStack
	 *            reference to the current instance of the ObjectStack
	 */
	private static void printResult(ObjectStack objectStack) {

		if (objectStack.size() != 1) {
			System.err.println("Your expression was not valid.");
			System.exit(1);
		} else {
			System.out.println("Expression evaluates to " + objectStack.pop());
		}

	}

}
