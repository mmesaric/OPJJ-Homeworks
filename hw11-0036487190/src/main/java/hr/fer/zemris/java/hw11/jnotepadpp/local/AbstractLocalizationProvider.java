package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is an abstract representation of a Localization provider. It
 * contains a collection of active listeners and methods needed for manipulating
 * then along with a way to notify them of change.
 * 
 * @author Marko Mesarić
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * Collection of listeners
	 */
	private List<ILocalizationListener> listeners;

	/**
	 * Initializes the object
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		Objects.requireNonNull(listener, "Listener to be added can't be null.");
		listeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		Objects.requireNonNull(listener, "Listener to be removed can't be null.");
		listeners.remove(listener);
	}

	/**
	 * Method used for notifying all active listeners
	 */
	public void fire() {
		listeners.stream().forEach((listener) -> listener.localizationChanged());
	}
}
