package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents the implementation of a smart script engine used for
 * execution of the given smart script based on parsed syntax tree.
 * 
 * @author Marko Mesarić
 *
 */
public class SmartScriptEngine {

	/**
	 * Root node in parsed syntax tree
	 */
	private DocumentNode documentNode;
	/**
	 * Request context used printing the result of traversal
	 */
	private RequestContext requestContext;
	/**
	 * Object multi-stack used when visiting for loop
	 */
	private ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * Custom constructor which creates the object with given values
	 * 
	 * @param documentNode
	 *            {@link #documentNode}
	 * @param requestContext
	 *            {@link #requestContext}
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		super();
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Method which starts the execution of syntax tree traversal.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

	/**
	 * This class represents the implementation of the Visitor object in Visitor
	 * design pattern. It implements the behavior when visiting all types of nodes
	 * in parse tree.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {

			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				throw new IllegalStateException("Can't write contents of text node to request context.");
			}

		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {

			ElementVariable variable = node.getVariable();
			Element startExpression = node.getStartExpression();
			Element endExpression = node.getEndExpression();
			Element stepExpression = node.getStepExpression();

			ValueWrapper start = new ValueWrapper(startExpression.asText());

			multistack.push(variable.getName(), start);

			ArrayIndexedCollection collection = node.getArrayIndexedCollection();

			while (start.numCompare(endExpression.asText()) <= 0) {

				for (int i = 0; i < collection.size(); i++) {
					Node childNode = (Node) collection.get(i);
					childNode.accept(this);
				}

				multistack.peek(variable.getName()).add(stepExpression.asText());
			}
			multistack.pop(variable.getName());

		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitEchoNode(EchoNode node) {

			Stack<Object> stack = new Stack<>();

			Element[] elements = node.getElements();

			for (int i = 0; i < elements.length; i++) {

				if (elements[i] instanceof ElementConstantDouble) {
					ElementConstantDouble doubleConstant = (ElementConstantDouble) elements[i];
					stack.push(doubleConstant.getValue());
				} else if (elements[i] instanceof ElementConstantInteger) {
					ElementConstantInteger integerConstant = (ElementConstantInteger) elements[i];
					stack.push(integerConstant.getValue());
				} else if (elements[i] instanceof ElementString) {
					ElementString stringConstant = (ElementString) elements[i];
					stack.push(stringConstant.getValue());
				} else if (elements[i] instanceof ElementVariable) {
					ElementVariable variable = (ElementVariable) elements[i];
					stack.push(multistack.peek(variable.getName()).getValue());

				} else if (elements[i] instanceof ElementOperator) {
					performOperation(stack, elements[i]);
				} else if (elements[i] instanceof ElementFunction) {
					performFunction(stack, elements[i]);
				}

			}
			for (Object obj : stack) {
				try {
					requestContext.write(obj.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * Auxiliary method used for executing different kinds of functions defined in
		 * assignment.
		 * 
		 * @param stack
		 *            reference to stack containing objects on which function is to be
		 *            performed
		 * @param element
		 *            current element
		 */
		private void performFunction(Stack<Object> stack, Element element) {

			ElementFunction function = (ElementFunction) element;
			String functionType = function.getName();

			switch (functionType) {
			case "sin":
				ValueWrapper wrapper = new ValueWrapper(stack.pop());
				double x = Math.sin(Math.toRadians(Double.parseDouble(wrapper.getValue().toString())));
				stack.push(x);
				break;
			case "decfmt":
				DecimalFormat formatter = new DecimalFormat(stack.pop().toString());
				Object toFormat = new ValueWrapper(stack.pop()).getValue();
				stack.push(formatter.format(toFormat));
				break;
			case "dup":
				Object obj = stack.pop();
				stack.push(obj);
				stack.push(obj);
				break;
			case "swap":
				Object a = stack.pop();
				Object b = stack.pop();
				stack.push(a);
				stack.push(b);
				break;
			case "setMimeType":
				requestContext.setMimeType(stack.pop().toString());
				break;
			case "paramGet":
				Object defaultValue = stack.pop();
				Object name = stack.pop();
				String value = requestContext.getParameter(name.toString());
				stack.push(value == null ? defaultValue.toString() : value);
				break;
			case "pparamGet":
				Object defaultValueP = stack.pop();
				Object nameP = stack.pop();
				String valueP = requestContext.getPersistentParameter(nameP.toString());
				stack.push(valueP == null ? defaultValueP.toString() : valueP);
				break;
			case "pparamSet":
				Object namePP = stack.pop();
				String valuePP = stack.pop().toString();
				requestContext.setPersistentParameter(namePP.toString(), valuePP);
				break;
			case "pparamDel":
				requestContext.removePersistentParameter(stack.pop().toString());
				break;
			case "tparamGet":
				Object defaultValueT = stack.pop();
				Object nameT = stack.pop();
				String valueT = requestContext.getTemporaryParameter(nameT.toString());
				stack.push(valueT == null ? defaultValueT.toString() : valueT);
				break;
			case "tparamSet":
				Object nameTT = stack.pop();
				String valueTT = stack.pop().toString();
				requestContext.setTemporaryParameter(nameTT.toString(), valueTT);
				break;
			case "tparamDel":
				requestContext.removeTemporaryParameter(stack.pop().toString());
				break;
			}
		}

		/**
		 * Auxiliary method used for executing different kinds of operations defined in
		 * assignment.
		 * 
		 * @param stack
		 *            reference to stack containing objects on which function is to be
		 *            performed
		 * @param element
		 *            current element
		 */
		private void performOperation(Stack<Object> stack, Element element) {

			ElementOperator operator = (ElementOperator) element;
			String operation = operator.getSymbol();

			Object operand2 = new ValueWrapper(stack.pop()).getValue();
			ValueWrapper operand1 = new ValueWrapper(stack.pop());

			switch (operation) {
			case "+":
				operand1.add(operand2);
				break;
			case "-":
				operand1.subtract(operand2);
				break;
			case "*":
				operand1.multiply(operand2);
				break;
			case "/":
				operand1.divide(operand2);
				break;
			}

			stack.push(operand1.getValue());

		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {

			ArrayIndexedCollection collection = node.getArrayIndexedCollection();

			for (int i = 0; i < collection.size(); i++) {
				Node childNode = (Node) collection.get(i);
				childNode.accept(this);
			}
		}
	};
}
