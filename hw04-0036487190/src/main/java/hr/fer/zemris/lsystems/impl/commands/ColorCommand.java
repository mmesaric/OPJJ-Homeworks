package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class which is used for storing given color value to the current Turtle state
 * 
 * @author Marko Mesarić
 *
 */
public class ColorCommand implements Command {

	/**
	 * Attribute used for storing color value
	 */
	private Color color;

	/**
	 * Constructor which sets the current color value to given value.
	 * 
	 * @param color
	 *            given color value to be set.
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	/**
	 * Method which stores color value to current turtle state
	 * 
	 * @param ctx
	 *            relevant context in which Fractal is shown
	 * @param painter
	 *            object used for drawing lines
	 */
	@Override
	public void execute(Context ctx, Painter painter) {

		TurtleState state = ctx.getCurrentState();

		 state.setColor(color);
	}
}
