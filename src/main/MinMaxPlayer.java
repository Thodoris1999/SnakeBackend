package main;

public class MinMaxPlayer extends Player {
	
	public MinMaxPlayer(int playerId, String name, int score, Board board) {
		super(playerId, name, score, board);
	}
	
	private double evaluate(int currentPos, int dice, Board board) {
		// temporarily change board and score
		int oldScore = score;
		this.board = board;
		
		int[] move = move(currentPos, dice);
		
		return 0.8 * (move[0] - currentPos) + 0.2 * (score - oldScore);
	}
	
	private int chooseMinMaxMove(Node root) {
		
	}
	
	public int[] getNextMove(int currentPos, int opponentCurrentPos) {
		Node root = new Node(null, board, 0, /* TODO: root evaluation? */ 0, currentPos, /* TODO: root die? */ 0);
		createMySubtree(root, 1, currentPos, opponentCurrentPos);
		int bestMoveIndex = chooseMinMaxMove(root);
		Node bestNode = root.getChild(bestMoveIndex);
		int[] moveResult = move(currentPos, bestNode.getDie());
		path.add(moveResult);
		return moveResult;
	}

	private void createMySubtree(Node parent, int depth, int currentPos, int opponentCurrentPos) {
		Board parentBoard = new Board(parent.getNodeBoard());
		// we need to restore the board after tree creation
		Board initialBoard = board;
		// make sure each evaluate() is called with the same score value the parent original had, and the score is
		// restore in the end
		int parentScore = score;
		for (int die = 0; die < 7; die++) {
			double evaluation = evaluate(currentPos, die, parentBoard);
			Node moveNode = new Node(parent, board, depth, evaluation, currentPos, die);
			parent.addChild(moveNode);
			createOpponentSubtree(moveNode, depth + 1, currentPos, opponentCurrentPos, evaluation);
			score = parentScore;
		}
		board = initialBoard;
	}
	
	private void createOpponentSubtree(Node parent, int depth, int currentPos, int opponentCurrentPos, double parentEval) {
		Board parentBoard = new Board(parent.getNodeBoard());
		for (int die = 0; die < 7; die++) {
			double evaluation = evaluate(opponentCurrentPos, die, parentBoard);
			Node moveNode = new Node(parent, board, depth, evaluation, currentPos, die);
			parent.addChild(moveNode);
		}
	}
}
