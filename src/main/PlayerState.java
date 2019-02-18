package main;

public class PlayerState {
    private int playerId;
    private Board board;
    private int position;
    private int points;

    public PlayerState(int playerId, Board board, int position, int points) {
        this.playerId = playerId;
        this.board = board;
        this.position = position;
        this.points = points;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Board getBoard() {
        return board;
    }

    public int getPoints() {
        return points;
    }

    public int getPosition() {
        return position;
    }
}
