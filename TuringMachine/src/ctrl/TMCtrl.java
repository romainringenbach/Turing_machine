package ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.TMView;
import data.TMData;

public class TMCtrl implements MouseListener, KeyListener, ActionListener{

	private TMView view;
	private TMData data;
	
	public TMCtrl(TMView v, TMData d){
		view = v;
		data = d;
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER && e.getSource()==view.getInputField()){
			String input = view.getInputField().getText();
			view.getTapeLabel().setText(input);
		}
	}

	public void keyReleased(KeyEvent arg0) {;}

	public void keyTyped(KeyEvent arg0) {;}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == view.getButStart()){
			String input = view.getInputField().getText();
			view.getTapeLabel().setText(input);
			data.run();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {;}

	@Override
	public void mousePressed(MouseEvent e) {;}

	@Override
	public void mouseReleased(MouseEvent e) {;}

	@Override
	public void mouseEntered(MouseEvent e) {
		Object src = e.getSource();
		if(src == view.getButStep()){
			view.getButStep().setToolTipText("Test");
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {;}
}
