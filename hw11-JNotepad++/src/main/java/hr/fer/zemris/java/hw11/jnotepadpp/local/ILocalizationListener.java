package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface which offers the method which is called after every localization
 * change.
 * 
 * @author Marko Mesarić
 *
 */
public interface ILocalizationListener {

	/**
	 * Called after every localization change
	 */
	void localizationChanged();
}
