package clashsoft.cscalc.strings.remove;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import clashsoft.cscalc.strings.DefaultStringConverter;

public class StringConverterRemoveFirst extends DefaultStringConverter
{
	public JTextField	textFieldPattern;
	public JCheckBox	checkBoxMode;
	
	public StringConverterRemoveFirst()
	{
		super("Remove text from beginning");
	}
	
	@Override
	public void addArguments()
	{
		this.addLabel("Replace first occurence of");
		this.textFieldPattern = this.addTextField(null);
		this.checkBoxMode = this.addCheckBox("Regular Expression");
	}
	
	@Override
	public String getConvertedString(String input)
	{
		String pattern = this.textFieldPattern.getText();
		boolean regex = this.checkBoxMode.isSelected();
		return replaceFirst(input, pattern, "", regex);
	}
}
