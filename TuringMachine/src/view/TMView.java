package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;

import ctrl.TMCtrl;
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
	private JMenuBar menuBar;
	private JMenu menu_fichier;
	private JMenu menu_exemples;
	private JMenu menu_param;
	private JMenuItem menu_charger;
	private JMenuItem menu_sauver;
	private JMenuItem menu_fermer;
	private JMenuItem menu_ex1;
	private JMenuItem menu_ex2;
	private JMenuItem menu_vitesse;
	private JMenu menu_couleur;
	private JMenu menu_cases;
	private JRadioButtonMenuItem menu_radio_jaune;
	private JRadioButtonMenuItem menu_radio_bleu;
	private JRadioButtonMenuItem menu_radio_rouge;
	private JRadioButtonMenuItem menu_radio_vert;
	private JRadioButtonMenuItem menu_radio_50;
	private JRadioButtonMenuItem menu_radio_100;
	private JRadioButtonMenuItem menu_radio_200;
	private ButtonGroup groupCouleur;
	private ButtonGroup groupCases;
	private TMCtrl ctrl;
	private TMListener listener;
	
	public TMView(){
		super();
		this.setResizable(false);
		this.setTitle("Turing Machine");
		this.init();
		//TODO:Load chosen file, not default one
		this.loadData();
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
		tapeScrollPane.setMinimumSize(new Dimension(575,70));
		tapeScrollPane.setPreferredSize(new Dimension(575,70));
		tapeScrollPane.setMaximumSize(new Dimension(575,70));
		tapeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		Border black = BorderFactory.createLineBorder(Color.black);
		tapeScrollPane.setBorder(black);
		
		//East
		table = new JTable();
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
		mainPanel.add(eastScrollPane, BorderLayout.EAST);
		
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
		westPanel.add(Box.createVerticalGlue());
		westPanel.add(configPanel);
		westPanel.add(Box.createRigidArea(new Dimension(10,10)));
		
		//Menu
		menuBar = new JMenuBar();
		
		menu_fichier = new JMenu("Fichier");
			menu_charger = new JMenuItem("Charger");
			menu_sauver = new JMenuItem("Sauver");
			menu_fermer = new JMenuItem("Fermer");
			
		menu_fichier.add(menu_charger);
		menu_fichier.add(menu_sauver);
		menu_fichier.add(menu_fermer);
			
		menu_exemples = new JMenu("Exemples");
			menu_ex1 = new JMenuItem("Exemple 1");
			menu_ex2 = new JMenuItem("Exemple 2");
			
		menu_exemples.add(menu_ex1);
		menu_exemples.add(menu_ex2);
			
		menu_param = new JMenu("Paramètres");
			menu_vitesse = new JMenuItem("Délais");
			menu_couleur = new JMenu("Couleur de tête");
				menu_radio_jaune = new JRadioButtonMenuItem("Jaune");
				menu_radio_jaune.setForeground(Color.YELLOW);
				menu_radio_bleu = new JRadioButtonMenuItem("Bleu");
				menu_radio_bleu.setForeground(Color.BLUE);
				menu_radio_rouge = new JRadioButtonMenuItem("Rouge");
				menu_radio_rouge.setForeground(Color.RED);
				menu_radio_vert = new JRadioButtonMenuItem("Vert");
				menu_radio_vert.setForeground(Color.GREEN);

			menu_couleur.add(menu_radio_jaune);
			menu_couleur.add(menu_radio_bleu);
			menu_couleur.add(menu_radio_rouge);
			menu_couleur.add(menu_radio_vert);
				
			menu_cases = new JMenu("Nombre de cases vides");
				menu_radio_50 = new JRadioButtonMenuItem("50");
				menu_radio_100 = new JRadioButtonMenuItem("100");
				menu_radio_200 = new JRadioButtonMenuItem("500");
				
			menu_cases.add(menu_radio_50);
			menu_cases.add(menu_radio_100);
			menu_cases.add(menu_radio_200);
				
		menu_param.add(menu_vitesse);
		menu_param.add(menu_couleur);
		menu_param.add(menu_cases);

		groupCouleur = new ButtonGroup();
		groupCouleur.add(menu_radio_jaune);
		groupCouleur.add(menu_radio_bleu);
		groupCouleur.add(menu_radio_rouge);
		groupCouleur.add(menu_radio_vert);
		menu_radio_jaune.setSelected(true);
	    
	    groupCases = new ButtonGroup();
	    groupCases.add(menu_radio_50);
	    groupCases.add(menu_radio_100);
	    groupCases.add(menu_radio_200);
	    menu_radio_50.setSelected(true);
	    
	    menuBar.add(menu_fichier);
	    menuBar.add(menu_exemples);
	    menuBar.add(menu_param);
		
		mainPanel.add(westPanel, BorderLayout.WEST);
		mainPanel.add(eastScrollPane, BorderLayout.EAST);
		mainPanel.add(tapeScrollPane,BorderLayout.NORTH);
		
		this.setJMenuBar(menuBar);
		this.add(mainPanel);
	}
	
	private void setListeners(){
		listener = new TMListener(this, ctrl);
		inputField.addKeyListener(listener);
		
		butStart.addActionListener(listener);
		butStop.addActionListener(listener);
		butStep.addActionListener(listener);
		butStep2.addActionListener(listener);
		butReset.addActionListener(listener);
		
		menu_charger.addActionListener(listener);
		menu_sauver.addActionListener(listener);
		menu_fermer.addActionListener(listener);
		menu_ex1.addActionListener(listener);
		menu_ex2.addActionListener(listener);
		menu_vitesse.addActionListener(listener);
		
		menu_radio_50.addItemListener(listener);
		menu_radio_100.addItemListener(listener);
		menu_radio_200.addItemListener(listener);
		
		menu_radio_jaune.addItemListener(listener);
		menu_radio_bleu.addItemListener(listener);
		menu_radio_rouge.addItemListener(listener);
		menu_radio_vert.addItemListener(listener);
		
		inputField.setToolTipText("Appuyer sur 'ENTER' pour initialiser le ruban");
		butStart.setToolTipText("Lance la machine jusqu'à la fin du programme ou un appui sur le bouton 'Arrêter'");
		butStop.setToolTipText("Arrête la machine sur l'état actuel");
		butStep.setToolTipText("Execute une seule transition");
		butStep2.setToolTipText("Lance la machine jusqu'à un état pause");
	}
	
	
	public void loadData(){
		ctrl = new TMCtrl(this);
		data = ctrl.getMachine();
		
		alphaLabel.setText("Alphabet : "+data.getTapeAlpha());
		
		model = new TransitionTableModel(data.getTrans());
		table.setModel(model);
		
		if(eastScrollPane != null)
		mainPanel.remove(eastScrollPane);
		
		eastScrollPane = new JScrollPane(table);
		eastScrollPane.setMinimumSize(new Dimension(240,290));
		eastScrollPane.setPreferredSize(new Dimension(240,290));
		eastScrollPane.setMaximumSize(new Dimension(240,290));
		mainPanel.add(eastScrollPane, BorderLayout.EAST);
		
		ctrl.resetButton();
		
		if(listener != null)
			listener.setCtrl(ctrl);
		
		mainPanel.revalidate();
		mainPanel.repaint();
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

	public JMenuItem getMenu_charger() {
		return menu_charger;
	}

	public JMenuItem getMenu_sauver() {
		return menu_sauver;
	}

	public JMenuItem getMenu_fermer() {
		return menu_fermer;
	}

	public JMenuItem getMenu_ex1() {
		return menu_ex1;
	}

	public JMenuItem getMenu_ex2() {
		return menu_ex2;
	}

	public JMenu getMenu_cases() {
		return menu_cases;
	}
	
	public JMenuItem getMenu_vitesse() {
		return menu_vitesse;
	}

	public JRadioButtonMenuItem getMenu_radio_jaune() {
		return menu_radio_jaune;
	}

	public JRadioButtonMenuItem getMenu_radio_bleu() {
		return menu_radio_bleu;
	}

	public JRadioButtonMenuItem getMenu_radio_rouge() {
		return menu_radio_rouge;
	}

	public JRadioButtonMenuItem getMenu_radio_vert() {
		return menu_radio_vert;
	}

	public JRadioButtonMenuItem getMenu_radio_50() {
		return menu_radio_50;
	}

	public JRadioButtonMenuItem getMenu_radio_100() {
		return menu_radio_100;
	}

	public JRadioButtonMenuItem getMenu_radio_200() {
		return menu_radio_200;
	}
	
	public ButtonGroup getGroupCases(){
		return groupCases;
	}
	
	public ButtonGroup getGroupCouleur(){
		return groupCouleur;
	}
	
	
}