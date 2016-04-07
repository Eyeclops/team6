import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class UserSettings extends JDialog implements ActionListener{

	JComboBox<String> goreOption;
	JTextField lethal;
	JTextField nonLethal;
	JComboBox<String> groupOption;
	JComboBox<String> districtOption;
	JButton submit;
	
	public UserSettings(JDialog dialog, String string){
		super(dialog, string, true);
	}
	
	public void init() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.PAGE_START;
		
		JLabel title = new JLabel("User Settings");
		add(title, c);
		
		
		//Set a level of gore
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.insets = new Insets(4,4,4,4);
		add(new JLabel("Level of Gore: "), c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		String[] goreLevel = { "Low", "Medium", "High" }; 
		goreOption = new JComboBox<String>(goreLevel);
		goreOption.setSelectedIndex(1);
		add(goreOption, c);
		
		//set number of deaths per day
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		add(new JLabel("Number of Deaths per Day: "), c);
		
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		lethal = new JTextField(15);
		add(lethal, c);
		
		//non lethal events per day
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		add(new JLabel("Number of Non-lethal Events: "), c);
		
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		nonLethal = new JTextField(15);
		add(nonLethal, c);
		
		//lists of people to pull from for "i dont have time for this"
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		add(new JLabel("List of Autofill Characters"), c);
		
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		String[] groups = { "Default", "Hunger Games", "Harry Potter", "Family Guy", "The Simpsons" }; 
		groupOption = new JComboBox<String>(groups);
		add(groupOption, c);
		
		
		//save one person
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		add(new JLabel("Person to \"Resurect\""), c);
		
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		String[] districts = { "None", 
							   "District 1 Male",
							   "District 1 Female",
							   "District 2 Male",
							   "District 2 Female",
							   "District 3 Male",
							   "District 3 Female",
							   "District 4 Male",
							   "District 4 Female",
							   "District 5 Male",
							   "District 5 Female",
							   "District 6 Male",
							   "District 6 Female",
							   "District 7 Male",
							   "District 7 Female",
							   "District 8 Male",
							   "District 8 Female",
							   "District 9 Male",
							   "District 9 Female",
							   "District 10 Male",
							   "District 10 Female",
							   "District 11 Male",
							   "District 11 Female",
							   "District 12 Male",
							   "District 12 Female"}; 
		districtOption = new JComboBox<String>(districts);
		add(districtOption, c);
		
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		submit = new JButton("Update Settings");
		submit.addActionListener(this);
		add(submit, c);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submit){
			if(lethal.getText().equals("")||
					nonLethal.getText().equals("") ||
					Integer.parseInt(nonLethal.getText()) < 2 || 
					Integer.parseInt(nonLethal.getText()) > 10 ||
					Integer.parseInt(lethal.getText()) < 2 || 
					Integer.parseInt(lethal.getText()) > 10 ||
					lethal.getText().contains(".") || 
					nonLethal.getText().contains(".")) {
				JOptionPane.showMessageDialog(null, "Lethal and non lethal events need to be numbers between 2 and 10");
			}
			else {
				HungerGames.data.setMinLethal(Integer.parseInt(nonLethal.getText()));
				HungerGames.data.setMinLethal(Integer.parseInt(lethal.getText()));
				HungerGames.data.setResurect(districtOption.getSelectedItem().toString());
				HungerGames.data.setGore(goreOption.getSelectedItem().toString());
				HungerGames.data.setTheme(groupOption.getSelectedItem().toString());
				
				HungerGames.data.setLethal(HungerGames.lethalEvents());
				HungerGames.data.setNonLethal(HungerGames.nonlethalEvents());
				this.setVisible(false);
			}
		}
	}

}
