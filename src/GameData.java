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
	private int minKill;
	private int minNonLethal;
	private int tributeTheme;

	// TODO add getters and setters

	/**
	 * Constructor
	 * 
	 * @param users
	 *            An arraylist of all users in the game
	 * @param events
	 *            An arraylist of all possible events
	 * @param dayOneEvents
	 *            An arraylist of all possible events on day one
	 * @param items
	 *            An arraylist of all items that can be distributed during the
	 *            game
	 * @param killNum
	 *            Max number of people to kill each day
	 * @param nonLethal
	 *            An arraylist of all events that do not kill people
	 * @param nonLethalnum
	 *            Max number of nonlethal events to perform each day
	 */
	public GameData(ArrayList<Tribute> users, ArrayList<Event> events,
			ArrayList<Event> dayOneEvents, ArrayList<Item> items, int killNum,
			ArrayList<Event> nonLethal, int nonLethalNum) {
		this.users = users;
		this.events = events;
		// Events that occur the first day
		this.dayOneEvents = dayOneEvents;
		// These are items that are allocatable through the day
		this.items = items;
		this.killNum = killNum;
		this.nonLethal = nonLethal;
		this.nonLethalNum = nonLethalNum;
		this.minKill = killNum / 2;
		this.minNonLethal = nonLethalNum / 2;
		day = 0;
		rand = new Random(); // Randomly determine # of people to kill each day
		tributeTheme = rand.nextInt(4);
	}

	public void setUsers(ArrayList<Tribute> users) {
		this.users = users;
	}

	public ArrayList<Tribute> getUsers() {
		return users;
	}

	/**
	 * Returns the current day in the hunger games
	 * 
	 * @return the current day
	 */
	public int getDay() {
		return this.day;
	}

	/**
	 * Progresses through a day.
	 * 
	 * @return List of the day's events. If the game is over the last string is
	 *         "_NULL_"
	 */
	public ArrayList<String> nextDay() {
		day++;
		ArrayList<String> dead = new ArrayList<String>();
		int toKill = rand.nextInt(killNum - minKill) + minKill + 1; // Makes it
																	// so toKill
																	// is at
																	// least 1
																	// and
																	// killNum
																	// can reach
																	// the max
																	// int.
		int randEvents = rand.nextInt(nonLethalNum - minNonLethal)
				+ minNonLethal + 1;
		if (users.size() == 1) {
			// To signify game is over, append a null-named user to the returned
			// list.
			dead.add("_NULL");
			return dead;
		}

		// List of nonlethal events
		for (int i = 0; i < randEvents; i++) {
			int tribute = rand.nextInt(users.size());
			int eventnum = rand.nextInt(nonLethal.size());
			Tribute t = users.get(tribute);
			Event e = nonLethal.get(eventnum);
			if (e.isTransitive()) {
				int u2index = rand.nextInt(users.size());
				while (u2index == tribute) {
					u2index = rand.nextInt(users.size());
				}
				Tribute t2 = users.get(u2index);
				dead.add(e.getString(t, t2));
			} else {
				if (e.GetItem() != null) {
					t.addItem(e.GetItem()); // Give item to user
				}
				dead.add(e.getString(t));
			}
		}

		// List of lethal events
		for (int i = 0; i < toKill; i++) { // Select a death tribute for each
											// person to kill
			int tributeNum = rand.nextInt(users.size() * 1000) + 1;
			Tribute lastUser = null;
			while (tributeNum > 0) {
				lastUser = users.get(rand.nextInt(users.size()));
				tributeNum -= (1000 - lastUser.getStrength());
			}
			Event e = events.get(rand.nextInt(events.size()));
			lastUser.setDead(true);
			users.remove(lastUser); // remove from active players
			if (e.isTransitive()) {
				if (e.getRequired() == null) {
					dead.add(e.getString(lastUser,
							users.get(rand.nextInt(users.size()))));
				} else {
					LinkedList<Tribute> counter = new LinkedList<Tribute>();
					// determine if there are any people who can legally do this
					// transitive action.
					for (Tribute t : users) {
						if (t.hasItem(e.getRequired())) {
							counter.add(t);
						}
					}
					if (counter.size() >= 0) {
						dead.add(e.getString(lastUser,
								counter.get(rand.nextInt(counter.size()))));
					} else {
						// Since we didn't kill anyone, decrement i
						users.add(lastUser);
						lastUser.setDead(false);
						i--;
						continue;
					}
				}
			} else {
				dead.add(e.getString(lastUser));
			}
			if (users.size() == 1) {
				// To signify game is over, append a null-named user to the
				// returned list.
				dead.add("_NULL_");
				break;
			}
		}
		return dead;
	}

	public void resetDay() {
		day = 0;
	}

	public ArrayList<String> getFemaleNames() {
		ArrayList<String> names = new ArrayList<String>();
		if (tributeTheme == 0) {
			// Hunger Games Theme
			names.add("Glimmer");
			names.add("Clove");
			names.add("Cashmere");
			names.add("Enobaria");
			names.add("Wiress");
			names.add("Mags");
			names.add("Johanna Mason");
			names.add("Cecelia");
			names.add("Seeder");
			names.add("Katniss Everdeen");
			names.add("Glimmer");
			names.add("Clove");
			names.add("Foxface");
			names.add("Rue");
			names.add("Annie");
		} else if (tributeTheme == 1) {
			// Harry Potter Theme
			names.add("Hermione Granger");
			names.add("Ginny Weasley");
			names.add("Luna Lovegood");
			names.add("Katie Bell");
			names.add("Cho Chang");
			names.add("Lavender Brown");
			names.add("Pavarti Patil");
			names.add("Padma Patil");
			names.add("Pansy Parkinson");
			names.add("Lily Potter");
			names.add("Molly Weasley");
			names.add("Rita Skeeter");
			names.add("Professor McGonagall");
			names.add("Bellatrix Lestrange");
			names.add("Petunia Dursley");
			names.add("Fleur Delacour");
			names.add("Dolores Umbridge");
		} else if (tributeTheme == 2) {
			// Family Guy Theme
			names.add("Lois Griffin");
			names.add("Meg Griffin");
			names.add("Thelma Griffin");
			names.add("Karen Griffin");
			names.add("Barbara Pewterschmidt");
			names.add("Carol Pewterschmidt");
			names.add("Loretta Brown");
			names.add("Bonnie Swanson");
			names.add("Susie Swanson");
			names.add("Muriel Goldman");
			names.add("Diane Simmons");
			names.add("Tricia Takanawa");
			names.add("Connie D'Amico");
			names.add("Jillian Russell");
		} else if (tributeTheme == 3) {
			// The Simpsons Theme
			names.add("Marge Simpson");
			names.add("Lisa Simpson");
			names.add("Maggie Simpson");
			names.add("Patty Bouvier");
			names.add("Selma Bouvier");
			names.add("Crazy Cat Lady");
			names.add("Maude Flanders");
			names.add("Mona Simpson");
			names.add("Sarah Wiggum");
			names.add("Luann Van Houten");
			names.add("Agnes Skinner");
			names.add("Elizabeth Hoover");
			names.add("Edna Krabappel");
			names.add("Snowball");
		} else {
			names.add("Leslie Knope");
			names.add("WonderWoman");
			names.add("Lidsay Lohan");
			names.add("Britney Spears");
			names.add("Hilary Clinton");
			names.add("Pheobe Buffay");
			names.add("Amy Phoeler");
			names.add("Tina Fey");
			names.add("Kristen Whig");
			names.add("Oprah");
			names.add("Michelle Obama");
			names.add("Hope Solo");
			names.add("Queen of England");
			names.add("The Purple Teletubbie");
			names.add("Beyonce");
			names.add("Ellen DeGeneres");
			names.add("Lady Gaga");
			names.add("Monica Lewinsky");
			names.add("Your Mom");
			names.add("Sarah Palin");
			names.add("A girl scout");
			names.add("The one female CS student");
			names.add("Rachel Green");
			names.add("Kim Kardashian");
			names.add("Wendy Testaburger");
		}

		long seed = System.nanoTime();
		Collections.shuffle(names, new Random(seed));

		return names;
	}

	public ArrayList<String> getMaleNames() {
		ArrayList<String> names = new ArrayList<String>();
		if (tributeTheme == 0) {
			// Hunger Games Theme
			names.add("Gloss");
			names.add("Brutus");
			names.add("Beetee");
			names.add("Finnick Odair");
			names.add("Blight");
			names.add("Woof");
			names.add("Chaff");
			names.add("Peeta Mellark");
			names.add("Marvel");
			names.add("Cato");
			names.add("Jason");
			names.add("Thresh");
			names.add("Haymitch");
			names.add("Brutus");
			names.add("Chaff");
		} else if (tributeTheme == 1) {
			// Harry Potter Theme
			names.add("Harry Potter");
			names.add("Ron Weasley");
			names.add("George Weasley");
			names.add("Fred Weasley");
			names.add("Professor Dumbledore");
			names.add("Rubeus Hagrid");
			names.add("Lord Voldemort");
			names.add("Sirius Black");
			names.add("Draco Malfoy");
			names.add("Cedric Diggory");
			names.add("Dudley Dursley");
			names.add("Vernon Dursley");
			names.add("Seamus Finnigan");
			names.add("Nicolas Flamel");
			names.add("Viktor Krum");
			names.add("Neville Longbottom");
			names.add("Remus Lupin");
			names.add("James Potter");
			names.add("Severus Snape");
			names.add("Dean Thomas");
		} else if (tributeTheme == 2) {
			// Family Guy Theme
			names.add("Peter Griffin");
			names.add("Chris Griffin");
			names.add("Stewie Griffin");
			names.add("Joe Swanson");
			names.add("Cleveland Brown");
			names.add("Glenn Quagmire");
			names.add("Brian Griffin");
			names.add("Francis Griffin");
			names.add("Carter Pewterschmidt");
			names.add("Jasper");
			names.add("Mr. Herbert");
			names.add("Mort Goldman");
			names.add("Neil Goldman");
			names.add("Tom Tucker");
			names.add("Adam West");
			names.add("James Wood");
			names.add("Evil Monkey");
			names.add("Kool-Aid Man");
			names.add("The Giant Chicken");
		} else if (tributeTheme == 3) {
			// The Simpsons Theme
			names.add("Homer Simpson");
			names.add("Bart Simpson");
			names.add("Kent Brockman");
			names.add("Bumblebee Man");
			names.add("Santa's Little Helper");
			names.add("Mr. Burns");
			names.add("Superintendent Chalmers");
			names.add("Comic Book Guy");
			names.add("Duffman");
			names.add("Fat Tony");
			names.add("Ned Flanders");
			names.add("Sideshow Bob");
			names.add("Abraham Simpson");
			names.add("Milhouse Van Houten");
			names.add("Ralph Wiggum");
			names.add("Chief Wiggum");
			names.add("Groundskeeper Willie");
		} else {
			names.add("Donald Trump");
			names.add("Adolf Hitler");
			names.add("Dwight Schrute");
			names.add("Jim Halpert");
			names.add("Andy Dwyer");
			names.add("Ron Swanson");
			names.add("Michael Scott");
			names.add("Chandler Bing");
			names.add("Al Gore");
			names.add("Eric Cartman");
			names.add("Stan Marsh");
			names.add("Randy Marsh");
			names.add("Kenny McCormick");
			names.add("Kyle Broflovski");
			names.add("Barack Obama");
			names.add("Santa");
			names.add("Easter Bunny");
			names.add("Kanye");
			names.add("Tom Cruise");
			names.add("Charlie Sheen");
			names.add("Michael Jackson");
			names.add("Batman");
			names.add("IronMan");
		}

		long seed = System.nanoTime();
		Collections.shuffle(names, new Random(seed));

		return names;
	}

}
