package main;

import java.util.ArrayList;

/**
 * A tree node that holds information about the game's state and evaluation of that state.
 * It points to all possible game states in the next turn, as the node's children
 * 
 * @author Τυροβούζης Θεόδωρος 
 * AEM 9369 
 * phone number 6955253435 
 * email ttyrovou@ece.auth.gr
 * 
 * @author Τσιμρόγλου Στυλιανός 
 * AEM 9468 
 * phone number 6977030504 
 * email stsimrog@ece.auth.gr
 */
public class Node {

	private Node parent;
	private ArrayList<Node> children;
	private int depth;
	private int[] nodeMove;
	private Board nodeBoard;
	private double evaluation;
	
	public Node(Node parent, Board nodeBoard, int depth, double evaluation, int currentPos, int die) {
		children = new ArrayList<Node>();
		this.parent = parent;
		this.nodeBoard = nodeBoard;
		this.depth = depth;
		this.evaluation = evaluation;
		nodeMove = new int[2];
		nodeMove[0] = currentPos;
		nodeMove[1] = die;
	}
	
	/**
	 * Adds a child to the node
	 * 
	 * @param node the node to be added
	 */
	public void addChild(Node node) {
		children.add(node);
	}

	/**
	 * Returns the board at the node's state
	 * @return the board at the node's state
	 */
	public Board getNodeBoard() {
		return nodeBoard;
	}
	
	/**
	 * Returns the child at the given index
	 * 
	 * @param index the index of the child
	 * @return the child at the given index
	 */
	public Node getChild(int index) {
		return children.get(index);
	}
	
	/**
	 * Returns the number of children of this node
	 * 
	 * @return the number of children of this node
	 */
	public int childCount() {
		return children.size();
	}
	
	/**
	 * Returns the evaluation of the node
	 * 
	 * @return the evaluation of the node
	 */
	public double getEvaluation() {
		return evaluation;
	}
	
	/**
	 * Returns the die that was rolled to end up in the node's state
	 * 
	 * @return the die that was rolled to end up in the node's state 
	 */
	public int getDie() {
		return nodeMove[1];
	}
}
