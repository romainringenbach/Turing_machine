package data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Machine {
	
	private static TuringIO io;

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
	 * List of all the configurations during the programm
	 */
	private ArrayList<String> configurations;

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
	
	public Machine(){
		io = TuringIO.getInstance();
		this.machine_alphabet = io.loadMachineAlpha();
		this.tape_alphabet = io.loadTapeAlpha();
		this.trans = io.loadTrans();
		this.init_state = io.loadInitState();
		this.accept_state = io.loadAcceptState();
		this.reject_state = io.loadRejectState();
		this.stop_states = io.loadStopStates();
		configurations = new ArrayList<String>();
	}

	/**
	 *	For given state and symbol, return the unique associated Transition
	 *
	 *	@param symbol the current symbol
	 *	@param state the current state
	 *	@return Return the next Transition object to run
	 */
	public Transition getTransitionFromSym(Character redChar, String state){
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
	
	public void addConfig(String conf){
		configurations.add(conf);
	}
	
	
	
	/* --- Getters --- */

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
	
	public ArrayList<String> getConfigurations() {
		return configurations;
	}
	
	public TuringIO getTuringIO() {
		return io;
	}
}