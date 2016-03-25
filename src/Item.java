
public class Item {
	ItemCategory category;
	String name;
	int power;
	
	public Item(String name, int power){
		category = null;
		this.name = name;
		this.power = power;
	}
	
	public void setPower(int power){
		this.power = power;
	}
	
	public int getPower(){
		return power;
	}
}
