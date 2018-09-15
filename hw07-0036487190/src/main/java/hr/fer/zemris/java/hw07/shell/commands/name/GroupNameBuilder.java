package hr.fer.zemris.java.hw07.shell.commands.name;

/**
 * This class represents the implementation of a specific name builder used for
 * generating names of groups.
 * 
 * @author Marko Mesarić
 *
 */
public class GroupNameBuilder implements NameBuilder {

	/**
	 * Used for storing group index
	 */
	private int groupIndex;
	/**
	 * Format which defines the ouput format
	 */
	private String format;

	/**
	 * Default constructor
	 * 
	 * @param groupIndex
	 *            index to be set
	 * @param format
	 *            format to be set
	 */
	public GroupNameBuilder(int groupIndex, String format) {
		this.groupIndex = groupIndex;
		this.format = format;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(NameBuilderInfo info) {

		if (format.equals("")) {
			info.getStringBuilder().append(info.getGroup(groupIndex));
		} else {
			int formatSize = Integer.parseInt(format);
			String formatting = "";

			if (format.startsWith("0")) {
				formatting = "%0" + formatSize + "d";
			} else {
				formatting = "%" + formatSize + "d";
			}
			int value = Integer.parseInt(info.getGroup(groupIndex));
			info.getStringBuilder().append(String.format(formatting, value));
		}
	}
}
