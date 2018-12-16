package main;

import java.util.ArrayList;

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
	
	public void addChild(Node node) {
		children.add(node);
	}

	public Board getNodeBoard() {
		return nodeBoard;
	}
	
	public Node getChild(int index) {
		return children.get(index);
	}
	
	public int getDie() {
		return nodeMove[1];
	}
}
