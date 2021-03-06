import java.awt.CardLayout;
import java.awt.Component;
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
import javax.swing.border.EmptyBorder;

public class HungerGames extends JFrame implements ActionListener {
	private JButton submit;
	static GameData data;
	JLabel[] districts;
	JTextField[] maleTributes;
	JTextField[] femaleTributes;
	JPanel cardPanel;
	JButton nextDay;
	JButton prevDay;
	JButton autofill;
	JButton userSettings;
	JPanel setUpPanel;
	private static int goreLevel;

	public HungerGames(String title) {
		super(title);
		goreLevel = 0;
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

		// top message
		JLabel topMessage = new JLabel();
		topMessage
				.setText("<html><center>Welcome to the Hunger Games!<br><br>"
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

		// male header
		JLabel male = new JLabel("Male");
		c.gridx = 1;
		c.gridy = 1;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;

		setUpPanel.add(male, c);

		// female header
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

		for (int i = 0; i < 12; i++) {
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
			c.insets = new Insets(4, 4, 4, 4);
			setUpPanel.add(maleTributes[i], c);

			femaleTributes[i] = new JTextField(20);
			c.gridx = 2;
			c.gridy = 2 + i;
			c.ipadx = 40;
			c.ipady = 10;
			c.gridwidth = 1;
			c.insets = new Insets(4, 4, 4, 4);
			setUpPanel.add(femaleTributes[i], c);
		}

		submit = new JButton("Let the Games Begin!");
		c.gridx = 0;
		c.gridy = 14;
		c.ipadx = 20;
		c.ipady = 10;
		c.gridwidth = 4;
		c.insets = new Insets(10, 10, 10, 10);
		setUpPanel.add(submit, c);
		submit.addActionListener(this);

		JPanel filler = new JPanel();
		filler.setBorder(new EmptyBorder(5, 5, 5, 5));
		c.gridy = 15;
		c.gridx = 0;
		c.gridwidth = 4;
		c.ipady = 20;
		setUpPanel.add(filler, c);

		userSettings = new JButton("Settings...");
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.SOUTHWEST;
		c.gridx = 0;
		c.gridy = 16;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 2;
		setUpPanel.add(userSettings, c);
		userSettings.addActionListener(this);

		autofill = new JButton("I don't have time for this");
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.gridx = 2;
		c.gridy = 16;
		c.gridwidth = 2;
		setUpPanel.add(autofill, c);
		autofill.addActionListener(this);

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

		ArrayList<Event> events = lethalEvents();

		ArrayList<Event> nonLethal = nonlethalEvents();

		data = new GameData(new ArrayList<Tribute>(), events,
				new ArrayList<Event>(), new ArrayList<Item>(), 5, nonLethal, 2);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			ArrayList<Tribute> tributes = new ArrayList<Tribute>();
			boolean flag = false;
			for (int i = 0; i < 12; i++) {
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
				if (maleTributes[i].getText().trim().equals("")
						|| femaleTributes[i].getText().trim().equals("")) {
					flag = true;
					break;
				}
			}
			if (flag) {
				JOptionPane.showMessageDialog(null,
						"You need to enter 24 names before starting.");
			} else {
				cardPanel.removeAll();
				cardPanel.add(setUpPanel, "Setup");
				data.resetDay();
				data.setUsers((ArrayList<Tribute>) tributes.clone());

				while (data.getUsers().size() != 1) {
					ArrayList<String> events = data.nextDay();

					JPanel card = new JPanel();
					card.setLayout(new GridBagLayout());
					GridBagConstraints c = new GridBagConstraints();

					// Current Day
					c.anchor = GridBagConstraints.NORTH;
					c.gridx = 0;
					c.gridy = 0;
					c.ipadx = 20;
					c.ipady = 20;
					c.gridwidth = 4;
					c.weighty = 1;
					card.add(
							new JLabel("<html><h1>Day "
									+ Integer.toString(data.getDay())
									+ "</h1></html>"), c);

					// List of events
					String eventString = getEventList(events);
					JPanel eventPanel = new JPanel();
					// eventPanel.setBorder(new EmptyBorder(40, 60, 40, 60));
					eventPanel.add(new JLabel(eventString));
					c.gridx = 0;
					c.gridy = 1;
					c.ipadx = 20;
					c.ipady = 10;
					c.gridwidth = 4;
					card.add(eventPanel, c);

					String females = getFemaleList(tributes);
					String males = getMaleList(tributes);

					// list of male tributes
					c.gridx = 1;
					c.gridy = 2;
					c.ipadx = 20;
					c.ipady = 20;
					c.gridwidth = 1;
					c.fill = GridBagConstraints.NONE;
					card.add(new JLabel(males), c);

					// list of female tributes
					c.anchor = GridBagConstraints.NORTHEAST;
					c.gridx = 2;
					c.gridy = 2;
					c.ipadx = 20;
					c.ipady = 20;
					c.gridwidth = 1;
					card.add(new JLabel(females, SwingConstants.RIGHT), c);

					if (data.getUsers().size() != 1) {
						nextDay = new JButton("Next Day");
					} else {
						nextDay = new JButton("Start Over!");
					}
					nextDay.addActionListener(this);
					prevDay = new JButton("Previous Day");
					prevDay.addActionListener(this);

					c.gridx = 3;
					c.gridy = 3;
					c.ipadx = 20;
					c.ipady = 20;
					c.gridwidth = 1;
					card.add(nextDay, c);

					c.gridx = 0;
					c.gridy = 3;
					c.ipadx = 20;
					c.ipady = 20;
					c.gridwidth = 1;
					card.add(prevDay, c);

					// add panel to cards
					cardPanel.add(card, "Day " + data.getDay());

				}
				// JOptionPane.showMessageDialog(null,
				// data.getUsers().get(0).getName() + " is the winner!");
				CardLayout l = (CardLayout) cardPanel.getLayout();
				l.next(cardPanel);
			}
		} else if (e.getSource() == autofill) {
			ArrayList<String> males = data.getMaleNames();
			ArrayList<String> females = data.getFemaleNames();

			for (int i = 0; i < 12; i++) {
				maleTributes[i].setText(males.get(i));
				femaleTributes[i].setText(females.get(i));
			}
		} else if (e.getActionCommand().equals("Next Day")
				|| e.getActionCommand().equals("Start Over!")) {
			CardLayout l = (CardLayout) cardPanel.getLayout();
			l.next(cardPanel);
		} else if (e.getActionCommand().toString().equals("Previous Day")) {
			CardLayout l = (CardLayout) cardPanel.getLayout();
			l.previous(cardPanel);
		}
	}

