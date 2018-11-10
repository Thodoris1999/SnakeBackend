package main;

import java.util.Random;

public class Game {

	private static int round;
	
	public static void main(String[] args) {
		int rows = 20, columns = 10;
		Board board = new Board(rows, columns, 3, 3, 6);
		board.createBoard();
		board.createElementBoard();
		Player thodoris = new Player(1, "Thodoris", 0, board);
		Player stelios = new Player(2, "Stelios", 0, board);
		
		int tilePlayer1 = 0, tilePlayer2 = 0;
		Random r = new Random();
		do {
			int[] turn1 = thodoris.move(tilePlayer1, r.nextInt(6) + 1);
			tilePlayer1 = turn1[0];
			int[] turn2 = stelios.move(tilePlayer2, r.nextInt(6) + 1);
			tilePlayer2 = turn2[0];
			round++;
		} while (tilePlayer1 < rows * columns && tilePlayer2 < rows * columns);
		System.out.println("round:"  + round);	
		System.out.println("the points of Thodoris:" + thodoris.getScore());
		System.out.println("the points of Stelios:" + stelios.getScore());
		if (tilePlayer1 >= rows * columns) {
			if (tilePlayer2 >= rows * columns) {
				if (thodoris.getScore() > stelios.getScore()) {
					System.out.println("Thodoris is the winner");
				} else {
					System.out.println("Stelios is the winner");
				}
			} else {
				System.out.println("Thodoris is the winner");
			}
		} else
			System.out.println("Stelios is the winner");
	}

	public Game(int round) {
		this.round = round;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}
	
}
