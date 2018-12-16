package main;

import java.util.Arrays;
import java.util.Random;

/**
 * Represents the board in a game of classic snake.
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
public class Board {
	private final int N, M;
	private int[][] tiles;
	private Snake[] snakes;
	private Apple[] apples;
	private Ladder[] ladders;

	public Board() {
		N = 10;
		M = 10;
		tiles = new int[M][N];
		snakes = new Snake[3];
		ladders = new Ladder[3];
		apples = new Apple[6];
	}

	public Board(int m, int n, int snakeCount, int ladderCount, int appleCount) {
		this.N = n;
		this.M = m;
		this.tiles = new int[m][n];
		this.snakes = new Snake[snakeCount];
		this.apples = new Apple[appleCount];
		this.ladders = new Ladder[ladderCount];
	}

	public Board(Board board) {
		this.N = board.getN();
		this.M = board.getM();
		this.tiles = new int[M][N];
		// copy tiles
		for (int i = 0; i < board.getTiles().length; i++)
			for (int j = 0; j < board.getTiles()[0].length; j++)
				this.tiles[i][j] = board.getTiles()[i][j];
		
		this.apples = Arrays.copyOf(board.getApples(), board.getApples().length);
		this.ladders = Arrays.copyOf(board.getLadders(), board.getLadders().length);
		this.snakes = Arrays.copyOf(board.getSnakes(), board.getSnakes().length);
	}

	/**
	 * Initializes the board with the tile's values, snakes, ladders and apples
	 */
	public void createBoard() {
		Random r = new Random();

		// fill in the tile's values
		for (int i = 0; i < M; i++) {
			if (i % 2 == 0) {
				for (int j = 0; j < N; j++) {
					tiles[i][j] = i * N + j + 1;
				}
			} else {
				for (int j = 0; j < N; j++) {
					tiles[i][N - 1 - j] = i * N + j + 1;
				}
			}
		}

		// randomly generate snakes
		for (int i = 0; i < snakes.length; i++) {
			int headId;
			// It is is impossible for two different snakes to have heads on the same tile,
			// so generate tile ids until we get one that doesn't already have a head on it
			// It is not allowed to have a head of a snake on the first or the last tile
			do {
				headId = r.nextInt(M * N - 2) + 2;
			} while (headIdExists(headId));

			int tailId = r.nextInt(headId - 1) + 1;
			snakes[i] = new Snake(Snake.nextSnake++, headId, tailId);
		}
		
		// randomly generate ladders
		for (int i = 0; i < ladders.length; i++) {
			int downstepId;
			Snake snake = null;
			// Same as snakes, it is not allowed to place a downstep where there already exists one.
			// Also, we have to avoid placing a downstep on a tile that already has a snake's head 
			do {
				downstepId = r.nextInt(M * N - 2) + 1;
			} while (downstepIdExists(downstepId) && headIdExists(downstepId));

			for (int j = 0; j < snakes.length; j++) {
				if (snakes[i] == null) break;
				if (snakes[j].getTailId() == downstepId) {
					// if there is a snake's tail on the downstep of a ladder, cache it
					snake = snakes[j];
					break;
				}
			}
			
			int upstepId;
			// Avoid placing the upstep of a ladder on the head of a snake, if its tail leads back to downstep, to avoid 
			// an infinite loop
			do {
				upstepId = r.nextInt(M * N - downstepId - 1) + downstepId + 1;
			} while (snake != null && snake.getHeadId() == upstepId);
			
			ladders[i] = new Ladder(Ladder.nextLadder++, upstepId, downstepId, false);
		}
		
		// randomly generate apples
		for (int i = 0; i < apples.length; i++) {
			int tile;
			// check if an apple already exists on the tile
			do {
				tile = r.nextInt(N * M - 1) + 1;
			} while (appleIdInvalid(tile));
			int points;
			do {
				points = r.nextInt(21) - 10;
			} while (points == 0);
			apples[i] = new Apple(Apple.nextAppleId++, tile, points > 0 ? "red" : "black", points);
		}
	}
	
	/**
	 * Checks if a snake's head exists on a tile
	 * 
	 * @param headId the id of the tile
	 * @return true if a snake's head exists on the tile, false otherwise
	 */
	private boolean headIdExists(int headId) {
		for (int i = 0; i < snakes.length; i++) {
			if (snakes[i] == null) break;
			if (snakes[i].getHeadId() == headId) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a ladder's downstep exists on a tile
	 * 
	 * @param downstepId the id of the tile
	 * @return true if a ladder's downstep exists on the tile, false otherwise
	 */
	private boolean downstepIdExists(int downstepId) {
		for (int i = 0; i < ladders.length; i++) {
			if (ladders[i] == null) break;
			if (ladders[i].getDownstepId() == downstepId) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if an apple exists on a tile
	 * 
	 * @param appleTile the id of the tile
	 * @return true if an apple exists on the tile, false otherwise
	 */
	private boolean appleIdInvalid(int appleTile) {
		for (int i = 0; i < snakes.length; i++) {
			if (snakes[i] == null) break;
			if (snakes[i].getHeadId() == appleTile) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Prints the state of the board and the apples, ladders, snakes on it
	 * Code:
	 * SH: Head of a snake
	 * ST: Tail of a snake
	 * LU: Highest step of a ladder
	 * LD: Lowest step of a ladder
	 * AR: Red apple
	 * AB: Black apple
	 */
	public void createElementBoard() {
		String[][] elementBoardSnakes = new String[M][N];
		for (int i = M - 1; i >= 0; i--) {
			for (int j = 0; j < N; j++) {
				String element = "___";
				for (Snake snake : snakes) {
					if (snake.getTailId() == tiles[i][j]) {
						element = "ST" + snake.getSnakeId();
						break;
					} else if (snake.getHeadId() == tiles[i][j]) {
						element = "SH" + snake.getSnakeId();
						break;
					}
				}
				elementBoardSnakes[i][j] = element;
				System.out.print(element + " ");
			}
			System.out.println();
		}
		System.out.println();
		
		String[][] elementBoardLadders = new String[M][N];
		for (int i = M - 1; i >= 0; i--) {
			for (int j = 0; j < N; j++) {
				String element = "___";
				for (Ladder ladder : ladders) {
					if (ladder.getDownstepId() == tiles[i][j]) {
						element = "LD" + ladder.getLadderId();
						break;
					} else if (ladder.getUpstepId() == tiles[i][j]) {
						element = "LU" + ladder.getLadderId();
						break;
					}
				}
				elementBoardLadders[i][j] = element;
				System.out.print(element + " ");
			}
			System.out.println();
		}
		System.out.println();
		
		String[][] elementBoardApples = new String[M][N];
		for (int i = M - 1; i >= 0; i--) {
			for (int j = 0; j < N; j++) {
				String element = "___";
				for (Apple apple : apples) {
					if (apple.getAppleTileId() == tiles[i][j]) {
						element = (apple.getColor() == "red" ? "AR" : "AB") + apple.getAppleId();
						break;
					}
				}
				elementBoardApples[i][j] = element;
				System.out.print(element + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Returns the values of the tiles on each element of the board
	 * 
	 * @return the values of the tiles on each element of the board
	 */
	public int[][] getTiles() {
		return tiles;
	}

	/**
	 * Specify the values of the tiles on each element of the board
	 * 
	 * @param tiles the values of the tiles on each element of the board
	 */
	public void setTiles(int[][] tiles) {
		this.tiles = tiles;
	}

	/**
	 * Returns an array of the snakes that exists on the board
	 * 
	 * @return an array of the snakes that exists on the board
	 */
	public Snake[] getSnakes() {
		return snakes;
	}

	/**
	 * Specify the array of the snakes that exists on the board
	 * 
	 * @param snakes the array of the snakes that exists on the board
	 */
	public void setSnakes(Snake[] snakes) {
		this.snakes = snakes;
	}

	/**
	 * Returns an array of the apples that exists on the board
	 * 
	 * @return an array of the apples that exists on the board
	 */
	public Apple[] getApples() {
		return apples;
	}

	/**
	 * Specify the array of the apples that exists on the board
	 * 
	 * @param apples the array of the apples that exists on the board
	 */
	public void setApples(Apple[] apples) {
		this.apples = apples;
	}

	/**
	 * Returns an array of the ladders that exists on the board
	 * 
	 * @return an array of the ladders that exists on the board
	 */
	public Ladder[] getLadders() {
		return ladders;
	}

	/**
	 * Specify the array of the ladders that exists on the board
	 * 
	 * @param ladders the array of the ladders that exists on the board
	 */
	public void setLadders(Ladder[] ladders) {
		this.ladders = ladders;
	}

	/**
	 * Returns the number of columns of the board
	 * 
	 * @return the number of columns of the board
	 */
	public int getN() {
		return N;
	}

	/**
	 * Returns the number of rows of the board
	 * 
	 * @return the number of rows of the board
	 */
	public int getM() {
		return M;
	}
}
