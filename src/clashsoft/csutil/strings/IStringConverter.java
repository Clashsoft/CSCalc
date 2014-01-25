package clashsoft.csutil.strings;

import javax.swing.JPanel;

import clashsoft.csutil.gui.GUI;

public interface IStringConverter
{
	public void addArguments(GUI gui, JPanel panel);
	public String getName();
	public String getConvertedString(String input);
	
	public String toString();
}
