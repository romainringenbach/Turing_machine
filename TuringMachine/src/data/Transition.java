package data;

public class Transition {

	public String currentState;
	public String redSymbole;
	public String newSymbole;
	public String direction;
	public String nextState;
	
	public Transition(String current, String redS, String newS, String dir, String next){
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
	public String getRedSymbole() {
		return redSymbole;
	}
	public String getNewSymbole() {
		return newSymbole;
	}
	public String getDirection() {
		return direction;
	}
	public String getNextState() {
		return nextState;
	}
	
}