	public String getFemaleList(ArrayList<Tribute> t) {
		String s = "<html><right><h4>Females<br><br>";
		for (int i = 0; i < t.size(); i++) {
			if (!t.get(i).getMale()) {
				if (t.get(i).getDead()) {
					s += "<strike>" + t.get(i).getName() + "</strike><br>";
				} else {
					s += t.get(i).getName() + "<br>";
				}
			}
		}
		s += "</h4></right></html>";
		return s;
	}

	public String getMaleList(ArrayList<Tribute> t) {
		String s = "<html><h4>Males<br><br>";
		for (int i = 0; i < t.size(); i++) {
			if (t.get(i).getMale()) {
				if (t.get(i).getDead()) {
					s += "<strike>" + t.get(i).getName() + "</strike><br>";
				} else {
					s += t.get(i).getName() + "<br>";
				}
			}
		}
		s += "</h4></html>";
		return s;
	}

	public String getEventList(ArrayList<String> events) {
		String s = "<html><h3><center><br>";
		for (int i = 0; i < events.size(); i++) {
			if (!events.get(i).equals("_NULL_")) {
				s += events.get(i) + "<br>";
			} else {
				s += data.getUsers().get(0).getName() + " is the winner!";
			}
		}
		s += "</h3></center></html>";
		return s;
	}

