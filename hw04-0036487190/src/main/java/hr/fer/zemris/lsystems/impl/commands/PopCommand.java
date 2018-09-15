package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class used for removing last state from stack
 * 
 * @author Marko Mesarić
 *
 */
public class PopCommand implements Command {

	/**
	 * Method which removes last state from stack in given context
	 * 
	 * @param ctx
	 *            relevant context in which Fractal is shown
	 * @param painter
	 *            object used for drawing lines
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}
}
