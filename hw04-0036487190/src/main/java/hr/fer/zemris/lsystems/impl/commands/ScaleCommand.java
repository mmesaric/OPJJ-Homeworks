package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class used for implementation of Scale command used for scaling the current
 * state vector with given factor
 * 
 * @author Marko Mesarić
 *
 */
public class ScaleCommand implements Command {

	/**
	 * factor value with which vector is scaled
	 */
	private double factor;

	/**
	 * Constructor which sets the factor value to given value.
	 * 
	 * @param factor
	 *            given factor value to be set
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	/**
	 * Method which scales the current state vector with given factor in given
	 * context
	 * 
	 * @param ctx
	 *            relevant context in which Fractal is shown
	 * @param painter
	 *            object used for drawing lines
	 */
	@Override
	public void execute(Context ctx, Painter painter) {

		TurtleState state = ctx.getCurrentState();

		state.getState().scale(factor);

	}

}