	public static ArrayList<Event> lethalEvents() {
		ArrayList<Event> events = new ArrayList<Event>();
		if (goreLevel == 0) {
			events.add(new Event("_v1 fell out of a tree and stopped moving", false));
			events.add(new Event("_v1 was really thirsty and fell asleep", false));
			events.add(new Event("_v1 fell off a cliff", false));
			events.add(new Event("_v1 was really hungry and fell asleep", false));
			events.add(new Event("_v1 got into a white van", false));
			events.add(new Event("_v1 ran into the forcefield", false));
			events.add(new Event("_v1 was stung by tracker jackers", false));
			events.add(new Event("_v1 went swimming without a swim buddy",
					false));
			events.add(new Event("_v1 was running with scissors and fell", false));
			events.add(new Event("_v1 was attacked by puppies", false));
			events.add(new Event("_v1 wandered off alone", false));
			events.add(new Event("_v1 fell into a hole in the ground", false));
			events.add(new Event("_v1 fell asleep and never woke up", false));
			events.add(new Event("_v1 lured _v2 into the forest", true));
		} else if (goreLevel == 1) {
			events.add(new Event("_v1 killed _v2 with a rock", true));
			events.add(new Event("_v1 shot _v2 with a gun", true));
			events.add(new Event("_v1 stabbed _v2 with a knife", true));
			events.add(new Event("_v1 strangled _v2", true));
			events.add(new Event("_v1 beheaded _v2 with an axe", true));
			events.add(new Event("_v1 broke _v2’s neck", true));
			events.add(new Event("_v1 pushed _v2 off a cliff", true));
			events.add(new Event("_v1 killed _v2 with a bow and arrow", true));
			events.add(new Event("_v1 was killed by mutts", false));
			events.add(new Event("_v1 beat _v2 to death", true));
			events.add(new Event("_v1 died from starvation", false));
			events.add(new Event("_v1 died from hypothermia", false));
			events.add(new Event("_v1 drowned in the lake", false));
			events.add(new Event("_v1 died from an infected wound", false));
			events.add(new Event("_v1 pushed _v2 into the forcefield", true));
			events.add(new Event("_v1 killed _v2 in their sleep", true));
			events.add(new Event(
					"_v1 was killed by _v2 while trying to cook food", true));
			events.add(new Event("_v1 was killed by poisonous gas", false));
			events.add(new Event("_v1 was struck by lightning", false));
			events.add(new Event("_v1 was killed by a landslide", false));
			events.add(new Event("_v1 ate poisonous berries", false));
			events.add(new Event("_v1 bled to death", false));
			events.add(new Event("_v1 fell on their own sword", false));
			events.add(new Event("_v1 killed _v2", true));
			events.add(new Event("_v1 fell off a cliff", false));
			events.add(new Event("_v1 shot _v2", true));
			events.add(new Event("_v1 died after eating poisoned food", false));
			events.add(new Event(
					"_v1 was stung to death by a swarm of hornets", false));
		} else {
			events.add(new Event("_v1 killed _v2 with a rock", true));
			events.add(new Event("_v1 blew _v2's head off with a gun", true));
			events.add(new Event("_v1 stabbed _v2 with a tree branch", true));
			events.add(new Event("_v1 strangled _v2 with their bare hands",
					true));
			events.add(new Event("_v1 beheaded _v2 with an axe", true));
			events.add(new Event("_v1 tore off every limb of _v2", true));
			events.add(new Event(
					"_v1 lost his legs to landmines and suffered in pain until he was eaten by wolves",
					false));
			events.add(new Event("_v1 lit _v2 on fire", true));
			events.add(new Event(
					"_v1 skewered _v2 with a spear and then roasted them over a fire",
					true));
			events.add(new Event("_v1 was eaten by _v2", true));
			events.add(new Event("_v1 crushed _v2's skull with a rock", true));
			events.add(new Event("_v1 decapitated _v2 with a machete", true));
		}

		return events;
	}

