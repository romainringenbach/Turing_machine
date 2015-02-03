package data;

import java.util.*

public class Machine {

	/**
	 * Set of states
	 */

	private HashSet<String> states;

	/**
	 * The machine alphabet as Set
	 */	

	private HashSet<String> machine_alphabet;

	/**
	 * The tape alphabet as Set
	 */		

	private HashSet<String> tape_alphabet;

	/**
	 * Transitions as HashMap, in this following form :
	 * key : "start_state start_symbol"
	 * value : "final_state final_symbol direction " , direction as "R" or "L" 
	 */

	private HashMap<String,String> transitions;

	/**
	 * The initial state
	 * must be in states !!
	 */

	private String init_state;

	/**
	 * The accept state
	 */

	private String accept_state;

	/**
	 * The reject state
	 */	

	private String reject_state;

	/**
	 * The configuration file as String
	 */

	private String machine;

	/**
	 *	CONSTRUCTOR 
	 *
	 *	@param states the states list
	 *	@param machine_alphabet the machine alphabet (alphabet that the user can use)	 
	 *	@param tape_alphabet the tape alphabet	
	 *	@param transitions the transitions list (in key the "start_state start_symbol" and in value "final_state final_symbol direction_R_or_L ")	
	 *	@param init_state the initial state
	 *	@param accept_state the accept state
	 *	@param reject_state the reject state
	 *	@param machine the configuration file as String
	 */

	public Machine(HashSet<String> states, HashSet<String> machine_alphabet, HashSet<String> tape_alphabet, HashMap<String,String> transitions, String init_state, String accept_state, String reject_state, String machine){

		this.states = states;
		this.machine_alphabet = machine_alphabet;
		this.tape_alphabet = tape_alphabet;
		this.transitions = transitions;
		this.init_state = init_state;
		this.accept_state = accept_state;
		this.reject_state = reject_state;
		this.machine = machine;		

	} 

	/**
	 *	nextAction
	 *
	 *	for given state and symbol, return the new state, new symbol, and the direction, left or right.
	 *
	 *	@param state the current state
	 *	@param symbol the current symbol
	 *	@return the nextAction as HashMap with the following keys : "new_state" , "new_symbol" , "direction"
	 */


	public HashMap<String,String> nextAction(String state, String symbol){

		HashMap<String,String> action = null;

		String key = state + " " + symbol; 

		if (transitions.containsKey(key)) {
			
			String tmpAction = transitions.get(key);

			String[] tmp = tmpAction.split("\s");

			action = new HashMap<String,String>();

			action.put("new_state",tmp[0]);
			action.put("new_symbol",tmp[1]);
			action.put("direction",tmp[2]);

		}

		return ret;

	}

	/**
	 *	isAccept 
	 *
	 *	this methode indicate if the current state is the accept state 	
	 *
	 *	@param state the current state
	 *	@return 
	 *
	 */	

	public boolean isAccept(String state){

		boolean ret = false;

		if (accept_state.compareTo(state) == 0) {
			ret = true;
		}

		return ret;
	}

	/**
	 *	isReject 
	 *
	 *	this methode indicate if the current state is the reject state 	
	 *
	 *	@param state the current state
	 *	@return 
	 *
	 */

	public boolean isReject(String state){

		boolean ret = false;

		if (reject_state.equals(state)) {
			ret = true;
		}

		return ret;
	}

	/**
	 *	Can be use for displaying the configuration file
	 *
	 *	@return the machine as String
	 */

	public String getMachine(){
		return machine;
	}

}