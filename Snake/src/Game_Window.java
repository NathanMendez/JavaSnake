import javax.swing.JFrame;

public class Game_Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Creates a new instance of the game.
	Game p = new Game();
	
	//Sets the windows properties.
	public Game_Window() {
		this.setTitle("Java Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,600);
		this.add(p);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
	}

	//Starts the game in the main method.
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Game_Window g = new Game_Window();
	}

}
