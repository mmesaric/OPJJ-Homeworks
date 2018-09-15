package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;

/**
 * This class offers an implementation of a Localizable action by extending
 * AbstractAction. It serves as a sort of wrapper to actions and offers the
 * solution to localization.
 * 
 * @author Marko Mesarić
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 * 
	 * @param key
	 *            key under which action's name is stored
	 * @param description
	 *            description key under which action's description is stored
	 * @param lp
	 *            localization provider
	 */
	public LocalizableAction(String key, String description, ILocalizationProvider lp) {
		super();
		putValue(NAME, lp.getString(key));
		putValue(SHORT_DESCRIPTION, lp.getString(description));

		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				putValue(NAME, lp.getString(key));
				putValue(SHORT_DESCRIPTION, lp.getString(description));
			}
		});
	}
}
