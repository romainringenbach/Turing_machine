package ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import view.TMView;
import data.Machine;

public class TMListener implements ActionListener, KeyListener, MouseListener, WindowListener{
	
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
		if(e.getKeyCode() == KeyEvent.VK_ENTER && e.getSource()==view.getInputField()){
			ctrl.init();
		}
	}
	
	/* --- Unused --- */
	@Override
	public void keyReleased(KeyEvent arg0) {;}
	@Override
	public void keyTyped(KeyEvent arg0) {;}
	/* -------------- */
	
		/* ------------------- */	

		/* --- ActionListener --- */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == view.getButStart()){
			if(!ctrl.started) ctrl.init();
			ctrl.startButton();
		}
		if(src == view.getButStep()){
			if(!ctrl.started) ctrl.init();
			ctrl.startStepButton();
		}
		if(src == view.getButStep2()){
			ctrl.init();
			//TODO:Lauch machine until stop state
		}
		if(src == view.getButStop()){
			ctrl.stopButton();
		}
		if(src == view.getButReset()){
			view.getInputField().setText("");
			ctrl.getTape().reset();
			view.getTable().getSelectionModel().clearSelection();
		}
	}
	
		/* --------------------- */

		/* --- MouseListener --- */		
	
	@Override
	public void mouseEntered(MouseEvent e) {
		Object src = e.getSource();
		if(src == view.getButStart()){
			view.getButStart().setToolTipText("Lance la machine jusqu'à la fin du programme ou un appui sur le bouton 'Arrêter'");
		}
		if(src == view.getButStop()){
			view.getButStop().setToolTipText("Arrête la machine sur l'état actuel");
		}
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
	
		/* --------------------- */

		/* --- WindowListener --- */
	
	@Override
	public void windowClosing(WindowEvent e) {
		//Stop the thread when closing the window
		ctrl.end();
	}
	
	/* --- Unused --- */
	@Override
	public void windowOpened(WindowEvent e) {;}
	@Override
	public void windowClosed(WindowEvent e) {;}
	@Override
	public void windowIconified(WindowEvent e) {;}
	@Override
	public void windowDeiconified(WindowEvent e) {;}
	@Override
	public void windowActivated(WindowEvent e) {;}
	@Override
	public void windowDeactivated(WindowEvent e) {;}
	/* -------------- */

		/* ---------------------- */
	
}
