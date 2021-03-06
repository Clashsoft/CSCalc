package clashsoft.csutil.gui;

import clashsoft.csutil.CSUtil;
import clashsoft.csutil.strings.IStringConverter;
import com.alee.laf.WebLookAndFeel;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class GUI
{
	public static GUI instance = new GUI();

	public CSUtil calc;

	public LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();

	public JFrame frameCSUtil;

	public JTextPane inputTextField;
	public JTextPane resultTextField;

	public JTextField commandTextField;
	public JTextPane  consoleTextField;

	public JTabbedPane tabbedPane;
	public JPanel      panelCalc;
	public JTabbedPane panelSettings;
	public JPanel      panelDevTab;
	public JPanel      panelNumpad;
	public JPanel      panelBasicOperations;
	public JPanel      panelBinaryOperations;
	public JPanel      panelClear;
	public JPanel      panelConstants;
	public JPanel      panelLAFSettings;
	public JPanel      panelDevSettings;
	public JPanel      panelFileRename;
	public JPanel      panelDrawInput;
	public JPanel      panelCalcSettings;
	public JPanel      panelRadix;
	public JPanel      panelStrings;

	public JButton buttonCalculate;

	public JButton button0;
	public JButton button1;
	public JButton button2;
	public JButton button3;
	public JButton button4;
	public JButton button5;
	public JButton button6;
	public JButton button7;
	public JButton button8;
	public JButton button9;
	public JButton buttonDecimalPoint;
	public JButton buttonNegate;

	public JButton buttonAdd;
	public JButton buttonSubstract;
	public JButton buttonMultiply;
	public JButton buttonDivide;
	public JButton buttonRemainder;
	public JButton buttonReciprocal;
	public JButton buttonPower;
	public JButton buttonRoot;

	public JButton buttonAND;
	public JButton buttonOR;
	public JButton buttonXOR;
	public JButton buttonNOT;
	public JButton buttonBitshiftRight;
	public JButton buttonBitshiftRightU;
	public JButton buttonBitshiftLeft;

	public JButton buttonCE;
	public JButton buttonC;

	public JButton buttonPI;
	public JButton buttonE;
	public JButton buttonInfinity;
	public JButton buttonNegativeInfinity;
	public JButton buttonNaN;

	public JButton       buttonFont;
	public JColorChooser colorChooser;
	public JComboBox     comboBoxLAF;
	public JCheckBox     checkboxDevMode;
	public JLabel        labelDevModeLogging;
	public JRadioButton  radioButtonLogDebug;
	public JRadioButton  radioButtonLogExtended;
	public JRadioButton  radioButtonLogMinimal;

	public JPopupMenu popupMenuGraph;
	public JMenu      menuZoom;
	public JMenuItem  menuItemZoomDefault;
	public JMenuItem  menuItemZoomMultipied;
	public JMenuItem  menuItemZoomCubed;
	public JMenuItem  menuItemZoomNumber;
	public JMenuItem  menuItemZoomInfo;

	private ButtonGroup buttonGroupDisplay = new ButtonGroup();

	public JRadioButton radioButtonBinary;
	public JRadioButton radioButtonOctal;
	public JRadioButton radioButtonDecimal;
	public JRadioButton radioButtonHexadecimal;
	public JRadioButton radioButtonCustomRadix;
	public JSlider      sliderRadix;
	public JLabel       labelRadix;

	public JTextArea   textFieldStringsInput;
	public JTextArea   textFieldStringsOutput;
	public JLabel      labelStrings;
	public JScrollPane scrollPaneStrings;
	public JTree       treeStringConversions;
	public JPanel      panelStringsArguments;

	public static void init()
	{
		instance.calc = CSUtil.instance;
		instance.initialize();
		instance.frameCSUtil.setVisible(true);
	}

	public GUI()
	{

	}

	/**
	 * @wbp.parser.entryPoint
	 */
	private void initialize()
	{
		this.frameCSUtil = new JFrame(I18n.getString("GUI.frameCSUtil.title")); //$NON-NLS-1$
		this.frameCSUtil.setBounds(100, 100, 480, 450);
		this.frameCSUtil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frameCSUtil.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				GUI.this.calc.exit();
			}
		});

		this.tabbedPane = new JTabbedPane(SwingConstants.TOP);
		this.tabbedPane.setBounds(0, 0, 480, 448);
		this.tabbedPane.addChangeListener(e -> GUI.this.setCurrentTab(GUI.this.tabbedPane.getSelectedIndex()));
		this.frameCSUtil.getContentPane().add(this.tabbedPane);

		this.addPanels();
		this.addTextFields();

		this.calc.devInfo("Initializing GUI...", 2);

		this.addCalculateButton();
		this.addNumpadButtons();
		this.addBasicOperationButtons();
		this.addBinaryOperationButtons();
		this.addAdvancedOperationButtons();
		this.addClearButtons();

		this.addStringsTab();

		this.addSettingsTab();

		this.calc.devInfo("Done Initializing GUI", 2);

		this.updateSettings();
	}

	private void addPanels()
	{
		// Tab Panels

		this.panelCalc = new JPanel();
		this.panelCalc.setLayout(null);
		this.tabbedPane.addTab(I18n.getString("GUI.panelCalculateTab.text"), null, this.panelCalc,
		                       I18n.getString("GUI.panelCalculateTab.toolTipText")); //$NON-NLS-1$ //$NON-NLS-2$

		this.panelStrings = new JPanel();
		this.panelStrings.setLayout(new FormLayout(
			new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("min:grow"),
				FormFactory.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC, },
			new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.MIN_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("fill:default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC, }));
		this.tabbedPane.addTab(I18n.getString("GUI.panelStrings.title"), null, this.panelStrings,
		                       I18n.getString("GUI.panelStrings.tooltip")); //$NON-NLS-1$

		this.panelFileRename = new JPanel();
		this.panelFileRename.setLayout(new BorderLayout(0, 0));
		this.tabbedPane.addTab(I18n.getString("GUI.panelFileRename.title"), null, this.panelFileRename,
		                       I18n.getString("GUI.panelFileRename.tooltip")); //$NON-NLS-1$ //$NON-NLS-2$

		this.panelSettings = new JTabbedPane(SwingConstants.TOP);
		this.tabbedPane.addTab(I18n.getString("GUI.panelSettingsTab.text"), null, this.panelSettings,
		                       I18n.getString("GUI.panelSettingsTab.toolTipText")); //$NON-NLS-1$ //$NON-NLS-2$

		this.panelDevTab = new JPanel();
		this.panelDevTab.setLayout(new BorderLayout(0, 0));
		this.tabbedPane.addTab(I18n.getString("GUI.panelDevTab.text"), null, this.panelDevTab,
		                       I18n.getString("GUI.panelDevTab.toolTipText")); //$NON-NLS-1$ //$NON-NLS-2$

		// Settings Panels

		this.panelLAFSettings = new JPanel();
		this.panelSettings.addTab(I18n.getString("GUI.panelLookAndFeel.title"), null, this.panelLAFSettings, null);

		this.panelCalcSettings = new JPanel();
		this.panelCalcSettings.setLayout(new BorderLayout(0, 0));
		this.panelSettings
			.addTab(I18n.getString("GUI.panelCalcSettings.title"), null, this.panelCalcSettings, null); //$NON-NLS-1$

		this.panelRadix = new JPanel();
		this.panelRadix.setBorder(new TitledBorder(null, "Radix", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.panelRadix.setLayout(new FormLayout(
			new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(82dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("0dlu"), },
			new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));
		this.panelCalcSettings.add(this.panelRadix);

		// Dev Settings Panels

		this.panelDevSettings = new JPanel();
		this.panelSettings
			.addTab(I18n.getString("GUI.panelDevSettings.title"), null, this.panelDevSettings, null); //$NON-NLS-1$

		// Calculate Panels

		this.panelNumpad = new JPanel();
		this.panelNumpad.setBounds(6, 110, 124, 176);
		this.panelNumpad.setBorder(new TitledBorder(I18n.getString("GUI.panelNumpad.borderTitle"))); //$NON-NLS-1$
		this.panelNumpad.setLayout(null);
		this.panelCalc.add(this.panelNumpad);

		this.panelBasicOperations = new JPanel();
		this.panelBasicOperations.setBounds(142, 110, 166, 94);
		this.panelBasicOperations
			.setBorder(new TitledBorder(I18n.getString("GUI.panelBasicOperations.borderTitle"))); //$NON-NLS-1$
		this.panelBasicOperations.setLayout(null);
		this.panelCalc.add(this.panelBasicOperations);

		this.panelConstants = new JPanel();
		this.panelConstants.setBounds(320, 216, 124, 94);
		this.panelConstants
			.setBorder(new TitledBorder(I18n.getString("GUI.panelAdvancedOperations.borderTitle"))); //$NON-NLS-1$
		this.panelConstants.setLayout(null);
		this.panelCalc.add(this.panelConstants);

		this.panelBinaryOperations = new JPanel();
		this.panelBinaryOperations.setBounds(142, 216, 166, 94);
		this.panelBinaryOperations
			.setBorder(new TitledBorder(I18n.getString("GUI.panelBinaryOperations.borderTitle"))); //$NON-NLS-1$
		this.panelBinaryOperations.setLayout(null);
		this.panelCalc.add(this.panelBinaryOperations);

		this.panelClear = new JPanel();
		this.panelClear.setBounds(320, 110, 124, 94);
		this.panelClear.setBorder(new TitledBorder(I18n.getString("GUI.panelClear.borderTitle"))); //$NON-NLS-1$
		this.panelClear.setLayout(null);
		this.panelCalc.add(this.panelClear);
	}

	private void addNumpadButtons()
	{
		this.button0 = new JButton(I18n.getString("GUI.button0.text")); //$NON-NLS-1$
		this.button0.setBounds(47, 140, 29, 29);
		this.button0.addActionListener(e -> GUI.this.calc.button0_click());
		this.panelNumpad.add(this.button0);

		this.button1 = new JButton(I18n.getString("GUI.button1.text")); //$NON-NLS-1$
		this.button1.setBounds(6, 99, 29, 29);
		this.button1.addActionListener(e -> GUI.this.calc.button1_click());
		this.panelNumpad.add(this.button1);

		this.button2 = new JButton(I18n.getString("GUI.button2.text")); //$NON-NLS-1$
		this.button2.setBounds(47, 99, 29, 29);
		this.button2.addActionListener(e -> GUI.this.calc.button2_click());
		this.panelNumpad.add(this.button2);

		this.button3 = new JButton(I18n.getString("GUI.button3.text")); //$NON-NLS-1$
		this.button3.setBounds(88, 99, 29, 29);
		this.button3.addActionListener(e -> GUI.this.calc.button3_click());
		this.panelNumpad.add(this.button3);

		this.button4 = new JButton(I18n.getString("GUI.button4.text")); //$NON-NLS-1$
		this.button4.setBounds(6, 58, 29, 29);
		this.button4.addActionListener(e -> GUI.this.calc.button4_click());
		this.panelNumpad.add(this.button4);

		this.button5 = new JButton(I18n.getString("GUI.button5.text")); //$NON-NLS-1$
		this.button5.setBounds(47, 58, 29, 29);
		this.button5.addActionListener(e -> GUI.this.calc.button5_click());
		this.panelNumpad.add(this.button5);

		this.button6 = new JButton(I18n.getString("GUI.button6.text")); //$NON-NLS-1$
		this.button6.setBounds(88, 58, 29, 29);
		this.button6.addActionListener(e -> GUI.this.calc.button6_click());
		this.panelNumpad.add(this.button6);

		this.button7 = new JButton(I18n.getString("GUI.button7.text")); //$NON-NLS-1$
		this.button7.setBounds(6, 17, 29, 29);
		this.button7.addActionListener(e -> GUI.this.calc.button7_click());
		this.panelNumpad.add(this.button7);

		this.button8 = new JButton(I18n.getString("GUI.button8.text")); //$NON-NLS-1$
		this.button8.setBounds(47, 17, 29, 29);
		this.button8.addActionListener(e -> GUI.this.calc.button8_click());
		this.panelNumpad.add(this.button8);

		this.button9 = new JButton(I18n.getString("GUI.button9.text")); //$NON-NLS-1$
		this.button9.setBounds(88, 17, 29, 29);
		this.button9.addActionListener(e -> GUI.this.calc.button9_click());
		this.panelNumpad.add(this.button9);

		this.buttonDecimalPoint = new JButton(I18n.getString("GUI.buttonDecimalPoint.text")); //$NON-NLS-1$
		this.buttonDecimalPoint.setToolTipText(I18n.getString("GUI.buttonDecimalPoint.toolTipText")); //$NON-NLS-1$
		this.buttonDecimalPoint.setBounds(88, 140, 29, 29);
		this.buttonDecimalPoint.addActionListener(e -> GUI.this.calc.buttonDecimalPoint_click());
		this.panelNumpad.add(this.buttonDecimalPoint);

		this.buttonNegate = new JButton(I18n.getString("GUI.buttonNegate.text")); //$NON-NLS-1$
		this.buttonNegate.setToolTipText(I18n.getString("GUI.buttonNegate.toolTipText")); //$NON-NLS-1$
		this.buttonNegate.setBounds(6, 140, 29, 29);
		this.buttonNegate.addActionListener(e -> GUI.this.calc.buttonNegate_click());
		this.panelNumpad.add(this.buttonNegate);
	}

	private void addBasicOperationButtons()
	{
		this.buttonAdd = new JButton(I18n.getString("GUI.buttonAdd.text")); //$NON-NLS-1$
		this.buttonAdd.setToolTipText(I18n.getString("GUI.buttonAdd.toolTipText")); //$NON-NLS-1$
		this.buttonAdd.setBounds(6, 17, 29, 29);
		this.buttonAdd.addActionListener(e -> GUI.this.calc.buttonAdd_click());
		this.panelBasicOperations.add(this.buttonAdd);

		this.buttonSubstract = new JButton(I18n.getString("GUI.buttonSubstract.text")); //$NON-NLS-1$
		this.buttonSubstract.setToolTipText(I18n.getString("GUI.buttonSubstract.toolTipText")); //$NON-NLS-1$
		this.buttonSubstract.setBounds(47, 17, 29, 29);
		this.buttonSubstract.addActionListener(e -> GUI.this.calc.buttonSubstract_click());
		this.panelBasicOperations.add(this.buttonSubstract);

		this.buttonMultiply = new JButton(I18n.getString("GUI.buttonMultiply.text")); //$NON-NLS-1$
		this.buttonMultiply.setToolTipText(I18n.getString("GUI.buttonMultiply.toolTipText")); //$NON-NLS-1$
		this.buttonMultiply.setBounds(6, 58, 29, 29);
		this.buttonMultiply.addActionListener(e -> GUI.this.calc.buttonMultiply_click());
		this.panelBasicOperations.add(this.buttonMultiply);

		this.buttonDivide = new JButton(I18n.getString("GUI.buttonDivide.text")); //$NON-NLS-1$
		this.buttonDivide.setToolTipText(I18n.getString("GUI.buttonDivide.toolTipText")); //$NON-NLS-1$
		this.buttonDivide.setBounds(47, 58, 29, 29);
		this.buttonDivide.addActionListener(e -> GUI.this.calc.buttonDivide_click());
		this.panelBasicOperations.add(this.buttonDivide);

		this.buttonRemainder = new JButton(I18n.getString("GUI.buttonRemainder.text")); //$NON-NLS-1$
		this.buttonRemainder.setToolTipText(I18n.getString("GUI.buttonRemainder.toolTipText")); //$NON-NLS-1$
		this.buttonRemainder.setBounds(88, 17, 29, 29);
		this.buttonRemainder.addActionListener(e -> GUI.this.calc.buttonRemainder_click());
		this.panelBasicOperations.add(this.buttonRemainder);

		this.buttonReciprocal = new JButton(I18n.getString("GUI.buttonReciprocal.text")); //$NON-NLS-1$
		this.buttonReciprocal.setToolTipText(I18n.getString("GUI.buttonReciprocal.toolTipText")); //$NON-NLS-1$
		this.buttonReciprocal.addActionListener(e -> GUI.this.calc.buttonReciprocal_click());
		this.buttonReciprocal.setBounds(88, 58, 29, 29);
		this.panelBasicOperations.add(this.buttonReciprocal);

		this.buttonPower = new JButton(I18n.getString("GUI.buttonPower.text")); //$NON-NLS-1$
		this.buttonPower.setToolTipText(I18n.getString("GUI.buttonPower.toolTipText"));
		this.buttonPower.setBounds(129, 17, 29, 29);
		this.buttonPower.addActionListener(e -> GUI.this.calc.buttonPower_click());
		this.panelBasicOperations.add(this.buttonPower);

		this.buttonRoot = new JButton(I18n.getString("GUI.buttonRoot.text")); //$NON-NLS-1$
		this.buttonRoot.setToolTipText(I18n.getString("GUI.buttonRoot.toolTipText")); //$NON-NLS-1$
		this.buttonRoot.setBounds(129, 58, 29, 29);
		this.buttonRoot.addActionListener(e -> GUI.this.calc.buttonRoot_click());
		this.panelBasicOperations.add(this.buttonRoot);
	}

	private void addBinaryOperationButtons()
	{
		this.buttonAND = new JButton(I18n.getString("GUI.buttonAND.text")); //$NON-NLS-1$
		this.buttonAND.setToolTipText(I18n.getString("GUI.buttonAND.toolTipText")); //$NON-NLS-1$
		this.buttonAND.setBounds(6, 17, 29, 29);
		this.buttonAND.addActionListener(e -> GUI.this.calc.buttonAND_click());
		this.panelBinaryOperations.add(this.buttonAND);

		this.buttonOR = new JButton(I18n.getString("GUI.buttonOR.text")); //$NON-NLS-1$
		this.buttonOR.setToolTipText(I18n.getString("GUI.buttonOR.toolTipText")); //$NON-NLS-1$
		this.buttonOR.setBounds(47, 17, 29, 29);
		this.buttonOR.addActionListener(e -> GUI.this.calc.buttonOR_click());
		this.panelBinaryOperations.add(this.buttonOR);

		this.buttonXOR = new JButton(I18n.getString("GUI.buttonXOR.text")); //$NON-NLS-1$
		this.buttonXOR.setToolTipText(I18n.getString("GUI.buttonXOR.toolTipText")); //$NON-NLS-1$
		this.buttonXOR.setBounds(88, 17, 29, 29);
		this.buttonXOR.addActionListener(e -> GUI.this.calc.buttonXOR_click());
		this.panelBinaryOperations.add(this.buttonXOR);

		this.buttonNOT = new JButton(CSUtil.NOT);
		this.buttonNOT.setToolTipText(I18n.getString("GUI.buttonNOT.toolTipText")); //$NON-NLS-1$
		this.buttonNOT.setBounds(129, 17, 29, 29);
		this.buttonNOT.addActionListener(e -> GUI.this.calc.buttonNOT_click());
		this.panelBinaryOperations.add(this.buttonNOT);

		this.buttonBitshiftRight = new JButton(I18n.getString("GUI.buttonBitshiftRight.text")); //$NON-NLS-1$
		this.buttonBitshiftRight.setToolTipText(I18n.getString("GUI.buttonBitshiftRight.toolTipText")); //$NON-NLS-1$
		this.buttonBitshiftRight.setBounds(6, 58, 29, 29);
		this.buttonBitshiftRight.addActionListener(e -> GUI.this.calc.buttonBitshiftRight_click());
		this.panelBinaryOperations.add(this.buttonBitshiftRight);

		this.buttonBitshiftRightU = new JButton(I18n.getString("GUI.buttonBitshiftRightU.text")); //$NON-NLS-1$
		this.buttonBitshiftRightU.setToolTipText(I18n.getString("GUI.buttonBitshiftRightU.toolTipText")); //$NON-NLS-1$
		this.buttonBitshiftRightU.setBounds(47, 58, 29, 29);
		this.buttonBitshiftRightU.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		this.buttonBitshiftRightU.addActionListener(e -> GUI.this.calc.buttonBitshiftRightU_click());
		this.panelBinaryOperations.add(this.buttonBitshiftRightU);

		this.buttonBitshiftLeft = new JButton(I18n.getString("GUI.buttonBitshiftLeft.text")); //$NON-NLS-1$
		this.buttonBitshiftLeft.setToolTipText(I18n.getString("GUI.buttonBitshiftLeft.toolTipText")); //$NON-NLS-1$
		this.buttonBitshiftLeft.setBounds(88, 58, 29, 29);
		this.buttonBitshiftLeft.addActionListener(e -> GUI.this.calc.buttonBitshiftLeft_click());
		this.panelBinaryOperations.add(this.buttonBitshiftLeft);
	}

	private void addClearButtons()
	{
		this.buttonCE = new JButton(I18n.getString("GUI.buttonCE.text")); //$NON-NLS-1$
		this.buttonCE.setToolTipText(I18n.getString("GUI.buttonCE.toolTipText")); //$NON-NLS-1$
		this.buttonCE.setBounds(6, 17, 112, 29);
		this.buttonCE.addActionListener(e -> GUI.this.calc.buttonCE_click());
		this.panelClear.add(this.buttonCE);

		this.buttonC = new JButton(I18n.getString("GUI.buttonC.text")); //$NON-NLS-1$
		this.buttonC.setToolTipText(I18n.getString("GUI.buttonC.toolTipText")); //$NON-NLS-1$
		this.buttonC.setBounds(6, 58, 112, 29);
		this.buttonC.addActionListener(e -> GUI.this.calc.buttonC_click());
		this.panelClear.add(this.buttonC);
	}

	private void addAdvancedOperationButtons()
	{
		this.buttonPI = new JButton(I18n.getString("GUI.buttonPI.text")); //$NON-NLS-1$
		this.buttonPI.setToolTipText(I18n.getString("GUI.buttonPI.toolTipText")); //$NON-NLS-1$
		this.buttonPI.setBounds(6, 17, 29, 29);
		this.buttonPI.addActionListener(e -> GUI.this.calc.buttonPI_click());
		this.panelConstants.add(this.buttonPI);

		this.buttonE = new JButton(I18n.getString("GUI.buttonE.text")); //$NON-NLS-1$
		this.buttonE.setToolTipText(I18n.getString("GUI.buttonE.toolTipText")); //$NON-NLS-1$
		this.buttonE.setBounds(47, 17, 29, 29);
		this.buttonE.addActionListener(e -> GUI.this.calc.buttonE_click());
		this.panelConstants.add(this.buttonE);

		this.buttonInfinity = new JButton(I18n.getString("GUI.buttonInfinity.text")); //$NON-NLS-1$
		this.buttonInfinity.setToolTipText(I18n.getString("GUI.buttonInfinity.toolTipText")); //$NON-NLS-1$
		this.buttonInfinity.setBounds(6, 58, 29, 29);
		this.buttonInfinity.addActionListener(e -> GUI.this.calc.buttonInfinity_click());
		this.panelConstants.add(this.buttonInfinity);

		this.buttonNegativeInfinity = new JButton(I18n.getString("GUI.buttonNegativeInfinity.text")); //$NON-NLS-1$
		this.buttonNegativeInfinity
			.setToolTipText(I18n.getString("GUI.buttonNegativeInfinity.toolTipText")); //$NON-NLS-1$
		this.buttonNegativeInfinity.setBounds(47, 58, 29, 29);
		this.buttonNegativeInfinity.addActionListener(e -> GUI.this.calc.buttonNegativeInfinity_click());
		this.panelConstants.add(this.buttonNegativeInfinity);

		this.buttonNaN = new JButton(I18n.getString("GUI.buttonNaN.text")); //$NON-NLS-1$
		this.buttonNaN.setToolTipText(I18n.getString("GUI.buttonNaN.toolTipText")); //$NON-NLS-1$
		this.buttonNaN.setBounds(88, 17, 29, 29);
		this.buttonNaN.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		this.buttonNaN.addActionListener(e -> GUI.this.calc.buttonNaN_click());
		this.panelConstants.add(this.buttonNaN);
	}

	private void addTextFields()
	{
		StyledDocument doc;
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_RIGHT);

		this.inputTextField = new JTextPane();
		this.inputTextField.setText(I18n.getString("GUI.inputTextField.text")); //$NON-NLS-1$
		this.inputTextField.setBounds(6, 6, 447, 52);
		this.inputTextField.setBorder(UIManager.getBorder("TextField.border"));
		this.inputTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		this.inputTextField.setEditable(false);
		this.panelCalc.add(this.inputTextField);

		doc = this.inputTextField.getStyledDocument();
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		this.resultTextField = new JTextPane();
		this.resultTextField.setText(I18n.getString("GUI.resultTextField.text")); //$NON-NLS-1$
		this.resultTextField.setBounds(6, 70, 447, 28);
		this.resultTextField.setBorder(UIManager.getBorder("TextField.border"));
		this.resultTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		this.resultTextField.setEditable(false);
		this.panelCalc.add(this.resultTextField);

		doc = this.resultTextField.getStyledDocument();
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		this.consoleTextField = new JTextPane();
		this.consoleTextField.setBorder(new LineBorder(Color.BLACK));
		this.consoleTextField.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(this.consoleTextField);
		this.panelDevTab.add(scrollPane, BorderLayout.CENTER);

		this.commandTextField = new JTextField();
		this.commandTextField.setBorder(new LineBorder(Color.BLACK));
		this.commandTextField.setColumns(10);
		this.commandTextField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				GUI.this.calc.commandInputTextField_keyTyped(e.getKeyChar());
			}
		});
		this.panelDevTab.add(this.commandTextField, BorderLayout.SOUTH);
	}

	private void addCalculateButton()
	{
		this.buttonCalculate = new JButton(I18n.getString("GUI.buttonCalculate.text"));
		this.buttonCalculate.setToolTipText(I18n.getString("GUI.buttonCalculate.toolTipText")); //$NON-NLS-1$
		this.buttonCalculate.setBounds(6, 322, 447, 54);
		this.buttonCalculate.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		this.buttonCalculate.addActionListener(e -> GUI.this.calc.buttonCalculate_click());
		this.panelCalc.add(this.buttonCalculate);
	}

	private void addSettingsTab()
	{
		this.radioButtonBinary = new JRadioButton(I18n.getString("GUI.radioButtonBinary.text"));
		this.radioButtonBinary.addItemListener(e -> {
			if (GUI.this.radioButtonBinary.isSelected())
			{
				GUI.this.setRadix(2);
			}
		});
		this.panelRadix.add(this.radioButtonBinary, "2, 2");
		this.buttonGroupDisplay.add(this.radioButtonBinary);

		this.radioButtonOctal = new JRadioButton(I18n.getString("GUI.radioButtonOctal.text")); //$NON-NLS-1$
		this.radioButtonOctal.addItemListener(e -> {
			if (GUI.this.radioButtonOctal.isSelected())
			{
				GUI.this.setRadix(8);
			}
		});
		this.panelRadix.add(this.radioButtonOctal, "2, 4");
		this.buttonGroupDisplay.add(this.radioButtonOctal);

		this.radioButtonDecimal = new JRadioButton(I18n.getString("GUI.radioButtonDecimal.text"));
		this.radioButtonDecimal.addItemListener(e -> {
			if (GUI.this.radioButtonDecimal.isSelected())
			{
				GUI.this.setRadix(10);
			}
		});
		this.panelRadix.add(this.radioButtonDecimal, "2, 6");
		this.buttonGroupDisplay.add(this.radioButtonDecimal);

		this.radioButtonHexadecimal = new JRadioButton(I18n.getString("GUI.radioButtonHexadecimal.text"));
		this.radioButtonHexadecimal.addItemListener(e -> {
			if (GUI.this.radioButtonHexadecimal.isSelected())
			{
				GUI.this.setRadix(16);
			}
		});
		this.panelRadix.add(this.radioButtonHexadecimal, "2, 8");
		this.buttonGroupDisplay.add(this.radioButtonHexadecimal);

		this.radioButtonCustomRadix = new JRadioButton(I18n.getString("GUI.radioButtonCustomRadix.text"));
		this.radioButtonCustomRadix
			.addItemListener(e -> GUI.this.sliderRadix.setEnabled(GUI.this.radioButtonCustomRadix.isSelected()));
		this.panelRadix.add(this.radioButtonCustomRadix, "2, 10, fill, top");
		this.buttonGroupDisplay.add(this.radioButtonCustomRadix);

		this.sliderRadix = new JSlider();
		this.sliderRadix.setMinorTickSpacing(1);
		this.sliderRadix.setPaintTicks(true);
		this.sliderRadix.setPaintLabels(true);
		this.sliderRadix.setSnapToTicks(true);
		this.sliderRadix.setValue(2);
		this.sliderRadix.setEnabled(false);
		this.sliderRadix.setMinimum(2);
		this.sliderRadix.setMaximum(36);
		this.sliderRadix.addChangeListener(e -> GUI.this.setRadix(GUI.this.sliderRadix.getValue()));
		this.panelRadix.add(this.sliderRadix, "4, 10, fill, top");

		this.labelRadix = new JLabel("0");
		this.panelRadix.add(this.labelRadix, "6, 10, fill, top");

		this.checkboxDevMode = new JCheckBox(I18n.getString("GUI.checkboxDevMode.text")); //$NON-NLS-1$
		this.checkboxDevMode.setBounds(6, 6, 112, 23);
		this.checkboxDevMode.setToolTipText(I18n.getString("GUI.checkboxDevMode.toolTipText")); //$NON-NLS-1$
		this.checkboxDevMode.addChangeListener(e -> GUI.this.calc.setDevMode(GUI.this.checkboxDevMode.isSelected()));
		this.panelDevSettings.setLayout(null);
		this.panelDevSettings.add(this.checkboxDevMode);

		this.labelDevModeLogging = new JLabel(I18n.getString("GUI.labelDevModeLogging.text")); //$NON-NLS-1$
		this.labelDevModeLogging.setBounds(6, 41, 112, 16);
		this.panelDevSettings.add(this.labelDevModeLogging);

		this.radioButtonLogMinimal = new JRadioButton(I18n.getString("GUI.radioButtonLogMinimal.text")); //$NON-NLS-1$
		this.radioButtonLogMinimal.setBounds(6, 69, 88, 23);
		this.radioButtonLogMinimal.addChangeListener(e -> {
			if (GUI.this.radioButtonLogMinimal.isSelected())
			{
				GUI.this.calc.setLoggingLevel(0);
			}
		});
		this.panelDevSettings.add(this.radioButtonLogMinimal);

		this.radioButtonLogDebug = new JRadioButton(I18n.getString("GUI.radioButtonLogDebug.text")); //$NON-NLS-1$
		this.radioButtonLogDebug.setBounds(6, 104, 74, 23);
		this.radioButtonLogDebug.addChangeListener(e -> {
			if (GUI.this.radioButtonLogDebug.isSelected())
			{
				GUI.this.calc.setLoggingLevel(2);
			}
		});
		this.panelDevSettings.add(this.radioButtonLogDebug);

		this.radioButtonLogExtended = new JRadioButton(I18n.getString("GUI.radioButtonLogExtended.text")); //$NON-NLS-1$
		this.radioButtonLogExtended.setBounds(6, 140, 117, 23);
		this.radioButtonLogExtended.addChangeListener(e -> {
			if (GUI.this.radioButtonLogExtended.isSelected())
			{
				GUI.this.calc.setLoggingLevel(1);
			}
		});
		this.panelDevSettings.add(this.radioButtonLogExtended);

		ButtonGroup logLevelGroup = new ButtonGroup();
		logLevelGroup.add(this.radioButtonLogDebug);
		logLevelGroup.add(this.radioButtonLogExtended);
		logLevelGroup.add(this.radioButtonLogMinimal);

		this.colorChooser = new JColorChooser();
		this.colorChooser.setToolTipText(I18n.getString("GUI.colorChooser.toolTipText")); //$NON-NLS-1$
		this.colorChooser.setColor(this.frameCSUtil.getContentPane().getBackground());
		this.colorChooser.getSelectionModel()
		                 .addChangeListener(e -> GUI.this.setColor(GUI.this.colorChooser.getColor()));
		this.panelLAFSettings.setLayout(new FormLayout(
			new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("190px:grow"),
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("190px:grow"), FormFactory.RELATED_GAP_COLSPEC, },
			new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("24px"), FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("260px:grow"), FormFactory.RELATED_GAP_ROWSPEC, }));
		this.panelLAFSettings.add(this.colorChooser, "2, 4, 3, 1, fill, fill");

		DefaultComboBoxModel model = new DefaultComboBoxModel();
		model.addElement("-Default-");
		model.addElement("Web");
		for (LookAndFeelInfo laf : this.lookAndFeels)
		{
			model.addElement(laf.getName());
		}

		this.comboBoxLAF = new JComboBox();
		this.comboBoxLAF.setModel(model);
		this.comboBoxLAF.addItemListener(e -> GUI.this.setLAF(GUI.this.comboBoxLAF.getSelectedIndex(), true));
		this.panelLAFSettings.add(this.comboBoxLAF, "2, 2, fill, fill");

		this.buttonFont = new JButton(I18n.getString("GUI.buttonFont.text")); //$NON-NLS-1$
		this.panelLAFSettings.add(this.buttonFont, "4, 2, fill, fill");
	}

	public void addStringsTab()
	{
		this.scrollPaneStrings = new JScrollPane();
		this.panelStrings.add(this.scrollPaneStrings, "2, 2, 1, 7, fill, fill");

		this.treeStringConversions = new JTree();
		this.scrollPaneStrings.setViewportView(this.treeStringConversions);
		this.treeStringConversions.addTreeSelectionListener(e -> {
			IStringConverter converter = GUI.this.getCurrentStringConverter();
			GUI.this.panelStringsArguments.removeAll();
			if (converter != null)
			{
				GUI.this.panelStringsArguments.setVisible(true);
				converter.addArguments(GUI.this, GUI.this.panelStringsArguments);
			}
			else
			{
				GUI.this.panelStringsArguments.setVisible(false);
			}
			GUI.this.updateStringConverter();
		});
		this.treeStringConversions.setBorder(UIManager.getBorder("TextField.border"));
		this.treeStringConversions.setModel(new DefaultTreeModel(new StringConversions("String Conversions")));

		this.panelStringsArguments = new JPanel();
		this.panelStringsArguments
			.setBorder(new TitledBorder(null, "Arguments", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.panelStrings.add(this.panelStringsArguments, "4, 2, fill, fill");
		this.panelStringsArguments.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		this.textFieldStringsInput = new JTextArea();
		this.textFieldStringsInput.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				GUI.this.updateStringConverter();
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				GUI.this.updateStringConverter();
			}
		});
		this.textFieldStringsInput.setBorder(UIManager.getBorder("TextField.border"));
		this.textFieldStringsInput.setColumns(10);
		this.panelStrings.add(this.textFieldStringsInput, "4, 4, fill, fill");

		this.labelStrings = new JLabel(I18n.getString("GUI.labelStrings.text")); //$NON-NLS-1$
		this.panelStrings.add(this.labelStrings, "4, 6, center, center");

		this.textFieldStringsOutput = new JTextArea();
		this.textFieldStringsOutput.setEditable(false);
		this.textFieldStringsOutput.setBorder(UIManager.getBorder("TextField.border"));
		this.textFieldStringsOutput.setColumns(10);
		this.panelStrings.add(this.textFieldStringsOutput, "4, 8, fill, fill");
	}

	public IStringConverter getCurrentStringConverter()
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.treeStringConversions
			                                                       .getLastSelectedPathComponent();
		if (node == null || !node.isLeaf())
		{
			return null;
		}
		Object nodeInfo = node.getUserObject();
		if (nodeInfo instanceof IStringConverter)
		{
			return (IStringConverter) nodeInfo;
		}
		return null;
	}

	public void updateStringConverter()
	{
		IStringConverter converter = this.getCurrentStringConverter();
		String text = this.textFieldStringsInput.getText();
		if (converter != null)
		{
			try
			{
				text = converter.getConvertedString(text);
			}
			catch (Exception ex)
			{
			}
		}
		this.textFieldStringsOutput.setText(text);
		this.frameCSUtil.repaint();
	}

	public void updateSettings()
	{
		int logLevel = this.calc.getLoggingLevel();
		boolean devMode = this.calc.getDevMode();
		Color color = this.calc.getColor();
		int laf = this.calc.getLAF();
		int radix = this.calc.getRadix();

		this.initLAF(laf, true);

		this.setDevMode(devMode);
		this.setColor(color);
		this.checkboxDevMode.setSelected(devMode);
		this.comboBoxLAF.setSelectedIndex(laf);

		this.radioButtonLogMinimal.setSelected(logLevel == 0);
		this.radioButtonLogExtended.setSelected(logLevel == 1);
		this.radioButtonLogDebug.setSelected(logLevel == 2);

		if (radix == 2)
		{
			this.radioButtonBinary.setSelected(true);
		}
		else if (radix == 8)
		{
			this.radioButtonOctal.setSelected(radix == 8);
		}
		else if (radix == 10)
		{
			this.radioButtonDecimal.setSelected(radix == 10);
		}
		else if (radix == 16)
		{
			this.radioButtonHexadecimal.setSelected(radix == 16);
		}
		else
		{
			this.radioButtonCustomRadix.setSelected(true);
			this.sliderRadix.setEnabled(true);
		}

		this.sliderRadix.setValue(radix);
		this.labelRadix.setText("" + radix);
	}

	public void setRadix(int radix)
	{
		this.calc.setRadix(radix);

		if (this.sliderRadix != null)
		{
			this.sliderRadix.setValue(radix);
		}
		if (this.labelRadix != null)
		{
			this.labelRadix.setText("" + radix);
		}
	}

	public void setDevMode(boolean dev)
	{
		if (this.tabbedPane != null)
		{
			this.tabbedPane.setEnabledAt(this.tabbedPane.indexOfComponent(this.panelDevTab), dev);
		}
	}

	public void setCurrentTab(int tab)
	{
		if (tab == this.tabbedPane.indexOfComponent(this.panelCalc))
		{
			this.frameCSUtil.setResizable(false);
			this.frameCSUtil.setSize(480, 450);
		}
		else
		{
			this.frameCSUtil.setResizable(true);
			this.frameCSUtil.setSize(640, 480);
		}
	}

	public void setColor(Color color)
	{
		this.calc.setColor(color);
		Component component = this.frameCSUtil.getContentPane();
		this.setColor(component, color);
	}

	public void setColor(Component component, Color color)
	{
		if (component instanceof Container && !(component instanceof JTextPane))
		{
			component.setBackground(color);
			Component[] sub = ((Container) component).getComponents();
			for (Component c : sub)
			{
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
			@Override
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
					else if (laf > 0 && laf - 2 < GUI.this.lookAndFeels.length)
					{
						UIManager.setLookAndFeel(GUI.this.lookAndFeels[laf - 2].getClassName());
					}

					if (update)
					{
						WebLookAndFeel.updateAllComponentUIs();
					}
				}
				catch (Exception e)
				{
					GUI.this.calc.devInfo("Error setting native LAF: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void print(List<String> lines)
	{
		StringBuilder builder = new StringBuilder();
		for (String s : lines)
		{
			builder.append(s).append('\n');
		}
		this.consoleTextField.setText(builder.toString());
	}
}
