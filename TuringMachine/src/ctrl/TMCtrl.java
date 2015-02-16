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
	private Transition currentTransition;
	private Tape tape;
	
	public TMCtrl(TMView v, Machine d){
		view = v;
		data = d;
		lect = 0;
		tape = view.getTapePanel();
	} 
	
	/* --- Actions --- */
	
	private void doTransition(Transition t){
		
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
			this.init();
			//TODO:Lauch machine loop
		}
		if(src == view.getButStep()){
			this.init();
			//TODO:Lauch machine step by step
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
	
	
	private void init(){
		String input = view.getInputField().getText();
		if(!input.equals("")){
			tape.initTape(input);
		}
		else{
			JOptionPane.showMessageDialog(view, "Veuillez inscrire dans le champ ci-dessous le ruban initial.");
		}
	}
	
}
