package hr.fer.zemris.java.hw16.trazilica;

import java.nio.file.Path;

/**
 * This class models a document results containing information about document
 * path and similarity between document described by path and given query
 * document.
 * 
 * @author Marko Mesarić
 *
 */
public class DocumentResult {

	/**
	 * Path to relevant document
	 */
	private Path path;
	/**
	 * similarity between document described by path and given query document
	 */
	private double similarity;

	/**
	 * 
	 * @param path {@link #path}
	 * @param similarity {@link #similarity}
	 */
	public DocumentResult(Path path, double similarity) {
		super();
		this.path = path;
		this.similarity = similarity;
	}

	/**
	 * Getter for path
	 * @return {@link #path}
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Setter for path
	 * @param path {@link #path}
	 */
	public void setPath(Path path) {
		this.path = path;
	}

	/**
	 * Getter for similarity
	 * @return {@link #similarity}
	 */
	public double getSimilarity() {
		return similarity;
	}

	/**
	 * Setter for similarity
	 * @param similarity {@link #similarity}
	 */
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

}
