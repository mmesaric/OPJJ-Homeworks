package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class used for pushing turtle state to stack
 * 
 * @author Marko Mesarić
 *
 */
public class PushCommand implements Command {

	/**
	 * Method which pushes turtle state to stack in given context
	 * 
	 * @param ctx
	 *            relevant context in which Fractal is shown
	 * @param painter
	 *            object used for drawing lines
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		
		TurtleState currentState = ctx.getCurrentState();
		
		ctx.pushState(currentState.copy());
		
	}

}
