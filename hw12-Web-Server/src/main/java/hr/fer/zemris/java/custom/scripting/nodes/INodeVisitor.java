package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This interface defines the methods which each node visitor has to implement
 * when traversing the syntax tree.
 * 
 * @author Marko Mesarić
 *
 */
public interface INodeVisitor {

	/**
	 * Method which executes upon visiting text node
	 * 
	 * @param node
	 *            visited text node
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Method which executes upon visiting for loop node
	 * 
	 * @param node
	 *            visited for loop node
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Method which executes upon visiting echo node
	 * 
	 * @param node
	 *            visited echo node
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Method which executes upon visiting document node
	 * 
	 * @param node
	 *            visited document node
	 */
	public void visitDocumentNode(DocumentNode node);
}
