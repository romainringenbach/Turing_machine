package data;

import java.util.ArrayList;

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

	public boolean equals(Object o){

		Transition tmp = (Transition) o;

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

	public static ArrayList<Transition> toSort(ArrayList<Transition> transitions){

		Transition[] tabTransitions = new Transition[transitions.size()];

		tabTransitions = transitions.toArray(tabTransitions);

		int first = 0;
		int last = tabTransitions.length - 1;
		quickSort(tabTransitions,first,last);

		ArrayList<Transition> ret = new ArrayList<Transition>();

		for (int i = 0; i < tabTransitions.length; i++){
			ret.add(tabTransitions[i]);
		}

		return ret;
		
	}	

	private static void quickSort(Transition[] transitions, int first, int last){

		if(first < last){

			int pivot = (int) (last-first)/2 + first;
					
			pivot = partition(transitions, first, pivot, last);
			quickSort(transitions, first, pivot - 1);
			quickSort(transitions, pivot+1, last);

		}



	}

	private static int partition(Transition[] transitions, int first, int pivot, int last){

		change(transitions,pivot,last);

		int j = first;

		for (int i = first; i < last; i++){

			if(transitions[last].compareTo(transitions[i]) >= 0){
				change (transitions, i,j);
				j++;
			}

		}

		change (transitions,last,j);
		return j;



	}

	private static void change(Transition[] transitions, int a, int b){

		Transition tmp = transitions[a];
		transitions[a] = transitions[b];
		transitions[b] = tmp;


	}	
	
}
