package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JLabel;

/**
 * This class represents the custom JLabel which offers the support of
 * localization and dynamic language change.
 * 
 * @author Marko Mesarić
 *
 */
public class LocalizableJLabel extends JLabel {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 * @param key key under which label's name is stored
	 * @param lp localization provider
	 */
	public LocalizableJLabel(String key, ILocalizationProvider lp) {
		super();
		setText(lp.getString(key));

		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				String currentText = getText();
				String[] number = currentText.split(":");
				if (number.length == 2) {
					setText(lp.getString(key) + number[1]);
				} else {
					setText(lp.getString(key));
				}
			}
		});
	}

}
