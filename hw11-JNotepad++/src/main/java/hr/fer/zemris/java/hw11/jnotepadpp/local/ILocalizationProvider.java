package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * This interface offers methods for a single localization provider. Through
 * this interface, managing localization listeners is available.
 * 
 * @author Marko Mesarić
 *
 */
public interface ILocalizationProvider {

	/**
	 * Adds the given localization listener
	 * 
	 * @param listener
	 *            listener to be added
	 */
	void addLocalizationListener(ILocalizationListener listener);

	/**
	 * Removes the given localization listener
	 * 
	 * @param listener
	 *            listener to be removed
	 */
	void removeLocalizationListener(ILocalizationListener listener);

	/**
	 * Getter for string value stored under given key
	 * 
	 * @param key
	 *            key value
	 * @return stored String
	 */
	String getString(String key);
}
