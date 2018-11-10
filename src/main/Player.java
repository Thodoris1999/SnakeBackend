package main;

public class Player {
	private int playerId;
	private String name;
	private int score;
	private Board board;
	
	public Player(int playerId, String name, int score, Board board) {
		this.playerId = playerId;
		this.name = name;
		this.score = score;
		this.board = board;
	}
	
	public int[] move(int id, int die) {
		int[] arr = new int[5];
		int nextTile = id + die;
		boolean somethingHappened = true;
		while (somethingHappened) {
			somethingHappened = false;
			for (Apple apple : board.getApples()) {
				if (apple.getAppleTileId() == nextTile && apple.getPoints() != 0) {
					if (apple.getColor().equals("red")) {
						System.out.println("Ate a red apple. Yummy!");
						arr[3]++;
					} else {
						System.out.println("Ate a black apple. Yikes!");
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
					System.out.println("Got bitten by a snake. Ouch!");
					arr[1]++;
					somethingHappened = true;
					break;
				}
			}
			if (somethingHappened) continue;
			for (Ladder ladder : board.getLadders()) {
				if (ladder.getDownstepId() == nextTile && !ladder.isBroken()) {
					nextTile = ladder.getUpstepId();
					ladder.setBroken(true);
					System.out.println("Climbed a ladder!");
					arr[2]++;
					somethingHappened = true;
					break;
				}
			}
		}
		arr[0] = nextTile;
		return arr;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
}
