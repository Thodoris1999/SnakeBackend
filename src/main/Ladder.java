package main;

public class Ladder {
	private int ladderId;
	private int upstepId;
	private int downstepId;
	private boolean broken;
	
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
	
	public int getLadderId() {
		return ladderId;
	}
	public void setLadderId(int ladderId) {
		this.ladderId = ladderId;
	}
	public int getUpstepId() {
		return upstepId;
	}
	public void setUpstepId(int upstepId) {
		this.upstepId = upstepId;
	}
	public int getDownstepId() {
		return downstepId;
	}
	public void setDownstepId(int downstepId) {
		this.downstepId = downstepId;
	}
	public boolean isBroken() {
		return broken;
	}
	public void setBroken(boolean broken) {
		this.broken = broken;
	}
}
