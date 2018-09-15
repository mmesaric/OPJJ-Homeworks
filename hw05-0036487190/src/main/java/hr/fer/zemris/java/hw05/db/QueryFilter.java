package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Class used for examining the conditions of the given list of conditional
 * expressions and determining if given student records satisfies those
 * conditions.
 * 
 * @author Marko Mesarić
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * List of all conditional expressions.
	 */
	List<ConditionalExpression> conditionalExpressions;

	/**
	 * Constructor which receives the list of conditional expressions and manages it
	 * 
	 * @param conditionalExpressions
	 *            list of conditional expressions which are evaluated
	 */
	public QueryFilter(List<ConditionalExpression> conditionalExpressions) {

		this.conditionalExpressions = conditionalExpressions;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(StudentRecord record) {

		boolean satisfied = true;

		for (ConditionalExpression condition : conditionalExpressions) {
			satisfied &= condition.getComparisonOperator().satisfied(condition.getFieldValueGetter().get(record),
					condition.getLiteral());
		}

		return satisfied;
	}
}
