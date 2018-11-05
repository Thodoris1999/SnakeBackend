package main;

public class Snake {
	private int snakeId;
	private int headId;
	private int tailId;
	
	public Snake(int snakeId, int headId, int tailId) {
		this.snakeId = snakeId;
		this.headId = headId;
		this.tailId = tailId;
	}
	
	public Snake(Snake snake) {
		this.snakeId = snake.getSnakeId();
		this.headId = snake.getHeadId();
		this.tailId = snake.getTailId();
	}

	public int getSnakeId() {
		return snakeId;
	}

	public void setSnakeId(int snakeId) {
		this.snakeId = snakeId;
	}

	public int getHeadId() {
		return headId;
	}

	public void setHeadId(int headId) {
		this.headId = headId;
	}

	public int getTailId() {
		return tailId;
	}

	public void setTailId(int tailId) {
		this.tailId = tailId;
	}
}
