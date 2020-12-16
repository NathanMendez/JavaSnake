import java.awt.Color;
import java.awt.Graphics;

public class SnakePart {
	public int x;
	public int y;
	//Snake movement velocity.
	public int vel = 25;
	
	//This is constructor for the snake parts, takes x and y coordinates and stores 
	//them for later use.

	public SnakePart(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	//This method is used for drawing the snake.
	public void paint(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, 25, 25);
	}
}
