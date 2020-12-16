import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener,KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//This timer indicates the game refresh rate
	Timer t = new Timer(90,this);
	
	//This array list contains all the parts that make up the snake, the index 0 is the snakes 
	//head.
	ArrayList<SnakePart> snake = new ArrayList<SnakePart>();
	
	//The apple the snake eats
	Apple apple = null;
	
	//The snakes head
	SnakePart head = null;
	
	//Booleans to control snake direction and game state.
	boolean left = false,right=true,up=false,down=false,isOver=false,start=false;
	
	//Score tracker
	int score = 0;
	
	//Possible apple spawn position
	int[] position = {50,100,150,200,250,300,350,400,450,500};
	
	//Font for the game text
	Font f = new Font("serif",Font.PLAIN,20);

	public Game() {
		
		//Sets the background color to black.
		this.setBackground(Color.BLACK);
		
		//KeyListener
		addKeyListener(this);
		
		//Sets game focus
		setFocusable(true);
		
		//Random initial apple placement,
		int x = position[ThreadLocalRandom.current().nextInt(0,10)];
	    int y = position[ThreadLocalRandom.current().nextInt(0,10)];
		apple = new Apple(x,y);
		
		//Adds the first starting part of the snake and assign it as the head,
		snake.add(new SnakePart(100,300));
		head = snake.get(0);
		
		//starts the refresh timer.
		t.start();

	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.WHITE);

		g.setFont(f);
		
		//Draws the score on the screen.
		g.drawString("Score: "+score+"", 10,30);
		
		//Draws the text that appears after a game over.
		if(isOver == true) {
			g.drawString("Game Over :(", 200,30);
			g.drawString("Press space to start over", 380,30);

		}
		//Draws the starting text to the screen.
		if(start == false) {
			g.drawString("Press any arrow key to begin.", 150,30);

		}
		//Decoration line
		g.fillRect(0, 42, 600, 5);

		//Draws every snake part.
		for(SnakePart part: snake) {
			part.paint(g);
		}
		
		//Draws the apple on the screen
		apple.paint(g);
		
		//Sync graphics with refresh rate.
		Toolkit.getDefaultToolkit().sync();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//Game controllers
		
		int c = e.getKeyCode();
		
		if(c == KeyEvent.VK_UP && down == false) {
			up = true;
			left = false;
			right = false;
			start = true; 
			
			moveVertical(-1);

			
		} else if(c == KeyEvent.VK_LEFT && right == false) {
			left = true;
			up = false;
			down = false;
			start = true;

			moveHorizontal(-1);

			
		}else if(c == KeyEvent.VK_RIGHT && left == false) {
			right = true;
			up = false;
			down = false;
			start = true;

			moveHorizontal(1);
			
		}else if(c == KeyEvent.VK_DOWN && up ==false) {
			down = true;
			left = false;
			right = false;
			start = true;

			moveVertical(1);
		}
		//If the player lost the game they can reset everything by pressing space
		else if(c == KeyEvent.VK_SPACE && isOver==true) {
			
			t.start();
			
			score = 0;
			
			this.snake.clear();
			
			this.snake.add(new SnakePart(100,300));
			
			head = snake.get(0);
			
			start = true;
			isOver = false;
			
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(t.isRunning()) {
			
			if(start == true) {
				//Snake Continuous movement 
				if(left == true) {
					
					moveHorizontal(-1);
					
				}else if(right == true) {
					
					moveHorizontal(1);
					
				}else if(up == true) {
					
					moveVertical(-1);
					
				}else if(down == true) {
					
					moveVertical(1);

				}
				//Screen looping.
				if(head.y < 50) {
					
					head.y=550;
					
				}else if(head.y > 550) {
					
					head.y = 50;
					
				}else if(head.x > 600) {
					
					head.x = 0;
					
				}else if(head.x < 0) {
					
					head.x = 600;
					
				}
				//Checks if the snake collides with itself.
				if(checkCollision() == true) {
					
					isOver = true;
					
					repaint();
					
					t.stop();
					
				}
			}
			//refreshes the screen.
			repaint();
			
		}
	}
	
	public void checkAppleColision() {
		//Checks to see if the snake has eaten the apple.
		if(head.x == apple.x && head.y == apple.y) {
			
			//Increases the game score.
			score++;
			
			//Adds a new snake part.
			snake.add(new SnakePart(head.x,head.y));
			
			//Places the new apple on a random location.
			
			int x = position[ThreadLocalRandom.current().nextInt(0,10)];
			int y = position[ThreadLocalRandom.current().nextInt(0,10)];
			//Checks to see if the apple is misplaced, if it is then its randomly placed on
			// another location until its not being obstructed.
			for (int i = 0; i < snake.size(); i++) {
				
				SnakePart part = snake.get(i);
				
				if(part.x == x && part.y == y) {
					i=0;
					
					x = position[ThreadLocalRandom.current().nextInt(0,10)];
					y = position[ThreadLocalRandom.current().nextInt(0,10)];
					
				}else {
					continue;
				}
			}apple.x = x;
			apple.y = y;
		}
	}
	
	public void moveParts(int index,int x,int y) {
		//Recursive method for moving the snake parts. Gives the snake moving visual effect.
		if(index == snake.size()) {
			return;
			
		}else {
			//The method saves the location of the snake part. The snake part is then moved to
			//a new location and the previous saved location is assigned to the next part. this
			//continues until all the parts are moved.
			SnakePart part = snake.get(index);
			
			int tempX = part.x;
			int tempY = part.y;
			
			part.y = y;
			part.x = x;
			
			moveParts(++index,tempX,tempY);
		}
	}
	
	//Moves the snake left or right using the recursive method, it also checks 
	//to see if the snake ate the apple.
	public void moveHorizontal(int direction) {
		int x = head.x;
		int y = head.y;
		
		head.x +=head.vel * direction;
		
		checkAppleColision();
		
		moveParts(1,x,y);
		
	}
	//Moves the snake up or down using the recursive method, it also checks 
	//to see if the snake ate the apple.
	public void moveVertical(int direction) {
		int x = head.x;
		int y = head.y;
		
		head.y +=head.vel * direction;
		
		checkAppleColision();
		
		moveParts(1,x,y);
		
	}
	
	//Checks to see if the head has collided with any part of the body.
	public boolean checkCollision() {
		for (int i = 5; i <snake.size(); i++) {
			SnakePart part = snake.get(i);
			if(part.x == head.x && part.y == head.y) {
				return true;
			}
		}return false;
	}

}
