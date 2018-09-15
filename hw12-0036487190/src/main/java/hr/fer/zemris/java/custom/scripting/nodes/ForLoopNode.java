package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct. It inherits from Node class.
 * 
 * @author Marko Mesarić
 *
 */
public class ForLoopNode extends Node {

	/**
	 * variable element of the for loop node
	 */
	private ElementVariable variable;
	/**
	 * startExpression in for loop node
	 */
	private Element startExpression;
	/**
	 * end expression in for loop node
	 */
	private Element endExpression;
	/**
	 * optional for loop node stepExpression
	 */
	private Element stepExpression;

	/**
	 * Constructor which sets the attributes' values to given values.
	 * 
	 * @param variable
	 *            variable value
	 * @param startExpression
	 *            start expression value
	 * @param endExpression
	 *            end expression value
	 * @param stepExpression
	 *            optional step expression value
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Getter for variable
	 * 
	 * @return variable element
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Getter for startExpression
	 * 
	 * @return start expression element
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Getter for endExpression
	 * 
	 * @return end expression element
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Getter for optional step expression element
	 * 
	 * @return step expression element
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("{$ FOR ").append(variable.asText()).append(" ").append(startExpression.asText()).append(" ").append(endExpression.asText());
		stringBuilder.append(stepExpression==null ? "" : " " +stepExpression.asText()).append(" ");
		stringBuilder.append("$}");
		
		return stringBuilder.toString();

//		if (stepExpression == null) {
//			return variable.asText() + " " + startExpression.asText()+ " " + endExpression.asText();
//		}
//		return variable.asText()+" " + startExpression.asText()+ " " + endExpression.asText()+ " " + stepExpression.toString();
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
}
