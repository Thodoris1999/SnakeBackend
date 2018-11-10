package main;

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

	public int getAppleId() {
		return appleId;
	}

	public void setAppleId(int appleId) {
		this.appleId = appleId;
	}

	public int getAppleTileId() {
		return appleTileId;
	}

	public void setAppleTileId(int appleTileId) {
		this.appleTileId = appleTileId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
