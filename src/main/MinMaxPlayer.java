package main;

/**
 * Represents player in snake that moves accordingly to the best possible move,
 * according to the minmax algorithm, computed in {@link #evaluate(int, int, int, Board, int, boolean)}
 *
 * @author Τυροβούζης Θεόδωρος
 * AEM 9369
 * phone number 6955253435
 * email ttyrovou@ece.auth.gr
 * @author Τσιμρόγλου Στυλιανός
 * AEM 9468
 * phone number 6977030504
 * email stsimrog@ece.auth.gr
 */
public class MinMaxPlayer extends Player {

    public MinMaxPlayer(int playerId, String name, int score, Board board) {
        super(playerId, name, score, board);
    }

    /**
     * Evaluates a game state based on the position of the two players and their scores. The higher the value return,
     * the more favorable the state is for the MinMaxPlayer. The lower it is, the more favorable the state is for the
     * opponent
     *
     * @param currentPos         the MinMaxPlayer's current position
     * @param opponentCurrentPos the opponent's current position
     * @param dice               the dice the player rolled
     * @param board              the board before right before the player's turn
     * @param opponentScore      the opponent's score
     * @param opponentTurn       whether it is the opponent's turn or the MinMaxPlayer's turn
     * @return the evaluation.
     */
    private double evaluate(int currentPos, int opponentCurrentPos, int dice, Board board, int opponentScore, boolean opponentTurn) {
        this.board = board;
        int[] move = move(opponentTurn ? opponentCurrentPos : currentPos, dice, false);
        return 0.869 * (opponentTurn ? currentPos - move[0] : move[0] - opponentCurrentPos) + 0.131 * (score - opponentScore);
    }

    /**
     * Implements the minmax algorithm to choose the best next move
     *
     * @param root the root node of the tree with all the possible future game states
     * @return the index of the child node that represents the best move according to the minmax algorithm
     */
    private int chooseMinMaxMove(Node root) {
        double[] minima = new double[6];
        int maxIndex = 0;
        for (int i = 0; i < root.childCount(); i++) {
            Node child = root.getChild(i);
            // find the minimum of each direct child of the root
            double min = 0;
            for (int j = 0; j < child.childCount(); j++) {
                if (child.getChild(j).getEvaluation() < min) {
                    min = child.getChild(j).getEvaluation();
                }
            }
            // find the maximum of the minima
            minima[i] = min;
            if (minima[i] > minima[maxIndex])
                maxIndex = i;
        }
        return maxIndex;
    }

    /**
     * Computes and executes the player's next move.
     *
     * @param currentPos         the current position of the player
     * @param opponentCurrentPos the current position of the opponent
     * @param opponentScore      the opponent's score
     * @return information about the performed move, as defined by {@link Player#move(int, int, boolean)}
     */
    public int[] getNextMove(int currentPos, int opponentCurrentPos, int opponentScore) {
        Node root = new Node(null, board, 0, 0, currentPos, 0);
        createMySubtree(root, 1, currentPos, opponentCurrentPos, opponentScore);
        int bestMoveIndex = chooseMinMaxMove(root);
        Node bestNode = root.getChild(bestMoveIndex);
        int[] moveResult = move(currentPos, bestNode.getDie(), true);
        path.add(moveResult);
        return moveResult;
    }

    public int[] getNextFrontendMove(int currentPos, int opponentCurrentPos, int opponentScore) {
        Node root = new Node(null, board, 0, 0, currentPos, 0);
        createMySubtree(root, 1, currentPos, opponentCurrentPos, opponentScore);
        int bestMoveIndex = chooseMinMaxMove(root);
        Node bestNode = root.getChild(bestMoveIndex);
        int[] moveResult = frontendMove(currentPos, bestNode.getDie());
        path.add(moveResult);
        return moveResult;
    }

    /**
     * Generates a subtree with all the possible moves and game states in 2 turns.
     *
     * @param parent             A node that represents the games state now
     * @param depth              the depth of the first children of the generated subtree
     * @param currentPos         the current position of the player
     * @param opponentCurrentPos the current position of the opponent
     * @param opponentScore      the opponent's score
     */
    private void createMySubtree(Node parent, int depth, int currentPos, int opponentCurrentPos, int opponentScore) {
        Board parentBoard = new Board(parent.getNodeBoard());
        // we need to restore the board after tree creation
        Board initialBoard = board;
        // make sure each evaluate() is called with the same score value the parent original had, and the score is
        // restored in the end
        int parentScore = score;
        for (int die = 1; die < 7; die++) {
            // get next current position for the specific move
            int newCurrentPos = simulateMove(currentPos, die, -1)[0];

            double evaluation = evaluate(currentPos, opponentCurrentPos, die, parentBoard, opponentScore, false);
            Node moveNode = new Node(parent, board, depth, evaluation, currentPos, die);
            parent.addChild(moveNode);
            createOpponentSubtree(moveNode, depth + 1, newCurrentPos, opponentCurrentPos, evaluation, opponentScore);
            score = parentScore;
        }
        board = initialBoard;
    }

    /**
     * Given a game state before the opponent's turn, it generates all children nodes representing
     * every possible move the opponent can make.
     *
     * @param parent             the node representing the game state before the opponent's turn
     * @param depth              the depth of the generated children
     * @param currentPos         the current position of the player
     * @param opponentCurrentPos the current position of the opponent
     * @param parentEval         the evaulation of the parent node, given by {@link #evaluate(int, int, int, Board, int, boolean)}
     * @param opponentScore      the opponent's score
     */
    private void createOpponentSubtree(Node parent, int depth, int currentPos, int opponentCurrentPos, double parentEval,
                                       int opponentScore) {
        Board parentBoard = new Board(parent.getNodeBoard());
        for (int die = 1; die < 7; die++) {
            double evaluation = evaluate(currentPos, opponentCurrentPos, die, parentBoard, opponentScore, true);
            Node moveNode = new Node(parent, board, depth, evaluation, currentPos, die);
            parent.addChild(moveNode);
        }
    }
}
