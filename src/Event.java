/**
 * The class for handling all events that occur.
 *
 */
public class Event {
	String event; //Strings are formatted with person 1 being "_v1" and person 2 being "_v2"
	boolean transitive;
	
	public Event(String s, boolean transitive) {
		event = s;
		this.transitive = transitive;
	}
	
	public boolean isTransitive(){
		return transitive;
	}
	
	public String getString(Tribute victim){
		String s = event;
		return s.replaceAll("_v1", victim.getName());
	}
	
	public String getString(Tribute victim, Tribute killer){
		String s = event;
		s = s.replaceAll("_v1", killer.getName());
		return s = s.replaceAll("_v2", victim.getName());
	}
}
