package main;

public class GameConfig {
    private int numRows, numCols, numPlayers, maxRounds, numSnakes, numLadders, numApples;

    public GameConfig() {
        numRows = 10;
        numCols = 10;
        numPlayers = 2;
        maxRounds = 30;
        numSnakes = 3;
        numLadders = 3;
        numApples = 6;
    }

    public GameConfig(int numRows, int numCols, int numPlayers, int maxRounds) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.numPlayers = numPlayers;
        this.maxRounds = maxRounds;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public int getNumApples() {
        return numApples;
    }

    public int getNumLadders() {
        return numLadders;
    }

    public int getNumSnakes() {
        return numSnakes;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public void setMaxRounds(int maxRounds) {
        this.maxRounds = maxRounds;
    }

    public void setNumLadders(int numLadders) {
        this.numLadders = numLadders;
    }

    public void setNumSnakes(int numSnakes) {
        this.numSnakes = numSnakes;
    }

    public void setNumApples(int numApples) {
        this.numApples = numApples;
    }
}
