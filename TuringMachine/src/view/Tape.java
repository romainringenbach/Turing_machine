package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tape extends JPanel {

	private static final long serialVersionUID = 1L;
	private ArrayList<Character> symList;
	private ArrayList<JLabel> labelList;
	private static final Color TAPE_COLOR = new Color(0x7c7c7c);

	public Tape(){
		this.setLayout(new GridLayout());
		symList = new ArrayList<Character>();
		labelList = new ArrayList<JLabel>();
		this.setupEmptyTape();
	}
	
	public void initTape(String s){
		//Reset all
		symList = new ArrayList<Character>();
		labelList = new ArrayList<JLabel>();
		this.removeAll();
		
		for(int i=0;i<s.length();i++){
			Character c = s.charAt(i);
			symList.add(c);
			JLabel addLabel = new JLabel();
			addLabel.setOpaque(true);
			addLabel.setPreferredSize(new Dimension(30,30));
			addLabel.setText(c.toString());
			addLabel.setBorder(BorderFactory.createMatteBorder(0,0,0,1,TAPE_COLOR));
			addLabel.setHorizontalAlignment(JLabel.CENTER);
			labelList.add(addLabel);
			this.add(addLabel);
		}
		//Add 50 blank symbol at the end
		this.setupEmptyTape();
		this.setHead(0);
		
		this.revalidate();
		this.repaint();
	}
	
	public void setHead(int pos){
		JLabel l = labelList.get(pos);
		l.setBackground(Color.yellow);
	}
	
	public void reset(){
		symList = new ArrayList<Character>();
		labelList = new ArrayList<JLabel>();
		this.removeAll();
		this.setupEmptyTape();
	}
	
	private void setupEmptyTape(){
		for(int j=0;j<50;j++){
			JLabel empty = new JLabel();
			empty.setPreferredSize(new Dimension(30,30));
			empty.setBorder(BorderFactory.createMatteBorder(0,0,0,1,TAPE_COLOR));
			empty.setHorizontalAlignment(JLabel.CENTER);
			this.add(empty);
		}
		this.revalidate();
		this.repaint();
	}
}
