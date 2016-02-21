/**
 * The class for handling all events that occur.
 *
 */
public class Event {
	String event; //Strings are formatted with person 1 being "_v1" and person 2 being "_v2"
	boolean transitive;
	
	public boolean isTransitive(){
		return transitive;
	}
	
	public String getString(Tribute victim){
		String s = event;
		return s.replaceAll("_v1", victim.getName());
	}
	
	public String getString(Tribute victim, Tribute killer){
		String s = event;
		s.replaceAll("_v1", victim.getName());
		return s = s.replaceAll("_v2", killer.getName());
	}
}
