import java.util.*;
public class GameData {
	private ArrayList<Tribute> users;
	private ArrayList<Event> events;
	private ArrayList<Event> dayOneEvents;
	private ArrayList<Item> items;
	private int day;
	private int killNum;
	private Random rand;
	//TODO add getters and setters
	
	/**
	 * Constructor
	 * @param users			An arraylist of all users in the game
	 * @param events 		An arraylist of all possible events
	 * @param dayOneEvents 	An arraylist of all possible events on day one
	 * @param items			An arraylist of all items that can be distributed during the game
	 * @param killNum		Max number of people to kill each day
	 */
	public GameData(ArrayList<Tribute> users, ArrayList<Event> events, ArrayList<Event> dayOneEvents, ArrayList<Item> items, int killNum){
		this.users = users;
		this.events = events;
		//Events that occur the first day
		this.dayOneEvents = dayOneEvents;
		//These are items that are allocatable through the day
		this.items = items;
		this.killNum = killNum;
		day = 0;
		rand = new Random(); //Randomly determine # of people to kill each day
	}
	
	/**
	 * Progresses through a day.
	 * @return Whether a winner has been decided
	 */
	public ArrayList<Tribute> nextDay(){
		ArrayList<Tribute> dead = new ArrayList<Tribute>();
 		int toKill = rand.nextInt(killNum) + 1; //Makes it so toKill is at least 1 and killNum can reach the max int.
 		if(users.size() == 1) {
			//To signify game is over, append a null-named user to the returned list.
			dead.add(new Tribute());
			return users;
		}
		for(int i = 0; i < toKill; i++){ //Select a death tribute for each person to kill
			int tributeNum = rand.nextInt(users.size()*1000) + 1;
			Tribute lastUser = null;
			while(tributeNum > 0){
				lastUser = users.get(rand.nextInt(users.size()));
				tributeNum -= (1000-lastUser.getStrength());
			}
			Event e = events.get(rand.nextInt(events.size()));
			users.remove(lastUser); //remove from active players
			if(e.isTransitive()){
				lastUser.setEvent(e, users.get(rand.nextInt(events.size())));
			} else {
				lastUser.setEvent(e, null);
			}
			dead.add(lastUser);
			if(users.size() == 1) {
				//To signify game is over, append a null-named user to the returned list.
				dead.add(new Tribute());
				break;
			}
		}
		return dead;
	}
	
	
}
