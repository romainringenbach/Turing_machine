package data;

/**
 * Class representing a Turing Transition
 * It contains :
 * <ul>
 *  <li>The current state</li>
 *  <li>The read symbole on the head</li>
 *  <li>The new symbole to write</li>
 *  <li>The direction of the head after the transition</li>
 *  <li>The next state of the machine</li>
 * </ul>
 */
public class Transition {

	public String currentState;
	public Character readSymbole;
	public Character newSymbole;
	public String direction;
	public String nextState;
	 
	public Transition(String current, Character readS, Character newS, String dir, String next){
		currentState = current;
		readSymbole = readS;
		newSymbole = newS;
		direction = dir;
		nextState = next;
	}
	
	/**
	 * @return Return the string representation of a Transition : [currentState readSymbole newSymbole direction nextState]
	 */
	public String toString(){
		return currentState + "  " + readSymbole + "  " + newSymbole + "  " + direction + "  " + nextState; 
	}

	/* --- Getters --- */
	public String getCurrentState() {
		return currentState;
	}
	public Character getReadSymbole() {
		return readSymbole;
	}
	public Character getNewSymbole() {
		return newSymbole;
	}
	public String getDirection() {
		return direction;
	}
	public String getNextState() {
		return nextState;
	}
	
	
	public boolean equals(Object obj){
		Transition tmp = (Transition) obj;
		boolean ret = false;

		if (
			this.currentState.equals(tmp.currentState) &&
			this.readSymbole.equals(tmp.readSymbole) &&
			this.newSymbole.equals(tmp.newSymbole) &&
			this.direction.equals(tmp.direction) &&
			this.nextState.equals(tmp.nextState)
			) {
			ret = true;
		}
		return ret;
	}

	public int compareTo(Transition transition){
		int ret = -1;
		ret = this.currentState.compareTo(transition.currentState);
		if(ret == 0){
			ret = this.readSymbole.compareTo(transition.readSymbole);
		}
		return ret;
	}
	
}
