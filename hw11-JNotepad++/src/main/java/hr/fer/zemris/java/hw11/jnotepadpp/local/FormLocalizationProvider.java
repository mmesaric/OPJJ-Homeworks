package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Objects from this class, when created, add window listener to given frame in
 * order to be able to connect and disconnect from listeners.
 * 
 * @author Marko Mesarić
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Default constructor.
	 * @param parent
	 *            parent localization provider
	 * @param frame
	 *            on which window listener is added
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {

				connect();
			}

			@Override
			public void windowClosed(WindowEvent e) {

				disconnect();
			}
		});
	}

}
