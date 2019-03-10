package clashsoft.csutil.strings.insert;

import clashsoft.csutil.strings.DefaultStringConverter;

import javax.swing.*;

public class StringConverterInsertAfter extends DefaultStringConverter
{
	public JTextField	textField;
	public JTextField	textFieldPattern;
	public JComboBox	comboBoxMode;

	public StringConverterInsertAfter()
	{
		super("Insert text after existing text");
	}

	@Override
	public void addArguments()
	{
		this.addLabel("Insert");
		this.textField = this.addTextField(null);
		this.addLabel("after");
		this.textFieldPattern = this.addTextField(null);
		this.addLabel("Mode:");
		this.comboBoxMode = this.addComboBox("First Index", "Last Index", "Regular Expression");
	}

	@Override
	public String getConvertedString(String input)
	{
		String text = this.textField.getText();
		String pattern = this.textFieldPattern.getText();
		int mode = this.comboBoxMode.getSelectedIndex();
		int pos = indexOf(input, pattern, mode);
		return insert(input, text, pos + pattern.length() + 1);
	}
}