	public static ArrayList<Event> nonlethalEvents() {
		ArrayList<Event> events = new ArrayList<Event>();
		if (goreLevel == 0) {
			events.add(new Event("_v1 tripped _v2", true));
			events.add(new Event(
					"_v1 hurt themselves while jumping on a trampoline", false));
			events.add(new Event("_v1 fell asleep", false));
			events.add(new Event("_v1 traveled to higher ground", false));
			events.add(new Event("_v1 was stung by bees", false));
			events.add(new Event("_v1 climbed a tree", false));
			events.add(new Event("_v1 ate a well balanced meal", false));
			events.add(new Event("_v1 injured themselves", false));
			events.add(new Event("_v1 slept through the night", false));
			events.add(new Event("_v1 saved _v2’s life", true));
			events.add(new Event("_v1 built a fire", false));
			events.add(new Event("_v1 received a gift from home", false));
			events.add(new Event("_v1 and _v2 make an alliance", true));
			events.add(new Event("_v1 and _v2 made a truce for the night", true));
			events.add(new Event("_v1 caught a cold", false));
			events.add(new Event("_v1 and _v2 decided to work together", true));
			events.add(new Event("_v1 built a wall", true));
		} else if (goreLevel == 1) {
			events.add(new Event("_v1 cut _v2 with a knife", true));
			events.add(new Event("_v1 fell out of a tree and broke their arm",
					false));
			events.add(new Event("_v1 punched _v2 and broke their nose", true));
			events.add(new Event("_v1 and _v2 got in a fight", true));
			events.add(new Event("_v1 passed out from exhaustion", false));
			events.add(new Event("_v1 fell down a hill", false));
			events.add(new Event("_v1 broke their ankle", false));
			events.add(new Event("_v1 broke their leg", false));
			events.add(new Event("_v1 traveled to higher ground", true));
			events.add(new Event("_v1 was stung by tracker jackers", false));
			events.add(new Event("_v1 climbed a tree", false));
			events.add(new Event("_v1 hid in a cave", false));
			events.add(new Event("_v1 hunted for food", false));
			events.add(new Event("_v1 found a source of water", false));
			events.add(new Event("_v1 followed _v2", true));
			events.add(new Event("_v1 injured themselves", false));
			events.add(new Event("_v1 attacked _v2, but they escape unharmed",
					true));
			events.add(new Event("_v1 slept through the night", false));
			events.add(new Event("_v1 saved _v2’s life", true));
			events.add(new Event(
					"_v1 got sick after drinking contaminated water", false));
			events.add(new Event(
					"_v1 slipped while climbing a tree and broke their leg",
					false));
			events.add(new Event("_v1 built a fire", false));
			events.add(new Event("_v1 received a gift from home", false));
			events.add(new Event("_v1 and _v2 made a truce for the night", true));
			events.add(new Event("_v1 chased _v2 deep into the woods", true));
			events.add(new Event("_v1 sprained their ankle while running away",
					false));
			events.add(new Event("_v1 caught a cold", false));
			events.add(new Event("_v1 was injured trying to escape from _v2",
					true));
			events.add(new Event("_v1 and _v2 got into a fistfight", true));
		} else {
			events.add(new Event("_v1 stabbed _v2 in the leg", true));
			events.add(new Event(
					"_v1 smashed _v2's leg and left them to suffer", true));
			events.add(new Event("_v1 traveled to higher ground", true));
			events.add(new Event("_v1 was stung by tracker jackers", false));
			events.add(new Event(
					"_v1 climbed a tree and fell from a broken branch", false));
			events.add(new Event("_v1 hid in a cave and was attacked by bats",
					false));
			events.add(new Event("_v1 was hunted by wolves", false));
			events.add(new Event(
					"_v1 lost both their legs to a landmine explosion", false));
			events.add(new Event("_v1 bit off _v2's fingers", true));
			events.add(new Event("_v1 drank unsanitary water", false));
			events.add(new Event("_v1 stalked _v2", true));
			events.add(new Event("_v1 injured themselves", false));
			events.add(new Event("_v1 attacked _v2, but they escape unharmed",
					true));
			events.add(new Event(
					"_v1 got sick after drinking contaminated water", false));
			events.add(new Event(
					"_v1 slipped while climbing a tree and broke their leg",
					false));
			events.add(new Event(
					"_v1 received an explosive from home and lost their hands",
					false));
			events.add(new Event("_v1 and _v2 make an alliance", true));
			events.add(new Event("_v1 chased _v2 deep into the woods", true));
			events.add(new Event("_v1 sprained their ankle while running away",
					false));
			events.add(new Event("_v1 coughed up blood", false));
			events.add(new Event("_v1 was injured trying to escape from _v2",
					true));
			events.add(new Event("_v1 and _v2 got into a fistfight", true));
		}
		// events.add(new Event("_v1 bitch-slapped _v2", true));
		return events;
	}
}
