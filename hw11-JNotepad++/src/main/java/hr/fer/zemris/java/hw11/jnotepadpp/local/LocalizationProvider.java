package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class offers the implementation of a Localization provider. Objects
 * which are instances of classes that implement this interface will be able to
 * give translations for given keys. Localization provider is a singleton.
 * 
 * @author Marko Mesarić
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Instance of localization provider
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();

	/**
	 * current language
	 */
	private String language;
	/**
	 * resource bundle
	 */
	private ResourceBundle bundle;

	/**
	 * Default constructor which does the initial resources load
	 */
	private LocalizationProvider() {
		language = "en";
		bundle = ResourceBundle.getBundle("translations.translation", Locale.forLanguageTag(language));
	}

	/**
	 * Getter for instance 
	 * @return {@link #instance}
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	/**
	 * Getter for language
	 * @return {@link #language}
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Setter for language 
	 * @param language {@link #language}
	 */
	public void setLanguage(String language) {
		this.language = language;
		bundle = ResourceBundle.getBundle("translations.translation", Locale.forLanguageTag(language));
		fire();
	}

}
