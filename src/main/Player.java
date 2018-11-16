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
	 * Executes a player's move, according to the rules of the game. Initially moves the player to the next
	 * tile according to the die and the climbs any ladders or falls off any snakes that the player comes across,
	 * while collecting any apples in the process
	 *  
	 * @param id the id of the tile the player was previously on
	 * @param die the number the player rolled
	 * @return an array of the id of the tile the player landed, the number of snakes he got bitten by,
	 * the number of ladders he climbed, the number of red apples he ate and the number of black apples he ate
	 */
	public int[] move(int id, int die, boolean test) {
		System.out.println(name + " rolled a " + die + "!");
		int[] arr = new int[5];
		int nextTile = id + die;
		System.out.println("Moved to tile " + nextTile);
		boolean somethingHappened = true;
		while (somethingHappened) {
			somethingHappened = false;
			for (Apple apple : board.getApples()) {
				if (apple.getAppleTileId() == nextTile && apple.getPoints() != 0) {
					if (apple.getColor().equals("red")) {
						if (!test) System.out.println(name + " ate a red apple. Yummy! Earned " + apple.getPoints() + " points.");
						arr[3]++;
					} else {
						if (!test) System.out.println(name + " ate a black apple. Yikes! Lost " + -apple.getPoints() + "  points.");
						arr[4]++;
					}
					score += apple.getPoints();
					if (!test) apple.setPoints(0);
					break;
				}
			}
			for (Snake snake : board.getSnakes()) {
				if (snake.getHeadId() == nextTile) {
					nextTile = snake.getTailId();
					if (!test) System.out.println(name + " got bitten by a snake. Ouch! Fell to tile " + nextTile);
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
					if (!test) ladder.setBroken(true);
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
