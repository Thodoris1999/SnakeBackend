package main;

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
		HeuristicPlayer heuristicPlayer = new HeuristicPlayer(2, "heuristic player", 0, board);
		gamePlayers.add(normalPlayer);
		gamePlayers.add(heuristicPlayer);
		start = (LinkedHashMap<Integer, Integer>) setTurns(gamePlayers);
		
		// Game procedure round by round
		while (round < 23) {
			for (Map.Entry<Integer, Integer> entry : start.entrySet()) {
				if (entry.getKey() == normalPlayer.getPlayerId()) {
					// Rolling the dice for 1st player
					dice = (int)(Math.random() * 6) + 1;
					// Player is moving and his new position is kept to be used in the next round
					playerId1 = normalPlayer.move(playerId1, dice)[0];
				} else {
					//heuristic player goes first
					// Player is moving and his new position is kept to be used in the next round
					playerId2 = heuristicPlayer.getNextMove(playerId2, playerId1);
				}
			}
			round++;
			
			// Checking if 1st player won during this round
			if(playerId1 == board.getM() * board.getN()) {
				break;
			}
			
			// Checking if 2nd player won during this round
			if(playerId2 == board.getM() * board.getN()) {
				break;
			}
		}
		
		boolean player1win = false;
		boolean player2win = false;
		double player1eval = playerId1 * 0.8 + normalPlayer.getScore() * 0.2;
		double player2eval = playerId2 * 0.8 + heuristicPlayer.getScore() * 0.2;

		if(player1eval > player2eval)
			player1win = true;
		else if(player1eval < player2eval)
			player2win = true;
		else
			player1win = true;
		
		System.out.println();
		heuristicPlayer.statistics();
		
		// Printing details of winner
		System.out.println();
		if(player1win) {
			System.out.println("Rounds: " + round + " " + normalPlayer.getName() + "score: " + normalPlayer.getScore() + " " + heuristicPlayer.getName() + " score: " + heuristicPlayer.getScore());
			System.out.println("Winner: " + normalPlayer.getName());
		} else if(player2win) {
			System.out.println("Rounds: " + round + " " + normalPlayer.getName() + "score: " + normalPlayer.getScore() + " " + heuristicPlayer.getName() + " score: " + heuristicPlayer.getScore());
			System.out.println("Winner: " + heuristicPlayer.getName());
		}
	}
	
	/**
	 * Computes the order in which players play
	 * 
	 * @param players the players in the game
	 * @return a map which contains the id of the player and the die he got, ordered by who got the largest number
	 * (the process is repeated if two or more players roll the same number)
	 */
	public static Map<Integer, Integer> setTurns(ArrayList<Player> players){
		LinkedHashMap<Integer, Integer> returnMap = new LinkedHashMap<Integer, Integer>();
		if (players.size() == 1) {
			returnMap.put(players.get(0).getPlayerId(), (int)(Math.random() * 6) + 1);
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
			die = (int)(Math.random() * 6) + 1;
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
		System.out.println(returnMap);
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
			die = (int)(Math.random() * 6) + 1;
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
	
}
