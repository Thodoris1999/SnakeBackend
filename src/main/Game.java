package main;

import java.util.Random;

/**
 * Game's starting point. This class creates the needed objects and implements a turn based gameplay.
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
public class Game {

	private static int round;
	
	/**
	 * Staring point of the game. This method initializes the board, the players, creates a loop in which
	 * each player makes a move, one after the other and declares a winner.
	 * 
	 * @param args unused command line arguments
	 */
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
	
}
