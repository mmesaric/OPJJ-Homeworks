package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class used for implementation of Rotation command used for rotating the
 * direction vector of current turtle state
 * 
 * @author Marko Mesarić
 *
 */
public class RotateCommand implements Command {

	/**
	 * angle value with which the direction vector is rotated
	 */
	private double angle;

	/**
	 * Constructor which sets the angle to given value
	 * @param angle given angle value
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	/**
	 * Method which rotates the direction vector of the current turtle state for
	 * given angle value
	 * 
	 * @param ctx
	 *            relevant context in which Fractal is shown
	 * @param painter
	 *            object used for drawing lines
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
//
//		Vector2D state = ctx.getCurrentState().getDirection();
//		
//		state.rotate(angle);
		
		TurtleState state = ctx.getCurrentState();
		state.setDirection(state.getDirection().rotated(angle));

	}
}
