package test;

import data.TuringSyntaxe;

public class TestConfiguration {

	public static void main(String[] args){

		testNoException("../tests/Configurations.txt", "The good configuration, no exception will be catch");
		testException("../tests/TestEndFollowRejectState.txt", "If end not follow Reject State area : catch exception"); 
		testException("../tests/TestMAInTA.txt", "If machine alphabet is not in tape alphabet : catch exception"); 
		testException("../tests/TestTAFollowMA.txt", "If tape alphabet not follow machine alphabet : catch exception"); 
		testException("../tests/TestTransitionSecondChar.txt", "If the second element of a transition is not a tape alphabet : catch exception"); 
		testException("../tests/TestAcceptStateFollowInitState.txt", "If accept state not follow init state area : catch exception"); 
		testException("../tests/TestInitStateFollowTransition.txt", "If init state not follow transition area  : catch exception"); 
		testException("../tests/TestRejectStateFollowAcceptState.txt", "If reject state not follow accept state area : catch exception"); 
		testException("../tests/TestTapeAlphabet.txt", "If tape alphabet is not a symbole : catch exception"); 
		testException("../tests/TestTransitionsFollowTA.txt", "If transition not follow tape alphabet area : catch exception"); 
		testException("../tests/TestAcceptStateInTransition.txt", "If accept state is not in transition as next state of a transition : catch exception"); 
		testException("../tests/TestInitStateInStates.txt", "If init state is not in states : catch exception"); 
		testException("../tests/TestRejectStateInTransition.txt", "If reject state is not in transition as next state of a transition : catch exception"); 
		testException("../tests/TestTransitionFirstChar.txt", "If first element of a transition is not a tape alphabet : catch exception"); 
		testException("../tests/TestTransitionThirdChar.txt", "If third element of a transition is not a state (states or accept or reject) : catch exception"); 
		testException("../tests/TestAcceptStateNotInStates.txt", "If accept state is in states : catch exception"); 
		testException("../tests/TestInitStateSingleWord.txt", "If init state is not a state : catch exception"); 
		testException("../tests/TestRejectStateNotInStates.txt", "If reject state is in states : catch exception"); 
		testException("../tests/TestTransitionFithChar.txt", "If fith element of a transition is not like < or > : catch exception"); 
		testException("../tests/TestAcceptState.txt", "If accept state is not like state : catch exception"); 
		testException("../tests/TestMachineAlphabet.txt", "If machine alphabet is not a symbole : catch exception"); 
		testException("../tests/TestRejectState.txt", "If reject state is not like state : catch exception"); 
		testException("../tests/TestTransitionForm.txt", "If transition have not a for like currentState readTape_alphabet nextState newTape_alphabet direction (< or >) : catch exception"); 
		testException("../tests/TestBeginByState.txt", "If not begin by states area : catch exception"); 
		testException("../tests/TestMAFollowState.txt", "If machine alphabet not follow states area : catch exception"); 
		testException("../tests/TestState.txt", "If element in states is not like \"word\" or \"word S\" : catch exception"); 
		testException("../tests/TestTransitionForthChar.txt", "If forth element of a transition is not a tape alphabet : catch exception"); 
		testException("../tests/TestAlreadyInTransition.txt", "If transition is already in transitions (same currentState and readTape_alphabet) : catch exception"); 
		testException("../tests/TestAlreadyInUnknowTransition.txt", "If transition is already in unknows transitions (same currentState and readTape_alphabet) : catch exception"); 


	}

	public static void testException(String path, String str){

		try {
			TuringSyntaxe.getInstance().checkAndCreate(path);
			System.out.println("false : Test : "+str);
		}
		catch (Exception e){
			System.out.println("true  : Test : "+str);
			System.out.println("\t\t"+e.getMessage());
		}
	}

	public static void testNoException(String path, String str){

		try {
			TuringSyntaxe.getInstance().checkAndCreate(path);
			System.out.println("true  : Test : "+str);
		}
		catch (Exception e){
			System.out.println("false : Test : "+str);
			System.out.println("\t\t"+e.getMessage());
			e.printStackTrace();
		}



	}	

} 