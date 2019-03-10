package clashsoft.csutil.strings.add;

import clashsoft.csutil.strings.DefaultStringConverter;

import javax.swing.*;

public class StringConverterAddEnd extends DefaultStringConverter
{
	public JTextField	textField;

	public StringConverterAddEnd()
	{
		super("Add text to end");
	}

	@Override
	public void addArguments()
	{
		this.addLabel("Add");
		this.textField = this.addTextField(null);
		this.addLabel("to end of text");
	}

	@Override
	public String getConvertedString(String input)
	{
		return input + this.textField.getText();
	}
}
