import java.util.ArrayList;

public class Tribute {
	private int district;
	private boolean isMale;
	private boolean isDead;
	private String name;
	private int health;
	private ArrayList<Item> items;
	private int strength;
	private int excess_strength;
	private String event;
	
	public Tribute(){
		district = 0;
		isMale = false;
		isDead = false;
		name = null;
		health = 0; // probably need to change this
		items = new ArrayList<Item>();
		strength = 0;
		excess_strength = 0;
		event = null;
	}
	
	
	int getDistrict(){
		return district;
	}
	
	void setDistrict(int district){
		this.district = district;
	}
	
	boolean getMale(){
		return isMale;
	}
	
	void setMale(boolean isMale){
		this.isMale = isMale;
	}
	
	boolean getDead(){
		return isDead;
	}
	
	void setDead(boolean isDead){
		this.isDead = isDead;
	}
	
	String getName(){
		return name;
	}
	
	void setName(String name) {
		this.name = name;
	}
	
	int getHealth(){
		return health;
	}
	
	void setHealth(int health){
		this.health = health;
	}

	public int getStrength(){
		return strength;
	}
	
	public void addItem(Item item){
		strength += item.getPower();
		if(strength > 999){
			int extra = 0;
			extra = strength - 999;
			excess_strength += extra;
			strength = 999;
		}
		items.add(item);
	}
	
	public void removeItem(Item item){
		int iStr = item.getPower();
		if(!items.contains(item)){
			System.err.println("Item not found");
			return; 
		}
		items.remove(items.get(items.indexOf(item)));
		if(excess_strength > 0){
			iStr = excess_strength;
			if(iStr > excess_strength){
				iStr -= excess_strength;
				excess_strength = 0;
			}
			else{
				excess_strength -= iStr;
				return;
			}
		}
		this.strength -= iStr;
		
	}
	
	public boolean hasItem(Item item){
		return items.contains(item);
	}
	
	void setEvent(Event event, Tribute killer){
		if(killer == null){
			this.event = event.getString(this);
		}
		else{
			this.event = event.getString(this, killer);
		}
	}
}
