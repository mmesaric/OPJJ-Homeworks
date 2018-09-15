package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * This class offers the implementation of a custom JMenu used in JNotepad++.
 * Offers the solution to creating "localized" menus.
 * 
 * @author Marko Mesarić
 *
 */
public class LocalizableJMenu extends JMenu {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 * @param key key under which JMenu's name is stored
	 * @param lp localization provider
	 */
	public LocalizableJMenu(String key, ILocalizationProvider lp) {
		super();
		setText(lp.getString(key));

		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				setText(lp.getString(key));
			}
		});
	}
}
