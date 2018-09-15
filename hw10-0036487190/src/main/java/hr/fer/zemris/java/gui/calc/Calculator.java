package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EmptyStackException;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.actions.OnDigitClickAction;
import hr.fer.zemris.java.gui.calc.actions.OnUnaryOperatorClickAction;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * This class offers the implementation of a calculator program using the
 * 'CalcLayout' layout solved in first problem. Digits and all other commands
 * are inputed manually by clicking the desired buttons. Values can't be inputed
 * through keyboard. Binary operations are performed in this manner: if we
 * wanted to calculate the summation of number 2 and 3, we would click button
 * '2', then click button '+', click '3' after, and finally, we would click
 * button '='. All binary operations are performed in this manner. Unary
 * operations are performed by inputing the desired number over which operation
 * is to be executed and then choosing the desired operation. When 'inv' check
 * box is ticked, some of the operations are reversed.
 * 
 * @author Marko Mesarić
 *
 */
public class Calculator extends JFrame {

	/**
	 * default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Container used for storing components
	 */
	private JPanel containerPanel;
	/**
	 * calculator model
	 */
	private CalcModel calcModel;
	/**
	 * check box used for deciding if regular or inverse operations are to be
	 * performed.
	 */
	private JCheckBox inv;
	/**
	 * Stack for storing values.
	 */
	private Stack<Double> stack;

	/**
	 * Default constructor
	 */
	public Calculator() {
		super();

		containerPanel = new JPanel(new CalcLayout(5));
		calcModel = new CalcModelImpl();
		inv = new JCheckBox("inv");
		stack = new Stack<>();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Calculator");
		setLocation(100, 100);
		setSize(600, 400);
		initGUI();
	}

	/**
	 * Starts the creation of GUI elements.
	 */
	private void initGUI() {
		createAndAddElements();
	}

	/**
	 * Method which calls the appropriate methods which build the GUI. Adds these
	 * elements to content pane in order to be displayed.
	 */
	private void createAndAddElements() {

		addDigits();
		addUnaryOperatorSymbols();
		addBinaryOperatorSymbols();
		addRemainingElements();
		addResultScreen();

		Container cp = getContentPane();
		cp.add(containerPanel);
	}

	/**
	 * Auxiliary method which adds the result "screen" to this frame and appoints
	 * him with the calculator value listener in order to track changes over value.
	 */
	private void addResultScreen() {

		JLabel result = new JLabel("", SwingConstants.RIGHT);
		result.setBackground(Color.orange);
		result.setOpaque(true);

		containerPanel.add(result, new RCPosition(1, 1));

		CalcValueListener listener = new CalcValueListener() {

			@Override
			public void valueChanged(CalcModel model) {
				result.setText(model.toString());
			}
		};

		calcModel.addCalcValueListener(listener);
	}

