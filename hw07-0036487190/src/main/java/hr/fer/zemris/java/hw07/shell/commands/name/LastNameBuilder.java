package hr.fer.zemris.java.hw07.shell.commands.name;

/**
 * This class represents the implementation of a specific name builder used for
 * generating the last of all name builders which internally stores references 
 * to all name builders before this one.
 * 
 * @author Marko Mesarić
 *
 */
import java.util.List;

public class LastNameBuilder implements NameBuilder {

	/**
	 * List used for storing all name builders
	 */
	private List<NameBuilder> nameBuilders;

	/**
	 * Default constructor
	 * @param nameBuilders list of all name builders to be set.
	 */
	public LastNameBuilder(List<NameBuilder> nameBuilders) {
		this.nameBuilders = nameBuilders;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(NameBuilderInfo info) {

		for (NameBuilder nameBuilder : nameBuilders) {
			nameBuilder.execute(info);
		}
	}
}
