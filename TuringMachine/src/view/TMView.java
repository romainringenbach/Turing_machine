package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import ctrl.TMCtrl;
import data.Machine;
import data.Transition;

public class TMView extends JFrame{


	public static void main(String[] args) {
		new TMView();
	}
	
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private Tape tapePanel;
	private JLabel tapeLabel;
	private JScrollPane tapeScrollPane;
	private JPanel northPanel;
	private JPanel eastPanel;
	private JScrollPane eastScrollPane;
	private JPanel westPanel;
	private JButton butStart;
	private JButton butStop;
	private JButton butStep;
	private JButton butReset;
	private JButton butStep2;
	private JLabel inputLabel;
	private JTextField inputField;
	private JPanel startPanel;
	private JPanel stepPanel;
	private Machine data;
	private JList<Transition> listTransition;
	private DefaultListModel<Transition> listModel;
	private JPanel resetPanel;
	
	public TMView(){
		super();
		
		data = Machine.getInstance();
		this.setMinimumSize(new Dimension(600,400));
		this.setResizable(false);
		this.load();
		this.init();
		this.setTitle("Turing Machine");
		this.setListeners();
		this.setLocation(200,200);
		this.setVisible(true);
	}
	
	private void init(){
		//Back
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(5,5,5,5));
		
		//North
		northPanel = new JPanel();
		Border black = BorderFactory.createLineBorder(Color.black);
		tapePanel = new Tape();
		tapeScrollPane = new JScrollPane(tapePanel);
		tapeScrollPane.setMinimumSize(new Dimension(570,50));
		tapeScrollPane.setPreferredSize(new Dimension(570,50));
		tapeScrollPane.setMaximumSize(new Dimension(570,50));
		tapeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		tapeScrollPane.setBorder(black);

		northPanel.add(tapeScrollPane);
		
		//East
		eastPanel = new JPanel();
		eastPanel.setMinimumSize(new Dimension(200,250));
		eastPanel.setPreferredSize(new Dimension(200,250));
		eastPanel.setMaximumSize(new Dimension(200,250));
		eastPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),"List",0,0,new Font("Arial", 0, 15)));
		
		listTransition = new JList<Transition>();
		listModel = new DefaultListModel<Transition>();
		/*int size = data.getStates().size();
		/for(int index=0; index<size; index++){
			//TODO:
		    listModel.addElement(data.getStates().get(index));
		}
		listTransition.setModel(listModel);
		*/
		eastScrollPane = new JScrollPane(listTransition);
		
		eastScrollPane.setMinimumSize(new Dimension(190,270));
		eastScrollPane.setPreferredSize(new Dimension(190,270));
		eastScrollPane.setMaximumSize(new Dimension(190,270));
		
		//West
		westPanel = new JPanel();
		westPanel.setMinimumSize(new Dimension(380,250));
		westPanel.setPreferredSize(new Dimension(380,250));
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.PAGE_AXIS));
		westPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),"Menu",0,0,new Font("Arial", 0, 15)));
		
		butStart = new JButton("Démarrer");
		butStart.setPreferredSize(new Dimension(130,25));
		butStop = new JButton("Arrêter");
		butStop.setPreferredSize(new Dimension(130,25));
		butStep = new JButton("Etat par état");
		butStep.setPreferredSize(new Dimension(130,25));
		butStep2 = new JButton("Etape par étape");
		butStep2.setPreferredSize(new Dimension(130,25));
		butReset = new JButton("Remise à zéro");
		butReset.setPreferredSize(new Dimension(130,25));
		
		inputLabel = new JLabel("Ruban initial");
		JPanel leftAlign = new JPanel();
		leftAlign.setLayout(new BoxLayout(leftAlign,BoxLayout.LINE_AXIS));
		leftAlign.add(inputLabel);
		leftAlign.add(Box.createHorizontalGlue());
		inputField = new JTextField();
		inputField.setMinimumSize(new Dimension(380,30));
		inputField.setPreferredSize(new Dimension(380,30));
		inputField.setMaximumSize(new Dimension(380,30));
		
		startPanel = new JPanel();
		startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.LINE_AXIS));
		
		stepPanel = new JPanel();
		stepPanel.setLayout(new BoxLayout(stepPanel, BoxLayout.LINE_AXIS));
		
		resetPanel = new JPanel();
		resetPanel.setLayout(new BoxLayout(resetPanel, BoxLayout.LINE_AXIS));
		
		startPanel.add(Box.createHorizontalGlue());
		startPanel.add(butStart);
		startPanel.add(Box.createHorizontalGlue());
		startPanel.add(butStop);
		startPanel.add(Box.createHorizontalGlue());
		
		stepPanel.add(Box.createHorizontalGlue());
		stepPanel.add(butStep);
		stepPanel.add(Box.createHorizontalGlue());
		stepPanel.add(butStep2);
		stepPanel.add(Box.createHorizontalGlue());
		
		resetPanel.add(Box.createHorizontalGlue());
		resetPanel.add(butReset);
		resetPanel.add(Box.createHorizontalGlue());
		
		westPanel.add(Box.createRigidArea(new Dimension(10,10)));
		westPanel.add(leftAlign);
		westPanel.add(inputField);
		westPanel.add(Box.createRigidArea(new Dimension(20,20)));
		westPanel.add(startPanel);
		westPanel.add(Box.createRigidArea(new Dimension(20,20)));
		westPanel.add(stepPanel);
		westPanel.add(Box.createRigidArea(new Dimension(20,20)));
		westPanel.add(resetPanel);
		westPanel.add(Box.createVerticalGlue());
		
		eastPanel.add(eastScrollPane);
		
		mainPanel.add(westPanel, BorderLayout.WEST);
		mainPanel.add(eastPanel, BorderLayout.EAST);
		mainPanel.add(northPanel,BorderLayout.NORTH);
		this.add(mainPanel);
	}
	
	private void load(){
		//TODO: Load data
	}
	
	private void setListeners(){
		TMCtrl listener = new TMCtrl(this, data);
		inputField.addKeyListener(listener);
		butStart.addActionListener(listener);
		butStep.addActionListener(listener);
		butStep2.addActionListener(listener);
		butReset.addActionListener(listener);
		butStep.addMouseListener(listener);
		butStep2.addMouseListener(listener);
	}
	
	public JButton getButStart() {
		return butStart;
	}

	public JButton getButStop() {
		return butStop;
	}

	public JButton getButStep() {
		return butStep;
	}
	
	public JButton getButStep2() {
		return butStep2;
	}

	public JButton getButReset() {
		return butReset;
	}

	public DefaultListModel<Transition> getListModel() {
		return listModel;
	}

	public JTextField getInputField() {
		return inputField;
	}
	public JLabel getTapeLabel() {
		return tapeLabel;
	}

	public Tape getTapePanel() {
		return tapePanel;
	}
	
}