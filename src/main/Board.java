package main;

import java.util.Random;

public class Board {
	private final int N, M;
	private int[][] tiles;
	private Snake[] snakes;
	private Apple[] apples;
	private Ladder[] ladders;

	// TODO: ask about empty constructor

	public Board(int n, int m, int snakeCount, int ladderCount, int appleCount) {
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
		this.tiles = board.getTiles();
		this.apples = board.getApples();
		this.ladders = board.getLadders();
	}

	public void createBoard() {
		Random r = new Random();

		for (int i = 0; i < M; i++) {
			if (i % 2 == 0) {
				for (int j = 0; j < N; j++) {
					tiles[i][j] = i * N + j + 1;
				}
			} else {
				for (int j = 0; j < M; j++) {
					tiles[i][j] = i * N + M - j + 1;
				}
			}
		}

		for (int i = 0; i < snakes.length; i++) {
			int headId;
			do {
				headId = r.nextInt(M * N - 2) + 2;
			} while (headIdExists(headId));

			int tailId = r.nextInt(headId - 1) + 1;
			snakes[i] = new Snake(Snake.nextSnake++, headId, tailId);
		}
		
		// generate ladders
		for (int i = 0; i < ladders.length; i++) {
			int downstepId;
			Snake snake = null;
			do {
				downstepId = r.nextInt(M * N - 1) + 1;
			} while (downstepIdExists(downstepId) && snakeHeadDownStep(downstepId));

			for (int j = 0; j < snakes.length; j++) {
				if (snakes[i] == null) break;
				if (snakes[j].getTailId() == downstepId) {
					snake = snakes[j];
					break;
				}
			}
			
			int upstepId;
			do {
				upstepId = r.nextInt(M * N - downstepId - 1) + downstepId + 1;
			} while (snake != null && snake.getHeadId() != upstepId);
			
			ladders[i] = new Ladder(Ladder.nextLadder++, downstepId, upstepId, false);
		}
		
		// generate apples
		for (int i = 0; i < apples.length; i++) {
			int tile;
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
	
	private boolean headIdExists(int headId) {
		for (int i = 0; i < snakes.length; i++) {
			if (snakes[i] == null) break;
			if (snakes[i].getHeadId() == headId) {
				return true;
			}
		}
		return false;
	}
	
	private boolean downstepIdExists(int downstepId) {
		for (int i = 0; i < ladders.length; i++) {
			if (ladders[i] == null) break;
			if (ladders[i].getDownstepId() == downstepId) {
				return true;
			}
		}
		return false;
	}
	
	private boolean snakeHeadDownStep(int tileId) {
		for (int i = 0; i < snakes.length; i++) {
			if (snakes[i] == null) break;
			if (snakes[i].getSnakeId() == tileId)
				return true;
		}
		return false;
	}
	
	private boolean appleIdInvalid(int appleTile) {
		for (int i = 0; i < snakes.length; i++) {
			if (snakes[i] == null) break;
			if (snakes[i].getHeadId() == appleTile) {
				return true;
			}
		}
		return false;
	}
	
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
				System.out.print(element + " ");
			}
			System.out.println();
		}
		
		String[][] elementBoardLadders = new String[M][N];
		for (int i = M - 1; i >= 0; i--) {
			for (int j = 0; j < N; j++) {
				String element = "___";
				for (Ladder ladder : ladders) {
					if (ladder.getDownstepId() == tiles[i][j]) {
						element = "LD" + ladder.getDownstepId();
						break;
					} else if (ladder.getUpstepId() == tiles[i][j]) {
						element = "LU" + ladder.getUpstepId();
						break;
					}
				}
				System.out.print(element + " ");
			}
			System.out.println();
		}
		
		String[][] elementBoardApples = new String[M][N];
		for (int i = M - 1; i >= 0; i--) {
			for (int j = 0; j < N; j++) {
				String element = "___";
				for (Apple apple : apples) {
					if (apple.getAppleTileId() == tiles[i][j]) {
						element = apple.getPoints() > 0 ? "AR" : "AB" + apple.getAppleId();
						break;
					}
				}
				System.out.print(element + " ");
			}
			System.out.println();
		}
	}

	public int[][] getTiles() {
		return tiles;
	}

	public void setTiles(int[][] tiles) {
		this.tiles = tiles;
	}

	public Snake[] getSnakes() {
		return snakes;
	}

	public void setSnakes(Snake[] snakes) {
		this.snakes = snakes;
	}

	public Apple[] getApples() {
		return apples;
	}

	public void setApples(Apple[] apples) {
		this.apples = apples;
	}

	public Ladder[] getLadders() {
		return ladders;
	}

	public void setLadders(Ladder[] ladders) {
		this.ladders = ladders;
	}

	public int getN() {
		return N;
	}

	public int getM() {
		return M;
	}
}
