package data;

import java.util.*;

public class Machine {
	
	/**
	 * Instance of Machine
	 */
	private static Machine instance;

	/**
	 * Set of states
	 */
	private static ArrayList<Transition> trans;

	/**
	 * The machine alphabet as Set
	 */
	private static ArrayList<Character> machine_alphabet;

	/**
	 * The tape alphabet as Set
	 */
	private static ArrayList<Character> tape_alphabet;
	
	/**
	 * The list of stopping states (for step by step)
	 */
	private static ArrayList<Transition> stopStates;

	/**
	 * The initial state must be in 'states' !!
	 */
	private static String init_state;

	/**
	 * The accept state
	 */
	private static String accept_state;

	/**
	 * The reject state
	 */	
	private static String reject_state;

	public static Machine getInstance(){
		Machine ret = null;
		TuringIO io = new TuringIO();
		if(instance == null){
			//TODO:Get all informations from TuringIO
			machine_alphabet = io.loadMachineAlpha();
		}
		else ret = instance;
		return ret;
	}

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
	 */
	private Machine(ArrayList<Transition> states, ArrayList<Character> machine_alphabet, ArrayList<Character> tape_alphabet,ArrayList<Transition> stopStates ,String init_state, String accept_state, String reject_state){
		this.trans = states;
		this.machine_alphabet = machine_alphabet;
		this.tape_alphabet = tape_alphabet;
		this.stopStates = stopStates;
		this.init_state = init_state;
		this.accept_state = accept_state;
		this.reject_state = reject_state;
	} 

	/**
	 *	nextAction
	 *	for given state and symbol, return the new state, new symbol, and the direction, left or right.
	 *
	 *	@param state the current state
	 *	@param symbol the current symbol
	 *	@return the nextAction as HashMap with the following keys : "new_state" , "new_symbol" , "direction"
	 */
	public Transition nextAction(char redChar, String state){
		Transition ret = null;
		Iterator<Transition> it = trans.iterator();
		while(it.hasNext() && ret == null){
			Transition index = it.next();
			if(index.getCurrentState().equals(state) && index.getRedSymbole() == redChar){
				ret = index;
			}
		}
		if(ret == null) throw new NoSuchElementException("Etat "+state+" symbole "+redChar+" introuvable");
		return ret;
	}

	/**
	 *	This method indicate if the current state is the accept state
	 *
	 *	@param state The current state
	 *	@return Return true if the state is accepting, false else
	 */	
	public boolean isAccept(String state){
		boolean ret = false;
		if (accept_state.compareTo(state) == 0) {
			ret = true;
		}
		return ret;
	}

	/**
	 *	This method indicate if the current state is the reject state 	
	 *	@param state the current state
	 *	@return 
	 */
	public boolean isReject(String state){
		boolean ret = false;
		if (reject_state.equals(state)) {
			ret = true;
		}
		return ret;
	}
	
	public boolean isStop(String state){
		boolean ret = false;
		
		
		return ret;
	}

	public ArrayList<Transition> getStates() {
		return trans;
	}
	
	public ArrayList<Character> getMachine_alphabet() {
		return machine_alphabet;
	}
	
	public ArrayList<Character> getTape_alphabet() {
		return tape_alphabet;
	}
	
	public ArrayList<Transition> getStopStates() {
		return stopStates;
	}
	
	public String getInit_state() {
		return init_state;
	}
	
	public String getAccept_state() {
		return accept_state;
	}
	
	public String getReject_state() {
		return reject_state;
	}

}