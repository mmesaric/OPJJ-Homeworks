package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Class which is responsible for calculating turtle path after repositioning
 * based on step value. Remembers the new position as current Turtle position in
 * this context. Acts the same as Draw Command without stroking the line between
 * old and new position
 * 
 * @author Marko Mesarić
 *
 */
public class SkipCommand implements Command {

	private double step;

	public SkipCommand(double step) {
		this.step = step;
	}

	/**
	 * Method which calculates the position to which turtle is to be moved, sets the
	 * new turtle position to calculated value and pushes newly generated turtle
	 * state to context
	 * 
	 * @param ctx
	 *            relevant context in which Fractal is shown
	 * @param painter
	 *            object used for drawing lines
	 */
	@Override
	public void execute(Context ctx, Painter painter) {

		TurtleState turtleState = ctx.getCurrentState();

		Vector2D current = turtleState.getState();
		Vector2D directionVector = turtleState.getDirection();

		double effectiveStep = turtleState.getDelta() * step;

		Vector2D newCurrentState = new Vector2D(directionVector.getX() * effectiveStep + current.getX(),
				directionVector.getY() * effectiveStep + current.getY());

		TurtleState newTurtleState = new TurtleState(newCurrentState, directionVector, turtleState.getColor(),
				effectiveStep);

		ctx.pushState(newTurtleState);

	}

}
