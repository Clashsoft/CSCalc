package clashsoft.csutil.strings;

import clashsoft.csutil.gui.GUI;

import javax.swing.*;

public interface IStringConverter
{
	void addArguments(GUI gui, JPanel panel);

	String getName();

	String getConvertedString(String input);

	@Override
	String toString();
}
