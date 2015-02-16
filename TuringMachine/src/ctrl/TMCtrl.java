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
	private int lect;
	private String currentState;
	private Tape tape;
	private boolean ended,started;
	
	public TMCtrl(TMView v, Machine d){
		view = v;
		data = d;
		lect = 0;
		ended = false;
		started = false;
		currentState = data.getInitState();
		tape = view.getTapePanel();
	}
	
	/* --- Actions --- */
	
	private void startButton(){
		ended = false;
		new Thread(){
            public void run(){
            	while(!ended){
        			TMCtrl.this.doTransition();
        			try {
						Thread.sleep(800); //Can define a speed
					} catch (InterruptedException e) {
					}
        		}
            }
        }.start();
	}
	
	
	private void startStepTM(){
		TMCtrl.this.doTransition();
	}
	
	private void doTransition(){
		Character currentChar = tape.getChar(lect);
		Transition t = data.nextAction(currentChar, currentState);
		//Overwrite the new symbole
		tape.setChar(lect, t.getNewSymbole());
		//Set the direction of the head
		if(t.getDirection().equals("r")){
			tape.setHead(lect);
			lect++;
		}
		else if(lect != 0){
			tape.setHead(lect);
			lect--;
		}
		tape.setHead(lect);
		view.getTable().setRowSelectionInterval(data.getTrans().indexOf(t), data.getTrans().indexOf(t));
		
		if(data.isAccept(t.getNextState())){
			this.end();
			JOptionPane.showMessageDialog(view, "Etat acceptant !");
		}
		else if(data.isReject(t.getNextState())){
			JOptionPane.showMessageDialog(view, "Etat rejetant !");
		}
		else{
			//Set the new state
			currentState = t.getNextState();
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
			//TODO:Lauch machine loop
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
			//TODO:Reset all
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
		started = true;
		ended = false;
		currentState = data.getInitState();

		String input = view.getInputField().getText();
		if(!input.equals("")) 
			tape.initTape(input);
		else 
			JOptionPane.showMessageDialog(view, "Veuillez inscrire dans le champ ci-dessous le ruban initial.");
	}
	
	private void end(){
		lect = 0;
		ended = true;
		started = false;
	}
	
}
