package main;

/**
 * Contains information about the snakes on the game's board
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
public class Snake {
	public static int nextSnake = 0;
	private int snakeId;
	private int headId;
	private int tailId;
	
	public Snake() {}
	
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

	/**
	 * Returns the id of the snake
	 * 
	 * @return the id of the snake
	 */
	public int getSnakeId() {
		return snakeId;
	}

	/**
	 * Specify the id of the snake
	 * 
	 * @param snakeId the new id of the snake
	 */
	public void setSnakeId(int snakeId) {
		this.snakeId = snakeId;
	}

	/**
	 * Returns the id of tile the snake's head is on
	 * 
	 * @return the id of tile the snake's head is on
	 */
	public int getHeadId() {
		return headId;
	}

	/**
	 * Specify the id of the tile the snake's head is on
	 * 
	 * @param headId the id of the tile the snake's head is on
	 */
	public void setHeadId(int headId) {
		this.headId = headId;
	}

	/**
	 * Returns the id of tile the snake's tail is on
	 * 
	 * @return the id of tile the snake's tail is on
	 */
	public int getTailId() {
		return tailId;
	}

	/**
	 * Specify the id of the tile the snake's tail is on
	 * 
	 * @param tailId the id of the tile the snake's tail is on
	 */
	public void setTailId(int tailId) {
		this.tailId = tailId;
	}
}
