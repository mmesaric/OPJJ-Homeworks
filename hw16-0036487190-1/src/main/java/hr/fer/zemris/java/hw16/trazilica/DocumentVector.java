package hr.fer.zemris.java.hw16.trazilica;

import java.nio.file.Path;
import java.util.Map;

/**
 * This class models a single document vector containing TF-IDF vector and path
 * to file with corresponding TFIDF vector.
 * 
 * @author Marko Mesarić
 *
 */
public class DocumentVector {

	/**
	 * Document file path
	 */
	private Path filePath;
	/**
	 * TF-IDF vector
	 */
	private Map<String, Double> tfIdf;
	/**
	 * TF vector
	 */
	private Map<String, Integer> tf;

	/**
	 * 
	 * @param filePath {@link #filePath}
	 * @param tf {@link #tf}
	 */
	public DocumentVector(Path filePath, Map<String, Integer> tf) {
		super();
		this.filePath = filePath;
		this.tf = tf;
	}

	/**
	 * Getter for file path
	 * @return {@link #filePath}
	 */
	public Path getFilePath() {
		return filePath;
	}

	/**
	 * Setter for file path
	 * @param filePath {@link #filePath}
	 */
	public void setFilePath(Path filePath) {
		this.filePath = filePath;
	}

	/**
	 * Getter for tf component
	 * @return {@link #tf}
	 */
	public Map<String, Integer> getTf() {
		return tf;
	}

	/**
	 * Setter for tf component 
	 * @param tf {@link #tf}
	 */
	public void setTf(Map<String, Integer> tf) {
		this.tf = tf;
	}

	/**
	 * Getter for TFIDF component
	 * @return {@link #tfIdf}
	 */
	public Map<String, Double> getTfIdf() {
		return tfIdf;
	}

	/**
	 * Setter for TFIDF component
	 * @param tfIdf {@link #tfIdf}
	 */
	public void setTfIdf(Map<String, Double> tfIdf) {
		this.tfIdf = tfIdf;
	}
}
