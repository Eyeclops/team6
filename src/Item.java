
public class Item {
	ItemCategory category;
	int power;
	
	public Item(){
		category = null;
	}
	
	public void setPower(int power){
		this.power = power;
	}
	
	public int getPower(){
		return power;
	}
}
