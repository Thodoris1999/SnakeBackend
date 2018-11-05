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
		this.tiles = new int[n][m];
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

		for (int i = 0; i < N; i++) {
			if (i % 2 == 0) {
				for (int j = 0; j < M; j++) {
					tiles[i][j] = i * N + j + 1;
				}
			} else {
				for (int j = 0; j < M; j++) {
					tiles[i][j] = i * N + M - j + 1;
				}
			}
		}

		for (int i = 0; i < apples.length; i++) {
			int tile = r.nextInt(N * M - 1) + 1;
			int points;
			do {
				points = r.nextInt(21) - 10;
			} while (points != 0);
			apples[i] = new Apple(Apple.nextAppleId++, tile, points > 0 ? "red" : "black", points);
		}

		for (int i = 0; i < snakes.length; i++) {
			int headId;
			do {
				headId = r.nextInt(M * N - 2) + 2;
			} while (headIdExists(headId));

			int tailId = r.nextInt(headId - 1) + 1;
			snakes[i] = new Snake(Snake.nextSnake++, headId, tailId);
		}
		
		for (int i = 0; i < ladders.length; i++) {
			int downstepId;
			do {
				downstepId = r.nextInt(M * N - 1) + 1;
			} while (downstepIdExists(downstepId));

			int upstepId = r.nextInt(M * N - downstepId - 2) + downstepId + 1;
			ladders[i] = new Ladder(Ladder.nextLadder++, downstepId, upstepId, false);
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
