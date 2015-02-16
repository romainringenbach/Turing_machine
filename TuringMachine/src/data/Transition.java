package data;

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
	
	public String toString(){
		return currentState + "  " + readSymbole + "  " + newSymbole + "  " + direction + "  " + nextState; 
	}

	
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
	
}
