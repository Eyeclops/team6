
import java.util.*;
import java.util.ArrayList;

public class GameData {
	private ArrayList<Tribute> users;
	private ArrayList<Event> events;
	private ArrayList<Event> dayOneEvents;
	private ArrayList<Item> items;
	private int day;
	private int killNum;
	private Random rand;
	private int nonLethalNum;
	private ArrayList<Event> nonLethal;
	//TODO add getters and setters
	
	/**
	 * Constructor
	 * @param users			An arraylist of all users in the game
	 * @param events 		An arraylist of all possible events
	 * @param dayOneEvents 	An arraylist of all possible events on day one
	 * @param items			An arraylist of all items that can be distributed during the game
	 * @param killNum		Max number of people to kill each day
	 * @param nonLethal		An arraylist of all events that do not kill people
	 * @param nonLethalnum	Max number of nonlethal events to perform each day
	 */
	public GameData(ArrayList<Tribute> users, ArrayList<Event> events, ArrayList<Event> dayOneEvents, ArrayList<Item> items, int killNum, ArrayList<Event> nonLethal, int nonLethalNum){
		this.users = users;
		this.events = events;
		//Events that occur the first day
		this.dayOneEvents = dayOneEvents;
		//These are items that are allocatable through the day
		this.items = items;
		this.killNum = killNum;
		this.nonLethal = nonLethal;
		this.nonLethalNum = nonLethalNum;
		day = 0;
		rand = new Random(); //Randomly determine # of people to kill each day
	}
	public void setUsers(ArrayList<Tribute> users) {
		this.users = users;
	}
	public ArrayList<Tribute> getUsers() {
		return users;
	}
	
	/**
	 * Progresses through a day.
	 * @return List of the day's events. If the game is over the last string is "_NULL_"
	 */
	public ArrayList<String> nextDay(){
		ArrayList<String> dead = new ArrayList<String>();
 		int toKill = rand.nextInt(killNum) + 1; //Makes it so toKill is at least 1 and killNum can reach the max int.
 		int randEvents = rand.nextInt(nonLethalNum) + 1;
 		if(users.size() == 1) {
			//To signify game is over, append a null-named user to the returned list.
			dead.add("_NULL");
			return dead;
		}
 		
 		//List of nonlethal events
 		for(int i = 0; i < randEvents; i++){
 			int tribute = rand.nextInt(users.size());
 			int eventnum = rand.nextInt(nonLethal.size());
 			Tribute t = users.get(tribute);
 			Event e = nonLethal.get(eventnum);
 			if(e.isTransitive()){
 				int u2index = rand.nextInt(users.size());
 				while(u2index == tribute) { u2index = rand.nextInt(users.size()); }
 				Tribute t2 = users.get(u2index);
 				dead.add(e.getString(t, t2));
 			}
 			else {
 				dead.add(e.getString(t));
 			}
 		}
 		
 		//List of lethal events
		for(int i = 0; i < toKill; i++){ //Select a death tribute for each person to kill
			int tributeNum = rand.nextInt(users.size()*1000) + 1;
			Tribute lastUser = null;
			while(tributeNum > 0){
				lastUser = users.get(rand.nextInt(users.size() - 1));
				tributeNum -= (1000-lastUser.getStrength());
			}
			Event e = events.get(rand.nextInt(events.size() - 1));
			users.remove(lastUser); //remove from active players
			if(e.isTransitive()){
				dead.add(e.getString(lastUser, users.get(rand.nextInt(events.size() - 1))));
			} else {
				dead.add(e.getString(lastUser));
			}
			if(users.size() == 1) {
				//To signify game is over, append a null-named user to the returned list.
				dead.add("_NULL_");
				break;
			}
		}
		return dead;
	}
	
	
}
