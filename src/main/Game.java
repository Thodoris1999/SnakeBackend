package main;

import java.util.Random;
import java.util.*;

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
		Game game = new Game(0);
		int dice; // Used as a dice
		boolean orderSet = true;
		int playerId1 = 1;
		int playerId2 = 1;
		ArrayList<int[]> playerDetail = new ArrayList<int[]>();
		ArrayList<Player> gamePlayers = new ArrayList<Player>();
		HashMap<Integer, Integer> start = new HashMap<Integer, Integer>();
		// Creation of the desired table
		Board board = new Board(20, 10, 3, 3, 6);
		board.createBoard();
		board.createElementBoard();
		System.out.println();
		// Creation of the two players
		Player Player1 = new Player(1, "Player1", 0, board); // Player to play first
		HeuristicPlayer Player2 = new HeuristicPlayer(2, "Player2", 0, board, playerDetail); // Player to play second
		gamePlayers.add(Player1);
		gamePlayers.add(Player2);
		start = setTurns(gamePlayers);
		
		for(HashMap.Entry<Integer, Integer> entry : start.entrySet()) {
			for(int i = 1; i < 7; i++) {
				if(entry.getValue().equals(i)) {
					if(Player2.playerId == entry.getKey()){
						orderSet = false;
					}
					
					break;
				}
			}
			
			break;
		}

		// Game procedure round by round
		// Intentionally creation of infinite loop (it will terminate due to the break statements that exist)
		for(int i=0; game.getRound() < 23; i++) {
			// Rolling the dice for 1st player
			if(orderSet) {
				game.setRound(game.getRound() + 1);
				dice = (int)(Math.random() * 6) + 1;
				// Player is moving and his new position is kept to be used in the next round
				playerId1 = Player1.move(playerId1, dice)[0];														// LOOK HERE! <I----------------------------------------------------------------------<
			}
			
			// Checking if 1st player won during this round
			if(playerId1 >= 200) {
				playerId1 = 200;
				break;
			}
			
			// Player is moving and his new position is kept to be used in the next round
			playerId2 = Player2.getNextMove(playerId2, playerId1);
			
			// Checking if 2nd player won during this round
			if(playerId2 >= 200) {
				playerId2 = 200;
				break;
			}
			
			// This allows Player1 to play in the next round
			if(orderSet == false)
				orderSet = true;
		}
		
		boolean Player1win = false;
		boolean Player2win = false;
		double player1eval = 0;
		double player2eval = 0;
		
		if(playerId1 > playerId2) {
			if(Player1.getScore() > Player2.getScore())
				Player1win = true;
			if(Player1.getScore() < Player2.getScore()) {
				player1eval = playerId1 * 0.63 + Player1.getScore() * 0.37;
				player2eval = playerId2 * 0.63 + Player2.getScore() * 0.37;
				if(player1eval > player2eval)
					Player1win = true;
				if(player1eval < player2eval)
					Player2win = true;
				if(player1eval == player2eval)
					Player1win = true;
			}
			
			if(Player1.getScore() == Player2.getScore())
				Player1win = true;
		}
		
		if(playerId1 < playerId2) {
			if(Player1.getScore() < Player2.getScore())
				Player2win = true;
			if(Player1.getScore() > Player2.getScore()) {
				player1eval = playerId1 * 0.63 + Player1.getScore() * 0.37;
				player2eval = playerId2 * 0.63 + Player2.getScore() * 0.37;
				if(player1eval > player2eval)
					Player1win = true;
				if(player1eval < player2eval)
					Player2win = true;
				if(player1eval == player2eval)
					Player2win = true;
			}
			
			if(Player1.getScore() == Player2.getScore())
				Player2win = true;
		}
		
		if(playerId1 == playerId2) {
			if(Player1.getScore() >= Player2.getScore())
				Player1win = true;
			if(Player1.getScore() < Player2.getScore())
				Player2win = true;
		}
		
		System.out.println();
		Player2.statistics();
		
		// Printing details of winner
		System.out.println();
		
		if(Player1win) {
			System.out.println("Rounds: " + game.getRound() + " " + Player1.getName() + "score: " + Player1.getScore() + " " + Player2.getName() + " score: " + Player2.getScore());
			System.out.println("Winner: " + Player1.getName());
		}
		if(Player2win) {
			System.out.println("Rounds: " + game.getRound() + " " + Player1.getName() + "score: " + Player1.getScore() + " " + Player2.getName() + " score: " + Player2.getScore());
			System.out.println("Winner: " + Player2.getName());
		}
	}
	
	public static HashMap<Integer, Integer> setTurns(ArrayList<Player> players){
		HashMap<Integer, Integer> toBegin = new HashMap<Integer, Integer>();
		boolean alExist = false;
		int dice; // Used as a dice
		int[] playersId = new int[6];
		int[] diceId = new int[6];
		
		for(int i = 0; i < players.size(); i++) {
			alExist = false;
			dice = (int)(Math.random() * 6) + 1;
			for(int j = 0; j < i; j++) {
				if(dice == diceId[j]) {
					alExist = true;
					break;
				}
			}
			if(alExist) {
				i--;
				break;
			}
			playersId[i] = players.get(i).getPlayerId();
			diceId[i] = dice;
		}
		
		int temp, temp2;
		
		for(int i = 1; i < players.size(); ++i){
		
			for(int j = 0; j < (players.size() - 1); ++j){
				if(diceId[j] > diceId[j+1]){
					temp = diceId[j];
					diceId[j] = diceId[j+1];
					diceId[j+1] = temp;

					temp2 = playersId[j];
					playersId[j] = playersId[j+1];
					playersId[j+1] = temp2;
				}
			}
		}
		
		for(int i = (players.size() - 1); i > -1; i--) {
			toBegin.put(playersId[i], diceId[i]);
		}
		
		return toBegin;
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
