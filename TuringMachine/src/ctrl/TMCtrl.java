package ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import view.TMView;
import view.Tape;
import data.Machine;
import data.Transition;

public class TMCtrl implements MouseListener, KeyListener, ActionListener{

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
	private Transition currentTrans;
	/**
	 * True if the machine is in accept or reject state
	 */
	private boolean ended;
	/**
	 * True if the program already started
	 */
	private boolean started;
	private Character currentChar;
	
	public TMCtrl(TMView v, Machine d){
		view = v;
		data = d;
		lect = 0;
		ended = false;
		started = false;
		tape = view.getTapePanel();
		currentState = data.getInitState();
	}
	
	/* --- Actions --- */
	
	/**
	 * Launch the machine. Don't stop before end.
	 */
	private void startButton(){
		view.getButStart().setEnabled(false);
		new Thread(){
            public void run(){
            	while(!ended){
        			TMCtrl.this.doTransition();
        			try {
						Thread.sleep(800); //Can define a speed
					} catch (InterruptedException e) {
					}
        		}
            	return;
            }
        }.start();
	}
	
	/**
	 * Do a single transition
	 */
	private void startStepTM(){
		if(!ended){
			TMCtrl.this.doTransition();
		}
	}
	
	
	/**
	 * Do the next transition of the machine
	 */
	private void doTransition(){
		/* TODO:
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
	
	
	/* --- Listeners of view --- */

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER && e.getSource()==view.getInputField()){
			this.init();
		}
	}
	
	/* --- Unused --- */
	public void keyReleased(KeyEvent arg0) {;}
	public void keyTyped(KeyEvent arg0) {;}
	/* -------------- */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == view.getButStart()){
			if(!started) this.init();
			this.startButton();
		}
		if(src == view.getButStep()){
			if(!started) this.init();
			this.startStepTM();
		}
		if(src == view.getButStep2()){
			this.init();
			//TODO:Lauch machine until stop state
		}
		if(src == view.getButReset()){
			view.getInputField().setText("");
			tape.reset();
			view.getTable().getSelectionModel().clearSelection();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		Object src = e.getSource();
		if(src == view.getButStep()){
			view.getButStep().setToolTipText("Execute une seule transition");
		}
		if(src == view.getButStep2()){
			view.getButStep2().setToolTipText("Lance la machine jusqu'à un état pause");
		}
	}
	
	/* --- Unused --- */
	@Override
	public void mouseClicked(MouseEvent e) {;}
	@Override
	public void mousePressed(MouseEvent e) {;}
	@Override
	public void mouseReleased(MouseEvent e) {;}
	@Override
	public void mouseExited(MouseEvent e) {;}
	/* -------------- */
	
	
	/**
	 * Setup the tape with the given string in the field and the first state
	 * This field can't be empty
	 */
	private void init(){
		/* --- IHM --- */
		String input = view.getInputField().getText();
		if(!input.equals("")) 
			tape.initTape(input);
		else 
			JOptionPane.showMessageDialog(view, "Veuillez inscrire dans le champ ci-dessous le ruban initial.");

		/* --- Data --- */
		lect = 0;
		started = true;
		ended = false;
		currentState = data.getInitState();
		currentChar = tape.getChar(lect);
		currentTrans = data.getTransitionFromSym(currentChar, currentState);
		
		view.getTable().setRowSelectionInterval(data.getTrans().indexOf(currentTrans), data.getTrans().indexOf(currentTrans));
	}
	
	/**
	 * Reset the machine at the begining, ready to start again.
	 */
	private void end(){
		ended = true;
		started = false;
		view.getButStart().setEnabled(true);
		view.getTable().getSelectionModel().clearSelection();
	}
	
}
