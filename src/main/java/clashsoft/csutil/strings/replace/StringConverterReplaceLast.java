package clashsoft.csutil.strings.replace;

import clashsoft.csutil.strings.DefaultStringConverter;

import javax.swing.*;

public class StringConverterReplaceLast extends DefaultStringConverter
{
	public JTextField textField;
	public JTextField textFieldPattern;
	public JCheckBox  checkBoxMode;

	public StringConverterReplaceLast()
	{
		super("Replace text from end");
	}

	@Override
	public void addArguments()
	{
		this.addLabel("Replace last occurence of");
		this.textFieldPattern = this.addTextField(null);
		this.addLabel("with");
		this.textField = this.addTextField(null);
		this.checkBoxMode = this.addCheckBox("Regular Expression");
	}

	@Override
	public String getConvertedString(String input)
	{
		String text = this.textField.getText();
		String pattern = this.textFieldPattern.getText();
		boolean regex = this.checkBoxMode.isSelected();
		return replaceLast(input, pattern, text, regex);
	}
}
