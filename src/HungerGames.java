

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;


public class HungerGames extends JFrame implements ActionListener{
	private JButton submit;
	static GameData data;
	JLabel[] districts;
	JTextField[] maleTributes;
	JTextField[] femaleTributes;
	
	public HungerGames(String title){
		super(title);
	}
	/**
	 * Set up main UI
	 */
	public void init() {
		setSize(800, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = getContentPane();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//top message
		JLabel topMessage = new JLabel();
		topMessage.setText("<html><center>Welcome to the Hunger Games!<br><br>"
				+ "This game will simulate a Hunger Games Event.<br>"
				+ "Enter in 12 male and 12 female names into the boxes below that will<br>"
				+ "paticipate in the Hunger Games. When ready, hit the button at the bottom.<br>"
				+ "This game is not suited for people under 13 years of age.</center></html>");
		topMessage.setHorizontalAlignment(JLabel.CENTER);
		topMessage.setVerticalAlignment(JLabel.TOP);
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 20;
		c.ipady = 20;
		c.gridwidth = 4;
		
		pane.add(topMessage, c);
		
		//male header
		JLabel male = new JLabel("Male");
		c.gridx = 1;
		c.gridy = 1;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		
		pane.add(male, c);
		
		//female header
		JLabel female = new JLabel("Female");
		c.gridx = 2;
		c.gridy = 1;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		
		pane.add(female, c);
		
		districts = new JLabel[12];
		maleTributes = new JTextField[12];
		femaleTributes = new JTextField[12];
		
		for(int i = 0; i < 12; i++) {
			districts[i] = new JLabel("District " + (i + 1));
			c.gridx = 0;
			c.gridy = 2 + i;
			c.ipadx = 40;
			c.ipady = 10;
			c.gridwidth = 1;
			pane.add(districts[i], c);
			
			maleTributes[i] = new JTextField(20);
			c.gridx = 1;
			c.gridy = 2 + i;
			c.ipadx = 40;
			c.ipady = 10;
			c.gridwidth = 1;
			c.insets = new Insets(4,4,4,4);
			pane.add(maleTributes[i], c);
			
			femaleTributes[i] = new JTextField(20);
			c.gridx = 2;
			c.gridy = 2 + i;
			c.ipadx = 40;
			c.ipady = 10;
			c.gridwidth = 1;
			c.insets = new Insets(4,4,4,4);
			pane.add(femaleTributes[i], c);
		}
		
		submit = new JButton("Let the Games Begin!");
		c.gridx = 0;
		c.gridy = 14;
		c.ipadx = 20;
		c.ipady = 10;
		c.gridwidth = 4;
		c.insets = new Insets(10,10,10,10);
		pane.add(submit, c);
		submit.addActionListener(this);
	}
	
	public static void main(String[] args) {
		HungerGames app = new HungerGames("Hunger Games Simulator");
		app.init();
		app.setPreferredSize(new Dimension(800, 800));
		app.setLocation(400, 150);
		app.pack();
		app.setVisible(true);
		
		ArrayList<Event> events = new ArrayList<Event>();
		events.add(new Event("_v1 killed _v2", true));
		ArrayList<Event> nonLethal = new ArrayList<Event>();
		nonLethal.add(new Event("_v1 bitch-slapped _v2", true));
		data = new GameData(new ArrayList<Tribute>(), events, new ArrayList<Event>(), new  ArrayList<Item>(), 5, nonLethal, 2);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submit){
			ArrayList<Tribute> tributes = new ArrayList<Tribute>();
			for(int i = 0; i < 12; i++){
				Tribute t1 = new Tribute();
				t1.setName(maleTributes[i].getText());
				Tribute t2 = new Tribute();
				t2.setName(femaleTributes[i].getText());
				tributes.add(t1);
				tributes.add(t2);
			}
			data.setUsers(tributes);
			while(data.getUsers().size() != 1) {
				ArrayList<String> returns = data.nextDay();
				for(String s : returns){
					System.out.println(s);
				}
			}
			JOptionPane.showMessageDialog(null, data.getUsers().get(0).getName() + " is the winner!");
		}
		
	}

}
