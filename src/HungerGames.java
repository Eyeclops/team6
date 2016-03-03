

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
	JPanel cardPanel;
	JButton nextDay;
	JButton prevDay;
	JPanel setUpPanel;
	
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
		pane.setLayout(new FlowLayout());
		
		cardPanel = new JPanel(new CardLayout());
		
		
		setUpPanel = new JPanel();
		setUpPanel.setLayout(new GridBagLayout());
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
		
		setUpPanel.add(topMessage, c);
		
		//male header
		JLabel male = new JLabel("Male");
		c.gridx = 1;
		c.gridy = 1;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		
		setUpPanel.add(male, c);
		
		//female header
		JLabel female = new JLabel("Female");
		c.gridx = 2;
		c.gridy = 1;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		
		setUpPanel.add(female, c);
		
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
			setUpPanel.add(districts[i], c);
			
			maleTributes[i] = new JTextField(20);
			c.gridx = 1;
			c.gridy = 2 + i;
			c.ipadx = 40;
			c.ipady = 10;
			c.gridwidth = 1;
			c.insets = new Insets(4,4,4,4);
			setUpPanel.add(maleTributes[i], c);
			
			femaleTributes[i] = new JTextField(20);
			c.gridx = 2;
			c.gridy = 2 + i;
			c.ipadx = 40;
			c.ipady = 10;
			c.gridwidth = 1;
			c.insets = new Insets(4,4,4,4);
			setUpPanel.add(femaleTributes[i], c);
		}
		
		submit = new JButton("Let the Games Begin!");
		c.gridx = 0;
		c.gridy = 14;
		c.ipadx = 20;
		c.ipady = 10;
		c.gridwidth = 4;
		c.insets = new Insets(10,10,10,10);
		setUpPanel.add(submit, c);
		submit.addActionListener(this);
		pane.add(cardPanel);
		cardPanel.add(setUpPanel, "Setup");
		CardLayout l = (CardLayout) cardPanel.getLayout();
		l.show(cardPanel, "Setup");
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
			boolean flag = false;
			for(int i = 0; i < 12; i++){
				Tribute t1 = new Tribute();
				t1.setName(maleTributes[i].getText());
				t1.setDistrict(i + 1);
				t1.setMale(true);
				Tribute t2 = new Tribute();
				t2.setDistrict(i + 1);
				t2.setMale(false);
				t2.setName(femaleTributes[i].getText());
				tributes.add(t1);
				tributes.add(t2);
				if(maleTributes[i].getText().trim().equals("") || femaleTributes[i].getText().trim().equals("")) {
					flag = true;
					break;
				}
			}
			if(flag)
			{
				JOptionPane.showMessageDialog(null, "You need to enter 24 names before starting.");
			}
			else {
				cardPanel.removeAll();
				cardPanel.add(setUpPanel, "Setup");
				data.resetDay();
				data.setUsers((ArrayList<Tribute>)tributes.clone());
				
				while(data.getUsers().size() != 1) {
					ArrayList<String> events = data.nextDay();
					
					JPanel card = new JPanel();
					card.setLayout(new GridBagLayout());
					GridBagConstraints c = new GridBagConstraints();
					
					//Current Day
					c.gridx = 0;
					c.gridy = 0;
					c.ipadx = 20;
					c.ipady = 20;
					c.gridwidth = 4;
					card.add(new JLabel("<html><h1>Day " + Integer.toString(data.getDay()) + "</h1></html>"), c);
					
					
				
					String females = getFemaleList(tributes);
					String males = getMaleList(tributes);
					
					//list of male tributes
					c.gridx = 0;
					c.gridy = 1;
					c.ipadx = 20;
					c.ipady = 20;
					c.gridwidth = 1;
					card.add(new JLabel(males), c);
					
					//list of female tributes
					c.gridx = 3;
					c.gridy = 1;
					c.ipadx = 20;
					c.ipady = 20;
					c.gridwidth = 1;
					card.add(new JLabel(females), c);
					
					//List of events
					String eventString = getEventList(events);
					c.gridx = 1;
					c.gridy = 1;
					c.ipadx = 20;
					c.ipady = 20;
					c.gridwidth = 2;
					card.add(new JLabel(eventString), c);
					
					if(data.getUsers().size() != 1) {
						nextDay = new JButton("Next Day");
					}
					else {
						nextDay = new JButton("Start Over!");
					}
					nextDay.addActionListener(this);
					prevDay = new JButton("Previous Day");
					prevDay.addActionListener(this);
					
					c.gridx = 2;
					c.gridy = 2;
					c.ipadx = 20;
					c.ipady = 20;
					c.gridwidth = 1;
					card.add(nextDay, c);
					
					c.gridx = 1;
					c.gridy = 2;
					c.ipadx = 20;
					c.ipady = 20;
					c.gridwidth = 1;
					card.add(prevDay, c);
					
					//add panel to cards
					cardPanel.add(card, "Day " + data.getDay());
					
				}
//				JOptionPane.showMessageDialog(null, data.getUsers().get(0).getName() + " is the winner!");
				CardLayout l = (CardLayout) cardPanel.getLayout();
				l.next(cardPanel);
			}
		}
		else if(e.getActionCommand().equals("Next Day") || e.getActionCommand().equals("Start Over!")) {
			CardLayout l = (CardLayout) cardPanel.getLayout();
			l.next(cardPanel);
		}
		else if(e.getActionCommand().toString().equals("Previous Day")) {
			CardLayout l = (CardLayout) cardPanel.getLayout();
			l.previous(cardPanel);
		}
	}
	public String getFemaleList(ArrayList<Tribute> t) {
		String s = "<html><h2>Females<br><br>";
		for(int i = 0; i < t.size(); i++)
		{
			if(!t.get(i).getMale()) {
				if(t.get(i).getDead()) {
					s += "<strike>" + t.get(i).getName() + "</strike><br>";
				}
				else {
					s += t.get(i).getName() + "<br>";
				}
			}
		}
		s += "</h2></html>";
		return s;
	}
	
	public String getMaleList(ArrayList<Tribute> t){
		String s = "<html><h2>Males<br><br>";
		for(int i = 0; i < t.size(); i++)
		{
			if(t.get(i).getMale()) {
				if(t.get(i).getDead()) {
					s += "<strike>" + t.get(i).getName() + "</strike><br>";
				}
				else {
					s += t.get(i).getName() + "<br>";
				}
			}
		}
		s += "</h2></html>";
		return s;
	}
	public String getEventList(ArrayList<String> events) {
		String s = "<html><h2><center><br>";
		for(int i = 0; i < events.size(); i++)
		{
			if(!events.get(i).equals("_NULL_")) {
				s += events.get(i) + "<br>";
			}
			else {
				s += data.getUsers().get(0).getName() + " is the winner!";
			}
		}
		s += "</h2></center></html>";
		return s;
	}

}
