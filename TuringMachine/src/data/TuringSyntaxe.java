package data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;

public final class TuringSyntaxe {

	private String regex_direction;
	private String regex_oneChar;
	private String regex_oneWord;
	private String regex_states;
	private String regex_stopState;
	private String regex_machine_alphabet;
	private String regex_aMachine_alphabet;
	private String regex_tape_alphabet;
	private String regex_aTape_alphabet;
	private String regex_transitions;
	private String regex_transition;
	private String regex_init_state;
	private String regex_accept_state;
	private String regex_reject_state;
	private String regex_end;

	private Pattern pattern_direction;
	private Pattern pattern_oneChar;
	private Pattern pattern_oneWord;
	private Pattern pattern_states;
	private Pattern pattern_stopState;
	private Pattern pattern_machine_alphabet;
	private Pattern pattern_aMachine_alphabet;
	private Pattern pattern_tape_alphabet;
	private Pattern pattern_aTape_alphabet;
	private Pattern pattern_transitions;
	private Pattern pattern_transition;
	private Pattern pattern_init_state;
	private Pattern pattern_accept_state;
	private Pattern pattern_reject_state;
	private Pattern pattern_end;

	private static TuringSyntaxe instance = null;

	public final static TuringSyntaxe getInstance(){

		if (instance == null) {
			TuringSyntaxe.instance = new TuringSyntaxe();
		}

		return instance;

	}

	private TuringSyntaxe(){

		this.initPatterns();

	}

	private void initPatterns(){

		regex_direction = "([<>])";
		regex_oneChar = "([^\\s])";
		regex_oneWord = "([^\\s])*";
		regex_states = "(states)";
		regex_stopState = "(([^\\s])*+(\\s)*+(S))";
		regex_machine_alphabet = "(machine_alphabet)";
		regex_aMachine_alphabet = "([^\\s\\t]*)";  
		regex_tape_alphabet = "(tape_alphabet)";
		regex_aTape_alphabet = "([^\\s\\t]*)";	
		regex_transitions = "(transitions)";
		regex_transition = "([^\\s]*+(\\s)+[^\\s]*+(\\s)+[^\\s]*+(\\s)+[^\\s]*+(\\s)+[<>])";
		regex_init_state = "(init_state)";
		regex_accept_state = "(accept_state)";
		regex_reject_state = "(reject_state)";
		regex_end = "(end)";

		pattern_direction = Pattern.compile(regex_direction);
		pattern_oneChar = Pattern.compile(regex_oneChar);
		pattern_oneWord = Pattern.compile(regex_oneWord);
		pattern_states = Pattern.compile(regex_states);
		pattern_stopState = Pattern.compile(regex_stopState);
		pattern_machine_alphabet = Pattern.compile(regex_machine_alphabet);
		pattern_aMachine_alphabet = Pattern.compile(regex_aMachine_alphabet);
		pattern_tape_alphabet = Pattern.compile(regex_tape_alphabet);
		pattern_aTape_alphabet = Pattern.compile(regex_aTape_alphabet);
		pattern_transitions = Pattern.compile(regex_transitions);
		pattern_transition = Pattern.compile(regex_transition);
		pattern_init_state = Pattern.compile(regex_init_state);
		pattern_accept_state = Pattern.compile(regex_accept_state);
		pattern_reject_state = Pattern.compile(regex_reject_state);
		pattern_end = Pattern.compile(regex_end);

	}	

    /**
     *  check that the string match to the pattern
     *
     *  @param str the string 
     *  @param pattern the pattern
     *  @return true if that match, else false
     */

    private boolean matche(String str, Pattern pattern){

    	boolean ret = false;

    	if(str != null && pattern != null)
    		ret = pattern.matcher(str).matches();	

        return ret;

    }


