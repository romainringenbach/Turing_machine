package ctrl;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JOptionPane;
import javax.swing.JViewport;

import view.TMView;
import view.Tape;
import data.Machine;
import data.Transition;
import data.TuringIO;

public class TMCtrl{

	private TMView view;
	private Machine data;
	private Tape tape;
	/**
	 * [Start mode]
	 * The delay between to transition
	 */
	private int speed;
	
	/**
	 * Indicates the position of the head on the tape
	 */
	private int lect;
	
	/**
	 * The state of the program in a given moment
	 */
	private String currentState;
	/**
	 * The current reading character
	 */
	private Character currentChar;
	/**
	 * The transition just done before
	 */
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
	 * True if the machine is running, false if paused
	 */
	private boolean running;
	
	/**
	 * True if the machine must run in 'stop mode'
	 */
	public boolean stop;
	
	private JViewport vport;
	/**
	 * The next state of the current transition
	 */
	private String nextS;
	
	/**
	 * Launch the program
	 * @param args You know what it is, Java.
	 */
	public static void main(String[] args) {
		new TMView();
	}
	
	/**
	 * Initialize attributes to default values
	 * @param v The main view
	 */
	public TMCtrl(TMView v){
		view = v;
		try {
			data = Machine.getMachine(TuringIO.getLOAD_PATH());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
			System.exit(0); //Close the window
		}
		lect = 0;
		speed = 300;
		ended = false;
		started = false;
		running = false;
		stop = false;
		tape = view.getTapePanel();
		currentState = data.getInitState();
		vport = view.getScrollTape().getViewport();
	}
	
	/* --- Actions --- */
	
	/**
	 * Launch the machine. Don't stop before end.
	 * Can be stoped if :
	 * <ul>
	 *  <li>ended = false : the program reached a final state</li>
	 *  <li>running = true : the program is stoped (but not ended)</li>
	 * </ul>
	 * @param st True if the program must be run in Stop mode
	 */
	public void startButton(boolean st){
		if(!started){
			this.init();
		}
		stop = st;
		if(view.getInputField().getText() != ""){
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
	public void stepButton(){
		if(!started){
			this.init();
			started = true;
			view.getInputField().setEnabled(false);
		}
		if(!ended){
			this.doTransition();
		}
	}
	
	/**
	 * Launch the machine until a stop state
	 */
	public void step2Button(){
		if(!started){
			this.init();
		}
		this.startButton(true);
	}

	/**
	 * Stop the running program [running = false]
	 */
	public void stopButton(){
		if(started){
			running = false;
			view.getInputField().setEnabled(true);
			view.getButStart().setEnabled(true);
		}
	}
	
	/**
	 * Stop the program and
	 * reset all the Turing Machine
	 */
	public void resetButton(){
		this.stopButton();
		tape.reset();
		view.getInputField().setText("");
		view.getTable().getSelectionModel().clearSelection();
		view.getStateLabel().setBackground(Color.WHITE);
		view.getStateLabel().setForeground(Color.BLACK);
		view.setConfigField("");
		view.setStateLabel("State");
		this.end();
	}
	
	
	/**
	 * Do the next transition of the machine<br>
	 * Update the GUI<br>
	 * Check if the next state is a final one<br>
	 * [Stop mode]<br>
	 * Stop the program if reaching a stop state
	 */
	private void doTransition(){
		view.setStateLabelColor(Color.WHITE);
		//Scroll the panel to the head
		
		//For unknown reasons, the autoscroll of the transition table make some trouble in the "start" mode.
		//view.getScrollTrans().getVerticalScrollBar().setValue(data.getTrans().indexOf(currentTrans)*12);
		
		//Autoscroll the tape to the head position (30 is the case's size)
		if(lect >= 9)
			vport.setViewPosition(new Point(lect*30-8*30,0));
		
		//Set the current elements of the machine
		currentChar = tape.getChar(lect);
		currentTrans = data.getTransitionFromSym(currentChar, currentState);
		
		//Update the old head
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
			this.end();
			JOptionPane.showMessageDialog(view, "Etat acceptant !");
		}
		else if(data.isReject(nextS)){
			//If the state is 'reject'
			view.setStateLabel(nextS);
			view.getStateLabel().setBackground(Color.RED);
			view.getStateLabel().setForeground(Color.WHITE);
			this.end();
			JOptionPane.showMessageDialog(view, "Etat rejetant !");
		}
		else{
			//Set the new state
			currentChar = tape.getChar(lect);
			currentState = currentTrans.getNextState();
			currentTrans = data.getTransitionFromSym(currentChar, currentState);
			view.getTable().setRowSelectionInterval(data.getTrans().indexOf(currentTrans), data.getTrans().indexOf(currentTrans));
			view.setStateLabel(currentState);
			this.setConfig();
		}
		if(stop && data.isStop(nextS)){
			//If stop is enable and state is stop, stop the program
			view.setStateLabelColor(Color.YELLOW);
			this.stopButton();
		}
	}
	
	/**
	 * Set the current configuration on the GUI<br>
	 * Add the configuration to the configuration list
	 */
	private void setConfig(){
		/* A configuration is the word at the left of the head,
		   the current state, and the remaining letters (from head to right) */
		
		//Word before the head
		String first = "";
		//Word from the head, up to the end
		String last = "";
		for(int i=0;i<lect;i++){
			first += view.getTapePanel().getChar(i).toString()+" ";
		}
		boolean end = false;
		for(int j=lect;!end;j++){
			Character c = view.getTapePanel().getChar(j);
			if(c.equals('_')) end = true;
			last += " "+c.toString();
		}
		String config = first+currentState+last;
		view.setConfigField(config);
		data.addConfig(config);
	}
	
	/**
	 * Setup the tape with the given string in the field and the first state<br>
	 * This field can't be empty
	 */
	public void init(){
		//Initialise only if the programm is not already started
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
				//Reset all the list of configurations
				data.resetConfigurations();
				this.setConfig();
			}
			else { //The input field can't be empty
				JOptionPane.showMessageDialog(view, "Veuillez inscrire dans le champ ci-dessous le ruban initial.");
			}
	}
	
	/**
	 * Indicate the machine reached a final state (accept or reject)<br>
	 * Reset the machine at the beginning, ready to start again.
	 */
	private void end(){
		ended = true;
		started = false;
		running = false;
		stop = false;
		view.getButStart().setEnabled(true);
		view.getInputField().setEnabled(true);
		TuringIO.getInstance().saveConfigurations(data.getConfigurations());
	}
	/* --------------- */
	
	public Machine getMachine(){
		return data;
	}
	
	public int getLect(){
		return lect;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	/**
	 * Set the new delay between two transitions
	 * @param s The new delay
	 */
	public void setSpeed(int s){
		speed = s;
	}
}
