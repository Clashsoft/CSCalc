package clashsoft.cscalc;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Properties;

import javax.swing.JTextField;

import clashsoft.cscalc.gui.GUI;

public class CSCalc
{
	public static final String	ROOT		= "\u221A";
	public static final String	XOR			= "\u22BB";
	public static final String	NOT			= "\u00AC";
	public static final String	PI			= "\u03C0";
	public static final String	E			= "\u2107";
	
	public static CSCalc		instance;
	
	public String				version		= "0.1";
	
	public Path					memoryPath	= new File(getAppdataDirectory(), "memory.txt").toPath();
	
	public double				var1;
	public double				var2;
	
	public boolean				second;
	public String				mode;
	public byte					decimalPoint;
	
	public double				result;
	public double				mem;
	
	public Properties			properties;
	
	public CSCalc()
	{
		this.properties = new Properties();
		this.setLoggingLevel(0);
		this.setDevMode(false);
		this.setColor(Color.LIGHT_GRAY);
		this.setLAF(0);
		this.reset();
	}
	
	public static void main(String[] args)
	{
		instance = new CSCalc();
		instance.init();
	}
	
	// Ladefunktion
	
	public static File getSaveDataFolder()
	{
		File f = new File(getAppdataDirectory(), "cscalc");
		if (!f.exists())
		{
			f.mkdirs();
		}
		return f;
	}
	
	public static String getAppdataDirectory()
	{
		String OS = System.getProperty("os.name").toUpperCase();
		if (OS.contains("WIN"))
		{
			return System.getenv("APPDATA");
		}
		else if (OS.contains("MAC"))
		{
			return System.getProperty("user.home") + "/Library/Application Support";
		}
		else if (OS.contains("NUX"))
		{
			return System.getProperty("user.home");
		}
		return System.getProperty("user.dir");
	}
	
	// Ladefunktion
	
	public void init()
	{
		this.devInfo("CSCalc version " + this.version, 1);
		this.devInfo("Initializing...", 1);
		this.loadSettings();
		GUI.init();
		GUI.instance.updateSettings();
		this.update();
	}
	
	public void reset()
	{
		this.var1 = 0D;
		this.var2 = 0D;
		this.mem = 0D;
		this.result = 0D;
		this.decimalPoint = 0;
		this.second = false;
		this.mode = "+";
	}
	
	public void update()
	{
		GUI.instance.inputTextField.setText(this.var1 + "\n" + this.mode + " " + this.var2);
		GUI.instance.resultTextField.setText("" + this.result);
	}
	
	public void loadSettings()
	{
		this.devInfo("Loading Settings", 1);
		File file = new File(getSaveDataFolder(), "save.txt");
		
		try
		{
			this.properties.load(new FileInputStream(file));
			this.devInfo("Successfully loaded Settings", 1);
		}
		catch (IOException ex)
		{
			this.devInfo("Error loading Settings: " + ex.getMessage(), 1);
			ex.printStackTrace();
		}
	}
	
	public void saveSettings()
	{
		File file = new File(getSaveDataFolder(), "save.txt");
		
		try
		{
			file.createNewFile();
			
			this.properties.store(new FileOutputStream(file), "CSCalc configuration file");
			this.devInfo("Successfully saved Settings", 1);
		}
		catch (IOException ex)
		{
			this.devInfo("Error saving Settings: " + ex.getMessage(), 1);
			ex.printStackTrace();
		}
	}
	
	public void setCurrentVar(double value)
	{
		if (this.second)
		{
			this.var2 = value;
		}
		else
		{
			this.var1 = value;
		}
		this.update();
	}
	
	public double getCurrentVar()
	{
		if (this.second)
		{
			return this.var2;
		}
		else
		{
			return this.var1;
		}
	}
	
	public void addNumber(int number)
	{
		if (this.second)
		{
			if (this.decimalPoint > 0)
			{
				this.var2 += Math.pow(0.1D, this.decimalPoint) * number;
				this.decimalPoint++;
			}
			else
			{
				this.var2 *= 10;
				this.var2 += number;
			}
		}
		else
		{
			if (this.decimalPoint > 0)
			{
				this.var1 += Math.pow(0.1D, this.decimalPoint) * number;
				this.decimalPoint++;
			}
			else
			{
				this.var1 *= 10;
				this.var1 += number;
			}
		}
		this.update();
	}
	
