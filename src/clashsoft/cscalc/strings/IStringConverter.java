package clashsoft.cscalc.strings;

import javax.swing.JPanel;

import clashsoft.cscalc.gui.GUI;

public interface IStringConverter
{
	public void addArguments(GUI gui, JPanel panel);
	public String getName();
	public String getConvertedString(String input);
	
	public String toString();
}
