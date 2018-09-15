package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;


public class UniqueNumbersTest {

	@Test
	public void addNode() {

		TreeNode treeNode = null;
		treeNode = UniqueNumbers.addNode(treeNode, 42);
		treeNode = UniqueNumbers.addNode(treeNode, 76);
		treeNode = UniqueNumbers.addNode(treeNode, 42);
		treeNode = UniqueNumbers.addNode(treeNode, 21);
		treeNode = UniqueNumbers.addNode(treeNode, 35);

		int input1=treeNode.left.value;
		int expected1=21;	
		int input2= treeNode.left.right.value;
		int expected2=35;
		int input3= treeNode.right.value;
		int expected3=76;
		
		Assert.assertEquals(expected1, input1);
		Assert.assertEquals(expected2, input2);
		Assert.assertEquals(expected3, input3);		
	}
	
	@Test
	public void containsValue() {		
		TreeNode treeNode=null;
		treeNode=UniqueNumbers.addNode(treeNode, 5);
		treeNode=UniqueNumbers.addNode(treeNode, 6);
		
		boolean input1=UniqueNumbers.containsValue(treeNode, 5);
		boolean expected1=true;
		
		boolean input2=UniqueNumbers.containsValue(treeNode, 6);
		boolean expected2=true;
		
		boolean input3=UniqueNumbers.containsValue(treeNode, 7);
		boolean expected3=false;
		
		Assert.assertEquals(expected1, input1);
		Assert.assertEquals(expected2, input2);
		Assert.assertEquals(expected3, input3);
	}
	
	@Test
	public void treeSize() {
		TreeNode treeNode=null;
		treeNode=UniqueNumbers.addNode(treeNode, 5);
		treeNode=UniqueNumbers.addNode(treeNode, 6);
		
		int input1=UniqueNumbers.treeSize(treeNode);
		int expected1=2;
		
		treeNode=UniqueNumbers.addNode(treeNode, 8);
	
		int input2=UniqueNumbers.treeSize(treeNode);
		int expected2=3;
		
		Assert.assertEquals(expected1, input1);
		Assert.assertEquals(expected2, input2);

	}

}