	/**
	 * Auxiliary method used for creating and setting up other elements
	 */
	private void addRemainingElements() {

		JButton equalSymbol = new JButton("=");
		JButton decimalPointSymbol = new JButton(".");
		JButton swapSymbol = new JButton("+/-");
		JButton clr = new JButton("clr");
		JButton res = new JButton("res");
		JButton push = new JButton("push");
		JButton pop = new JButton("pop");
		JButton divide1WithX = new JButton("1/x");
		JButton power = new JButton("x^n");

		inv.setHorizontalAlignment(SwingConstants.CENTER);
		inv.setBackground(new Color(51, 153, 255));
		inv.setOpaque(true);

		containerPanel.add(equalSymbol, new RCPosition(1, 6));
		containerPanel.add(decimalPointSymbol, new RCPosition(5, 5));
		containerPanel.add(swapSymbol, new RCPosition(5, 4));
		containerPanel.add(clr, new RCPosition(1, 7));
		containerPanel.add(res, new RCPosition(2, 7));
		containerPanel.add(push, new RCPosition(3, 7));
		containerPanel.add(pop, new RCPosition(4, 7));
		containerPanel.add(divide1WithX, new RCPosition(2, 1));
		containerPanel.add(inv, new RCPosition(5, 7));
		containerPanel.add(power, new RCPosition(5, 1));

		equalSymbol.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					double value = calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(),
							calcModel.getValue());
					calcModel.setValue(value);
					calcModel.clearActiveOperand();
				} catch (IllegalStateException exc) {
					JOptionPane.showMessageDialog(containerPanel, "Active operand is not set.");
				}
			}
		});
		decimalPointSymbol.addActionListener((e) -> calcModel.insertDecimalPoint());
		swapSymbol.addActionListener((e) -> calcModel.swapSign());
		clr.addActionListener((e) -> calcModel.clear());
		res.addActionListener((e) -> calcModel.clearAll());
		push.addActionListener((e) -> stack.push(calcModel.getValue()));
		pop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					double newValue = stack.pop();
					calcModel.setValue(newValue);
				} catch (EmptyStackException exc) {
					JOptionPane.showMessageDialog(containerPanel, "Stack is empty.");
				}
			}
		});
		divide1WithX.addActionListener((e) -> calcModel.setValue(1.0 / calcModel.getValue()));
		power.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (inv.isSelected()) {
					calcModel.setPendingBinaryOperation((num1, num2) -> Math.pow(num1, 1.0 / num2));
					return;
				}
				calcModel.setPendingBinaryOperation((num1, num2) -> Math.pow(num1, num2));
			}
		});

	}

	/**
	 * Auxiliary method used for creating and setting up the binary operator symbol
	 * elements with appropriate listeners.
	 */
	private void addBinaryOperatorSymbols() {

		JButton divisionSymbol = new JButton("/");
		JButton multiplicationSymbol = new JButton("*");
		JButton subSymbol = new JButton("-");
		JButton addSymbol = new JButton("+");

		containerPanel.add(divisionSymbol, new RCPosition(2, 6));
		containerPanel.add(multiplicationSymbol, new RCPosition(3, 6));
		containerPanel.add(subSymbol, new RCPosition(4, 6));
		containerPanel.add(addSymbol, new RCPosition(5, 6));

		divisionSymbol.addActionListener((e) -> calcModel.setPendingBinaryOperation((num1, num2) -> num1 / num2));
		multiplicationSymbol.addActionListener((e) -> calcModel.setPendingBinaryOperation((num1, num2) -> num1 * num2));
		subSymbol.addActionListener((e) -> calcModel.setPendingBinaryOperation((num1, num2) -> num1 - num2));
		addSymbol.addActionListener((e) -> calcModel.setPendingBinaryOperation((num1, num2) -> num1 + num2));

	}

	/**
	 * Auxiliary method used for creating and setting up the unary operator symbol
	 * elements with appropriate listeners.
	 */
	private void addUnaryOperatorSymbols() {

		JButton sin = new JButton("sin");
		JButton cos = new JButton("cos");
		JButton tan = new JButton("tan");
		JButton ctg = new JButton("ctg");
		JButton log = new JButton("log");
		JButton ln = new JButton("ln");

		containerPanel.add(sin, new RCPosition(2, 2));
		containerPanel.add(cos, new RCPosition(3, 2));
		containerPanel.add(tan, new RCPosition(4, 2));
		containerPanel.add(ctg, new RCPosition(5, 2));
		containerPanel.add(log, new RCPosition(3, 1));
		containerPanel.add(ln, new RCPosition(4, 1));

		sin.addActionListener(
				new OnUnaryOperatorClickAction(calcModel, value -> Math.sin(value), value -> Math.asin(value), inv));
		cos.addActionListener(
				new OnUnaryOperatorClickAction(calcModel, value -> Math.cos(value), value -> Math.acos(value), inv));
		tan.addActionListener(
				new OnUnaryOperatorClickAction(calcModel, value -> Math.tan(value), value -> Math.atan(value), inv));
		ctg.addActionListener(new OnUnaryOperatorClickAction(calcModel, value -> (1.0 / Math.tan(value)),
				value -> (Math.PI / 2 - Math.atan(value)), inv));
		log.addActionListener(new OnUnaryOperatorClickAction(calcModel, value -> Math.log10(value),
				value -> Math.pow(10, value), inv));
		ln.addActionListener(new OnUnaryOperatorClickAction(calcModel, value -> Math.log(value),
				value -> Math.pow(Math.E, value), inv));

	}

	/**
	 * Method used for creating the digit buttons and setting up the on-click
	 * behavior
	 */
	private void addDigits() {

		JButton number0 = new JButton("0");
		JButton number1 = new JButton("1");
		JButton number2 = new JButton("2");
		JButton number3 = new JButton("3");
		JButton number4 = new JButton("4");
		JButton number5 = new JButton("5");
		JButton number6 = new JButton("6");
		JButton number7 = new JButton("7");
		JButton number8 = new JButton("8");
		JButton number9 = new JButton("9");

		containerPanel.add(number7, new RCPosition(2, 3));
		containerPanel.add(number8, new RCPosition(2, 4));
		containerPanel.add(number9, new RCPosition(2, 5));
		containerPanel.add(number4, new RCPosition(3, 3));
		containerPanel.add(number5, new RCPosition(3, 4));
		containerPanel.add(number6, new RCPosition(3, 5));
		containerPanel.add(number1, new RCPosition(4, 3));
		containerPanel.add(number2, new RCPosition(4, 4));
		containerPanel.add(number3, new RCPosition(4, 5));
		containerPanel.add(number0, new RCPosition(5, 3));

		number0.addActionListener(new OnDigitClickAction(calcModel, Integer.parseInt(number0.getText())));
		number1.addActionListener(new OnDigitClickAction(calcModel, Integer.parseInt(number1.getText())));
		number2.addActionListener(new OnDigitClickAction(calcModel, Integer.parseInt(number2.getText())));
		number3.addActionListener(new OnDigitClickAction(calcModel, Integer.parseInt(number3.getText())));
		number4.addActionListener(new OnDigitClickAction(calcModel, Integer.parseInt(number4.getText())));
		number5.addActionListener(new OnDigitClickAction(calcModel, Integer.parseInt(number5.getText())));
		number6.addActionListener(new OnDigitClickAction(calcModel, Integer.parseInt(number6.getText())));
		number7.addActionListener(new OnDigitClickAction(calcModel, Integer.parseInt(number7.getText())));
		number8.addActionListener(new OnDigitClickAction(calcModel, Integer.parseInt(number8.getText())));
		number9.addActionListener(new OnDigitClickAction(calcModel, Integer.parseInt(number9.getText())));
	}

	/**
	 * Main method which begins the execution of a calculator program.
	 * 
	 * @param args
	 *            command line arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Calculator window = new Calculator();
				window.setVisible(true);
			}
		});
	}
}
