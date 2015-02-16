package data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Machine {
	
	/**
	 * Instance of Machine
	 */
	private static Machine instance;

	/**
	 * Set of states
	 */
	private ArrayList<Transition> trans;

	/**
	 * The machine alphabet as Set
	 */
	private ArrayList<Character> machine_alphabet;

	/**
	 * The tape alphabet as Set
	 */
	private ArrayList<Character> tape_alphabet;
	
	/**
	 * The list of stopping states (for step by step)
	 */
	private ArrayList<String> stop_states;

	/**
	 * The initial state, within the machine starts
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
	
	public static Machine getInstance(){
		Machine ret = null;
		TuringIO io = new TuringIO();
		if(instance == null){
			ret = new Machine (io.loadMachineAlpha(),io.loadTapeAlpha(),io.loadTrans(),io.loadInitState(),io.loadAcceptState(),io.loadRejectState(),io.loadStopStates());
		}
		else ret = instance;
		return ret;
	}

	/**
	 *  Constructor of the Turing Machine.
	 *	@param machine_alphabet the machine alphabet (alphabet that the user can use)	 
	 *	@param tape_alphabet the tape alphabet	
	 *	@param states the states list
	 *	@param transitions the transitions list (in key the "start_state start_symbol" and in value "final_state final_symbol direction_R_or_L ")	
	 *	@param init_state the initial state
	 *	@param accept_state the accept state
	 *	@param reject_state the reject state
	 */
	private Machine(ArrayList<Character> machine_alphabet, ArrayList<Character> tape_alphabet, ArrayList<Transition> states,String init_state, String accept_state, String reject_state, ArrayList<String> stopStates){
		this.trans = states;
		this.machine_alphabet = machine_alphabet;
		this.tape_alphabet = tape_alphabet;
		this.init_state = init_state;
		this.accept_state = accept_state;
		this.reject_state = reject_state;
		this.stop_states = stopStates;
	} 

	/**
	 *	For given state and symbol, return the unique associated Transition
	 *
	 *	@param symbol the current symbol
	 *	@param state the current state
	 *	@return Return the next Transition object to run
	 */
	public Transition nextAction(Character redChar, String state){
		Transition ret = null;
		Iterator<Transition> it = trans.iterator();
		while(it.hasNext() && ret == null){
			Transition index = it.next();
			if(index.getCurrentState().equals(state) && index.getReadSymbole().equals(redChar)){
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
		if (accept_state.equals(state)) {
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
		if(stop_states.contains(state)){
			ret = true;
		}
		return ret;
	}

	public ArrayList<Transition> getTrans() {
		return trans;
	}
	
	public ArrayList<Character> getMachineAlpha() {
		return machine_alphabet;
	}
	
	public ArrayList<Character> getTapeAlpha() {
		return tape_alphabet;
	}
	
	public String getInitState() {
		return init_state;
	}
	
	public String getAcceptState() {
		return accept_state;
	}
	
	public String getRejectState() {
		return reject_state;
	}

	public ArrayList<String> getStopStates() {
		return stop_states;
	}
}