	public void setCalculation(String calc)
	{
		if (this.second && this.var2 != 0D)
		{
			this.var1 = this.calculate(this.var1, this.mode, this.var2);
			this.var2 = 0D;
			this.result = 0D;
		}
		else
		{
			this.second = true;
		}
		
		this.mode = calc;
		this.setDecimalPoint(false);
		this.update();
	}
	
	public void setDecimalPoint(boolean flag)
	{
		this.decimalPoint = (byte) (flag ? 1 : 0);
		this.update();
	}
	
	public strictfp double calculate(double var1, String mode, double var2)
	{
		switch (mode)
		{
		case "+":
			return var1 + var2;
		case "-":
			return var1 - var2;
		case "*":
			return var1 * var2;
		case "/":
			return var1 / var2;
		case "%":
			return var1 % var2;
		case "^":
			return Math.pow(var1, var2);
		case ROOT:
			return Math.pow(var1, 1D / var2);
		case "|":
			return Double.longBitsToDouble(Double.doubleToRawLongBits(1) | Double.doubleToRawLongBits(var2));
		case "&":
			return Double.longBitsToDouble(Double.doubleToRawLongBits(var1) & Double.doubleToRawLongBits(var2));
		case XOR:
			return Double.longBitsToDouble(Double.doubleToRawLongBits(var1) ^ Double.doubleToRawLongBits(var2));
		case ">>":
			return var1 / Math.pow(2D, (int) var2);
		case ">>>":
			return var1 / Math.pow(2D, (int) var2);
		case "<<":
			return var1 * Math.pow(2D, (int) var2);
		}
		return 0D;
	}
	
	public void setLoggingLevel(int level)
	{
		this.properties.setProperty("logginglevel", level + "");
	}
	
	public int getLoggingLevel()
	{
		return Integer.parseInt(this.properties.getProperty("logginglevel"));
	}
	
	public void setDevMode(boolean dev)
	{
		this.properties.setProperty("devmode", dev + "");
		GUI.instance.setDevMode(dev);
	}
	
	public boolean getDevMode()
	{
		return Boolean.parseBoolean(properties.getProperty("devmode"));
	}
	
	public Color getColor()
	{
		int color = Integer.parseInt(this.properties.getProperty("color"), 16);
		return new Color(color);
	}
	
	public void setColor(Color color)
	{
		int i = color.getRGB() & 0xFFFFFF;
		this.properties.setProperty("color", Integer.toHexString(i));
	}
	
	public int getLAF()
	{
		return Integer.parseInt(this.properties.getProperty("lookandfeel"));
	}
	
	public void setLAF(int laf)
	{
		this.properties.setProperty("lookandfeel", laf + "");
	}
	
	public void devInfo(String text)
	{
		GUI.instance.print(text);
	}
	
	public void devInfo(String text, int level)
	{
		if (this.getLoggingLevel() >= level)
		{
			this.devInfo(text);
		}
	}
	
	// Numpad
	
	public void button0_click()
	{
		this.addNumber(0);
	}
	
	public void button1_click()
	{
		this.addNumber(1);
	}
	
	public void button2_click()
	{
		this.addNumber(2);
	}
	
	public void button3_click()
	{
		this.addNumber(3);
	}
	
	public void button4_click()
	{
		this.addNumber(4);
	}
	
	public void button5_click()
	{
		this.addNumber(5);
	}
	
	public void button6_click()
	{
		this.addNumber(6);
	}
	
	public void button7_click()
	{
		this.addNumber(7);
	}
	
	public void button8_click()
	{
		this.addNumber(8);
	}
	
	public void button9_click()
	{
		this.addNumber(9);
	}
	
	public void buttonDecimalPoint_click()
	{
		this.setDecimalPoint(true);
		this.devInfo("Switching to Comma Mode", 1);
	}
	
	// Basic Operations
	
	public void buttonAdd_click()
	{
		this.setCalculation("+");
	}
	
	public void buttonSubstract_click()
	{
		this.setCalculation("-");
	}
	
