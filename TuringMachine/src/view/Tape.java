package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * Panel displaying the entire tape 
 */
public class Tape extends JPanel {

	private static final long serialVersionUID = 1L;
	/**
	 * The list of all JLabel of the tape
	 */
	private ArrayList<JLabel> labelList;
	private static final Color TAPE_BORDER_COLOR = new Color(0x7c7c7c);
	/**
	 * The color of the head
	 */
	private Color head_color;
	/**
	 * The number of empty cases at the end of the tape
	 */
	private int nbCases;
	private JPanel numberCase;
	private JPanel caseLabel;

	/**
	 * Init default values (Head color to yellow, nbcases to 50)<br>
	 * Add 50 empty cases to the tape
	 */
	public Tape(){
		this.head_color = Color.YELLOW;
		this.nbCases = 50;
		
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		
		caseLabel = new JPanel();
		numberCase = new JPanel();
		
		caseLabel.setLayout(new GridLayout());
		numberCase.setLayout(new GridLayout());
		labelList = new ArrayList<JLabel>();
		
		this.add(numberCase);
		this.add(caseLabel);
		
		this.setupEmptyTape();
	}
	
	/**
	 * Initialize the tape with the given word and put the head to the first case
	 * @param s The initial word to display on the tape
	 */
	public void initTape(String s){
		
		//Reset all
		labelList = new ArrayList<JLabel>();
		caseLabel.removeAll();
		numberCase.removeAll();
		
		//Add each character of the initial word to the tape
		for(int i=0;i<s.length();i++){
			Character c = s.charAt(i);
			//The character label
			JLabel addLabel = new JLabel();
			addLabel.setOpaque(true);
			addLabel.setPreferredSize(new Dimension(30,30));
			addLabel.setText(c.toString());
			addLabel.setBorder(BorderFactory.createMatteBorder(0,0,0,1,TAPE_BORDER_COLOR));
			addLabel.setHorizontalAlignment(JLabel.CENTER);
			labelList.add(addLabel);
			caseLabel.add(addLabel);
			//The index of the case
			JLabel num = new JLabel(""+(i+1));
			num.setPreferredSize(new Dimension(10,10));
			num.setOpaque(true);
			num.setForeground(Color.GRAY);
			num.setHorizontalAlignment(JLabel.CENTER);
			num.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.BLACK));
			numberCase.add(num);
		}
		//Add 50 blank symbol at the end
		this.setupEmptyTape();
		this.setHead(0);
		
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Set the background color of the label by the default one
	 * @param pos The label's position
	 */
	public void setDefaultColor(int pos){
		JLabel l = labelList.get(pos);
		l.setBackground(UIManager.getColor ("Panel.background"));
	}
	
	/**
	 * Set the head to the given position
	 * @param pos The position of the head
	 */
	public void setHead(int pos){
		JLabel l = labelList.get(pos);
		l.setBackground(head_color);
	}
	
	/**
	 * Return the character of a given case of the tape
	 * @param pos The index of the case
	 * @return Return the associated character
	 */
	public Character getChar(int pos){
		if(pos>labelList.size()) throw new IllegalArgumentException("There is no label to this position");
		JLabel l = labelList.get(pos);
		return l.getText().charAt(0);
	}
	
	/**
	 * Write a new character on a case
	 * @param pos The index of the case to modify
	 * @param c The new character to write
	 */
	public void setChar(int pos, Character c){
		JLabel l = labelList.get(pos);
		l.setText(c.toString());
	}
	
	/**
	 * Reset the tape and add empty cases
	 */
	public void reset(){
		labelList = new ArrayList<JLabel>();
		caseLabel.removeAll();
		numberCase.removeAll();
		this.setupEmptyTape();
	}
	
	/**
	 * Add empty cases at the end of the tape
	 */
	private void setupEmptyTape(){
		int tmp = labelList.size();
		for(int j=tmp;j<(tmp+nbCases);j++){
			JLabel empty = new JLabel("_");
			empty.setOpaque(true);
			empty.setPreferredSize(new Dimension(30,30));
			empty.setBorder(BorderFactory.createMatteBorder(0,0,0,1,TAPE_BORDER_COLOR));
			empty.setHorizontalAlignment(JLabel.CENTER);
			labelList.add(empty);
			caseLabel.add(empty);
			JLabel num = new JLabel(""+(j+1));
			num.setPreferredSize(new Dimension(10,10));
			num.setOpaque(true);
			num.setForeground(Color.GRAY);
			num.setHorizontalAlignment(JLabel.CENTER);
			num.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.BLACK));
			numberCase.add(num);
		}
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Change the head color and update it
	 * @param col The new head's color
	 * @param posHead The position of the head
	 */
	public void setHeadColor(Color col, int posHead){
		head_color = col;
		this.setHead(posHead);
	}
	
	/**
	 * Set the number of empty cases at the end of the tape
	 * @param nb The number of empty cases
	 */
	public void setNbCases(int nb){
		nbCases = nb;
	}
}
