package main;

import java.io.Serializable;

public class GameConfig implements Serializable {
    private int numRows, numCols, maxRounds, numSnakes, numLadders, numApples;
    private PlayerType[] playerTypes;

    public GameConfig() {
        numRows = 10;
        numCols = 10;
        maxRounds = 30;
        numSnakes = 3;
        numLadders = 3;
        numApples = 6;
        playerTypes = new PlayerType[2];
        playerTypes[0] = PlayerType.NORMAL;
        playerTypes[1] = PlayerType.NORMAL;
    }

    public GameConfig(int numRows, int numCols, int numPlayers, int maxRounds) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.maxRounds = maxRounds;
        playerTypes = new PlayerType[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            playerTypes[i] = PlayerType.NORMAL;
        }
    }

    public GameConfig(int numRows, int numCols, int maxRounds, PlayerType[] playerTypes) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.maxRounds = maxRounds;
        this.playerTypes = playerTypes;
    }

    public int getNumCols() {
        return numCols;
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

    public PlayerType[] getPlayerTypes() {
        return playerTypes;
    }

    public int getNumPlayers() {
        return playerTypes.length;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
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

    public void setPlayerTypes(PlayerType[] playerTypes) {
        this.playerTypes = playerTypes;
    }
}
