

public class Tribute {
	private int district;
	private boolean isMale;
	private boolean isDead;
	private String name;
	private int health;
	private Item[] items;
	private int strength;
	private String event;
	
	//TODO add getters and setters
	int getStrength(){
		return strength;
	}
	
	String getName(){
		return name;
	}
	
	void setName(String name) {
		this.name = name;
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
