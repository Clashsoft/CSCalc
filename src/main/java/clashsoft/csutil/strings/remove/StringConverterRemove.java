package clashsoft.csutil.strings.remove;

import clashsoft.csutil.strings.DefaultStringConverter;

import javax.swing.*;

public class StringConverterRemove extends DefaultStringConverter
{
	public JTextField textFieldPattern;
	public JCheckBox  checkBoxMode;

	public StringConverterRemove()
	{
		super("Remove text");
	}

	@Override
	public void addArguments()
	{
		this.addLabel("Remove");
		this.textFieldPattern = this.addTextField(null);
		this.checkBoxMode = this.addCheckBox("Regular Expression");
	}

	@Override
	public String getConvertedString(String input)
	{
		String pattern = this.textFieldPattern.getText();
		boolean regex = this.checkBoxMode.isSelected();
		return replaceAll(input, pattern, "", regex);
	}
}
