
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

public class HungerGames extends JFrame{

	/**
	 * Set up main UI
	 */
	public void init() {
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = getContentPane();
		pane.setLayout(new GridBagLayout());
		
		
		
		
	}
	
	public static void main(String[] args) {
		HungerGames app = new HungerGames();
		app.init();
		app.setPreferredSize(new Dimension(800, 600));
		app.setLocation(400, 250);
		app.pack();
		app.setVisible(true);

	}

}
