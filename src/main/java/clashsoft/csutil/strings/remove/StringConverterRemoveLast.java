package clashsoft.csutil.strings.remove;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import clashsoft.csutil.strings.DefaultStringConverter;

public class StringConverterRemoveLast extends DefaultStringConverter
{
	public JTextField	textFieldPattern;
	public JCheckBox	checkBoxMode;
	
	public StringConverterRemoveLast()
	{
		super("Remove text from end");
	}
	
	@Override
	public void addArguments()
	{
		this.addLabel("Remove last occurence of");
		this.textFieldPattern = this.addTextField(null);
		this.checkBoxMode = this.addCheckBox("Regular Expression");
	}
	
	@Override
	public String getConvertedString(String input)
	{
		String pattern = this.textFieldPattern.getText();
		boolean regex = this.checkBoxMode.isSelected();
		return replaceLast(input, pattern, "", regex);
	}
}