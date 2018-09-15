package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * This class consists exclusively of static methods which define 
 * operations used for Binary Tree generation according to user input.
 * Tree is ultimately used as a data structure to store unique number values. 
 * @author Marko Mesaric
 *
 */
public class UniqueNumbers {

	/**
	 * This static class represents the Tree Node model which 
	 * consists of declarations of the left node, right node and 
	 * value of the current node. 
	 * 
	 */
	public static class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;
	}

	/**
	 * Main method responsible for consuming user input and calling
	 * dedicated methods for Binary Tree generation and traversal.
	 * In case of a wrong type or value of passed input, notifies 
	 * the user and continues prompting for input and adding values to tree
	 * until "kraj" is passed as input.
	 * @param args String array of input arguments passed from console
	 */
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		TreeNode treeNode = null;

		while (true) {
			int input = 0;
			System.out.println("Unesite broj");
			String line = scanner.nextLine();

			if (line.equals("kraj")) {
				printTree(treeNode);
				break;
			}

			try {
				input = Integer.parseInt(line);
			}  catch (NumberFormatException ex) {
				System.out.println("\'" + line + "\' " + " nije cijeli broj");
				continue;
			}

			if (!containsValue(treeNode, input)) {
				treeNode = addNode(treeNode, input);
				System.out.println("Dodano.");
			} else {
				System.out.println("Broj već postoji. Preskačem.");
			}

		}
		
		scanner.close();
	}

	/**
	 * This method traverses the tree in recursive manner and 
	 * searches for occurrence of provided value
	 * @param treeNode referent Node
	 * @param value returns true if tree contains value, false otherwise 
	 * @return
	 */
	public static boolean containsValue(TreeNode treeNode, int value) {
		if (treeNode == null) {
			return false;
		}

		if (treeNode.value == value) {
			return true;
		}

		if (value < treeNode.value) {
			return containsValue(treeNode.left, value);
		} else {
			return containsValue(treeNode.right, value);
		}

	}

	/**
	 * Method responsible for traversing the Binary Tree
	 * in recursive manner. In case when there are no nodes 
	 * in the tree, returns zero.
	 * @param treeNode referent node structure
	 * @return number of nodes in the Tree
	 */
	public static int treeSize(TreeNode treeNode) {
		if (treeNode == null) {
			return 0;
		}

		return 1 + treeSize(treeNode.left) + treeSize(treeNode.right);
	}

	/**
	 * Method responsible for traversing the Binary Tree 
	 * in recursive manner and adding the Node in dedicated
	 * position if node with demanded value doesn't already exist. 
	 * @param treeNode referent node structure
	 * @param value the value to be added in tree
	 * @return referent Node structure
	 */
	public static TreeNode addNode(TreeNode treeNode, int value) {

		if (treeNode == null) {
			TreeNode root = new TreeNode();
			root.value=value;
			return root;
		}

		if (value < treeNode.value) {
			treeNode.left = addNode(treeNode.left, value);
		} else if (value > treeNode.value) {
			treeNode.right = addNode(treeNode.right, value);
		}

		return treeNode;
	}
	
	/**
	 * Method responsible for calling two different methods which 
	 * print the contents of the tree from smallest to biggest value 
	 * and in reverse from biggest to smallest after that. 
	 * @param treeNode referent Node
	 */
	private static void printTree(TreeNode treeNode) {
		System.out.print("Ipis od najmanjeg: ");
		
		printFromSmallest(treeNode);
		
		System.out.println();
		System.out.print("Ipis od najvećeg: ");
		
		printFromBiggest(treeNode);

	}

	/**
	 * This method traverses the tree in recursive manner 
	 * and prints its contents from biggest to smallest value
	 * @param treeNode referent Node
	 */
	private static void printFromBiggest(TreeNode treeNode) {
		
		if(treeNode!=null) {
			printFromBiggest(treeNode.right);
			System.out.print(treeNode.value+ " ");
			printFromBiggest(treeNode.left);
		}
		
	}

	/**
	 * This method reverses the tree in recursive manner 
	 * and prints its contents from smallest to biggest value
	 * @param treeNode
	 */
	private static void printFromSmallest(TreeNode treeNode) {
		
		if(treeNode!=null) {
			printFromSmallest(treeNode.left);
			System.out.print(treeNode.value+ " ");
			printFromSmallest(treeNode.right);
		}
	}
}
