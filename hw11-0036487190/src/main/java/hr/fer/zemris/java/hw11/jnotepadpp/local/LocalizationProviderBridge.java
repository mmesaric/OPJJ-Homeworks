package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * This class offers the implementation of a decorator for some other
 * IlocalizationProvider. It manages a connection status and is responsible for
 * connecting and disconnecting (managing localization listeners).
 * 
 * @author Marko Mesarić
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * managing connection status
	 */
	private boolean connected;
	/**
	 * Localization provider
	 */
	private ILocalizationProvider parent;
	/**
	 * localization listener
	 */
	private ILocalizationListener listener;

	/**
	 * 
	 * @param parent
	 *            {@link #parent}
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
		connected = false;
	}

	/**
	 * "Connects" by adding newly created localization listener to parent
	 * localization provider
	 */
	public void connect() {
		if (connected)
			return;
		listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				fire();
			}
		};
		parent.addLocalizationListener(listener);
		connected = true;
	}

	/***
	 * "Disconnects" by removing given localization listener from parent
	 * localization provider
	 */
	public void disconnect() {
		if (!connected)
			return;
		parent.removeLocalizationListener(listener);
		connected = false;
	}

	/**
	 * Returns the string stored under given key
	 */
	public String getString(String key) {
		return parent.getString(key);
	}

}
