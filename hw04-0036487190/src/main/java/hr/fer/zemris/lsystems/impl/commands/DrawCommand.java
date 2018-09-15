package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Class which is responsible for calculating turtle path and stroking the line
 * with given color from current position to calculated position. Remembers the
 * new position as current Turtle position in this context.
 * 
 * @author Marko Mesarić
 *
 */
public class DrawCommand implements Command {

	/**
	 * Step value which is used for turtle movement calculation
	 */
	private double step;

	/**
	 * Constructor which sets the step value to given value.
	 * 
	 * @param step
	 *            given step value
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	/**
	 * Method which calculates the position to which turtle is to be moved, sets the
	 * new turtle position to calculated value, strokes the line between old and new
	 * position and pushes newly generated turtle state to context.
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

		double effectiveStep = turtleState.getDelta()*step;

		
		Vector2D newCurrentState = new Vector2D(directionVector.getX() * effectiveStep + current.getX(),
				directionVector.getY() * effectiveStep + current.getY());

		painter.drawLine(current.getX(), current.getY(), newCurrentState.getX(), newCurrentState.getY(),
				turtleState.getColor(), 1);

//		TurtleState newTurtleState = new TurtleState(newCurrentState, directionVector.copy(), turtleState.getColor(),
//				turtleState.getDelta());
//
//		ctx.pushState(newTurtleState);
		
		turtleState.setState(newCurrentState);
	}

}
