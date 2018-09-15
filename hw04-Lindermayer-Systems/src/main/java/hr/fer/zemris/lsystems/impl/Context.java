package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Objects of this class provide the execution of the Fractal representation.
 * Each context contains object of the type ObjectStack which is used for
 * storing Turtle states.
 * 
 * @author Marko Mesarić
 *
 */
public class Context {

	/**
	 * Object of the type ObjectStack used for storing Turtle States which acts as
	 * stack.
	 */
	private ObjectStack objectStack;

	/**
	 * Default constructor which allocates the memory for object stack collection.
	 */
	public Context() {
		this.objectStack = new ObjectStack();
	}

	/**
	 * Method used for getting the current Turtle State without removing it from the
	 * stack.
	 * 
	 * @throws EmptyStackException in case of empty stack
	 * @return current Turtle state
	 */
	public TurtleState getCurrentState() {
		if (objectStack.isEmpty()) {
			throw new EmptyStackException("Stack is empty");
		}
		return (TurtleState) objectStack.peek();
	}

	/**
	 * Method used for pushing given Turtle state to the stack.
	 * @param state given state to be pushed to stack
	 * @throws IllegalArgumentException in case of given null reference
	 */
	public void pushState(TurtleState state) {
		if (state == null) {
			throw new IllegalArgumentException("Turtle State can't be null.");
		}
		objectStack.push(state);
	}

	/**
	 * Method used for removing last Turtle state from stack.
	 * @throws EmptyStackException in case of empty stack
	 */
	public void popState() {
		if (objectStack.isEmpty()) {
			throw new EmptyStackException("Stack is empty");
		}

		objectStack.pop();
	}

}
