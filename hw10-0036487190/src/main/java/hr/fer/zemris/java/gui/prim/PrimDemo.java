package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * This program, upon starting, displays a window with two lists one next to
 * each other and a button which, when clicked, generates the next prime number
 * and adds it to both lists as shown by Professor Marko Čupić.
 * 
 * @author Marko Mesarić
 *
 */
public class PrimDemo extends JFrame {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which sets up the frame.
	 */
	public PrimDemo() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Prime number generator");
		setLocation(100, 100);
		setSize(600, 400);
		initGUI();
	}

	/**
	 * Method used for setting up and creating elements to be added to container and
	 * showed to user.
	 */
	private void initGUI() {

		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		PrimListModel model = new PrimListModel();

		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);

		JPanel bottomPanel = new JPanel(new GridLayout(1, 0));

		JButton nextButton = new JButton("sljedeci");
		bottomPanel.add(nextButton);

		nextButton.addActionListener((e) -> model.next());

		JPanel central = new JPanel(new GridLayout(1, 0));
		central.add(new JScrollPane(list1));
		central.add(new JScrollPane(list2));

		cp.add(central, BorderLayout.CENTER);
		cp.add(bottomPanel, BorderLayout.PAGE_END);
	}

	/**
	 * Main method which starts the execution of the program.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				PrimDemo window = new PrimDemo();
				window.setVisible(true);
			}
		});
	}
}
