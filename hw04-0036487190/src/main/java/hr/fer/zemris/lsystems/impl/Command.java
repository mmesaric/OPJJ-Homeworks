package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * This interface defines a method used for executing commands in the given
 * context by commands that implement it.
 * 
 * @author Marko Mesarić
 *
 */
public interface Command {
	void execute(Context ctx, Painter painter);

}