	public Machine checkAndCreate(String path) throws Exception{

		TuringIO io = TuringIO.getInstance();

		String configuration = io.getConfiguration(path);

		Machine machine = null;

		if(configuration != null){

			HashSet<String> states = new HashSet<String>();
			ArrayList<Transition> transitions_with_unknow_state = new ArrayList<Transition>();
			ArrayList<Transition> transitions = new ArrayList<Transition>();
			ArrayList<Character> machine_alphabet = new ArrayList<Character>();
			ArrayList<Character> tape_alphabet = new ArrayList<Character>();
			ArrayList<String> stopStates = new ArrayList<String>();
			String init_state;
			String accept_state;
			String reject_state = null;			

			String[] areas = configuration.split(":");

			for (int i = 0; i < areas.length; i++){
				areas[i] = this.purge(areas[i]);
			}

			String[] sub_areas;

			// check states

			if (this.matche(areas[0],this.pattern_states)) {
				
				sub_areas = areas[1].split("(,)");

				for (String state : sub_areas) {

					state = this.purge(state);
					
					if(!state.equals(""))
						if (this.matche(state,this.pattern_oneWord)) {
							states.add(state);
						}
						else if (this.matche(state,this.pattern_stopState)){
							String[] sub_state = state.split("[\\s\t\n\r]");
							stopStates.add(sub_state[0]);

						}
						else {
							throw new Exception("State must be \" q, \" or \" q S, \" , error : "+state);
						}

				}


			}
			else {

				throw new Exception("Configuration must begin by states area, error : "+areas[0]);

			}

			// check machine_alphabet

			if (this.matche(areas[2],this.pattern_machine_alphabet)) {

				sub_areas = areas[3].split("[\\s\t\n\r]");				

				for (String alphabet : sub_areas) {
					
					alphabet = this.purge(alphabet);

					if(!alphabet.equals(""))
						if (this.matche(alphabet,this.pattern_oneChar)) {
							if (!machine_alphabet.contains(Character.valueOf(alphabet.charAt(0)))) {
								machine_alphabet.add(Character.valueOf(alphabet.charAt(0)));
							}
							else {
								throw new Exception("this symbole is already in machine_alphabet, error : "+alphabet);
							}
							
						}
						else {
							throw new Exception("machine_alphabet must be a single symbole, error : "+alphabet);
						}

				}				

			}
			else {

				throw new Exception("States area must be follow by machine_alphabet area, error :" +areas[2]);

			}

			// check tape_alphabet

			if (this.matche(areas[4],this.pattern_tape_alphabet)) {

				sub_areas = areas[5].split("[\\s\t\n\r]");				

				for (String alphabet : sub_areas) {

					alphabet = this.purge(alphabet);
					
					if(!alphabet.equals(""))
						if (this.matche(alphabet,this.pattern_oneChar)) {
							if (!tape_alphabet.contains(Character.valueOf(alphabet.charAt(0)))) {
								tape_alphabet.add(Character.valueOf(alphabet.charAt(0)));
							}
							else {
								throw new Exception("this symbole is already in tape_alphabet, error : "+alphabet);
							}
						}
						else {
							throw new Exception("tape_alphabet must be a single symbole, error : "+alphabet);
						}

				}

				Iterator<Character> it = machine_alphabet.iterator(); 
				Character element_it;	
				while (it.hasNext()){
					element_it =  it.next();
					if (!(tape_alphabet.contains(element_it))) {
						throw new Exception("all element of machine_alphabet area must be in tape_alphabet area, error : "+element_it.toString());				
					}

				}				

			}
			else {

				throw new Exception("machine_alphabet area must be follow by tape_alphabet area, error : "+areas[4]);

			}

			// check transitions

			if (this.matche(areas[6],this.pattern_transitions)) {

				sub_areas = areas[7].split("[,]");		

				for (String transition : sub_areas) {

					transition = this.purge(transition);
					
					if(!transition.equals(""))
						if (this.matche(transition,this.pattern_transition)) {

							Transition aTransition;

							String current;
							Character readS;
							Character newS;
							String dir;
							String next;
							
							boolean unknow = false;

							String[] element_transition = transition.split("[\\s\t\n\r]");

							for (int i = 0; i < element_transition.length; i++){
								element_transition[i] = this.purge(element_transition[i]);
							}

							// current state

							if ( states.contains(element_transition[0]) || stopStates.contains(element_transition[0]) ) {
								current = element_transition[0];
							}
							else {
								throw new Exception ("a transition must begin by a state, error : "+element_transition[0]);
							}

							// current element of tape alphabet

							if ( tape_alphabet.contains(Character.valueOf(element_transition[1].charAt(0))) && this.matche(element_transition[1], this.pattern_oneChar)) {
								readS = Character.valueOf(element_transition[1].charAt(0));
							}
							else {
								throw new Exception ("second element must be in tape_alphabet, error : "+element_transition[1]);
							}	

							// next state

							if ( states.contains(element_transition[2]) || stopStates.contains(element_transition[2]) ){
								next = element_transition[2];
							}
							else {

								if (this.matche(element_transition[2],this.pattern_oneWord)) {
									next = element_transition[2];
									unknow = true;
									
								}
								else {
									throw new Exception ("thrid element must be a state, error : "+element_transition[2]);
								}
							}

							// next element of tape alphabet

							if ( tape_alphabet.contains(Character.valueOf(element_transition[3].charAt(0)))&& this.matche(element_transition[3], this.pattern_oneChar)) {
								newS = Character.valueOf(element_transition[3].charAt(0));
							}
							else {
								throw new Exception ("forth element must be in tape_alphabet, error : "+element_transition[3]);
							}	

							// direction

							if ( this.matche(element_transition[4],this.pattern_direction) ) {
								dir = element_transition[4];
							}
							else {
								throw new Exception ("fith element must be a direction like \"<\" or \">\" , error : "+element_transition[4]);
							}						

							Boolean find = false;

							Iterator<Transition> it = transitions.iterator();

							Transition tmp ;

							while(it.hasNext() && !find){
								tmp = it.next();

								find = (tmp.getCurrentState().equals(current) && tmp.getReadSymbole().equals(readS));
							}

							it = transitions_with_unknow_state.iterator();

							while(it.hasNext() && !find){
								tmp = it.next();

								find = (tmp.getCurrentState().equals(current) && tmp.getReadSymbole().equals(readS));
							}								

							if(!find){

								aTransition = new Transition(current, readS, newS, dir, next);

								if (!unknow) {
									transitions.add(aTransition);	
								}
								else {
									transitions_with_unknow_state.add(aTransition);
								}		

							}
							else{
								throw new Exception("there are already transitions with this current state and current symbol, error : "+transition);
							}
							


						}
						else {
							throw new Exception("transition must be like \" state tape_alphabet state tape_alphabet [RL]  \", error : "+transition);
						}

				}				

			}
			else {

				throw new Exception("tape_alphabet area must be follow by transitions area, error : "+areas[6]);

			}						

			// Init state

			if (this.matche(areas[8],this.pattern_init_state)) {
				
				areas[9] = this.purge(areas[9]);

				if (this.matche(areas[9],this.pattern_oneWord)) {
					
					if (states.contains(areas[9]) || stopStates.contains(areas[9])) {
						
						init_state = areas[9];

					}
					else {
						throw new Exception("init_state must be in states, error : "+areas[9]);
					}

				}
				else {
					throw new Exception("init_state must be a single word, error : "+areas[9]);
				}

			}
			else {
				throw new Exception("transitions area must be follow by init_state area, error : "+areas[8]);

			}

			// Accept state

			if (this.matche(areas[10],this.pattern_accept_state)) {

				areas[11] = this.purge(areas[11]);
				
				if (this.matche(areas[11],this.pattern_oneWord)) {
					if (!states.contains(areas[11]) && !stopStates.contains(areas[11])) {
						Boolean find = false;

						Transition tmp = null;

						for(int i = 0; i < transitions_with_unknow_state.size();i++){
							tmp = transitions_with_unknow_state.get(i);
							if (tmp.getNextState().equals(areas[11])){
								transitions.add(tmp);
								find = true;
								transitions_with_unknow_state.remove(tmp);
							}
						}						

						if ( find ){
							accept_state = areas[11];

						}
						else {
							throw new Exception("accept_state must be in a transition (minimum) as next state, error : "+areas[11]);
						}
						
					}
					else {
						throw new Exception("accept_state can't be in states list, error : "+areas[11]);
					}
				}
				else {
					throw new Exception("accept_state must be a single word, error : "+areas[11]);
				}



			}
			else {
				throw new Exception("init_state area must be follow by accept_state area, error : "+areas[10]);

			}	

			// Reject state

			if (this.matche(areas[12],this.pattern_reject_state)) {

				areas[13] = this.purge(areas[13]);
				
				if (this.matche(areas[13],this.pattern_oneWord)) {
					if (!states.contains(areas[13]) && !stopStates.contains(areas[13])) {
						Boolean find = false;

						Transition tmp = null;

						for(int i = 0; i < transitions_with_unknow_state.size();i++){
							tmp = transitions_with_unknow_state.get(i);
							if (tmp.getNextState().equals(areas[13])){
								transitions.add(tmp);
								find = true;
								transitions_with_unknow_state.remove(tmp);
							}
						}

						if ( find ){
							reject_state = areas[13];							
						}
						else {
							throw new Exception("reject_state must be in a transition (minimum) as next state, error : "+areas[13]);
						}
						
					}
					else {
						throw new Exception("reject_state can't be in states list, error : "+areas[13]);
					}
				}
				else {
					throw new Exception("reject_state must be a single word, error : "+areas[13]);
				}

				// End

				if (this.matche(areas[14],this.pattern_end)) {
					
				}
				else {
					throw new Exception("reject_state area must be follow by \" :end: \", error : "+areas[14]);

				}

			}

			else {

				// End

				if (this.matche(areas[12],this.pattern_end)) {
					
				}
				else {
					throw new Exception("accept_state area must be follow by \" :end: \" or reject_state, error : "+areas[14]);

				}

			}

	
			
			if (transitions_with_unknow_state.size() != 0) {

				Iterator<Transition> it = transitions_with_unknow_state.iterator();

				String some_states = "";

				while(it.hasNext()){

					some_states = some_states + it.next().getNextState() + ",";

				}

				throw new Exception("There are some unknow next states in transitions, error : "+some_states);
			}

			machine = new Machine 		(transitions,
										 machine_alphabet,
										 tape_alphabet,
										 stopStates,
										 init_state,
										 accept_state,
										 reject_state
										);

		}
		
		else {

			throw new Exception("Syntaxe error, configuration file is null");

		}

		return machine;	

	}

	public boolean checkSyntax(String path){

		boolean ret = false;

		try {

			Machine test = this.checkAndCreate(path);
			ret = true;
		}

		catch (Exception e ){

			System.out.println(e.getMessage());
			e.printStackTrace();

		}

		return ret;

	}

	/**
	 *	Create because trim remove only white space, we need to remove \n \t \r char
	 *
	 */

	private String purge(String str){

		str = str.replace('\t',' ');
		str = str.replace('\n',' ');
		str = str.replace('\r',' ');
		str = str.trim();

		return str;

	}

}