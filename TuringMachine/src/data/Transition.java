package data;

public class Transition {

	public String currentState;
	public char redSymbole;
	public char newSymbole;
	public String direction;
	public String nextState;
	 
	public Transition(String current, char redS, char newS, String dir, String next){
		currentState = current;
		redSymbole = redS;
		newSymbole = newS;
		direction = dir;
		nextState = next;
	}
	
	public String toString(){
		return currentState + " " + redSymbole + " " + newSymbole + " " + direction + " " + nextState; 
	}

	
	public String getCurrentState() {
		return currentState;
	}
	public char getRedSymbole() {
		return redSymbole;
	}
	public char getNewSymbole() {
		return newSymbole;
	}
	public String getDirection() {
		return direction;
	}
	public String getNextState() {
		return nextState;
	}
	
}
