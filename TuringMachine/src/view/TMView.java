package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import ctrl.TMListener;
import data.Machine;

public class TMView extends JFrame{

	public static void main(String[] args) {
		new TMView();
	}
	
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JPanel westPanel;
	private JPanel startPanel;
	private JPanel stepPanel;
	private JPanel resetPanel;
	private JPanel configPanel;
	private JLabel tapeLabel;
	private JLabel inputLabel;
	private JLabel alphaLabel;
	private JLabel stateLabel;
	private JScrollPane tapeScrollPane;
	private JScrollPane eastScrollPane;
	private JButton butStart;
	private JButton butStop;
	private JButton butStep;
	private JButton butStep2;
	private JButton butReset;
	private JTextField inputField;
	private JTextField configField;
	private JTable table;
	private TransitionTableModel model;
	private Tape tapePanel;

	private Machine data;
	
	public TMView(){
		super();
		data = Machine.getInstance();
		this.setResizable(false);
		this.init();
		this.setTitle("Turing Machine");
		this.setListeners();
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		inputLabel = new JLabel("Ruban initial");
		JPanel leftAlign = new JPanel();
		leftAlign.setLayout(new BoxLayout(leftAlign,BoxLayout.LINE_AXIS));
		leftAlign.add(Box.createRigidArea(new Dimension(8,8)));
		leftAlign.add(inputLabel);
		leftAlign.add(Box.createHorizontalGlue());
		inputField = new JTextField();
		inputField.setMinimumSize(new Dimension(310,30));
		inputField.setPreferredSize(new Dimension(310,30));
		inputField.setMaximumSize(new Dimension(310,30));

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
		
		alphaLabel = new JLabel();
		alphaLabel.setText("Alphabet : "+data.getTapeAlpha());
		JPanel leftAlign2 = new JPanel();
		leftAlign2.setLayout(new BoxLayout(leftAlign2,BoxLayout.LINE_AXIS));
		leftAlign2.add(Box.createRigidArea(new Dimension(8,8)));
		leftAlign2.add(alphaLabel);
		leftAlign2.add(Box.createHorizontalGlue());
		
		configField = new JTextField();
		configField.setMinimumSize(new Dimension(250,30));
		configField.setPreferredSize(new Dimension(250,30));
		configField.setMaximumSize(new Dimension(250,30));
		configField.setEnabled(false);
		configField.setDisabledTextColor(Color.BLACK);
		stateLabel = new JLabel("State");
		stateLabel.setMinimumSize(new Dimension(40,30));
		stateLabel.setPreferredSize(new Dimension(40,30));
		stateLabel.setMaximumSize(new Dimension(40,30));
		stateLabel.setOpaque(true);
		stateLabel.setBackground(Color.white);
		stateLabel.setHorizontalAlignment(JLabel.CENTER);
		stateLabel.setBorder(BorderFactory.createMatteBorder(1,1,1,1, new Color(0xB8CFE5)));
		
		startPanel = new JPanel();
		startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.LINE_AXIS));
		
		stepPanel = new JPanel();
		stepPanel.setLayout(new BoxLayout(stepPanel, BoxLayout.LINE_AXIS));
		
		resetPanel = new JPanel();
		resetPanel.setLayout(new BoxLayout(resetPanel, BoxLayout.LINE_AXIS));
		
		configPanel = new JPanel();
		configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.LINE_AXIS));
		
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
		//Forth lign with current state and configuration
		configPanel.add(Box.createHorizontalGlue());
		configPanel.add(configField);
		configPanel.add(Box.createHorizontalGlue());
		configPanel.add(stateLabel);
		configPanel.add(Box.createHorizontalGlue());
		
		westPanel.add(Box.createRigidArea(new Dimension(10,10)));
		westPanel.add(leftAlign);
		westPanel.add(inputField);
		westPanel.add(Box.createRigidArea(new Dimension(10,10)));
		westPanel.add(leftAlign2);
		westPanel.add(Box.createRigidArea(new Dimension(30,30)));
		westPanel.add(startPanel);
		westPanel.add(Box.createRigidArea(new Dimension(10,10)));
		westPanel.add(stepPanel);
		westPanel.add(Box.createRigidArea(new Dimension(10,10)));
		westPanel.add(resetPanel);
		westPanel.add(Box.createVerticalGlue());//RigidArea(new Dimension(10,10)));
		westPanel.add(configPanel);
		westPanel.add(Box.createRigidArea(new Dimension(10,10)));
		
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
		
		inputField.setToolTipText("Appuyer sur 'ENTER' pour initialiser le ruban");
		butStart.setToolTipText("Lance la machine jusqu'à la fin du programme ou un appui sur le bouton 'Arrêter'");
		butStop.setToolTipText("Arrête la machine sur l'état actuel");
		butStep.setToolTipText("Execute une seule transition");
		butStep2.setToolTipText("Lance la machine jusqu'à un état pause");
	}

	/* --- Getters ---*/
	
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
	
	public JScrollPane getScrollTape(){
		return tapeScrollPane;
	}
	
	public JTable getTable() {
		return table;
	}

	public JTextField getInputField() {
		return inputField;
	}
	
	public JLabel getStateLabel() {
		return stateLabel;
	}
	
	public void setStateLabel(String text) {
		stateLabel.setText(text);
	}
	
	public JTextField getConfigField(){
		return configField;
	}
	
	public void setConfigField(String s){
		configField.setText(s);
	}
	
	public JLabel getTapeLabel() {
		return tapeLabel;
	}

	public Tape getTapePanel() {
		return tapePanel;
	}
	
	
}