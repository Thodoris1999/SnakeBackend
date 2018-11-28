package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents player in snake that moves accordingly to the best possible move,
 * maximizing the value returned by {@link #evaluate(int, int)}
 * Also supports printing some basic statistics about the turn history of the player.
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
public class HeuristicPlayer extends Player {

    private ArrayList<int[]> path;

    public HeuristicPlayer(int playerId, String name, int score, Board board) {
        super(playerId, name, score, board);
    }

    public HeuristicPlayer(int playerId, String name, int score, Board board, ArrayList<int[]> path) {
        super(playerId, name, score, board);
        this.path = path;
    }

    /**
	 * A function that takes a move choice and evaluates it based on the position increase (higher return value)
	 * or decrease (lower return value) on the board, score increase (higher return value) or decrease (lower return value)
	 * and how the median evaluation of the other player changed
	 * The function is: 
	 * 				0.8 * (positionAfter - positionBefore) + 0.1 * (scoreAfter - scoreBefore)
	 *  				+ 0.1 * (medianEnemyEvaluationAfter - medianEnemyEvaluationBefore)
	 * 
	 * @param currentPos current position of the player
	 * @param dice the number the player rolled
	 * @param otherPlayerPos The position of another player, in order to take into account any possible apples
	 * he ate, or ladders he climbed
	 * @return the evaluation of the move
	 */
    @Override
    protected double evaluate(int currentPos, int dice, int otherPlayerPos) {
    	double enemyMedianMoveEvaluationBefore = medianMoveEvaluation(otherPlayerPos, -1);
        int[] moveData = simulateMove(currentPos, dice, -1);
        double enemyMedianMoveEvaluationAfter = medianMoveEvaluation(otherPlayerPos, currentPos + moveData[0]);
        return 0.8 * moveData[0] + 0.1 * moveData[1] + 0.1 * (enemyMedianMoveEvaluationAfter - enemyMedianMoveEvaluationBefore);
    }

    /**
     * Calculates the best possible move in the player's current position, maximizing the value returned by
     * {@link #evaluate(int, int)}, then executes that move and finally, it updates the {@link #path} ArrayList
     * according to the selected move.
     * 
     * @param currentPos The position of the heuristic player in the board
     * @return The position of the heuristic player after their turn is complete
     */
    public int getNextMove(int currentPos, int otherPlayerPos) {
        Map<Integer, Double> moves = new HashMap<>();

        int highestRewardDie = 1;
        double evaluation = evaluate(currentPos, 1, otherPlayerPos);
        moves.put(1, evaluation);

        for (int i = 2; i < 7; i++) {
            double reward = evaluate(currentPos, i, otherPlayerPos);
            moves.put(i, reward);
            if (reward > moves.get(highestRewardDie))
                highestRewardDie = i;
        }

        int[] move = move(currentPos, highestRewardDie, false);
        path.add(move);

        return move[0];
    }

    /**
     * Prints the:
     * <ul>
     *   <li>the die that was selected</li>
     *   <li>number of ladders climbed</li>
     *   <li>number snake heads that were stepped on</li>
     *   <li>number of red apples eaten</li>
     *   <li>number of black apples eaten</li>
     * </ul>
	 * for each turn the heuristic player has played
     */
    public void statistics() {
        for (int i = 0; i < path.size(); i++) {
        	int die;
        	if (i == 0)
        		die = path.get(i)[0];
        	else
        		die = path.get(i)[0] - path.get(i -1)[0];
        	
            System.out.println("turn " + (i + 1) + " (selected die=)" + die + ": Stepped on " + path.get(i)[1] +
            		" snake heads, climbed " + path.get(i)[2] + " ladders, ate " + path.get(i)[3] +
            		" red apples and " + path.get(i)[4] + " black apples");
            
        }
    }

    /**
     * Returns an ArrayList of information on each turn the player has played
     * 
     * @return an ArrayList of information on each turn the player has played
     */
    public ArrayList<int[]> getPath() {
        return path;
    }

    /**
     * Specify an ArrayList of information on each turn the player has played
     * 
     * @param path an ArrayList of information on each turn the player has played
     */
    public void setPath(ArrayList<int[]> path) {
        this.path = path;
    }
}
