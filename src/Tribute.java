

public class Tribute {
	private int district;
	private boolean isMale;
	private boolean isDead;
	private String name;
	private int health;
	private Item[] items;
	private int strength;
	private String event;
	
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

	int getStrength(){
		return strength;
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
