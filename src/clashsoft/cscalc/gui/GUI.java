package clashsoft.cscalc.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import clashsoft.cscalc.CSCalc;
import clashsoft.cscalc.gui.graph.Graph;

import com.alee.laf.WebLookAndFeel;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class GUI
{
	public static GUI			instance		= new GUI();
	
	public CSCalc				calc;
	
	public LookAndFeelInfo[]	lookAndFeels	= UIManager.getInstalledLookAndFeels();
	
	public JFrame				frame;
	
	public JTextPane			inputTextField;
	public JTextPane			resultTextField;
	
	public JTextField			commandTextField;
	public JTextPane			consoleTextField;
	
	public JTabbedPane			tabbedPane;
	public JPanel				panelCalculateTab;
	public JPanel				panelSettingsTab;
	public JPanel				panelDevTab;
	
	public JButton				buttonCalculate;
	
	public JPanel				panelNumpad;
	public JButton				button0;
	public JButton				button1;
	public JButton				button2;
	public JButton				button3;
	public JButton				button4;
	public JButton				button5;
	public JButton				button6;
	public JButton				button7;
	public JButton				button8;
	public JButton				button9;
	public JButton				buttonDecimalPoint;
	public JButton				buttonNegate;
	
	public JPanel				panelBasicOperations;
	public JButton				buttonAdd;
	public JButton				buttonSubstract;
	public JButton				buttonMultiply;
	public JButton				buttonDivide;
	public JButton				buttonRemainder;
	
	public JPanel				panelBinaryOperations;
	public JButton				buttonAND;
	public JButton				buttonOR;
	public JButton				buttonXOR;
	public JButton				buttonNOT;
	public JButton				buttonBitshiftRight;
	public JButton				buttonBitshiftRightU;
	public JButton				buttonBitshiftLeft;
	
	public JPanel				panelClear;
	public JButton				buttonCE;
	public JButton				buttonC;
	
	public JPanel				panelAdvancedOperations;
	public JButton				buttonPower;
	public JButton				buttonRoot;
	public JButton				buttonPI;
	public JButton				buttonE;
	
	public JPanel				panelLookAndFeel;
	public JPanel				panelDevSettings;
	
	public JButton				buttonFont;
	public JColorChooser		colorChooser;
	public JComboBox			comboBoxLAF;
	public JCheckBox			checkboxDevMode;
	public JLabel				labelDevModeLogging;
	public JRadioButton			radioButtonLogDebug;
	public JRadioButton			radioButtonLogExtended;
	public JRadioButton			radioButtonLogMinimal;
	
	public JPanel				panelDraw;
	public JPanel				panelDraw_Input;
	public Graph				canvasGraph;
	public JButton				buttonDraw;
	public JComboBox			comboBoxGraph;
	public JLabel				lblFx;
	
	public static void init()
	{
		instance.calc = CSCalc.instance;
		instance.initialize();
		instance.frame.setVisible(true);
	}
	
	/**
	 * Create the application.
	 */
	public GUI()
	{
		
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	private void initialize()
	{
		this.initLAF(this.calc.getLAF(), false);
		
		this.frame = new JFrame();
		this.frame.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				calc.window_keyTyped(e.getKeyChar());
			}
		});
		this.frame.setResizable(false);
		this.frame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				GUI.this.calc.exit();
			}
		});
		this.frame.setTitle(I18n.getString("GUI.frame.title")); //$NON-NLS-1$
		this.frame.setBounds(100, 100, 480, 450);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.tabbedPane = new JTabbedPane(SwingConstants.TOP);
		this.tabbedPane.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				setCurrentTab(tabbedPane.getSelectedIndex());
			}
		});
		this.tabbedPane.setBounds(0, 0, 480, 448);
		this.frame.getContentPane().add(this.tabbedPane);
		
		this.addPanels();
		this.addTextFields();
		
		this.calc.devInfo("Initializing GUI...", 2);
		
		this.addCalculateButton();
		this.addNumpadButtons();
		this.addBasicOperationButtons();
		this.addBinaryOperationButtons();
		this.addAdvancedOperationButtons();
		this.addClearButtons();
		
		this.addDraw();
		
		this.addSettings();
		
		this.calc.devInfo("Done Initializing GUI", 2);
	}
	
	private void addPanels()
	{
		// Tab Panels
		
		this.panelCalculateTab = new JPanel();
		this.panelCalculateTab.setLayout(null);
		this.tabbedPane.addTab(I18n.getString("GUI.panelCalculateTab.text"), null, this.panelCalculateTab, I18n.getString("GUI.panelCalculateTab.toolTipText")); //$NON-NLS-1$
		
		this.panelDraw = new JPanel();
		this.panelDraw.setLayout(new BorderLayout(0, 0));
		this.tabbedPane.addTab(I18n.getString("GUI.panelDrawTab.text"), null, this.panelDraw, I18n.getString("GUI.panelDrawTab.toolTipText"));
		
		this.panelSettingsTab = new JPanel();
		this.panelSettingsTab.setLayout(new BorderLayout(0, 0));
		this.tabbedPane.addTab(I18n.getString("GUI.panelSettingsTab.text"), null, this.panelSettingsTab, I18n.getString("GUI.panelSettingsTab.toolTipText")); ////$NON-NLS-1$
		
		this.panelDevTab = new JPanel();
		this.panelDevTab.setLayout(new BorderLayout(0, 0));
		this.tabbedPane.addTab(I18n.getString("GUI.panelDevTab.text"), null, this.panelDevTab, I18n.getString("GUI.panelDevTab.toolTipText")); //$NON-NLS-1$
		
		// Settings Panels
		
		this.panelLookAndFeel = new JPanel();
		this.panelLookAndFeel.setBorder(new TitledBorder(I18n.getString("GUI.panelLookAndFeel.borderTitle"))); //$NON-NLS-1$
		this.panelLookAndFeel.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("200px:grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("200px:grow"), }, new RowSpec[] { RowSpec.decode("24px"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("259px:grow"), }));
		this.panelSettingsTab.add(this.panelLookAndFeel, BorderLayout.CENTER);
		
		this.panelDevSettings = new JPanel();
		this.panelDevSettings.setBorder(new TitledBorder(I18n.getString("GUI.panelDevSettings.borderTitle"))); //$NON-NLS-1$
		this.panelDevSettings.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"), ColumnSpec.decode("default:grow"), FormFactory.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("default:grow"), }, new RowSpec[] { RowSpec.decode("23px"), RowSpec.decode("23px"), }));
		this.panelSettingsTab.add(this.panelDevSettings, BorderLayout.NORTH);
		
		// Calculate Panels
		
		this.panelNumpad = new JPanel();
		this.panelNumpad.setBounds(6, 110, 124, 176);
		this.panelCalculateTab.add(this.panelNumpad);
		this.panelNumpad.setBorder(new TitledBorder(I18n.getString("GUI.panelNumpad.borderTitle"))); //$NON-NLS-1$
		this.panelNumpad.setLayout(null);
		
		this.panelBasicOperations = new JPanel();
		this.panelBasicOperations.setBounds(142, 110, 124, 94);
		this.panelBasicOperations.setBorder(new TitledBorder(I18n.getString("GUI.panelBasicOperations.borderTitle"))); //$NON-NLS-1$
		this.panelBasicOperations.setLayout(null);
		this.panelCalculateTab.add(this.panelBasicOperations);
		
		this.panelAdvancedOperations = new JPanel();
		this.panelAdvancedOperations.setBounds(287, 216, 166, 70);
		this.panelAdvancedOperations.setLayout(null);
		this.panelAdvancedOperations.setBorder(new TitledBorder(I18n.getString("GUI.panelAdvancedOperations.borderTitle"))); //$NON-NLS-1$
		this.panelCalculateTab.add(this.panelAdvancedOperations);
		
		this.panelBinaryOperations = new JPanel();
		this.panelBinaryOperations.setBounds(287, 110, 166, 94);
		this.panelBinaryOperations.setLayout(null);
		this.panelBinaryOperations.setBorder(new TitledBorder(I18n.getString("GUI.panelBinaryOperations.borderTitle"))); //$NON-NLS-1$
		this.panelCalculateTab.add(this.panelBinaryOperations);
		
		this.panelClear = new JPanel();
		this.panelClear.setBounds(142, 216, 124, 70);
		this.panelClear.setBorder(new TitledBorder(I18n.getString("GUI.panelClear.borderTitle"))); //$NON-NLS-1$
		this.panelClear.setLayout(null);
		this.panelCalculateTab.add(this.panelClear);
	}
	
	private void addNumpadButtons()
	{
		this.button0 = new JButton(I18n.getString("GUI.button0.text")); //$NON-NLS-1$
		this.button0.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.button0_click();
			}
		});
		this.button0.setBounds(47, 140, 29, 29);
		this.panelNumpad.add(this.button0);
		
		this.button1 = new JButton(I18n.getString("GUI.button1.text")); //$NON-NLS-1$
		this.button1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.button1_click();
			}
		});
		this.button1.setBounds(6, 99, 29, 29);
		this.panelNumpad.add(this.button1);
		
		this.button2 = new JButton(I18n.getString("GUI.button2.text")); //$NON-NLS-1$
		this.button2.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.button2_click();
			}
		});
		this.button2.setBounds(47, 99, 29, 29);
		this.panelNumpad.add(this.button2);
		
		this.button3 = new JButton(I18n.getString("GUI.button3.text")); //$NON-NLS-1$
		this.button3.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.button3_click();
			}
		});
		this.button3.setBounds(88, 99, 29, 29);
		this.panelNumpad.add(this.button3);
		
		this.button4 = new JButton(I18n.getString("GUI.button4.text")); //$NON-NLS-1$
		this.button4.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.button4_click();
			}
		});
		this.button4.setBounds(6, 58, 29, 29);
		this.panelNumpad.add(this.button4);
		
		this.button5 = new JButton(I18n.getString("GUI.button5.text")); //$NON-NLS-1$
		this.button5.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.button5_click();
			}
		});
		this.button5.setBounds(47, 58, 29, 29);
		this.panelNumpad.add(this.button5);
		
		this.button6 = new JButton(I18n.getString("GUI.button6.text")); //$NON-NLS-1$
		this.button6.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.button6_click();
			}
		});
		this.button6.setBounds(88, 58, 29, 29);
		this.panelNumpad.add(this.button6);
		
		this.button7 = new JButton(I18n.getString("GUI.button7.text")); //$NON-NLS-1$
		this.button7.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.button7_click();
			}
		});
		this.button7.setBounds(6, 17, 29, 29);
		this.panelNumpad.add(this.button7);
		
		this.button8 = new JButton(I18n.getString("GUI.button8.text")); //$NON-NLS-1$
		this.button8.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.button8_click();
			}
		});
		this.button8.setBounds(47, 17, 29, 29);
		this.panelNumpad.add(this.button8);
		
		this.button9 = new JButton(I18n.getString("GUI.button9.text")); //$NON-NLS-1$
		this.button9.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.button9_click();
			}
		});
		this.button9.setBounds(88, 17, 29, 29);
		this.panelNumpad.add(this.button9);
		
		this.buttonDecimalPoint = new JButton(I18n.getString("GUI.buttonDecimalPoint.text")); //$NON-NLS-1$
		this.buttonDecimalPoint.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonDecimalPoint_click();
			}
		});
		this.buttonDecimalPoint.setToolTipText(I18n.getString("GUI.buttonDecimalPoint.toolTipText")); //$NON-NLS-1$
		this.buttonDecimalPoint.setBounds(88, 140, 29, 29);
		this.panelNumpad.add(this.buttonDecimalPoint);
		
		this.buttonNegate = new JButton(I18n.getString("GUI.buttonNegate.text")); //$NON-NLS-1$
		this.buttonNegate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonNegate_click();
			}
		});
		this.buttonNegate.setToolTipText(I18n.getString("GUI.buttonNegate.toolTipText")); //$NON-NLS-1$
		this.buttonNegate.setBounds(6, 140, 29, 29);
		this.panelNumpad.add(this.buttonNegate);
	}
	
	private void addBasicOperationButtons()
	{
		this.buttonAdd = new JButton(I18n.getString("GUI.buttonAdd.text")); //$NON-NLS-1$
		this.buttonAdd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonAdd_click();
			}
		});
		this.buttonAdd.setToolTipText(I18n.getString("GUI.buttonAdd.toolTipText")); //$NON-NLS-1$
		this.buttonAdd.setBounds(6, 17, 29, 29);
		this.panelBasicOperations.add(this.buttonAdd);
		
		this.buttonSubstract = new JButton(I18n.getString("GUI.buttonSubstract.text")); //$NON-NLS-1$
		this.buttonSubstract.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonSubstract_click();
			}
		});
		this.buttonSubstract.setToolTipText(I18n.getString("GUI.buttonSubstract.toolTipText")); //$NON-NLS-1$
		this.buttonSubstract.setBounds(47, 17, 29, 29);
		this.panelBasicOperations.add(this.buttonSubstract);
		
		this.buttonMultiply = new JButton(I18n.getString("GUI.buttonMultiply.text")); //$NON-NLS-1$
		this.buttonMultiply.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonMultiply_click();
			}
		});
		this.buttonMultiply.setToolTipText(I18n.getString("GUI.buttonMultiply.toolTipText")); //$NON-NLS-1$
		this.buttonMultiply.setBounds(6, 58, 29, 29);
		this.panelBasicOperations.add(this.buttonMultiply);
		
		this.buttonDivide = new JButton(I18n.getString("GUI.buttonDivide.text")); //$NON-NLS-1$
		this.buttonDivide.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonDivide_click();
			}
		});
		this.buttonDivide.setToolTipText(I18n.getString("GUI.buttonDivide.toolTipText")); //$NON-NLS-1$
		this.buttonDivide.setBounds(47, 58, 29, 29);
		this.panelBasicOperations.add(this.buttonDivide);
		
		this.buttonRemainder = new JButton(I18n.getString("GUI.buttonRemainder.text")); //$NON-NLS-1$
		this.buttonRemainder.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonRemainder_click();
			}
		});
		this.buttonRemainder.setToolTipText(I18n.getString("GUI.buttonRemainder.toolTipText")); //$NON-NLS-1$
		this.buttonRemainder.setBounds(88, 17, 29, 29);
		this.panelBasicOperations.add(this.buttonRemainder);
	}
	
	private void addBinaryOperationButtons()
	{
		this.buttonAND = new JButton(I18n.getString("GUI.buttonAND.text")); //$NON-NLS-1$
		this.buttonAND.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonAND_click();
			}
		});
		this.buttonAND.setToolTipText(I18n.getString("GUI.buttonAND.toolTipText")); //$NON-NLS-1$
		this.buttonAND.setBounds(6, 17, 29, 29);
		this.panelBinaryOperations.add(this.buttonAND);
		
		this.buttonOR = new JButton(I18n.getString("GUI.buttonOR.text")); //$NON-NLS-1$
		this.buttonOR.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonOR_click();
			}
		});
		this.buttonOR.setToolTipText(I18n.getString("GUI.buttonOR.toolTipText")); //$NON-NLS-1$
		this.buttonOR.setBounds(47, 17, 29, 29);
		this.panelBinaryOperations.add(this.buttonOR);
		
		this.buttonXOR = new JButton(CSCalc.XOR);
		this.buttonXOR.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonXOR_click();
			}
		});
		this.buttonXOR.setToolTipText(I18n.getString("GUI.buttonXOR.toolTipText")); //$NON-NLS-1$
		this.buttonXOR.setBounds(88, 17, 29, 29);
		this.panelBinaryOperations.add(this.buttonXOR);
		
		this.buttonNOT = new JButton(CSCalc.NOT);
		this.buttonNOT.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonNOT_click();
			}
		});
		this.buttonNOT.setToolTipText(I18n.getString("GUI.buttonNOT.toolTipText")); //$NON-NLS-1$
		this.buttonNOT.setBounds(129, 17, 29, 29);
		this.panelBinaryOperations.add(this.buttonNOT);
		
		this.buttonBitshiftRight = new JButton(I18n.getString("GUI.buttonBitshiftRight.text")); //$NON-NLS-1$
		this.buttonBitshiftRight.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonBitshiftRight_click();
			}
		});
		this.buttonBitshiftRight.setToolTipText(I18n.getString("GUI.buttonBitshiftRight.toolTipText")); //$NON-NLS-1$
		this.buttonBitshiftRight.setBounds(6, 58, 29, 29);
		this.panelBinaryOperations.add(this.buttonBitshiftRight);
		
		this.buttonBitshiftRightU = new JButton(I18n.getString("GUI.buttonBitshiftRightU.text")); //$NON-NLS-1$
		this.buttonBitshiftRightU.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonBitshiftRightU_click();
			}
		});
		this.buttonBitshiftRightU.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		this.buttonBitshiftRightU.setToolTipText(I18n.getString("GUI.buttonBitshiftRightU.toolTipText")); //$NON-NLS-1$
		this.buttonBitshiftRightU.setBounds(47, 58, 29, 29);
		this.panelBinaryOperations.add(this.buttonBitshiftRightU);
		
		this.buttonBitshiftLeft = new JButton(I18n.getString("GUI.buttonBitshiftLeft.text")); //$NON-NLS-1$
		this.buttonBitshiftLeft.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonBitshiftLeft_click();
			}
		});
		this.buttonBitshiftLeft.setToolTipText(I18n.getString("GUI.buttonBitshiftLeft.toolTipText")); //$NON-NLS-1$
		this.buttonBitshiftLeft.setBounds(88, 58, 29, 29);
		this.panelBinaryOperations.add(this.buttonBitshiftLeft);
	}
	
	private void addClearButtons()
	{
		this.buttonCE = new JButton(I18n.getString("GUI.buttonCE.text")); //$NON-NLS-1$
		this.buttonCE.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonC_click();
			}
		});
		this.buttonCE.setToolTipText(I18n.getString("GUI.buttonCE.toolTipText")); //$NON-NLS-1$
		this.buttonCE.setBounds(6, 17, 29, 29);
		this.panelClear.add(this.buttonCE);
		
		this.buttonC = new JButton(I18n.getString("GUI.buttonC.text")); //$NON-NLS-1$
		this.buttonC.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonCE_click();
			}
		});
		this.buttonC.setToolTipText(I18n.getString("GUI.buttonC.toolTipText")); //$NON-NLS-1$
		this.buttonC.setBounds(47, 17, 29, 29);
		this.panelClear.add(this.buttonC);
	}
	
	private void addAdvancedOperationButtons()
	{
		this.buttonPower = new JButton(I18n.getString("GUI.buttonPower.text")); //$NON-NLS-1$
		this.buttonPower.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonPower_click();
			}
		});
		this.buttonPower.setToolTipText(I18n.getString("GUI.buttonPower.toolTipText")); //$NON-NLS-1$
		this.buttonPower.setBounds(6, 17, 29, 29);
		this.panelAdvancedOperations.add(this.buttonPower);
		
		this.buttonRoot = new JButton(CSCalc.ROOT);
		this.buttonRoot.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonRoot_click();
			}
		});
		this.buttonRoot.setToolTipText(I18n.getString("GUI.buttonRoot.toolTipText")); //$NON-NLS-1$
		this.buttonRoot.setBounds(47, 17, 29, 29);
		this.panelAdvancedOperations.add(this.buttonRoot);
		
		this.buttonPI = new JButton(CSCalc.PI);
		this.buttonPI.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonPI_click();
			}
		});
		this.buttonPI.setToolTipText(I18n.getString("GUI.buttonPI.toolTipText")); //$NON-NLS-1$
		this.buttonPI.setBounds(88, 17, 29, 29);
		this.panelAdvancedOperations.add(this.buttonPI);
		
		this.buttonE = new JButton(CSCalc.E);
		this.buttonE.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonE_click();
			}
		});
		this.buttonE.setToolTipText(I18n.getString("GUI.buttonE.toolTipText")); //$NON-NLS-1$
		this.buttonE.setBounds(129, 17, 29, 29);
		this.panelAdvancedOperations.add(this.buttonE);
	}
	
	private void addTextFields()
	{
		StyledDocument doc;
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_RIGHT);
		
		this.inputTextField = new JTextPane();
		this.inputTextField.setBounds(6, 6, 447, 52);
		this.inputTextField.setText(I18n.getString("GUI.inputTextField.text")); //$NON-NLS-1$
		this.inputTextField.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.inputTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		this.inputTextField.setEditable(false);
		this.panelCalculateTab.add(this.inputTextField);
		
		this.resultTextField = new JTextPane();
		this.resultTextField.setBounds(6, 70, 447, 28);
		this.resultTextField.setText(I18n.getString("GUI.resultTextField.text")); //$NON-NLS-1$
		this.resultTextField.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.resultTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		this.resultTextField.setEditable(false);
		this.panelCalculateTab.add(this.resultTextField);
		
		doc = this.resultTextField.getStyledDocument();
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		doc = this.inputTextField.getStyledDocument();
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		this.consoleTextField = new JTextPane();
		this.consoleTextField.setEditable(false);
		this.consoleTextField.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.consoleTextField.setBackground(Color.WHITE);
		this.consoleTextField.setForeground(Color.BLACK);
		this.panelDevTab.add(this.consoleTextField, BorderLayout.CENTER);
		
		this.commandTextField = new JTextField();
		this.commandTextField.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.commandTextField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				GUI.this.calc.commandInputTextField_keyTyped(e.getKeyChar());
			}
		});
		this.commandTextField.setColumns(10);
		this.panelDevTab.add(this.commandTextField, BorderLayout.SOUTH);
	}
	
	private void addCalculateButton()
	{
		this.buttonCalculate = new JButton(I18n.getString("GUI.buttonCalculate.text"));
		this.buttonCalculate.setBounds(6, 298, 447, 75);
		this.buttonCalculate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.this.calc.buttonCalculate_click();
			}
		});
		this.buttonCalculate.setToolTipText(I18n.getString("GUI.buttonCalculate.toolTipText")); //$NON-NLS-1$
		this.buttonCalculate.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		this.panelCalculateTab.add(this.buttonCalculate);
	}
	
	private void addSettings()
	{
		this.checkboxDevMode = new JCheckBox(I18n.getString("GUI.checkboxDevMode.text"));
		this.checkboxDevMode.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				GUI.this.calc.setDevMode(GUI.this.checkboxDevMode.isSelected());
			}
		});
		this.checkboxDevMode.setToolTipText(I18n.getString("GUI.checkboxDevMode.toolTipText"));
		this.panelDevSettings.add(this.checkboxDevMode, "1, 1, fill, top");
		
		this.labelDevModeLogging = new JLabel(I18n.getString("GUI.labelDevModeLogging.text"));
		this.panelDevSettings.add(this.labelDevModeLogging, "1, 2, fill, center");
		
		this.radioButtonLogMinimal = new JRadioButton(I18n.getString("GUI.radioButtonLogMinimal.text"));
		this.radioButtonLogMinimal.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				if (GUI.this.radioButtonLogMinimal.isSelected())
				{
					GUI.this.calc.setLoggingLevel(0);
				}
			}
		});
		this.panelDevSettings.add(this.radioButtonLogMinimal, "2, 2, left, top");
		
		this.radioButtonLogDebug = new JRadioButton(I18n.getString("GUI.radioButtonLogDebug.text"));
		this.radioButtonLogDebug.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				if (GUI.this.radioButtonLogDebug.isSelected())
				{
					GUI.this.calc.setLoggingLevel(2);
				}
			}
		});
		this.panelDevSettings.add(this.radioButtonLogDebug, "4, 2, left, top");
		
		this.radioButtonLogExtended = new JRadioButton(I18n.getString("GUI.radioButtonLogExtended.text"));
		this.panelDevSettings.add(this.radioButtonLogExtended, "6, 2, fill, top");
		this.radioButtonLogExtended.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				if (GUI.this.radioButtonLogExtended.isSelected())
				{
					GUI.this.calc.setLoggingLevel(1);
				}
			}
		});
		
		this.colorChooser = new JColorChooser();
		this.colorChooser.getSelectionModel().addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				setColor(colorChooser.getColor());
			}
		});
		this.colorChooser.setColor(this.frame.getContentPane().getBackground());
		this.colorChooser.setToolTipText(I18n.getString("GUI.colorChooser.toolTipText"));
		this.panelLookAndFeel.add(this.colorChooser, "1, 3, 3, 1, fill, fill");
		
		this.buttonFont = new JButton(I18n.getString("GUI.buttonFont.text"));
		this.panelLookAndFeel.add(this.buttonFont, "1, 1, fill, fill");
		
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		model.addElement("-Default-");
		model.addElement("Web");
		for (LookAndFeelInfo laf : this.lookAndFeels)
		{
			model.addElement(laf.getName());
		}
		
		this.comboBoxLAF = new JComboBox();
		this.comboBoxLAF.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				setLAF(comboBoxLAF.getSelectedIndex(), true);
			}
		});
		this.comboBoxLAF.setModel(model);
		this.panelLookAndFeel.add(this.comboBoxLAF, "3, 1, fill, fill");
		
		ButtonGroup logLevelGroup = new ButtonGroup();
		logLevelGroup.add(this.radioButtonLogDebug);
		logLevelGroup.add(this.radioButtonLogExtended);
		logLevelGroup.add(this.radioButtonLogMinimal);
	}
	
	private void addDraw()
	{
		this.canvasGraph = new Graph();
		this.panelDraw.add(this.canvasGraph, BorderLayout.CENTER);
		
		this.panelDraw_Input = new JPanel();
		GridBagLayout inputPanelConstraint = new GridBagLayout();
		inputPanelConstraint.columnWidths = new int[] { 0, 415, 0, 0 };
		inputPanelConstraint.rowHeights = new int[] { 0, 0 };
		inputPanelConstraint.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		inputPanelConstraint.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		this.panelDraw_Input.setLayout(inputPanelConstraint);
		this.panelDraw.add(this.panelDraw_Input, BorderLayout.NORTH);
		
		this.lblFx = new JLabel(I18n.getString("GUI.lblFx.text")); //$NON-NLS-1$
		GridBagConstraints gbc_lblFx = new GridBagConstraints();
		gbc_lblFx.fill = GridBagConstraints.BOTH;
		gbc_lblFx.insets = new Insets(0, 0, 0, 5);
		gbc_lblFx.gridx = 0;
		gbc_lblFx.gridy = 0;
		this.panelDraw_Input.add(this.lblFx, gbc_lblFx);
		
		this.comboBoxGraph = new JComboBox();
		this.comboBoxGraph.setBackground(Color.WHITE);
		this.comboBoxGraph.setEditable(true);
		GridBagConstraints graphConstraint = new GridBagConstraints();
		graphConstraint.insets = new Insets(0, 0, 0, 5);
		graphConstraint.fill = GridBagConstraints.BOTH;
		graphConstraint.gridx = 1;
		graphConstraint.gridy = 0;
		this.panelDraw_Input.add(this.comboBoxGraph, graphConstraint);
		
		this.buttonDraw = new JButton("\u2192");
		this.buttonDraw.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String item = (String) comboBoxGraph.getEditor().getItem();
				if (!canvasGraph.hasEquation(item))
				{
					comboBoxGraph.addItem(item);
					canvasGraph.addEquation(item);
				}
				
				while (canvasGraph.getEquationCount() > 10)
				{
					comboBoxGraph.removeItemAt(0);
					canvasGraph.removeEquation(0);
				}
			}
		});
		GridBagConstraints drawButtonConstraint = new GridBagConstraints();
		drawButtonConstraint.fill = GridBagConstraints.BOTH;
		drawButtonConstraint.gridx = 2;
		drawButtonConstraint.gridy = 0;
		this.panelDraw_Input.add(this.buttonDraw, drawButtonConstraint);
	}
	
	public void setDevMode(boolean dev)
	{
		if (this.tabbedPane != null)
		{
			this.tabbedPane.setEnabledAt(this.tabbedPane.indexOfComponent(this.panelDevTab), dev);
		}
	}
	
	public void updateSettings()
	{
		int logLevel = this.calc.getLoggingLevel();
		boolean devMode = this.calc.getDevMode();
		Color color = this.calc.getColor();
		int laf = this.calc.getLAF();
		
		this.setDevMode(devMode);
		this.setColor(color);
		this.checkboxDevMode.setSelected(devMode);
		this.comboBoxLAF.setSelectedIndex(laf);
		this.radioButtonLogMinimal.setSelected(logLevel == 0);
		this.radioButtonLogExtended.setSelected(logLevel == 1);
		this.radioButtonLogDebug.setSelected(logLevel == 2);
	}
	
	public void setCurrentTab(int tab)
	{
		if (tab == this.tabbedPane.indexOfComponent(this.panelCalculateTab))
		{
			this.frame.setResizable(false);
			this.frame.setSize(480, 450);
		}
		else
		{
			this.frame.setResizable(true);
		}
	}
	
	public void setColor(Color color)
	{
		this.calc.setColor(color);
		Component component = this.frame.getContentPane();
		this.setColor(component, color);
	}
	
	public void setColor(Component component, Color color)
	{
		if (component instanceof Container && !(component instanceof JTextPane))
		{
			component.setBackground(color);
			Component[] sub = ((Container) component).getComponents();
			for (int i = 0; i < sub.length; i++)
			{
				Component c = sub[i];
				this.setColor(c, color);
			}
		}
		else
		{
			component.setBackground(color.brighter());
		}
	}
	
	public void setLAF(int laf, boolean update)
	{
		int oldLAF = this.calc.getLAF();
		this.calc.setLAF(laf);
		
		if (oldLAF == laf)
		{
			return;
		}
		
		this.initLAF(laf, update);
	}
	
	public void initLAF(final int laf, final boolean update)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					if (laf == 0)
					{
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					}
					else if (laf == 1)
					{
						WebLookAndFeel.install();
					}
					else if (laf > 0 && laf - 2 < lookAndFeels.length)
					{
						UIManager.setLookAndFeel(lookAndFeels[laf - 2].getClassName());
					}
					
					if (update)
					{
						WebLookAndFeel.updateAllComponentUIs();
						JOptionPane.showMessageDialog(frame, "Restart the Application to apply LAF changes", "Warning", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				catch (Exception e)
				{
					print("Error setting native LAF: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void print(String text)
	{
		if (this.consoleTextField != null)
		{
			this.consoleTextField.setText(GUI.instance.consoleTextField.getText() + text + "\n");
		}
		System.out.println(text);
	}
}
