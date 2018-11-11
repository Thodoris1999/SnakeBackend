package main;

/**
 * Contains information about the ladders on the game's board
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
public class Ladder {
	public static int nextLadder = 0;
	private int ladderId;
	private int upstepId;
	private int downstepId;
	private boolean broken;
	
	public Ladder() {}
	
	public Ladder(int ladderId, int upstepId, int downstepId, boolean broken) {
		this.ladderId = ladderId;
		this.upstepId = upstepId;
		this.downstepId = downstepId;
		this.broken = broken;
	}
	
	public Ladder(Ladder ladder) {
		this.ladderId = ladder.getLadderId();
		this.upstepId = ladder.getUpstepId();
		this.downstepId = ladder.getDownstepId();
		this.broken = ladder.isBroken();
	}
	
	/**
	 * Returns the id of the ladder
	 * 
	 * @return the id of the snake
	 */
	public int getLadderId() {
		return ladderId;
	}
	
	/**
	 * Specify the id of the ladder
	 * 
	 * @param ladderId the new id of the ladder
	 */
	public void setLadderId(int ladderId) {
		this.ladderId = ladderId;
	}
	
	/**
	 * Returns the id of the tile the ladder's highest step is on
	 * 
	 * @return the id of the tile the ladder's highest step is on
	 */
	public int getUpstepId() {
		return upstepId;
	}
	
	/**
	 * Specify the id of the tile the ladder's highest step is on
	 * 
	 * @param upstepId the id of the tile the ladder's highest step is on
	 */
	public void setUpstepId(int upstepId) {
		this.upstepId = upstepId;
	}
	
	/**
	 * Returns the id of the tile the ladder's lowest step is on
	 * 
	 * @return the id of the tile the ladder's lowest step is on
	 */
	public int getDownstepId() {
		return downstepId;
	}
	
	/**
	 * Specify the id of the tile the ladder's lowest step is on
	 * 
	 * @param the id of the tile the ladder's lowest step is on
	 */
	public void setDownstepId(int downstepId) {
		this.downstepId = downstepId;
	}
	
	/**
	 * Returns whether the ladder is broken or not (ladders get destroyed when a user has already uses them)
	 * 
	 * @return whether the ladder is broken or not
	 */
	public boolean isBroken() {
		return broken;
	}
	
	/**
	 * Specify whether the ladder is broken or not (ladders get destroyed when a user has already uses them)
	 * @param broken whether the ladder is broken or not
	 */
	public void setBroken(boolean broken) {
		this.broken = broken;
	}
}
