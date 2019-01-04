package main;

import java.util.*;

/**
 * Game's starting point. This class creates the needed objects and implements a turn based gameplay.
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
public class Game {

    private static int round;
    private ArrayList<Player> gamePlayers;
    private ArrayList<Integer> playerPositions;
    private Board board;
    private LinkedHashMap<Integer, Integer> turns;
    private int maxRounds;
    private int lastTurnPlayerId;
    private boolean gameOver = false;

    public Game(GameConfig gameConfig, Player.MoveUpdateListener moveUpdateListener) {
        gamePlayers = new ArrayList<>(gameConfig.getNumPlayers());
        playerPositions = new ArrayList<>(gameConfig.getNumPlayers());

        board = new Board(gameConfig.getNumRows(), gameConfig.getNumCols(), gameConfig.getNumSnakes(), gameConfig.getNumLadders(),
                gameConfig.getNumApples());
        board.createBoard();

        for (int i = 0; i < gameConfig.getNumPlayers(); i++) {
            Player player = new Player(Player.nextPlayer++, "Player" + (i + 1), 0, board);
            player.setMoveUpdateListener(moveUpdateListener);
            gamePlayers.add(player);
            playerPositions.add(0);
        }
        turns = (LinkedHashMap<Integer, Integer>) setTurns(gamePlayers);
        round = 0;
        this.maxRounds = gameConfig.getMaxRounds();
        lastTurnPlayerId = -1;
    }

    public void progressTurn() {
        int playerWhoPlaysThisTurnIndex = -1;
        boolean shouldProgressRound = false;
        if (lastTurnPlayerId == -1) {
            playerWhoPlaysThisTurnIndex = findPlayerIndexById(turns.keySet().iterator().next());
        } else {
            int i = 0;
            for (Iterator<Map.Entry<Integer, Integer>> iterator = turns.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<Integer, Integer> entry = iterator.next();
                if (entry.getKey() == lastTurnPlayerId) {
                    if (i == turns.size() - 1) {
                        playerWhoPlaysThisTurnIndex = findPlayerIndexById(turns.keySet().iterator().next());
                    } else {
                        if (i == turns.size() - 2)
                            shouldProgressRound = true;
                        playerWhoPlaysThisTurnIndex = findPlayerIndexById(iterator.next().getKey());
                    }
                    break;
                }
                i++;
            }
        }

        int die = (int) (Math.random() * 6) + 1;
        int[] moveResult = gamePlayers.get(playerWhoPlaysThisTurnIndex).frontendMove(playerPositions.get(playerWhoPlaysThisTurnIndex), die);
        playerPositions.set(playerWhoPlaysThisTurnIndex, moveResult[0]);

        if (shouldProgressRound) {
            for (int i = 0; i < gamePlayers.size(); i++) {
                if (playerPositions.get(i) == board.getM() * board.getN()) {
                    gameOver = true;
                    break;
                }
            }
            round++;
        }
        lastTurnPlayerId = gamePlayers.get(playerWhoPlaysThisTurnIndex).getPlayerId();
    }

    public int findPlayerIndexById(int id) {
        for (int i = 0; i < gamePlayers.size(); i++) {
            Player player = gamePlayers.get(i);
            if (player.getPlayerId() == id)
                return i;
        }
        return -1;
    }

    /**
     * Staring point of the game. This method initializes the board, the players, creates a loop in which
     * each player makes a move, one after the other and declares a winner.
     *
     * @param args unused command line arguments
     */
    public static void main(String[] args) {
        int dice; // Used as a dice
        int playerId1 = 0;
        int playerId2 = 0;
        ArrayList<Player> gamePlayers = new ArrayList<Player>();
        LinkedHashMap<Integer, Integer> start = new LinkedHashMap<Integer, Integer>();
        // Creation of the desired table
        Board board = new Board(20, 10, 3, 3, 6);
        board.createBoard();
        board.createElementBoard();
        System.out.println();
        // Creation of the two players
        Player normalPlayer = new Player(1, "normal player", 0, board);
        MinMaxPlayer minmaxPlayer = new MinMaxPlayer(2, "minmax player", 0, board);
        gamePlayers.add(normalPlayer);
        gamePlayers.add(minmaxPlayer);
        start = (LinkedHashMap<Integer, Integer>) setTurns(gamePlayers);

        // Game procedure round by round
        while (round < 23) {
            for (Map.Entry<Integer, Integer> entry : start.entrySet()) {
                if (entry.getKey() == normalPlayer.getPlayerId()) {
                    // Rolling the dice for 1st player
                    dice = (int) (Math.random() * 6) + 1;
                    // Player is moving and his new position is kept to be used in the next round
                    playerId1 = normalPlayer.move(playerId1, dice, true)[0];
                } else {
                    //heuristic player goes first
                    // Player is moving and his new position is kept to be used in the next round
                    playerId2 = minmaxPlayer.getNextMove(playerId2, playerId1, normalPlayer.getScore())[0];
                }
            }
            round++;

            // Checking if 1st player won during this round
            if (playerId1 == board.getM() * board.getN()) {
                break;
            }

            // Checking if 2nd player won during this round
            if (playerId2 == board.getM() * board.getN()) {
                break;
            }
        }

        boolean player1win = false;
        boolean player2win = false;
        double player1eval = playerId1 * 0.8 + normalPlayer.getScore() * 0.2;
        double player2eval = playerId2 * 0.8 + minmaxPlayer.getScore() * 0.2;

        if (player1eval > player2eval)
            player1win = true;
        else if (player1eval < player2eval)
            player2win = true;
        else
            player1win = true;

        System.out.println();
        //minmaxPlayer.statistics();

        // Printing details of winner
        System.out.println();
        if (player1win) {
            System.out.println("Rounds: " + round + " " + normalPlayer.getName() + "score: " + normalPlayer.getScore() + " " + minmaxPlayer.getName() + " score: " + minmaxPlayer.getScore());
            System.out.println("Winner: " + normalPlayer.getName());
        } else if (player2win) {
            System.out.println("Rounds: " + round + " " + normalPlayer.getName() + "score: " + normalPlayer.getScore() + " " + minmaxPlayer.getName() + " score: " + minmaxPlayer.getScore());
            System.out.println("Winner: " + minmaxPlayer.getName());
        }
    }

    /**
     * Computes the order in which players play
     *
     * @param players the players in the game
     * @return a map which contains the id of the player and the die he got, ordered by who got the largest number
     * (the process is repeated if two or more players roll the same number)
     */
    public static Map<Integer, Integer> setTurns(ArrayList<Player> players) {
        LinkedHashMap<Integer, Integer> returnMap = new LinkedHashMap<Integer, Integer>();
        if (players.size() == 1) {
            returnMap.put(players.get(0).getPlayerId(), (int) (Math.random() * 6) + 1);
        } else if (players.size() == 0) return returnMap;
        LinkedHashMap<Player, Integer> one = new LinkedHashMap<Player, Integer>();
        LinkedHashMap<Player, Integer> two = new LinkedHashMap<Player, Integer>();
        LinkedHashMap<Player, Integer> three = new LinkedHashMap<Player, Integer>();
        LinkedHashMap<Player, Integer> four = new LinkedHashMap<Player, Integer>();
        LinkedHashMap<Player, Integer> five = new LinkedHashMap<Player, Integer>();
        LinkedHashMap<Player, Integer> six = new LinkedHashMap<Player, Integer>();
        int die;

        // throw dice and separate into categories based on value gotten
        for (Player player : players) {
            die = (int) (Math.random() * 6) + 1;
            switch (die) {
                case 1:
                    one.put(player, die);
                    break;
                case 2:
                    two.put(player, die);
                    break;
                case 3:
                    three.put(player, die);
                    break;
                case 4:
                    four.put(player, die);
                    break;
                case 5:
                    five.put(player, die);
                    break;
                case 6:
                    six.put(player, die);
                    break;
                default:
                    throw new RuntimeException("Illegal die");
            }
        }

        // break the tie for all die values
        for (Map.Entry<Player, Integer> entry : breakTie(one).entrySet())
            returnMap.put(entry.getKey().getPlayerId(), entry.getValue());
        for (Map.Entry<Player, Integer> entry : breakTie(two).entrySet())
            returnMap.put(entry.getKey().getPlayerId(), entry.getValue());
        for (Map.Entry<Player, Integer> entry : breakTie(three).entrySet())
            returnMap.put(entry.getKey().getPlayerId(), entry.getValue());
        for (Map.Entry<Player, Integer> entry : breakTie(four).entrySet())
            returnMap.put(entry.getKey().getPlayerId(), entry.getValue());
        for (Map.Entry<Player, Integer> entry : breakTie(five).entrySet())
            returnMap.put(entry.getKey().getPlayerId(), entry.getValue());
        for (Map.Entry<Player, Integer> entry : breakTie(six).entrySet())
            returnMap.put(entry.getKey().getPlayerId(), entry.getValue());
        return returnMap;
    }

    /**
     * Tie breaker function in case of die ties in {@link #setTurns(ArrayList)}
     *
     * @param players a map of players and their initial die value that need to have their tie broken
     * @return a map of players and their initial die value ordered by repetitively rethrowing dice
     */
    public static LinkedHashMap<Player, Integer> breakTie(LinkedHashMap<Player, Integer> players) {
        LinkedHashMap<Player, Integer> returnMap = new LinkedHashMap<Player, Integer>(players.size());
        if (players.size() == 1) {
            returnMap.putAll(players);
            return returnMap;
        } else if (players.size() == 0) return returnMap;
        LinkedHashMap<Player, Integer> one = new LinkedHashMap<Player, Integer>();
        LinkedHashMap<Player, Integer> two = new LinkedHashMap<Player, Integer>();
        LinkedHashMap<Player, Integer> three = new LinkedHashMap<Player, Integer>();
        LinkedHashMap<Player, Integer> four = new LinkedHashMap<Player, Integer>();
        LinkedHashMap<Player, Integer> five = new LinkedHashMap<Player, Integer>();
        LinkedHashMap<Player, Integer> six = new LinkedHashMap<Player, Integer>();
        int die;

        // throw dice and separate into categories based on value gotten
        for (Map.Entry<Player, Integer> entry : players.entrySet()) {
            die = (int) (Math.random() * 6) + 1;
            switch (die) {
                case 1:
                    one.put(entry.getKey(), entry.getValue());
                    break;
                case 2:
                    two.put(entry.getKey(), entry.getValue());
                    break;
                case 3:
                    three.put(entry.getKey(), entry.getValue());
                    break;
                case 4:
                    four.put(entry.getKey(), entry.getValue());
                    break;
                case 5:
                    five.put(entry.getKey(), entry.getValue());
                    break;
                case 6:
                    six.put(entry.getKey(), entry.getValue());
                    break;
                default:
                    throw new RuntimeException("Illegal die");
            }
        }

        // break the tie for all die values
        for (Map.Entry<Player, Integer> entry : breakTie(one).entrySet())
            returnMap.put(entry.getKey(), entry.getValue());
        for (Map.Entry<Player, Integer> entry : breakTie(two).entrySet())
            returnMap.put(entry.getKey(), entry.getValue());
        for (Map.Entry<Player, Integer> entry : breakTie(three).entrySet())
            returnMap.put(entry.getKey(), entry.getValue());
        for (Map.Entry<Player, Integer> entry : breakTie(four).entrySet())
            returnMap.put(entry.getKey(), entry.getValue());
        for (Map.Entry<Player, Integer> entry : breakTie(five).entrySet())
            returnMap.put(entry.getKey(), entry.getValue());
        for (Map.Entry<Player, Integer> entry : breakTie(six).entrySet())
            returnMap.put(entry.getKey(), entry.getValue());
        return returnMap;
    }

    public Game(int round) {
        this.round = round;
    }

    /**
     * Returns the current round of the game
     *
     * @return the current round of the game
     */
    public int getRound() {
        return round;
    }

    /**
     * Specify the current round of the game
     *
     * @param round the current round of the game
     */
    public void setRound(int round) {
        this.round = round;
    }

    public Board getBoard() {
        return board;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public ArrayList<Player> getGamePlayers() {
        return gamePlayers;
    }

    public ArrayList<Integer> getPlayerPositions() {
        return playerPositions;
    }

    public LinkedHashMap<Integer, Integer> getTurns() {
        return turns;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
