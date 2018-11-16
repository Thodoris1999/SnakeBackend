package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HeuristicPlayer extends Player {

    private ArrayList<int[]> path;

    public HeuristicPlayer(int playerId, String name, int score, Board board) {
        super(playerId, name, score, board);
    }

    public HeuristicPlayer(int playerId, String name, int score, Board board, ArrayList<int[]> path) {
        super(playerId, name, score, board);
        this.path = path;
    }

    // TODO: consider enemy
    private double evaluate(int currentPos, int dice) {
        int oldScore = score;
        int[] move = move(currentPos, dice, true);
        int newScore = score;
        return 0.8 * (move[0] - currentPos) + 0.2 * (newScore - oldScore);
    }

    public int getNextMove(int currentPos) {
        Map<Integer, Double> moves = new HashMap<>();

        int highestRewardDie = 1;
        double evaluation = evaluate(currentPos, 1);
        moves.put(1, evaluation);

        for (int i = 2; i < 7; i++) {
            double reward = evaluate(currentPos, i);
            moves.put(i, reward);
            if (reward > highestRewardDie)
                highestRewardDie = i;
        }

        int[] move = move(currentPos, highestRewardDie, false);
        path.add(move);

        return move[0];
    }

    public void statistics() {
        for (int i = 0; i < path.size(); i++) {
            System.out.println("turn " + (i + 1) + ": Stepped on " + path.get(i)[1] + " snake heads, climbed " +
            path.get(i)[2] + " ladders, ate " + path.get(i)[3] + " red apples and " + path.get(i)[4] + " black apples");

        }
    }

    public ArrayList<int[]> getPath() {
        return path;
    }

    public void setPath(ArrayList<int[]> path) {
        this.path = path;
    }
}
