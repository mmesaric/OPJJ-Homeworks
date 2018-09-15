package hr.fer.zemris.java.galerija.model;

import java.util.List;

/**
 * This class models a single Picture entry parsed from "opisnik.txt" file
 * located in WEB-INF folder.
 * 
 * @author Marko Mesarić
 *
 */
public class PictureEntry {

	/**
	 * Picture filename
	 */
	private String fileName;
	/**
	 * Picture description
	 */
	private String description;
	/**
	 * Picture tags
	 */
	private List<String> tags;

	/**
	 * Constructor which sets the values of this object to given values
	 * 
	 * @param fileName {@link #fileName}
	 * @param description {@link #description}
	 * @param tags {@link #tags}
	 */
	public PictureEntry(String fileName, String description, List<String> tags) {
		super();
		this.fileName = fileName;
		this.description = description;
		this.tags = tags;
	}

	/**
	 * Getter for file name
	 * @return {@link #fileName}
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Setter for file name
	 * @param fileName {@link #fileName}
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Getter for description
	 * @return {@link #description}
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter for description
	 * @param fileName {@link #description}
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter for tags list
	 * @return {@link #tags}
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * Setter for tags
	 * @param fileName {@link #tags}
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
