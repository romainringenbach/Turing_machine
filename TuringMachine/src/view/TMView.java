package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;

import ctrl.TMListener;
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
	private DefaultListModel<Transition> listModel;
	private JPanel resetPanel;
	private TransitionTableModel model;
	private JTable table;
	
	public TMView(){
		super();
		
		data = Machine.getInstance();
		this.setResizable(false);
		this.init();
		this.setTitle("Turing Machine");
		this.setListeners();
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void init(){
		//Referrence Size : 600 x 400
		
		//Back
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(5,5));
		mainPanel.setBorder(new EmptyBorder(10,10,10,10));
		
		//North
		tapePanel = new Tape();
		tapeScrollPane = new JScrollPane(tapePanel);
		tapeScrollPane.setMinimumSize(new Dimension(575,50));
		tapeScrollPane.setPreferredSize(new Dimension(575,50));
		tapeScrollPane.setMaximumSize(new Dimension(575,50));
		tapeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		Border black = BorderFactory.createLineBorder(Color.black);
		tapeScrollPane.setBorder(black);
		
		//East
		model = new TransitionTableModel(data.getTrans());
		table = new JTable(model);
		table.setShowGrid(false);
		table.setEnabled(false);
		table.setIntercellSpacing(new Dimension(0,0));
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, centerRenderer);
		table.setFillsViewportHeight(true);
		
		eastScrollPane = new JScrollPane(table);
		eastScrollPane.setMinimumSize(new Dimension(240,290));
		eastScrollPane.setPreferredSize(new Dimension(240,290));
		eastScrollPane.setMaximumSize(new Dimension(240,290));
		
		//West
		westPanel = new JPanel();
		westPanel.setMinimumSize(new Dimension(330,250));
		westPanel.setPreferredSize(new Dimension(330,250));
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.PAGE_AXIS));
		westPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		butStart = new JButton("Démarrer");
		butStart.setPreferredSize(new Dimension(125,25));
		butStop = new JButton("Arrêter");
		butStop.setPreferredSize(new Dimension(125,25));
		butStep = new JButton("Etat par état");
		butStep.setPreferredSize(new Dimension(125,25));
		butStep2 = new JButton("Etape par étape");
		butStep2.setPreferredSize(new Dimension(125,25));
		butReset = new JButton("Remise à zéro");
		butReset.setPreferredSize(new Dimension(125,25));
		
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
		
		//First lign of button (Start + Stop)
		startPanel.add(Box.createHorizontalGlue());
		startPanel.add(butStart);
		startPanel.add(Box.createRigidArea(new Dimension(10,10)));
		startPanel.add(butStop);
		startPanel.add(Box.createHorizontalGlue());
		//Second lign of button(Step + Step2)
		stepPanel.add(Box.createHorizontalGlue());
		stepPanel.add(butStep);
		stepPanel.add(Box.createRigidArea(new Dimension(10,10)));
		stepPanel.add(butStep2);
		stepPanel.add(Box.createHorizontalGlue());
		//Third lign of button (Reset)
		resetPanel.add(Box.createHorizontalGlue());
		resetPanel.add(butReset);
		resetPanel.add(Box.createHorizontalGlue());
		
		westPanel.add(Box.createRigidArea(new Dimension(10,10)));
		westPanel.add(leftAlign);
		westPanel.add(inputField);
		westPanel.add(Box.createRigidArea(new Dimension(10,10)));
		westPanel.add(startPanel);
		westPanel.add(Box.createRigidArea(new Dimension(10,10)));
		westPanel.add(stepPanel);
		westPanel.add(Box.createRigidArea(new Dimension(10,10)));
		westPanel.add(resetPanel);
		westPanel.add(Box.createVerticalGlue());
		
		
		mainPanel.add(westPanel, BorderLayout.WEST);
		mainPanel.add(eastScrollPane, BorderLayout.EAST);
		mainPanel.add(tapeScrollPane,BorderLayout.NORTH);
		this.add(mainPanel);
	}
	
	private void setListeners(){
		TMListener listener = new TMListener(this);
		inputField.addKeyListener(listener);
		
		butStart.addActionListener(listener);
		butStop.addActionListener(listener);
		butStep.addActionListener(listener);
		butStep2.addActionListener(listener);
		butReset.addActionListener(listener);
		
		butStart.addMouseListener(listener);
		butStop.addMouseListener(listener);
		butStep.addMouseListener(listener);
		butStep2.addMouseListener(listener);
		
		this.addWindowListener(listener);
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

	public JTable getTable() {
		return table;
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