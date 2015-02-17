package ctrl;

import javax.swing.JOptionPane;

import view.TMView;
import view.Tape;
import data.Machine;
import data.Transition;

public class TMCtrl{

	private TMView view;
	private Machine data;
	private Tape tape;
	/**
	 * Indicates the position of the reading head on the tape
	 */
	private int lect;
	/**
	 * The state of the program in a given moment
	 */
	private String currentState;
	private Character currentChar;
	private Transition currentTrans;
	/**
	 * True if the machine is in accept or reject state
	 */
	public boolean ended;
	/**
	 * True if the machine is already started and not finished
	 */
	public boolean started;
	/**
	 * True if the machine is running or paused
	 */
	public boolean running;
	
	public TMCtrl(TMView v, Machine d){
		view = v;
		data = d;
		lect = 0;
		ended = false;
		started = false;
		running = false;
		tape = view.getTapePanel();
		currentState = data.getInitState();
	}
	
	/* --- Actions --- */
	
	/**
	 * Launch the machine. Don't stop before end.
	 */
	public void startButton(){
		running = true;
		view.getButStart().setEnabled(false);
		new Thread(){
            public void run(){
            	while(!ended && running){
        			TMCtrl.this.doTransition();
        			try {
						Thread.sleep(300); //Can define a speed
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
        		}
            	return;
            }
        }.start();
	}
	
	/**
	 * Do a single transition
	 */
	public void startStepButton(){
		if(!ended){
			TMCtrl.this.doTransition();
		}
	}
	
	/**
	 * Stop the running program
	 */
	public void stopButton(){
		running = false;
		view.getButStart().setEnabled(true);
	}
	
	
	/**
	 * Do the next transition of the machine
	 */
	private void doTransition(){
		/* 
		 * Changer valeur 'lect'
		 * Décolorer l'ancien emplacement de la tête
		 * Colorer le nouvel emplacement de la tête 
		 * Sélectionner la transition suivante
		 * Ecraser caractère de la transition
		 */
		
		currentChar = tape.getChar(lect);
		currentTrans = data.getTransitionFromSym(currentChar, currentState);
		
		tape.setDefaultColor(lect);
		//Overwrite the new symbole
		tape.setChar(lect, currentTrans.getNewSymbole());
		//Set the direction of the head
		if(currentTrans.getDirection().equals(">")) 
			lect++;
		else if(lect > 0)
			lect--;
		//Color the head
		tape.setHead(lect);
		
		
		if(data.isAccept(currentTrans.getNextState())){
			JOptionPane.showMessageDialog(view, "Etat acceptant !");
			this.end();
		}
		else if(data.isReject(currentTrans.getNextState())){
			JOptionPane.showMessageDialog(view, "Etat rejetant !");
			this.end();
		}
		else{
			//Set the new state
			currentChar = tape.getChar(lect);
			currentState = currentTrans.getNextState();
			currentTrans = data.getTransitionFromSym(currentChar, currentState);
			view.getTable().setRowSelectionInterval(data.getTrans().indexOf(currentTrans), data.getTrans().indexOf(currentTrans));
		}
	}
	
	

	/**
	 * Setup the tape with the given string in the field and the first state
	 * This field can't be empty
	 */
	public void init(){
		String input = view.getInputField().getText();
		if(!input.equals("")){
			tape.initTape(input);
			lect = 0;
			started = true;
			running = true;
			ended = false;
			currentState = data.getInitState();
			currentChar = tape.getChar(lect);
			currentTrans = data.getTransitionFromSym(currentChar, currentState);
			
			view.getTable().setRowSelectionInterval(data.getTrans().indexOf(currentTrans), data.getTrans().indexOf(currentTrans));
		}
		else 
			JOptionPane.showMessageDialog(view, "Veuillez inscrire dans le champ ci-dessous le ruban initial.");

	}
	
	/**
	 * Reset the machine at the begining, ready to start again.
	 */
	public void end(){
		ended = true;
		started = false;
		running = false;
		view.getButStart().setEnabled(true);
		view.getTable().getSelectionModel().clearSelection();
	}
	
	/* --------------- */
	
	
	
	public Tape getTape(){
		return tape;
	}
}