	public void buttonMultiply_click()
	{
		this.setCalculation("*");
	}
	
	public void buttonDivide_click()
	{
		this.setCalculation("/");
	}
	
	public void buttonPower_click()
	{
		this.setCalculation("^");
	}
	
	public void buttonRoot_click()
	{
		this.setCalculation(ROOT);
	}
	
	public void buttonRemainder_click()
	{
		this.setCalculation("%");
	}
	
	public void buttonReciprocal_click()
	{
		this.setCurrentVar(1D / this.getCurrentVar());
	}
	
	public void buttonNegate_click()
	{
		this.setCurrentVar(-this.getCurrentVar());
	}
	
	// Binary Operations
	
	public void buttonAND_click()
	{
		this.setCalculation("&");
	}
	
	public void buttonOR_click()
	{
		this.setCalculation("|");
	}
	
	public void buttonXOR_click()
	{
		this.setCalculation(XOR);
	}
	
	public void buttonNOT_click()
	{
		this.setCurrentVar(Double.longBitsToDouble(~Double.doubleToRawLongBits(this.getCurrentVar())));
	}
	
	public void buttonBitshiftLeft_click()
	{
		this.setCalculation("<<");
	}
	
	public void buttonBitshiftRight_click()
	{
		this.setCalculation(">>");
	}
	
	public void buttonBitshiftRightU_click()
	{
		this.setCalculation(">>>");
	}
	
	// Calculate Button
	
	public void buttonCalculate_click()
	{
		this.result = this.calculate(this.var1, this.mode, this.var2);
		this.second = false;
		this.update();
	}
	
	// Clear Buttons
	
	public void buttonCE_click()
	{
		this.reset();
	}
	
	public void buttonC_click()
	{
		setCurrentVar(0D);
	}
	
	// Memory Buttons
	
	public void buttonM_click()
	{
		
	}
	
	public void buttonML_click()
	{
		
	}
	
	public void buttonMC_click()
	{
		this.mem = 0D;
	}
	
	// Konstanten Buttons
	
	public void buttonPI_click()
	{
		this.setCurrentVar(Math.PI);
	}
	
	public void buttonE_click()
	{
		this.setCurrentVar(Math.E);
	}
	
	public void handleCommand(String text)
	{
		if ("restart".equals(text))
		{
			this.restart();
		}
		else if ("exit".equals(text))
		{
			this.exit();
		}
		else if ("clear".equals(text))
		{
			GUI.instance.consoleTextField.setText("");
		}
		else if ("calculate".equals(text))
		{
			this.buttonCalculate_click();
		}
		else if ("reset".equals(text))
		{
			this.reset();
			// TODO Reset Settings
		}
		else if ("info".equals(text))
		{
			this.devInfo("CSCalc version " + this.version);
			this.devInfo("Copyright (c) 2014, Clashsoft");
		}
		else if ("?".equals(text) || "help".equals(text))
		{
			this.devInfo("Avaiable commands:", 0);
			this.devInfo("calculate - Calculates the current equation", 0);
			this.devInfo("clear - Clears the console", 0);
			this.devInfo("exit - Exits the Application", 0);
			this.devInfo("info - Opens the Info Dialog", 0);
			this.devInfo("reset - Resets the Settings", 0);
			this.devInfo("restart - Restarts the Application", 0);
		}
		else
		{
			this.devInfo("Unknown console command", 0);
		}
	}
	
	// Key Manager
	
	public void window_keyTyped(char c)
	{
		// TODO
	}
	
	public void commandInputTextField_keyTyped(char c)
	{
		if (c == '\n')
		{
			JTextField textField = GUI.instance.commandTextField;
			this.handleCommand(textField.getText().trim());
			textField.setText("");
		}
	}
	
	public void exit()
	{
		this.saveSettings();
		System.exit(0);
	}
	
	public void restart()
	{
		try
		{
			File currentJar = new File(CSCalc.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			if (!currentJar.getName().endsWith(".jar"))
			{
				return;
			}
			
			String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
			final ProcessBuilder builder = new ProcessBuilder(Arrays.asList(javaBin, "-jar", currentJar.getPath()));
			builder.start();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		this.exit();
	}
}
