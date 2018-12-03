package main;

/**
 * Represents a player in the game of classic snake
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
public class Player {
	protected int playerId;
	protected String name;
	protected int score;
	protected Board board;

	public Player(int playerId, String name, int score, Board board) {
		this.playerId = playerId;
		this.name = name;
		this.score = score;
		this.board = board;
	}
	
	/**
	 * Returns the median value {@link #evaluate(int, int, int)} returns for all possible die values (1 to 6)
	 * @param currentPos current position of the player
	 * @param otherPlayerPos The position of another player, in order to take into account any possible apples
	 * he ate, or ladders he climbed
	 * @return
	 */
	public double medianMoveEvaluation(int currentPos, int otherPlayerPos) {
		double sum = 0;
		for (int i = 1; i < 7; i++) {
			sum += evaluateNormalPlayer(currentPos, i, otherPlayerPos);
		}
		return sum / 6;
	}
	
	/**
	 * A function that takes a move choice and evaluates it based on the position increase (higher return value)
	 * or decrease (lower return value) on the board and score increase (higher return value) or decrease (lower return value)
	 * The function is: 
	 * 				0.8 * (positionAfter - positionBefore) + 0.2 * (scoreAfter - scoreBefore)
	 * 
	 * @param currentPos current position of the player
	 * @param dice the number the player rolled
	 * @param otherPlayerPos The position of another player, in order to take into account any possible apples
	 * he ate, or ladders he climbed
	 * @return the evaluation of the move
	 */
	protected double evaluateNormalPlayer(int currentPos, int dice, int otherPlayerPos) {
		int[] moveData = simulateMove(currentPos, dice, otherPlayerPos);
        return 0.8 * moveData[0] + 0.2 * moveData[1];
	}

	/**
	 * Executes a player's move, according to the rules of the game. Initially moves the player to the next
	 * tile according to the die and the climbs any ladders or falls off any snakes that the player comes across,
	 * while collecting any apples in the process
	 *  
	 * @param id the id of the tile the player was previously on
	 * @param die the number the player rolled
	 * @return an array of the id of the tile the player landed, the number of snakes he got bitten by,
	 * the number of ladders he climbed, the number of red apples he ate and the number of black apples he ate
	 */
	public int[] move(int id, int die) {
		System.out.println(name + " rolled a " + die + "!");
		int[] arr = new int[5];
		int nextTile = ((id + die) > board.getM() * board.getN()) ? (board.getM() * board.getN()) : (id + die);
		System.out.println("Moved to tile " + nextTile);
		boolean somethingHappened = true;
		while (somethingHappened) {
			somethingHappened = false;
			for (Apple apple : board.getApples()) {
				if (apple.getAppleTileId() == nextTile && apple.getPoints() != 0) {
					if (apple.getColor().equals("red")) {
						System.out.println(name + " ate a red apple. Yummy! Earned " + apple.getPoints() + " points.");
						arr[3]++;
					} else {
						System.out.println(name + " ate a black apple. Yikes! Lost " + -apple.getPoints() + "  points.");
						arr[4]++;
					}
					score += apple.getPoints();
					apple.setPoints(0);
					break;
				}
			}
			for (Snake snake : board.getSnakes()) {
				if (snake.getHeadId() == nextTile) {
					nextTile = snake.getTailId();
					System.out.println(name + " got bitten by a snake. Ouch! Fell to tile " + nextTile);
					arr[1]++;
					somethingHappened = true;
					break;
				}
			}
			if (somethingHappened)
				continue;
			for (Ladder ladder : board.getLadders()) {
				if (ladder.getDownstepId() == nextTile && !ladder.isBroken()) {
					nextTile = ladder.getUpstepId();
					ladder.setBroken(true);
					System.out.println(name + " climbed a ladder! Reached tile " + nextTile);
					arr[2]++;
					somethingHappened = true;
					break;
				}
			}
		}
		arr[0] = nextTile;
		return arr;
	}
	
	/**
	 * Same as {@link #move(int, int, boolean)}, but it does not make any changes to the board, snake,
	 * ladder and apple objects, returns data about how the player's position and score changed
	 * and does not print anything
	 * 
	 * @param tileId the current position of the player
	 * @param die the number the player rolled
	 * @param otherPlayerPos The position of the other player. It is used to consider the case that the other player 
	 * has already been on the specified tile, in which case ladders and apples are ignored
	 * @return an integer array which contains the difference betweeen the initial and finial position and the 
	 * score gain/loss
	 */
	public int[] simulateMove(int tileId, int die, int otherPlayerPos) {
		int[] arr = new int[2];
		// initial score
		int initialScore = score;
		int nextTile = ((tileId + die) > board.getM() * board.getN()) ? (board.getM() * board.getN()) : (tileId + die);

		boolean somethingHappened = true;
		while (somethingHappened) {
			somethingHappened = false;
			for (Apple apple : board.getApples()) {
				if (otherPlayerPos == nextTile) break;
				if (apple.getAppleTileId() == nextTile && apple.getPoints() != 0) {
					score += apple.getPoints();
					break;
				}
			}
			for (Snake snake : board.getSnakes()) {
				if (snake.getHeadId() == nextTile) {
					nextTile = snake.getTailId();
					somethingHappened = true;
					break;
				}
			}
			if (somethingHappened)
				continue;
			for (Ladder ladder : board.getLadders()) {
				if (otherPlayerPos == nextTile) break;
				if (ladder.getDownstepId() == nextTile && !ladder.isBroken()) {
					nextTile = ladder.getUpstepId();
					somethingHappened = true;
					break;
				}
			}
		}
		arr[0] = nextTile - tileId;
		arr[1] = score - initialScore;
		// undo score changes
		score = initialScore;
		return arr;
	}

	/**
	 * Returns the id of the player
	 * 
	 * @return the id of the player
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * Specify the id of the player
	 * 
	 * @param playerId the id of the player
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	/**
	 * Returns the player's name
	 * 
	 * @return the player's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Specify the player's name
	 * 
	 * @param name the player's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the player's score
	 * 
	 * @return the player's score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Specify the player's score
	 * 
	 * @param score the player's score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Returns the board that the player plays on
	 * 
	 * @return the board that the player plays on
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Specify the board the player plays on
	 * 
	 * @param board the board that the player plays on
	 */
	public void setBoard(Board board) {
		this.board = board;
	}
}
