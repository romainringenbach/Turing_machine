package ctrl;

import java.awt.Color;
import java.awt.Point;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JViewport;

import view.TMView;
import view.Tape;
import data.Machine;
import data.Transition;

public class TMCtrl{

	private TMView view;
	private Machine data;
	private Tape tape;
	private int speed;
	
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
	 * True if the machine is already started and not finished
	 */
	public boolean started;
	
	/**
	 * True if the machine is in accept or reject state
	 */
	private boolean ended;
	
	/**
	 * True if the machine is running or paused
	 */
	private boolean running;
	
	/**
	 * True if the machine must run in 'stop mode'
	 */
	public boolean stop;
	
	/**
	 * True if the machine is ready to start (good input)
	 */
	private boolean ready;
	private JViewport vport;
	private String nextS;
	private JViewport vport2;
	
	public static void main(String[] args) {
		new TMView();
	}
	
	public TMCtrl(TMView v){
		view = v;
		data = new Machine();
		lect = 0;
		speed = 300;
		ended = false;
		started = false;
		running = false;
		stop = false;
		tape = view.getTapePanel();
		currentState = data.getInitState();
		vport = view.getScrollTape().getViewport();
		vport2 = view.getScrollTrans().getViewport();
	}
	
	/* --- Actions --- */
	
	/**
	 * Launch the machine. Don't stop before end.
	 */
	public void startButton(boolean st){
		stop = st;
		if(ready){
			started = true;
			running = true;
			view.getButStart().setEnabled(false);
			view.getInputField().setEnabled(false);
			new Thread(){
	            public void run(){
	            	while(!ended && running){
	        			try {
							Thread.sleep(speed);
							TMCtrl.this.doTransition();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	        		}
	            	return;
	            }
	        }.start();
		}
	}
	
	/**
	 * Do a single transition
	 */
	public void startStepButton(){
		started = true;
		view.getInputField().setEnabled(false);
		if(!ended){
			TMCtrl.this.doTransition();
		}
	}
	
	/**
	 * Stop the running program
	 */
	public void stopButton(){
		running = false;
		view.getInputField().setEnabled(true);
		view.getButStart().setEnabled(true);
	}
	
	/**
	 * Launch the machine until a stop state
	 */
	public void step2Button(){
		this.startButton(true);
	}
	
	public void resetButton(){
		view.getInputField().setText("");
		tape.reset();
		view.getTable().getSelectionModel().clearSelection();
		view.getStateLabel().setBackground(Color.WHITE);
		view.getStateLabel().setForeground(Color.BLACK);
		view.setConfigField("");
		view.setStateLabel("State");
		this.end();
	}
	
	
	/**
	 * Do the next transition of the machine
	 */
	private void doTransition(){
		view.setStateLabelColor(Color.WHITE);
		//Scroll the panel to the head
		view.getScrollTrans().getViewport().setViewPosition(new Point(0,data.getTrans().indexOf(currentTrans)*13));
		if(lect >= 9){
			vport.setViewPosition(new Point(lect*30-8*30,0));
		}
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
		
		nextS = currentTrans.getNextState();
		if(data.isAccept(nextS)){
			//If the state is 'accept'
			view.setStateLabel(nextS);
			view.getStateLabel().setBackground(new Color(0x00D915));
			view.getStateLabel().setForeground(Color.WHITE);
			JOptionPane.showMessageDialog(view, "Etat acceptant !");
			this.end();
		}
		else if(data.isReject(nextS)){
			//If the state is 'reject'
			view.setStateLabel(nextS);
			view.getStateLabel().setBackground(Color.RED);
			view.getStateLabel().setForeground(Color.WHITE);
			JOptionPane.showMessageDialog(view, "Etat rejetant !");
			this.end();
		}
		else{
			//Set the new state
			currentChar = tape.getChar(lect);
			currentState = currentTrans.getNextState();
			currentTrans = data.getTransitionFromSym(currentChar, currentState);
			view.getTable().setRowSelectionInterval(data.getTrans().indexOf(currentTrans), data.getTrans().indexOf(currentTrans));
			view.setStateLabel(currentState);
			setConfig();
		}
		if(stop && data.isStop(nextS)){
			//If stop is enable and state is stop, stop the program
			view.setStateLabelColor(Color.YELLOW);
			this.stopButton();
		}
		
	}
	
	
	private void setConfig(){
		String first = "";
		String last = "";
		for(int i=0;i<=lect;i++){
			first += view.getTapePanel().getChar(i).toString()+" ";
		}
		boolean end = false;
		for(int j=lect;!end;j++){
			Character c = view.getTapePanel().getChar(j);
			if(c.equals('_')) end = true;
			last += " "+c.toString();
		}
		view.setConfigField(first+currentState+last);
		if(ready)
		data.addConfig(first+currentState+last);
	}
	
	public void reset(){
		lect = 0;
		speed = 300;
		ended = false;
		started = false;
		running = false;
		stop = false;
		tape = view.getTapePanel();
		currentState = data.getInitState();
	}
	
	

	/**
	 * Setup the tape with the given string in the field and the first state
	 * This field can't be empty
	 */
	public boolean init(){
		boolean ret = true;
		//Initialise only if the programm is not already started
		if(!started){
			//Set the scroll bar to the left born
			if(vport.getViewPosition().getX() > 0)
				vport.setViewPosition(new Point(0,0));
			//State in black
			//view.getStateLabel().setForeground(Color.BLACK);
			String input = view.getInputField().getText();
			//Tape input can't be empty
			if(!input.equals("")){
				tape.initTape(input);
				lect = 0;
				ended = false;
				currentState = data.getInitState();
				currentChar = tape.getChar(lect);
				currentTrans = data.getTransitionFromSym(currentChar, currentState);
				
				view.setStateLabelColor(Color.WHITE);
				view.setStateLabel(currentState);
				view.getTable().setRowSelectionInterval(data.getTrans().indexOf(currentTrans), data.getTrans().indexOf(currentTrans));
				ready = true;
				this.setConfig();
			}
			else {
				JOptionPane.showMessageDialog(view, "Veuillez inscrire dans le champ ci-dessous le ruban initial.");
				ready = false;
			}
		}
		return ret;

	}
	
	/**
	 * Reset the machine at the begining, ready to start again.
	 */
	public void end(){
		ended = true;
		started = false;
		running = false;
		stop = false;
		view.getButStart().setEnabled(true);
		view.getInputField().setEnabled(true);
		data.getTuringIO().saveConfigurations(data.getConfigurations());
	}
	
	public String getSelectedRadioText(ButtonGroup buttonGroup) {
		String ret = "";
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
            	ret =  button.getText();
            }
        }
        return ret;
    }
	
	
	
	/* --------------- */
	
	
	public Tape getTape(){
		return tape;
	}
	
	public Machine getMachine(){
		return data;
	}
	
	public int getLect(){
		return lect;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public void setSpeed(int s){
		speed = s;
	}
}
