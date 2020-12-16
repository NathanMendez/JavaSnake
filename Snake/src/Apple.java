import java.awt.Color;
import java.awt.Graphics;

public class Apple {
	public int x;
	public int y;

	//This is the apple constructor, takes x and y coordinates and stores them for later use.
	public Apple(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	//Method for drawing the apple.
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, 25, 25);
	}
}
