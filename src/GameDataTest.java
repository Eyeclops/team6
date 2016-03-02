import java.util.ArrayList;

public class GameDataTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		//Create events
		ArrayList<Event> events = new ArrayList<Event>();
		events.add(new Event("_v1 killed _v2", true));
		events.add(new Event("_v1 tripped on a banana peel and died.", false));
		ArrayList<Event> nonLethal = new ArrayList<Event>();
		nonLethal.add(new Event("_v1 slapped _v2", true));
		nonLethal.add(new Event("_v1 took a nap", false));
		
		//create tributes
		ArrayList<Tribute> t = new ArrayList<Tribute>();
		for(int i = 0; i < 24; i++){
			Tribute temp = new Tribute();
			temp.setDistrict(i + 1);
			if(i % 2 == 0)	{	temp.setMale(true); }
			else			{	temp.setMale(false); }
			t.add(new Tribute());
			Character name = new Character((char)('a'+i));
			t.get(t.size() - 1).setName(name.toString());
		}
		//Copy for the second game
		ArrayList<Tribute> t2 = (ArrayList<Tribute>) t.clone();
		
		
		//Game 1
		System.out.println("Game 1:");
		GameData test1 = new GameData(t, events, events, new  ArrayList<Item>(), 5, nonLethal, 2);
		while(test1.getUsers().size() != 1) {
			ArrayList<String> returns = test1.nextDay();
			for(String s : returns){
				if(!s.equals("_NULL_"))	{	System.out.println(s);	}
			}
		}
		System.out.println();
		System.out.println();
		
		//Game 2
		System.out.println("Game 2:");
		GameData test2 = new GameData(t2, events, events, new  ArrayList<Item>(), 10, nonLethal, 10);
		while(test2.getUsers().size() != 1) {
			ArrayList<String> returns = test2.nextDay();
			for(String s : returns){
				if(!s.equals("_NULL_"))	{	System.out.println(s);	}
			}
		}
			
	}

}
