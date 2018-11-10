package main;

/**
 * Contains information about the apples on the game's board
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
public class Apple {
	
	public static int nextAppleId = 0;
	private int appleId;
	private int appleTileId;
	private String color;
	private int points;
	
	public Apple(int appleId, int appleTileId, String color, int points) {
		this.appleId = appleId;
		this.appleTileId = appleTileId;
		this.color = color;
		this.points = points;
	}
	
	public Apple(Apple apple) {
		this.appleId = apple.getAppleId();
		this.appleTileId = apple.getAppleTileId();
		this.color = apple.getColor();
		this.points = apple.getPoints();
	}

	/**
	 * Returns the id of the apple
	 * 
	 * @return the id of the apple
	 */
	public int getAppleId() {
		return appleId;
	}

	/**
	 * Specify the id of the apple
	 * 
	 * @param appleId the new id of the apple
	 */
	public void setAppleId(int appleId) {
		this.appleId = appleId;
	}

	/**
	 * Returns the id of the tile the apple is on
	 * 
	 * @return the id of the tile the apple is on
	 */
	public int getAppleTileId() {
		return appleTileId;
	}

	/**
	 * Specify the id of the tile the apple is on
	 * 
	 * @param appleTileId the id of the tile the apple is on
	 */
	public void setAppleTileId(int appleTileId) {
		this.appleTileId = appleTileId;
	}

	/**
	 * Returns the color of the apple. Apple that increase the score are red and apples that decrease the score are black
	 * 
	 * @return "red" if {@link Apple#points} is positive and "black" if {@link Apple#points} is negative
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Specify the color of the apple
	 * 
	 * @param color the color of the apple
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Returns the points the apple adds/subtracts if someone eats it
	 * 
	 * @return the points the apple adds/subtracts if someone eats it
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Specify the points the apple adds/subtracts if someone eats it
	 * 
	 * @param points the points the apple adds/subtracts if someone eats it
	 */
	public void setPoints(int points) {
		this.points = points;
	}
}
