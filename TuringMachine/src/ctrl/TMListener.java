package ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import view.TMView;
import data.Machine;

public class TMListener implements ActionListener, KeyListener{
	
	private TMView view;
	private TMCtrl ctrl;
	
	public TMListener(TMView v){
		this.view = v;
		this.ctrl = new TMCtrl(v,Machine.getInstance());
	}

	/* --- Listeners of view --- */

		/* --- KeyListener --- */	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getSource() == view.getInputField() && e.getKeyCode() == KeyEvent.VK_ENTER){
			ctrl.init();
		}
	}
	
	/* --- Unused --- */
	@Override
	public void keyReleased(KeyEvent e) {
		

	}
	@Override
	public void keyTyped(KeyEvent e) {
		Character c = e.getKeyChar();
		if(e.getSource() == view.getInputField() && !ctrl.getMachine().getTapeAlpha().contains(c)){
			e.consume();
		}
	}
	/* -------------- */
	
		/* ------------------- */	

		/* --- ActionListener --- */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == view.getButStart()){
			if(!ctrl.started){
				ctrl.init();
			}
			ctrl.startButton(false);
		}
		if(src == view.getButStep()){
			if(!ctrl.started) ctrl.init();
			ctrl.startStepButton();
		}
		if(src == view.getButStep2()){
			if(!ctrl.started){
				ctrl.init();
			}
			ctrl.step2Button();
		}
		if(src == view.getButStop()){
			ctrl.stopButton();
		}
		if(src == view.getButReset()){
			ctrl.resetButton();
		}
	}
		/* --------------------- */

}
