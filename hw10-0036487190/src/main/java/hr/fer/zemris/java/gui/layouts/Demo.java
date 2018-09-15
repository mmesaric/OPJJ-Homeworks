package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class used for demonstration of 'CalcLayout' layout behavior with added test
 * buttons.
 * 
 * @author Marko Mesarić
 *
 */
public class Demo extends JFrame {
	private static final long serialVersionUID = 1L;

	public Demo() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Prozor1");
		setLocation(0, 0);
		setSize(500, 500);
		initGUI();
	}

	private void initGUI() {

		Container cp = getContentPane();

		JLabel x = new JLabel("x", SwingConstants.CENTER);
		x.setBackground(new Color(51, 153, 255));
		x.setOpaque(true);
		JLabel n = new JLabel("n", SwingConstants.CENTER);
		n.setBackground(new Color(51, 153, 255));
		n.setOpaque(true);
		JLabel y = new JLabel("y", SwingConstants.CENTER);
		y.setBackground(new Color(51, 153, 255));
		y.setOpaque(true);
		JLabel z = new JLabel("z", SwingConstants.CENTER);
		z.setOpaque(true);
		z.setBackground(new Color(51, 153, 255));
		JLabel w = new JLabel("w", SwingConstants.CENTER);
		w.setOpaque(true);
		w.setBackground(new Color(51, 153, 255));
		JLabel a = new JLabel("a", SwingConstants.CENTER);
		a.setOpaque(true);
		a.setBackground(new Color(51, 153, 255));
		JLabel b = new JLabel("b", SwingConstants.CENTER);
		b.setOpaque(true);
		b.setBackground(new Color(51, 153, 255));
		JLabel c = new JLabel("c", SwingConstants.CENTER);
		c.setOpaque(true);
		c.setBackground(new Color(51, 153, 255));
		JLabel i = new JLabel("i", SwingConstants.CENTER);
		i.setOpaque(true);
		i.setBackground(new Color(51, 153, 255));
		JLabel l = new JLabel("i", SwingConstants.CENTER);
		l.setOpaque(true);
		l.setBackground(new Color(51, 153, 255));
		JLabel j = new JLabel("i", SwingConstants.CENTER);
		j.setOpaque(true);
		j.setBackground(new Color(51, 153, 255));

		JPanel p = new JPanel(new CalcLayout(2));
		p.add(x, new RCPosition(1, 1));
		p.add(n, new RCPosition(1, 6));
		p.add(y, new RCPosition(2, 3));
		p.add(z, new RCPosition(2, 7));
		p.add(w, new RCPosition(4, 2));
		p.add(a, new RCPosition(4, 5));
		p.add(b, new RCPosition(4, 7));
		p.add(c, new RCPosition(4, 6));
		p.add(i, new RCPosition(5, 2));
		p.add(l, new RCPosition(2, 5));
		p.add(j, new RCPosition(2, 6));

		// p.add(x, "1,1");
		// p.add(y, "2,3");
		// p.add(z, "2,7");
		// p.add(w, "4,2");
		// p.add(a, "4,5");
		// p.add(b, "4,7");

		cp.add(p);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Demo window = new Demo();
				window.setVisible(true);
			}
		});
	}
}
